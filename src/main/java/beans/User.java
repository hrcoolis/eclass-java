package beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import org.apache.catalina.realm.MessageDigestCredentialHandler;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final MessageDigestCredentialHandler credentialHandler;

	static {
		credentialHandler = new MessageDigestCredentialHandler();
		try {
			credentialHandler.setAlgorithm("SHA-256");
		} catch (NoSuchAlgorithmException e) {
		}
		credentialHandler.setIterations(4);
		credentialHandler.setSaltLength(8);
	}

	//Variables//
	private Long id;
	private String username;
	private String password;
	private Role role = Role.NONE;

	//Constructors//
	public User() {}

	public User(Long id, String username, String password, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	//Getters-Setters//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordDigest(String password) {
		this.password = credentialHandler.mutate(password);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// Object overrides
	@Override
	public boolean equals(Object other) {
		return (other instanceof User) && (id != null)
			? id.equals(((User) other).getId())
			: (other == this);
	}

	@Override
	public String toString() {
		return String.format("User[id=%d,username=%s,password=%s,role=%s]",
				id, username, password, role.name());
	}
}
