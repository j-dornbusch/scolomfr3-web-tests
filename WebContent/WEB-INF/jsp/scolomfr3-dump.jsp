<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
		<div class="row">
			Skos complete parsing
		</div>
<table class="table table-striped">
	<tr>
		<th>Sujet</th>
		<th>Predicat</th>
		<th>Objet</th>
	</tr>
	<c:forEach items="${vocabularyContent}" var="vocabularyEntry"
					varStatus="status">
		<tr>
			<td>${vocabularyEntry.getFirst()}</td>
			<td>${vocabularyEntry.getSecond()}</td>
			<td>${vocabularyEntry.getThird()}</td>
		</tr>
	</c:forEach>
</table>	
	</div>
        
    </jsp:body>
</t:layout>

