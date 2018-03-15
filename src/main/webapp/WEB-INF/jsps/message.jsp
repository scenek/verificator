<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="body">
${message}
<hr />
<form:form modelAttribute="verificator">
<strong>Stage:</strong><form:input path="stageNo" size="2" /> / 
<strong>Password:</strong><form:input path="password" size="16" />
<input type="submit" value="verify">
</form:form>
</c:set>

<%@ include file="_template.jsp"%>