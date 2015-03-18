package com.documentos.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_det_motivosdebito database table.
 * 
 */
@Entity
@Table(name="fac_det_motivosdebito")
public class FacDetMotivosdebito implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacDetMotivosdebitoPK id;

	@Column(name="\"baseImponible\"")
	private double baseImponible;

	@Column(name="\"codImpuesto\"")
	private Integer codImpuesto;

	@Column(name="\"codPorcentaje\"")
	private Integer codPorcentaje;

	private String razon;

	private Integer tarifa;

	@Column(name="\"tipoImpuestos\"")
	private String tipoImpuestos;

	private double valor;

    public FacDetMotivosdebito() {
    }

	public FacDetMotivosdebitoPK getId() {
		return this.id;
	}

	public void setId(FacDetMotivosdebitoPK id) {
		this.id = id;
	}
	
	public double getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Integer getCodImpuesto() {
		return this.codImpuesto;
	}

	public void setCodImpuesto(Integer codImpuesto) {
		this.codImpuesto = codImpuesto;
	}

	public Integer getCodPorcentaje() {
		return this.codPorcentaje;
	}

	public void setCodPorcentaje(Integer codPorcentaje) {
		this.codPorcentaje = codPorcentaje;
	}

	public String getRazon() {
		return this.razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
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