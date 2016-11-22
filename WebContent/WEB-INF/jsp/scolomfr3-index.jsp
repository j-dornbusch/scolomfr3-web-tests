<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<spring:url value="/scolomfr3/graphs" var="graphs"></spring:url>
<spring:url value="/scolomfr3/search" var="search"></spring:url>
<spring:url value="/scolomfr3/labels" var="labels"></spring:url>
<t:layout>
	<jsp:body>
	

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<p>Plateforme de test des vocabulaires skos et rdf scolomfr3.</p>
			<p>Exploitation des vocabulaires sous <a
						href="https://jena.apache.org/">Apache Jena</a>.</p>
			<p>Le code source de cette plateforme (Maven, Spring, Boostrap) est disponible sur  <a
						href="https://github.com/j-dornbusch/scolomfr3-web-tests">Github</a>.</p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-3">
				<h2>Affichage</h2>
				<p>Affichage de vocabulaires, parcours et visualisation des hiérarchies.</p>
				<p>
					<a class="btn btn-default" href="${trees}" role="button">Accéder
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-3">
				<h2>Recherche </h2>
				<p>Recherche de ressources (plein texte ou par URI) et affichage de leurs propriétés .</p>
				<p>
					<a class="btn btn-default" href="${search}" role="button">Accéder
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-3">
				<h2>Graphes </h2>
				<p>Vérifications des hiérarchies de notions : relations broader/narrower.</p>
				<p>
					<a class="btn btn-default" href="${graphs}" role="button">Accéder
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-3">
				<h2>Libellés </h2>
				<p>Contrôle des libellés : orthographe, casse, exhaustivité.</p>
				<p>
					<a class="btn btn-default" href="${labels}" role="button">Accéder
						&raquo;</a>
				</p>
			</div>
			
		</div>
	</div>
        
    </jsp:body>
</t:layout>

