package com.pcperu.web.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcperu.web.app.model.Carrito;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.service.CarritoService;
import com.pcperu.web.app.service.UsuarioService;

@Controller
public class CarritoController {

	@Autowired
	CarritoService carritoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@GetMapping("/carrito")
	public String mostrarCarrito(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario usuario = usuarioService.obtenerUsuario(auth.getName());
		List<Carrito> carritoItems = carritoService.listItems(usuario);
		
		model.addAttribute("carritoItems", carritoItems);
		model.addAttribute("pageTitle", "Shopping Cart");
		
		return "carrito";
	}
	
}
