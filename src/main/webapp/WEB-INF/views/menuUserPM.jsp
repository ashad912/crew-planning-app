<!--  <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 
<p><spring:message code="label.menu"/></p>
<a href="hello.html">Say Hello</a><br>
<a href="users.html">User page</a><br>
<a href="persons.html">Person page</a><br>
-->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<p><spring:message code="label.menu"/></p>
<sec:authorize access="hasRole('ROLE_USER_PM')">
<a href="userPlanningMember.html">Flight Management</a><br>
<a href="planningMemberEditInit.html">Personal Data Edit</a><br>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER_PM') or hasRole('ROLE_ADMIN')">
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
<a href="planes.html">Planes</a><br>
<a href="flights.html">Flights</a><br>
<a href="crewMembers.html">Crew Members</a><br>
<a href="planningMembers.html">Planning Members</a><br>
</sec:authorize>
