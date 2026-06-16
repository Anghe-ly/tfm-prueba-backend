package com.example.demo.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity
public class SeguridadConfig {


	private UserDetailsService userDetailsService;
	private  JwtAuthenticationFilter jwtFilter;
	//private JwtServicio jwtServicio;
	
	//constructor
	public SeguridadConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	
	//bean para el passwordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	
	//bean del SecutiryFilterChain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
		.cors(Customizer.withDefaults()) // <-- CORS  para que admita llamadas desde mi front
        .csrf(csrf -> csrf.disable()) // <-- Esto permite POST sin CSRF token
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.requestMatchers("/public/**", "/login/**", "/files/**").permitAll()
				.anyRequest().authenticated()
				)
		
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);	
		
		return http.build();
	}
	
	// Bean de Configuración de CORS 
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:4200"); // Origen del frontend
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


	  @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        return http.getSharedObject(AuthenticationManagerBuilder.class)
	                   .userDetailsService(userDetailsService)
	                   .passwordEncoder(passwordEncoder())
	                   .and()
	                   .build();
	    }
}
