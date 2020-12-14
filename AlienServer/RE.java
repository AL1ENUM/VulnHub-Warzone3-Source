package alien;

import java.io.Serializable;

public class RE implements Serializable {
	
	private Token token;
	private String option;
	private String cmd;
	private String value;
	
	public RE() {
		
	}
	
	public RE(Token token, String option, String cmd, String value) {
		
		this.token = token;
		this.option = option;
		this.cmd = cmd;
		this.value = value;
	}


	public Token getToken() {
		return token;
	}


	public void setToken(Token token) {
		this.token = token;
	}


	public String getOption() {
		return option;
	}


	public void setOption(String option) {
		this.option = option;
	}


	public String getCmd() {
		return cmd;
	}


	public void setCmd(String cmd) {
		this.cmd = cmd;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "RE [ " + token + ", option=" + option + ", cmd=" + cmd + ", value=" + value + "]";
	}
	
	
	
	

}
