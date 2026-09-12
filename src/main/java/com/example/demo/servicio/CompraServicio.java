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


/**
 * 
 * Servicio para la gestion de compra*/
@Service
public class CompraServicio {

	
	@Autowired
	private CompraRepositorio compraRepositorio;

    @Autowired
    private CarritoServicio carritoServicio;

		
	/**
	 * Metodo que crea la compra 
	 * clonando el carrito dentro de la compra 
	 * y luego limpiando el carrito
	 * @param idUsuario identifica asociado a la compra
	 * @return la compra
	 * */
	public CompraModelo crearCompra(Long idUsuario) {
		
		CarritoModelo carrito = carritoServicio.obtenerCarrito(idUsuario);
				
		if(carrito.getProductos() == null || carrito.getProductos().isEmpty()) {
			throw new RuntimeException("El carrito está vacio");
			   
		}
		
		
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
			
			carritoServicio.vaciarCarrito(carrito);
				
		return compra;
	}
	
	/**
	 * Metodo que obtiene la lista de compras
	 * @param idUsuario identifica al usuario asociado a las compras
	 * @return lista de compras 
	 * */
	public List<CompraModelo> obtenerCompras(Long idUsuario) {
		
		return compraRepositorio.findAllByUsuario_IdUsuario(idUsuario);
	}
	
	
	/**
	 * Metodo que obtiene una compra especifica con su id
	 * @param idCompra identifica la compra
	 * @return la compra 
	 * */
	public CompraModelo obtenerDetalles(Long idCompra) {
		return compraRepositorio.findById(idCompra)
	            .orElseThrow(() -> new RuntimeException("No se encontró el ID de la compra seleccionada"));
		}
	
	
	/**
	 * Metodo que elimina una compra
	 * @param idCompra identifica la compra a eliminar
	 * */
	public void eliminarCompra(Long idCompra) {
		
		CompraModelo compra = obtenerDetalles(idCompra);
		
		compraRepositorio.delete(compra);
	}
	
	
}
