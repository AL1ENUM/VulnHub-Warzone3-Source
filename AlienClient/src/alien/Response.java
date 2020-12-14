package alien;

import java.io.Serializable;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private Tokenyzer Tokenyzer;
	
	public Response(String message, alien.Tokenyzer tokenyzer) {
		
		this.message = message;
		Tokenyzer = tokenyzer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Tokenyzer getTokenyzer() {
		return Tokenyzer;
	}

	public void setTokenyzer(Tokenyzer tokenyzer) {
		Tokenyzer = tokenyzer;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", " + Tokenyzer + "]";
	}
	
	
	
	
	

	

}
