package com.menu.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_roles_opcion_menu database table.
 * 
 */
@Embeddable
public class FacRolesOpcionMenuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"codRol\"")
	private String codRol;

	@Column(name="\"CodOpcionMenu\"")
	private String codOpcionMenu;

    public FacRolesOpcionMenuPK() {
    }
	public String getCodRol() {
		return this.codRol;
	}
	public void setCodRol(String codRol) {
		this.codRol = codRol;
	}
	public String getCodOpcionMenu() {
		return this.codOpcionMenu;
	}
	public void setCodOpcionMenu(String codOpcionMenu) {
		this.codOpcionMenu = codOpcionMenu;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacRolesOpcionMenuPK)) {
			return false;
		}
		FacRolesOpcionMenuPK castOther = (FacRolesOpcionMenuPK)other;
		return 
			this.codRol.equals(castOther.codRol)
			&& this.codOpcionMenu.equals(castOther.codOpcionMenu);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codRol.hashCode();
		hash = hash * prime + this.codOpcionMenu.hashCode();
		
		return hash;
    }
}