package com.general.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacEstablecimiento;
import com.general.entidades.FacEstablecimientoPK;
import com.general.servicios.FacEstablecimientoServicios;


@ViewScoped
@ManagedBean
public class FacEstablecimientoControlador {
	
	//metodo de FacEstablecimientoServicio
	@EJB
	private FacEstablecimientoServicios estServicio;
	
	//variable
	private String Ruc;
	private String CodEstablecimiento;
	private String DireccionEstablecimiento;
	private boolean isActive;
	private String Correo;
	private String Mensaje;
	private boolean Modificar;
	private boolean codExiste;
	private boolean grisado;
	private boolean valido;
	private final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//metodo SelectItem
	private List<SelectItem> itemsRuc;
	
	//entidad de Establecimiento
	private FacEstablecimiento establecimiento;
	private FacEstablecimientoPK id;
	private FacEstablecimiento selectCampos;
	private FacEstablecimiento updateCampos;
	private List<FacEstablecimiento> listEstablecimiento;
	
	//metodo FacEmpresa
	private List<FacEmpresa> listEmpresa;
	
	//metodo de aplicacion
	
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
			
			this.setCodEstablecimiento(null);
			this.setDireccionEstablecimiento(null);
			this.setCorreo(null);
			this.setMensaje(null);
			isActive = true;
			
			this.llenarRucEmpresa();
			
			this.llenarTablaEstablecimiento();
			
