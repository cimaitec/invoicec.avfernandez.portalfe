package com.general.lazy.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fac_cab_documentos database table.
 * 
 */
@Entity
@Table(name="fac_cab_documentos")
public class FacCabDocumentoRepo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacCabDocumentoRepoPK id;	

	private String autorizacion;

	@Column(name="\"claveAcceso\"")
	private String claveAcceso;

	@Column(name="\"codDocModificado\"")
	private String codDocModificado;

	@Column(name="\"codDocSustento\"")
	private String codDocSustento;

	@Column(name="\"ESTADO_TRANSACCION\"")
	private String estadoTransaccion;
    
	@Column(name="\"fecEmisionDocSustento\"")
	private Date fecEmisionDocSustento;

    @Temporal( TemporalType.DATE)
	private Date fechaautorizacion;

    @Temporal( TemporalType.DATE)
	@Column(name="\"fechaEmision\"")
	private Date fechaEmision;
    
	@Column(name="\"fechaEmisionDocSustento\"")
	private String fechaEmisionDocSustento;

	@Column(name="\"guiaRemision\"")
	private String guiaRemision;

	@Column(name="\"identificacionComprador\"")
	private String identificacionComprador;

	@Column(name="\"importeTotal\"")
	private double importeTotal;

	@Column(name="\"razonSocialComprador\"")
	private String razonSocialComprador;

	@Column(name="\"tipIdentificacionComprador\"")
	private String tipIdentificacionComprador;

	@Column(name="\"tipoEmision\"")
	private String tipoEmision;
	
	@Column(name="\"claveAccesoContigente\"")
	private String claveAccesoContigente;

	@Column(name="\"claveContingencia\"")
	private String claveContingencia;
		
	@Column(name="\"docuAutorizacion\"")
	private String docuAutorizacion;
	
	@Column(name="\"fechaAutorizado\"")
	private String fechaAutorizado;
	
    public FacCabDocumentoRepo() {
    }

	public FacCabDocumentoRepoPK getId() {
		return this.id;
	}

	public void setId(FacCabDocumentoRepoPK id) {
		this.id = id;
	}	

	public String getAutorizacion() {
		return this.autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getClaveAcceso() {
		return this.claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getCodDocModificado() {
		return this.codDocModificado;
	}

	public void setCodDocModificado(String codDocModificado) {
		this.codDocModificado = codDocModificado;
	}

	public String getCodDocSustento() {
		return this.codDocSustento;
	}

	public void setCodDocSustento(String codDocSustento) {
		this.codDocSustento = codDocSustento;
	}

	public String getEstadoTransaccion() {
		return this.estadoTransaccion;
	}

	public void setEstadoTransaccion(String estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public Date getFecEmisionDocSustento() {
		return this.fecEmisionDocSustento;
	}

	public void setFecEmisionDocSustento(Date fecEmisionDocSustento) {
		this.fecEmisionDocSustento = fecEmisionDocSustento;
	}

	public Date getFechaautorizacion() {
		return this.fechaautorizacion;
	}

	public void setFechaautorizacion(Date fechaautorizacion) {
		this.fechaautorizacion = fechaautorizacion;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getFechaEmisionDocSustento() {
		return this.fechaEmisionDocSustento;
	}

	public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
		this.fechaEmisionDocSustento = fechaEmisionDocSustento;
	}

	public String getGuiaRemision() {
		return this.guiaRemision;
	}

	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}

	public String getIdentificacionComprador() {
		return this.identificacionComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public double getImporteTotal() {
		return this.importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getRazonSocialComprador() {
		return this.razonSocialComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public String getTipIdentificacionComprador() {
		return this.tipIdentificacionComprador;
	}

	public void setTipIdentificacionComprador(String tipIdentificacionComprador) {
		this.tipIdentificacionComprador = tipIdentificacionComprador;
	}

	public String getTipoEmision() {
		return this.tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getClaveAccesoContigente() {
		return claveAccesoContigente;
	}

	public void setClaveAccesoContigente(String claveAccesoContigente) {
		this.claveAccesoContigente = claveAccesoContigente;
	}

	public String getClaveContingencia() {
		return claveContingencia;
	}

	public void setClaveContingencia(String claveContingencia) {
		this.claveContingencia = claveContingencia;
	}

	public String getDocuAutorizacion() {
		return docuAutorizacion;
	}

	public void setDocuAutorizacion(String docuAutorizacion) {
		this.docuAutorizacion = docuAutorizacion;
	}

	public String getFechaAutorizado() {
		return fechaAutorizado;
	}

	public void setFechaAutorizado(String fechaAutorizado) {
		this.fechaAutorizado = fechaAutorizado;
	}	
}