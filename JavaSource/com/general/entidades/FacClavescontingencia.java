package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fac_clavescontingencia database table.
 * 
 */
@Entity
@Table(name="fac_clavescontingencia")
public class FacClavescontingencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idclavecontingencia;

	private String clave;

	private String estado;

    @Temporal( TemporalType.DATE)
	private Date fechauso;

	private String ruc;

	private String tipo;

    public FacClavescontingencia() {
    }

	public Integer getIdclavecontingencia() {
		return this.idclavecontingencia;
	}

	public void setIdclavecontingencia(Integer idclavecontingencia) {
		this.idclavecontingencia = idclavecontingencia;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechauso() {
		return this.fechauso;
	}

	public void setFechauso(Date fechauso) {
		this.fechauso = fechauso;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}