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


/**
 * Controlador de carrito que agrega, muestra o elimina 
 * productos de un carrito de compra
	 * */
	@RestController
	@RequestMapping("/private")
	public class CarritoControlador {
 
	@Autowired
	private CarritoServicio servicio;
	
	/**
	 * Metodo que obtiene y muestra el carrito de compra
	 * @param idUsuario  identifica al usuario asociado al carrito
	 * @return ResponseEntity con el carrito en el body 
	 * */
	@GetMapping("{idUsuario}/carrito")
	public ResponseEntity<CarritoModelo> mostrarCarrito(@PathVariable Long idUsuario){
		
		CarritoModelo carrito = servicio.obtenerCarrito(idUsuario);
		
		return ResponseEntity.ok(carrito);
	}
	
	/**
	 * Metodo para añadir un producto al carrito
	 * @param idUsuario identifica al usuario 
	 * @param idProducto identifica al producto a añadir
	 * @param cantidad del producto
	 * @return ResponseEntity con el carrito actualizado en el body*/
	@PostMapping("/{idUsuario}/agregar")
	public ResponseEntity<CarritoModelo> agregarProducto(
	        @PathVariable Long idUsuario, 
	        @RequestParam Long idProducto, 
	        @RequestParam int cantidad) {
	    
	    CarritoModelo carrito = servicio.agregarProducto(cantidad, idProducto, idUsuario);
	    
	    return ResponseEntity.ok(carrito);
	}
	
	/**
	 * Metodo que elimina un producto del carrito
	 * @param idUsuario  identifica al usuario asociado al carrito
	 * @param idProducto identifica al producto a eliminar
	 * @return ResponseEntity con el carrito actualizado en el body 
	 * */
	@DeleteMapping("/{idUsuario}/eliminar")
	public ResponseEntity<CarritoModelo> eliminarProducto(@PathVariable Long idUsuario, @RequestParam Long idProducto) {
		CarritoModelo carrito = servicio.eliminarProducto(idProducto, idUsuario);
		
		 return ResponseEntity.ok(carrito);
	}
	
	/**
	 * Metodo que vacia el carrito entero
	 * @param idUsuario  identifica al usuario asociado al carrito
	 * @return ResponseEntity con el status HTTP
	 * */
	@DeleteMapping("/{idUsuario}/vaciar")
	public ResponseEntity<String> vaciarCarrito(@PathVariable Long idUsuario){

		CarritoModelo carrito = servicio.obtenerCarrito(idUsuario);
		servicio.vaciarCarrito(carrito);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	

}
