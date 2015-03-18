package com.general.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Column;

import com.documentos.entidades.FacEmpresa;
import com.general.servicios.FacEmpresaEmisoraServicios;



/**
 * @author Administrador
 *
 */
@ViewScoped
@ManagedBean
public class FacEmpresaEmisoraControlador {
	//entidad empresa
	private FacEmpresa empresa;
	private FacEmpresa camposModificar;
	private FacEmpresa selecionarCampos;
	private FacEmpresa verCampos;
	private FacEmpresa vercamposSeleccionado;
	//Variables
	private String Ruc; 
	private String RazonSocial;
	private String RazonComercial;
	private String DireccionMatriz;
	private int ContribEspecial;
	private Boolean ObligContabilidad;
	private String PathCompGenerados;
	private String PathCompFirmados; 
	private String PathCompAutorizados;
	private String PathCompNoAutorizados;
	private String PathInfoRecibida;
	private String UrlWebServices;
	private String ColorEmpresa; 
	private String PathLogoEmpresa;
	private String PathFirma;
	private String isActive;
	private int puertoSMTP;
	private String servidorSMTP;
	private boolean sslSMTP;
	private String userSMTP;
	private String passSMTP;
	private String auxPassSMTP;
	private String emailEnvio;
	private String pathcomprecepcion;
	private String correorecepcion;
	private boolean grisado;
	private boolean Modificar;
	private String pathCompContingencia;
	private String fechaResolucionContribEspecial;
	private String PassFirma;
	private String TypeFirma;
	private String PathXSD;
	private String PathJasper;	
	private final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//metodo de SelectIten
	private List<SelectItem> li_estado;
	//metodo lista
	private List<FacEmpresa> listEmpresa;
	
	
	
	//metodo servicio
	
	@EJB
	private FacEmpresaEmisoraServicios facEmpSer;
	
