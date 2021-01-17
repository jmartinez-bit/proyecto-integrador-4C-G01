package com.pcperu.web.app.service;

import java.util.List;

import com.pcperu.web.app.model.Carrito;
import com.pcperu.web.app.model.Usuario;

public interface CarritoService {
	
	public List<Carrito> listItems(Usuario usuario);
	
	public Integer agregarProducto(Integer productoId, Integer cantidad, Usuario usuario);
	
	public float updateCantidad(Integer productoId, Integer cantidad, Usuario usuario);
	
	public void removeProducto(Integer productoId, Usuario usuario);
}
