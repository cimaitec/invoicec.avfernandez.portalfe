package com.usuario.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_usuarios_establecimiento database table.
 * 
 */
@Entity
@Table(name="fac_usuarios_establecimiento")
public class FacUsuariosEstablecimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacUsuariosEstablecimientoPK id;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"TipoAcceso\"")
	private String tipoAcceso;

    public FacUsuariosEstablecimiento() {
    }

	public FacUsuariosEstablecimientoPK getId() {
		return this.id;
	}

	public void setId(FacUsuariosEstablecimientoPK id) {
		this.id = id;
	}
	
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getTipoAcceso() {
		return this.tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

}