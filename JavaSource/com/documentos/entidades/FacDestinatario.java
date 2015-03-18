package com.documentos.entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.Order;

@Entity
@Table(name="fac_destinatario")
public class FacDestinatario
{
	@Id
	@EmbeddedId
	private FacDestinatarioPK id;
	
	@Column(name="\"razonSocial\"")			private String razonSocialDestinatario;
	@Column(name="\"direccion\"")			private String direccion;
	@Column(name="\"motivoTraslado\"")		private String motivoTraslado;
	@Column(name="\"documentoAduanero\"")	private String docAduanero;
	@Column(name="\"codEstabDestino\"")		private String codEstabDestino;
	@Column(name="\"ruta\"")				private String rutaDest;
	@Column(name="\"codDocSustento\"")		private String codDocSustentoDest;
	@Column(name="\"numDocSustento\"")		private String numDocSustentoDest;
	@Column(name="\"numAutorizacionDocSustento\"")	private String numAutDocSustDest;
	@Column(name="\"fechaEmisionDocSustento\"")		private String fechEmisionDocSustDest;
	
	@Column(name="\"secuencialDetalle\"")	private int secuencialDetalle;
	
	//@OneToMany(mappedBy="destinatario", targetEntity=Order.class, fetch=FetchType.EAGER)
	//private List<FacDetGuiaRemision> listDetallesGuiaRemision;

	// INI HFU
	
	/*public List<FacDetGuiaRemision> getListDetallesGuiaRemision() {
		return listDetallesGuiaRemision;
	}
	public void setListDetallesGuiaRemision(List<FacDetGuiaRemision> listDetallesGuiaRemision) {
		this.listDetallesGuiaRemision = listDetallesGuiaRemision;
	}*/
	
	/*public List<DetalleGuiaRemision> getListDetallesGuiaRemision() {
		return listDetallesGuiaRemision;
	}
	public void setListDetallesGuiaRemision(
			List<DetalleGuiaRemision> listDetallesGuiaRemision) {
		this.listDetallesGuiaRemision = listDetallesGuiaRemision;
	}*/
	
	
	/*public List<FacDetDocumento> getListaDetallesDocumentos() {
		return listaDetallesDocumentos;
	}
	public void setListaDetallesDocumentos(
			List<FacDetDocumento> listaDetallesDocumentos) {
		this.listaDetallesDocumentos = listaDetallesDocumentos;
	}*/
	// FIN HFU
	
	
	/*public List<DetalleDocumento> getListaDetalles() {
		return listaDetalles;
	}
	public void setListaDetalles(List<DetalleDocumento> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}*/
	
	public FacDestinatarioPK getId() {
		return id;
	}
	public void setId(FacDestinatarioPK id) {
		this.id = id;
	}
	
	public String getRazonSocialDestinatario() {
		return razonSocialDestinatario;
	}
	public void setRazonSocialDestinatario(String razonSocialDestinatario) {
		this.razonSocialDestinatario = razonSocialDestinatario;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String dirDestinatario) {
		this.direccion = dirDestinatario;
	}
	public String getMotivoTraslado() {
		return motivoTraslado;
	}
	public void setMotivoTraslado(String motTraslDestinatario) {
		this.motivoTraslado = motTraslDestinatario;
	}
	public String getDocAduanero() {
		return docAduanero;
	}
	public void setDocAduanero(String docAduanero) {
		this.docAduanero = docAduanero;
	}
	public String getCodEstabDestino() {
		return codEstabDestino;
	}
	public void setCodEstabDestino(String codEstabDestino) {
		this.codEstabDestino = codEstabDestino;
	}
	public String getRutaDest() {
		return rutaDest;
	}
	public void setRutaDest(String rutaDest) {
		this.rutaDest = rutaDest;
	}
	public String getCodDocSustentoDest() {
		return codDocSustentoDest;
	}
	public void setCodDocSustentoDest(String codDocSustentoDest) {
		this.codDocSustentoDest = codDocSustentoDest;
	}
	public String getNumDocSustentoDest() {
		return numDocSustentoDest;
	}
	public void setNumDocSustentoDest(String numDocSustentoDest) {
		this.numDocSustentoDest = numDocSustentoDest;
	}
	public String getNumAutDocSustDest() {
		return numAutDocSustDest;
	}
	public void setNumAutDocSustDest(String numAutDocSustDest) {
		this.numAutDocSustDest = numAutDocSustDest;
	}
	public String getFechEmisionDocSustDest() {
		return fechEmisionDocSustDest;
	}
	public void setFechEmisionDocSustDest(String fechEmisionDocSustDest) {
		this.fechEmisionDocSustDest = fechEmisionDocSustDest;
	}
	
	public int getSecuencialDetalle() {
		return secuencialDetalle;
	}
	public void setSecuencialDetalle(int secuencialDetalle) {
		this.secuencialDetalle = secuencialDetalle;
	}
	
	

}

