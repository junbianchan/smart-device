package co.darma.daos.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import co.darma.exceptions.DatabaseException;
import play.db.DB;

/**
 * Abstract layer of DB operations.
 * 
 * This abstract dao class provides a set of db operation wrappers which reduces some responsibilities of each sub class dao
 * (e.g. closing connection and statement when exception happens)
 * 
 * NOTE: Similar functionality has been supported by Spring, for sake of simplicity/dependency, we create this simplified version
 */
public class AbstractDao {
	
	protected void runQuery(String sql, ParametersUpdater paramsUpdater, ResultMapper resMapper, List<Object> result) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DB.getConnection();
			stmt = conn.prepareStatement(sql);
			paramsUpdater.updateParameters(stmt);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				result.add(resMapper.mapResult(rs));
			}
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new DatabaseException("FATAL ERROR WHILE CLOSING DB CONNECTION OR STATEMENT", e);
			}
		}
	}
	
	protected int runInsertOrUpdate(String sql, ParametersUpdater paramsUpdater) throws DatabaseException {
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				conn = DB.getConnection();
				stmt = conn.prepareStatement(sql);
				paramsUpdater.updateParameters(stmt);
				return stmt.executeUpdate();
			} catch (Exception e) {
				throw new DatabaseException(e);
			} finally {
				try {
					if(stmt != null) {
						stmt.close();
					}
					if(conn != null) {
						conn.close();
					}
				} catch (Exception e) {
					throw new DatabaseException("FATAL ERROR WHILE CLOSING DB CONNECTION OR STATEMENT", e);
				}
			}
	}
	
	protected Long runInsertAndGetGeneratedPrimaryKey(String sql, ParametersUpdater paramsUpdater) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DB.getConnection();
			stmt = conn.prepareStatement(sql);
			paramsUpdater.updateParameters(stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				return rs.getLong(1);
			}
			return null;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new DatabaseException("FATAL ERROR WHILE CLOSING DB CONNECTION OR STATEMENT", e);
			}
		}
}
}
