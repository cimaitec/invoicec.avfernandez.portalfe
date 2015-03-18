package com.usuario.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_usuarios_roles database table.
 * 
 */
@Entity
@Table(name="fac_usuarios_roles")
public class FacUsuariosRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacUsuariosRolePK id;

	@Column(name="\"isActive\"")
	private String isActive;

    public FacUsuariosRole() {
    }

	public FacUsuariosRolePK getId() {
		return this.id;
	}

	public void setId(FacUsuariosRolePK id) {
		this.id = id;
	}
	
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}