package com.grabit.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/home")
						.permitAll())
				// .httpBasic(Customizer.withDefaults())
				// .requiresChannel(channel -> channel.anyRequest().requiresSecure())
				// .exceptionHandling(e ->
				// e.authenticationEntryPoint(authenticationEntryPoint()))
				// .addFilter(digestAuthenticationFilter())
				.build();
	}

	@Bean
	UserDetailsService userDetailsService(
			@Value("${spring.datasource.url}") String mysqlUrl,
			@Value("${spring.datasource.username}") String mysqlUsername,
			@Value("${spring.datasource.password}") String mysqlPassword) {
		var admin = User
				.withUsername("admin")
				.password("{noop}admin")
				.roles("USER", "ADMIN")
				.build();

		var datasource = new MysqlDataSource();
		datasource.setURL(mysqlUrl);
		datasource.setUser(mysqlUsername);
		datasource.setPassword(mysqlPassword);

		// var datasource = new EmbeddedDatabaseBuilder()
		// .setType(EmbeddedDatabaseType.H2)
		// .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
		// .build();
		var manager = new JdbcUserDetailsManager(datasource);

		if (!manager.userExists("admin"))
			manager.createUser(admin);
		return manager;
		// return new InMemoryUserDetailsManager(admin);
	}

	@Bean
	DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
		DigestAuthenticationEntryPoint result = new DigestAuthenticationEntryPoint();
		result.setRealmName("GrabIt Realm");
		result.setKey(UUID.randomUUID().toString());
		return result;
	}

	@Bean
	DigestAuthenticationFilter digestAuthenticationFilter(UserDetailsService userDetailsService) {
		DigestAuthenticationFilter result = new DigestAuthenticationFilter();
		result.setUserDetailsService(userDetailsService);
		result.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
		return result;
	}
}
