<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<c:set var="body">
	<form:form modelAttribute="verificator">
		<table class="centered" style="min-width: 330px;">
			<tr>
				<td style="width: 120px; text-align: right;"><strong>Cache:</strong></td>
				<td><form:input path="cache" size="10" /> <form:errors
					path="cache" cssClass="errors" /></td>

			</tr>
			<tr>
				<td style="text-align: right;"><strong>Stage:</strong></td>
				<td><form:input path="stageNo" size="2" /> <form:errors
					path="stageNo" cssClass="errors" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><strong>Password:</strong></td>
				<td><form:input path="password" size="16" /> <form:errors
					path="password" cssClass="errors" /></td>
			</tr>
			<tr>
				<td colspan="2">
				<%
					Boolean validCaptcha = false;
					HttpSession session = request.getSession();
					if (!session.isNew()) {
						try {
							validCaptcha = (Boolean) session
									.getAttribute("ValidCaptcha");
						} catch (NullPointerException ex) {
							session
							.setAttribute("ValidCaptcha", false);
						}
					}

					if (validCaptcha != null && !validCaptcha) {
						ReCaptcha c = ReCaptchaFactory.newReCaptcha(
								"6LcAsL4SAAAAAFX2CbMGosJcWoCt78hxxuMFLbA7",
								"6LcAsL4SAAAAAJ6iyZ60sgeyQc2YhgI9fwt5uINs",
								false);
						out.print(c.createRecaptchaHtml(null, null));
					}
				%> <form:errors path="captcha" cssClass="errors" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="verify"></td>
			</tr>
		</table>
	</form:form>
	<br />
	<hr />
	<br />
	<div class="centered" style="max-width: 660px">
	<div style="float: left; display: inline-block; width: 330px;"><strong>Hints:</strong>
	<ul>
		<li><small>please be gentle to this verificator and don't
		try brutal force. passwords are long enough and server is limited by
		quotas. don't make me use time/no/ip limit restriction...</small></li>
		<li><small>tested on: Opera Mini, Opera Mobile, Dorothy
		Browser, Chrome, FF, Android Browser</small></li>
		<li><small><strong>cache</strong> - GC waypoint (eg.
		GCxxxxx)</small></li>
		<li><small><strong>stage</strong> - stage no. (1, 2...99)</small></li>
		<li><small><strong>password</strong> - concatenation of
		codes from previous and actual stage (eg. stage 1 = AAA, stage 2 =
		BBB, <strong>password</strong> on stage <strong>2</strong> will be <strong>AAABBB</strong>),
		first stage is exception (only one code)</small></li>
		<li><small><strong>password</strong> - case sensitive</small></li>
	</ul>
	</div>
	<div style="display: inline-block; width: 330px;"><strong>Nápověda:</strong>
	<ul>
		<li><small>buďte prosím ohleduplní k tomuto verifikátoru
		i k ostatním kačerům a nezkoušejte brutal force. hesla jsou dostatečně
		dlouhá a server má nastaveny limity. nenuťte mě nasadit omezení podle
		času/počtu/ip.</small></li>
		<li><small>testováno na: Opera Mini, Opera Mobile,
		Dorothy Browser, Chrome, FF, Android Browser</small></li>
		<li><small><strong>cache</strong> - kód keše (např.
		GCxxxxx)</small></li>
		<li><small><strong>stage</strong> - číslo stage (1,
		2...99)</small></li>
		<li><small><strong>password</strong> - kód z předchozí a
		aktuální stage zapsaný za sebou (např. stage 1 = AAA, stage 2 = BBB, <strong>heslo</strong>
		pro stage <strong>2</strong> je tedy <strong>AAABBB</strong>), první
		stage je vyjímka (pouze jeden kód)</small></li>
		<li><small><strong>password</strong> - dodržujte
		velikosti písmen</small></li>
	</ul>
	</div>
	</div>
</c:set>

<%@ include file="_template.jsp"%>
