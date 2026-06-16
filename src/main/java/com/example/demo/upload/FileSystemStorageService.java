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
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = StringUtils.getFilenameExtension(filename);
		String justFilename = filename.replace("."+extension, "");
		String storedFilename = System.currentTimeMillis() + "_" + justFilename + "." + extension;
		
		try {
			//verifica que el archivo no este vacio
			if(file.isEmpty()) {
				throw new RuntimeException("File is empty: " + filename);

			}
			
			//verifica la seguridad del archivo
			if(filename.contains("..")) {
			    throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
			}
			
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, this.rootLocation.resolve(storedFilename), StandardCopyOption.REPLACE_EXISTING);
				return storedFilename;
				
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
		    throw new RuntimeException("Failed to store file " + filename, e);
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
