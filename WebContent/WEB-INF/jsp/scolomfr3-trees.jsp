<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
		<div class="row">
			<h2>Affichage des hiérarchies de termes</h2>
			<p class="lead">Les entrées du menu sont les racines du graphe des propriétés <span
						class="badge">narrower</span> qui n'ont pas de parent dans le graphe des propriétés <span
						class="badge">broader</span>.</p>
		</div>
		<div class="row">
		<form action="${trees}" method="get">
			<label for="uri">Choix d'une racine</label>
			<select name="uri" id="root-uri-selector" class="form-control">
			<c:forEach items="${treeRoots}" var="resource" varStatus="status">
		<option value="${resource.getKey()}"
								<c:if	test="${resource.getKey() eq uri}">selected="selected"</c:if>>
		${resource.getValue()}
		</option>
	</c:forEach>
				
			</select>
				</form>
		</div>
	</div>
       <div class="row">
			<div class="container">
       <c:set var="node" value="${tree.getRoot()}" scope="request" />
       <c:set var="root" value="true" scope="request" />
       <jsp:include page="vocablist.jsp" />
       </div> </div>
    </jsp:body>
</t:layout>

