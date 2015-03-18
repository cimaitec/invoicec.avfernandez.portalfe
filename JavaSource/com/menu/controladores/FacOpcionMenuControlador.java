package com.menu.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacEmpresa;
import com.menu.entidades.FacOpcionMenu;
import com.menu.entidades.FacRolesOpcionMenu;
import com.menu.servicios.FacOpcionMenuServicios;
import com.usuario.entidades.FacUsuariosRole;


@ViewScoped
@ManagedBean
@SessionScoped
public class FacOpcionMenuControlador {

	@EJB
	private FacOpcionMenuServicios opcionesMenu;
	
	private MenuModel menuModel;
	private String loginUsuario;
	protected String id_usua;
	private String Ruc;
	private String nombreEmpresa;
	private String razonSocial;
	private FacesContext context;
	private HttpSession sesion;
		
	public void cerrarSesion(){
		//1. Borrar mis objetos de sesion
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioDataManager");
		//2. Redireccionar a login
		//una puede ser con un return
		try{
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
			ctx.redirect(ctxPath + "/paginas/cuenta/login.jsf");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void cambiarClave()
	{
		try{
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
			ctx.redirect(ctxPath + "/paginas/cuenta/resetpassword.jsf");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// contructor que genera el menu dinamico
	 private void geraMenu() {
		 try{
			
			 
			List<FacUsuariosRole> usuarioRoles = new ArrayList<FacUsuariosRole>();
			List<FacRolesOpcionMenu> rolesxOpcion = new ArrayList<FacRolesOpcionMenu>();
			usuarioRoles = opcionesMenu.buscarRolxUsuario(id_usua, Ruc);
			if(usuarioRoles.isEmpty())
				return;
				
				rolesxOpcion = opcionesMenu.buscarrolxOpcion(usuarioRoles);
				
			 menuModel = new DefaultMenuModel();
			 
			 List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
			 listaMenu = opcionesMenu.listaTodos(rolesxOpcion);
			 if(!listaMenu.isEmpty())
				 for (FacOpcionMenu menu : listaMenu) {//// padre
					 if (menu.getCodOpcionMenuPadre().equals("00")) {
						 Submenu submenu = new Submenu();
						 submenu.setLabel(menu.getDescripcion());
						 List<FacOpcionMenu> submenuItem =  opcionesMenu.buscaPorMenu(menu);
						 for (FacOpcionMenu m : validarporitemMenu(submenuItem, rolesxOpcion)) {/// menu hijo
							 if (!opcionesMenu.verificaSubMenu(m)) {
								 MenuItem itemMenu = new MenuItem();
								 itemMenu.setValue(m.getDescripcion());
								 itemMenu.setUrl(m.getUrlPages());
								 itemMenu.setUpdate("FormularioPrincipal");
								 submenu.getChildren().add(itemMenu);
							 	} else {
							 		Submenu sm = new Submenu();
							 		sm.setLabel(m.getDescripcion());
							 		sm = geraSubmenu(m, rolesxOpcion);
							 		submenu.getChildren().add(sm);
							 	}
						 }
						menuModel.addSubmenu(submenu);			
					}
			 }
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	 }
	 
	// listado de detalle de documento
	private Submenu geraSubmenu(FacOpcionMenu menu , List<FacRolesOpcionMenu> rolesxOpcion) {
		 Submenu submenu = new Submenu();
		 submenu.setLabel(menu.getDescripcion());
		 List<FacOpcionMenu> submenuItem =  opcionesMenu.buscaPorMenu(menu);
		 for (FacOpcionMenu m : validarporitemMenu(submenuItem, rolesxOpcion)) {
			 if (!opcionesMenu.verificaSubMenu(m)) {
				 MenuItem mi = new MenuItem();
				 mi.setValue(m.getDescripcion());
				 mi.setUrl(m.getUrlPages());
				 mi.setUpdate("FormularioPrincipal");
				 submenu.getChildren().add(mi);
			} else {
				submenu.getChildren().add(geraSubmenu(m, rolesxOpcion));
			}
		 }
		 return submenu;
	 }

	private List<FacOpcionMenu>  validarporitemMenu(List<FacOpcionMenu> submenuItem  , List<FacRolesOpcionMenu> rolesxOpcion){
		 List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
		 
		 try{
			 for(FacOpcionMenu listaItemMenu : submenuItem)
				 for(FacRolesOpcionMenu rolesXOpcion : rolesxOpcion)
					if(listaItemMenu.getCodOpcionMenu().trim().equals(rolesXOpcion.getId().getCodOpcionMenu().trim()))
						listaMenu.add(listaItemMenu);
			 
		 }catch (Exception e) {
				FacesMessage mensaje=null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		 
		 return listaMenu;
	 }

	 public FacOpcionMenuServicios getOpcionesMenu() {
		return opcionesMenu;
	}

	public void setOpcionesMenu(FacOpcionMenuServicios opcionesMenu) {
		this.opcionesMenu = opcionesMenu;
	}

	public MenuModel getMenuModel() {
		//geraMenu();
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	
	public String getRazonSocial() throws IOException {
		
		context = FacesContext.getCurrentInstance();
		sesion = (HttpSession)context.getExternalContext().getSession(true);
		
		if(sesion.getAttribute("Ruc_Cliente") != null){
			razonSocial = sesion.getAttribute("Nombre_Cliente").toString();
		}
		else
		{
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
			ctx.redirect(ctxPath + "/paginas/cuenta/login.jsf");
		}
		
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getLoginUsuario() throws IOException {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getNombreEmpresa() {
		/*context = FacesContext.getCurrentInstance();
		sesion = (HttpSession)context.getExternalContext().getSession(true);
		if(sesion.getAttribute("id_usuario") != null){
			nombreEmpresa = sesion.getAttribute("NombreEmpresa").toString();
		}*/
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	
	
}
