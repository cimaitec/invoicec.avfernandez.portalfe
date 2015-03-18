package com.general.entidades;
import java.sql.Date;
import javax.persistence.*;

/**
 * The persistent class for the fac_general database table.
 * 
 */
@Entity
@Table(name="")
public class facDetalleDocumentoEntidad {
	@Id
	@Column(name="\"RFCREC\"")
	private String RFCREC;

	@Column(name="\"NOMREC\"")
	private String NOMREC;

	@Column(name="\"codDoc\"")
	private String codDoc;

	@Column(name="\"TIPODOC\"")
	private String TIPODOC;

	@Column(name="\"FOLFAC\"")
	private String FOLFAC;
	
	@Column(name="\"TOTAL\"")
	private Double TOTAL;
	
	@Column(name="\"FECHA\"")
	private Date FECHA;
	
	@Column(name="\"EDOFAC\"")
	private String EDOFAC;
	
	@Column(name="\"PDFARC\"")
	private String PDFARC;
	
	@Column(name="\"XMLARC\"")
	private String XMLARC;
	
	@Column(name="\"MARCAR\"")
	private Boolean MARCAR;
	
	@Column(name="\"email\"")
	private String email;
	
	@Column(name="\"direccion\"")
	private String direccion;
	
	@Column(name="\"formato\"")
	private String formato;
	
	// CPA
	@Column(name="\"codEstablecimiento\"")
	private String codEstablecimiento;
	
	@Column(name="\"codPuntoEmision\"")
	private String codPuntoEmision;
	
	@Column(name="\"codigoDocumento\"")
	private String codigoDocumento;
	
	@Column(name="\"secuencial\"")
	private String secuencial;
	
	@Column(name="\"docuAutorizacion\"")
	private String docuAutorizacion;
	
	@Column(name="\"rucEmirsor\"")
	private String rucEmisor;
	
	private String ambiente;
	
	//String ruc, String codEstablecimiento, String codPuntoEmision, String secuencial, String codigoDocumento
	
    public facDetalleDocumentoEntidad(String RFCREC,String NOMREC,String codDoc,String TIPODOC,String FOLFAC,Double TOTAL, Date FECHA,String EDOFAC,
    		String PDFARC,String XMLARC,String email,String direccion, String formato, String codEstablecimiento, String codPuntoEmision,
    		String codigoDocumento, String secuencial, String docuAutorizacion, String ambiente,String rucEmisor) {
    	this.RFCREC= RFCREC;
    	this.NOMREC=NOMREC;
    	this.codDoc= codDoc;
    	this.TIPODOC= TIPODOC;
    	this.FOLFAC= FOLFAC;
    	this.TOTAL= TOTAL;
    	this.FECHA= FECHA;
    	this.EDOFAC = EDOFAC;
    	this.PDFARC= PDFARC;
    	this.XMLARC = XMLARC;
    	this.email= email;
    	this.direccion=direccion;
    	this.formato= formato;
    	this.codEstablecimiento= codEstablecimiento;
    	this.codPuntoEmision=codPuntoEmision;
    	this.codigoDocumento= codigoDocumento;
    	this.secuencial= secuencial;
    	this.docuAutorizacion = docuAutorizacion;
    	this.ambiente = ambiente;
    	this.rucEmisor = rucEmisor;
    }

    public 	facDetalleDocumentoEntidad(){
    	
    }
    
	public String getRFCREC() {
		return RFCREC;
	}

	public void setRFCREC(String rFCREC) {
		RFCREC = rFCREC;
	}

	public String getNOMREC() {
		return NOMREC;
	}

	public void setNOMREC(String nOMREC) {
		NOMREC = nOMREC;
	}

	public String getCodDoc() {
		return codDoc;
	}

	public void setCodDoc(String codDoc) {
		this.codDoc = codDoc;
	}

	public String getTIPODOC() {
		return TIPODOC;
	}

	public void setTIPODOC(String tIPODOC) {
		TIPODOC = tIPODOC;
	}

	public String getFOLFAC() {
		return FOLFAC;
	}

	public void setFOLFAC(String fOLFAC) {
		FOLFAC = fOLFAC;
	}

	public Double getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(Double tOTAL) {
		TOTAL = tOTAL;
	}

	public Date getFECHA() {
		return FECHA;
	}

	public void setFECHA(Date fECHA) {
		FECHA = fECHA;
	}

	public String getEDOFAC() {
		return EDOFAC;
	}

	public void setEDOFAC(String eDOFAC) {
		EDOFAC = eDOFAC;
	}

	public String getPDFARC() {
		return PDFARC;
	}

	public void setPDFARC(String pDFARC) {
		PDFARC = pDFARC;
	}

	public String getXMLARC() {
		return XMLARC;
	}

	public void setXMLARC(String xMLARC) {
		XMLARC = xMLARC;
	}

	public Boolean getMARCAR() {
		return MARCAR;
	}

	public void setMARCAR(Boolean mARCAR) {
		MARCAR = mARCAR;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getCodEstablecimiento() {
		return codEstablecimiento;
	}

	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}

	public String getCodPuntoEmision() {
		return codPuntoEmision;
	}

	public void setCodPuntoEmision(String codPuntoEmision) {
		this.codPuntoEmision = codPuntoEmision;
	}

	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public String getDocuAutorizacion() {
		return docuAutorizacion;
	}

	public void setDocuAutorizacion(String docuAutorizacion) {
		this.docuAutorizacion = docuAutorizacion;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getRucEmisor() {
		return rucEmisor;
	}

	public void setRucEmisor(String rucEmisor) {
		this.rucEmisor = rucEmisor;
	}
    
	
	
}
