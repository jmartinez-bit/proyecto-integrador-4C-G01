package com.pcperu.web.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pcperu.web.app.model.Usuario;
import com.pcperu.web.app.service.UsuarioService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "register";
	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid Usuario usuario, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("successMessage", "Por favor corrige los errores del formulario");
		}else if(usuarioService.usuarioExiste(usuario)){
			model.addAttribute("successMessage", "Usuario ya existe");
		}else {
			usuarioService.registrarUsuario(usuario);
			model.addAttribute("successMessage", "Usuario registrado satisfactoriamente");
		}
		return "register";
	}
	
}
