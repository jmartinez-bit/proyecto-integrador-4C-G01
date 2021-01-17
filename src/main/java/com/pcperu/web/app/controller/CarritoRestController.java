package com.pcperu.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.service.CarritoService;
import com.pcperu.web.app.service.UsuarioService;

@RestController
public class CarritoRestController {

	@Autowired
	private CarritoService carritoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@PostMapping("/carrito/add/{pid}/{qty}")
	public String agregarProductoAlCarrito(@PathVariable("pid") Integer productoId, 
				@PathVariable("qty") Integer cantidad,
				@AuthenticationPrincipal Authentication authentication) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName() == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Debes iniciar sesión para agregar productos al carrito";
		}
		Usuario usuario = usuarioService.obtenerUsuario(auth.getName());
		
		if (usuario == null) {
			return "Debes iniciar sesión para agregar productos al carrito";
		}
		
		Integer cantidadAgregada = carritoService.agregarProducto(productoId, cantidad, usuario);
		
		return cantidadAgregada + " items de este producto estan agregados en tu carrito de compra.";
	}
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@PostMapping("/carrito/update/{pid}/{qty}")
	public String updateCantidad(@PathVariable("pid") Integer productoId, 
				@PathVariable("qty") Integer cantidad,
				@AuthenticationPrincipal Authentication authentication) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName() == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Debes iniciar sesión para actualizar cantidad.";
		}
		Usuario usuario = usuarioService.obtenerUsuario(auth.getName());
		
		if (usuario == null) {
			return "Debes iniciar sesión para actualizar cantidad.";
		}
		
		float subtotal = carritoService.updateCantidad(productoId, cantidad, usuario);
		
		return String.valueOf(subtotal);
	}
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@PostMapping("/carrito/remove/{pid}")
	public String removeProductFromCart(@PathVariable("pid") Integer productoId, 
				@AuthenticationPrincipal Authentication authentication) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName() == null || authentication instanceof AnonymousAuthenticationToken) {
			return "Debes iniciar sesión para remover un producto";
		}
		Usuario usuario = usuarioService.obtenerUsuario(auth.getName());
		
		if (usuario == null) {
			return "Debes iniciar sesión para remover un producto";
		}
		
		carritoService.removeProducto(productoId, usuario);
		
		return "El producto fue removido de tu carrito de compras.";
	}
}
