package com.example.demo.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.repositorio.UserRepositorio;

@Service
public class UsuarioServicio {
		
	@Autowired
	private UserRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	//Revisar funcion
	public long mostrarUser(){
		return repositorio.count();
	}
	
	
	public UsuarioModelo insertarUser(UsuarioModelo usuario) {
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
		/**UsuarioModelo nuevo = new UsuarioModelo();
		nuevo.setUser(usuario.getUser());
		nuevo.setPassword(passwordEncoder.encode(usuario.getPassword()));**/
		
		
		return repositorio.save(usuario);
	}
	
	public UsuarioModelo obtenerPorId(Long idUsuario) {
		UsuarioModelo usuario = repositorio.findById(idUsuario)
		.orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));

		return usuario;
	}

	
}
