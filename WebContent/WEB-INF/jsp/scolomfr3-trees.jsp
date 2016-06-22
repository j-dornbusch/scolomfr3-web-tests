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
			<p class="lead">Deux options de parcours sont proposées.</p>
			</div>
	
		<div class="row">
		<div class="list-group">
  			<a href="${trees}?use_member=true"
						class="list-group-item <c:if	test="${useMember}">active</c:if>">
    		<h4 class="list-group-item-heading">Entrée par les arcs member</h4>
    		<p class="list-group-item-text">Les entrées du menu sont les prédécesseurs du graphe des propriétés <span
								class="badge">member</span>.</p>
  			</a>
  			<a href="${trees}?use_member=false"
						class="list-group-item <c:if	test="${!useMember}">active</c:if>">
    		<h4 class="list-group-item-heading">Entrée par les racines du graphe des narrower</h4>
    		<p class="list-group-item-text">Les entrées du menu sont les prédécesseurs du graphe des propriétés <span
								class="badge">narrower</span> qui ne sont jamais prédécesseurs dans le graphe des propriétés <span
								class="badge">broader</span>.</p>
  			</a>
				</div>
		<div class="row">
		
		<div class="panel panel-success">
  <div class="panel-heading">
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
			<input type="hidden" value="${useMember}" name="use_member" />
				</form>
  
  </div>
  <div class="panel-body">
   

       <c:set var="node" value="${tree.getRoot()}" scope="request" />
       <c:set var="root" value="true" scope="request" />
       <jsp:include page="vocablist.jsp" />

   
   
   
  </div>
</div>
			
		</div>
	</div>
       
    </div>
	
	
	
	
	
	
	
	
	
	</jsp:body>
</t:layout>

