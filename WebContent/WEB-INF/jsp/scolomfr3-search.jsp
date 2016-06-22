<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<spring:url value="/scolomfr3/trees" var="trees"></spring:url>
<t:layout>
	<jsp:body>
	

	

	<div class="container">
		<div class="row">
			<h2>Recherche de ressources par label</h2>
			<p class="lead">Les ressources peuvent être recherchées par <span
						class="badge">prefLabel</span> et par <span class="badge">altlabel</span> afin d'afficher les propriétés dont elles sont sujet ou objet.</p>
		</div>
		<div class="row">
		<div class="well">		
			<form>
			    <fieldset>
			        <div class="form-group">
			            <label for="query">Search:</label>
			            <input class="form-control" name="query" id="query"
									placeholder="prefLabel ou altLabel" type="text">              
			        </div>
			        <button type="submit" class="btn btn-primary pull-right">Rechercher</button>
			    </fieldset>
			</form>
				</div>
</div>
 
		
		
	</div>
      
    
	
	</jsp:body>
</t:layout>

