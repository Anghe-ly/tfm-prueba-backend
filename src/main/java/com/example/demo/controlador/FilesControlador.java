package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.upload.StorageService;

@RestController
public class FilesControlador {

	@Autowired
	StorageService storageService;
	
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> serveFiles(@PathVariable String filename){
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().body(file);
	}
}
