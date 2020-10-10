package beans;

import java.io.Serializable;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private Long id;
	private String code;
	private String title;
	private String description;
	private Professor professor;

	//Constructors//
	public Course() {}

	//Getters-Setters//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	// Object overrides
	@Override
	public boolean equals(Object other) {
		return (other instanceof Course) && (id != null)
			? id.equals(((Course) other).getId())
			: (other == this);
	}

	@Override
	public String toString() {
		return String.format("Course[id=%d,code=%s,title=%s,professor=%s]",
				id, code, title, professor != null ? professor.getUsername(): "null");
	}
}
