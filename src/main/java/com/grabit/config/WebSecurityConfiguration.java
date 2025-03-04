package com.grabit.config;

import org.springframework.beans.factory.annotation.Value;
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
import com.grabit.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Value("${DEFAULT_SECURITY_USERNAME}")
	private String DEFAULT_SECURITY_USERNAME;

	@Value("${DEFAULT_SECURITY_PASSWORD}")
	private String DEFAULT_SECURITY_PASSWORD;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login/**").permitAll()
						.requestMatchers("/oauth2/**").permitAll()
						.anyRequest().authenticated())
				// .formLogin(form -> form
				// 		.loginPage("/login")
				// 		.defaultSuccessUrl("/home")
				// 		.permitAll())
				// .httpBasic(Customizer.withDefaults())
				.oauth2Login(Customizer.withDefaults())
				.oauth2Client(Customizer.withDefaults())
				// .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
				// .csrf(csrf -> csrf.disable())
				// .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// .requiresChannel(channel -> channel.anyRequest().requiresSecure())
				// .headers(Customizer.withDefaults())
				// .exceptionHandling((exceptions) -> exceptions
				// 		.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
				// 		.accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
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
		var user = User.withUsername(DEFAULT_SECURITY_USERNAME)
				// .password(DEFAULT_SECURITY_USERNAME)
				// .passwordEncoder(passwordEncoder()::encode)
				.password(passwordEncoder().encode(DEFAULT_SECURITY_PASSWORD))
				.roles("GUEST", "CUSTOMER", "SUPPLIER", "SUPPORT_AGENT", "ANALYST", "ADMIN")
				.build();

		if (!manager.userExists(DEFAULT_SECURITY_USERNAME))
			manager.createUser(user);
		return manager;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
