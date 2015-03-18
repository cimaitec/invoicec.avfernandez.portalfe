package com.usuario.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_roles database table.
 * 
 */
@Entity
@Table(name="fac_roles")
public class FacRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CodRol\"")
	private String codRol;

	@Column(name="\"Descripcion\"")
	private String descripcion;

	@Column(name="\"isActive\"")
	private String isActive;

    public FacRole() {
    }

	public String getCodRol() {
		return this.codRol;
	}

	public void setCodRol(String codRol) {
		this.codRol = codRol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}