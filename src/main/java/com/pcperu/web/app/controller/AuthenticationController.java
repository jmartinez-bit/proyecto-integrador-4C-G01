package com.pcperu.web.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.service.CategoriaService;
import com.pcperu.web.app.service.ProductoService;
import com.pcperu.web.app.service.UsuarioService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProductoService productoService;

	@PreAuthorize("!isAuthenticated()")
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PreAuthorize("!isAuthenticated()")
	@GetMapping("/register")
	public String register(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "register";
	}
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("productoFavoritos", productoService.getProductosFavoritos());
		model.addAttribute("productoExtra", productoService.getProductosExtra());
		return "home";
	}
	
	@GetMapping("/user")
	public String user() {
		return "redirect:/";
	}
	
	@GetMapping("/admin")
	public String adminHome() {
		return "redirect:/admin/productos";
	}

	@PreAuthorize("!isAuthenticated()")
	@PostMapping("/register")
	public String registerUser(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		
		if(usuario.getUsername().isBlank()){
			model.addAttribute("error", "El nombre de usuario es requerido");
		}else if(result.hasErrors()) {
			model.addAttribute("successMessage", "Por favor corrige los errores del formulario");
		}else if(usuarioService.usuarioExiste(usuario)){
			model.addAttribute("successMessage", "Usuario ya existe");
		}else {
			usuarioService.registrarUsuario(usuario);
			flash.addFlashAttribute("successMessage", "Usuario registrado satisfactoriamente");
			return "redirect:/login";
		}
		return "register";
	}
	
	@PreAuthorize("!isAuthenticated()")
	@GetMapping("/register2")
	public String registerAdmin(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "register2";
	}

	@PreAuthorize("!isAuthenticated()")
	@PostMapping("/register2")
	public String registerAdmin(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		if(usuario.getNombres().isBlank()) {
			model.addAttribute("errorNombre", "El nombre es requerido");
		}
		if(usuario.getApellidos().isBlank()) {
			model.addAttribute("errorApellido", "El apellido es requerido");
		}
		if(usuario.getTelefono().isBlank()) {
			model.addAttribute("errorTelefono", "El telefono es requerido");
		}
		if(usuario.getDireccion().isBlank()) {
			model.addAttribute("errorDireccion", "La direccion es requerida");
		}
		if(result.hasErrors()) {
			model.addAttribute("successMessage", "Por favor corrige los errores del formulario");
		}else if(usuarioService.usuarioExiste(usuario)){
			model.addAttribute("successMessage", "Usuario ya existe");
		}else {
			if(!usuario.getNombres().isBlank() && !usuario.getApellidos().isBlank() && !usuario.getTelefono().isBlank() && !usuario.getDireccion().isBlank()) {				
				usuarioService.registrarAdmin(usuario);
				flash.addFlashAttribute("successMessage", "Cuenta registrada satisfactoriamente, Inicia Sesión!");
				return "redirect:/login";
			}
		}
		return "register2";
	}
	
}
