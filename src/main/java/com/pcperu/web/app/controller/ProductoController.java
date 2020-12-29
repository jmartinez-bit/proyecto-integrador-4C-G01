package com.pcperu.web.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcperu.web.app.model.Categoria;
import com.pcperu.web.app.model.Producto;
import com.pcperu.web.app.service.CategoriaService;
import com.pcperu.web.app.service.IUploadFileService;
import com.pcperu.web.app.service.ProductoService;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping({"/", ""})
	public String list(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			List<Producto> productos = productoService.list(auth.getName());
			model.addAttribute("productos", productos);
		}
		return "/producto/lista";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		Producto producto = new Producto();
		List<Categoria> categorias = categoriaService.list();
		model.addAttribute("categoriasLista", categorias);
		model.addAttribute("producto", producto);
		return "/producto/nuevo";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/guardar")
	public String crearProducto(@Valid Producto producto, BindingResult result, Model model, RedirectAttributes flash,@RequestParam("file") MultipartFile foto) {
		
		List<Categoria> categorias2 = categoriaService.list();
		model.addAttribute("categoriasLista", categorias2);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(result.hasErrors()) {
			model.addAttribute("error", "Por favor corrige los siguientes errores");
			return "/producto/nuevo";
		}
		
		if(auth != null) {
			if(productoService.existsByNombre(producto.getNombre(), auth.getName())) { 
				model.addAttribute("error", "Hay un producto registrado con el mismo nombre"); 
				return "/producto/nuevo";
			} 
		}
		
		if(!foto.isEmpty()) {
			
			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFileName + "'");
			producto.setFoto(uniqueFileName);
		}
		
		productoService.save(producto, auth.getName());
		return "redirect:/admin/productos";
	}
	
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> vistaFoto(@PathVariable String filename) {
		
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") int id, Map<String, Object> model, RedirectAttributes flash) {
		List<Categoria> categorias = categoriaService.list();
		model.put("categoriasLista", categorias);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {			
			if(!productoService.existsById(id, auth.getName())) {
				flash.addFlashAttribute("error", "El producto no existe");
				return "redirect:/admin/productos";
			}
		}
		Producto producto = productoService.getOne(id).get();
		model.put("producto", producto);
		return "/producto/detalle";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") int id, RedirectAttributes flash, Model model) {
		List<Categoria> categorias = categoriaService.list();
		model.addAttribute("categoriasLista", categorias);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {			
			if(!productoService.existsById(id, auth.getName())) {
				flash.addFlashAttribute("error", "El producto no existe");
				return "redirect:/admin/productos";
			}
		}
		Producto producto = productoService.getOne(id).get();
		model.addAttribute("producto", producto);
		return "/producto/editar";
	}
	
	@PostMapping("/actualizar")
	public String actualizarProducto(@Valid Producto producto, BindingResult result, Model model, RedirectAttributes flash,@RequestParam("file") MultipartFile foto) {
		List<Categoria> categorias2 = categoriaService.list();
		model.addAttribute("categoriasLista", categorias2);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(result.hasErrors()) {
			
			model.addAttribute("error", "Por favor corrige los siguientes errores");
			return "/producto/editar";
		}
		
		if(auth != null) {
			if(!productoService.getOne(producto.getId()).get().getNombre().equals(producto.getNombre())) {
				if(productoService.existsByNombre(producto.getNombre(), auth.getName())) { 
					model.addAttribute("error", "Hay un producto registrado con el mismo nombre"); 
					return "/producto/editar";
				}
			}
		}
		
		if(!foto.isEmpty()) {
			Producto productoFoto = productoService.getOne(producto.getId()).get();
			
			if(productoFoto.getId() != 0 
					&& productoFoto.getId() > 0 
					&& productoFoto.getFoto() != null 
					&& productoFoto.getFoto().length() > 0) {
				
				uploadFileService.delete(productoFoto.getFoto());
			}
			
			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFileName + "'");
			producto.setFoto(uniqueFileName);
		}
		 
		productoService.save(producto, auth.getName());
		return "redirect:/admin/productos/detalle/"+producto.getId();
	}
	
	@GetMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") int id, RedirectAttributes flash) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {			
			if(!productoService.existsById(id, auth.getName())) {
				flash.addFlashAttribute("error", "El producto no existe");
				return "redirect:/admin/productos";
			}
		}
		Producto producto = productoService.getOne(id).get();
		productoService.delete(id);
		
		if (producto.getFoto() != null) {
			if(uploadFileService.delete(producto.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + producto.getFoto() + " eliminada con exito!");
			}
		}
		return "redirect:/admin/productos";
	}
}
