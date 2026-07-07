package com.example.demo.servicio;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.CarritoModelo;
import com.example.demo.modelo.ProductoCarrito;
import com.example.demo.modelo.UsuarioModelo;
import com.example.demo.modelo.ProductoModelo;

import com.example.demo.repositorio.CarritoRepositorio;
import com.example.demo.repositorio.PruebaRepositorio;
import com.example.demo.repositorio.UserRepositorio;

@Service
public class CarritoServicio {

	@Autowired
	private CarritoRepositorio repositorio;
	
	@Autowired
	private UserRepositorio usuarioRepositorio;
	
	@Autowired
	private  PruebaRepositorio productoRepositorio;
	
	public CarritoModelo obtenerCarrito(Long idUsuario) {
		CarritoModelo carrito = repositorio.findByUsuario_IdUsuario(idUsuario);
		
		if (carrito == null) {
			return crearCarrito(idUsuario);
			
		}else {
			return carrito;
		}
	}
	
	
	public CarritoModelo crearCarrito(Long idUsuario) {
		CarritoModelo carrito = repositorio.findByUsuario_IdUsuario(idUsuario);
		
		if(carrito == null) {
			UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
			
			carrito = new CarritoModelo();
			carrito.setUsuario(usuario);
			carrito.setFechaCompra(LocalDateTime.now());
			
			
		}
		
		return repositorio.save(carrito);
	}
	
	
	
	   private void actualizarTotales(CarritoModelo carrito) {
	        int cantidadTotal = 0;
	        float precioTotal = 0;

	        for (ProductoCarrito pc : carrito.getProductos()) {
	            cantidadTotal += pc.getCantidad();
	            precioTotal += pc.getPrecioTotal();
	        }

	        carrito.setCantidadTotal(cantidadTotal);
	        carrito.setTotal(precioTotal);
	    }
	   
	   public CarritoModelo agregarProducto(int cantidad, Long idProducto, Long idUsuario) {
		   
		   
		   
		   CarritoModelo carrito = obtenerCarrito(idUsuario);
		   

		   ProductoModelo producto = productoRepositorio.findById(idProducto)
				   .orElseThrow(()-> new RuntimeException("Producto no encontrado con id:  " + idProducto));
		   
		   
		   Optional<ProductoCarrito> productoExistente = carrito.getProductos().stream()
		            .filter(pc -> pc.getProducto().getIdProducto().equals(idProducto))
		            .findFirst();
		 
		   
		   
		   if(productoExistente.isPresent()) {
			   
			   ProductoCarrito pc = productoExistente.get();
			   
			   if(cantidad <= 0) {
				   carrito.getProductos().remove(pc);
			   }else {
				   pc.setCantidad(cantidad);
				   pc.setPrecioTotal(pc.getProducto().getPrecio() * cantidad);
				   
			   }
			   
			  
		   }else if(cantidad > 0){
			   ProductoCarrito productoNuevo = new ProductoCarrito();
			   productoNuevo.setCarrito(carrito);
			   productoNuevo.setCantidad(cantidad);
			   productoNuevo.setProducto(producto);
			   productoNuevo.setPrecioTotal(producto.getPrecio() * cantidad);
			   carrito.getProductos().add(productoNuevo);
			   
		   }
		   
		   actualizarTotales(carrito);
		   repositorio.save(carrito);
		   
		   return carrito;
	   }
	   
	   
	   public CarritoModelo eliminarProducto(Long idProducto, Long idUsuario) {
		   
		 CarritoModelo carrito = obtenerCarrito(idUsuario);
		 
		  ProductoCarrito pc = carrito.getProductos().stream()
			        .filter(p -> p.getId().equals(idProducto))
			        .findFirst()
			        .orElseThrow(() -> new RuntimeException("Producto no existe en el carrito"));

			    carrito.getProductos().remove(pc);
		
		actualizarTotales(carrito);
		repositorio.save(carrito);
		
		return carrito;
		   
	   }
	   
	   public void eliminarCarrito(CarritoModelo carrito) {
		   carrito.getProductos().clear();
		   carrito.setCantidadTotal(0);
		   carrito.setTotal(0);
		   
		   actualizarTotales(carrito);
		   repositorio.save(carrito);
	   }
	   
	   
	}

	
	
	

