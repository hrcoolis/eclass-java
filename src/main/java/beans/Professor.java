package beans;

import java.io.Serializable;

public class Professor extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private String firstName;
	private String lastName;
	private String email;

	//Constructors//
	public Professor () {
		this.setRole(Role.professor);
	}

	public Professor(Long id, String username, String password, String firstName, String lastName, String email) {
		super(id, username, password, Role.professor);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	// Object overrides
	@Override
	public String toString() {
		return String.format("Professor[%s,firstName=%s,lastName=%s,email=%s]",
				super.toString(), firstName, lastName, email);
	}
}
