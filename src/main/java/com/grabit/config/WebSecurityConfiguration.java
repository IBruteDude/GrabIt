package com.grabit.config;

import java.util.UUID;

import javax.sql.DataSource;

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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

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
	UserDetailsService productionUserDetailsService(DataSource datasource) {
		return userDetailsService(datasource);
	}


	@Bean
	@DependsOnDatabaseInitialization
	@DependsOn("entityManagerFactory")
    @Profile("test")
	@Primary
	UserDetailsService testingUserDetailsService(DataSource datasource) {
		return userDetailsService(datasource);
	}


	UserDetailsService userDetailsService(DataSource datasource) {
		var admin = User
				.withUsername("admin")
				.password("{noop}admin")
				.roles("USER", "ADMIN")
				.build();

		var manager = new JdbcUserDetailsManager(datasource);

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
}
