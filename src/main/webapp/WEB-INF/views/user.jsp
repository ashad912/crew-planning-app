<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="label.title"/></title>
</head>
<body>

<h2><spring:message code="label.user"/></h2>
<form:form method="post" action="addUser.html" commandName="user">
 
    <table>
    <tr>
    	<td><form:hidden path="id"/>
    </tr>
    <tr>
        <td><form:label path="login"><spring:message code="label.login"/></form:label></td>
        <td><form:input path="login" /></td>
        <td><form:errors path="login"/></td>
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
        <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
        <td><form:input path="email" /></td>
        <td><form:errors path="email"/></td>
    </tr>
    <tr>
        <td><form:label path="telephone"><spring:message code="label.telephone"/></form:label></td>
        <td><form:input path="telephone" /></td>
        <td><form:errors path="telephone"/></td>
    </tr>
    <tr>
        <td><form:label path="enabled"><spring:message code="label.enabled"/></form:label></td>
        <td><form:checkbox path="enabled" /></td>
        <td><form:errors path="enabled"/></td>
    </tr>
    <tr>
        <td colspan="2">
        <c:if test="${user.id==0}">
            <input type="submit" value="<spring:message code="label.addUser"/>"/>
        </c:if>    
        <c:if test="${user.id!=0}">
        	<input type="submit" value="<spring:message code="label.editUser"/>"/>
       	</c:if>
        </td>
    </tr>
</table> 
     
</form:form>
<h3><spring:message code="label.userList"/></h3>
<c:if  test="${!empty userList}">
<table class="data">
<tr>
    <th><spring:message code="label.firstname"/></th>
    <th><spring:message code="label.lastname"/></th>
    <th><spring:message code="label.email"/></th>
    <th><spring:message code="label.telephone"/></th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
</tr>
<c:forEach items="${userList}" var="user">
    <tr>
        <td>${user.firstname} </td>
        <td>${user.lastname} </td>
        <td>${user.email}</td>
        <td>${user.telephone}</td>
        <td><a href="delete/${user.id}.html">delete</a></td>
        <td><a href="users.html?userId=${user.id}">edit</a></td>
    </tr>
</c:forEach>
</table>
</c:if>
</body>
</html>