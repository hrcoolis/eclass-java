<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
			<a class="nav-link" id="announcements-tab" role="tab"  href="<c:url value="/student/announcements"/>">Announcements</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" id="assignments-tab" role="tab"  href="<c:url value="/student/assignments"/>" >Assignments</a>
		</li>
		<li class="nav-item">
			<a class="nav-link active" id="all-courses-tab" role="tab"  href="<c:url value="/student/allcourses"/>" >All Courses</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" id="teams-tab" role="tab"  href="<c:url value="/student/teams"/>" >Teams</a>
		</li>
	</ul>
	
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="all-courses" role="tabpanel" >
			<h3 class="mt-4">Επιλογή Μαθημάτων</h3>
			<table id="dtmathimata_all" class="table table-striped table-bordered" cellspacing="0" style="width:100%">
				<thead>
					<tr> <th>
						</th>
						<th class="th-sm">Μάθημα
						</th>
						<th class="th-sm">Καθηγητης</th>
	
					</tr>
				</thead>
				<tbody>
	
					<c:forEach items="${AllcoursesList}" var="c">
					<tr>
						<td><input type="checkbox" value="${c.getId()}" name="regCourse[]" <c:if test='${fn:contains(registeredList, c.getId())}'>checked</c:if>/></td>
						<td>
							<a href="<c:url value="/course/${c.getId()}/"/>">${c.getTitle()}</a> <strong class="font-weight-bold text-monospace">(${c.getCode()})</strong>
						</td>
						<td>${c.getProfessor().getFirstName()} ${c.getProfessor().getLastName()}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</jsp:body>
</t:usertemplate>