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
import com.example.demo.repositorio.ProductoRepositorio;
import com.example.demo.repositorio.UserRepositorio;

/**
 *Servicio con la logica del carrito de compra */
@Service
public class CarritoServicio {

	@Autowired
	private CarritoRepositorio repositorio;
	
	@Autowired
	private UserRepositorio usuarioRepositorio;
	
	@Autowired
	private  ProductoRepositorio productoRepositorio;
	
	
	/**
	 *Metodo que muestra un carrito asociado a un 
	 *usuario si no tiene uno lo crea
	 *@param idUsuario identifica al usuario asociado al carrito
	 *@return el carrito ya xistente o el nuevo */
	public CarritoModelo obtenerCarrito(Long idUsuario) {
		CarritoModelo carrito = repositorio.findByUsuario_IdUsuario(idUsuario);
		
		if (carrito == null) {
			return crearCarrito(idUsuario);
			
		}else {
			return carrito;
		}
	}
	
	/**
	 * Metodo donde se crea un carrito asignado a un
	 * usuario y con una fecha de creacion
	 * @param idUsuario identifica al usuario
	 * @return el carrito creado
	 * */
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
	
	
	/**
	 * Metodo privado para actualizar los totales de cantidades 
	 * y precios en el carrito*/
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
	   
	   
	   /**
	    * Metodo que agrega un producto al carrito
	    * del usuario, verificando si el producto existe
	    * y actualizando su cantidad. Si esta es menor a 0
	    * se elimina del carrito
	    * @param cantidad identifica la cantidad
	    * @param idProducto identifica el producto a incluir
	    * @param idUsuario identifica al usuario
	    * @return carrito actualizado */
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
	   
	   /**
	    * Metodo que elimina un producto
	    * del carrito, actualiza totales
	    * y lo guarda
	    * @param idProducto identifica al producto dentro del carrito a eliminar
	    * @param idUsuario identifica al usuario
	    * @return carrito actualizado*/
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
	   
	   /**
	    * Metodo que vacia el carrito entero
	    * limpiando sus totales
	    * @param carrito el carrito a limpiar*/
	   public void vaciarCarrito(CarritoModelo carrito) {
		   carrito.getProductos().clear();
		   carrito.setCantidadTotal(0);
		   carrito.setTotal(0);
		   
		   actualizarTotales(carrito);
		   repositorio.save(carrito);
	   }
	   
	   
	}

	
	
	

