package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the fac_productos database table.
 * 
 */
@Entity
@Table(name="fac_productos")
public class FacProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"codPrincipal\"")
	private Integer codPrincipal;

	private String atributo1;

	private String atributo2;

	private String atributo3;

	@Column(name="\"codAuxiliar\"")
	private Integer codAuxiliar;

	@Column(name="\"codIce\"")
	private Integer codIce;

	private String descripcion;

	@Column(name="\"tipoIva\"")
	private Integer tipoIva;

	@Column(name="\"tipoProducto\"")
	private String tipoProducto;

	private String valor1;

	private String valor2;

	private String valor3;

	@Column(name="\"valorUnitario\"")
	private double valorUnitario;

    public FacProducto() {
    }

	public Integer getCodPrincipal() {
		return this.codPrincipal;
	}

	public void setCodPrincipal(Integer codPrincipal) {
		this.codPrincipal = codPrincipal;
	}

	public String getAtributo1() {
		return this.atributo1;
	}

	public void setAtributo1(String atributo1) {
		this.atributo1 = atributo1;
	}

	public String getAtributo2() {
		return this.atributo2;
	}

	public void setAtributo2(String atributo2) {
		this.atributo2 = atributo2;
	}

	public String getAtributo3() {
		return this.atributo3;
	}

	public void setAtributo3(String atributo3) {
		this.atributo3 = atributo3;
	}

	public Integer getCodAuxiliar() {
		return this.codAuxiliar;
	}

	public void setCodAuxiliar(Integer codAuxiliar) {
		this.codAuxiliar = codAuxiliar;
	}

	public Integer getCodIce() {
		return this.codIce;
	}

	public void setCodIce(Integer codIce) {
		this.codIce = codIce;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getTipoIva() {
		return this.tipoIva;
	}

	public void setTipoIva(Integer tipoIva) {
		this.tipoIva = tipoIva;
	}

	public String getTipoProducto() {
		return this.tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getValor1() {
		return this.valor1;
	}

	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	public String getValor2() {
		return this.valor2;
	}

	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	public String getValor3() {
		return this.valor3;
	}

	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}

	public double getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}