package com.general.controladores;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;

import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacClavescontingencia;
import com.general.servicios.FacClaveContingenciaServicios;



@ViewScoped
@ManagedBean
public class clavesContingenciaControlador {

	@EJB
	private FacClaveContingenciaServicios servicio;
	private FacEmpresa Empresa;
	private String ambiente;
	private FacClavescontingencia entidadClave;
	
	public void cargarDatos(){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
			if(sesion.getAttribute("Ruc_Empresa") == null)
			{
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
				try {
					ctx.redirect(ctxPath + "/paginas/Administrador/Cuenta/Login.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// datos empresa emisora
			cargarEmpresa(sesion.getAttribute("Ruc_Empresa").toString().trim());
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	

    public void handleFileUpload(FileUploadEvent event) {
    	try {
    		BufferedReader leerInsert=null;
    		BufferedReader leerNumero=null;
    		FileReader file = null;
    		FileReader file2 = null;
    		boolean mostar=false;
    		
    		if(ambiente.equals("0")){
    			FacesMessage msg = new FacesMessage("Seleccionar Tipo de ambiente", "");
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			return;
    		}

    		file = new FileReader(event.getFile().getFileName());
    		file2 = new FileReader(event.getFile().getFileName());
    		leerInsert = new BufferedReader(file);
    		leerNumero = new BufferedReader(file2);
    		String linea ="";
    		int length = 1;
    		while((linea=leerNumero.readLine())!=null){
    			for (char x : linea.toCharArray()) {
    				if (Character.isLetter(x)) {
    					FacesMessage mensaje = null;
    					mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "EL txt no se puede ingresar", "Solo numeros en el txt");
    					FacesContext.getCurrentInstance().addMessage("Campo ",	mensaje);
    					return;
    				}
    			}
    		}
    		linea = "";
    		
    		while ((linea=leerInsert.readLine())!=null) {
    			int corte = 13;
    			int corteFin = 37;
    			int digitos = linea.length();
    			String ruc="";
    			String clave="";
    			for (int i = 0; i < digitos; i++) {
    				if(i<corte){
    					ruc = ruc+linea.charAt(i);
    				}
    				if(i<=corteFin){
    					clave = clave+linea.charAt(i);
    				}
    				if((i+1)==corteFin){
    					entidadClave = new FacClavescontingencia();
    					entidadClave.setClave(clave);
    					entidadClave.setEstado("0");
    					entidadClave.setRuc(ruc);
    					entidadClave.setTipo(ambiente);
    					entidadClave.setIdclavecontingencia(servicio.lengthTabla());
    					try {
    						servicio.IngresarClaveContingencia(entidadClave);
    						mostar=true;
						} catch (Exception e) {
							mostar=false;
							e.printStackTrace();
							FacesMessage msg = new FacesMessage("Error al guardar", length+" "+e.getLocalizedMessage());
							FacesContext.getCurrentInstance().addMessage(null, msg);
							return;
						}
    					corte=corteFin+13;
    					corteFin=37+corteFin;
        				clave="";ruc="";
    				}
				}
    			length++;
    		}
    		if (mostar) {
    	    	FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " Se genero Correctamnete ");
    			FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("Error generar el txt ","");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

    //contructor que carga los datos de la empresa emisora
	private FacEmpresa cargarEmpresa(String ruc){
		Empresa = new FacEmpresa();
		try{
			Empresa = servicio.verificarRuc(ruc);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Empresa;
	}

	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

}
