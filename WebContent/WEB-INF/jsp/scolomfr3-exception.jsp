<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
	<div class="row">
	<h2>Un problème est survenu</h2>
			<p class="lead">${message}</p>
			</div>
	
		</div>
	
	
	
	
	</jsp:body>
</t:layout>

