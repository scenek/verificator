<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title">Edit cache</c:if></c:set>

<c:set var="body">
<form:form modelAttribute="cache">
<form:hidden path="id"/>
<table>
<tr><td>Code:</td><td><form:input path="code" size="10" /> <form:errors path="code" cssClass="errors" /></td></tr>
<tr><td>Title:</td><td><form:input path="title" size="40" /> <form:errors path="title" cssClass="errors" /></td></tr>
<tr><td>URL:</td><td><form:input path="url" size="40" /> <form:errors path="url" cssClass="errors" /></td></tr>
<tr><td>Description:</td><td><form:input path="desc" /> <form:errors path="desc" cssClass="errors" /></td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td></td><td><input type="submit" value="save"></td></tr>
</table>
</form:form>
<p><a href="cacheadm.go">back</a></p>
</c:set>

<%@ include file="_template.jsp" %>
