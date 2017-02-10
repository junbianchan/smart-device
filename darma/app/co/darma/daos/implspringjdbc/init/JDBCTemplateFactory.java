package co.darma.daos.implspringjdbc.init;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import play.Play;

public class JDBCTemplateFactory {
	
	private static JdbcTemplate jdbcTemplate = initJdbcTemplate();
	
	private static JdbcTemplate initJdbcTemplate() {
		return new JdbcTemplate(initDatasource());
	}
	
	private static DriverManagerDataSource initDatasource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Play.application().configuration().getString("database.driver"));
        dataSource.setUrl(Play.application().configuration().getString("database.url"));
        dataSource.setUsername(Play.application().configuration().getString("database.user"));
        dataSource.setPassword(Play.application().configuration().getString("database.password"));
        return dataSource;
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
