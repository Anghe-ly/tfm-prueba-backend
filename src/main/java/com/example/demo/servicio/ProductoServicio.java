package com.example.demo.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.modelo.ProductoModelo;
import com.example.demo.repositorio.ProductoRepositorio;
import com.example.demo.upload.StorageService;

/**
 * Servicio que gestiona los productos del catalogo*/
@Service
public class ProductoServicio {
	@Autowired
	ProductoRepositorio repositorio;
	@Autowired
	private StorageService storageService;
	

	/**
	 * Metodo que muestra todos los productos
	 * @return lista de productos
	 * */
	public List<ProductoModelo> mostrar(){
		return repositorio.findAll();	
	}
	
	
	/**
	 * Metodo que busca un producto por su id
	 * @param idproducto identifica el producto 
	 * @return producto encontrado
	 * */
	public ProductoModelo obtenerPorId(long idproducto) {
		return repositorio.findById(idproducto).orElse(null);
	}

	/**
	 * Metodo para añadir un producto
	 * @param producto datos del producto a añadir
	 * @return producto guardado
	 * */
	public ProductoModelo insertarProducto(ProductoModelo producto) {
		return repositorio.save(producto);
	}
	
	/**
	 * Metodo que elimina un producto
	 * @param idproducto identifica el producto a eliminar
	 */
	public void eliminarProducto(long idproducto) {
		repositorio.deleteById(idproducto);
	}
	
	/**
	 * Metodo que elimina un producto y su imagen
	 * @param producto datos del producto a eliminar
	 * */
	public void eliminarConImg(ProductoModelo producto) {
		if(producto.getImg() != null && !producto.getImg().isEmpty()) {
			storageService.delete(producto.getImg());
		}
		
		repositorio.delete(producto);

	}
}
