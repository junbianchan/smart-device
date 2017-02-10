package co.darma.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import play.Play;

@Configuration
public class DataConfig {

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Play.application().configuration().getString("database.driver"));
//        dataSource.setUrl(Play.application().configuration().getString("database.url"));
//        dataSource.setUsername(Play.application().configuration().getString("database.user"));
//        dataSource.setPassword(Play.application().configuration().getString("database.password"));
        return dataSource;
    }

}