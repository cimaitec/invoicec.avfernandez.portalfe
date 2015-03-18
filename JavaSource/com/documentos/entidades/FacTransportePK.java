package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_transportes database table.
 * 
 */
@Embeddable
public class FacTransportePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"RucCliente\"")
	private String rucCliente;

	@Column(name="\"Placa\"")
	private String placa;

    public FacTransportePK() {
    }
	public String getRuc() {
		return this.ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRucCliente() {
		return this.rucCliente;
	}
	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}
	public String getPlaca() {
		return this.placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacTransportePK)) {
			return false;
		}
		FacTransportePK castOther = (FacTransportePK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.rucCliente.equals(castOther.rucCliente)
			&& this.placa.equals(castOther.placa);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.rucCliente.hashCode();
		hash = hash * prime + this.placa.hashCode();
		
		return hash;
    }
}