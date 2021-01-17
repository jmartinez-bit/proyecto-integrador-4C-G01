package com.pcperu.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.service.ProductoService;
import com.pcperu.web.app.util.paginator.PageRender;

@Controller
public class CatalogoController {
	
	@Autowired
	ProductoService productoService;
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@GetMapping("/productos")
	public String catalogoProductos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 18);
		Page<Producto> productos = productoService.list(pageRequest);
		PageRender<Producto> pageRender = new PageRender<>("/productos/", productos);
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "/producto/lista";
	}
	
	@PreAuthorize("!hasAuthority('ADMIN')")
	@GetMapping("/productos/{id}")
	public String productoDetalle(@PathVariable("id") int id, Model model) {
		Producto producto = productoService.getOne(id).get();
		model.addAttribute("producto", producto);
		return "/producto/detalle";
	}

}
