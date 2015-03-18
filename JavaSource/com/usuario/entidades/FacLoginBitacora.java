package com.usuario.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the fac_usuarios database table.
 * 
 */
@Entity
@Table(name="fac_login_bitacora")
public class FacLoginBitacora implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="ruc")
	private String ruc;  
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="fecha_inicio_sesion")
	private Date fechaInicioSesion;
	
	@Column(name="fecha_fin_sesion")
	private Date fechaFinSesion;
	
	@Column(name="ip_cliente")
	private String ipCliente;
	
	public FacLoginBitacora()
	{}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Date getFechaInicioSesion() {
		return fechaInicioSesion;
	}
	public void setFechaInicioSesion(Date fechaInicioSesion) {
		this.fechaInicioSesion = fechaInicioSesion;
	}
	
	public Date getFechaFinSesion() {
		return fechaFinSesion;
	}
	public void setFechaFinSesion(Date fechaFinSesion) {
		this.fechaFinSesion = fechaFinSesion;
	}
	
	public String getIpCliente() {
		return ipCliente;
	}
	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}
	
}