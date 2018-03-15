<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title">Administration</c:set>

<c:set var="body">

<table border="1" cellspacing="0" class="list">
<tr><th>Cache</th><th>Title</th><th>Stages</th><th><a href="/cacheadm.go?editCache" class="invert">+</a></th></tr>
<c:forEach items="${caches}" var="i"><tr> 
	<td class="nowrap"><a href="/cacheadm.go?showCache=${i.id}"><c:out value="${i.code}"/></a></td>
 	<td class="nowrap"><c:out value="${i.title}"/></td>
 	<td class="nowrap center"><a href="/stageadm.go?addStage=${i.id}" class="invert">+</a></td>
 	<td class="nowrap center"><a href="/cacheadm.go?editCache=${i.id}">edit</a>, <a href="${i.url}">web</a></td>
</tr></c:forEach>
</table>
</c:set>

<%@ include file="_template.jsp" %>