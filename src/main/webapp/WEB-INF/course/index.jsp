<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Arrays" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<t:usertemplate title="Course Index - ${course.getCode()}">
<jsp:attribute name="head">
</jsp:attribute>
<jsp:attribute name="headernavbar">
      <a class="nav-item nav-link active" href="<c:url value="/course/${course.getId()}"/>">Πληροφοριες</a>
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}/announcements"/>">Βαθμολογίες</a>
      <a class="nav-item nav-link" href="<c:url value="/course/${course.getId()}/assignments"/>">Εργασιες</a>
</jsp:attribute>
<jsp:body>
<t:course_cover>
<div class="row">
<div class="col-lg-8 mb-5">
	<div class="mb-5">
		<h3 class="text-black">Περιγραφή</h3>
		<p>${course.getDescription()}</p>
	</div>
</div>
<div class="col-lg-4 pl-lg-5">
	<div class="mb-5 text-center border rounded course-instructor">
		<h3 class="mb-5 text-black text-uppercase h6 border-bottom pb-3">Καθηγητης</h3>
		<div class="mb-4 text-center">
			<img src="<c:url value="/images/person_2.jpg"/>" alt="Image" class="w-25 rounded-circle mb-4">  
			<h3 class="h5 text-black mb-4">${ course.getProfessor().getFirstName()} ${course.getProfessor().getLastName()}</h3>
			<p>${course.getProfessor().getEmail()}</p>
		</div>
	</div>
</div>
</div>
</t:course_cover>
</jsp:body>
</t:usertemplate>