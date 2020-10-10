<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="headernavbar" fragment="true" %>
<%@ attribute name="aside" fragment="true" %>
<%@ attribute name="bottomjs" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
	<title>${title} - Eclass</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
	<script>var ctx = "${pageContext.request.contextPath}"</script>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.bundle.min.js"/>"></script>
	<jsp:invoke fragment="head" />
</head>
<body>
	<header>
		<t:header-nav>
			<jsp:invoke fragment="headernavbar"/>
		</t:header-nav>
		<jsp:invoke fragment="header"/>
	</header>
	<div class="container">
		<div class="container position-relative flex-wrapper" id="content">
			<div class="row h-100 mt-5">
				<jsp:invoke fragment="aside"/>
				<main class="col py-5">
				<t:alert />
				<div class="row position-relative">
					<div class="col">
						<jsp:doBody/>
					</div>
				</div>
				</main>
			</div>
			<t:footer/>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		var timeoutID = null;
		var sb = $('#search-box');
		
		sb.keyup(function(e) {
			clearTimeout(timeoutID);
			timeoutID = setTimeout(searchServlet.bind(undefined, e.target.value), 500);
		});

		function searchServlet(sterm) {
			$.ajax({
				url: ctx + "/search/course",
				type: "POST",
				data: { 
					searchTerm: sterm
				},
				beforeSend: function() {
					$('#search-results').empty();
					if ( sb.attr('aria-expanded') === "false") {
						sb.dropdown('toggle');
					}
				},
				success: function(response) {
					$(response.courses).each(function()  {
						$('#search-results').append($("<a />", {
							text: this.title,
							href: ctx + "/course/" + this.id,
							class: "dropdown-item"
						}));
					});
				}
			});
		}
	
	});
	</script>
	<jsp:invoke fragment="bottomjs"/>
</body>
</html>
