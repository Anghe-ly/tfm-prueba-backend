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

@RestController
@RequestMapping("/private/compra")
public class CompraControlador {

	@Autowired
	CompraServicio compraServicio;
	
	
	@PostMapping("/{idUsuario}")
	public ResponseEntity<CompraModelo> crearCompra(@PathVariable Long idUsuario){
		
		CompraModelo compra = compraServicio.crearCompra(idUsuario);
		
		return new ResponseEntity<>(compra, HttpStatus.OK);
	}
	
	
	@GetMapping("/historial/{idUsuario}")
	public List<CompraModelo> historialCompra (@PathVariable Long idUsuario){
		
		List<CompraModelo> compras = compraServicio.obtenerCompras(idUsuario);
		
		return compras;
		
	}
	
	
	
	@GetMapping("/detalles/{idCompra}")
	public ResponseEntity<CompraModelo> obtenerDetalles (@PathVariable Long idCompra){
		
		CompraModelo detalles = compraServicio.obtenerDetalles(idCompra);
		
		return new ResponseEntity<>(detalles, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/eliminar/{idCompra}")
	public ResponseEntity<String> eliminarCompra (@PathVariable Long idCompra){
		
		compraServicio.eliminarCompra(idCompra);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	
	
}
