package com.usuario.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_usuarios_roles database table.
 * 
 */
@Embeddable
public class FacUsuariosRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"CodUsuario\"")
	private String codUsuario;

	@Column(name="\"CodRol\"")
	private String codRol;

	@Column(name="\"Ruc\"")
	private String ruc;

    public FacUsuariosRolePK() {
    }
	public String getCodUsuario() {
		return this.codUsuario;
	}
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getCodRol() {
		return this.codRol;
	}
	public void setCodRol(String codRol) {
		this.codRol = codRol;
	}
	public String getRuc() {
		return this.ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacUsuariosRolePK)) {
			return false;
		}
		FacUsuariosRolePK castOther = (FacUsuariosRolePK)other;
		return 
			this.codUsuario.equals(castOther.codUsuario)
			&& this.codRol.equals(castOther.codRol)
			&& this.ruc.equals(castOther.ruc);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codUsuario.hashCode();
		hash = hash * prime + this.codRol.hashCode();
		hash = hash * prime + this.ruc.hashCode();
		
		return hash;
    }
}