	//metodos de la aplicacion
	public void cargarDatos(){
		try {
			Limpiar();
			this.llenaComboEstado();
			this.llenarTablaEmpresa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean ValidarCampos(){
		
		if((Ruc.trim().equals("") || Ruc == null) || (ContribEspecial == 0) || (RazonSocial.trim().equals("") || RazonSocial == null) || (PathCompGenerados.trim().equals("") || PathCompGenerados == null) ||
				(PathCompFirmados.trim().equals("") || PathCompFirmados == null) || (PathCompAutorizados.trim().equals("") || PathCompAutorizados == null) || (PathCompNoAutorizados.trim().equals("") || PathCompNoAutorizados == null) ||
				(PathInfoRecibida.equals("") || PathInfoRecibida == null) || (PathFirma.trim().equals("") || PathFirma == null) || (UrlWebServices.trim().equals("") || UrlWebServices == null) ||
				(servidorSMTP.trim().equals("") || servidorSMTP == null) || (correorecepcion.trim().equals("") || correorecepcion == null) || (pathcomprecepcion.trim().equals("") || pathcomprecepcion == null) || 
				(puertoSMTP == 0) || (userSMTP.trim().equals("") || userSMTP == null) || (emailEnvio.trim().equals("") || emailEnvio == null)|| (pathCompContingencia.trim().equals("") || pathCompContingencia == null)){			
				 	mensajeAlerta("Mensaje del sistema", "Por favor llenar los campos obligatorio", "peligro");
			return false;
		}
		return true;
	}
	
	//TODO contructor de mensaje de alerta para mostrar al usuario
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
	       FacesContext context = FacesContext.getCurrentInstance();            
	       context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
	}
	
	public void Limpiar(){
		try {
			this.setRuc(null);
			this.setRazonSocial(null);
			this.setRazonComercial(null);
			this.setDireccionMatriz(null);
			this.setContribEspecial(0);
			this.setPathCompAutorizados(null);
			this.setPathCompFirmados(null);
			this.setPathCompGenerados(null);
			this.setPathCompNoAutorizados(null);
			this.setPathFirma(null);
			this.setPathInfoRecibida(null);
			this.setPathLogoEmpresa(null);
			this.setUrlWebServices(null);
			this.setColorEmpresa(null);
			this.setServidorSMTP(null);
			this.setPuertoSMTP(0);
			this.setPassSMTP(null);
			this.setUserSMTP(null);
			this.setSslSMTP(false);
			this.setEmailEnvio(null);
			this.setCorreorecepcion(null);
			this.setPathcomprecepcion(null);
			this.setPathCompContingencia(null);
			this.setFechaResolucionContribEspecial(null);
			this.setPassFirma(null);
			this.setTypeFirma(null);
			this.ObligContabilidad = false;
			this.setPathXSD(null);
			this.setPathJasper(null);
			grisado = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void vistaprebia(){
		vercamposSeleccionado = verCampos;
	}
	
	public void insert(String Evento){

		empresa = new FacEmpresa();
		try {
			if(isModificar()!=true){
				if(ValidarCampos()){
					empresa.setRuc(Ruc);
					empresa.setRazonSocial(RazonSocial);
					empresa.setRazonComercial(RazonComercial);
					empresa.setDireccionMatriz(DireccionMatriz);
					empresa.setContribEspecial(ContribEspecial);
					empresa.setObligContabilidad((ObligContabilidad ? "S" : "N"));
					empresa.setPathCompGenerados(PathCompGenerados);
					empresa.setPathCompFirmados(PathCompFirmados);
					empresa.setPathCompAutorizados(PathCompAutorizados);
					empresa.setPathCompNoAutorizados(PathCompNoAutorizados);
					empresa.setPathInfoRecibida(PathInfoRecibida);
					empresa.setUrlWebServices(UrlWebServices);
					empresa.setColorEmpresa(ColorEmpresa);
					empresa.setPathLogoEmpresa(PathLogoEmpresa);
					empresa.setPathFirma(PathFirma);
					empresa.setIsActive(isActive);
					empresa.setPuertoSMTP(puertoSMTP);
					empresa.setServidorSMTP(servidorSMTP);
					empresa.setSslSMTP(sslSMTP);
					empresa.setPathCompRecepcion(pathcomprecepcion);
					empresa.setCorreoRecepcion(correorecepcion);
					empresa.setPathCompContingencia(pathCompContingencia);
					empresa.setFechaResolucionContribEspecial(fechaResolucionContribEspecial);
					empresa.setPassFirma(PassFirma);
					empresa.setTypeFirma(TypeFirma);
					empresa.setPathXSD(PathXSD);
					empresa.setPathJasper(PathJasper);
					if(validar_correo(userSMTP)!=false){
						empresa.setUserSMTP(userSMTP);
					}else{
						mensajeAlerta("Mensaje del sistema", "usuario de SMTP:: Esta incorrecto \n Verifique el campo", "peligro");
						return;
					}
					empresa.setPassSMTP(passSMTP);
					if(validar_correo(emailEnvio)!=false){
						empresa.setEmailEnvio(emailEnvio);
					}else{
						mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Esta incorrecto \n Verifique el campo", "peligro");
						return;
					}
					
					facEmpSer.insertarEmpresa(empresa);
					cargarDatos();
					this.setEmailEnvio(null);
					mensajeAlerta("Mensaje del sistema", "Registro ingresado con exito", "Informacion");					
				}
			}else{
				if(ValidarCampos()){
					empresa.setRuc(Ruc);
					empresa.setRazonSocial(RazonSocial);
					empresa.setRazonComercial(RazonComercial);
					empresa.setDireccionMatriz(DireccionMatriz);
					empresa.setContribEspecial(ContribEspecial);
					empresa.setObligContabilidad((ObligContabilidad ? "S" : "N"));
					empresa.setPathCompGenerados(PathCompGenerados);
					empresa.setPathCompFirmados(PathCompFirmados);
					empresa.setPathCompAutorizados(PathCompAutorizados);
					empresa.setPathCompNoAutorizados(PathCompNoAutorizados);
					empresa.setPathInfoRecibida(PathInfoRecibida);
					empresa.setUrlWebServices(UrlWebServices);
					empresa.setColorEmpresa(ColorEmpresa);
					empresa.setPathLogoEmpresa(PathLogoEmpresa);
					empresa.setPathFirma(PathFirma);
					empresa.setIsActive(isActive);
					empresa.setPuertoSMTP(puertoSMTP);
					empresa.setServidorSMTP(servidorSMTP);
					empresa.setSslSMTP(sslSMTP);
					empresa.setPathCompRecepcion(pathcomprecepcion);
					empresa.setCorreoRecepcion(correorecepcion);
					empresa.setPathCompContingencia(pathCompContingencia);
					empresa.setFechaResolucionContribEspecial(fechaResolucionContribEspecial);
					empresa.setPassFirma(PassFirma);
					empresa.setTypeFirma(TypeFirma);
					empresa.setPathXSD(PathXSD);
					empresa.setPathJasper(PathJasper);
					
					if(validar_correo(userSMTP)!=false){
						empresa.setUserSMTP(userSMTP);
					}else{
						mensajeAlerta("Mensaje del sistema", "usuario de SMTP:: Esta incorrecto \n Verifique el campo", "peligro");
						grisado=true;
						return;
					}
					if(passSMTP.trim().equals("")){
						empresa.setPassSMTP(this.getAuxPassSMTP());
					}else{
						empresa.setPassSMTP(passSMTP);
					}
					if(validar_correo(emailEnvio)!=false){
						empresa.setEmailEnvio(emailEnvio);
					}else{
						mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Esta incorrecto \n Verifique su correo", "peligro");
						grisado=true;
						return;
					}
					facEmpSer.modificarDatos(empresa);
					cargarDatos();
					mensajeAlerta("Mensaje del sistema", "Registro Modificado Con Exito", "informacion");
					this.setModificar(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
			
	}
	
	public void llenaComboEstado(){
		 li_estado = new ArrayList<SelectItem>();  
		try{
			
			li_estado.add(new SelectItem("Y","Activo"));
			li_estado.add(new SelectItem("0","Inactivo"));
						
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	public void llenarTablaEmpresa(){
		listEmpresa = new ArrayList<FacEmpresa>();
		try {
			listEmpresa = buscarDatosDeEmpresa();
			for(int i=0;i<listEmpresa.size();i++){
				if(listEmpresa.get(i).getObligContabilidad().trim().equals("S")){
					listEmpresa.get(i).setObligContabilidad("SI");
				}else{
					listEmpresa.get(i).setObligContabilidad("No");
				}
				if(listEmpresa.get(i).getIsActive().trim().equals("Y")){
					listEmpresa.get(i).setIsActive("Activo");
				}if(listEmpresa.get(i).getIsActive().trim().equals("0")){
					listEmpresa.get(i).setIsActive("Inactivo");
				}
			}
		} catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	public List<FacEmpresa> buscarDatosDeEmpresa(){
		
		List<FacEmpresa> listaFacEmpresa= null;
		
		try{
			listaFacEmpresa = facEmpSer.BuscarDatosEmpresa();

		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  listaFacEmpresa;
	}	

	public void validarRuc(){
		empresa = new FacEmpresa();
		try {
			empresa = facEmpSer.verificarRuc(Ruc);
			if(empresa!=null){
				mensajeAlerta("Mensaje del sistema", "Ruc de Empresa:: Ya Existente", "peligro");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void modificar(){
		
		try {
			if(camposModificar.getObligContabilidad().trim().equals("SI")){
				this.setObligContabilidad(true);
			}else{
				this.setObligContabilidad(false);
			}
			if(camposModificar.getIsActive().trim().equals("Activo")){
				this.setIsActive("Y");
			}else{
				this.setIsActive("0");
			}
			
			this.setRuc(camposModificar.getRuc());
			this.setRazonSocial(camposModificar.getRazonSocial());
			this.setRazonComercial(camposModificar.getRazonComercial());
			this.setDireccionMatriz(camposModificar.getDireccionMatriz());
			this.setContribEspecial(camposModificar.getContribEspecial());
			this.setPathCompAutorizados(camposModificar.getPathCompAutorizados());
			this.setPathCompFirmados(camposModificar.getPathCompFirmados());
			this.setPathCompGenerados(camposModificar.getPathCompGenerados());
			this.setPathCompNoAutorizados(camposModificar.getPathCompNoAutorizados());
			this.setPathFirma(camposModificar.getPathFirma());
			this.setPathInfoRecibida(camposModificar.getPathInfoRecibida());
			this.setPathLogoEmpresa(camposModificar.getPathLogoEmpresa());
			this.setUrlWebServices(camposModificar.getUrlWebServices());
			this.setColorEmpresa(camposModificar.getColorEmpresa());
			this.setServidorSMTP(camposModificar.getServidorSMTP());
			this.setPuertoSMTP(camposModificar.getPuertoSMTP());
			this.setUserSMTP(camposModificar.getUserSMTP());
			this.setEmailEnvio(camposModificar.getEmailEnvio());
			this.setAuxPassSMTP(camposModificar.getPassSMTP());
			this.setSslSMTP(camposModificar.getSslSMTP());
			this.setPathcomprecepcion(camposModificar.getPathCompRecepcion());
			this.setPathCompContingencia(camposModificar.getPathCompContingencia());			
			this.setCorreorecepcion(camposModificar.getCorreoRecepcion());
			this.setPassFirma(camposModificar.getPassFirma());
			this.setTypeFirma(camposModificar.getTypeFirma());
			this.setPathXSD(camposModificar.getPathXSD());
			this.setPathJasper(camposModificar.getPathJasper());
			grisado=true;
			this.setModificar(true);
		} catch (Exception e) {
			e.printStackTrace();
			Limpiar();
		}
	}
	
	public void BorrarLiniea(){
		try{
		this.listEmpresa.remove(selecionarCampos);
		facEmpSer.EliminarDatosLogico(selecionarCampos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validarNumero(){
		String texto = String.valueOf( puertoSMTP);
		for(char x: texto.toCharArray()){
			if(Character.isDigit(x)){
			}
			if(Character.isLetter(x)){
				FacesMessage mensaje = null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,"Solo numeros", "En este campo");
				FacesContext.getCurrentInstance().addMessage("Codigo Establecimiento", mensaje);
				break;
			}
		}
	}
	
	public boolean validar_correo(String email){
		 // Compiles the given regular expression into a pattern.
      Pattern pattern = Pattern.compile(PATTERN_EMAIL);

      // Match the given input against this pattern
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
	}

	
	//metodos de get y set de la aplicacion
	
	public String getRuc() {
		return Ruc;
	}
	public void setRuc(String ruc) {
		Ruc = ruc;
	}
	public String getRazonSocial() {
		return RazonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	public String getRazonComercial() {
		return RazonComercial;
	}
	public void setRazonComercial(String razonComercial) {
		RazonComercial = razonComercial;
	}
	public String getDireccionMatriz() {
		return DireccionMatriz;
	}
	public void setDireccionMatriz(String direccionMatriz) {
		DireccionMatriz = direccionMatriz;
	}
	
	public int getContribEspecial() {
		return ContribEspecial;
	}

	public void setContribEspecial(int contribEspecial) {
		ContribEspecial = contribEspecial;
	}
	
	public Boolean getObligContabilidad() {
		return ObligContabilidad;
	}

	public void setObligContabilidad(Boolean obligContabilidad) {
		ObligContabilidad = obligContabilidad;
	}
	
	public String getPathCompGenerados() {
		return PathCompGenerados;
	}
	public void setPathCompGenerados(String pathCompGenerados) {
		PathCompGenerados = pathCompGenerados;
	}
	public String getPathCompFirmados() {
		return PathCompFirmados;
	}
	public void setPathCompFirmados(String pathCompFirmados) {
		PathCompFirmados = pathCompFirmados;
	}
	public String getPathCompAutorizados() {
		return PathCompAutorizados;
	}
	public void setPathCompAutorizados(String pathCompAutorizados) {
		PathCompAutorizados = pathCompAutorizados;
	}
	public String getPathCompNoAutorizados() {
		return PathCompNoAutorizados;
	}
	public void setPathCompNoAutorizados(String pathCompNoAutorizados) {
		PathCompNoAutorizados = pathCompNoAutorizados;
	}
	public String getPathInfoRecibida() {
		return PathInfoRecibida;
	}
	public void setPathInfoRecibida(String pathInfoRecibida) {
		PathInfoRecibida = pathInfoRecibida;
	}
	public String getUrlWebServices() {
		return UrlWebServices;
	}
	public void setUrlWebServices(String urlWebServices) {
		UrlWebServices = urlWebServices;
	}
	public String getColorEmpresa() {
		return ColorEmpresa;
	}
	public void setColorEmpresa(String colorEmpresa) {
		ColorEmpresa = colorEmpresa;
	}
	public String getPathLogoEmpresa() {
		return PathLogoEmpresa;
	}
	public void setPathLogoEmpresa(String pathLogoEmpresa) {
		PathLogoEmpresa = pathLogoEmpresa;
	}
	public String getPathFirma() {
		return PathFirma;
	}
	public void setPathFirma(String pathFirma) {
		PathFirma = pathFirma;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public FacEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(FacEmpresa empresa) {
		this.empresa = empresa;
	}

	public List<SelectItem> getLi_estado() {
		return li_estado;
	}

	public void setLi_estado(List<SelectItem> li_estado) {
		this.li_estado = li_estado;
	}
	
	public int getPuertoSMTP() {
		return puertoSMTP;
	}

	public void setPuertoSMTP(int puertoSMTP) {
		this.puertoSMTP = puertoSMTP;
	}

	public String getServidorSMTP() {
		return servidorSMTP;
	}

	public void setServidorSMTP(String servidorSMTP) {
		this.servidorSMTP = servidorSMTP;
	}

	public String getUserSMTP() {
		return userSMTP;
	}

	public void setUserSMTP(String userSMTP) {
		this.userSMTP = userSMTP;
	}

	public String getPassSMTP() {
		return passSMTP;
	}

	public void setPassSMTP(String passSMTP) {
		this.passSMTP = passSMTP;
	}

	public String getEmailEnvio() {
		return emailEnvio;
	}

	public void setEmailEnvio(String emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

	public FacEmpresaEmisoraServicios getFacEmpSer() {
		return facEmpSer;
	}

	public void setFacEmpSer(FacEmpresaEmisoraServicios facEmpSer) {
		this.facEmpSer = facEmpSer;
	}

	public List<FacEmpresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<FacEmpresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}
	
	public FacEmpresa getCamposModificar() {
		return camposModificar;
	}

	public void setCamposModificar(FacEmpresa camposModificar) {
		this.camposModificar = camposModificar;
	}

	public FacEmpresa getSelecionarCampos() {
		return selecionarCampos;
	}

	public void setSelecionarCampos(FacEmpresa selecionarCampos) {
		this.selecionarCampos = selecionarCampos;
	}

	public boolean isGrisado() {
		return grisado;
	}

	public void setGrisado(boolean grisado) {
		this.grisado = grisado;
	}

	public boolean isModificar() {
		return Modificar;
	}

	public void setModificar(boolean modificar) {
		Modificar = modificar;
	}

	public FacEmpresa getVerCampos() {
		return verCampos;
	}

	public void setVerCampos(FacEmpresa verCampos) {
		this.verCampos = verCampos;
	}

	public String getAuxPassSMTP() {
		return auxPassSMTP;
	}

	public void setAuxPassSMTP(String auxPassSMTP) {
		this.auxPassSMTP = auxPassSMTP;
	}

	public boolean isSslSMTP() {
		return sslSMTP;
	}

	public void setSslSMTP(boolean sslSMTP) {
		this.sslSMTP = sslSMTP;
	}

	public String getPathcomprecepcion() {
		return pathcomprecepcion;
	}

	public void setPathcomprecepcion(String pathcomprecepcion) {
		this.pathcomprecepcion = pathcomprecepcion;
	}

	public String getCorreorecepcion() {
		return correorecepcion;
	}

	public void setCorreorecepcion(String correorecepcion) {
		this.correorecepcion = correorecepcion;
	}

	public FacEmpresa getVercamposSeleccionado() {
		return vercamposSeleccionado;
	}

	public void setVercamposSeleccionado(FacEmpresa vercamposSeleccionado) {
		this.vercamposSeleccionado = vercamposSeleccionado;
	}

	public String getPathCompContingencia() {
		return pathCompContingencia;
	}

	public void setPathCompContingencia(String pathCompContingencia) {
		this.pathCompContingencia = pathCompContingencia;
	}

	public String getFechaResolucionContribEspecial() {
		return fechaResolucionContribEspecial;
	}

	public void setFechaResolucionContribEspecial(
			String fechaResolucionContribEspecial) {
		this.fechaResolucionContribEspecial = fechaResolucionContribEspecial;
	}

	public String getPassFirma() {
		return PassFirma;
	}

	public void setPassFirma(String passFirma) {
		PassFirma = passFirma;
	}

	public String getTypeFirma() {
		return TypeFirma;
	}

	public void setTypeFirma(String typeFirma) {
		TypeFirma = typeFirma;
	}

	public String getPathXSD() {
		return PathXSD;
	}

	public void setPathXSD(String pathXSD) {
		PathXSD = pathXSD;
	}

	public String getPathJasper() {
		return PathJasper;
	}

	public void setPathJasper(String pathJasper) {
		PathJasper = pathJasper;
	}
	
	

}//fin de la aplicacion FacEmpresaEmisoraControlador
