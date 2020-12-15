package com.pcperu.web.app.service;

import java.util.List;
import java.util.Optional;

import com.pcperu.web.app.model.Producto;

public interface ProductoService {

	public List<Producto> list(String nombre);
	
	public Optional<Producto> getOne(int id);
	
	public Optional<Producto> getByNombre(String nombre);
	
	public void save(Producto producto);
	
	public void delete(int id);
	
	public boolean existsById(int id);
	
	public boolean existsByNombre(String nombre);
	
}