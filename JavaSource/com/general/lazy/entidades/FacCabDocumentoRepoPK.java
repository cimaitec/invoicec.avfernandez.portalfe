package com.general.lazy.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_cab_documentos database table.
 * 
 */
@Embeddable
public class FacCabDocumentoRepoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"CodEstablecimiento\"")
	private String codEstablecimiento;

	@Column(name="\"CodPuntEmision\"")
	private String codPuntEmision;

	private String secuencial;

	@Column(name="\"CodigoDocumento\"")
	private String codigoDocumento;
	
	private Integer ambiente;
	
    public FacCabDocumentoRepoPK() {
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
	public String getCodPuntEmision() {
		return this.codPuntEmision;
	}
	public void setCodPuntEmision(String codPuntEmision) {
		this.codPuntEmision = codPuntEmision;
	}
	public String getSecuencial() {
		return this.secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public String getCodigoDocumento() {
		return this.codigoDocumento;
	}
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
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
		if (!(other instanceof FacCabDocumentoRepoPK)) {
			return false;
		}
		FacCabDocumentoRepoPK castOther = (FacCabDocumentoRepoPK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.codEstablecimiento.equals(castOther.codEstablecimiento)
			&& this.codPuntEmision.equals(castOther.codPuntEmision)
			&& this.secuencial.equals(castOther.secuencial)
			&& this.codigoDocumento.equals(castOther.codigoDocumento)
			&& this.ambiente.equals(castOther.ambiente);
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.codEstablecimiento.hashCode();
		hash = hash * prime + this.codPuntEmision.hashCode();
		hash = hash * prime + this.secuencial.hashCode();
		hash = hash * prime + this.codigoDocumento.hashCode();
		hash = hash * prime + this.ambiente.hashCode();
		return hash;
    }
	
}