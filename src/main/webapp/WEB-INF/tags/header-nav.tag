<%@tag description="Header Navbar" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
	<a class="navbar-brand" href="#"><i class="fa fa-graduation-cap fa-lg" aria-hidden="true"></i> CS Unipi Eclass</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarsExample04">
		<ul class="navbar-nav ml-auto">
			<jsp:doBody />
		</ul>
		<div class="dropdown ml-auto" id="plz">
			<input class="form-control form-control-dark dropdown-toggle"  data-toggle="dropdown" type="text" placeholder="&#xF002; Search" id="search-box" style="font-family:FontAwesome, Arial">
	
			<div class="dropdown-menu"  aria-labelledby="search-box" id="search-results">
			</div>
		</div>
		<ul class="navbar-nav">
			<li class="nav-item dropdown">
				<a class="nav-link" href="<c:url value="/Login"/>" id="dropdown04">${user.getUsername()}</a>
			</li>
			<li>
				<a class="nav-link dropdown-toggle" href="#" id="dropdown04" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
				<div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdown04">
					<a class="dropdown-item" href="<c:url value="/Login"/>">Home</a>
					<a class="dropdown-item" href="#">Settings</a>
					<a class="dropdown-item" href="<c:url value="/Logout"/>">Logout</a>
				</div>
			</li>
		</ul>
	</div>
</nav>
