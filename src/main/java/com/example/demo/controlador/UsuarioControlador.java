package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.servicio.UsuarioServicio;

@RestController
@RequestMapping("/public")

public class UsuarioControlador {

	@Autowired
	private UsuarioServicio servicio;
	

	// metodo que crea un usuario
	@PostMapping("/usuario")
	public UsuarioModelo nuevoUsuario(@RequestBody UsuarioModelo usuario) {
		return servicio.insertarUser(usuario);
	}
	
	//metodo que recupera al usuario por su ID
	@GetMapping("/usuario/{id}")
	public UsuarioModelo obtenerPorId(@PathVariable long id) {
		return servicio.obtenerPorId(id);
	}

}
