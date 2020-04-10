<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<body>
<div class="header">
	<h1>CrewPlanningApp</h1>
	
	<c:url value="/logout" var="logoutUrl" />
 
	<!-- csrt for log out-->
	<form action="${logoutUrl}" method="post" id="logoutForm">
	  <input type="hidden" 
		name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	</form>
 
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
 

	<span style="float: right">
    	<c:if test="${pageContext.request.userPrincipal.name != null}">
			Welcome : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
		</c:if>
	</span>
	<br>
	<span style="float: right">
		<a href="?lang=pl">pl</a> | <a href="?lang=en">en</a> | <a href="?lang=de">de</a>
	</span>
</div>
</body>
</html>