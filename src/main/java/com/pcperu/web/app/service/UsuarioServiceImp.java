package com.pcperu.web.app.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcperu.web.app.model.Rol;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.repository.RolRepository;
import com.pcperu.web.app.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public void registrarUsuario(Usuario usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuario.setStatus("VERIFIED");
		Rol usuarioRol = rolRepository.findByNombre("USER");
		usuario.setRoles(new HashSet<Rol>(Arrays.asList(usuarioRol)));
		usuarioRepository.save(usuario);
	}

	@Override
	public boolean usuarioExiste(Usuario usuario) {
		boolean existe = false;
		Usuario existeUsuario = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(existeUsuario != null) {
			existe = true;
		}
		return existe;
	}

}
