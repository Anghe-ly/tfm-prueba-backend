package com.example.demo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.UsuarioModelo;

@Repository
public interface UserRepositorio extends JpaRepository <UsuarioModelo, Long> {

	UsuarioModelo findByUser(String user);
	
	Optional<UsuarioModelo> findById(Long idUsuario);


	
}
