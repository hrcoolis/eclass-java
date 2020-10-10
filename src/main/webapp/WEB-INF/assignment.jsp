<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<t:usertemplate title="Assignmet">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:body>
<div class="container" id="Assignment">
	<div class="site-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mb-5">
					<div class="mb-5">
						<h1 class="display-4">${a.getTitle()}</h1>
						<br>
						<br>
						<p class="lead">Προστέθηκε στις: <fmt:formatDate value="${a.getCreationDate()}" pattern="dd-MM-yyyy" /> &nbsp; &nbsp; &nbsp; &nbsp;	 • Παράδοση έως: <fmt:formatDate value="${a.getDeadline()}" pattern="dd-MM-yyyy" /></p>
						<p>Η εργασία βαθμολογείται με άριστα το : ${a.getMaxGrade()}  </p>
						<a class="btn btn-lg btn-primary mb-4" href="<c:url value="/download/assignment/${a.getId()}"/>" role="button">${a.getFilename()} <i class="fa fa-download fa-2x"></i></a>
						<h2 class="text-black pb-4">Περιγραφή</h2>
						<p>${a.getDescription()}</p>
					</div>
				</div>
				<div class="col-lg-4 pl-lg-5">
					<div class="mb-5 text-center border rounded course-instructor">
						<div class="mb-4 text-center">
							<h4 class="h4 text-black mb-4">${a.getCourse().getCode()}</h4>
							<h5 class="h5 text-black mb-4">${a.getCourse().getTitle()}</h5>
							<hr>
							<img src="<c:url value="/images/person_2.jpg"/>" alt="Image" class="w-25 rounded-circle mb-4">  
							<h3 class="h5 text-black mb-4">${a.getCourse().getProfessor().getFirstName()} ${a.getCourse().getProfessor().getLastName()}</h3>
							<p>${a.getCourse().getProfessor().getEmail()}</p>
						</div>
					</div>
				</div>
			</div>

			<c:if test="${sessionScope.user.role == 'professor'}">
			<table id="assignments" class="table table-hover table-striped">
				<thead>
					<tr class="d-flex">
						<th class="col-9" >Student</th>
						<th class="col-2">Submission Date</th>
						<th class="col-1"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${asubs}" var="as">
					<tr class="d-flex">
						<td class="col-9">${as.getStudent().getFirstName()} ${as.getStudent().getLastName()}</td>
						<td class="col-2"><fmt:formatDate value="${as.getSubmissionDate()}" pattern="dd-MM-yyyy" /></td>
						<td class="col-1">
							<a class="btn btn-sm btn-primary" href="<c:url value="/download/assignmentsub/${as.getId()}"/>" role="button"><i class="fa fa-download"></i></a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</c:if>


			<c:if test="${sessionScope.user.role == 'student'}">
			<div class="row"><div class="col">
					<form action="<c:url value="/student/submit/assignment"/>" method="post" class="form-box" enctype="multipart/form-data">
						<h3>Assignment Submission</h3>
						<div class="form-group">
							<label for="a_description">Comment</label>
							<textarea class="form-control" name="as_comment" id="as_comment" rows="4" required></textarea>
						</div>
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="a_file">File</label>
								<input type="file" class="form-control-file" name="as_file" id="as_file" required>
							</div>
						</div>
						<input type="hidden" id="as_aid" name="as_aid" value="${a.getId()}">
						<button type="submit" class="btn btn-primary">Submit <i class="fa fa-upload" aria-hidden="true"></i> </button>
					</form>
			</div></div>
			</c:if>


		</div>
	</div>
</div>

</jsp:body>
</t:usertemplate>