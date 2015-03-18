package com.menu.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_roles_opcion_menu database table.
 * 
 */
@Entity
@Table(name="fac_roles_opcion_menu")
public class FacRolesOpcionMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacRolesOpcionMenuPK id;

    public FacRolesOpcionMenu() {
    }

	public FacRolesOpcionMenuPK getId() {
		return this.id;
	}

	public void setId(FacRolesOpcionMenuPK id) {
		this.id = id;
	}
	
}