package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.CompraModelo;
import com.example.demo.servicio.CompraServicio;


/**
 * Controlador que crea, obtiene o elimina una compra*/
@RestController
@RequestMapping("/private/compra")
public class CompraControlador {

	@Autowired
	CompraServicio compraServicio;
	
	
	/**
	 * Metodo que crea una compra 
	 * @param idUsuario identifica al usuario asociado a la compra
	 * @return un ResponseEntity con un status HTTP y la compra en el body*/
	@PostMapping("/{idUsuario}")
	public ResponseEntity<CompraModelo> crearCompra(@PathVariable Long idUsuario){
		
		CompraModelo compra = compraServicio.crearCompra(idUsuario);
		
		return new ResponseEntity<>(compra, HttpStatus.OK);
	}
	
	/**
	 * Metodo que devuelve el historial de compras
	 * @param idUsuario identifica al usuario asociado a la compra
	 * @return la lista de compras*/
	@GetMapping("/historial/{idUsuario}")
	public List<CompraModelo> historialCompra (@PathVariable Long idUsuario){
		
		List<CompraModelo> compras = compraServicio.obtenerCompras(idUsuario);
		
		return compras;	
	}
	
	
	/**
	 * Metodo que devuelve los detalles de la compra
	 * @param idCompra identifica a la compra a detallar
	 * @return ResponseEntity con los detalles y un status HTTP en el body*/
	@GetMapping("/detalles/{idCompra}")
	public ResponseEntity<CompraModelo> obtenerDetalles (@PathVariable Long idCompra){
		
		CompraModelo detalles = compraServicio.obtenerDetalles(idCompra);
		
		return new ResponseEntity<>(detalles, HttpStatus.OK);
	}
	
	
	/**
	 * Metodo que elimina una compra
	 * @param idCompra identifica la compra a eliminar
	 * @return ResponseEntity con un status HTTP*/
	@DeleteMapping("/eliminar/{idCompra}")
	public ResponseEntity<String> eliminarCompra (@PathVariable Long idCompra){
		
		compraServicio.eliminarCompra(idCompra);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	
	
}
