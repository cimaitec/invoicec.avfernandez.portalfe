package com.menu.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_opcion_menu database table.
 * 
 */
@Entity
@Table(name="fac_opcion_menu")
public class FacOpcionMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CodOpcionMenu\"")
	private String codOpcionMenu;

	@Column(name="\"codOpcionMenuPadre\"")
	private String codOpcionMenuPadre;

	@Column(name="\"Descripcion\"")
	private String descripcion;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"ParamDefault\"")
	private String paramDefault;

	@Column(name="\"UrlPages\"")
	private String urlPages;

    public FacOpcionMenu() {
    }

	public String getCodOpcionMenu() {
		return this.codOpcionMenu;
	}

	public void setCodOpcionMenu(String codOpcionMenu) {
		this.codOpcionMenu = codOpcionMenu;
	}

	public String getCodOpcionMenuPadre() {
		return this.codOpcionMenuPadre;
	}

	public void setCodOpcionMenuPadre(String codOpcionMenuPadre) {
		this.codOpcionMenuPadre = codOpcionMenuPadre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getParamDefault() {
		return this.paramDefault;
	}

	public void setParamDefault(String paramDefault) {
		this.paramDefault = paramDefault;
	}

	public String getUrlPages() {
		return this.urlPages;
	}

	public void setUrlPages(String urlPages) {
		this.urlPages = urlPages;
	}

}