package com.pcperu.web.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcperu.web.app.model.Carrito;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.CarritoRepository;
import com.pcperu.web.app.repository.ProductoRepository;

@Service
@Transactional
public class CarritoServiceImp implements CarritoService{
	
	@Autowired
	private CarritoRepository carritoRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	public List<Carrito> listItems(Usuario usuario) {
		return carritoRepository.findByUsuarioId(usuario);
	}
	
	public Integer agregarProducto(Integer productoId, Integer cantidad, Usuario usuario) {
		Integer cantidadAgregada = cantidad;
		
		Producto producto = productoRepository.findById(productoId).get();
	
		Carrito carrito = carritoRepository.findByUsuarioIdAndProductoId(usuario, producto);
		
		if (carrito != null) {
			cantidadAgregada = carrito.getCantidad() + cantidad;
			carrito.setCantidad(cantidadAgregada);
		}else {
			carrito = new Carrito();
			carrito.setCantidad(cantidad);
			carrito.setUsuarioId(usuario);
			carrito.setProductoId(producto);
		}
		
		carritoRepository.save(carrito);
		
		return cantidadAgregada;
	}
	
	public float updateCantidad(Integer productoId, Integer cantidad, Usuario usuario) {
		carritoRepository.updateCantidad(cantidad, productoId, usuario.getId());
		Producto producto = productoRepository.findById(productoId).get();
		float subtotal = producto.getPrecioVenta() * cantidad;
		return subtotal;
	}
	
	public void removeProducto(Integer productoId, Usuario usuario) {
		carritoRepository.deleteByUsuarioIdAndProductoIId(usuario.getId(), productoId);
	}
}
