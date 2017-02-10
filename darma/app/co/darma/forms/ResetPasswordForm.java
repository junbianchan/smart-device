package co.darma.forms;

import play.data.validation.Constraints.Required;

public class ResetPasswordForm {

	@Required
	public String password;
	
	@Required
	public String confirmPassword;

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
