package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.modelo.ProductoModelo;
import com.example.demo.servicio.ProductoServicio;
import com.example.demo.upload.StorageService;


/** controlador de funciones admin que 
 * contiene los metodos para CREAR, EDITAR o ELIMINAR productos
 * */

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin")

public class AdminControlador {
	
	@Autowired
	private ProductoServicio servicio;
	@Autowired
	private StorageService storageService;
	

	/** metodo para crear un nuevo producto del catalogo
	 * @param producto es el producto a crear 
	 * @param file es la imagen asociada al producto
	 * @return un status HTTP */
    @PostMapping("/producto")
    public ResponseEntity<String> nuevoProducto(@RequestPart ProductoModelo producto, @RequestPart("file") MultipartFile file) {
    	 
    	if(!file.isEmpty()) {
    		String img = storageService.store(file);
    		String imgURL = MvcUriComponentsBuilder.fromMethodName(FilesControlador.class, "serveFiles", img).build().toUriString();
    		producto.setImg(imgURL);
    		
    	}
    	
    	servicio.insertarProducto(producto);
     	return new ResponseEntity<>(HttpStatus.OK);

    }
    
    

	/** metodo para eliminar un producto del catalogo y su imagen si la tiene
		@param id del producto a eliminar
	 * @return un status HTTP */
    @DeleteMapping("/eliminar/{id}")
    public  ResponseEntity<String> borrarProducto(@PathVariable long id) {
    	
    	ProductoModelo producto = servicio.obtenerPorId(id);
    	
    	if(producto.getImg() == null || producto.getImg().isEmpty()) {
    		servicio.eliminarProducto(id);
    	}else {
    		servicio.eliminarConImg(producto);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    	
    }
    

    /** metodo para editar un producto
     * @param id del producto a editar
     * @param infoProducto todos los datos del producto para editar
     * @return un status HTTP 
     * 
     * */
    @PutMapping("/editar/{id}")
    public ResponseEntity<String> modificarProducto(@PathVariable long id, @RequestBody ProductoModelo infoProducto){
    	 ProductoModelo prod = servicio.obtenerPorId(id);
    	 
    	 if(prod != null) {
	    	 prod.setNombre(infoProducto.getNombre());
	    	 prod.setPrecio(infoProducto.getPrecio());
	    	 servicio.insertarProducto(prod);
	    	 
	    	return new ResponseEntity<>(HttpStatus.OK);
    	 }else {
    		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	 }
    	
    }

}
