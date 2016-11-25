<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@tag description="Page layout" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="navbar-nav navbar-right">
	<form method="get" class="">
		<div class="dropdown nav pull-right">
			<select class="form-control target-selector" name="format">
				<c:forEach items="${formats}" var="format">
					<option value="${format}">${format}</option>
				</c:forEach>
			</select>
		</div>
		<div class="dropdown pull-right">

			<select class="form-control target-selector" name="version">
				<c:forEach items="${versions}" var="version">
					<option value="${version}"
						<c:if test="${version==currentVersion}">selected="selected"</c:if>>${version.toString()}</option>
				</c:forEach>
			</select>
		</div>
	</form>

</div>

