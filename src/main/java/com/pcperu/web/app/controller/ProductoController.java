package com.pcperu.web.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.service.ProductoService;

@Controller
@RequestMapping("/admin")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	
	@GetMapping("/productos")
	public String list(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			List<Producto> productos = productoService.list(auth.getName());
			model.addAttribute("productos", productos);
		}
		return "/producto/lista";
	}
}
