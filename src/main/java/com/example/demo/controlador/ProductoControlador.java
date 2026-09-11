package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.ProductoModelo;
import com.example.demo.servicio.PruebaServicio;


@RestController
@RequestMapping("/public")


public class PruebaControlador {
	
	@Autowired
	private PruebaServicio servicio;

	//metodo que recupera y lista los productos
		    @GetMapping("/prueba")
		    public List<ProductoModelo> productos(){
		    	return servicio.mostrar();
		    }

	//metodo que recupera el producto por su ID
		    @GetMapping("/producto/{id}")
		    public ResponseEntity<ProductoModelo> obtenerPorId(@PathVariable long id){
		    	System.out.println(("buscando el id:    " + id));
		    	ProductoModelo producto = servicio.obtenerPorId(id);
		    	
		    	
		    	System.out.println("Producto encontrado:    " + producto);
		    	return ResponseEntity.ok(producto);
		    }
		    
		    
		  
	}
