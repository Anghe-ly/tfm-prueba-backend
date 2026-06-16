package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.CarritoModelo;


@Repository
public interface CarritoRepositorio extends JpaRepository <CarritoModelo, Long>{

	CarritoModelo findByUsuario_IdUsuario(Long idUsuario);
}
