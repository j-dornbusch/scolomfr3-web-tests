<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<spring:url value="/scolomfr3/search" var="search"></spring:url>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
		<div class="row">
			<h2>Contrôle des labels</h2>
			<p class="lead">Divers contrôles (exhaustivité, orthographe, casse).</p>
		</div>
		<div class="row">
		<ul class="nav nav-pills" role="tablist" id="graph-control-tabs">
  			<li role="presentation"><a href="#lang-control">Contrôle de l'orthographe  (${languageConcerns.size()} ressources)</a></li>
  			<li role="presentation"><a href="#case-concerns">Variation de la casse (${caseConcerns.size()} listes de labels)</a></li>
  			<li role="presentation"><a href="#missing-labels">Labels manquants (${missingLabels.size()} ressources)</a></li>
		</ul>
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="lang-control">
    <p>Contrôle de l'orthographe avec <a
								href="https://hunspell.github.io">Hunspell</a>
						</p>
						<ul class="list-group">
							<c:forEach items="${languageConcerns}" var="languageConcern">
						<li class="list-group-item"><p>
										<a href="${search}?uri=${languageConcern.key}">${languageConcern.key}</a>
									</p>
						<p>
										<c:forEach items="${languageConcern.value}" var="label">
						<span class="label label-danger">${label}</span>
						</c:forEach>
									</p></li>
						</c:forEach>
						</ul>
										</div>
    
					
	<div role="tabpanel" class="tab-pane" id="case-concerns">
		<p>L'algorithme détecte, pour chaque liste de ressources situées à une même profondeur dans l'arbre des relations narrower, si des libellés ont une première lettre en en majuscule sans justification. Seules les listes problématiques sont présentées ci dessous par uri du parent. Les ${nbListsLowercase} listes dont tous les libellés commencent par une minuscule ne sont pas représentées.</p>
		<c:forEach items="${caseConcerns}" var="caseConcern">
		<details>
								<summary>
									${caseConcern.key}
		</summary>
								<ul class="list-group">
		<c:forEach items="${caseConcern.value}" var="label">
		<c:set var="labelWithoutFlag" value="${fn:substring(label, 1, -1)}" />
			<li
											class="list-group-item <c:if test="${fn:startsWith(label, '-')}">alert alert-danger</c:if>">${labelWithoutFlag}</li>
		</c:forEach>
		</ul>
		</details>
		</c:forEach>
					</div>
					
					<div role="tabpanel" class="tab-pane" id="missing-labels">
    <p>Contrôle des prefLabels Manquants
						</p>
						<ul class="list-group">
							<c:forEach items="${missingLabels}" var="missingLabel">
						<li class="list-group-item"><p>
										<a href="${search}?uri=${missingLabel.key}">${missingLabel.key}</a>
									</p>
						<p>
										<c:forEach items="${missingLabel.value}" var="label">
						<span class="label label-danger">${label}</span>
						</c:forEach>
									</p></li>
						</c:forEach>
						</ul>
										</div>
  </div>
			</div>
		

		
		
	
		
		
		
		
	</div>
      
    </jsp:body>
</t:layout>

