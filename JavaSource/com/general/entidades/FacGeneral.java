package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the fac_general database table.
 * 
 */
@Entity
@Table(name="fac_general")
public class FacGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"idGeneral\"")
	private Integer idGeneral;

	@Column(name="\"codTabla\"")
	private String codTabla;

	@Column(name="\"codUnico\"")
	private String codUnico;

	private String descripcion;

	@Column(name="\"isActive\"")
	private String isActive;

	private Integer porcentaje;
	
    public FacGeneral() {
    }
    
	public Integer getIdGeneral() {
		return this.idGeneral;
	}
	
	public void setIdGeneral(Integer idGeneral) {
		this.idGeneral = idGeneral;
	}
	
	public String getCodTabla() {
		return this.codTabla;
	}
	
	public void setCodTabla(String codTabla) {
		this.codTabla = codTabla;
	}
	
	public String getCodUnico() {
		return this.codUnico;
	}

	public void setCodUnico(String codUnico) {
		this.codUnico = codUnico;
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

	public Integer getPorcentaje() {
		return this.porcentaje;
	}
	
	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}