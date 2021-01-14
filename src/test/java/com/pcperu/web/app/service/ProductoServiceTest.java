package com.pcperu.web.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.pcperu.web.app.model.Categoria;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.ProductoRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductoServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceTest.class);
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testRegistrarProducto() {
		String NOMBRE = "Producto de prueba";
		String DESCRIPCION = "Descripción de prueba";
		String ESTADO = "nuevo";
		float PREC_VENTA = 400;
		int STOCK = 20;
		Usuario usuario = usuarioService.obtenerUsuario("admin@admin.com");
		Categoria categoria = categoriaService.getByNombre("Procesadores");
		Set<Categoria> categorias = new HashSet<Categoria>();
		categorias.add(categoria);
		Producto producto = new Producto();
		producto.setNombre(NOMBRE);
		producto.setDescripcion(DESCRIPCION);
		producto.setEstado(ESTADO);
		producto.setPrecioVenta(PREC_VENTA);
		producto.setStock(STOCK);
		producto.setCategorias(categorias);
		
		productoService.save(producto, usuario.getEmail());
		logger.info(">" + producto);
		producto = productoService.getByNombre(NOMBRE).get();
		
		assertThat(producto.getId()).isNotNull();
		assertEquals(NOMBRE, producto.getNombre());
		assertEquals(DESCRIPCION, producto.getDescripcion());
		assertEquals(ESTADO, producto.getEstado());
		assertEquals(PREC_VENTA, producto.getPrecioVenta());
		assertEquals(STOCK, producto.getStock());
		
		productoRepository.delete(producto);
	}
}
