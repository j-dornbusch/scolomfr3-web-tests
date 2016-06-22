<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@tag description="Page layout" pageEncoding="UTF-8"%>
<spring:url value="/resources" var="baseResourcesPath" />
<spring:url value="/scolomfr3/index" var="index"></spring:url>
<spring:url
	value="${baseResourcesPath}/js/vendor/modernizr-2.8.3.min.js"
	var="modernizerJs" />
<spring:url value="${baseResourcesPath}/js/vendor/jquery-1.11.2.min.js"
	var="jqueryJs" />
<spring:url value="${baseResourcesPath}/js/vendor/bootstrap.min.js"
	var="bootstrapJs" />
<spring:url value="${baseResourcesPath}/img/wait.gif" var="preloaderSrc" />
<spring:url value="${baseResourcesPath}/js/plugin.js" var="pluginJs" />
<spring:url value="${baseResourcesPath}/js/main.js" var="mainJs" />
<spring:url value="${baseResourcesPath}/css/main.css" var="mainCss" />
<spring:url value="${baseResourcesPath}/css/bootstrap.min.css"
	var="bootstrapCss" />
<spring:url value="${baseResourcesPath}/css/bootstrap-theme.min.css"
	var="bootstrapThemeCss" />

<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title></title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="apple-touch-icon" href="apple-touch-icon.png">
<link rel="stylesheet" href="${bootstrapCss}">
<link rel="stylesheet" href="${bootstrapThemeCss}">
<style>
body {
	padding-top: 50px;
	padding-bottom: 20px;
}
</style>
<link rel="stylesheet" href="${bootstrapThemeCss}">
<link rel="stylesheet" href="${mainCss}">

<script src="${modernizerJs}"></script>
</head>
<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${index}">Recette ScoLomfr3 - plateforme de test</a>
			</div>

		</div>
	</nav>
	<jsp:doBody />

	<!-- /container -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

	<script>
		window.jQuery || document.write('<script src="${jqueryJs}"><\/script>')
	</script>
	<img alt="veillez patienter" src="${preloaderSrc}" id="preloader">
	<script src="${bootstrapJs}"></script>
	<script src="${pluginJs}"></script>
	<script src="${mainJs}"></script>
</body>
</html>
<!doctype html>




