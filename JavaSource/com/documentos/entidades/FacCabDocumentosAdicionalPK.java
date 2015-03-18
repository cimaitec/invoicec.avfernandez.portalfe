package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_cab_documentos_adicional database table.
 * 
 */
@Embeddable
public class FacCabDocumentosAdicionalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"CodEstablecimiento\"")
	private String codEstablecimiento;

	@Column(name="\"CodPuntoEmision\"")
	private String codPuntoEmision;

	private Integer secuencial;
	
	private Integer ambiente;

    public FacCabDocumentosAdicionalPK() {
    }
	public String getRuc() {
		return this.ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCodEstablecimiento() {
		return this.codEstablecimiento;
	}
	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}
	public String getCodPuntoEmision() {
		return this.codPuntoEmision;
	}
	public void setCodPuntoEmision(String codPuntoEmision) {
		this.codPuntoEmision = codPuntoEmision;
	}
	public Integer getSecuencial() {
		return this.secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

	
	public Integer getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(Integer ambiente) {
		this.ambiente = ambiente;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacCabDocumentosAdicionalPK)) {
			return false;
		}
		FacCabDocumentosAdicionalPK castOther = (FacCabDocumentosAdicionalPK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.codEstablecimiento.equals(castOther.codEstablecimiento)
			&& this.codPuntoEmision.equals(castOther.codPuntoEmision)
			&& this.secuencial.equals(castOther.secuencial)
			&& this.ambiente.equals(castOther.ambiente);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.codEstablecimiento.hashCode();
		hash = hash * prime + this.codPuntoEmision.hashCode();
		hash = hash * prime + this.secuencial.hashCode();
		hash = hash * prime + this.ambiente.hashCode();
		
		return hash;
    }
}