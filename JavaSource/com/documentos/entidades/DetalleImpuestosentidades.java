package com.documentos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="")
public class DetalleImpuestosentidades {
	@Column(name="\"codImpuesto\"", nullable=false)
	private int codImpuesto;
	
	@Column(name="\"codPorcentaje\"", nullable=false)
	private int codPorcentaje;
	
	@Column(name="\"baseImponible\"", nullable=false)
	private double baseImponible;
	
	@Column(name="Tarifa", nullable=false)
	private double tarifa;

	@Column(name="Valor", nullable=false)
	private double valor;
	
	@Column(name="\"TipoImpuestos\"", nullable=false)
	private String tipoImpuestos;
	
	public DetalleImpuestosentidades(){
		
	}
	public DetalleImpuestosentidades(int codImpuesto,	int codPorcentaje,	double baseImponible,	double tarifa,	double valor,	String tipoImpuestos){
		this.codImpuesto = codImpuesto;
		this.codPorcentaje = codPorcentaje;
		this.baseImponible = baseImponible;
		this.tarifa = tarifa;
		this.valor = valor;
		this.tipoImpuestos = tipoImpuestos;
	}
	
	public int getCodImpuesto() {
		return codImpuesto;
	}
	public void setCodImpuesto(int codImpuesto) {
		this.codImpuesto = codImpuesto;
	}
	public int getCodPorcentaje() {
		return codPorcentaje;
	}
	public void setCodPorcentaje(int codPorcentaje) {
		this.codPorcentaje = codPorcentaje;
	}
	public double getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}
	public double getTarifa() {
		return tarifa;
	}
	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getTipoImpuestos() {
		return tipoImpuestos;
	}
	public void setTipoImpuestos(String tipoImpuestos) {
		this.tipoImpuestos = tipoImpuestos;
	}
}
