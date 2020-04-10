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
<h2><spring:message code="label.plane"/></h2>
<form:form method="post" action="addPlane.html" commandName="plane">
 
    <table cellpadding=5>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="planeId"><spring:message code="label.planeId"/></form:label></td>
        <td><form:input path="planeId" /></td>
        <td><form:errors path="planeId"/></td>
    </tr>
    
    <tr>
        <td><form:label path="planeName"><spring:message code="label.planeName"/></form:label></td> 
        <td><form:input path="planeName" /></td>
        <td><form:errors path="planeName"/></td>
    </tr>
    <tr>
        <td colspan="2">
        <c:if test="${plane.id==0}">
            <input type="submit" value="<spring:message code="label.addPlane"/>"/>
        </c:if>    
        <c:if test="${plane.id!=0}">
        	<input type="submit" value="<spring:message code="label.editPlane"/>"/>
       	</c:if>
        </td>
    </tr>
</table> 
</form:form>

<h3><spring:message code="label.planeList"/></h3>

<table class="data" border=1 frame=void rules=rows cellpadding=10>
<tr>
    <th><spring:message code="label.planeId"/></th>
    <th><spring:message code="label.planeName"/></th>
    
    <th>&nbsp;</th>
    <th>&nbsp;</th>
</tr>
<c:if  test="${!empty planeList}">
<c:forEach items="${planeList}" var="plane">
    <tr>
        <td>${plane.planeId} </td>
        <td>${plane.planeName} </td>
        <td>
	        <a href="delete/plane/${plane.id}.html">Delete</a>
	        <a href="planes.html?planeId=${plane.id}">Edit</a>
	        <a href="planeDetails.html?planeId=${plane.id}">Flights</a>
        </td>
    </tr>
</c:forEach>
</table>
</c:if>

</body>
</html>