package com.example.demo.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.modelo.ProductoModelo;
import com.example.demo.repositorio.PruebaRepositorio;
import com.example.demo.upload.StorageService;

@Service
public class PruebaServicio {
	@Autowired
	PruebaRepositorio repositorio;
	@Autowired
	private StorageService storageService;
	

	
	public List<ProductoModelo> mostrar(){
		return repositorio.findAll();	
	}
	
	public ProductoModelo obtenerPorId(long idproducto) {
		return repositorio.findById(idproducto).orElse(null);
	}

	public ProductoModelo insertarProducto(ProductoModelo producto) {
		return repositorio.save(producto);
	}
	
	public void eliminarProducto(long idproducto) {
		repositorio.deleteById(idproducto);
	}
	
	public void eliminarConImg(ProductoModelo producto) {
		if(producto.getImg() != null && !producto.getImg().isEmpty()) {
			storageService.delete(producto.getImg());
		}
		
		repositorio.delete(producto);

	}
	
	//metodo para editar
	public ProductoModelo editarProducto(ProductoModelo producto) {
		return repositorio.save(producto);
		
	}
}
