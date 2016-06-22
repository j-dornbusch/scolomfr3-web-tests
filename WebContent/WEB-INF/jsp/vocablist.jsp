<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:url value="/scolomfr3/search" var="search"></spring:url>
<c:if test="${fn:length(node.getChildren()) gt 0}">
	<ul <c:if test="${root==true}">class="vocab-tree"</c:if>>
		<c:forEach var="childNode" items="${node.getChildren()}">
			<li>${childNode.getData().getRight()}<span
				class="glyphicon glyphicon-link" aria-hidden="true"
				title='<a href="${search}?uri=${childNode.getData().getLeft()}">${childNode.getData().getLeft()}</a>'
				data-toggle="popover"></span> <c:set var="node" value="${childNode}"
					scope="request" /> <c:set var="root" value="false" scope="request" />
				<jsp:include page="vocablist.jsp" />
			</li>
		</c:forEach>
	</ul>
</c:if>
