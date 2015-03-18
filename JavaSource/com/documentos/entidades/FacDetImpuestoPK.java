package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_det_impuestos database table.
 * 
 */
@Embeddable
public class FacDetImpuestoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"CodEstablecimiento\"")
	private String codEstablecimiento;

	@Column(name="\"CodPuntEmision\"")
	private String codPuntEmision;

	private String secuencial;

	@Column(name="\"codImpuesto\"")
	private Integer codImpuesto;

	@Column(name="\"detSecuencial\"")
	private Integer detSecuencial;

	@Column(name="\"CodigoDocumento\"")
	private String codigoDocumento;

	@Column(name="\"secuencialDetalle\"")
	private int secuencialDetalle;

	private Integer ambiente;
	
    public FacDetImpuestoPK() {
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
	public Integer getCodImpuesto() {
		return this.codImpuesto;
	}
	public void setCodImpuesto(Integer codImpuesto) {
		this.codImpuesto = codImpuesto;
	}
	public Integer getDetSecuencial() {
		return this.detSecuencial;
	}
	public void setDetSecuencial(Integer detSecuencial) {
		this.detSecuencial = detSecuencial;
	}
	public String getCodigoDocumento() {
		return this.codigoDocumento;
	}
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}
	public int getSecuencialDetalle() {
		return this.secuencialDetalle;
	}
	public void setSecuencialDetalle(int secuencialDetalle) {
		this.secuencialDetalle = secuencialDetalle;
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
		if (!(other instanceof FacDetImpuestoPK)) {
			return false;
		}
		FacDetImpuestoPK castOther = (FacDetImpuestoPK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.codEstablecimiento.equals(castOther.codEstablecimiento)
			&& this.codPuntEmision.equals(castOther.codPuntEmision)
			&& this.secuencial.equals(castOther.secuencial)
			&& this.codImpuesto.equals(castOther.codImpuesto)
			&& this.detSecuencial.equals(castOther.detSecuencial)
			&& this.codigoDocumento.equals(castOther.codigoDocumento)
			&& this.secuencialDetalle == castOther.secuencialDetalle
			&& this.ambiente == castOther.ambiente;

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.codEstablecimiento.hashCode();
		hash = hash * prime + this.codPuntEmision.hashCode();
		hash = hash * prime + this.secuencial.hashCode();
		hash = hash * prime + this.codImpuesto.hashCode();
		hash = hash * prime + this.detSecuencial.hashCode();
		hash = hash * prime + this.codigoDocumento.hashCode();
		hash = hash * prime + this.secuencialDetalle;
		hash = hash * prime + this.ambiente;
		return hash;
    }
}