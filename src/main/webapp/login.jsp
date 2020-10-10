<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<title>Login - Eclass</title>
		<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/css/loginstyles.css"/>">
	</head>
	<body class="text-center">
		<form action="j_security_check" method="post" class="form-signin">
			<h1 class="h3 mb-3 font-weight-normal">Please log in</h1>
			<label for="j_username" class="sr-only">Username</label>
			<input type="text" name="j_username" id="j_username" class="form-control" placeholder="Username" required autofocus>
			<label for="j_password" class="sr-only">Password</label>
			<input type="password" name="j_password" id="j_password" class="form-control" placeholder="Password" required>
			<div class="checkbox mb-3">
				<label>
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<p class="mt-5 mb-3 text-muted">&copy; 2020-2021 hr polak</p>
		</form>
	</body>
</html>
