package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the fac_cab_documentos database table.
 * 
 */
@Entity
@Table(name="fac_cab_documentos")
public class FacCabDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacCabDocumentoPK id;
	
	private String autorizacion;

	@Column(name="\"claveAcceso\"")
	private String claveAcceso;

	@Column(name="\"codDocModificado\"")
	private String codDocModificado;

	@Column(name="\"codDocSustento\"")
	private String codDocSustento;

	@Column(name="\"codEstablecimientoDest\"")
	private String codEstablecimientoDest;

	@Column(name="\"direccionDestinatario\"")
	private String direccionDestinatario;

	@Column(name="\"docAduaneroUnico\"")
	private String docAduaneroUnico;

	private String email;

	@Column(name="\"ESTADO_TRANSACCION\"")
	private String estadoTransaccion;

    
	@Column(name="\"fecEmisionDocSustento\"")
	private String fecEmisionDocSustento;

    @Temporal( TemporalType.DATE)
	private Date fechaautorizacion;

    @Temporal( TemporalType.DATE)
	@Column(name="\"fechaEmision\"")
	private Date fechaEmision;
    
	@Column(name="\"fechaEmisionDocSustento\"")
	private String fechaEmisionDocSustento;

    @Temporal( TemporalType.DATE)
	@Column(name="\"fechaFinTransporte\"")
	private Date fechaFinTransporte;

    @Temporal( TemporalType.DATE)
	@Column(name="\"fechaInicioTransporte\"")
	private Date fechaInicioTransporte;

	@Column(name="\"guiaRemision\"")
	private String guiaRemision;

	@Column(name="\"identificacionComprador\"")
	private String identificacionComprador;

	@Column(name="\"identificacionDestinatario\"")
	private String identificacionDestinatario;

	@Column(name="\"importeTotal\"")
	private double importeTotal;

	@Column(name="\"infoAdicional\"")
	private String infoAdicional;

	@Column(name="\"isActive\"")
	private String isActive;

	private double iva12;

	private String moneda;

	@Column(name="\"motivoRazon\"")
	private String motivoRazon;

	@Column(name="\"motivoTraslado\"")
	private String motivoTraslado;

	@Column(name="\"motivoValor\"")
	private double motivoValor;

	@Column(name="\"MSJ_ERROR\"")
	private String msjError;

	@Column(name="\"numAutDocSustento\"")
	private String numAutDocSustento;

	@Column(name="\"numDocModificado\"")
	private String numDocModificado;

	@Column(name="\"numDocSustento\"")
	private String numDocSustento;

	private String partida;

	@Column(name="\"periodoFiscal\"")
	private String periodoFiscal;

	private String placa;

	private double propina;

	@Column(name="\"razonSocialComprador\"")
	private String razonSocialComprador;

	@Column(name="\"razonSocialDestinatario\"")
	private String razonSocialDestinatario;

	private String rise;

	private String ruta;

	private double subtotal0;

	private double subtotal12;

	@Column(name="\"subtotalNoIva\"")
	private double subtotalNoIva;

	@Column(name="\"tipIdentificacionComprador\"")
	private String tipIdentificacionComprador;

	@Column(name="\"Tipo\"")
	private String tipo;

	@Column(name="\"tipoEmision\"")
	private String tipoEmision;

	@Column(name="\"TipoIdentificacion\"")
	private Integer tipoIdentificacion;

	@Column(name="\"totalDescuento\"")
	private double totalDescuento;

	@Column(name="\"totalSinImpuesto\"")
	private double totalSinImpuesto;

	@Column(name="\"totalvalorICE\"")
	private double totalvalorICE;
	

	@Column(name="\"docuAutorizacion\"")
	private String docuAutorizacion;
	
	@Column(name="\"fechaAutorizado\"")
	private String fechaAutorizado;

    public FacCabDocumento() {
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

	public String getCodEstablecimientoDest() {
		return this.codEstablecimientoDest;
	}

	public void setCodEstablecimientoDest(String codEstablecimientoDest) {
		this.codEstablecimientoDest = codEstablecimientoDest;
	}

	public String getDireccionDestinatario() {
		return this.direccionDestinatario;
	}

	public void setDireccionDestinatario(String direccionDestinatario) {
		this.direccionDestinatario = direccionDestinatario;
	}

	public String getDocAduaneroUnico() {
		return this.docAduaneroUnico;
	}

	public void setDocAduaneroUnico(String docAduaneroUnico) {
		this.docAduaneroUnico = docAduaneroUnico;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadoTransaccion() {
		return this.estadoTransaccion;
	}

	public void setEstadoTransaccion(String estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public String getFecEmisionDocSustento() {
		return this.fecEmisionDocSustento;
	}

	public void setFecEmisionDocSustento(String fecEmisionDocSustento) {
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

	public Date getFechaFinTransporte() {
		return this.fechaFinTransporte;
	}

	public void setFechaFinTransporte(Date fechaFinTransporte) {
		this.fechaFinTransporte = fechaFinTransporte;
	}

	public Date getFechaInicioTransporte() {
		return this.fechaInicioTransporte;
	}

	public void setFechaInicioTransporte(Date fechaInicioTransporte) {
		this.fechaInicioTransporte = fechaInicioTransporte;
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

	public String getIdentificacionDestinatario() {
		return this.identificacionDestinatario;
	}

	public void setIdentificacionDestinatario(String identificacionDestinatario) {
		this.identificacionDestinatario = identificacionDestinatario;
	}

	public double getImporteTotal() {
		return this.importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getInfoAdicional() {
		return this.infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public double getIva12() {
		return this.iva12;
	}

	public void setIva12(double iva12) {
		this.iva12 = iva12;
	}

	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMotivoRazon() {
		return this.motivoRazon;
	}

	public void setMotivoRazon(String motivoRazon) {
		this.motivoRazon = motivoRazon;
	}

	public String getMotivoTraslado() {
		return this.motivoTraslado;
	}

	public void setMotivoTraslado(String motivoTraslado) {
		this.motivoTraslado = motivoTraslado;
	}

	public double getMotivoValor() {
		return this.motivoValor;
	}

	public void setMotivoValor(double motivoValor) {
		this.motivoValor = motivoValor;
	}

	public String getMsjError() {
		return this.msjError;
	}

	public void setMsjError(String msjError) {
		this.msjError = msjError;
	}

	public String getNumAutDocSustento() {
		return this.numAutDocSustento;
	}

	public void setNumAutDocSustento(String numAutDocSustento) {
		this.numAutDocSustento = numAutDocSustento;
	}

	public String getNumDocModificado() {
		return this.numDocModificado;
	}

	public void setNumDocModificado(String numDocModificado) {
		this.numDocModificado = numDocModificado;
	}

	public String getNumDocSustento() {
		return this.numDocSustento;
	}

	public void setNumDocSustento(String numDocSustento) {
		this.numDocSustento = numDocSustento;
	}

	public String getPartida() {
		return this.partida;
	}

	public void setPartida(String partida) {
		this.partida = partida;
	}

	public String getPeriodoFiscal() {
		return this.periodoFiscal;
	}

	public void setPeriodoFiscal(String periodoFiscal) {
		this.periodoFiscal = periodoFiscal;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getPropina() {
		return this.propina;
	}

	public void setPropina(double propina) {
		this.propina = propina;
	}

	public String getRazonSocialComprador() {
		return this.razonSocialComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public String getRazonSocialDestinatario() {
		return this.razonSocialDestinatario;
	}

	public void setRazonSocialDestinatario(String razonSocialDestinatario) {
		this.razonSocialDestinatario = razonSocialDestinatario;
	}

	public String getRise() {
		return this.rise;
	}

	public void setRise(String rise) {
		this.rise = rise;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public double getSubtotal0() {
		return this.subtotal0;
	}

	public void setSubtotal0(double subtotal0) {
		this.subtotal0 = subtotal0;
	}

	public double getSubtotal12() {
		return this.subtotal12;
	}

	public void setSubtotal12(double subtotal12) {
		this.subtotal12 = subtotal12;
	}

	public double getSubtotalNoIva() {
		return this.subtotalNoIva;
	}

	public void setSubtotalNoIva(double subtotalNoIva) {
		this.subtotalNoIva = subtotalNoIva;
	}

	public String getTipIdentificacionComprador() {
		return this.tipIdentificacionComprador;
	}

	public void setTipIdentificacionComprador(String tipIdentificacionComprador) {
		this.tipIdentificacionComprador = tipIdentificacionComprador;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoEmision() {
		return this.tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public Integer getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	public void setTipoIdentificacion(Integer tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public double getTotalDescuento() {
		return this.totalDescuento;
	}

	public void setTotalDescuento(double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public double getTotalSinImpuesto() {
		return this.totalSinImpuesto;
	}

	public void setTotalSinImpuesto(double totalSinImpuesto) {
		this.totalSinImpuesto = totalSinImpuesto;
	}

	public double getTotalvalorICE() {
		return this.totalvalorICE;
	}

	public void setTotalvalorICE(double totalvalorICE) {
		this.totalvalorICE = totalvalorICE;
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