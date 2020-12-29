package com.pcperu.web.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.pcperu.web.app.model.Usuario;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceTest.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testRegistrarUsuario() {
		String USERNAME = "JSanches";
		String EMAIL = "jsanches@correo.com";
		String PASS = "12345";
		
		Usuario usuario = new Usuario();
		usuario.setUsername(USERNAME);
		usuario.setEmail(EMAIL);
		usuario.setPassword(PASS);
		
		usuarioService.registrarUsuario(usuario);
		logger.info(">" + usuario);
		usuario = usuarioService.obtenerUsuario(EMAIL);
		
		assertThat(usuario.getId()).isNotNull();
		assertEquals(USERNAME, usuario.getUsername());
		assertEquals(EMAIL, usuario.getEmail());
	}
}
