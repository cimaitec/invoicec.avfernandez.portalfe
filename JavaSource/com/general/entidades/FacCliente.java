package com.general.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fac_clientes database table.
 * 
 */
@Entity
@Table(name="fac_clientes")
public class FacCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacClientePK id;

	@Column(name="\"Direccion\"")
	private String direccion;

	@Column(name="\"Email\"")
	private String email;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"RazonSocial\"")
	private String razonSocial;

	@Column(name="\"Rise\"")
	private String rise;

	@Column(name="\"Telefono\"")
	private String telefono;

	@Column(name="\"TipoCliente\"")
	private String tipoCliente;

	@Column(name="\"TipoIdentificacion\"")
	private String tipoIdentificacion;
	
	@Column(name="\"codCliente\"")
	private String codCliente;

    public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public FacCliente() {
    }

	public FacClientePK getId() {
		return this.id;
	}

	public void setId(FacClientePK id) {
		this.id = id;
	}
	
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRise() {
		return this.rise;
	}

	public void setRise(String rise) {
		this.rise = rise;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoCliente() {
		return this.tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

}