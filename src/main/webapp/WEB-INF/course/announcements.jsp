<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<t:usertemplate title="Course Announcements - ${course.getCode()}">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:attribute name="headernavbar">
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}"/>">Πληροφοριες</a>
      <a class="nav-item nav-link active" href="<c:url value="/course/${course.getId()}/announcements"/>">Βαθμολογίες</a>
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}/assignments"/>">Εργασιες</a>
</jsp:attribute>
<jsp:body>
<t:course_cover>
	<div class="row"><div class="col">
	<h3>Announcements</h3>
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
	</div></div>
	<c:if test="${sessionScope.user.role == 'professor'}">
	<div class="row"><div class="col">
	<form action="<c:url value="/professor/create/announcement"/>" method="post" class="form-box">
		<h3>Ανακοίνωση Βαθμολογίας</h3>
		
		<div class="form-group row">
			<select name="department">
    			<c:forEach var="assignment" items="${assignments}">
        			<option value="${assignment.getTitle()}" ${assignment.getTitle() == selectedDept ? 'selected="selected"' : ''}>${assignment.getTitle()}</option>
    			</c:forEach>
			</select>
		</div>
		<div class="form-group row">
			<label for="na_description" class="col-sm-2 col-form-label">Description</label>
			<div class="col-sm-10">
				<textarea id="na_description" name="na_description" rows="3" placeholder="Description"  required></textarea>
			</div>
		</div>
		<input type="hidden" id="na_cid" name="na_cid" value="${course.getId()}">
		<div class="form-group row">
			<div class="col-sm-10">
				<button type="submit" class="btn btn-primary">Create Announcement</button>
			</div>
		</div>
	</form>
	</div></div>
	</c:if>
</t:course_cover>
</jsp:body>
</t:usertemplate>