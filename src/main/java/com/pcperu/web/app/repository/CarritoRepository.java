package com.pcperu.web.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pcperu.web.app.model.Carrito;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer>{
	public List<Carrito> findByUsuarioId(Usuario usuario);
	public Carrito findByUsuarioIdAndProductoId(Usuario usuario, Producto producto);
	
	@Transactional
	@Modifying
	@Query("UPDATE Carrito c SET c.cantidad = ?1 WHERE c.productoId.id = ?2 "
			+ "AND c.usuarioId.id = ?3")
	public void updateCantidad(Integer cantidad, Integer productoId, Integer usuarioId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Carrito c WHERE c.usuarioId.id = ?1 AND c.productoId.id = ?2")
	public void deleteByUsuarioIdAndProductoIId(Integer usuarioId, Integer productoId);
}
