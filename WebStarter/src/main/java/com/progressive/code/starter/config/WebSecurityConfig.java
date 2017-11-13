package com.progressive.code.starter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests().antMatchers("/admin/**")
				.authenticated()
				.anyRequest()
				.permitAll()
			.and()
				.formLogin().loginPage("/login").permitAll()
			.and()
				.logout()
			       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			       .permitAll();
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("secret").roles("USER");
    }
}