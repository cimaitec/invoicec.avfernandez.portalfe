package com.login.controladores;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacEmpresa;
import com.general.controladores.FacEncriptarcadenasControlador;
import com.general.entidades.FacCliente;
import com.general.entidades.FacGeneral;
import com.general.servicios.FacClienteServicios;
import com.usuario.entidades.FacLoginBitacora;
import com.usuario.entidades.FacUsuario;
import com.usuario.entidades.FacUsuarioPK;
import com.usuario.servicios.FacUsuarioServicio;

@ViewScoped
@ManagedBean
public class FacLoginControlador {
	
	@EJB
	private FacClienteServicios clienteServicio;

	@EJB
	private FacUsuarioServicio usuarioServicio;
	
	private String loginUsuario;
	private String rucCliente;
	
	private FacUsuario usuario; // variable que retiene la entidad del usuario
	private FacUsuarioPK Id;
	public String getRucCliente() {
		return rucCliente;
	}

	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}
	
	public String Ingresar(){
		return ingresar_usuario();
	}
	public String IngresarProveedor(){
		return ingresar_usuarioProveedor();
	}
	
	//TODO metodo que es llamado del boton ingresar para verificar si el usuario existe
	protected String ingresar_user(){
		String ReturnPagina = "";
		Boolean ResultadoUsuario = true;
		String Mensaje = "";
		try{
			System.out.println("ingresar_usuario...");
			
			/*if(Integer.parseInt(loginUsuario) < 1 && (ResultadoUsuario == true)){
				Mensaje = "Debe ingresar el Codigo del Cliente";
				ResultadoUsuario= false;
			}*/
			if(loginUsuario!=null)
				if(loginUsuario.equals("")  && (ResultadoUsuario == true)){
					Mensaje = "Debe ingresar el Codigo del Cliente";
					ResultadoUsuario= false;
				}
			
			if(rucCliente.equals("") && (ResultadoUsuario == true)){
				Mensaje = "Debe ingresar la Identificación";
				ResultadoUsuario= false;
			}

			FacCliente datosCliente = null;
			FacEmpresa datosEmpresa = null;
			FacGeneral datosGeneral = null;
			if(ResultadoUsuario == true){
				
				datosCliente = clienteServicio.buscaClienteporCodigo(loginUsuario);
				datosGeneral = clienteServicio.buscarParametroUnicoCodTabla("0","99");
				datosEmpresa = clienteServicio.buscarEmpresa(datosGeneral.getDescripcion());
				Mensaje = "Identificación y Codigo Cliente incorrectos";
			}
			
			System.out.println("  datosCliente: "+datosCliente);
			
			if(datosCliente != null){
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
		        if (session != null)
		        {
		            session.setAttribute("Cod_Cliente", loginUsuario);
		            session.setAttribute("Ruc_Cliente", rucCliente);
		            session.setAttribute("Nombre_Cliente", datosCliente.getRazonSocial());
		            session.setAttribute("Ruc_Empresa", datosEmpresa.getRuc());
		        }
				ReturnPagina = "/paginas/Consulta_Documentos/Con_Documento2.jsf";
				System.out.println("ReturnPagina..."+ReturnPagina);
			}else
			{
				FacesMessage mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,Mensaje, null);
				FacesContext.getCurrentInstance().addMessage("frm1", mensaje);
				ReturnPagina = "";
			}
		}catch (Exception e) {
			System.out.println("Error..."+e);
			e.printStackTrace();
		}
		return ReturnPagina;
	}

	//TODO metodo que es llamado del boton ingresar para verificar si el usuario existe
		protected String ingresar_usuarioProveedor(){
			String ReturnPagina = "/paginas/Administrador/Cuenta/LoginProveedor.jsf";
			Boolean ResultadoUsuario = true;
			String Mensaje = "";
			try{
				System.out.println("ingresar_usuarioProveedor...");
				if(rucCliente.equals("") && (ResultadoUsuario == true)){
					Mensaje = "Debe ingresar la Identificación";
					ResultadoUsuario= false;
				}
				
				FacCliente datosCliente = null;
				FacEmpresa datosEmpresa = null;
				FacGeneral datosGeneral = null;
				System.out.println("ResultadoUsuario..."+ResultadoUsuario);
				if(ResultadoUsuario == true){
					datosGeneral = clienteServicio.buscarParametroUnicoCodTabla("0","99");				
					datosEmpresa = clienteServicio.buscarEmpresa(datosGeneral.getDescripcion());
					datosCliente = clienteServicio.buscaUsuarioProveedor(rucCliente, datosGeneral.getDescripcion());
					Mensaje = "Identificación y Codigo Cliente incorrectos";
				}
				System.out.println("datosCliente..."+datosCliente);
				if(datosCliente != null){
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			        if (session != null)
			        {
			            session.setAttribute("Cod_Cliente", loginUsuario);
			            session.setAttribute("Ruc_Cliente", rucCliente);
			            session.setAttribute("Nombre_Cliente", datosCliente.getRazonSocial());
			            session.setAttribute("Ruc_Empresa", datosEmpresa.getRuc());
			        }
					ReturnPagina = "/paginas/Consulta_Documentos/Con_Documento2.jsf";
					System.out.println("ReturnPagina..."+ReturnPagina);
				}else
				{
					FacesMessage mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,Mensaje, null);
					FacesContext.getCurrentInstance().addMessage("frm1", mensaje);
					ReturnPagina = "/paginas/Administrador/Cuenta/LoginProveedor.jsf";
				}
			}catch (Exception e) {
				System.out.println("Error..."+e);
				e.printStackTrace();
			}
			return ReturnPagina;
		}
	
	protected String ingresar_usuario() {
		
		String ReturnPagina = "";
		Boolean ResultadoUsuario = true;
		String Mensaje = "";
		int liLoginFail = 0;

		try {
			
			if (rucCliente.equals("") && (ResultadoUsuario == true)) {
				Mensaje = "Debe ingresar el usuario";
				ResultadoUsuario = false;
				mensajeSistema(Mensaje, FacesMessage.SEVERITY_ERROR);
				return Mensaje;
			}
			if (rucCliente.length() < 4) {
				Mensaje = "Debe ingresar el usuario valido";
				ResultadoUsuario = false;
				mensajeSistema(Mensaje, FacesMessage.SEVERITY_ERROR);
				return Mensaje;
			}
			if (loginUsuario.equals("") && (ResultadoUsuario == true)) {
				Mensaje = "Debe ingresar la contraseña";
				ResultadoUsuario = false;
				mensajeSistema(Mensaje, FacesMessage.SEVERITY_ERROR);
				return Mensaje;
			}

			String usua = rucCliente;
			String contra = FacEncriptarcadenasControlador
					.encrypt(loginUsuario);

			List<FacUsuario> listaDatosUsuario = null;
			if (ResultadoUsuario) {
				usuario = new FacUsuario();
				Id = new FacUsuarioPK();
				Id.setCodUsuario(usua);
				// Id.setRuc(SeleccionaEmpresa);
				usuario.setId(Id);
				usuario.setPassword(contra);
				listaDatosUsuario = usuarioServicio.validarUsuario(usuario);
			}
			
			
			if (listaDatosUsuario == null) {
				Mensaje = "Usuario no existe o Usuario inactivo ";
				mensajeSistema(Mensaje, FacesMessage.SEVERITY_ERROR);
				return Mensaje;
			} else {
				boolean passCorrecto = false;
				for (FacUsuario datosUsuario : listaDatosUsuario) {
					if (FacEncriptarcadenasControlador
							.decrypt(datosUsuario.getPassword())
							.trim()
							.equals(FacEncriptarcadenasControlador.decrypt(
											usuario.getPassword()).trim())) {
		
								HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
								if (session != null) {
									session.setAttribute("Ruc_Cliente", datosUsuario.getId()
											.getCodUsuario());
									session.setAttribute("Nombre_Cliente",
											datosUsuario.getNombre());
									FacLoginBitacora facLoginBitacora = new FacLoginBitacora();
									facLoginBitacora.setId(usuarioServicio.geSecuencialLoginBitacora());
									facLoginBitacora.setRuc(datosUsuario.getId().getRuc());
									facLoginBitacora.setUsuario(datosUsuario.getId()
											.getCodUsuario());
									facLoginBitacora.setFechaInicioSesion(new Date());
									String remoteAddr = "";
									remoteAddr = ((HttpServletRequest) FacesContext
											.getCurrentInstance().getExternalContext()
											.getRequest()).getRemoteAddr();
		
									facLoginBitacora.setIpCliente(remoteAddr);
									usuarioServicio.ingresaLoginBitacora(facLoginBitacora);
		
									String estado = datosUsuario.getAutenticado();
									if (estado.equals("N"))
										ReturnPagina =  "resetpassword.jsf";
									else
									ReturnPagina = "/paginas/consulta/consulta.jsf";
									passCorrecto = true;
								}
							break;
						}
				
				} 
				if(!passCorrecto) {
					liLoginFail=listaDatosUsuario.get(0).getLoginErroneo();
					liLoginFail++;
					usuarioServicio.modificarLoginError(usuario, liLoginFail);
					Mensaje = "Contraseña incorrecta..";
					mensajeSistema(Mensaje, FacesMessage.SEVERITY_ERROR);
					return "";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ReturnPagina;
		
	}
	
	public void consultadoc()
	{
		try{
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
			ctx.redirect(ctxPath + "/paginas/consulta/consultadoc.jsf");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
		
		public void mensajeSistema (String Mensaje , Severity tipo){
			FacesMessage mensaje= new FacesMessage(tipo,Mensaje, null);
			FacesContext.getCurrentInstance().addMessage("frm1", mensaje);	
		}
		
	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

}
