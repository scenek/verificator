package cz.coccinelles.gc.verificator.model;

import org.springframework.validation.BindingResult;

public class ValidatorMessage {
	private String message;
	private String coords;
	private BindingResult result;
	private String lastPassword;
	private int stageNo;

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public int getStageNo() {
		return stageNo;
	}

	public void setStageNo(int stageNo) {
		this.stageNo = stageNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public BindingResult getResult() {
		return result;
	}

	public void setResult(BindingResult result) {
		this.result = result;
	}
	
	public void setRejectValue(String arg0, String arg1, String arg2) {
		this.result.rejectValue(arg0, arg1, arg2);
	}

	public ValidatorMessage(BindingResult result) {
		super();
		this.result = result;
	}
	
	public ValidatorMessage() {
		super();
	}
}
