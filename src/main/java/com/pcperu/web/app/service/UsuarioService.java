package com.pcperu.web.app.service;

import com.pcperu.web.app.model.Usuario;

public interface UsuarioService {
	
	public void registrarUsuario(Usuario usuario);
	
	public boolean usuarioExiste(Usuario usuario);

}
