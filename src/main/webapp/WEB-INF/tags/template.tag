<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<%@ attribute name="bottomjs" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
	<title>${title} - Eclass</title>
	<meta charset="utf-8">
	<jsp:invoke fragment="head"/>
</head>
<body>
	<header>
		<jsp:invoke fragment="header"/>
	</header>
	<main>
		<jsp:doBody/>
	</main>
	<footer>
		<jsp:invoke fragment="footer"/>
	</footer>
	<jsp:invoke fragment="bottomjs"/>
</body>
</html>
