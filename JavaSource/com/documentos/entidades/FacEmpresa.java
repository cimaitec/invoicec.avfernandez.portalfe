package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_empresa database table.
 * 
 */
@Entity
@Table(name="fac_empresa")
public class FacEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"Ruc\"")
	private String ruc;

	@Column(name="\"ColorEmpresa\"")
	private String colorEmpresa;

	@Column(name="\"ContribEspecial\"")
	private Integer contribEspecial;

	@Column(name="\"CorreoRecepcion\"")
	private String correoRecepcion;

	@Column(name="\"DireccionMatriz\"")
	private String direccionMatriz;

	@Column(name="\"emailEnvio\"")
	private String emailEnvio;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"marcaAgua\"")
	private String marcaAgua;

	@Column(name="\"ObligContabilidad\"")
	private String obligContabilidad;

	@Column(name="\"passSMTP\"")
	private String passSMTP;

	@Column(name="\"PathCompAutorizados\"")
	private String pathCompAutorizados;

	@Column(name="\"PathCompFirmados\"")
	private String pathCompFirmados;

	@Column(name="\"PathCompGenerados\"")
	private String pathCompGenerados;

	@Column(name="\"PathCompNoAutorizados\"")
	private String pathCompNoAutorizados;

	@Column(name="\"PathCompRecepcion\"")
	private String pathCompRecepcion;

	@Column(name="\"PathFirma\"")
	private String pathFirma;

	@Column(name="\"PathInfoRecibida\"")
	private String pathInfoRecibida;

	@Column(name="\"PathLogoEmpresa\"")
	private String pathLogoEmpresa;

	@Column(name="\"pathMarcaAgua\"")
	private String pathMarcaAgua;

	@Column(name="\"puertoSMTP\"")
	private Integer puertoSMTP;

	@Column(name="\"RazonComercial\"")
	private String razonComercial;

	@Column(name="\"RazonSocial\"")
	private String razonSocial;

	@Column(name="\"servidorSMTP\"")
	private String servidorSMTP;

	@Column(name="\"sslSMTP\"")
	private Boolean sslSMTP;

	@Column(name="\"UrlWebServices\"")
	private String urlWebServices;

	@Column(name="\"userSMTP\"")
	private String userSMTP;

	@Column(name="\"PathCompContingencia\"")
	private String pathCompContingencia;
	
	@Column(name="\"FechaResolucionContribEspecial\"")
	private String fechaResolucionContribEspecial;	
	
	@Column(name="\"PassFirma\"")
	private String PassFirma;	
	
	@Column(name="\"TypeFirma\"")
	private String TypeFirma;	
	
	@Column(name="\"PathXSD\"")
	private String PathXSD;	
	
	@Column(name="\"PathJasper\"")
	private String PathJasper;	
	
    public FacEmpresa() {
    }

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getColorEmpresa() {
		return this.colorEmpresa;
	}

	public void setColorEmpresa(String colorEmpresa) {
		this.colorEmpresa = colorEmpresa;
	}

	public Integer getContribEspecial() {
		return this.contribEspecial;
	}

	public void setContribEspecial(Integer contribEspecial) {
		this.contribEspecial = contribEspecial;
	}

	public String getCorreoRecepcion() {
		return this.correoRecepcion;
	}

	public void setCorreoRecepcion(String correoRecepcion) {
		this.correoRecepcion = correoRecepcion;
	}

	public String getDireccionMatriz() {
		return this.direccionMatriz;
	}

	public void setDireccionMatriz(String direccionMatriz) {
		this.direccionMatriz = direccionMatriz;
	}

	public String getEmailEnvio() {
		return this.emailEnvio;
	}

	public void setEmailEnvio(String emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMarcaAgua() {
		return this.marcaAgua;
	}

	public void setMarcaAgua(String marcaAgua) {
		this.marcaAgua = marcaAgua;
	}

	public String getObligContabilidad() {
		return this.obligContabilidad;
	}

	public void setObligContabilidad(String obligContabilidad) {
		this.obligContabilidad = obligContabilidad;
	}

	public String getPassSMTP() {
		return this.passSMTP;
	}

	public void setPassSMTP(String passSMTP) {
		this.passSMTP = passSMTP;
	}

	public String getPathCompAutorizados() {
		return this.pathCompAutorizados;
	}

	public void setPathCompAutorizados(String pathCompAutorizados) {
		this.pathCompAutorizados = pathCompAutorizados;
	}

	public String getPathCompFirmados() {
		return this.pathCompFirmados;
	}

	public void setPathCompFirmados(String pathCompFirmados) {
		this.pathCompFirmados = pathCompFirmados;
	}

	public String getPathCompGenerados() {
		return this.pathCompGenerados;
	}

	public void setPathCompGenerados(String pathCompGenerados) {
		this.pathCompGenerados = pathCompGenerados;
	}

	public String getPathCompNoAutorizados() {
		return this.pathCompNoAutorizados;
	}

	public void setPathCompNoAutorizados(String pathCompNoAutorizados) {
		this.pathCompNoAutorizados = pathCompNoAutorizados;
	}

	public String getPathCompRecepcion() {
		return this.pathCompRecepcion;
	}

	public void setPathCompRecepcion(String pathCompRecepcion) {
		this.pathCompRecepcion = pathCompRecepcion;
	}

	public String getPathFirma() {
		return this.pathFirma;
	}

	public void setPathFirma(String pathFirma) {
		this.pathFirma = pathFirma;
	}

	public String getPathInfoRecibida() {
		return this.pathInfoRecibida;
	}

	public void setPathInfoRecibida(String pathInfoRecibida) {
		this.pathInfoRecibida = pathInfoRecibida;
	}

	public String getPathLogoEmpresa() {
		return this.pathLogoEmpresa;
	}

	public void setPathLogoEmpresa(String pathLogoEmpresa) {
		this.pathLogoEmpresa = pathLogoEmpresa;
	}

	public String getPathMarcaAgua() {
		return this.pathMarcaAgua;
	}

	public void setPathMarcaAgua(String pathMarcaAgua) {
		this.pathMarcaAgua = pathMarcaAgua;
	}

	public Integer getPuertoSMTP() {
		return this.puertoSMTP;
	}

	public void setPuertoSMTP(Integer puertoSMTP) {
		this.puertoSMTP = puertoSMTP;
	}

	public String getRazonComercial() {
		return this.razonComercial;
	}

	public void setRazonComercial(String razonComercial) {
		this.razonComercial = razonComercial;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getServidorSMTP() {
		return this.servidorSMTP;
	}

	public void setServidorSMTP(String servidorSMTP) {
		this.servidorSMTP = servidorSMTP;
	}

	public Boolean getSslSMTP() {
		return this.sslSMTP;
	}

	public void setSslSMTP(Boolean sslSMTP) {
		this.sslSMTP = sslSMTP;
	}

	public String getUrlWebServices() {
		return this.urlWebServices;
	}

	public void setUrlWebServices(String urlWebServices) {
		this.urlWebServices = urlWebServices;
	}

	public String getUserSMTP() {
		return this.userSMTP;
	}

	public void setUserSMTP(String userSMTP) {
		this.userSMTP = userSMTP;
	}

	public String getPathCompContingencia() {
		return pathCompContingencia;
	}

	public void setPathCompContingencia(String pathCompContingencia) {
		this.pathCompContingencia = pathCompContingencia;
	}

	public String getFechaResolucionContribEspecial() {
		return fechaResolucionContribEspecial;
	}

	public void setFechaResolucionContribEspecial(
			String fechaResolucionContribEspecial) {
		this.fechaResolucionContribEspecial = fechaResolucionContribEspecial;
	}

	public String getPassFirma() {
		return PassFirma;
	}

	public void setPassFirma(String passFirma) {
		PassFirma = passFirma;
	}

	public String getTypeFirma() {
		return TypeFirma;
	}

	public void setTypeFirma(String typeFirma) {
		TypeFirma = typeFirma;
	}

	public String getPathXSD() {
		return PathXSD;
	}

	public void setPathXSD(String pathXSD) {
		PathXSD = pathXSD;
	}

	public String getPathJasper() {
		return PathJasper;
	}

	public void setPathJasper(String pathJasper) {
		PathJasper = pathJasper;
	}
		
}