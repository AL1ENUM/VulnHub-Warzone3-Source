package alien;

import java.io.Serializable;

public class Request implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String option;

	public Request(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "Request [option=" + option + "]";
	}
	
	
	
	
	
	

}
