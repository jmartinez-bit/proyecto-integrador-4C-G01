package com.pcperu.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pcperu.web.app.model.Producto;

public interface ProductoService {

	public List<Producto> list(String nombre);
	
	public Page<Producto> list(Pageable pageable, String nombre);
	
	public Page<Producto> list(Pageable pageable);
	
	public Optional<Producto> getOne(int id);
	
	public Optional<Producto> getByNombre(String nombre);
	
	public void save(Producto producto, String email);
	
	public void delete(int id);
	
	public boolean existsById(int id);
	
	public boolean existsByNombre(String nombre, String emailUsuario);
	
	public boolean existsById(int id, String emailUsuario);
	
	public List<Producto> getProductosFavoritos();
	
	public List<Producto> getProductosExtra();
	
}