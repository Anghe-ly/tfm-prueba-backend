package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.modelo.ProductoModelo;


/**
 * Repositorio asociado a la tabla producto
 * */
@Repository
public interface ProductoRepositorio extends JpaRepository <ProductoModelo, Long> {

}
