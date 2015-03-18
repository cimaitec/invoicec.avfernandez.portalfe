/**
 * 
 */
package com.usuario.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.usuario.entidades.FacUsuariosEstablecimiento;
import com.usuario.entidades.FacUsuariosEstablecimientoPK;
import com.usuario.entidades.FacUsuario;
import com.usuario.servicios.FacUsuarioEstablecimientoServicio;



@ViewScoped
@ManagedBean
public class FacUsuarioEstablecimientoControlador {

	@EJB
	private FacUsuarioEstablecimientoServicio facUsuarioEstablecimientoServicio;

	private String ruc;
	private String codUsuario;
	private String codEstablecimiento;
	private String tipoAcceso;
	private boolean guardar;
	private boolean activarBtn;
	private boolean Modificar;

	// metodos lista
	private SelectItem listItems;
	private List<SelectItem> /*li_usuario, li_establecimiento,*/ li_tiposAcceso, li_Ruc;
	//entidades Usuario
	private FacUsuario usuario;
	private List<FacUsuario> listfUsuario;
	private FacUsuario selectUsuario;
	private FacUsuario filtarUsuario;
	// entidades usuarios establecimiento
	private FacUsuariosEstablecimiento UEstablecimiento;
	private FacUsuariosEstablecimientoPK id;
	private List<FacUsuariosEstablecimiento> li_UEstablecimiento;
	private FacUsuariosEstablecimiento seleccionarCampos;
	// entidades establecimiento
	private FacEstablecimiento establecimiento;
	private List<FacEstablecimiento> listEstablecimiento;
	private FacEstablecimiento selectEstabl;
	private FacEstablecimiento filtarEstabl;
	// entidades empresa 
	private List<FacEmpresa> listaEmpresa;

	//entidades Usuario
	private FacCliente cliente;
	private List<FacCliente> listfcliente;
	private FacCliente selectcliente;
	private FacCliente filtarcliente;
	
	//metodos de aplicación
	
	public void CargarDatos() {
		try {
			ruc = "0";
			this.llenaComboRuc();
			
			this.llenarTiposAcceso();
             ///para mostrar la tabla con los datos existentes
			guardar=false;
			activarBtn = true;
			Modificar = false;
			codEstablecimiento="";
			codUsuario="";
			
			this.llenarTablaUEstablecimiento();
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}

	public void inserto(String NombreEvento) {

		if(ruc.trim().equals("0")){
			mensajeAlerta("Mensaje del sistema", "Empresa:: Seleccione la empresa", "peligro");
			return;
		}
		if(codUsuario == "" || codUsuario == null){
			mensajeAlerta("Mensaje del sistema", "Usuario:: seleccione al usuario", "peligro");
			return;
		}
		if(codEstablecimiento == "" || codEstablecimiento == null){
			mensajeAlerta("Mensaje del sistema", "Establecimiento:: seleccione el establecimiento", "peligro");
			return;
		}
		try {
			UEstablecimiento = new FacUsuariosEstablecimiento();
 
			id = new FacUsuariosEstablecimientoPK();
			id.setRuc(ruc);
			id.setCodUsuario(codUsuario);
			id.setCodEstablecimiento(codEstablecimiento);
			UEstablecimiento.setId(id);
			UEstablecimiento.setTipoAcceso(tipoAcceso);
			UEstablecimiento.setIsActive("Y");
			
			if(Modificar)
				facUsuarioEstablecimientoServicio.ModificarUsuarioEstablecimiento(UEstablecimiento);
			else
				facUsuarioEstablecimientoServicio.insertarUsuarioEstablecimiento(UEstablecimiento);

			mensajeAlerta("Mensaje del sistema", "Registro "+ (Modificar ? "Modificado" : "Ingresado") +" con exito", "Informacion");
			CargarDatos();
			ruc="0";
		} catch (Exception e) {
			activarBtn=false;
			mensajeAlerta("Mensaje del sistema", "El registro ingresado ya existe", "alerta");
		}
	}

	//TODO contructor de mensaje de alerta para mostrar al usuario
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
	       FacesContext context = FacesContext.getCurrentInstance();            
	       context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
	}
	
