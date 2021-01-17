package com.pcperu.web.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.pcperu.web.app.model.Carrito;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.CarritoRepository;
import com.pcperu.web.app.repository.ProductoRepository;
import com.pcperu.web.app.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CarritoTest {

	@Autowired
	private CarritoRepository carritoRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private UsuarioRepository UsuarioRepository;
	
	@Test
	public void testAddOneCartItem() {
		Producto producto = productoRepository.findById(67).get();
		Usuario usuario = UsuarioRepository.findById(4).get();
		
		Carrito carrito = new Carrito();
		carrito.setUsuarioId(usuario);
		carrito.setProductoId(producto);
		carrito.setCantidad(1);
		
		Carrito saveCarrito = carritoRepository.save(carrito);
		
		assertTrue(saveCarrito.getId() > 0);
	}
	
	@Test
	public void testGetCartItemsByUser() {
		Usuario usuario = new Usuario();
		usuario.setId(4);
		List<Carrito> carrito = carritoRepository.findByUsuarioId(usuario);
	
		assertEquals(2, carrito.size());
	}
}
