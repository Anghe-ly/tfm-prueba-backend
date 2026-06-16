package com.example.demo.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Cors implements WebMvcConfigurer{
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
		    @Override
		    public void addCorsMappings(CorsRegistry registry) {
		        registry.addMapping("/**") // Permite todas las rutas
		                .allowedOrigins("http://localhost:4200") // Permite solicitudes desde este origen
		                .allowedMethods("GET", "POST", "PUT", "DELETE")// Métodos permitidos
		                .allowCredentials(true); //admite credenciales, esto para los login 
		        
		    }
		
		};
	}
	
}
	