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

/**
 * Controlador encargado del registro y 
 * recuperacion del usuario*/
@RestController
@RequestMapping("/public")

public class UsuarioControlador {

	@Autowired
	private UsuarioServicio servicio;
	

	/**
	 * Metodo que inserta un nuevo usuario
	 * @param usuario datos del usuario a insertar
	 * @return el usuario insertado
	 * */
	@PostMapping("/usuario")
	public UsuarioModelo nuevoUsuario(@RequestBody UsuarioModelo usuario) {
		return servicio.insertarUser(usuario);
	}
	
	/**
	 * Metodo que obtiene un usuario por su id 
	 * @param id identificador del usuario a recuperar
	 * @return usuario encontrado con id
	 * */
	@GetMapping("/usuario/{id}")
	public UsuarioModelo obtenerPorId(@PathVariable long id) {
		return servicio.obtenerPorId(id);
	}

}
