<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<link rel="stylesheet" type="text/css" href="/style.css">
<title><c:if test="${not empty title}">${title} - </c:if>GC Verificator</title>
</head>
<body><div id="container">
<div id="header">
<h1><a href="/">GC Verificator</a></h1>
<c:if test="${not empty title}"><h2>${title}</h2></c:if>
</div>

${body}

<div id="footer">
	(c) 2010 <b>les-coccinelles</b>
</div>

</div>
<!--<c:if test='${pageContext.request.serverName != "localhost"}'><script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-9291278-1");
pageTracker._trackPageview();
} catch(err) {}</script></c:if>-->
</body>
</html>