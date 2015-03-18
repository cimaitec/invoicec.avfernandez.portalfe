package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_punto_emision database table.
 * 
 */
@Embeddable
public class FacPuntoEmisionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"CodEstablecimiento\"")
	private String codEstablecimiento;

	@Column(name="\"CodPuntEmision\"")
	private String codPuntEmision;

	@Column(name="\"TipoDocumento\"")
	private String tipoDocumento;

	@Column(name="\"Secuencial\"")
	private Integer secuencial;

    public FacPuntoEmisionPK() {
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
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Integer getSecuencial() {
		return this.secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacPuntoEmisionPK)) {
			return false;
		}
		FacPuntoEmisionPK castOther = (FacPuntoEmisionPK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.codEstablecimiento.equals(castOther.codEstablecimiento)
			&& this.codPuntEmision.equals(castOther.codPuntEmision)
			&& this.tipoDocumento.equals(castOther.tipoDocumento)
			&& this.secuencial.equals(castOther.secuencial);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.codEstablecimiento.hashCode();
		hash = hash * prime + this.codPuntEmision.hashCode();
		hash = hash * prime + this.tipoDocumento.hashCode();
		hash = hash * prime + this.secuencial.hashCode();
		
		return hash;
    }
}