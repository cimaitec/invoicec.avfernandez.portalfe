package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_transportes database table.
 * 
 */
@Entity
@Table(name="fac_transportes")
public class FacTransporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacTransportePK id;

	@Column(name="\"Chasis\"")
	private String chasis;

	@Column(name="\"Conductor\"")
	private String conductor;

	@Column(name="\"Descripcion\"")
	private String descripcion;

	@Column(name="\"Marca\"")
	private String marca;

	@Column(name="\"Modelo\"")
	private String modelo;

    public FacTransporte() {
    }

	public FacTransportePK getId() {
		return this.id;
	}

	public void setId(FacTransportePK id) {
		this.id = id;
	}
	
	public String getChasis() {
		return this.chasis;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public String getConductor() {
		return this.conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

}