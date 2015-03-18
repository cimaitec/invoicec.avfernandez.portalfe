package com.documentos.entidades;

public class PantallaDetalleDocumento {
	
	//pk de detalle de documento
		String ruc; //foraneo de cab documento
		String codEstablecimiento;//foraneo de cab documento
		String puntoEmision;//foraneo de cab documento
		int secuencial;//foraneo de cab documento
		int linea;//secuencial de detalle documento
		
		// campos de la linea
		String codigoProducto;
		String codigoAdicional;
		String Descripcion;
		int cantidad;
		double precio;
		double descuento;
		double valorSubtotal;
		double valorTotal;
		double valorIce;
		double valorIVA;
		int tipoIVA;
		int tipoIce;
		String DescripcionIce;
		String DescripcionIVA;
		int porcentajeICE;
		int porcentajeIVA;
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
		public String getCodigoProducto() {
			return codigoProducto;
		}
		public void setCodigoProducto(String codigoProducto) {
			this.codigoProducto = codigoProducto;
		}
		public String getCodigoAdicional() {
			return codigoAdicional;
		}
		public void setCodigoAdicional(String codigoAdicional) {
			this.codigoAdicional = codigoAdicional;
		}
		public String getDescripcion() {
			return Descripcion;
		}
		public void setDescripcion(String descripcion) {
			Descripcion = descripcion;
		}
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		public double getDescuento() {
			return descuento;
		}
		public void setDescuento(double descuento) {
			this.descuento = descuento;
		}
		public double getValorSubtotal() {
			return valorSubtotal;
		}
		public void setValorSubtotal(double valorSubtotal) {
			this.valorSubtotal = valorSubtotal;
		}
		public double getValorTotal() {
			return valorTotal;
		}
		public void setValorTotal(double valorTotal) {
			this.valorTotal = valorTotal;
		}
		public double getValorIce() {
			return valorIce;
		}
		public void setValorIce(double valorIce) {
			this.valorIce = valorIce;
		}
		public double getValorIVA() {
			return valorIVA;
		}
		public void setValorIVA(double valorIVA) {
			this.valorIVA = valorIVA;
		}
		public int getTipoIVA() {
			return tipoIVA;
		}
		public void setTipoIVA(int tipoIVA) {
			this.tipoIVA = tipoIVA;
		}
		public int getTipoIce() {
			return tipoIce;
		}
		public void setTipoIce(int tipoIce) {
			this.tipoIce = tipoIce;
		}
		public String getDescripcionIce() {
			return DescripcionIce;
		}
		public void setDescripcionIce(String descripcionIce) {
			DescripcionIce = descripcionIce;
		}
		public String getDescripcionIVA() {
			return DescripcionIVA;
		}
		public void setDescripcionIVA(String descripcionIVA) {
			DescripcionIVA = descripcionIVA;
		}
		public int getPorcentajeICE() {
			return porcentajeICE;
		}
		public void setPorcentajeICE(int porcentajeICE) {
			this.porcentajeICE = porcentajeICE;
		}
		public int getPorcentajeIVA() {
			return porcentajeIVA;
		}
		public void setPorcentajeIVA(int porcentajeIVA) {
			this.porcentajeIVA = porcentajeIVA;
		}
	
	
}
