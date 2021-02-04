package pl.kurs.java.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    @Profile("postgres")
    public DataSource postgresDataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        log.info("Initializing PostgreSQL database: {}", databaseUrl);

        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        }
        catch (URISyntaxException e) {
            log.error(String.format("Invalid DATABASE_URL: %s", databaseUrl), e);
            return null;
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath();

        // fully-qualified class name to distuinguish from javax.sql.DataSource
        org.apache.tomcat.jdbc.pool.DataSource dataSource
                = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}