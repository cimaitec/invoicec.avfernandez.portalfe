package com.documentos.entidades;

public class PantallaDetalleDocumentoRetencion {
	//pk de detalle de documento
		String ruc; //foraneo de cab documento
		String codEstablecimiento;//foraneo de cab documento
		String puntoEmision;//foraneo de cab documento
		int secuencial;//foraneo de cab documento
		int linea;//foraneo de cab documento
		
		// campos de la linea
		String Cod_impuesto;
		String Cod_porcentaje;
		String cod_descripcion;
		int tarifa;
		double valorBaseImp;
		double porcentajeRetencion;
		double valorTotal;
		boolean visibleBoton;
		
		public int getTarifa() {
			return tarifa;
		}
		public void setTarifa(int tarifa) {
			this.tarifa = tarifa;
		}
		public boolean isVisibleBoton() {
			return visibleBoton;
		}
		public void setVisibleBoton(boolean visibleBoton) {
			this.visibleBoton = visibleBoton;
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
		public String getCod_impuesto() {
			return Cod_impuesto;
		}
		public void setCod_impuesto(String cod_impuesto) {
			Cod_impuesto = cod_impuesto;
		}
		public String getCod_porcentaje() {
			return Cod_porcentaje;
		}
		public void setCod_porcentaje(String cod_porcentaje) {
			Cod_porcentaje = cod_porcentaje;
		}
		public String getCod_descripcion() {
			return cod_descripcion;
		}
		public void setCod_descripcion(String cod_descripcion) {
			this.cod_descripcion = cod_descripcion;
		}
		public double getValorBaseImp() {
			return valorBaseImp;
		}
		public void setValorBaseImp(double valorBaseImp) {
			this.valorBaseImp = valorBaseImp;
		}
		public double getPorcentajeRetencion() {
			return porcentajeRetencion;
		}
		public void setPorcentajeRetencion(double porcentajeRetencion) {
			this.porcentajeRetencion = porcentajeRetencion;
		}
		public double getValorTotal() {
			return valorTotal;
		}
		public void setValorTotal(double valorTotal) {
			this.valorTotal = valorTotal;
		}
		public int getLinea() {
			return linea;
		}
		public void setLinea(int linea) {
			this.linea = linea;
		}
		
}
