package com.pcperu.web.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;

@Repository
public interface ProductoRepository extends PagingAndSortingRepository<Producto, Integer>{
	Optional<Producto> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
	boolean existsByNombreAndUsuarioId(String nombre, Usuario usuario);
	boolean existsByIdAndUsuarioId(int id, Usuario usuario);
	List<Producto> findByUsuarioId(Usuario usuario);
	Page<Producto> findByUsuarioId(Pageable pageable,Usuario usuario);
	List<Producto> findFirst6ByOrderByIdDesc();
	List<Producto> findFirst6ByOrderById();
}