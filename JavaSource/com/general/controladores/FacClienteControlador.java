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
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacEmpresa;
import com.documentos.entidades.FacTransporte;
import com.documentos.entidades.FacTransportePK;
import com.general.entidades.FacCliente;
import com.general.entidades.FacClientePK;
import com.general.entidades.FacGeneral;
import com.general.servicios.FacClienteServicios;


@ViewScoped
@ManagedBean
public class FacClienteControlador {
	
	@EJB
	private FacClienteServicios servicioCliente;
	
	//VARIABLE DE LA ENTIDAD CLIENTE
	private String Ruc;
	private String RazonSocial;
	private String Direccion;
	private String Email;
	private String TipoCliente;
	private String TipoIdentificacion;
	private String Rise;
	private String Telefono;
	private String RucCliente;
	
	//VARIABLE DE LA ENTIDAD TRANSPORTISTA
	private String Placa;
	private String Descripcion;
	private String Marca;
	private String Modelo;
	private String Chasis;
	private String Conductor;
	
	//VARIABLE ADICIONALES
	private boolean activoPanel;
	private boolean activarVehiculo;
	private boolean seguir;
	private boolean modificar;
	private boolean grisar;
	private UIData datosTablaVehiculo;
	private UIData datosTablaCliente;
	private boolean disabled;
	private final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//ENTIDAD CLIENTE
	private FacCliente cliente;
	private FacCliente modificarCliente;
	private FacClientePK clienteId;
	private FacCliente verCamposClientes;
	
	//ENTIDAD TRANSPORTE
	private FacTransporte transporte;
	private FacTransportePK idTransporte;
	
	//VARIABLE TIPO LIST<ENTIDAD>
	private List<FacGeneral> listGeneral;
	private List<FacEmpresa> listEmpresa;
	private List<FacCliente> listCliente;
	private List<FacCliente> FiltrosCliente;
	private List<FacTransporte> listTrasnporte;
	private List<FacTransporte> listVehiculo;
	
	//VARIABLE TIPO LIST<SELECITEN>
	private List<SelectItem> li_tipUsuario;
	private List<SelectItem> li_tipIdentificacion;
	private List<SelectItem> li_RucEmpresa;
	
	private facVerificarIdentificacionControladores VerificarIdentificacion;
	
	//METODOS DE APLICACION
	
	public void cargarDatos(){
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
		
		Limpiar();
		listCliente = new ArrayList<FacCliente>();
		this.llenarComboTipoUsuario();
		this.llenarRucEmpres();
		this.llenarComboTipoIdenti();
		this.llenarTablaCliente();
		activoPanel = false;
		activarVehiculo = false;
		modificar = false;
		grisar = false;
		disabled=false;
		listTrasnporte = new ArrayList<FacTransporte>();
	}
	
	public void Limpiar(){
		if(modificar)modificar=false;
		grisar = false;
		disabled=false;
		RucCliente="";
		Direccion="";
		RazonSocial="";
		Rise="";
		Telefono="";
		Email="";
		TipoCliente="0";
		Ruc="0";
		TipoIdentificacion="0";
		FiltrosCliente = new ArrayList<FacCliente>();
	}
	
