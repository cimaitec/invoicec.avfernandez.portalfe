package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_det_adicional database table.
 * 
 */
@Entity
@Table(name="fac_det_adicional")
public class FacDetAdicional implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacDetAdicionalPK id;

	private String nombre;

	private String valor;

    public FacDetAdicional() {
    }

	public FacDetAdicionalPK getId() {
		return this.id;
	}

	public void setId(FacDetAdicionalPK id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}