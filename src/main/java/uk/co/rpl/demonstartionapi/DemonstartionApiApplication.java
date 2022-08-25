package uk.co.rpl.demonstartionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.DispatcherType;

import static javax.servlet.DispatcherType.REQUEST;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.util.EnumSet;


@SpringBootApplication
public class DemonstartionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemonstartionApiApplication.class, args);
    }

}
