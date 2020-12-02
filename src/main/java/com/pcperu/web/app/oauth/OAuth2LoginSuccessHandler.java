package com.pcperu.web.app.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pcperu.web.app.model.AuthenticationProvider;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.service.UsuarioService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		UsuarioOAuth2Usuario oauth2Usuario = (UsuarioOAuth2Usuario) authentication.getPrincipal();
		String email = oauth2Usuario.getEmail();
		Usuario usuario = usuarioService.obtenerUsuario(email);
		
		if(usuario == null) {
			usuarioService.crearNuevoUsuarioOAuth(email, oauth2Usuario.getName(), AuthenticationProvider.GOOGLE);
		}else {
			usuarioService.actualizarUsuarioOAuth(usuario, oauth2Usuario.getName(), AuthenticationProvider.GOOGLE);
		}
		
		System.out.println("User's email: "+ email);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
