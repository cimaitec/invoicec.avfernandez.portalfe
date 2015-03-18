package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_cab_documentos_adicional database table.
 * 
 */
@Entity
@Table(name="fac_cab_documentos_adicional")
public class FacCabDocumentosAdicional implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacCabDocumentosAdicionalPK id;

	@Column(name="\"CodigoDocumento\"")
	private String codigoDocumento;

	private String descripcion;

	private String nombre;

    public FacCabDocumentosAdicional() {
    }

	public FacCabDocumentosAdicionalPK getId() {
		return this.id;
	}

	public void setId(FacCabDocumentosAdicionalPK id) {
		this.id = id;
	}
	
	public String getCodigoDocumento() {
		return this.codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}