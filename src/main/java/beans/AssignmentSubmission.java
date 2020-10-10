package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Part;

public class AssignmentSubmission implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private Long id;
	private Assignment assignment;
	private Student student;
	private String comment;
	private Date submissionDate;
	private float grade;
	private String gradeComment;
	private Date gradeSubmissionDate;
	private InputStream file = null;
	private String filename;

	//Constructors//
	public AssignmentSubmission() {}

	//Getters-Setters//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getGradeComment() {
		return gradeComment;
	}

	public void setGradeComment(String gradeComment) {
		this.gradeComment = gradeComment;
	}

	public Date getGradeSubmissionDate() {
		return gradeSubmissionDate;
	}

	public void setGradeSubmissionDate(Date gradeSubmissionDate) {
		this.gradeSubmissionDate = gradeSubmissionDate;
	}

	public InputStream getFile() {
		return this.file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFile(Part filePart) {
		try {
			this.file = filePart.getInputStream();
			this.filename = filePart.getSubmittedFileName();
		} catch (IOException e) {
			this.file = null;
			this.filename = null;
		}
	}

	@Override
	public String toString() {
		return String.format(
				"AssignmentSubmission[id=%s, submissionDate=%s, "
				+ "grade=%s, gradeComment=%s, gradeSubmissionDate=%s, filename=%s, assignment=%s, student=%s]",
				id, submissionDate, grade, gradeComment, gradeSubmissionDate, filename, assignment, student);
	}
}
