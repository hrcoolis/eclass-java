package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Part;


public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;

	//variables//
	private Long id;
	private Course course;
	private String title;
	private String description;
	private boolean active;
	private float maxGrade;
	private Date creationDate;
	private Date deadline;
	private InputStream file = null;
	private String filename;

	public Assignment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(float maxGrade) {
		this.maxGrade = maxGrade;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public InputStream getFile() {
		return file;
	}
	
	public void setFile(InputStream file) {
		this.file = file;
	}


	public String getFilename() {
		return filename;
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
				"Assignment[id=%s, title=%s, description=%s, maxGrade=%s, creationDate=%s, deadline=%s, filename=%s, course=%s]",
				id, title, description, maxGrade, creationDate, deadline, filename, course);
	}


}