	public List<FacEstablecimiento> buscarDatosEstablecimiento(String ruc) {

		List<FacEstablecimiento> listaEstablecimiento = null;

		try {
			listaEstablecimiento = facUsuarioEstablecimientoServicio.buscarTodosDatosEstablecimiento(ruc);

		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return listaEstablecimiento;
	}

	public FacUsuario buscarUsuarioPorCodUsuario(String codUsuario) {
		try {
			usuario = new FacUsuario();
			 usuario = facUsuarioEstablecimientoServicio.buscarRucPorCodigo(codUsuario);
			 
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		
		return usuario;
	}
	
	public List<FacCliente> buscarDatosUsuariocliente(String ruc) {

		List<FacCliente> listaUsuario = null;

		try {
			listaUsuario = facUsuarioEstablecimientoServicio.buscarTodosDatosUsuariocliente(ruc);
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return listaUsuario;
	}
	
	public List<FacUsuario> buscarDatosUsuario(String ruc) {

		List<FacUsuario> listaUsuario = null;

		try {

			listaUsuario = facUsuarioEstablecimientoServicio.buscarTodosDatosUsuario(ruc);

		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return listaUsuario;
	}

	public List<FacEmpresa> buscarDatosRuc(){
		List<FacEmpresa> listEmpresa = null;
				try {
					
					listEmpresa = facUsuarioEstablecimientoServicio.buscarDatosPorRuc();
				} catch (Exception e) {
					FacesMessage mensaje = null;
					mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), null);
					FacesContext.getCurrentInstance().addMessage(null, mensaje);
				}
		return listEmpresa;
	}
	
	public List<FacUsuariosEstablecimiento> buscarDatosUsuarioEstablecimiento() {

		List<FacUsuariosEstablecimiento> listaUsarioEstablecimiento = null;

		try {
			listaUsarioEstablecimiento = facUsuarioEstablecimientoServicio.buscarTodosDatosUsarioEstablecimiento();

		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return listaUsarioEstablecimiento;
	}
	
	public void llenarTiposAcceso() {
		 li_tiposAcceso= new ArrayList<SelectItem>();
		try {
			li_tiposAcceso.add(new SelectItem("T","Total"));
			li_tiposAcceso.add(new SelectItem("L","Limitado"));
		
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}
	
	public void llenarTablaUEstablecimiento() {
		li_UEstablecimiento = new ArrayList<FacUsuariosEstablecimiento>();
		try {
			li_UEstablecimiento = buscarDatosUsuarioEstablecimiento();
			for(int i=0;i<li_UEstablecimiento.size();i++){		
				String tipoAc = li_UEstablecimiento.get(i).getTipoAcceso().trim();
				li_UEstablecimiento.get(i).setTipoAcceso((tipoAc.equals("T") ? "Total" : "Limitado"));
				String Estado =  li_UEstablecimiento.get(i).getIsActive().trim();
				li_UEstablecimiento.get(i).setIsActive((Estado.equals("Y") ? "Activo" : "Inactivo"));
			}
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	public void llenaComboRuc() {
		li_Ruc = new ArrayList<SelectItem>();
		try {

			listaEmpresa = buscarDatosRuc();

			// si viene vacia
			if (listaEmpresa.isEmpty()) {
				li_Ruc.add(new SelectItem("0","No hay datos"));
				return;
			}
			
			li_Ruc.add(new SelectItem("0","seleccione la opcion"));
			
			for (int i = 0; i < listaEmpresa.size(); i++) {
				li_Ruc.add(new SelectItem(listaEmpresa.get(i).getRuc().trim() , listaEmpresa.get(i).getRazonSocial()));
			}
			
		} catch (Exception e) {
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}
	
	public void llenarCombos(){
		if(!ruc.equals("0")) activarBtn = false;
		else activarBtn = true;
		codEstablecimiento = "";
		codUsuario = "";
	}
	
	public void CargarDatosModificables(){
		Modificar = true;
		activarBtn = true;
		ruc = seleccionarCampos.getId().getRuc().trim();
		codUsuario = seleccionarCampos.getId().getCodUsuario().trim();
		codEstablecimiento = seleccionarCampos.getId().getCodEstablecimiento().trim();
		tipoAcceso = seleccionarCampos.getTipoAcceso();
	}
	
	public void llenaComboUsuario() {
		//li_usuario = new ArrayList<SelectItem>();
		try {
			
			listfUsuario = buscarDatosUsuario(ruc);
			listfcliente = new ArrayList<FacCliente>();
			List<FacCliente> cliente =  buscarDatosUsuariocliente(ruc);
			
			for (FacUsuario facusuario : listfUsuario) 
				for (FacCliente facCliente : cliente) 
					if(facusuario.getId().getCodUsuario().trim().equals(facCliente.getId().getRucCliente()))
						listfcliente.add(facCliente);
				

			} catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	public void ubicarUsuario(){
		codUsuario = selectcliente.getId().getRucCliente().trim();
	}
	
	public void ubicarEstablecimiento(){
		codEstablecimiento = selectEstabl.getId().getCodEstablecimiento().trim();
	}
	
	public void llenaComboEstablecimiento() {
		try {

			listEstablecimiento = buscarDatosEstablecimiento(ruc);


			// si viene vacia
			if (listEstablecimiento.isEmpty()) {
				establecimiento.setCorreo("0");
				establecimiento.setDireccionEstablecimiento("0");
				establecimiento.setIsActive("Y");
				establecimiento.setMensaje("0");
				establecimiento.setPathAnexo("0");
				listEstablecimiento.add(establecimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	public void BorrarLiniea(){
		try {
			this.li_UEstablecimiento.remove(seleccionarCampos);
			facUsuarioEstablecimientoServicio.ElminadoLogicoUsuarioEstablecimiento(seleccionarCampos);
			mensajeAlerta("Mensaje del sistema", "Registro eliminado con exito", "Informacion");
			CargarDatos();
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje = null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	//fin de metodos de aplicación
	
	//metodos de get y set de la aplicacion FacUsuarioEstableciminetoControlador
	
	public FacUsuariosEstablecimiento getSeleccionarCampos() {
		return seleccionarCampos;
	}

	public void setSeleccionarCampos(FacUsuariosEstablecimiento seleccionarCampos) {
		this.seleccionarCampos = seleccionarCampos;
	}

	public FacUsuarioEstablecimientoServicio getFacUsuarioEstablecimientoServicio() {
		return facUsuarioEstablecimientoServicio;
	}

	public void setFacUsuarioEstablecimientoServicio(
			FacUsuarioEstablecimientoServicio facUsuarioEstablecimientoServicio) {
		this.facUsuarioEstablecimientoServicio = facUsuarioEstablecimientoServicio;
	}

	public SelectItem getListItems() {
		return listItems;
	}

	public void setListItems(SelectItem listItems) {
		this.listItems = listItems;
	}

	public FacUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(FacUsuario usuario) {
		this.usuario = usuario;
	}

	public FacEstablecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(FacEstablecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public List<FacEstablecimiento> getListEstablecimiento() {
		return listEstablecimiento;
	}

	public void setListEstablecimiento(
			List<FacEstablecimiento> listEstablecimiento) {
		this.listEstablecimiento = listEstablecimiento;
	}

	public FacUsuariosEstablecimiento getUEstablecimiento() {
		return UEstablecimiento;
	}

	public void setUEstablecimiento(FacUsuariosEstablecimiento uEstablecimiento) {
		UEstablecimiento = uEstablecimiento;
	}

	public List<FacUsuariosEstablecimiento> getLi_UEstablecimiento() {
		return li_UEstablecimiento;
	}

	public void setLi_UEstablecimiento(
			List<FacUsuariosEstablecimiento> li_UEstablecimiento) {
		this.li_UEstablecimiento = li_UEstablecimiento;
	}

	public FacUsuariosEstablecimientoPK getId() {
		return id;
	}

	public void setId(FacUsuariosEstablecimientoPK id) {
		this.id = id;
	}

	
	public List<FacUsuario> getListfUsuario() {
		return listfUsuario;
	}

	public void setListfUsuario(List<FacUsuario> listfUsuario) {
		this.listfUsuario = listfUsuario;
	}

	public FacUsuario getSelectUsuario() {
		return selectUsuario;
	}

	public void setSelectUsuario(FacUsuario selectUsuario) {
		this.selectUsuario = selectUsuario;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getCodEstablecimiento() {
		return codEstablecimiento;
	}

	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}

	public String getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}


	public List<SelectItem> getLi_tiposAcceso() {
		return li_tiposAcceso;
	}

	public void setLi_tiposAcceso(List<SelectItem> li_tiposAcceso) {
		this.li_tiposAcceso = li_tiposAcceso;
	}

	public List<SelectItem> getLi_Ruc() {
		return li_Ruc;
	}

	public void setLi_Ruc(List<SelectItem> li_Ruc) {
		this.li_Ruc = li_Ruc;
	}

	public List<FacEmpresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<FacEmpresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public boolean isGuardar() {
		return guardar;
	}

	public void setGuardar(boolean guardar) {
		this.guardar = guardar;
	}


	public boolean isActivarBtn() {
		return activarBtn;
	}

	public void setActivarBtn(boolean activarBtn) {
		this.activarBtn = activarBtn;
	}

	public FacUsuario getFiltarUsuario() {
		return filtarUsuario;
	}

	public void setFiltarUsuario(FacUsuario filtarUsuario) {
		this.filtarUsuario = filtarUsuario;
	}

	public FacEstablecimiento getSelectEstabl() {
		return selectEstabl;
	}

	public void setSelectEstabl(FacEstablecimiento selectEstabl) {
		this.selectEstabl = selectEstabl;
	}

	public FacEstablecimiento getFiltarEstabl() {
		return filtarEstabl;
	}

	public void setFiltarEstabl(FacEstablecimiento filtarEstabl) {
		this.filtarEstabl = filtarEstabl;
	}

	public FacCliente getCliente() {
		return cliente;
	}

	public void setCliente(FacCliente cliente) {
		this.cliente = cliente;
	}

	public List<FacCliente> getListfcliente() {
		return listfcliente;
	}

	public void setListfcliente(List<FacCliente> listfcliente) {
		this.listfcliente = listfcliente;
	}

	public FacCliente getSelectcliente() {
		return selectcliente;
	}

	public void setSelectcliente(FacCliente selectcliente) {
		this.selectcliente = selectcliente;
	}

	public FacCliente getFiltarcliente() {
		return filtarcliente;
	}

	public void setFiltarcliente(FacCliente filtarcliente) {
		this.filtarcliente = filtarcliente;
	}

	public boolean isModificar() {
		return Modificar;
	}

	public void setModificar(boolean modificar) {
		Modificar = modificar;
	}
	
}//fin de aplicacion FacUsuarioEstablecimientoControlador
