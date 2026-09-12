package com.example.demo.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.repositorio.UserRepositorio;


/**
 * Servicio que inserta y obtiene a un usuario*/
@Service
public class UsuarioServicio {
		
	@Autowired
	private UserRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	/**
	 * Metodo que añade un usuario 
	 * @param usuario datos del usuario a insertar
	 * @return usuario insertado
	 * */
	public UsuarioModelo insertarUser(UsuarioModelo usuario) {
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));	
		return repositorio.save(usuario);
	}

	
	/**
	 * Metodo que recupera un usuario con su id
	 * @param idUsuario identifica al usuario 
	 * @return usuario obtenido
	 * */
	public UsuarioModelo obtenerPorId(Long idUsuario) {
		UsuarioModelo usuario = repositorio.findById(idUsuario)
		.orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));

		return usuario;
	}

	
}
