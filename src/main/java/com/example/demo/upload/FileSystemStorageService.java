package com.example.demo.upload;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileSystemStorageService implements StorageService {

	
	private final Path rootLocation;
	
	
	public FileSystemStorageService(StorageProperties properties) {
		
		this.rootLocation = Paths.get(properties.getLocation());
		
	}
	
	

	@Override
	public void init() {
		
		try {
			Files.createDirectories(rootLocation);
			
		}
		catch(IOException e){
			throw new RuntimeException("Could not initialize storage", e);
		}
		
	}
	
	
	@Override
	public Stream<Path> loadAll(){
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		}
		catch(IOException e) {
		    throw new RuntimeException("Failed to read files ", e);

		}
	}
	
	@Override
	public String store(MultipartFile file) {
		
		String originalFilename= file.getOriginalFilename();
		String filename = StringUtils.cleanPath(originalFilename != null ? originalFilename : "desconocido");
		String extension = StringUtils.getFilenameExtension(filename);
		String justFilename;
		
		if(extension == null || extension.isEmpty()) {
			String contentType = file.getContentType();
			extension = (contentType != null && contentType.contains("/") ? contentType.split("/")[1] : "png");
			justFilename = filename;
		
		}else {
			justFilename = filename.substring(0, filename.lastIndexOf("."));
		}
		
		String normalized = java.text.Normalizer.normalize(justFilename, java.text.Normalizer.Form.NFD);
		String noTildes = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		String cleanName = noTildes.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").toLowerCase();
		
		String storedFilename = System.currentTimeMillis() + "_" + cleanName + "." + extension;
		
		
		try {
			//verifica que el archivo no este vacio
			if(file.isEmpty()) {
				throw new RuntimeException("El archivo esta vacío: " + filename);

			}
			
			//verifica la seguridad del archivo
			if(filename.contains("..")) {
			    throw new RuntimeException("No se puede guardar en archivo en una ruta fuera del directorio actual" + filename);
			}
			
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, this.rootLocation.resolve(storedFilename), StandardCopyOption.REPLACE_EXISTING);
				return storedFilename;
				
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
		    throw new RuntimeException("Error al guardar el archivo " + filename, e);
		}
	}
	

	
	
	@Override
	public Path load(String filename) {
		
	return rootLocation.resolve(filename);
	}
	
	
	
	@Override
	public Resource loadAsResource(String filename){
		try {
			Path file = load(filename);
				Resource resource = new UrlResource(file.toUri());
				
				if(!(resource.exists() || resource.isReadable())) {
				    throw new RuntimeException("Could not read file " + filename);					
				}
				
				return resource;
				
		} catch (MalformedURLException e) {
			// TODO: handle exception
		    throw new RuntimeException("Could not read file " + filename, e);
		}
	}
	
	
	@Override
	public void delete(String filename) {
		String justFilename = StringUtils.getFilename(filename);
		
		try {
			Path file = load(justFilename);
			Files.deleteIfExists(file);
			
			
		}catch(IOException e){
			throw new RuntimeException("Error to delete the file", e);
		}
		
	}
	
	
}
