package com.pcperu.web.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String nombre;
	
	@Min(value = 0)
	@Column(name = "precio_venta")
	private float precioVenta;
	private String foto;
	
	@NotEmpty
	@Type(type = "text")
	private String descripcion;
	
	@NotNull
	private String estado;
	
	@NotNull
	@Min(value = 0)
	private int stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuarioId;

	@NotNull
	@NotEmpty
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "producto_categoria", joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private Set<Categoria> categorias;

	public Producto(@NotEmpty String nombre, @Min(0) float precioVenta, String foto, @NotEmpty String descripcion,
			@NotNull String estado, @NotNull @Min(0) int stock, Usuario usuarioId,
			@NotNull @NotEmpty Set<Categoria> categorias) {
		super();
		this.nombre = nombre;
		this.precioVenta = precioVenta;
		this.foto = foto;
		this.descripcion = descripcion;
		this.estado = estado;
		this.stock = stock;
		this.usuarioId = usuarioId;
		this.categorias = categorias;
	}

	public Producto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
