package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_det_impuestos database table.
 * 
 */
@Entity
@Table(name="fac_det_impuestos")
public class FacDetImpuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacDetImpuestoPK id;

	@Column(name="\"baseImponible\"")
	private double baseImponible;

	@Column(name="\"codPorcentaje\"")
	private Integer codPorcentaje;

	@Column(name="\"porcentajeRetencion\"")
	private double porcentajeRetencion;

	private Integer tarifa;

	@Column(name="\"tipoImpuestos\"")
	private String tipoImpuestos;

	private double valor;

	
	
    public FacDetImpuesto() {
    }

	public FacDetImpuestoPK getId() {
		return this.id;
	}

	public void setId(FacDetImpuestoPK id) {
		this.id = id;
	}
	
	public double getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Integer getCodPorcentaje() {
		return this.codPorcentaje;
	}

	public void setCodPorcentaje(Integer codPorcentaje) {
		this.codPorcentaje = codPorcentaje;
	}

	public double getPorcentajeRetencion() {
		return this.porcentajeRetencion;
	}

	public void setPorcentajeRetencion(double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	public Integer getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(Integer tarifa) {
		this.tarifa = tarifa;
	}

	public String getTipoImpuestos() {
		return this.tipoImpuestos;
	}

	public void setTipoImpuestos(String tipoImpuestos) {
		this.tipoImpuestos = tipoImpuestos;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}