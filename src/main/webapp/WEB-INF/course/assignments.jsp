<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<t:usertemplate title="Course Assignments - ${course.getCode()}">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:attribute name="headernavbar">
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}"/>">Πληροφοριες</a>
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}/announcements"/>">Βαθμολoγίες</a>
      <a class="nav-item nav-link active" href="<c:url value="/course/${course.getId()}/assignments"/>">Εργασιες</a>
</jsp:attribute>
<jsp:body>
<t:course_cover>
	<div class="row"><div class="col">
	<h3>Assignments</h3>
	<table id="assignments" class="table table-hover table-sm table-striped">
		<thead>
			<tr class="d-flex">
				<th class="col-10" >Title</th>
				<th class="col-2">Deadline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${assignments}" var="a">
				<tr class="d-flex">
					<td class="col-10"><a href="<c:url value="/assignment/${a.getId()}"/>">${a.getTitle()}</a></td>
					<td class="col-2"><fmt:formatDate value="${a.getDeadline()}" pattern="dd-MM-yyyy" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div></div>
	<c:if test="${sessionScope.user.role == 'professor'}">
	<div class="row"><div class="col">
	<form action="<c:url value="/professor/create/assignment"/>" method="post" class="form-box" enctype="multipart/form-data">
		<h3>New Assignment</h3>
		<div class="form-group">
			<label for="a_title">Title</label>
			<input type="text" class="form-control" name="a_title" id="a_title" placeholder="Εργασια ΠΣ" required>
		</div>
		<div class="form-group">
			<label for="a_description">Description</label>
			<textarea class="form-control" name="a_description" id="a_description" rows="4" required></textarea>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2">
				<label for="exampleFormControlFile1">Max Grade</label>
				<input  class="form-control" type="number" name="a_max_grade" id="a_max_grade" min="1" max="10" value="4" required>
			</div>
			<div class="form-group col-md-4">
				<label for="a_deadline">Deadline</label>
				<input class="form-control" type="date" name="a_deadline" id="a_deadline" min="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy"/>" value="<fmt:formatDate value="${now}" pattern="dd-MM-yyyy"/>" pattern="dd-MM-yyyy" required>
			</div>
			<div class="form-group col-md-6">
				<label for="a_file">File</label>
				<input type="file" class="form-control-file" name="a_file" id="a_file" required>
			</div>
		</div>
		<input type="hidden" id="a_cid" name="a_cid" value="${course.getId()}">
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>
	</div></div>
	</c:if>
</t:course_cover>
</jsp:body>
</t:usertemplate>