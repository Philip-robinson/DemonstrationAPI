package uk.co.rpl.demonstartionapi.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author philip
 */
@Getter
@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:git.properties")
public class AppConfig {
    private final String branch;
    private final String version;
    private final String buildTime;
    private final String key;
    public AppConfig(
            @Value("${git.branch}") String branch,
            @Value("${git.build.version}") String version,
            @Value("${git.build.time}") String buildTime,
            @Value("${security-api-key}") String key
    ) {

        this.branch = branch;
        this.version = version;
        this.buildTime = buildTime;
        this.key = key;
    }
}
