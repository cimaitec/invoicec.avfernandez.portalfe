package com.roles.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.menu.entidades.FacOpcionMenu;
import com.menu.entidades.FacRolesOpcionMenu;
import com.menu.entidades.FacRolesOpcionMenuPK;
import com.menu.servicios.FacOpcionMenuServicios;
import com.usuario.entidades.FacRole;
import com.usuario.servicios.FacRolesServicio;



@ViewScoped
@ManagedBean
@SessionScoped
public class Fac_roles_menu_controladores {

	@EJB
	private FacOpcionMenuServicios opcionesMenu;
	
	@EJB
	private FacRolesServicio rolServicio;
	
	private TreeNode ArbolOpciones;// variable de arbol principal de las opciones
	private List<FacRole> cargarDetalleRol;// variable que carga el datatable que son todos los roles
	private List<FacRole> filteredRol; // variable que se encarga de filtrar la los roles
	private FacRole verCampos;// variable que se encarga de recoger el registro que el usuario selecciono del rol
	private TreeNode[] selectedNodes;// variable que recoge un arreglo de los diferentes opciones del arbol
	
	private String cod_rol; // variable que es el cod_rol
	private String descripcion;// variable que es la descripcion del rol

	
	//TODO contructor que hace es cargar las opciones de la empresa y eso cargar a una variable de arbol
	private void Cargararbolprueba() throws Exception{
		ArbolOpciones = new DefaultTreeNode("FacOpcionMenu", null);
		 List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
		 List<FacRolesOpcionMenu> rolesxOpcion = new ArrayList<FacRolesOpcionMenu>();
		 rolesxOpcion = opcionesMenu.buscarrolxopcionMantenimiento(cod_rol);
		 listaMenu = opcionesMenu.listatodos_menu();
		 if(!listaMenu.isEmpty())
			 for (FacOpcionMenu menu : listaMenu) {
				 TreeNode treeNode = new DefaultTreeNode(menu.getDescripcion(), ArbolOpciones);
				 List<FacOpcionMenu> submenuItem =  opcionesMenu.buscaPorMenu(menu);
				 int contador = 0;
				 for (FacOpcionMenu m : validarporitemMenuprueba(submenuItem, rolesxOpcion,treeNode, submenuItem.size())) {
					 TreeNode tr = new DefaultTreeNode();
					 if (!opcionesMenu.verificaSubMenu(m)) {
						 tr = new DefaultTreeNode(m.getDescripcion(), treeNode);
						 tr.setSelected(true);
					 }
					 else
						 tr.getChildren().add(geraSubmenu(treeNode, m, rolesxOpcion));
					 
					 
					 if(m.getIsActive().trim().equals("-1")){
						 tr.setSelected(false);
					 }
				 }
				 for (int i = 0; i < treeNode.getChildCount(); i++) 
						if(treeNode.getChildren().get(i).isSelected())
							contador++;
				 
				 if(treeNode.getChildCount() == contador)
					 treeNode.setSelected(true);
			 }
	}
	
	private TreeNode geraSubmenu(TreeNode tr1, FacOpcionMenu listaItemMenu, List<FacRolesOpcionMenu> rolesxOpcion){
		TreeNode treeNode = new DefaultTreeNode(listaItemMenu.getDescripcion(), tr1); ;
		try{
			List<FacOpcionMenu> submenuItem =  opcionesMenu.buscaPorMenu(listaItemMenu);
			int contador = 0;
			 for (FacOpcionMenu m : validarporitemMenuprueba(submenuItem, rolesxOpcion,treeNode, submenuItem.size())) {
				 TreeNode tr = new DefaultTreeNode();
				 if (!opcionesMenu.verificaSubMenu(m)) {
					 tr = new DefaultTreeNode(m.getDescripcion(), treeNode);
					 tr.setSelected(true);
				 } 
				 else
				 {
					 tr.getChildren().add(geraSubmenu(treeNode, m, rolesxOpcion));
				 }
				 if(m.getIsActive().trim().equals("-1")){
					 tr.setSelected(false);
				 }
			 }
			 for (int i = 0; i < treeNode.getChildCount(); i++) 
					if(treeNode.getChildren().get(i).isSelected())
						contador++;
			 if(treeNode.getChildCount() == contador)
				 treeNode.setSelected(true);
		}catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return treeNode;
	}
			
