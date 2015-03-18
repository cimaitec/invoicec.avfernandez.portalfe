package com.general.lazy.wrappers;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the fac_cab_documentos database table.
 * 
 */
public class FacCabDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	private FacCabDocumentoPK id;	

	private String autorizacion;

	private String claveAcceso;

	private String codDocModificado;
	
	private String descDocModificado;
	
	private String codDocSustento;
	
	private String numDocSustento;

	private String estadoTransaccion;
    
	private Date fecEmisionDocSustento;

	private Date fechaautorizacion;

	private String fechaEmision;
    
	private String fechaEmisionDocSustento;

	private String guiaRemision;

	private String identificacionComprador;

	private String importeTotal;

	private String razonSocialComprador;

	private String tipIdentificacionComprador;

	private String tipoEmision;
	
	private String claveAccesoContigente;

	private String claveContingencia;
		
	private String docuAutorizacion;
	
	private String fechaAutorizado;
	public FacCabDocumento() {
		
	}
    public FacCabDocumento(String ruc, String codEstablecimiento,String codPuntEmision,String secuencial,String codigoDocumento, 
    					   String ambiente,String autorizacion, String claveAcceso, String codDocModificado, String descDocModificado, 
    					   String codDocSustento,String estadoTransaccion, Date fecEmisionDocSustento, Date fechaautorizacion, String fechaEmision, 
    					   String fechaEmisionDocSustento, String guiaRemision, String identificacionComprador, String importeTotal, 
    					   String razonSocialComprador, String tipIdentificacionComprador, String tipoEmision, String claveAccesoContigente, 
    					   String claveContingencia, String docuAutorizacion, String fechaAutorizado, String numDocSustento) {
    	
    	this.id = new FacCabDocumentoPK(); 
    	this.id.setRuc(ruc);
    	this.id.setCodEstablecimiento(codEstablecimiento);
    	this.id.setCodPuntEmision(codPuntEmision); 
    	this.id.setSecuencial(secuencial);
    	this.id.setCodigoDocumento(codigoDocumento);
    	this.id.setAmbiente(ambiente);
    	
	    	this.autorizacion= autorizacion; 
	    	this.claveAcceso=claveAcceso; 
	    	this.codDocModificado=codDocModificado;
	    	this.descDocModificado=descDocModificado;
	    	this.codDocSustento=codDocSustento;	    	
			this.estadoTransaccion=estadoTransaccion;
			this.fecEmisionDocSustento=fecEmisionDocSustento;
			this.fechaautorizacion=fechaautorizacion;
			this.fechaEmision=fechaEmision; 
			this.fechaEmisionDocSustento=fechaEmisionDocSustento;
			this.guiaRemision=guiaRemision;
			this.identificacionComprador=identificacionComprador;
			this.importeTotal=importeTotal; 
			this.razonSocialComprador=razonSocialComprador; 
			this.tipIdentificacionComprador=tipIdentificacionComprador;
			this.tipoEmision=tipoEmision;
			this.claveAccesoContigente=claveAccesoContigente; 
			this.claveContingencia=claveContingencia;
			this.docuAutorizacion=docuAutorizacion;
			this.fechaAutorizado=fechaAutorizado;
			this.fechaAutorizado=numDocSustento;
    }

	public FacCabDocumentoPK getId() {
		return this.id;
	}

	public void setId(FacCabDocumentoPK id) {
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

	public String getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
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

	public String getImporteTotal() {
		return this.importeTotal;
	}

	public void setImporteTotal(String importeTotal) {
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
	public String getNumDocSustento() {
		return numDocSustento;
	}
	public void setNumDocSustento(String numDocSustento) {
		this.numDocSustento = numDocSustento;
	}	
	
	
}