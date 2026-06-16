package com.example.demo.upload;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init(); //sirve para que se cree la carpeta sy no existe
   
	Stream<Path> loadAll();//metodo para cargar todos los ficheros

	
	String store(MultipartFile file); //guarda el archivo
	
	Path load(String filename); //carga el archivo
	
	Resource loadAsResource(String filename); //devuelve el fichero como recurso
	
	void delete(String filename); //borra el archivo
	
}
