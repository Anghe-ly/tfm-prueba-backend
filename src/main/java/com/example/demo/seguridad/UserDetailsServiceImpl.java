package com.example.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.repositorio.UserRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepositorio repositorio;
	
	
	
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
		
		System.out.println("Usuario: " + usuario.getUser());
		System.out.println("Rol asignado: " + rol);
		
		
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
