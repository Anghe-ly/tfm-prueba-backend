package com.example.demo.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.repositorio.UserRepositorio;
import com.example.demo.servicio.JwtServicio;

@RestController
@RequestMapping("/login")

public class InicioSesionControlador {


	
	private final AuthenticationManager authenticationManager;
	private final JwtServicio jwtServicio;
	private final UserRepositorio userRepositorio;

	public InicioSesionControlador(AuthenticationManager authenticationManager, JwtServicio jwtServicio, UserRepositorio userRepositorio ) {
		this.authenticationManager =   authenticationManager;
		this.jwtServicio = jwtServicio;
		this.userRepositorio = userRepositorio;
	}
	
	//clases internas 
	
	static class LoginRequest{
		public String username;
		public String password;
	}
	
	static class LoginResponse{
		public String token;
		public LoginResponse(String token) {
			this.token = token;
		}
	}
	
	
	//peticiones HTTP
	@PostMapping("/auth")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		
		
		try {
		authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(request.username, request.password));
		
		UsuarioModelo usuario = userRepositorio.findByUser(request.username);
		if(usuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("usuario no encontrado"));
 
			}
		

	    String rol;
	    if (usuario.getUser().equals("admin")) {
	        rol = "ROLE_ADMIN";
	    } else {
	        rol = "ROLE_USER";
	    }
	    String token = jwtServicio.generarTokenConIdYRol(usuario.getIdUsuario(), usuario.getUser(), rol);
			return ResponseEntity.ok(new LoginResponse(token));

		
		} catch (BadCredentialsException e) {
	        // Contraseña incorrecta
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new LoginResponse("Credenciales inválidas"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new LoginResponse("Error interno: " + e.getMessage()));
	    }
	
	}
	
}
