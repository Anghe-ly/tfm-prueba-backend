package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.modelo.ProductoModelo;


@Repository
public interface PruebaRepositorio extends JpaRepository <ProductoModelo, Long> {

}
