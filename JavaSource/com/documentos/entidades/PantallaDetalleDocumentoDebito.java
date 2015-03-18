package com.documentos.entidades;

public class PantallaDetalleDocumentoDebito {
	//pk de detalle de documento
	String ruc; //foraneo de cab documento
	String codEstablecimiento;//foraneo de cab documento
	String puntoEmision;//foraneo de cab documento
	int secuencial;//foraneo de cab documento
	int linea;//secuencial de detalle documento
	
	// campos de la linea
	String razon;
	String cod_impuesto; // si es ice o iva
	String cod_porcentaje; //
	double baseImponible;
	int tarifa;
	double valor;
	double valor_tarifa;
	
	public double getValor_tarifa() {
		return valor_tarifa;
	}
	public void setValor_tarifa(double valor_tarifa) {
		this.valor_tarifa = valor_tarifa;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCodEstablecimiento() {
		return codEstablecimiento;
	}
	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}
	public String getPuntoEmision() {
		return puntoEmision;
	}
	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}
	public int getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(int secuencial) {
		this.secuencial = secuencial;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public String getRazon() {
		return razon;
	}
	public void setRazon(String razon) {
		this.razon = razon;
	}
	public String getCod_impuesto() {
		return cod_impuesto;
	}
	public void setCod_impuesto(String cod_impuesto) {
		this.cod_impuesto = cod_impuesto;
	}
	public String getCod_porcentaje() {
		return cod_porcentaje;
	}
	public void setCod_porcentaje(String cod_porcentaje) {
		this.cod_porcentaje = cod_porcentaje;
	}
	public double getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}
	public int getTarifa() {
		return tarifa;
	}
	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
