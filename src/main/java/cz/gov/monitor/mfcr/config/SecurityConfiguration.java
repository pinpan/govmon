package cz.gov.monitor.mfcr.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${management.context-path}")
    private String managementContextPath;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // some paths I don't want to secure at all
                .antMatchers("//**", "/path2/**").permitAll()
                // access to health endpoint is open to anyone
                .antMatchers(HttpMethod.GET, managementContextPath + "/health").permitAll();
                // but app.admin scope is necessary for other management endpoints
                ///////.antMatchers(managementContextPath + "/**").access("#oauth2.hasScope('my-super-scope')") //
                // And we make sure the user is authenticated for all the other cases
                //.anyRequest().authenticated();
    }
}