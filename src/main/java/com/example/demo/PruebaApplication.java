package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.upload.StorageService;

@SpringBootApplication
public class PruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
	    return (args) -> {
	        try {
	            storageService.init();
	        } catch (Exception e) {
	            System.err.println("Error al crear la carpeta: " + e.getMessage());
	        }
	    };
	}
	

}
