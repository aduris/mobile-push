package mobile.push.sender.vo;

/**
 * 에러정보 VO
 * @since	2015-07-03
 * @author 	최일규
 */
public class ErrorVO  {

	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private String deviceToken;
	private String errorMessage;

}