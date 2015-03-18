package com.documentos.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FacDestinatarioPK implements Serializable
{
	@Column(name="\"ambiente\"")
	private int ambiente;
	
	@Column(name="\"Ruc\"")
	private String ruc;
	
	@Column(name="\"codEstablecimiento\"")
	private String codEstablecimiento;
	
	@Column(name="\"CodPuntEmision\"")
	private String codPuntEmision;
	
	@Column(name="\"secuencial\"")
	private String secuencial;
	
	@Column(name="\"identificacion\"")
	private String identificacion;
	
	//private String codigoDocumento;
	
	
	public int getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(int ambiente) {
		this.ambiente = ambiente;
	}
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getCodEstablecimiento() {
		return codEstablecimiento;
	}
	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}
	
	public String getCodPuntEmision() {
		return codPuntEmision;
	}
	public void setCodPuntEmision(String codPuntEmision) {
		this.codPuntEmision = codPuntEmision;
	}
	
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	
	public String getIdentificacionDestinatario() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	/*public String getCodigoDocumento() {
		return codigoDocumento;
	}
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}*/
	

}
