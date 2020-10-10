package beans;

import java.io.Serializable;
import java.util.Date;

public class Announcement implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private Long id;
	private Course course;
	private String title;
	private String description;
	private Date date;

	//Constructors//
	public Announcement() {}

	//Getters-Setters//
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Object overrides
	@Override
	public boolean equals(Object other) {
		return (other instanceof Announcement) && (id != null)
			? id.equals(((Announcement) other).getId())
			: (other == this);
	}

	@Override
	public String toString() {
		return String.format("Announcement[id=%d,title=%s,course=%s,date=%s]",
				id, title, course != null ? course.getCode(): "", date);
	}
}
