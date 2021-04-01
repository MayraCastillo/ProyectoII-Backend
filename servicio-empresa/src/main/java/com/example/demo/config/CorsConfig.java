package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Por razones de seguridad, los navegadores prohíben las llamadas AJAX a recursos que residen fuera 
 * del origen actual, esta clase sirve para que las peticiones que llegan desde frameworks como 
 * react que utilizan AJAX no sean rechazadas
 * 
 * @author Ruben
 *
 */
@Configuration
public class CorsConfig {	

	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {			
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:3000");			
			}
		};
	}
	
}