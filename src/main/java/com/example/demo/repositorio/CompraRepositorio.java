package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.CompraModelo;

@Repository
public interface CompraRepositorio extends JpaRepository <CompraModelo, Long>{

	CompraModelo findByUsuario_IdUsuario(Long idUsuario);
	
	List<CompraModelo> findAllByUsuario_IdUsuario(Long idUsuario);
	
}
