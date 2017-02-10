package co.darma.models.view;

public class ErrorResponseModel {
	
	public int errorCode;
	
	public String errorMsg;
	
	public ErrorResponseModel(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
