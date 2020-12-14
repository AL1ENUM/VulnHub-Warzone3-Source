package alien;

import java.io.Serializable;

public class Token implements Serializable{
	
	private String value;
	private String role;
	
	public Token(String value, String role) {
		
		this.value = value;
		this.role = role;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Token [value=" + value + ", role=" + role + "]";
	}
	
	
	
	
	
	

}
