package com.example.demo.modelo;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioModelo {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long idUsuario;
	
	private String user;
	
	private String correo;
	
	private String password;
	
	
	

	public UsuarioModelo(Long idUsuario, String user, String correo, String password) {
		super();
	
		this.idUsuario = idUsuario;
		this.user = user;
		this.correo = correo;
		this.password = password;
	}
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsuarioModelo() {
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long usuario) {
		this.idUsuario = usuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