	public void insert(){
		try {
			if (Ruc.trim().equals("0") || RucCliente.equals("") || RazonSocial.equals("") || Email.equals("") || TipoCliente.trim().equals("0")
				||TipoIdentificacion.trim().equals("0")) {
				if (Ruc.trim().equals("0")) {
					mensajeAlerta("Mensaje del sistema", "Seleccione la empresa", "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}if (RucCliente.equals("")) {
					mensajeAlerta("Mensaje del sistema", "Identificación del Usuario:: Ingrese datos", "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}if (RazonSocial.equals("")) {
					mensajeAlerta("Mensaje del sistema", "Razón Social:: Ingrese datos", "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}if (Email.equals("")) {
					mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Ingrese datos", "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}if (TipoCliente.trim().equals("0")) {
					mensajeAlerta("Mensaje del sistema", "Tipo de Usuario:: Seleccione el tipo usuario" , "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}if (TipoIdentificacion.trim().equals("0")||TipoIdentificacion==null) {
					mensajeAlerta("Mensaje del sistema", "Tipo de Identificación" , "peligro");
					disabled=false;
					grisar = (modificar ? true : false);
					return;
				}
			}else {
				if (TipoCliente.trim().equals("T")) {
					if(listTrasnporte.size()==0){
						mensajeAlerta("Mensaje del sistema", "Detalle Vehículos:: Agregar vehiculos", "peligro"); 
						grisar=false;
						disabled = false;
						return;}
					for (int i = 0; i < listTrasnporte.size(); i++) {
						if (listTrasnporte.get(i).getId().getPlaca().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Placa:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled = false;
							return;
						}if (listTrasnporte.get(i).getDescripcion().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Descripcion:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled=false;
							return;
						}if (listTrasnporte.get(i).getMarca().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Marca:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled=false;
							return;
						}if (listTrasnporte.get(i).getModelo().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Modelo:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled=false;
							return;
						}if (listTrasnporte.get(i).getChasis().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Chasis:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled=false;
							return;
						}if (listTrasnporte.get(i).getConductor().trim().equals("")) {
							mensajeAlerta("Mensaje del sistema", "Vehículo Conductor:: Ingrese datos", "peligro");
							grisar = (modificar ? true : false);
							disabled=false;
							return;
						}
					}
				}
				this.insertCliente();
				if (seguir==false) {
					disabled=false;
					return;
				}
				if (TipoCliente.trim().equals("T")) {
					this.insertTransporte();
					listTrasnporte.remove(true);
					activoPanel=false;
				}
				cargarDatos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertCliente (){
		try {
			if (modificar) {
				cliente = new FacCliente();
				clienteId = new FacClientePK();
				clienteId.setRuc(Ruc);
				clienteId.setRucCliente(RucCliente);
				cliente.setId(clienteId);
				cliente.setRazonSocial(RazonSocial);
				cliente.setDireccion(Direccion);
				if (validar_correo(Email)!=false) {
					cliente.setEmail(Email);
					setSeguir(true);
				}else {
					mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Verficar correo", "peligro");
					setSeguir(false);
					disabled=false;
					grisar=true;
					return;
				}
				cliente.setRise(Rise);
				cliente.setTelefono(Telefono);
				cliente.setTipoCliente(TipoCliente);
				cliente.setTipoIdentificacion(TipoIdentificacion);
				servicioCliente.modificarCliente(cliente);
			}else{/////////////////////////////////////////////////////////////////////////////
				cliente = new FacCliente();
				clienteId = new FacClientePK();
				clienteId.setRuc(Ruc);
				if (validarIdentificacionUsuario()) {
					if (TipoIdentificacion.trim().equals("04")) {
						if(RucCliente.length()==13){
							clienteId.setRucCliente(RucCliente);
							setSeguir(true);
						} else {
							mensajeAlerta("Mensaje del sistema", "Identificacion del usuario:: Ruc del usuario faltan digitos", "Información");
							setSeguir(false);
							grisar = (modificar ? true : false);
							disabled = false;
							return;
						}
					} else if (TipoIdentificacion.trim().equals("05")) {
						if(RucCliente.length()==10){
							clienteId.setRucCliente(RucCliente);
							setSeguir(true);
						} else {
							mensajeAlerta("Mensaje del sistema", "Identificacion del usuario:: Cedula del usuario faltan digitos", "Información");
							setSeguir(false);
							grisar = (modificar ? true : false);
							disabled = false;
							return;
						}
					} else if (TipoIdentificacion.trim().equals("06")) {
						clienteId.setRucCliente(RucCliente);
						setSeguir(true);
					}
				}else{
					mensajeAlerta("Mensaje del sistema", "Usuario:: ESTE USUARIO YA EXISTE", "alerta");
					grisar = false;
					disabled = false;
					return;
				}
				cliente.setId(clienteId);
				cliente.setRazonSocial(RazonSocial);
				cliente.setDireccion(Direccion);
				if (validar_correo(Email)!=false) {
					cliente.setEmail(Email);
					setSeguir(true);
				}else {
					mensajeAlerta("Mensaje del sistema", "Correo Electronico:: Verficar correo electronico", "peligro");
					setSeguir(false);
					disabled = false;
					return;
				}
				cliente.setRise(Rise);
				cliente.setTelefono(Telefono);
				cliente.setTipoCliente(TipoCliente);
				cliente.setTipoIdentificacion(TipoIdentificacion);
				cliente.setIsActive("Y");
				servicioCliente.insertCliente(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertTransporte(){
		try {
			transporte = new FacTransporte();
			idTransporte = new FacTransportePK();
			for (int i = 0; i < listTrasnporte.size(); i++) {
				idTransporte.setRuc(Ruc);
				idTransporte.setRucCliente(RucCliente);
				idTransporte.setPlaca(listTrasnporte.get(i).getId().getPlaca());
				transporte.setId(idTransporte);
				transporte.setDescripcion(listTrasnporte.get(i).getDescripcion());
				transporte.setMarca(listTrasnporte.get(i).getMarca());
				transporte.setModelo(listTrasnporte.get(i).getModelo());
				transporte.setChasis(listTrasnporte.get(i).getChasis());
				transporte.setConductor(listTrasnporte.get(i).getConductor());
				if (modificar) {
					servicioCliente.modificarTransporte(transporte);
				}else{
					servicioCliente.insertTransporte(transporte);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void llenarComboTipoUsuario(){
		li_tipUsuario = new ArrayList<SelectItem>();
		try {
			listGeneral = servicioCliente.buscarCodigoTabla("1");
			if(listGeneral.isEmpty()){
				return;
			}
			li_tipUsuario.add(new SelectItem("0","Selecionar Tipo"));
			for (int i = 0; i < listGeneral.size(); i++) {
				li_tipUsuario.add(new SelectItem(listGeneral.get(i).getCodUnico().trim(),listGeneral.get(i).getDescripcion()));
			}
			li_tipUsuario.add(new SelectItem("T","TRANSPORTISTA"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarComboTipoIdenti(){
		li_tipIdentificacion = new ArrayList<SelectItem>();
		try {
			listGeneral = servicioCliente.buscarCodigoTabla("95");
			if(listGeneral.isEmpty()){
				return;
			}
			li_tipIdentificacion.add(new SelectItem(0,"Selecionar Identificacion"));
			for (int i = 0; i < listGeneral.size(); i++) {
				
				li_tipIdentificacion.add(new SelectItem(listGeneral.get(i).getCodUnico().trim(),listGeneral.get(i).getDescripcion()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarRucEmpres(){
		li_RucEmpresa = new ArrayList<SelectItem>();
		try {
			listEmpresa = servicioCliente.buscarRuc();
			if (listEmpresa.isEmpty()) {
				return;
			}
			li_RucEmpresa.add(new SelectItem("0","Selecionar Empresa"));
			for (int i = 0; i < listEmpresa.size(); i++) {
				li_RucEmpresa.add(new SelectItem(listEmpresa.get(i).getRuc(),listEmpresa.get(i).getRazonSocial()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarTablaCliente(){
		
		try {
			listCliente = servicioCliente.buscarDatosCliente();
			for (int i = 0; i < listCliente.size(); i++) {
				if (listCliente.get(i).getTipoCliente().trim().equals("E")) {
					listCliente.get(i).setTipoCliente("EMPLEADO");
				}else if(listCliente.get(i).getTipoCliente().trim().equals("P")){
					listCliente.get(i).setTipoCliente("PROVEEDOR");
				}else if(listCliente.get(i).getTipoCliente().trim().equals("C")){
					listCliente.get(i).setTipoCliente("CLIENTE");
				}else if(listCliente.get(i).getTipoCliente().trim().equals("T")){
					listCliente.get(i).setTipoCliente("TRANSPORTISTA");
				}
				if(listCliente.get(i).getTipoIdentificacion().equals("04")||listCliente.get(i).getTipoIdentificacion().equals("4")){
					listCliente.get(i).setTipoIdentificacion("RUC");
				}else if (listCliente.get(i).getTipoIdentificacion().equals("05")||listCliente.get(i).getTipoIdentificacion().equals("5")) {
					listCliente.get(i).setTipoIdentificacion("CEDULA");
				}else if (listCliente.get(i).getTipoIdentificacion().equals("06")||listCliente.get(i).getTipoIdentificacion().equals("6")){
					listCliente.get(i).setTipoIdentificacion("PASAPORTE");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarTablaVehiculo(){
		if (TipoCliente.equals("T")) {
			activoPanel = true;
			try {
				
				transporte = new FacTransporte();
				idTransporte = new FacTransportePK();
				idTransporte.setRuc("");
				idTransporte.setPlaca("");
				idTransporte.setRucCliente("");
				transporte.setId(idTransporte);
				transporte.setDescripcion("");
				transporte.setMarca("");
				transporte.setModelo("");
				transporte.setChasis("");
				transporte.setConductor("");
				listTrasnporte.add(transporte);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			activoPanel = false;
		}
	}
	
	public void añadirVehiculo(){
		transporte = new FacTransporte();
		idTransporte = new FacTransportePK();
		idTransporte.setRuc("");
		idTransporte.setPlaca("");
		idTransporte.setRucCliente("");
		transporte.setId(idTransporte);
		transporte.setDescripcion("");
		transporte.setMarca("");
		transporte.setModelo("");
		transporte.setChasis("");
		transporte.setConductor("");
		listTrasnporte.add(transporte);
	}
	
	public void borrarVehiculo(){
		FacTransporte dellTransportes = (FacTransporte) datosTablaVehiculo.getRowData();
		if (modificar) {
			String ruc = dellTransportes.getId().getRuc().trim();
			String rucCliente = dellTransportes.getId().getRucCliente().trim();
			String placa=dellTransportes.getId().getPlaca().trim();
			servicioCliente.eliminarVehiculo(ruc,rucCliente,placa);
			listTrasnporte.remove(dellTransportes);
		}else {
			listTrasnporte.remove(dellTransportes);
		}
		
	}
	
	public void llenarDialogoVehiculo(){
		listVehiculo = new ArrayList<FacTransporte>();
		
		try {
			
				if (verCamposClientes.getTipoCliente().trim().equals("T")||verCamposClientes.getTipoCliente().trim().equals("TRANSPORTISTA")) {
					activarVehiculo = true;
					listVehiculo = servicioCliente.buscarVehiculos(verCamposClientes.getId().getRuc(), verCamposClientes.getId().getRucCliente());
				}else{
					mensajeAlerta("Mensaje del sistema", "Vehículo:: Este Usuario no tiene vehiculo", "Información");
					activarVehiculo = false;
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void llenarCamposModificar(String Evento){
		FacCliente modificarCliente = (FacCliente) datosTablaCliente.getRowData();
		if(Evento.trim().equals("Modificar")){
			if (modificarCliente.getTipoCliente().trim().equals("TRANSPORTISTA")){
				setTipoCliente("T");
				try {
					activoPanel = true;
					listTrasnporte = servicioCliente.buscarVehiculos(modificarCliente.getId().getRuc(), modificarCliente.getId().getRucCliente());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} if (modificarCliente.getTipoCliente().trim().equals("PROVEEDOR")){
				setTipoCliente("P");
				activoPanel = false;
				listTrasnporte.remove(true);
			} if (modificarCliente.getTipoCliente().trim().equals("CLIENTE")){
				setTipoCliente("C");
				activoPanel = false;
				listTrasnporte.remove(true);
			} if (modificarCliente.getTipoCliente().trim().equals("EMPLEADO")){
				setTipoCliente("E");
				activoPanel = false;
				listTrasnporte.remove(true);
			}
			
			if (modificarCliente.getTipoIdentificacion().trim().equals("RUC")) {
				setTipoIdentificacion("04");
			}else if (modificarCliente.getTipoIdentificacion().trim().equals("CEDULA")) {
				setTipoIdentificacion("05");
			}else if (modificarCliente.getTipoIdentificacion().trim().equals("PASAPORTE")) {
				setTipoIdentificacion("06");
			}
			
			Ruc = modificarCliente.getId().getRuc();
			RucCliente = modificarCliente.getId().getRucCliente();
			RazonSocial = modificarCliente.getRazonSocial();
			Direccion = modificarCliente.getDireccion();
			Email = modificarCliente.getEmail();
			Rise = modificarCliente.getRise();
			Telefono = modificarCliente.getTelefono();
			modificar = true;
			grisar = true;
		}else{
			if(Evento.trim().equals("Eliminar")){
				modificarCliente = verCamposClientes;
				servicioCliente.EliminarClienteLogico(modificarCliente);
				mensajeAlerta("Mensaje del sistema", "Registro Eliminado con exito", "Informacioón");
				cargarDatos();
			}
			disabled = (Evento.trim().equals("verificar") ? true : false);
			grisar = (Evento.trim().equals("verificar") ? true : false);
		}
	}

	
	public boolean validarIdentificacionUsuario(){
		boolean existe=false;
		
		try {
			
			if (TipoIdentificacion.trim().equals("04")) {
				existe = servicioCliente.busquedaCliente(Ruc, RucCliente, TipoIdentificacion);

			}else if (TipoIdentificacion.trim().equals("05")) {
				existe = servicioCliente.busquedaCliente(Ruc, RucCliente, TipoIdentificacion);

			}else if (TipoIdentificacion.trim().equals("06")) {
				existe = servicioCliente.busquedaCliente(Ruc, RucCliente, TipoIdentificacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existe;
	}
	
	public void soloNumero(String dato, String nombre) {
		String texto = dato;
		
		for (char x : texto.toCharArray()) {
			if (Character.isDigit(x)) {
				
			}
			if (Character.isLetter(x)) {
				
				FacesMessage mensaje = null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo "	+ nombre, "Solo Numero");
				FacesContext.getCurrentInstance().addMessage("Campo " + dato,mensaje);
				break;
			}
		}
		
	}
	
	public void verificarTelefono(){
		soloNumero(Telefono, "Telefono");
	}
	
	public void validarCampoUsuario(){
		FacCliente llenar = null;
		try {
			llenar = new FacCliente();
			if (TipoIdentificacion.trim().equals("04")||TipoIdentificacion.trim().equals("05")) {
				soloNumero(RucCliente, "Identificacion del usuario");
				if(RucCliente.length()>=10||RucCliente.length()==13){
					llenar = servicioCliente.buscaUsuario(RucCliente);
					RazonSocial = llenar.getRazonSocial();
					Direccion = llenar.getDireccion();
					Email = llenar.getEmail();
					Rise = llenar.getRise();
					Telefono = llenar.getTelefono();
				}else{
					RazonSocial = "";
					Direccion = "";
					Email = "";
					Rise = "";
					Telefono = "";
				}
			}else if(TipoIdentificacion.trim().equals("06")){
				
				if(RucCliente.length()>=8||RucCliente.length()==13){
					llenar = servicioCliente.buscaUsuario(RucCliente);
					RazonSocial = llenar.getRazonSocial();
					Direccion = llenar.getDireccion();
					Email = llenar.getEmail();
					Rise = llenar.getRise();
					Telefono = llenar.getTelefono();
				}else{
					RazonSocial = "";
					Direccion = "";
					Email = "";
					Rise = "";
					Telefono = "";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO validar identificacion
	public void ValidarIdentificacion(String identifi){
		
		if(TipoIdentificacion.trim().equals("06") | TipoIdentificacion.trim().equals("07"))
			return;
		
		VerificarIdentificacion = new facVerificarIdentificacionControladores();
		if (!VerificarIdentificacion.ValidarNumeroIdentificacion(identifi))
        {
            if ((TipoIdentificacion.trim().equals("04")))
            {
            	mensajeAlerta("Mensaje del sistema", "RUC INCORRECT0", "alerta");
                return;
            }
            if (TipoIdentificacion.trim().equals("05"))
            {
            	mensajeAlerta("Mensaje del sistema", "CEDULA INCORRECTA", "alerta");
                return;
            }
        }

	}
	
	public void noGrisarTodo (){
		if (modificar) {
			disabled = false;
			grisar = true;
		}else{
			disabled = false;
			grisar = false;
		}
	}
	public boolean validar_correo(String email){
		 // Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	//TODO contructor de mensaje de alerta para mostrar al usuario
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
	       FacesContext context = FacesContext.getCurrentInstance();            
	       context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
	}
	
	//metodos Get y Set
	public FacClienteServicios getServicioCliente() {
		return servicioCliente;
	}
	public void setServicioCliente(FacClienteServicios servicioCliente) {
		this.servicioCliente = servicioCliente;
	}
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
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getTipoCliente() {
		return TipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		TipoCliente = tipoCliente;
	}

	public String getTipoIdentificacion() {
		return TipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		TipoIdentificacion = tipoIdentificacion;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getRise() {
		return Rise;
	}
	public void setRise(String rise) {
		Rise = rise;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getRucCliente() {
		return RucCliente;
	}
	public void setRucCliente(String rucCliente) {
		RucCliente = rucCliente;
	}
	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getMarca() {
		return Marca;
	}
	public void setMarca(String marca) {
		Marca = marca;
	}
	public String getModelo() {
		return Modelo;
	}
	public void setModelo(String modelo) {
		Modelo = modelo;
	}
	public String getChasis() {
		return Chasis;
	}
	public void setChasis(String chasis) {
		Chasis = chasis;
	}
	public String getConductor() {
		return Conductor;
	}
	public void setConductor(String conductor) {
		Conductor = conductor;
	}
	public FacCliente getCliente() {
		return cliente;
	}
	public void setCliente(FacCliente cliente) {
		this.cliente = cliente;
	}
	public FacCliente getModificarCliente() {
		return modificarCliente;
	}
	public void setModificarCliente(FacCliente modificarCliente) {
		this.modificarCliente = modificarCliente;
	}
	public FacClientePK getClienteId() {
		return clienteId;
	}
	public void setClienteId(FacClientePK clienteId) {
		this.clienteId = clienteId;
	}
	public List<FacGeneral> getListGeneral() {
		return listGeneral;
	}
	public void setListGeneral(List<FacGeneral> listGeneral) {
		this.listGeneral = listGeneral;
	}
	public List<FacEmpresa> getListEmpresa() {
		return listEmpresa;
	}
	public void setListEmpresa(List<FacEmpresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}
	public List<FacCliente> getListCliente() {
		return listCliente;
	}
	public void setListCliente(List<FacCliente> listCliente) {
		this.listCliente = listCliente;
	}
	public List<SelectItem> getLi_tipUsuario() {
		return li_tipUsuario;
	}
	public void setLi_tipUsuario(List<SelectItem> li_tipUsuario) {
		this.li_tipUsuario = li_tipUsuario;
	}
	public List<SelectItem> getLi_tipIdentificacion() {
		return li_tipIdentificacion;
	}
	public void setLi_tipIdentificacion(List<SelectItem> li_tipIdentificacion) {
		this.li_tipIdentificacion = li_tipIdentificacion;
	}
	public List<SelectItem> getLi_RucEmpresa() {
		return li_RucEmpresa;
	}
	public void setLi_RucEmpresa(List<SelectItem> li_RucEmpresa) {
		this.li_RucEmpresa = li_RucEmpresa;
	}

	public boolean isActivoPanel() {
		return activoPanel;
	}

	public void setActivoPanel(boolean activoPanel) {
		this.activoPanel = activoPanel;
	}

	public List<FacTransporte> getListTrasnporte() {
		return listTrasnporte;
	}

	public void setListTrasnporte(List<FacTransporte> listTrasnporte) {
		this.listTrasnporte = listTrasnporte;
	}

	public FacTransporte getTransporte() {
		return transporte;
	}

	public void setTransporte(FacTransporte transporte) {
		this.transporte = transporte;
	}

	public FacTransportePK getIdTransporte() {
		return idTransporte;
	}

	public void setIdTransporte(FacTransportePK idTransporte) {
		this.idTransporte = idTransporte;
	}

	public UIData getDatosTablaVehiculo() {
		return datosTablaVehiculo;
	}

	public void setDatosTablaVehiculo(UIData datosTablaVehiculo) {
		this.datosTablaVehiculo = datosTablaVehiculo;
	}

	public FacCliente getVerCamposClientes() {
		return verCamposClientes;
	}

	public void setVerCamposClientes(FacCliente verCamposClientes) {
		this.verCamposClientes = verCamposClientes;
	}

	public boolean isActivarVehiculo() {
		return activarVehiculo;
	}

	public void setActivarVehiculo(boolean activarVehiculo) {
		this.activarVehiculo = activarVehiculo;
	}

	public List<FacTransporte> getListVehiculo() {
		return listVehiculo;
	}

	public void setListVehiculo(List<FacTransporte> listVehiculo) {
		this.listVehiculo = listVehiculo;
	}

	public String getPATTERN_EMAIL() {
		return PATTERN_EMAIL;
	}

	public boolean isSeguir() {
		return seguir;
	}

	public void setSeguir(boolean seguir) {
		this.seguir = seguir;
	}

	public UIData getDatosTablaCliente() {
		return datosTablaCliente;
	}

	public void setDatosTablaCliente(UIData datosTablaCliente) {
		this.datosTablaCliente = datosTablaCliente;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isGrisar() {
		return grisar;
	}

	public void setGrisar(boolean grisar) {
		this.grisar = grisar;
	}

	public List<FacCliente> getFiltrosCliente() {
		return FiltrosCliente;
	}

	public void setFiltrosCliente(List<FacCliente> filtrosCliente) {
		FiltrosCliente = filtrosCliente;
	}

}//FIN DEL CONTROLADOR