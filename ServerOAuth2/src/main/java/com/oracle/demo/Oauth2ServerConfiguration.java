package com.oracle.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
public class Oauth2ServerConfiguration extends WebSecurityConfigurerAdapter implements AuthorizationServerConfigurer {

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	

	@Autowired
	AuthenticationManager authMgr;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()");
		// security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.inMemory().withClient("web").secret(passwordEncoder.encode("webpass")).scopes("READ", "WRITE")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.accessTokenValiditySeconds(12000000).refreshTokenValiditySeconds(12000000);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer end) throws Exception {

		end.authenticationManager(authMgr).tokenStore(tokenStore()).accessTokenConverter(tokenEnhancer());
		;
	}

	// JWT Enhancement
	private String clientid = "web";
	private String clientSecret = "webpass";
	private String privateKey = "ayush";
	private String publicKey = "ayush";

	

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		return converter;
		
		
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	 @Bean
	   public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
	   }
	 
	 
	 public static void main(String[] args) throws IOException {
		 FileOutputStream fout1=new FileOutputStream("D:\\f1.txt");    
		 ByteArrayOutputStream bout=new ByteArrayOutputStream();    
	      bout.write(65);    
	      bout.writeTo(fout1);    
	      
	       
	}
}
