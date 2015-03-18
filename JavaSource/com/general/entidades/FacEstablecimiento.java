package com.general.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the fac_establecimiento database table.
 * 
 */
@Entity
@Table(name="fac_establecimiento")
public class FacEstablecimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacEstablecimientoPK id;

	@Column(name="\"Correo\"")
	private String correo;

	@Column(name="\"DireccionEstablecimiento\"")
	private String direccionEstablecimiento;
	
	//VPI se agrega campos telefono para reporte
	@Column(name="\"Telefono\"")
	private String telefono;

	@Column(name="\"isActive\"")
	private String isActive;

	@Column(name="\"Mensaje\"")
	private String mensaje;

	@Column(name="\"PathAnexo\"")
	private String pathAnexo;
	
	@Column(name="\"local\"")
	private String Local;

	@Column(name="\"fechaCreacion\"")
	private Date fechaCreacion;

	@Column(name="\"userCreacion\"")
	private String userCreacion;
	
	@Column(name="\"fechaModificacion\"")
	private Date fechaModificacion;
	
    @Column(name="\"userModificacion\"")
	private String userModificacion;	
	
	//bi-directional many-to-one association to FacPuntoEmision
	/*@OneToMany(mappedBy="facEstablecimiento")
	private List<FacPuntoEmision> facPuntoEmisions;
    */
    public FacEstablecimiento() {
    }

	public FacEstablecimientoPK getId() {
		return this.id;
	}

	public void setId(FacEstablecimientoPK id) {
		this.id = id;
	}
	
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccionEstablecimiento() {
		return this.direccionEstablecimiento;
	}

	public void setDireccionEstablecimiento(String direccionEstablecimiento) {
		this.direccionEstablecimiento = direccionEstablecimiento;
	}
	
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getPathAnexo() {
		return this.pathAnexo;
	}

	public void setPathAnexo(String pathAnexo) {
		this.pathAnexo = pathAnexo;
	}

	public String getLocal() {
		return Local;
	}

	public void setLocal(String Local) {
		this.Local = Local;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUserCreacion() {
		return userCreacion;
	}

	public void setUserCreacion(String userCreacion) {
		this.userCreacion = userCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUserModificacion() {
		return userModificacion;
	}

	public void setUserModificacion(String userModificacion) {
		this.userModificacion = userModificacion;
	}	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String ls_string = "id.Ruc:"+id.getRuc()+"|id.CodEstablecimiento:"+id.getCodEstablecimiento()+
						   "|Correo:"+correo+"|direccionEstablecimiento:"+direccionEstablecimiento+
						   "|Telefono:"+telefono+
						   "|isActive:"+isActive+"|mensaje:"+mensaje+
						   "|pathAnexo:"+pathAnexo+"|Local:"+Local;
		return ls_string;
	}
}