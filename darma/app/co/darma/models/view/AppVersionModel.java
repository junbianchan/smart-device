package co.darma.models.view;

import java.util.Date;

public class AppVersionModel {

	public int version;
	public Date updatedAt = new Date();

	public AppVersionModel(int version, Date updatedAt) {
		this.version = version;
		this.updatedAt = updatedAt;
	}

	public AppVersionModel() {
	}
}
