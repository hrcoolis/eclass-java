package beans;

import java.io.Serializable;
import java.util.Date;

public class UserReg extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	//Variables//
	private String firstName;
	private String lastName;
	private String email;
	private String am;
	private String comment;
	

	//Constructors//
	public UserReg() {}
	
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


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	// Object overrides
	@Override
	public String toString() {
		return String.format("URequser[%s,firstName=%s,lastName=%s,email=%s,am=%s, comment=%s]",
				super.toString(), firstName, lastName, email, am, comment);
	}
}
