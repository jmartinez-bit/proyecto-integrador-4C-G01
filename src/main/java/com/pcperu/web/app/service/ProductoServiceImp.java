package com.pcperu.web.app.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcperu.web.app.model.Categoria;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.CategoriaRepository;
import com.pcperu.web.app.repository.ProductoRepository;

@Service
@Transactional
public class ProductoServiceImp implements ProductoService{
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	UsuarioServiceImp usuarioService;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
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
	public void save(Producto producto, String email) {
		producto.setUsuarioId(usuarioService.obtenerUsuario(email));
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
	public boolean existsByNombre(String nombre, String emailUsuario) {
		Usuario usuario = usuarioService.obtenerUsuario(emailUsuario);
		return productoRepository.existsByNombreAndUsuarioId(nombre, usuario);
	}

	@Override
	public boolean existsById(int id, String emailUsuario) {
		Usuario usuario = usuarioService.obtenerUsuario(emailUsuario);
		return productoRepository.existsByIdAndUsuarioId(id, usuario);
	}

}
