package co.darma.daos.common;

import java.sql.PreparedStatement;

public interface ParametersUpdater {
	
	public void updateParameters(PreparedStatement stmt);

}
