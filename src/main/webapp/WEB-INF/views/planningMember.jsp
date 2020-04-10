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
<script src='https://www.google.com/recaptcha/api.js'></script>
  
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
<h2><spring:message code="label.planningMember"/></h2>
<form:form method="post" action="addPlanningMember.html?${_csrf.parameterName}=${_csrf.token}" commandName="planningMember">
	
    <table cellpadding=5>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="planningMemberId"><spring:message code="label.planningMemberId"/></form:label></td>
        <td><form:input path="planningMemberId" /></td>
        <td><form:errors path="planningMemberId"/></td>
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
		<td><form:label path="flight"><spring:message code="label.flight"/></form:label></td>
		<td><form:select path="flight">
				<option  value= "" />
				<c:forEach items="${flightList}" var="flight">
        			<option value="${flight.id}" ${flight.id == selectedFlights ? 'selected="selected"' : ''}>${flight.flightId}</option>
   				</c:forEach>
		</form:select></td>
		<td><form:errors path="flight"/></td>
	</tr>
    <tr>
        <td><form:label path="admin"><spring:message code="label.admin"/></form:label></td>
        <td><form:checkbox path="admin" /></td>
        <td><form:errors path="admin"/></td>
    </tr>
    <tr>
    <tr>
        <td>
          <div class="g-recaptcha" data-sitekey="6LeuO2EUAAAAADZlDHrL7tUosReZ4vwHws0x-dCU"></div>
        </td>
    </tr>
    <tr>
        <td colspan="4">
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty emailError}">
            <div class="emailError">${error}</div>
        </c:if>
        </td>
    </tr>
    <tr>
        <td colspan="4">
        <span style="float: right">
        <c:if test="${planningMember.id==0}">
            <input type="submit" value="<spring:message code="label.addPlanningMember"/>"/>
        </c:if>
        <c:if test="${planningMember.id!=0}">
            <input type="submit" value="<spring:message code="label.editPlanningMember"/>"/>
        </c:if>
        </span>
        </td>
    </tr>
</table> 
</form:form>
<h3><spring:message code="label.planningMemberList"/></h3>

<table  class="data" border=1 frame=void rules=rows cellpadding=10>
<tr>
	<th><spring:message code="label.planningMemberId"/></th>
    <th><spring:message code="label.firstname"/></th>
    <th><spring:message code="label.lastname"/></th>
    <th><spring:message code="label.flightId"/></th>
    <th><spring:message code="label.enabled"/></th>
    
    <th>&nbsp;</th>
    <th>&nbsp;</th>
</tr>
<c:if  test="${!empty planningMemberList}">
<c:forEach items="${planningMemberList}" var="planningMember">
    <tr>
    	<td>${planningMember.planningMemberId} </td>
        <td>${planningMember.firstname} </td>
        <td>${planningMember.lastname} </td>
        <td>${planningMember.flight.flightId} </td>
        <c:if test="${planningMember.enabled}">
            <td><spring:message code="label.yes"/></td>
        </c:if>
        <c:if test="${not planningMember.enabled}">
            <td><spring:message code="label.no"/></td>
        </c:if>
        <td>
        	<a href="delete/planningMember/${planningMember.id}.html">Delete</a>
       		<a href="planningMembers.html?planningMemberId=${planningMember.id}">Edit</a>
    	</td>
    </tr>
</c:forEach>
</table>
</c:if>
</body>
</html>