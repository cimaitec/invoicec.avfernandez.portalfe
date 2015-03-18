package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_det_documentos database table.
 * 
 */
@Entity
@Table(name="fac_det_documentos")
public class FacDetDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacDetDocumentoPK id;

	private double cantidad;

	@Column(name="\"CodAuxiliar\"")
	private String codAuxiliar;

	@Column(name="\"CodPrincipal\"")
	private String codPrincipal;

	private String descripcion;

	private double descuento;

	@Column(name="\"precioTotalSinImpuesto\"")
	private double precioTotalSinImpuesto;

	@Column(name="\"precioUnitario\"")
	private double precioUnitario;

	@Column(name="\"valorIce\"")
	private double valorIce;

    public FacDetDocumento() {
    }

	public FacDetDocumentoPK getId() {
		return this.id;
	}

	public void setId(FacDetDocumentoPK id) {
		this.id = id;
	}
	
	public double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodAuxiliar() {
		return this.codAuxiliar;
	}

	public void setCodAuxiliar(String codAuxiliar) {
		this.codAuxiliar = codAuxiliar;
	}

	public String getCodPrincipal() {
		return this.codPrincipal;
	}

	public void setCodPrincipal(String codPrincipal) {
		this.codPrincipal = codPrincipal;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getDescuento() {
		return this.descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getPrecioTotalSinImpuesto() {
		return this.precioTotalSinImpuesto;
	}

	public void setPrecioTotalSinImpuesto(double precioTotalSinImpuesto) {
		this.precioTotalSinImpuesto = precioTotalSinImpuesto;
	}

	public double getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getValorIce() {
		return this.valorIce;
	}

	public void setValorIce(double valorIce) {
		this.valorIce = valorIce;
	}

}