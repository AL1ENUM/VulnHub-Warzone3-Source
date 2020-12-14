package alien;

import java.io.Serializable;

public class Tokenyzer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private String role;
	
	public Tokenyzer(String token, String role) {
		super();
		this.token = token;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Tokenyzer [token=" + token + ", role=" + role + "]";
	}
	
	
	
	
	
	
	
	

}
