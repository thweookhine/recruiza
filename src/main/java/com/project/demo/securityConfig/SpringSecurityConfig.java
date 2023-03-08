package com.project.demo.securityConfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private DataSource dataSource;

	@Bean
	// Method
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**")
		.antMatchers("/static/**")
		.antMatchers("/css/**")
		.antMatchers("/js/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
				.passwordEncoder(passwordEncoder())
				.dataSource(dataSource)
				.usersByUsernameQuery("select email, password, enabled from user where email = ?")
				.authoritiesByUsernameQuery("select email, role from user where email = ?");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/market/**").hasAnyAuthority("MARKETER", "ADMIN")
				.antMatchers("/login", "/resources/**").permitAll()
				.antMatchers("/**").hasAnyAuthority("ADMIN", "USER", "MARKETER")
				.antMatchers("/home/**")
				.authenticated();

		http.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("email")
				.passwordParameter("password")
				.failureHandler(authenticationFailureHandler);

		http.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login");

		http.exceptionHandling()
				.accessDeniedPage("/unauthorized");
	}
}
