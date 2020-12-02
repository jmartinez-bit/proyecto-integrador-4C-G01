package com.pcperu.web.app.service;

import com.pcperu.web.app.model.AuthenticationProvider;
import com.pcperu.web.app.model.Usuario;

public interface UsuarioService {
	
	public void registrarUsuario(Usuario usuario);
	
	public boolean usuarioExiste(Usuario usuario);
	
	public Usuario obtenerUsuario(String email);

	public void crearNuevoUsuarioOAuth(String email, String name, AuthenticationProvider provider);

	public void actualizarUsuarioOAuth(Usuario usuario, String name, AuthenticationProvider provider);

}
