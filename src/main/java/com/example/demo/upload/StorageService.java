package com.example.demo.upload;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


/*Interfaz con la que se gestionan 
 * los archivos de la aplicacion
 * */
public interface StorageService {

	/**
	 * sirve para que se cree la carpeta si no existe
	 */
	void init(); 
	
	/**
	 * Se usa para cargar todos los ficheros
	 * @return las rutas de los archivos*/
	Stream<Path> loadAll();
	
	/**
	 * Guarda el archivo
	 * @param file archivo a guardar
	 * @return nombre del archivo guardado
	 */
	String store(MultipartFile file); 
	
	/**
	 * Carga el archivo
	 * @param filename nombre del archivo 
	 * @return ruta donde se almacenó
	 * */
	Path load(String filename); 
	
	/**
	 * Devuelve el fichero como recurso
	 * @param filename nombre del archivo
	 * @return el archivo como recurso
	 * */
	Resource loadAsResource(String filename); 
	
	/**
	 * Borra el archivo 
	 * @param filename nombre del archivo a eliminar
	  */
	void delete(String filename); 
	
}
