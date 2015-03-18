package com.roles.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;



import com.usuario.entidades.FacRole;
import com.usuario.servicios.FacRolesServicio;

@ViewScoped
@ManagedBean
public class FacRolesControlador {

	//variables
	private String CodRol;
	private String Descripcion;
	private String isActive;
	private boolean modificar;
	private boolean rolExiste;
	private boolean requiered;
	
	//entidad FacRoles
	private FacRole role;
	private FacRole rolmodificar;
	private List<FacRole> listRoles;
	
	//metodo servicio
	@EJB
	private FacRolesServicio rolServicio;
	
	//metodo de SelectIten
	private List<SelectItem> li_estado;

	
	//metodos de aplicacion
	public void cargarDatos(){
		try {
			this.llenaComboEstado();
			this.llenarTablaRol();
			this.setModificar(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String NombreEvento ){
		try {

			if(isModificar()!=true){
				if(rolExiste!=true){
				role=new FacRole();
				role.setCodRol(CodRol);
				role.setDescripcion(Descripcion);
				role.setIsActive(isActive);
				rolServicio.insert(role);
				mensajeAlerta("Mensaje del sistema", "Registro ingresado con exito", "informacion");
				this.setCodRol(null);
				this.setDescripcion(null);
				this.cargarDatos();
				requiered=false;
				}else{
					if(CodRol.trim().equals(null)){
						mensajeAlerta("Mensaje del sistema", "Codigo de Rol:: Campos vacio", "peligro");
					}else{
						mensajeAlerta("Mensaje del sistema", "Codigo de Rol:: Ya Existente", "alerta");
					}
				}
			}else{
				role=new FacRole();
				role.setCodRol(CodRol);
				role.setDescripcion(Descripcion);
				role.setIsActive(isActive);
				rolServicio.modificarDatos(role);
				mensajeAlerta("Mensaje del sistema", "Registro modificado con exito", "informacion");
				this.setCodRol(null);
				this.setDescripcion(null);
				this.setModificar(false);
				requiered=false;
				cargarDatos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void verificarRol(){
		FacRole rol= null;
		requiered=true;
		try {
			rol = rolServicio.verificarCodRol(CodRol);
			if(rol!=null){
				mensajeAlerta("Mensaje del sistema", "Codigo de Rol:: Ya Existente", "alerta");
				rolExiste=true;
			}else{
				rolExiste=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarTablaRol(){
		listRoles = new ArrayList<FacRole>();
		try {
			listRoles = rolServicio.buscarDatos();
			for(int i=0;i<listRoles.size();i++){
				if(listRoles.get(i).getIsActive().trim().equals("Y")){
					listRoles.get(i).setIsActive("Activo");
				}else{
					listRoles.get(i).setIsActive("Inactivo");
				}
			}
			
		} catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	public void llenaComboEstado(){
		 li_estado = new ArrayList<SelectItem>();  
		try{
			
			li_estado.add(new SelectItem("1","Activo"));
			li_estado.add(new SelectItem("0","Inactivo"));
						
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		
	}

	public void modificar(){
		try {
			this.setCodRol(rolmodificar.getCodRol());
			this.setDescripcion(rolmodificar.getDescripcion());
			if(rolmodificar.getIsActive().trim().equals("Activo")){
				this.setIsActive("Y");
			}else{
				this.setIsActive("0");
			}
			this.setModificar(true);
		} catch (Exception e) {
			this.setCodRol(rolmodificar.getCodRol());
			this.setDescripcion(rolmodificar.getDescripcion());
			e.printStackTrace();
		}
	}
	
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
		 FacesContext context = FacesContext.getCurrentInstance();            
	      context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
   }
	
	//metodos de get and set
	public String getCodRol() {
		return CodRol;
	}

	public void setCodRol(String codRol) {
		CodRol = codRol;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public FacRole getRole() {
		return role;
	}

	public void setRole(FacRole role) {
		this.role = role;
	}

	public FacRolesServicio getRolServicio() {
		return rolServicio;
	}

	public void setRolServicio(FacRolesServicio rolServicio) {
		this.rolServicio = rolServicio;
	}

	public List<FacRole> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<FacRole> listRoles) {
		this.listRoles = listRoles;
	}

	public List<SelectItem> getLi_estado() {
		return li_estado;
	}

	public void setLi_estado(List<SelectItem> li_estado) {
		this.li_estado = li_estado;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isRequiered() {
		return requiered;
	}

	public void setRequiered(boolean requiered) {
		this.requiered = requiered;
	}

	public FacRole getRolmodificar() {
		return rolmodificar;
	}

	public void setRolmodificar(FacRole rolmodificar) {
		this.rolmodificar = rolmodificar;
	}

	public boolean isRolExiste() {
		return rolExiste;
	}

	public void setRolExiste(boolean rolExiste) {
		this.rolExiste = rolExiste;
	}

}//fin del metodo 
