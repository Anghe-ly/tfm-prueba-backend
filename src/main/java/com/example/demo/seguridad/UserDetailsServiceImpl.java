package com.example.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.repositorio.UserRepositorio;

/**
 * Servicio que implementa el UserDetailsService
 * para que Spring busque a un usuario determinado*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepositorio repositorio;
	
	
	/**
	 * Metodo que busca a un usuario por su username
	 * y construye un objeto User
	 * @param user nombre del usuario
	 * @return el usuario con las indicaciones de Spring Security
	 * @throws UsernameNotFoundException si el usuario no existe 
	 */
	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		UsuarioModelo usuario = repositorio.findByUser(user);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("usuario no encontrado");
		}
		
		String rol;
		
		
		if(usuario.getUser().equals("admin")) {
			rol = "ROLE_ADMIN";
		}else {
			rol = "ROLE_USER";
		}
		
		return User.builder()
				.username(usuario.getUser())
				.password(usuario.getPassword())
				.authorities(rol)
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}
}
