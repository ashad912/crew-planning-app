<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

<style>
input[type="submit"]{
    /* change these properties to whatever you want */
    background-color: #4169e1;
    color: #fff;
    border-radius: 5px;
}
</style>

<title><spring:message code="label.title"/></title>
</head>
<body>
<h2>${crewMemberEdited.firstname} ${crewMemberEdited.lastname}</h2>
<form:form method="post" action="crewMemberEdit.html?${_csrf.parameterName}=${_csrf.token}" commandName="crewMemberEdited" enctype="multipart/form-data">
    <table cellpadding=5>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="crewMemberId"><spring:message code="label.crewMemberId"/></form:label></td>
        <td><form:input path="crewMemberId" readonly="true"/></td>
        <td><form:errors path="crewMemberId"/></td>
    </tr>
    <tr>
        <td><form:label path="password"><spring:message code="label.password"/></form:label></td>
        <td><form:input type="password" path="password" /></td>
        <td><form:errors path="password"/></td>
    </tr>
    <tr>
        <td><form:label path="firstname"><spring:message code="label.firstname"/></form:label></td> 
        <td><form:input path="firstname" /></td>
        <td><form:errors path="firstname"/></td>
    </tr>
    <tr>
        <td><form:label path="lastname"><spring:message code="label.lastname"/></form:label></td>
        <td><form:input path="lastname" /></td>
        <td><form:errors path="lastname"/></td>
    </tr>
	<tr>
 		<td><form:label path="avatar"><spring:message code="label.avatar"/></form:label>	</td>
 		<td>
	<c:if test="${crewMemberEdited.id != 0}">
	
		<c:if test="${not empty crewMemberEdited.avatar}">
	 	<a href="<c:url value="crewMembers/avatar.html?crewMemberId=${crewMemberEdited.id}"/>" target="_blank"><spring:message code="avatar.download"/></a>
		</c:if>
	
		<c:if test="${empty crewMemberEdited.avatar}">
	 	<a><spring:message code="avatar.noPicture"/></a>
		</c:if>
		<br>
	</c:if>	
	<input id="avatar" name="avatar" type="file"/> </td>
    </tr>
    <tr>
    <tr>
        <td colspan="4">
        <span style="float: right">
        <c:if test="${crewMemberEdited.id==0}">
            <input type="submit" value="<spring:message code="label.addCrewMember"/>"/>
        </c:if>
        <c:if test="${crewMemberEdited.id!=0}">
            <input type="submit" value="<spring:message code="label.editCrewMember"/>"/>
        </c:if>
        </span>
        </td>
    </tr>
</table> 
</form:form>

</body>
</html>