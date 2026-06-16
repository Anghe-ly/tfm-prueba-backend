package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_carrito")
public class ProductoCarrito {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne 
	@JoinColumn(name = "carrito_id")
	@JsonBackReference
	private CarritoModelo carrito;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "producto_id", referencedColumnName = "idproducto")
	private ProductoModelo producto;
	

    private int cantidad;
    
    private float  precioTotal;

    
    public ProductoCarrito() {
    	
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public CarritoModelo getCarrito() {
		return carrito;
	}


	public void setCarrito(CarritoModelo carrito) {
		this.carrito = carrito;
	}


	public ProductoModelo getProducto() {
		return producto;
	}


	public void setProducto(ProductoModelo producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public float getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(float  precioTotal) {
		this.precioTotal = precioTotal;
	}
    
    
    
    
}
