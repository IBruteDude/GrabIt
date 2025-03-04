package com.grabit.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

	@Value("${SERVER_ADDRESS}")
	private String SERVER_ADDRESS;

	@Value("${SERVER_PORT}")
	private String SERVER_PORT;

	@Value("${LOCAL_CLIENT_ID}")
	private String LOCAL_CLIENT_ID;

	@Value("${LOCAL_CLIENT_SECRET}")
	private String LOCAL_CLIENT_SECRET;

	@Value("${REDIRECT_URI}")
	private String REDIRECT_URI;

	@Value("${ISSUER_URI}")
	private String ISSUER_URI;

	@Value("${POST_LOGOUT_REDIRECT_URI}")
	private String POST_LOGOUT_REDIRECT_URI;


	@Bean
	@Order(0)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer
				.authorizationServer().oidc(Customizer.withDefaults());
		http
				.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
				.with(authorizationServerConfigurer, Customizer.withDefaults())
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
				.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
		return http.build();
	}

	@Bean
	@DependsOn("liquibase")
	@Profile("prod")
	RegisteredClientRepository productionRegisteredClientRepository(JdbcOperations ops) {
		return registeredClientRepository(ops);
	}

	@Bean
	@DependsOnDatabaseInitialization
	@DependsOn("entityManagerFactory")
	@Profile("test")
	@Primary
	RegisteredClientRepository testingRegisteredClientRepository(JdbcOperations ops) {
		return registeredClientRepository(ops);
	}

	RegisteredClientRepository registeredClientRepository(JdbcOperations ops) {
		RegisteredClient localClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId(LOCAL_CLIENT_ID)
				.clientSecret("{noop}" + LOCAL_CLIENT_SECRET)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri(REDIRECT_URI)
				.postLogoutRedirectUri(POST_LOGOUT_REDIRECT_URI)
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope(OidcScopes.EMAIL)
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

		JdbcRegisteredClientRepository registeredClients = new JdbcRegisteredClientRepository(ops);

		if (registeredClients.findByClientId(LOCAL_CLIENT_ID) == null)
			registeredClients.save(localClient);
		return registeredClients;
	}

	@Bean
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder()
				.issuer(ISSUER_URI)
				.authorizationEndpoint("/oauth2/authorize")
				.tokenEndpoint("/oauth2/token")
				.build();
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

}
