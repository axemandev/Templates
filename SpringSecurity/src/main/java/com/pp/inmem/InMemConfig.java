package com.pp.inmem;

import java.security.Principal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class InMemConfig {
	
	@Bean
	UserDetailsManager userDetailsService() {
		return new InMemoryUserDetailsManager();
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	InitializingBean initializer (UserDetailsManager manager) {
		return () -> {
			UserDetails pen = User
				.withDefaultPasswordEncoder()
				.username("pen")
				.password("pen")
				.roles("USER")
				.build();
			manager.createUser(pen);
			
			UserDetails penny = User
					.withDefaultPasswordEncoder()
					.username("penny")
					.password("penny")
					.roles("USER")
					.build();
			manager.createUser(penny);
		};
	}

}


@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		
		// authenticate requests using basic authentication
		http
			.httpBasic();
		
		// authorize all incoming requests
		http
			.authorizeRequests()
			.anyRequest()
			.authenticated();
	}
}

@RestController
class HelloController {
	
	@GetMapping("/ola")
	public String hello(Principal principal) {
		return "Hello Mr. " + principal.getName();
	}
}