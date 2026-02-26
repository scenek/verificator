<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
	<link rel="stylesheet" type="text/css" href="/style.css">
	<title><c:if test="${not empty title}">${title} - </c:if>GC Verificator</title>
	<script src="https://www.google.com/recaptcha/api.js" async defer></script>
	<script>
		function onSubmit(token) {
			console.log('success!');
			document.getElementById("verificator").submit();
		}
	</script>
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
</body>
</html>
