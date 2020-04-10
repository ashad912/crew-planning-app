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
	$(document).ready(function($) {
  		$("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
  	});
</script>

<script>
	$(document).ready(function($) {
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
<form:form method="post" action="addFlight.html" commandName="flight">
    <table cellpadding=5>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="flightId"><spring:message code="label.flightId"/></form:label></td>
        <td><form:input path="flightId" /></td>
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
        <c:if test="${flight.id==0}">
            <input type="submit" value="<spring:message code="label.addFlight"/>"/>
        </c:if>
        <c:if test="${flight.id!=0}">
            <input type="submit" value="<spring:message code="label.editFlight"/>" style = ""/>
        </c:if>
        </span>
        </td>
    </tr>
</table> 
</form:form>
<br>
<h3><spring:message code="label.flightList"/></h3>
<a href="flights.html">Wszystkie</a> |
<a href="flights.html?chosenMonth=1">Styczeń</a> | 
<a href="flights.html?chosenMonth=2">Luty</a> | 
<a href="flights.html?chosenMonth=3">Marzec</a> | 
<a href="flights.html?chosenMonth=4">Kwiecień</a> | 
<a href="flights.html?chosenMonth=5">Maj</a> | 
<a href="flights.html?chosenMonth=6">Czerwiec</a><br>
<a href="flights.html?chosenMonth=7">Lipiec</a> | 
<a href="flights.html?chosenMonth=8">Sierpień</a> | 
<a href="flights.html?chosenMonth=9">Wrzesień</a> | 
<a href="flights.html?chosenMonth=10">Październik</a> | 
<a href="flights.html?chosenMonth=11">Listopad</a> | 
<a href="flights.html?chosenMonth=12">Grudzień</a>


<table class="data" border=1 frame=void rules=rows cellpadding=10>
<tr>
    <th><spring:message code="label.flightId"/></th>
    <th><spring:message code="label.flightStartDate"/></th>
    <th><spring:message code="label.flightEndDate"/></th>
    <th><spring:message code="label.plane"/></th>
    <th><spring:message code="label.crewMembers"/></th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
</tr>
<c:if  test="${!empty flightList}">
<c:forEach items="${flightList}" var="flight">
    <tr>
        <td>${flight.flightId} </td>
        <td>${flight.startDate} </td>
        <td>${flight.endDate} </td>
        <td>${flight.plane.planeName}</td>
        <td>
        <c:forEach items="${flight.crewMember}" var="crewMemberVar">
        	${crewMemberVar.firstname} ${crewMemberVar.lastname} <br>
        </c:forEach>
        </td>
        <td>
        	<a href="delete/flight/${flight.id}.html">Delete</a>
        	<a href="flights.html?flightId=${flight.id}">Edit</a>
        </td>
    </tr>
</c:forEach>
</table>
</c:if>
</body>
</html>