package com.pcperu.web.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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
	private String descripcion;
	
	@NotEmpty
	private String estado;
	
	@Min(value = 0)
	private int stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuarioId;

	public Producto(@NotEmpty String nombre, @Min(0) float precioVenta, String foto, @NotEmpty String descripcion,
			@NotEmpty String estado, @Min(0) int stock, Usuario usuarioId) {
		this.nombre = nombre;
		this.precioVenta = precioVenta;
		this.foto = foto;
		this.descripcion = descripcion;
		this.estado = estado;
		this.stock = stock;
		this.usuarioId = usuarioId;
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
	
}
