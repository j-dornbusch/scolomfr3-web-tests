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
			<h2>Recherche de ressources &amp; navigation</h2>
			<p class="lead">Deux options sont proposées : les ressources peuvent être recherchées soit en texte libre (sparql + expression régulière), soit par URI, afin d'afficher leurs propriétés.</p>
		</div>
		<div class="row">
		<div class="well">		
			<form>
			    <fieldset>
			        <div class="row">
			        <div class="col-md-4">
			        <input id="search-type-toggle"
										<c:if test="${searchBy=='query'}">checked</c:if>
										type="checkbox" />
								</div>
					<div class="col-md-6">
			            <input
										class="form-control <c:if test="${searchBy!='query'}">hidden-form-control</c:if>"
										<c:if test="${searchBy=='query'}">name="query"</c:if>
										id="query" placeholder="prefLabel, altLabel, scopeNote"
										value="${query}" type="text" />
			            <input
										class="form-control <c:if test="${searchBy!='uri'}">hidden-form-control</c:if>"
										<c:if test="${searchBy=='uri'}">name="uri"</c:if> id="uri"
										value="${uri}" placeholder="http://..." type="text" />
									</div>              
			         <div class="col-md-2">
								<button type="submit" class="btn btn-primary pull-right">Rechercher</button>
							</div>
</div>			        
			    </fieldset>
			</form>
				</div>
</div>
<p class="lead">Résultat(s) : ${results.size()}</p>
		<div>
		
		<c:forEach items="${results}" var="result">
		 <ul class="list-group">
		 <c:set var="prefLabelToDisplay"
							value="${fn:replace(result.value.prefLabel, 
                                '||', ' / ')}" />
		 <li class="list-group-item list-group-item-info"><span
							class="lead">${prefLabelToDisplay}&nbsp;</span>(<a
							href="${search}?uri=${result.key}"><span
								class="glyphicon glyphicon-link" aria-hidden="true"></span>&nbsp;${result.key}</a>)</li>
		<c:forEach items="${result.value}" var="entry">
		
  			
							<li class="list-group-item">
							<c:set var="entryKeyToDisplay"
							value="${fn:replace(entry.key, 
                                '|', '')}" />
							<span class="label label-success pull-left">${entryKeyToDisplay}</span>&nbsp;
								<c:choose>
											  <c:when
										test="${fn:startsWith(entry.key, '|')}">
										<span class="glyphicon glyphicon-arrow-left"
											aria-hidden="true"></span>
											  </c:when>
											  <c:otherwise>
										<span class="glyphicon glyphicon-arrow-right"
											aria-hidden="true"></span>
											  </c:otherwise>
											</c:choose>
								<c:set var="splittedEntries"
									value="${fn:split(entry.value, '||')}" />
									
									<c:forEach items="${splittedEntries}" var="splittedEntry">
									<c:set var="trimmedSplittedEntry"
										value="${fn:trim(splittedEntry)}" />
									<c:choose>
											  <c:when
											test="${fn:startsWith(trimmedSplittedEntry, 'http')}">
											     &nbsp;<a href="${search}?uri=${trimmedSplittedEntry}"><span
												class="glyphicon glyphicon-link" aria-hidden="true"></span>&nbsp;${trimmedSplittedEntry}</a>
											  </c:when>
											  <c:otherwise>
											     &nbsp;<a href="${search}?query=${splittedEntry}"><span
												class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;${splittedEntry}</a>
											  </c:otherwise>
											</c:choose>
  

								
						</c:forEach>
							</li>
					</c:forEach>
					</ul>
		</c:forEach>
		</div>
	</div>
      
    
	
	</jsp:body>
</t:layout>

