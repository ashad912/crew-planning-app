<!--  <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 
<p><spring:message code="label.menu"/></p>
<a href="hello.html">Say Hello</a><br>
<a href="users.html">User page</a><br>
<a href="persons.html">Person page</a><br>
-->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<p><spring:message code="label.menu"/></p>
<a href="hello.html">Say Hello</a><br>
<a href="users.html">User page</a><br>
<a href="persons.html">Person page</a><br>
<a href="login.html">Login</a><br>
<a href="planes.html">Plane</a><br>
<a href="flights.html">Flights</a><br>
<a href="crewMembers.html">Crew Members</a><br>
<a href="planningMembers.html">Planning Members</a><br>
<sec:authorize access="hasRole('ROLE_USER')">
	<a href="persons.html">Person page</a><br>
</sec:authorize>

 
<sec:authorize access="isAnonymous()">
    Restricted link, you are not allowed to access this resource.<br>    
</sec:authorize>
<a href="admin.html">Admin</a>