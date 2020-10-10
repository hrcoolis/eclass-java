<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:usertemplate title="Professor Index">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:attribute name="bottomjs">
</jsp:attribute>
<jsp:body>

  <div class="row justify-content-center">
		<div class="col col-lg-8 ">
			<div class="mb-5 text-center border rounded course-instructor">
				<h3 class="mb-5 text-black text-uppercase h6 border-bottom pb-3">Καθηγητης</h3>
				<div class="mb-4 text-center">
					<img src="<c:url value="/images/person_2.jpg"/>" alt="Image" class="w-25 rounded-circle mb-4">  
					<h3 class="h5 text-black mb-4">${ user.getFirstName()} ${user.getLastName()}</h3>
					<p>${user.getEmail()}</p>
					<p># of courses : ${courses.size()}</p>
				</div>
			</div>
		</div>
		</div>

		<table id="courses" class="table table-hover table-striped">
			<thead>
				<tr>
					<th></th>
					<th class="th-sm">Code</th>
					<th class="th-sm">Title</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${courses}" var="c">
				<tr>
					<td><input type="checkbox" /></td>
					<td>${c.getCode()}</td>
					<td><a href="<c:url value="/course/${c.getId()}/"/>">${c.getTitle()}</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

		<form action="<c:url value="/professor/create/course"/>" method="post" class="form-box">
			<h3>Create New Course</h3>
			<div class="form-group row">
				<label for="nc_title" class="col-sm-2 col-form-label">Title</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="nc_title" name="nc_title" placeholder="Title" required>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPassword3" class="col-sm-2 col-form-label">Code</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="nc_code" name="nc_code" placeholder="Code" required>
				</div>
			</div>
			<div class="form-group row">
				<label for="nc_description" class="col-sm-2 col-form-label">Description</label>
				<div class="col-sm-10">
					<textarea class="form-control" id="nc_description" name="nc_description" rows="3" placeholder="Description"  required></textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-10">
					<button type="submit" class="btn btn-primary">Create Course</button>
				</div>
			</div>
		</form>
</jsp:body>
</t:usertemplate>