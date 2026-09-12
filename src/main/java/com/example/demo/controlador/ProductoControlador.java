package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.ProductoModelo;
import com.example.demo.servicio.ProductoServicio;

/**
 * Controlador de los productos en el catalogo publico
 *  */
@RestController
@RequestMapping("/public")


public class ProductoControlador {
	
	@Autowired
	private ProductoServicio servicio;

	/** Metodo que muestra todos los productos en la lista */		   
	@GetMapping("/prueba")
		    public List<ProductoModelo> productos(){
		    	return servicio.mostrar();
		    }
	
	/**
	 * Metodo que recupera un producto por su id
	 * @param id
	 * @return una response Entity con el producto
	 * */
	@GetMapping("/producto/{id}")
	public ResponseEntity<ProductoModelo> obtenerPorId(@PathVariable Long id){
		ProductoModelo producto = servicio.obtenerPorId(id);
		return ResponseEntity.ok(producto);
	}
   
		    
		  
	}