	//TODO contructor que hace es valdar los items del menu de opciones
	private List<FacOpcionMenu>  validarporitemMenuprueba(List<FacOpcionMenu> submenuItem  , List<FacRolesOpcionMenu> rolesxOpcion,  TreeNode treeNode, int CantidadItem){
		List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
		List<String> listaMenu2 = new ArrayList<String>();
		 try{
			 for(FacOpcionMenu listaItemMenu : submenuItem){			 
				 if(!rolesxOpcion.isEmpty())
					 for(FacRolesOpcionMenu rolesXOpcion : rolesxOpcion)
						if(listaItemMenu.getCodOpcionMenu().trim().equals(rolesXOpcion.getId().getCodOpcionMenu().trim())){
							listaMenu.add(listaItemMenu);
							listaMenu2.add(listaItemMenu.getCodOpcionMenu().trim());
						}
			 }
			for (FacOpcionMenu facOpcionMenu2 : submenuItem) 
				if(!listaMenu2.contains(facOpcionMenu2.getCodOpcionMenu().trim())){
					facOpcionMenu2.setIsActive("-1");
					listaMenu.add(facOpcionMenu2);
				}
			 				
		 }catch (Exception e) {
				FacesMessage mensaje=null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		 return listaMenu;
	 }
	
	// contructor que se encarga de cuando seleccionamos el rol le asigna a las variables y carga las opciones del menu
	 public void seleccionarRol() {  
		 cod_rol = verCampos.getCodRol();
		 descripcion = verCampos.getDescripcion();
		 if(cod_rol != ""){
			 try {
				Cargararbolprueba();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
     }  
		
	//TODO contructor que me permite cargar todos roles de la empresa
	public void Cargar_Roles(String filtro){
		try {
			if(filtro.toString().trim().equals("arboles")){
				cargarDetalleRol = rolServicio.buscarDatos();
			}
			
			
			} catch (Exception e) {
				FacesMessage mensaje=null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			}
			
	}
	
	// TODO limpiando los registros
	private void LimpiandoReguistro(){
		cod_rol = null;
		descripcion = null;
		ArbolOpciones = null;
	}
	
	//TODO contructor de mensaje de alerta
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
		 FacesContext context = FacesContext.getCurrentInstance();            
	     context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
	}
	
	//TODO contructor que se encarga de guardar los cambios en la BD
	public void GuardarDatos(String estado_control){
		try{
			
			if(estado_control.toString().equals("cancelar"))
				return;
			
			
			if(cod_rol == null)
			{
				mensajeAlerta("Mensaje del sistema", "No a seleccionado el ROL", "peligro");
				return;
			}
			
			
			// recorriendo las opciones de menu para eliminar
			for(FacRolesOpcionMenu rol_opcion : opcionesMenu.buscarrolxopcionMantenimiento(cod_rol))
				opcionesMenu.EliminarRolOpcionesMenu(rol_opcion);

			
			// insertando rol de opciones de menu
			List<FacOpcionMenu> listatodos_opcionesMenu = opcionesMenu.listatodos_opcionesMenu();
			for (FacOpcionMenu opciones : listatodos_opcionesMenu) {
//				if(opciones.getCodOpcionMenuPadre().toString().trim().equals("00")){
//					FacRolesOpcionMenuPK listRolesOpcionMenu_Pk = new FacRolesOpcionMenuPK();
//					listRolesOpcionMenu_Pk.setCodOpcionMenu(opciones.getCodOpcionMenu());
//					listRolesOpcionMenu_Pk.setCodRol(cod_rol);
//					FacRolesOpcionMenu listRolesOpcionMenu = new FacRolesOpcionMenu();
//					listRolesOpcionMenu.setId(listRolesOpcionMenu_Pk);
//					opcionesMenu.insertarRolOpcionesMenu(listRolesOpcionMenu);
//
//				}
				for (int i = 0; i < selectedNodes.length; i++) {
						if(opciones.getDescripcion().toString().equals(selectedNodes[i].toString()))
						{
							FacRolesOpcionMenuPK listRolesOpcionMenu_Pk = new FacRolesOpcionMenuPK();
							listRolesOpcionMenu_Pk.setCodOpcionMenu(opciones.getCodOpcionMenu());
							listRolesOpcionMenu_Pk.setCodRol(cod_rol);
							FacRolesOpcionMenu listRolesOpcionMenu = new FacRolesOpcionMenu();
							listRolesOpcionMenu.setId(listRolesOpcionMenu_Pk);
							opcionesMenu.insertarRolOpcionesMenu(listRolesOpcionMenu);
						}
				}
			}
			//opcionesMenu.filtrarRolesPadres(cod_rol);
			for(String rol_opcion : opcionesMenu.filtrarRolesPadres(cod_rol))
			{
				System.out.println("menu = " + rol_opcion.toString());
				FacRolesOpcionMenuPK listRolesOpcionMenu_Pk = new FacRolesOpcionMenuPK();
				listRolesOpcionMenu_Pk.setCodOpcionMenu(rol_opcion.toString());
				listRolesOpcionMenu_Pk.setCodRol(cod_rol);
				FacRolesOpcionMenu listRolesOpcionMenu = new FacRolesOpcionMenu();
				listRolesOpcionMenu.setId(listRolesOpcionMenu_Pk);
				opcionesMenu.insertarRolOpcionesMenu(listRolesOpcionMenu);
			}
			
			LimpiandoReguistro();
			mensajeAlerta("Mensaje del sistema","Registro Guardado con exito", "informacion");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCod_rol() {
		return cod_rol;
	}
	public void setCod_rol(String cod_rol) {
		this.cod_rol = cod_rol;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<FacRole> getcargarDetalleRol() {
		return cargarDetalleRol;
	}

	public void setcargarDetalleRol(List<FacRole> cargardetalleRol) {
		this.cargarDetalleRol = cargardetalleRol;
	}

	public List<FacRole> getFilteredRol() {
		return filteredRol;
	}

	public void setFilteredRol(List<FacRole> filteredRol) {
		this.filteredRol = filteredRol;
	}

	public FacRole getVerCampos() {
		return verCampos;
	}

	public void setVerCampos(FacRole verCampos) {
		this.verCampos = verCampos;
	}

	public FacRolesServicio getRolServicio() {
		return rolServicio;
	}

	public void setRolServicio(FacRolesServicio rolServicio) {
		this.rolServicio = rolServicio;
	}
	
	public TreeNode getarbolOpciones() {
		return ArbolOpciones;
	}

	public void setarbolOpciones(TreeNode ArbolOpciones) {
		this.ArbolOpciones = ArbolOpciones;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

}
