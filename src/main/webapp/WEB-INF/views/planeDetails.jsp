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
<h2>${plane.planeName}</h2>

<h3><spring:message code="label.flightListForPlane"/></h3>
<a href="planeDetails.html?planeId=${plane.id}">Wszystkie</a> |
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=1">Styczen </a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=2">Luty</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=3">Marzec</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=4">Kwiecień</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=5">Maj</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=6">Czerwiec</a><br>
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=7">Lipiec</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=8">Sierpień</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=9">Wrzesień</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=10">Październik</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=11">Listopad</a> | 
<a href="planeDetails.html?planeId=${plane.id}&chosenMonth=12">Grudzień</a>
<table class="data" border=1 frame=void rules=rows cellpadding=10>
<tr>
    <th><spring:message code="label.flightId"/></th>
    <th><spring:message code="label.flightStartDate"/></th>
    <th><spring:message code="label.flightEndDate"/></th>
    <th><spring:message code="label.crewMembers"/></th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
</tr>
<c:if  test="${!empty flightsForPlane}">
<c:forEach items="${flightsForPlane}" var="flight">
    <tr>
        <td>${flight.flightId} </td>
        <td>${flight.startDate} </td>
        <td>${flight.endDate} </td>
        <td>
        <c:forEach items="${flight.crewMember}" var="crewMemberVar">
        	${crewMemberVar.firstname} ${crewMemberVar.lastname} <br>
        </c:forEach>
        </td>
    </tr>
</c:forEach>
</table>
</c:if>
</body>
</html>