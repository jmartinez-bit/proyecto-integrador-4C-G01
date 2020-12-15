package com.pcperu.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.ProductoRepository;
import com.pcperu.web.app.repository.UsuarioRepository;

@Service
@Transactional
public class ProductoServiceImp implements ProductoService{
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	UsuarioServiceImp usuarioService;
	
	@Override
	public List<Producto> list(String nombre) {
		Usuario usuario = usuarioService.obtenerUsuario(nombre);
		return productoRepository.findByUsuarioId(usuario);
	}

	@Override
	public Optional<Producto> getOne(int id) {
		return productoRepository.findById(id);
	}

	@Override
	public Optional<Producto> getByNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	@Override
	public void save(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void delete(int id) {
		productoRepository.deleteById(id);
	}

	@Override
	public boolean existsById(int id) {
		return productoRepository.existsById(id);
	}

	@Override
	public boolean existsByNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}

}
