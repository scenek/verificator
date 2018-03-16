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
	<form:input path="cache" value="${cacheCode}" type="hidden" />
	<table class="centered" style="min-width: 330px;">
	<tr>
		<td style="width: 120px; text-align: right;"><strong>Stage:</strong></td>
		<td><form:input path="stageNo" size="2" /></td>
	</tr>
	<tr>
		<td style="width: 120px; text-align: right;"><strong>Password:</strong></td>
		<td><form:input path="password" size="16" /></td>
	</tr>
	<tr>
		<td></td>
		<td><button class="g-recaptcha" data-sitekey="${recaptchasitekey}" data-callback='onSubmit'>Submit</button></td>
	</tr>
	</table>
	</form:form>
</c:set>

<%@ include file="_template.jsp"%>
