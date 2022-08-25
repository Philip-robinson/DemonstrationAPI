package uk.co.rpl.demonstartionapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@Order(1)
@Slf4j
public class SecConfig extends WebSecurityConfigurerAdapter {
    private final String key;

    public SecConfig(@Value("${security-api-key}") String key) {
        this.key = key;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        var filter = new AbstractPreAuthenticatedProcessingFilter(){

            @Override
            protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
                return request.getHeader("api-key");
            }

            @Override
            protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
                return null;
            }
        };

        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                var reqKey = (String) authentication.getPrincipal();
                if (!key.equals(reqKey)) {
                    throw new BadCredentialsException("The API key was invalid or not provided.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });
        httpSecurity.
                antMatcher("/api/**").
                csrf().
                disable().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                addFilter(filter).
                authorizeRequests().
                anyRequest().
                authenticated();
    }
}
