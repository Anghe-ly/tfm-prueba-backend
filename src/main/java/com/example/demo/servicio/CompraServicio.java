package com.example.demo.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.CarritoModelo;
import com.example.demo.modelo.CompraModelo;
import com.example.demo.modelo.ProductoCarrito;
import com.example.demo.modelo.ProductoModelo;
import com.example.demo.repositorio.CompraRepositorio;

@Service
public class CompraServicio {

	
	@Autowired
	private CompraRepositorio compraRepositorio;

    @Autowired
    private CarritoServicio carritoServicio;

		
	
	public CompraModelo crearCompra(Long idUsuario) {
		
		//buscar carrito por id del usuario
		CarritoModelo carrito = carritoServicio.obtenerCarrito(idUsuario);
		
		//verificar si carrito tiene productos
		
		if(carrito.getProductos() == null || carrito.getProductos().isEmpty()) {
			throw new RuntimeException("El carrito está vacio");
			   
		}
		
		//clonamos el carrito a la compra
		
			CompraModelo compra = new CompraModelo();
			compra.setFechaCompra(LocalDateTime.now());
			compra.setUsuario(carrito.getUsuario());
			compra.setTotal(carrito.getTotal());
			
			
			List<ProductoModelo> productosCompra = new ArrayList<>();
			for( ProductoCarrito productoCarrito : carrito.getProductos()) {
				
				productosCompra.add(productoCarrito.getProducto());
				
			}
			
			compra.setProductos(productosCompra);
		
			
			//guardamos la compra 
			
			compraRepositorio.save(compra);
			
		//limpiamos el carrito
			
			carritoServicio.eliminarCarrito(carrito);
				
		return compra;
	}
	
	public List<CompraModelo> obtenerCompras(Long idUsuario) {
		
		return compraRepositorio.findAllByUsuario_IdUsuario(idUsuario);
	}
	
	
	public CompraModelo obtenerDetalles(Long idCompra) {
		return compraRepositorio.findById(idCompra)
	            .orElseThrow(() -> new RuntimeException("No se encontró el ID de la compra seleccionada"));
		}
	
	
	public void eliminarCompra(Long idCompra) {
		
		CompraModelo compra = obtenerDetalles(idCompra);
		
		compraRepositorio.delete(compra);
	}
	
	
}
