package beans;

import java.io.Serializable;

public class Student extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private String firstName;
	private String lastName;
	private String email;
	private String am;

	//Constructors//
	public Student () {
		this.setRole(Role.student);
	}

	public Student(Long id, String username, String password, String firstName, String lastName, String email, String am) {
		super(id, username, password, Role.student);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.am = am;
	}

	//Getters-Setters//
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	// Object overrides
	@Override
	public String toString() {
		return String.format("Student[%s,firstName=%s,lastName=%s,email=%s,am=%s]",
				super.toString(), firstName, lastName, email, am);
	}
}
