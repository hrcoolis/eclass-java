package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Part;


public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	//variables//
	private Long id;
	private Long student_id;
	private String name;
	private String lastname;
	private String am;
	private String email;

	public Team() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getStudent_id() {
		return student_id;
	}
	

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public String toString() {
		return String.format(
				"Assignment[id=%s]",
				id);
	}


}
