package com.documentos.entidades;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="fac_det_guia_remision")
public class FacDetGuiaRemision
{
	@Id @EmbeddedId private FacDetGuiaRemisionPK id;
	
	@Column(name="\"codigoInterno\"")	private String codigoInterno;
	@Column(name="\"codigoAdicional\"")	private String codigoAdicional;
	@Column(name="\"descripcion\"")		private String descripcion;
	@Column(name="\"cantidad\"")		private int cantidad;
	@Column(name="\"detallesAdicionales\"") private String detallesAdicionales;
	
	public FacDetGuiaRemisionPK getId() {
		return id;
	}
	public void setId(FacDetGuiaRemisionPK id) {
		this.id = id;
	}
	
	public String getCodigoInterno() {
		return codigoInterno;
	}
	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}
	public String getCodigoAdicional() {
		return codigoAdicional;
	}
	public void setCodigoAdicional(String codigoAdicional) {
		this.codigoAdicional = codigoAdicional;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDetallesAdicionales() {
		return detallesAdicionales;
	}
	public void setDetallesAdicionales(String detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}

}
