package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class ProductoModelo {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "idproducto")
	private Long idProducto;

	private String nombre;
	
	private float precio;
	
	private String img;
	
	@JsonIgnore
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<ProductoCarrito> productoCarrito = new ArrayList<>();
	
	@JsonIgnore
    @ManyToMany(mappedBy = "productos")
    private List<CompraModelo> compras = new ArrayList<>();

	public ProductoModelo() {
	}

	
	

	
	public ProductoModelo(Long idProducto, String nombre, float precio, String img,
			List<ProductoCarrito> productoCarrito, List<CompraModelo> compras) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.img = img;
		this.productoCarrito = productoCarrito;
		this.compras = compras;
	}


	
	



	public Long getIdProducto() {
		return idProducto;
	}





	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public float getPrecio() {
		return precio;
	}





	public void setPrecio(float precio) {
		this.precio = precio;
	}





	public String getImg() {
		return img;
	}





	public void setImg(String img) {
		this.img = img;
	}





	public List<ProductoCarrito> getProductoCarrito() {
		return productoCarrito;
	}





	public void setProductoCarrito(List<ProductoCarrito> productoCarrito) {
		this.productoCarrito = productoCarrito;
	}





	public List<CompraModelo> getCompras() {
		return compras;
	}





	public void setCompras(List<CompraModelo> compras) {
		this.compras = compras;
	}





	@Override
	public String toString() {
		return "ProductoModelo [idProducto=" + idProducto + ", nombre=" + nombre + ", precio=" + precio + ", img=" + img
				+ "]";
	}




	
	
}