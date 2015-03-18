package com.login.controladores;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacEmpresa;
import com.general.controladores.FacEncriptarcadenasControlador;
import com.general.entidades.FacCliente;
import com.general.entidades.FacClientePK;
import com.general.servicios.FacClienteServicios;
import com.general.servicios.FacDocumentoServicios;
import com.general.servicios.FacGeneralServicio;
//import com.usuario.entidades.FacLoginBitacora;
import com.usuario.entidades.FacUsuario;
import com.usuario.entidades.FacUsuarioPK;
import com.usuario.servicios.FacUsuarioServicio;
import com.webServices.cliente.ClienteWS;

@ViewScoped
@ManagedBean
public class FacCambioContrasenaControlador
{
	private String usuario;
	private String actualContrasena;
	private String nuevaContrasena;
	private String confirmarContrasena;
	private FacesContext context;
	private HttpSession sesion;
	
	private String seleccionEmpresa;
	private ArrayList<SelectItem> listaEmpresas;
	@EJB
	//private FacUsuarioServicio usuarioServicio;
	private FacUsuarioServicio UsuarioServicio;
	
	@EJB
	private FacGeneralServicio facGenSer;
	private HashMap<String, String> hashMap = null;
	
	@EJB
	private FacDocumentoServicios facDocumentoServicios;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getActualContrasena() {
		return actualContrasena;
	}
	public void setActualContrasena(String actualContrasena) {
		this.actualContrasena = actualContrasena;
	}
	
	public String getSeleccionEmpresa() {
		return seleccionEmpresa;
	}
	public void setSeleccionEmpresa(String seleccionEmpresa) {
		this.seleccionEmpresa = seleccionEmpresa;
	}
	
	public String getNuevaContrasena() {
		return nuevaContrasena;
	}
	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}
	
	public String getConfirmarContrasena() {
		return confirmarContrasena;
	}
	public void setConfirmarContrasena(String confirmarContrasena) {
		this.confirmarContrasena = confirmarContrasena;
	}
	
	public ArrayList<SelectItem> getListaEmpresas() {
		return listaEmpresas;
	}
	public void setListaEmpresas(ArrayList<SelectItem> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	
	public void cargaVariablesSesion()
	{
		context = FacesContext.getCurrentInstance();
		sesion = (HttpSession)context.getExternalContext().getSession(true);
		this.setUsuario(sesion.getAttribute("Ruc_Cliente").toString());
		//this.setSeleccionEmpresa(sesion.getAttribute("Ruc_Empresa").toString());
		//System.out.println(sesion.getAttribute("Ruc_Empresa").toString());
		System.out.println(this.getUsuario());
		//System.out.println(this.getSeleccionEmpresa());
	}
	
	public String cambiarContrasenaCliente()
	{
		System.out.println("Inicio cambio de contraseña");
		
		FacUsuario usuario = new FacUsuario();
		FacUsuarioPK usuariopk = new FacUsuarioPK();
		String mensaje = "";
		//HttpSession sesion;
		
		System.out.println("Comparacion contraseñas...");
		if(!this.getNuevaContrasena().equals(this.getConfirmarContrasena()))
		{
			mensaje = "La contraseña no coincide, Confirme la contraseña";
			//ResultadoUsuario= false;
			mensajeSistema (mensaje,FacesMessage.SEVERITY_ERROR);
			return mensaje;
		}
		//FacesContext context = FacesContext.getCurrentInstance();
		//sesion = (HttpSession) context.getExternalContext().getSession(true);
		
		//usuariopk.setRuc(sesion.getAttribute("Ruc_Empresa").toString());
		usuariopk.setCodUsuario(this.getUsuario());
		//clientePk.setRucCliente(this.getUsuario());
		usuario.setId(usuariopk);
		usuario.setPassword(FacEncriptarcadenasControlador.encrypt(this.getConfirmarContrasena()));
		/*
		System.out.println("Valido si usuario actual...");
		System.out.println(cliente.getId().getRuc());
		System.out.println(cliente.getId().getRucCliente());
		*/
		// -INI- Invocación a Servicio Web para actualizar clientes en Pycca
		try
		{
			UsuarioServicio.cambiarContrasena(usuario);
			/*
			ClienteWS cWs = new ClienteWS();
			cWs.setHostname("localhost");
			cWs.setPort(8080);
			cWs.setPath("http://localhost:8080/WebServicesFacturacion/WsFacturacionComprobantes?wsdl");
			cWs.setSoapAction("/WsFacturacionComprobantes/consultaComprobante");
			cWs.setCharset("utf-8");
			
			hashMap = facGenSer.buscarDatosGeneralPadreHash("200");
			String lsUsuario = hashMap.get("01");
			String lsClave = hashMap.get("02");
			String lsPeticion = hashMap.get("03");
			String lsHost = hashMap.get("04");
			String lsSoapAction = hashMap.get("05");
			//String lsLength = hashMap.get("06");
			String lsPort = hashMap.get("07");
			String lsCharset = hashMap.get("08");
			String lsPath = hashMap.get("09");
			
			lsPeticion = lsPeticion.replace("|RUC|", cliente.getId().getRucCliente());
			lsPeticion = lsPeticion.replace("|USUARIO|", lsUsuario);
			lsPeticion = lsPeticion.replace("|CLAVE|", lsClave);
			
			cWs.setHostname(lsHost);
			cWs.setPort(Integer.parseInt(lsPort));
			cWs.setPath(lsPath);
			cWs.setSoapAction(lsSoapAction);
			cWs.setCharset(lsCharset);
			cWs.setPostInfo(lsPeticion);
			
			String respuesta = cWs.doPost();
			System.out.println(respuesta);
			*/
		}catch(Exception e){
			System.out.println("Ocurrio un error al cambiar contraseña de usuario :"+usuario.getId().getCodUsuario() );
			e.printStackTrace();
		}
		// -FIN- Invocación a Servicio Web para actualizar clientes en Pycca
		
		return "login.jsf";
	}
	
	public void mensajeSistema (String Mensaje , Severity tipo){
		FacesMessage mensaje= new FacesMessage(tipo,Mensaje, null);
		FacesContext.getCurrentInstance().addMessage("frm1", mensaje);	
	}
	
	public void cargarListas()
	{
		cargaVariablesSesion();
		FacEmpresa facEmpresas = new FacEmpresa();
		listaEmpresas = new ArrayList<SelectItem>();
		List<FacEmpresa> EmpresaGeneral;
		
		EmpresaGeneral = fac_empresas();
		
		if(EmpresaGeneral.isEmpty()){
			facEmpresas.setRuc("0");
			facEmpresas.setRazonSocial("No hay datos de la empresa");
			EmpresaGeneral.add(facEmpresas);
		}else
			listaEmpresas.add(new SelectItem("0", "Seleccione la empresa"));

		for(int i = 0;i<EmpresaGeneral.size();i++)
			listaEmpresas.add(new SelectItem(EmpresaGeneral.get(i).getRuc(), EmpresaGeneral.get(i).getRazonSocial()));
	}
	
	
	public List<FacEmpresa> fac_empresas() {
		List<FacEmpresa> detalledocumento = new ArrayList<FacEmpresa>();
				
		try{			
			detalledocumento = facDocumentoServicios.listadoTodasEmpresas();    
			
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			e.printStackTrace();
		}
		return  detalledocumento;
	}

}
