package cz.coccinelles.gc.verificator.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.jsp.JspFactory;
import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;

/**
 * Initialises JSP support on App Engine java21 (Jetty 12 EE10).
 *
 * Jetty 12 with the quickstart-web.xml approach registers pre-compiled JSP
 * classes directly as servlets, bypassing the normal JspServlet initialisation.
 * This means two things are never set up automatically:
 *
 *   1. JspFactory.getDefaultFactory() — needed by pre-compiled JSP classes to
 *      obtain / release PageContext instances.
 *   2. org.apache.tomcat.InstanceManager in the ServletContext — needed by
 *      Jasper's TagHandlerPool to initialise JSTL tag handler instances.
 *
 * mortbay-apache-jsp-11.0.10.1.jar is added to the application by the App Engine
 * staging tool (it is also the compiler used for the pre-compiled JSPs, so its
 * JspFactoryImpl must be used — not wasp's — to avoid ELContextImpl cast errors).
 */
public class JasperInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 1. Register the wasp JspFactory so pre-compiled JSP _jspxFactory is non-null
        if (JspFactory.getDefaultFactory() == null) {
            JspFactory.setDefaultFactory(new JspFactoryImpl());
        }

        // 2. Register the Tomcat InstanceManager so TagHandlerPool can initialise
        sce.getServletContext().setAttribute(
                InstanceManager.class.getName(),
                new SimpleInstanceManager());
    }
}
