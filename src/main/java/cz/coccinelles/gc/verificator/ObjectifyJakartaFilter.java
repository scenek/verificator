package cz.coccinelles.gc.verificator;

import java.io.Closeable;
import java.io.IOException;

import com.googlecode.objectify.ObjectifyService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Jakarta Servlet filter that wraps each request in an Objectify session.
 * Replaces the built-in ObjectifyFilter (which uses javax.servlet).
 */
public class ObjectifyJakartaFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try (Closeable session = ObjectifyService.begin()) {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
