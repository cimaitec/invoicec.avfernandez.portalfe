package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_tipos_documentos database table.
 * 
 */
@Entity
@Table(name="fac_tipos_documentos")
public class FacTiposDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="\"idDocumento\"")
	private String idDocumento;

	private String descripcion;

	@Column(name="\"formatoTexto\"")
	private String formatoTexto;

	@Column(name="\"formatoXML\"")
	private String formatoXML;

	@Column(name="\"isActive\"")
	private String isActive;

    public FacTiposDocumento() {
    }

	public String getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFormatoTexto() {
		return this.formatoTexto;
	}

	public void setFormatoTexto(String formatoTexto) {
		this.formatoTexto = formatoTexto;
	}

	public String getFormatoXML() {
		return this.formatoXML;
	}

	public void setFormatoXML(String formatoXML) {
		this.formatoXML = formatoXML;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}