			this.setModificar(false);
			this.setGrisado(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String NombreBoton){
		try {			
			if(isModificar()!=true){
				if((!getCodEstablecimiento().trim().equals(""))&&!getDireccionEstablecimiento().trim().equals("")&&!getCorreo().trim().equals("")
						&&isModificar()!=true&&isValido()!=false){

					establecimiento = new FacEstablecimiento();
					id = new FacEstablecimientoPK();
					id.setCodEstablecimiento(CodEstablecimiento);
					id.setRuc(Ruc);
					establecimiento.setId(id);
					establecimiento.setDireccionEstablecimiento(DireccionEstablecimiento);
					if(validar_correo(Correo)!=false)
						establecimiento.setCorreo(Correo);
					else{
						mensajeAlerta("Mensaje del sistema", "Correo Electronico: Esta incorrecto \n Verifique su correo", "peligro");
						return;
					}
					establecimiento.setMensaje(Mensaje);
					if(this.isActive()){
						establecimiento.setIsActive("Y");
					}else{
						establecimiento.setIsActive("0");
					}
					estServicio.insertEstablecimiento(establecimiento);
					cargarDatos();
					mensajeAlerta("Mensaje del sistema", "Se guardo con exito", "Informacion");
				}else{
					if(isCodExiste()!=false){
						mensajeAlerta("Mensaje del sistema", "Establecimiento:: Ya existente en este Ruc", "peligro");
						return;
					}else if(isValido()!=true){
						mensajeAlerta("Mensaje del sistema", "Establecimiento:: Solo se permite numero", "alerta");
						return;
					}
					else{
						mensajeAlerta("Mensaje del sistema", "Campos obligatorios estan vacio", "peligro");
						return;
					}
				}
			}else{
				if(!getDireccionEstablecimiento().trim().equals("")&&!getCorreo().trim().equals("")){
					establecimiento = new FacEstablecimiento();
					id = new FacEstablecimientoPK();
					id.setCodEstablecimiento(CodEstablecimiento);
					id.setRuc(Ruc);
					establecimiento.setId(id);
					establecimiento.setDireccionEstablecimiento(DireccionEstablecimiento);
					if(validar_correo(Correo)!=false){
					establecimiento.setCorreo(Correo);}else{
						mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Esta incorrecto \n Verifique su correo", "peligro");
						return;
					}
					establecimiento.setMensaje(Mensaje);
					if(isActive())
						establecimiento.setIsActive("Y");
					else
						establecimiento.setIsActive("0");
					
					estServicio.modificarDatos(establecimiento);
					this.setGrisado(false);
					this.setModificar(false);
					cargarDatos();
					mensajeAlerta("Mensaje del sistema", "Se modifico con exito", "Informacion");
				}else{
					mensajeAlerta("Mensaje del sistema", "Campos obligatorios estan vacio", "peligro");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void modificar(){
		try {
			this.setRuc(updateCampos.getId().getRuc());
			this.setCodEstablecimiento(updateCampos.getId().getCodEstablecimiento());
			this.setDireccionEstablecimiento(updateCampos.getDireccionEstablecimiento());
			this.setCorreo(updateCampos.getCorreo());
			this.setMensaje(updateCampos.getMensaje());
			if(updateCampos.getIsActive().trim().equals("ACTIVO")){
				this.setActive(true);				
			}else{
				this.setActive(false);
			}
			this.setModificar(true);
			this.setGrisado(true);
		} catch (Exception e) {
		}
	}
	
	public void llenarRucEmpresa(){
		itemsRuc = new ArrayList<SelectItem>();
		try {
			listEmpresa = buscarRuc();
			if(listEmpresa.isEmpty()){
				listEmpresa=null;
			}
			
			for(int i=0;i<listEmpresa.size();i++)
				itemsRuc.add(new SelectItem(listEmpresa.get(i).getRuc(),listEmpresa.get(i).getRazonSocial()));
			
		} catch (Exception e) {
		}
	}
	
	public void llenarTablaEstablecimiento(){
		try {
			listEstablecimiento = buscarDatos();
			
			for(int i =0 ;i< listEstablecimiento.size();i++){
				if(listEstablecimiento.get(i).getIsActive().trim().equals("Y"))
					listEstablecimiento.get(i).setIsActive("ACTIVO");
				else
					listEstablecimiento.get(i).setIsActive("INACTIVO");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<FacEmpresa> buscarRuc(){
		List<FacEmpresa> empresa = null;
		try {
			empresa = estServicio.bucarRuc();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return empresa;
	}
	
	public List<FacEstablecimiento> buscarDatos(){
		List<FacEstablecimiento> establecimiento = null;
		try {
			establecimiento = estServicio.buscardatosEstablecimiento();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return establecimiento;
	}
	public FacEstablecimiento buscarCodEsatblecimiento(String ruc,String codEstablecimiento){
		FacEstablecimiento buscar= null;
		try {
			buscar = estServicio.buscarCodEstablecimiento(ruc,codEstablecimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return buscar;
	}
	
	public void valiadarEstablecimiento(){
		establecimiento = new FacEstablecimiento();
		
		try {
			String texto = CodEstablecimiento;
			for(char x: texto.toCharArray()){
				if(Character.isDigit(x)){
					this.setValido(true);
				}
				if(Character.isLetter(x)){
					this.setValido(false);
					mensajeAlerta("Mensaje del sistema", "Establecimiento:: solo s ingresa numeros", "peligro");
					break;
				}
			}
			establecimiento = buscarCodEsatblecimiento(this.getRuc().trim(),this.getCodEstablecimiento().trim());
			if(establecimiento!=null){
				this.setCodExiste(true);
				mensajeAlerta("Mensaje del sistema", "EL Establecimiento ingresado ya existente en este Ruc", "peligro");
			}else
				this.setCodExiste(false);
			
		} catch (Exception e) {
		}
	}
	
	
	public boolean validar_correo(String email){
		 // Compiles the given regular expression into a pattern.
       Pattern pattern = Pattern.compile(PATTERN_EMAIL);

       // Match the given input against this pattern
       Matcher matcher = pattern.matcher(email);
       return matcher.matches();
	}
	
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
		 FacesContext context = FacesContext.getCurrentInstance();            
	     context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
   }
	
	//fin de metodode aplicacion
	
	//metodos de set y get
	public FacEstablecimientoServicios getEstServicio() {
		return estServicio;
	}
	public void setEstServicio(FacEstablecimientoServicios estServicio) {
		this.estServicio = estServicio;
	}
	public String getRuc() {
		return Ruc;
	}
	public void setRuc(String ruc) {
		Ruc = ruc;
	}
	public String getCodEstablecimiento() {
		return CodEstablecimiento;
	}
	public void setCodEstablecimiento(String codEstablecimiento) {
		CodEstablecimiento = codEstablecimiento;
	}
	public String getDireccionEstablecimiento() {
		return DireccionEstablecimiento;
	}
	public void setDireccionEstablecimiento(String direccionEstablecimiento) {
		DireccionEstablecimiento = direccionEstablecimiento;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCorreo() {
		return Correo;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}
	public String getMensaje() {
		return Mensaje;
	}
	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}
	public boolean isModificar() {
		return Modificar;
	}
	public void setModificar(boolean modificar) {
		Modificar = modificar;
	}
	public List<SelectItem> getItemsRuc() {
		return itemsRuc;
	}
	public void setItemsRuc(List<SelectItem> itemsRuc) {
		this.itemsRuc = itemsRuc;
	}
	public FacEstablecimiento getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(FacEstablecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	public FacEstablecimientoPK getId() {
		return id;
	}
	public void setId(FacEstablecimientoPK id) {
		this.id = id;
	}
	public FacEstablecimiento getSelectCampos() {
		return selectCampos;
	}
	public void setSelectCampos(FacEstablecimiento selectCampos) {
		this.selectCampos = selectCampos;
	}
	public FacEstablecimiento getUpdateCampos() {
		return updateCampos;
	}
	public void setUpdateCampos(FacEstablecimiento updateCampos) {
		this.updateCampos = updateCampos;
	}
	public List<FacEmpresa> getListEmpresa() {
		return listEmpresa;
	}
	public void setListEmpresa(List<FacEmpresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}

	public List<FacEstablecimiento> getListEstablecimiento() {
		return listEstablecimiento;
	}

	public void setListEstablecimiento(List<FacEstablecimiento> listEstablecimiento) {
		this.listEstablecimiento = listEstablecimiento;
	}

	public boolean isCodExiste() {
		return codExiste;
	}

	public void setCodExiste(boolean codExiste) {
		this.codExiste = codExiste;
	}

	public boolean isGrisado() {
		return grisado;
	}

	public void setGrisado(boolean grisado) {
		this.grisado = grisado;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

}//fin de class FacEstablecimineto
