package com.example.demo.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Clase que define la ruta para guardar las 
 * imagenes, en nuestro caso una carpeta "upload"
 * */
@Component
@ConfigurationProperties(prefix= "storage")
public class StorageProperties {

	
	private String location = "upload";
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		
		this.location = location;
	}
	
}
