package com.grabit.config;

import java.util.UUID;

import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.grabit.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, DigestAuthenticationFilter digestAuthenticationFilter) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.headers(Customizer.withDefaults())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/home")
						.permitAll())
				// .httpBasic(Customizer.withDefaults())
				// .requiresChannel(channel -> channel.anyRequest().requiresSecure())
				// .exceptionHandling(e -> e.authenticationEntryPoint(digestAuthenticationEntryPoint()))
				// .addFilter(digestAuthenticationFilter)
				.build();
	}


	@Bean
	@DependsOn("liquibase")
    @Profile("prod")
	UserDetailsService productionUserDetailsService(UserService userService) {
		return userDetailsService(userService);
	}


	@Bean
	@DependsOnDatabaseInitialization
	@DependsOn("entityManagerFactory")
    @Profile("test")
	@Primary
	UserDetailsService testingUserDetailsService(UserService userService) {
		return userDetailsService(userService);
	}


	UserDetailsService userDetailsService(UserService manager) {
		var admin = User
				.withUsername("admin")
				.password("{noop}admin")
				.roles( "GUEST", "CUSTOMER", "SUPPLIER", "SUPPORT_AGENT", "ANALYST", "ADMIN")
				.build();

		if (!manager.userExists("admin"))
			manager.createUser(admin);
		return manager;
	}

	@Bean
	DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
		DigestAuthenticationEntryPoint entrypoint = new DigestAuthenticationEntryPoint();
		entrypoint.setRealmName("GrabIt Realm");
		entrypoint.setKey(UUID.randomUUID().toString());
		return entrypoint;
	}

	@Bean
	DigestAuthenticationFilter digestAuthenticationFilter(UserDetailsService userDetailsService) {
		DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
		filter.setUserDetailsService(userDetailsService);
		filter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
		return filter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
