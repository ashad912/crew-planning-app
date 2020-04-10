<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
<script>
	$(document).ready(function() {
  		$("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
  	});
</script>


<script>
	$(document).ready(function() {
  		$("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });
  	});
</script>

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
<h2><spring:message code="label.flight"/></h2>
<form:form method="post" action="flightEdit.html" commandName="flight">
    <table cellpadding=5>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="flightId"><spring:message code="label.flightId"/></form:label></td>
        <c:if test="${planningMember.admin}">
            <td><form:input path="flightId"/></td>
        </c:if>
        <c:if test="${not planningMember.admin}">
            <td><form:input path="flightId" readonly="true"/></td>
        </c:if>
        <td><form:errors path="flightId"/></td>
    </tr>
    <tr>
        <td><form:label path="startDate"><spring:message code="label.flightStartDate"/></form:label></td>
        <td><form:input path="startDate" id="datepicker"/></td>
        <td><form:errors path="startDate"/></td>
    </tr>
    <tr>
        <td><form:label path="endDate"><spring:message code="label.flightEndDate"/></form:label></td>
        <td><form:input path="endDate" id="datepicker2"/></td>
        <td><form:errors path="endDate"/></td>
    </tr>
    <tr>
    <tr>
		<td><form:label path="plane"><spring:message code="label.plane"/></form:label></td>
		<td><form:select path="plane">
				<c:forEach items="${planeList}" var="plane">
        			<option value="${plane.id}" ${plane.id == selectedPlane ? 'selected="selected"' : ''}>${plane.planeName}</option>
   				</c:forEach>
		</form:select></td>
		<td><form:errors path="plane"/></td>
	</tr>
    <tr>
		<td><form:label path="crewMember"><spring:message code="label.crewMembers"/></form:label></td>
		<td><form:select path="crewMember" multiple="true">
				<c:forEach items="${crewMemberList}" var="crewMember">
				<option value="${crewMember.id}"><c:out value = "${crewMember.firstname} ${crewMember.lastname}"/>
				</c:forEach>
		</form:select></td>
		<td><form:errors path="crewMember"/></td>
	</tr>
    <tr>
    <tr>
        <td colspan="4">
        <span style="float: right">
            <input type="submit" value="<spring:message code="label.editFlight"/>"/>
        </span>
        </td>
    </tr>
</table> 
</form:form>
</body>
</html>