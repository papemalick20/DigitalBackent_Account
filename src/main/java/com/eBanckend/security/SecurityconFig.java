package com.eBanckend.security;



import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.query.EqlParser.New_valueContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

//import java.util.List;

//import javax.crypto.SecretKey;
//import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

//import com.nimbusds.jose.jwk.source.ImmutableSecret;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityconFig  {
     @Bean
     protected  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
     
     @Bean
     protected InMemoryUserDetailsManager inMemoryUserDetailsManager() {
      	PasswordEncoder passwordEncoder=passwordEncoder();
  	   return new InMemoryUserDetailsManager(
      	 User.withUsername("user1").password(passwordEncoder.encode("12345")).authorities("USER").build(),
      	 User.withUsername("admin").password(passwordEncoder.encode("root")).authorities("ADMIN","USER").build()
      	
      	);
      	
  		
  		
  	}
  	
	@Bean
     protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	return httpSecurity
    			.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.csrf(csrf->csrf.disable())
    			.cors(Customizer.withDefaults())
    			.authorizeHttpRequests(ar->ar.requestMatchers("/auth/login/**").permitAll())
    			.authorizeHttpRequests(ar->ar.anyRequest().authenticated())
    		    .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
    			//.httpBasic(Customizer.withDefaults())
    			.build();
     }
	@Bean
	 JwtEncoder jwtEncoder() {
		String secretKey ="608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
	}
     
	@Bean
	 JwtDecoder jwtDecoder() {
		String secretKey ="608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
		SecretKeySpec secretKeySpec=new SecretKeySpec(secretKey.getBytes(), "RSA");
		return  NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}
    @Bean
	protected AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(daoAuthenticationProvider);
		
	}
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration corsConfiguration= new CorsConfiguration();
    	corsConfiguration.addAllowedOrigin("*");
    	corsConfiguration.addAllowedMethod("*");
    	corsConfiguration.addAllowedHeader("*");
    	//corsConfiguration.setExposedHeaders(List.of("Authorization"));
    	UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
    	source .registerCorsConfiguration("/**", corsConfiguration);
    	return  source;
    }
   

}
