<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
		<div class="row">
			<h2>Contrôle des graphes hiérarchiques</h2>
			<p class="lead">L'algorithme contrôle que chaque arc <span
						class="badge">narrower</span> du graphe est inversé par un arc <span
						class="badge">broader</span> et vice-versa.</p>
		</div>
		<div class="row">
		<ul class="nav nav-pills" role="tablist" id="graph-control-tabs">
  			<li role="presentation" class="active"><a
						href="#missing-narrower">Relations narrower manquantes (${missingNarrower.size()})</a></li>
  			<li role="presentation"><a href="#missing-broader">Relations broader manquantes (${missingBroader.size()})</a></li>
  			<li role="presentation"><a href="#complete-narrower">Toutes les relations narrower (${revertedNarrower.size()})</a></li>
  			<li role="presentation"><a href="#complete-broader">Toutes les relations broader (${revertedBroader.size()})</a></li>
		</ul>
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="missing-narrower">
						<c:if test="${missingNarrower.size()==0}">
						<p class="alert alert-success">Aucune relation narrower n'est manquante</p>
					</c:if>
		<ul>
		<c:forEach items="${missingNarrower}" var="missing">
		<li>${missing.getLeft()} -> ${missing.getRight()} </li>
		</c:forEach>
		</ul>
					</div>
    <div role="tabpanel" class="tab-pane" id="missing-broader">
						<c:if test="${missingBroader.size()==0}">
						<p class="alert alert-success">Aucune relation broader n'est manquante</p>
					</c:if>
		<ul>
		
		<c:forEach items="${missingBroader}" var="missing">
		<li>${missing.getLeft()} -> ${missing.getRight()} </li>
		</c:forEach>
		</ul>
					</div>
    <div role="tabpanel" class="tab-pane" id="complete-narrower">
						<ul>
		<c:forEach items="${revertedNarrower}" var="reverted">
		<li><span class="glyphicon glyphicon-check" aria-hidden="true"></span> ${reverted.getLeft().substring(0 , reverted.getLeft().indexOf("§"))} <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"> ${reverted.getRight().substring(0 , reverted.getRight().indexOf("§"))}</span> </li>
		</c:forEach>
		</ul>
					</div>
    <div role="tabpanel" class="tab-pane" id="complete-broader">
						<ul>
		
		<c:forEach items="${revertedBroader}" var="reverted">
		<li><span class="glyphicon glyphicon-check" aria-hidden="true"></span> ${reverted.getLeft().substring(0 , reverted.getLeft().indexOf("§"))} <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"> ${reverted.getRight().substring(0 , reverted.getRight().indexOf("§"))} </span></li>
		</c:forEach>
		</ul>
					</div>
  </div>
			</div>
		

		
		
	
		
		
		
		
	</div>
      
    </jsp:body>
</t:layout>

