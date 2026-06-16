package com.example.demo.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.CarritoModelo;
import com.example.demo.servicio.CarritoServicio;

@RestController
@RequestMapping("/private")
public class CarritoControlador {
 
	@Autowired
	private CarritoServicio servicio;
	
	
	@GetMapping("{idUsuario}/carrito")
	public ResponseEntity<CarritoModelo> mostrarCarrito(@PathVariable Long idUsuario){
		
		CarritoModelo carrito = servicio.obtenerCarrito(idUsuario);
		
		return ResponseEntity.ok(carrito);
	}
	

	@PostMapping("/{idUsuario}/agregar")
	public ResponseEntity<CarritoModelo> agregarProducto(
	        @PathVariable Long idUsuario, 
	        @RequestParam Long idProducto, 
	        @RequestParam int cantidad) {
	    
	    // Llamamos al método del servicio que ya tienes programado
	    CarritoModelo carrito = servicio.agregarProducto(cantidad, idProducto, idUsuario);
	    
	    return ResponseEntity.ok(carrito);
	}
	
	
	@DeleteMapping("/{idUsuario}/eliminar")
	public ResponseEntity<CarritoModelo> eliminarProducto(@PathVariable Long idUsuario, @RequestParam Long idProducto) {
		CarritoModelo carrito = servicio.eliminarProducto(idProducto, idUsuario);
		
		 return ResponseEntity.ok(carrito);
	}
	
	@DeleteMapping("/{idUsuario}/vaciar")
	public ResponseEntity<String> eliminarCarrito(@PathVariable Long idUsuario){

		CarritoModelo carrito = servicio.obtenerCarrito(idUsuario);
		servicio.eliminarCarrito(carrito);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	

}
