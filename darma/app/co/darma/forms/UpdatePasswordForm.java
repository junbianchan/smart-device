package co.darma.forms;

import play.data.validation.Constraints.Required;

public class UpdatePasswordForm {
	@Required
	public String newPassword;
	@Required
	public String oldPassword;
	
	public String getOldPassword() {
		return this.oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
