package com.pp.jdbc;

import java.security.Principal;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableWebSecurity
@Order(value=101)
public class JdbcSecuredConfig extends WebSecurityConfigurerAdapter{

	@Bean
	UserDetailsManager userDetailsManagerJdbc(DataSource dataSource) {
		JdbcUserDetailsManager detailsManager = new JdbcUserDetailsManager();
		detailsManager.setDataSource(dataSource);
		return detailsManager;
	}
	
	
	  @Bean 
	  InitializingBean initializer(@Qualifier("userDetailsManagerJdbc") UserDetailsManager manager) { 
			return () -> {
				UserDetails pen = User
					.withDefaultPasswordEncoder()
					.username("nen")
					.password("nen")
					.roles("USER")
					.build();
				manager.createUser(pen);
				
				UserDetails penny = User
						.withDefaultPasswordEncoder()
						.username("nenny")
						.password("nenny")
						.roles("USER")
						.build();
				manager.createUser(penny);
			};
	  }
	 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		
		http
			.httpBasic();
		http
			.authorizeRequests()
			.anyRequest()
			.authenticated();
	}
}

@RestController
class OlaDb {
	
	@GetMapping("/ola-db")
	public String hello(Principal principal) {
		return "Hello Mr. " + principal.getName() + " from db!";
	}
	
}