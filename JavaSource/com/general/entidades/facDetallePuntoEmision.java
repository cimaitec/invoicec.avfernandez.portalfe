package com.general.entidades;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="")
public class facDetallePuntoEmision {
	@Id
	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"codEstablecimiento\"")
	private String CodEstablecimiento;

	@Column(name="\"codPuntoEmision\"")
	private String CodPuntoEmision;

	@Column(name="\"codTipoDocumento\"")
	private String CodTipoDocumento;

	@Column(name="\"isActive\"")
	private String IsActive;
	
	@Column(name="\"TipoAmbiente\"")
	private String tipoAmbiente;
	
	@Column(name="\"Secuencial\"")
	private int secuencial;
	
	@Column(name="\"FormaEmision\"")
	private String formaEmision;
	
	@Column(name="\"Accion\"")
	private String accion;
	
	public facDetallePuntoEmision(String ruc, String CodEstablecimiento, String CodPuntoEmision, String CodTipoDocumento ,String IsActive, String tipoAmbiente,
			int secuencial, String formaEmision, String accion){
		this.ruc = ruc;
		this.CodEstablecimiento = CodEstablecimiento;
		this.CodPuntoEmision = CodPuntoEmision;
		this.CodTipoDocumento = CodTipoDocumento;
		this.IsActive = IsActive;
		this.tipoAmbiente = tipoAmbiente;
		this.secuencial = secuencial;
		this.formaEmision = formaEmision;
		this.accion = accion;
	}
	
	public facDetallePuntoEmision(){
		
	}
	
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getCodEstablecimiento() {
		return CodEstablecimiento;
	}

	public void setCodEstablecimiento(String codEstablecimiento) {
		CodEstablecimiento = codEstablecimiento;
	}

	public String getCodPuntoEmision() {
		return CodPuntoEmision;
	}

	public void setCodPuntoEmision(String codPuntoEmision) {
		CodPuntoEmision = codPuntoEmision;
	}

	public String getCodTipoDocumento() {
		return CodTipoDocumento;
	}

	public void setCodTipoDocumento(String codTipoDocumento) {
		CodTipoDocumento = codTipoDocumento;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	
	public String getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(String tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}

	public int getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(int secuencial) {
		this.secuencial = secuencial;
	}

	public String getFormaEmision() {
		return formaEmision;
	}

	public void setFormaEmision(String formaEmision) {
		this.formaEmision = formaEmision;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
}
