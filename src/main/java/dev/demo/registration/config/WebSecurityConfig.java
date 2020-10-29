package dev.demo.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors()
		.and()
        .authorizeRequests()
        .antMatchers(PATH_LIST).permitAll().anyRequest().authenticated()
        .and()
        .csrf().disable()
        .headers().frameOptions().disable();
		
    }
	
	
	private static final String[] PATH_LIST = {
            "/api/demo/**",
            "/h2-console/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };
	
	

}
