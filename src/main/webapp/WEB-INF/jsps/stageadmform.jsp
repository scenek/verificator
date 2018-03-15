<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title">Edit stage<c:if test="${not empty web.title}"> - ${web.title}</c:if></c:set>

<c:set var="body">
<form:form modelAttribute="stage">
<form:hidden path="id"/>
<form:hidden path="cache"/>
<table>
<tr><td>Title:</td><td><form:input path="title" size="50"/> <form:errors path="title" cssClass="errors"/></td></tr>
<tr><td>Coords:</td><td><form:input path="coords" size="25"/> <form:errors path="coords" cssClass="errors"/></td></tr>
<tr><td>Stage Wpt:</td><td><form:input path="stageId" size="6"/> <form:errors path="stageId" cssClass="errors"/></td></tr>
<tr><td>Stage No:</td><td><form:input path="stageNo" size="2"/> <form:errors path="stageNo" cssClass="errors"/></td></tr>
<tr><td>Password:</td><td><form:input path="password" size="20"/> <form:errors path="password" cssClass="errors"/></td></tr>
<tr><td>Description:</td><td><form:input path="description"/> <form:errors path="description" cssClass="errors"/></td></tr>
<tr><td>Message:</td><td><form:input path="message"/> <form:errors path="message" cssClass="errors"/></td></tr>
<tr><td>Message Coords:</td><td><form:input path="messageCoords"/> <form:errors path="messageCoords" cssClass="errors"/></td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td></td><td><input type="submit" value="save"></td></tr>
</table>
</form:form>
<p><a href="cacheadm.go">back</a></p>
</c:set>

<%@ include file="_template.jsp" %>