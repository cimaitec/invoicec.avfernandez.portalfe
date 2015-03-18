package com.general.entidades;

public class PantallaDetalleProductoentidades {
	//pk de detalle de documento
	int cod_producto; //foraneo de cab documento
	int cod_adicional;//foraneo de cab documento
	String descripcion;//foraneo de cab documento
	String tipoProductoDEscrip;
	double valorUnitario;
	String tipoIvadescrip;
	String codIcedescrip;
	
	public PantallaDetalleProductoentidades(int cod_producto, int cod_adicional, String descripcion, String tipoProductoDEscrip, double valorUnitario, 
											String tipoIvadescrip, String codIcedescrip){
		this.cod_producto = cod_producto;
		this.cod_adicional = cod_adicional;
		this.descripcion = descripcion;
		this.tipoProductoDEscrip = tipoProductoDEscrip;
		this.valorUnitario = valorUnitario;
		this.tipoIvadescrip = tipoIvadescrip;
		this.codIcedescrip = codIcedescrip;
	}

	public int getCod_producto() {
		return cod_producto;
	}
	
	public void setCod_producto(int cod_producto) {
		this.cod_producto = cod_producto;
	}
	
	public int getCod_adicional() {
		return cod_adicional;
	}
	
	public void setCod_adicional(int cod_adicional) {
		this.cod_adicional = cod_adicional;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipoProductoDEscrip() {
		return tipoProductoDEscrip;
	}
	
	public void setTipoProductoDEscrip(String tipoProductoDEscrip) {
		this.tipoProductoDEscrip = tipoProductoDEscrip;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public String getTipoIvadescrip() {
		return tipoIvadescrip;
	}
	
	public void setTipoIvadescrip(String tipoIvadescrip) {
		this.tipoIvadescrip = tipoIvadescrip;
	}
	
	public String getCodIcedescrip() {
		return codIcedescrip;
	}
	
	public void setCodIcedescrip(String codIcedescrip) {
		this.codIcedescrip = codIcedescrip;
	}
}
