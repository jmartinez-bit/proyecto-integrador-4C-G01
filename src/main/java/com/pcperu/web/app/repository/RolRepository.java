package com.pcperu.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcperu.web.app.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

	public Rol findByNombre(String rol);
}
