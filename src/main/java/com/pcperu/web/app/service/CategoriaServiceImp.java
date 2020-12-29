package com.pcperu.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcperu.web.app.model.Categoria;
import com.pcperu.web.app.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaServiceImp implements CategoriaService{
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> list() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria getByNombre(String nombre) {
		return categoriaRepository.findByNombre(nombre);
	}
}
