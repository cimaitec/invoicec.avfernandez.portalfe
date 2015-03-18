package com.usuario.entidades;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the fac_usuarios database table.
 * 
 */
@Entity
@Table(name="fac_usuarios")
public class FacUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacUsuarioPK id;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"Nombre\"")
	private String nombre;

	@Column(name="\"Password\"")
	private String password;

	@Column(name="\"RucEmpresa\"")
	private String rucEmpresa;

	@Column(name="\"TipoUsuario\"")
	private String tipoUsuario;

	@Column(name="\"loginErroneo\"")
	private int loginErroneo;
	

	@Column(name="\"autenticado\"")
	private String autenticado;
	
	public String getAutenticado() {
		return autenticado;
	}

	public void setAutenticado(String autenticado) {
		this.autenticado = autenticado;
	}

	public int getLoginErroneo() {
		return loginErroneo;
	}

	public void setLoginErroneo(int loginErroneo) {
		this.loginErroneo = loginErroneo;
	}
	
    public FacUsuario() {
    }

	public FacUsuarioPK getId() {
		return this.id;
	}

	public void setId(FacUsuarioPK id) {
		this.id = id;
	}
	
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRucEmpresa() {
		return this.rucEmpresa;
	}

	public void setRucEmpresa(String rucEmpresa) {
		this.rucEmpresa = rucEmpresa;
	}

	public String getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}