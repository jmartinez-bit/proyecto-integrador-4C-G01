package com.pcperu.web.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	Optional<Producto> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
	List<Producto> findByUsuarioId(Usuario usuario);
}