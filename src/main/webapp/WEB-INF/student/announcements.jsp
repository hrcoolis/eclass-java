<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:usertemplate title="Student Index">
<jsp:attribute name="bottomjs">
<script>
$("input[name='regCourse[]']").change(function() {
	var action = this.checked ? "register" : "unregister";
	$.ajax({type: "GET",
		dataType: "json",
		url: ctx + "/student/" + action + "/course/" + this.value,
		success: function(data) {//TODO
		},
		error: function(data) {//TODO
		},
		beforeSend: function() {
		}
	});
});

$("button[name='unregCourse[]']").click(function() {
	var $this = this;
	$.ajax({type: "GET",
		dataType: "json",
		url: ctx + "/student/unregister/course/" + this.value,
		success: function(data) {//TODO
			$this.closest('tr').remove();
		},
		error: function(data) {//TODO
		},
		beforeSend: function() {
		}
	});
});

</script>
</jsp:attribute>
<jsp:body>
	<div class="row mb-2 justify-content-center">
		<div class="col-md-8">
			<div class="card flex-md-row mb-4 shadow-sm h-md-250">
				<div class="card-body d-flex flex-column align-items-start">
					<h2 >${ user.getFirstName()} ${user.getLastName()} </h2>
					<p class="card-text"><strong>Username: </strong> ${user.getUsername()} </p>
					<p class="card-text"><strong>AM: </strong> ${user.getAm()} </p>
					<p class="card-text"><strong>Email: </strong> ${user.getEmail()} </p>
				</div>
				<img class="card-img-right flex-auto d-none d-lg-block" style="width: 250px; height: 250px;" src="<c:url value="/images/person_student.png"/>" data-holder-rendered="true">
			</div>
		</div>
	</div>
	
	<ul class="nav nav-tabs" id="myTab" role="tablist">
		<li class="nav-item">
			<a class="nav-link" id="my-courses-tab" role="tab"   href="<c:url value="/student/"/>">Courses</a>
		</li>
		<li class="nav-item">
			<a class="nav-link active" id="announcements-tab" role="tab"  href="<c:url value="/student/announcements"/>">Announcements</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" id="assignments-tab" role="tab"  href="<c:url value="/student/assignments"/>" >Assignments</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" id="all-courses-tab" role="tab"  href="<c:url value="/student/allcourses"/>" >All Courses</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" id="teams-tab" role="tab"  href="<c:url value="/student/teams"/>" >Teams</a>
		</li>
	</ul>
	
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="announcements" role="tabpanel" aria-labelledby="announcements-tab" >
			<h3 class="mt-4">Announcements</h3>
			<table id="announcements" class="table  table-hover table-striped table-bordered">
				<thead>
					<tr class="d-flex">
						<th class="col-2">Date</th>
						<th class="col-10" >Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${announcements}" var="a">
					<tr class="d-flex">
						<td class="col-2"><fmt:formatDate value="${a.getDate()}" pattern="dd-MM-yyyy HH:mm" /></td>
						<td class="col-10">${a.getTitle()}<br>${a.getDescription()}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</jsp:body>
</t:usertemplate>