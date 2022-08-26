package uk.co.rpl.demonstartionapi;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.co.rpl.demonstartionapi.configuration.AppConfig;


@SpringBootApplication
public class DemonstartionApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt =
            SpringApplication.run(DemonstartionApiApplication.class, args);

        AppConfig config=ctxt.getBean( AppConfig.class);
        Logger log = getLogger(DemonstartionApiApplication.class);
        log.info("=========== Application Started");
        log.info("Version: {}", config.getVersion());
        log.info("Built on branch: {}", config.getBranch());
        log.info("Built at: {}", config.getBuildTime());
        log.info("=========== Application Started");
    }

}
