package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.CarritoModelo;

/**
 * Repositorio asociado a la tabla Carrito
 * */
@Repository
public interface CarritoRepositorio extends JpaRepository <CarritoModelo, Long>{

	/**
	 * Metodo que busca un carrito asociado 
	 * a un usuario por su id
	 * @param idUsuario identifica al usuario 
	 * @return carrito de ese usuario
	 * */
	CarritoModelo findByUsuario_IdUsuario(Long idUsuario);
}
