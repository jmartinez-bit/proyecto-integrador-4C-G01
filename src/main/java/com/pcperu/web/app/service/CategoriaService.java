package com.pcperu.web.app.service;

import java.util.List;
import java.util.Optional;

import com.pcperu.web.app.model.Categoria;

public interface CategoriaService {
	public List<Categoria> list();
	public Categoria getByNombre(String nombre);
}
