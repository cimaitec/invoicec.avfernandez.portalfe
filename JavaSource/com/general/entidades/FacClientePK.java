package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_clientes database table.
 * 
 */
@Embeddable
public class FacClientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"RucCliente\"")
	private String rucCliente;

    public FacClientePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacClientePK)) {
			return false;
		}
		FacClientePK castOther = (FacClientePK)other;
		return 
			this.ruc.equals(castOther.ruc)
			&& this.rucCliente.equals(castOther.rucCliente);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruc.hashCode();
		hash = hash * prime + this.rucCliente.hashCode();
		
		return hash;
    }
}