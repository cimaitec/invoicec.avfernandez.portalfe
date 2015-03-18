/**
 * 
 */
package com.documentos.controladores;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.component.api.UIData;

import com.documentos.servicios.FacturaServicio;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.general.entidades.FacEstablecimientoPK;
import com.general.entidades.FacGeneral;
import com.general.entidades.FacProducto;
import com.general.entidades.FacPuntoEmision;
import com.general.entidades.FacPuntoEmisionPK;
import com.documentos.entidades.DetalleImpuestosentidades;
import com.documentos.entidades.FacCabDocumentoPK;
import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetAdicionalPK;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacDetDocumentoPK;
import com.documentos.entidades.FacDetImpuesto;
import com.documentos.entidades.FacDetImpuestoPK;
import com.documentos.entidades.FacDetRetencione;
import com.documentos.entidades.FacEmpresa;
import com.documentos.entidades.FacCabDocumento;
import com.documentos.entidades.PantallaDetalleDocumento;


@SessionScoped
@ManagedBean
public class FacturaControlador {

	@EJB
	private FacturaServicio facDocumentoServicio;
	private FacEmpresa empresa;
	protected String TipoAmbiente;
	public int TamañoSecuencial = 9;
	
	private boolean visibleBotonPuntoEmision;

	private List<FacEmpresa> listEmpresa;
	protected String ruc; //cima
	protected String usuario;

	//cabecera Factura
	private FacCabDocumento cabDocumento;
	private List<FacCabDocumento> listCabDocumento;

	//metodos Generales
	private List<SelectItem> li;
	private List<SelectItem> tiposComprador;	
	private List<FacGeneral> listGeneral;
	private FacGeneral generales;
	

	//private List<SelectItem> establecimientos;	
	private List<FacEstablecimiento> listEstablecimientos;
	private FacEstablecimiento establecimiento;
	private FacEstablecimientoPK establecimientoPK;

	//private List<SelectItem> puntosEmision;	
	private List<FacPuntoEmision> listPuntosEmision;
	private FacPuntoEmision puntoEmisionObj;
	private FacPuntoEmisionPK puntoEmisionPK;
	
	private FacDetDocumento detDocumento;
	private FacDetDocumentoPK detDocumentoPK;
	private List<FacDetDocumento> listDetalleDocumento;
	//para mejor manejo del detalle del datatable
	private PantallaDetalleDocumento pantallaDetalleDocumento ;
	private List<PantallaDetalleDocumento> listPantallaDetalleDocumento;
	private PantallaDetalleDocumento seleccionaDetalleDocumento;
	private UIData DataTableDetalle;
	public String Evento2 ="";
	
	//detalle Adicional
	private FacDetAdicional detAdicional;
	private FacDetAdicionalPK detAdicionalPK;		
	private List<FacDetAdicional>   listPantallaDetalleAdicional;
	private UIData DataTableDetalleAdicional;
 	
	
	private FacCliente comprador;
    private boolean presenta;
    
	// datos bean de objeto	
	private String codEstablecimiento;
	private String puntoEmision;	                
	private int secuencial;
	
	//estos Valores se cargan automaticamente De la Sesion
	private String codigoTipoDocumento = "01";
	private String tipoDocumento = "FACTURA";
	private String tipoEmision;
	private String guiaDeRemision;
	private Date FechaDeEmision;
	//private String FechaDeEmision;
	private String tipoComprador;
	private String identificacionDelComprador;
	private String razonSocial;
	private String direccionEstablecimiento;
	private String correoElectronico;
	private String telefono;
	private int secuencialDetalle;
	private int secuencialDetalleAdicional;	
	
	//totales
	private double subtotalSinImpuesto;
	private double subtotal12;
	private double subtotal0;
	private double subtotalNoIva;
	private double totalDescuento;
	private double totalvalorICE;
	private double iva12;
	private double propina10;
	private boolean propina; 
	private double valorTotalFactura;
	private boolean banderaComprador;
	private String tcRuc= "04"; 
	private String tcCed= "05";
	private String tcPas= "06";
	private String tcCof= "07";
	
	////
	private List<FacEstablecimiento> filtraEstablecimiento; // variable que se encarga de filtrar la los roles
	private FacEstablecimiento verCampos;// variable que se encarga de recoger el registro que el usuario selecciono del rol
	
	private List<FacPuntoEmision> filtraPuntosEmision; // variable que se encarga de filtrar la los roles
	private FacPuntoEmision verCamposPE;// variable que se encarga de recoger el registro que el usuario selecciono del rol

	//private List<SelectItem> productos;	
	private List<FacProducto> listProducto;
	private List<FacProducto> filtraProducto;	
	private FacProducto verCamposPro;
	private FacProducto producto;	
    private String valorProducto;
	
	
	
	  /* para generar el archivo de texto */
	String Espacios = "";
   
   
	public void CargarDatos(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
		if(sesion.getAttribute("Ruc_Empresa") != null)
		{
			ruc = sesion.getAttribute("Ruc_Empresa").toString();
			usuario = sesion.getAttribute("id_usuario").toString();
		}
		else{
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
			try {
				ctx.redirect(ctxPath + "/paginas/Administrador/Cuenta/Login.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		subtotal0 = 0;
		subtotal12 = 0;
		subtotalNoIva = 0;
		subtotalSinImpuesto = 0;
		totalDescuento = 0;
		totalvalorICE = 0;
		iva12 = 0;
		propina10 = 0;
		propina = false;
		valorTotalFactura = 0;
		visibleBotonPuntoEmision = true;
		///encero los datos	                
		secuencial= 0;
		secuencialDetalle= 0;
		secuencialDetalleAdicional= 0;
		
		tipoEmision= "0";
		FechaDeEmision= new Date();
		tipoComprador= "0";
		identificacionDelComprador= null;
		razonSocial= " ";
		direccionEstablecimiento= "";
		correoElectronico= "";
		Evento2 = "";
		telefono= "";
		codEstablecimiento = null;
		puntoEmision = null;
		guiaDeRemision = null;
		

		    /// llamo a obtener la informacion Tributaria
	         this.cargaInformacionTributaria();
			//Ahora llamo a cargar los combos
            
			//tipos Emision       
			li = new ArrayList<SelectItem>();
			llenaCombosFacGeneral("8",li);
			
			//tipos Comprador
			tiposComprador = new ArrayList<SelectItem>();
		    llenaCombosFacGeneral("16",tiposComprador);
			
			listDetalleDocumento = new ArrayList<FacDetDocumento>();
			listPantallaDetalleDocumento= new ArrayList<PantallaDetalleDocumento>();
            listPantallaDetalleAdicional = new ArrayList<FacDetAdicional>();
            presenta = true;
            
            
            System.out.println("Iniciando session");
    		context = FacesContext.getCurrentInstance();
    		sesion = (HttpSession)context.getExternalContext().getSession(true);
    		System.out.println("El inicio de session es de:: " + sesion.getMaxInactiveInterval());
	}

    public void cargaInformacionTributaria(){
		try{
    	empresa = new FacEmpresa();
		empresa = facDocumentoServicio.buscarDatosPorRuc(ruc);
		empresa.setObligContabilidad((empresa.getObligContabilidad().trim().equals("S") ? "SI" : "NO"));
	}catch (Exception e) {
		FacesMessage mensaje=null;
		mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}
    	
    }
    
     public void CargaTiposEmision(){
 		//tipos Emision   
 		li = new ArrayList<SelectItem>();
 		llenaCombosFacGeneral("8",li);    	 
     }

      public void nuevoDetalle(){
    	  if (codEstablecimiento.equals("0") || puntoEmision.equals("0") || tipoComprador.equals("0") || identificacionDelComprador == null || identificacionDelComprador.equals(" ") || banderaComprador== false || FechaDeEmision == null){
			if (codEstablecimiento.equals("0")){
				mensajeAlerta("Mensaje del sistema","Establecimiento:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
			}
				
			if(puntoEmision.equals("0")){
				mensajeAlerta("Mensaje del sistema","Punto de Emisión:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
			}
   		    
			if(tipoComprador.equals("0")){
					   mensajeAlerta("Mensaje del sistema","Tipo Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
	   		}
				
			if(identificacionDelComprador.equals("null") || identificacionDelComprador.equals(" ")){
			    mensajeAlerta("Mensaje del sistema","Identificación del Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
			}

			if(banderaComprador== false){
			    mensajeAlerta("Mensaje del sistema","Identificacion Comprador:: Es incorrecto ", "peligro");
			}

			if(FechaDeEmision== null){
			    mensajeAlerta("Mensaje del sistema","Fecha Emision:: Esta Vacio ", "peligro");
			}
			
		}else{
			pantallaDetalleDocumento = new PantallaDetalleDocumento();
			 //Asigno pk	 
			pantallaDetalleDocumento.setRuc(ruc);
			pantallaDetalleDocumento.setCodEstablecimiento(codEstablecimiento);
			pantallaDetalleDocumento.setPuntoEmision(puntoEmision);
			pantallaDetalleDocumento.setSecuencial(secuencial);
	  		 //Genero Secuencial de Detalle
	   		secuencialDetalle = listPantallaDetalleDocumento.size();
			secuencialDetalle = secuencialDetalle + 1;
			pantallaDetalleDocumento.setLinea(secuencialDetalle);
			 		 

			pantallaDetalleDocumento.setCodigoProducto("");		 	
			pantallaDetalleDocumento.setDescripcion("");
			pantallaDetalleDocumento.setCantidad(0);
			pantallaDetalleDocumento.setPrecio(0.00);
			pantallaDetalleDocumento.setDescuento(0.00);
			pantallaDetalleDocumento.setValorTotal(0);
			pantallaDetalleDocumento.setValorSubtotal(0.00);
			pantallaDetalleDocumento.setValorIce(0.00);
			pantallaDetalleDocumento.setValorIVA(0.00);
			pantallaDetalleDocumento.setTipoIVA(0);
			pantallaDetalleDocumento.setTipoIce(0);
			pantallaDetalleDocumento.setDescripcionIce("");
			pantallaDetalleDocumento.setDescripcionIVA("");
			pantallaDetalleDocumento.setPorcentajeICE(0);
			pantallaDetalleDocumento.setPorcentajeIVA(0);
			listPantallaDetalleDocumento.add(pantallaDetalleDocumento);	
		}
	}      
    
      public void nuevoDetalleAdicional(){
		if (codEstablecimiento == "" || codEstablecimiento == null || puntoEmision == "" || puntoEmision == null || tipoComprador.equals("0") || identificacionDelComprador == null || identificacionDelComprador == "" || banderaComprador== false || listPantallaDetalleDocumento.size()== 0 || FechaDeEmision == null){	
			if (codEstablecimiento == "" || codEstablecimiento == null){
				mensajeAlerta("Mensaje del sistema","Establecimiento:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
			}
				
			if( puntoEmision == "" || puntoEmision == null){
				mensajeAlerta("Mensaje del sistema","Punto de Emisión:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
			}
   		    
			if(tipoComprador.equals("0")){
	   	  			   mensajeAlerta("Mensaje del sistema","Tipo Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
	   		}
				
			if(identificacionDelComprador.equals("null") || identificacionDelComprador.equals(" ")){
  			    mensajeAlerta("Mensaje del sistema","Identificacion del Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
			}

			if (listPantallaDetalleDocumento.size()== 0){
   			    mensajeAlerta("Mensaje del sistema","Ingrese el Detalle de Factura ", "peligro");				
			}
			
			if(banderaComprador== false){
   			    mensajeAlerta("Mensaje del sistema","Identificación Comprador:: Es incorrecto ", "peligro");
			}
			
			if(FechaDeEmision== null){
   			    mensajeAlerta("Mensaje del sistema","Fecha Emisión:: Esta Vacio ", "peligro");
			}

		}else{
			//Lleno Combos Producto		
			FacDetAdicionalPK facDetAdicionalPk = new FacDetAdicionalPK();
			FacDetAdicional facDetAdicional = new FacDetAdicional();
			//Asigno pk
			facDetAdicionalPk.setRuc(ruc);
			facDetAdicionalPk.setCodEstablecimiento(codEstablecimiento);
			facDetAdicionalPk.setCodPuntEmision(puntoEmision);
			facDetAdicionalPk.setSecuencial(cadenaSecuencial(secuencial,TamañoSecuencial));
	   		 
	  		//Genero Secuencial de Detalle
	   		secuencialDetalleAdicional = listPantallaDetalleAdicional.size();
			secuencialDetalleAdicional = secuencialDetalleAdicional + 1;
			facDetAdicionalPk.setSecuencialDetAdicional(secuencialDetalleAdicional);
			 

			facDetAdicional.setId(facDetAdicionalPk);		 	
			facDetAdicional.setNombre(null);
			facDetAdicional.setValor(null);
			 
			listPantallaDetalleAdicional.add(facDetAdicional);	
		}
			
	}
      
      public List<FacPuntoEmision> cargaPuntosEmision(){
          List<FacPuntoEmision> listaFacPuntoEmision = null;
 		
 		try{
 			listaFacPuntoEmision = facDocumentoServicio.buscarDatosPuntoEmision(empresa.getRuc(),codEstablecimiento,codigoTipoDocumento.trim());

 		}catch (Exception e) {
 			FacesMessage mensaje=null;
 			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
 			FacesContext.getCurrentInstance().addMessage(null, mensaje);
 		}
 		return listaFacPuntoEmision;	
     }
      
      
    public List<FacEstablecimiento> cargaEstablecimientos(){
         List<FacEstablecimiento> listaFacEstablecimiento = null;
		
		try{
			listaFacEstablecimiento = facDocumentoServicio.buscarDatosEstablecimiento(empresa.getRuc(), usuario);

		}catch (Exception e) {
		}
		return listaFacEstablecimiento;	
    }
    
	public void buscarPorCodigoCabeceraFactura(){
		try{
		
			cabDocumento = new FacCabDocumento();
			cabDocumento = facDocumentoServicio.buscarDatosCabDocPorRuc(ruc);

		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	public List<FacGeneral> buscarPorCodigo(String codigo){
		
		List<FacGeneral> listaFacGeneral = null;
		
		try{
			listaFacGeneral = facDocumentoServicio.buscarDatosPorCodigo(codigo);

		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  listaFacGeneral;
	}

	public List<FacProducto> buscarProductos(){
		
		List<FacProducto> listaFacProducto = null;
		
		try{	
			listaFacProducto = facDocumentoServicio.buscarDatosProducto();

		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  listaFacProducto;
	}
	
      public void ubicarValoresProducto(){
    	  PantallaDetalleDocumento detDocumentoUI = seleccionaDetalleDocumento;
		try{
			Evento2 = "productoseleccionado";
			detDocumentoUI.setCodigoProducto(String.valueOf(verCamposPro.getCodPrincipal()));
			detDocumentoUI.setCodigoAdicional(verCamposPro.getCodAuxiliar().toString());
			detDocumentoUI.setDescripcion(verCamposPro.getDescripcion());
			detDocumentoUI.setPrecio(verCamposPro.getValorUnitario());						
			detDocumentoUI.setTipoIVA(verCamposPro.getTipoIva());						
			detDocumentoUI.setTipoIce(verCamposPro.getCodIce());
			FacGeneral general = new FacGeneral();
			general = facDocumentoServicio.valoImpuestos("24", verCamposPro.getTipoIva().toString());
			detDocumentoUI.setPorcentajeIVA(general.getPorcentaje());
			detDocumentoUI.setDescripcionIVA(general.getDescripcion());
			if(!verCamposPro.getCodIce().toString().trim().equals("-1"))
				general = facDocumentoServicio.valoImpuestos("28", verCamposPro.getCodIce().toString());
			
			detDocumentoUI.setPorcentajeICE((verCamposPro.getCodIce().toString().trim().equals("-1") ? 0 : general.getPorcentaje()));
			detDocumentoUI.setDescripcionIce((verCamposPro.getCodIce().toString().trim().equals("-1") ? "" : general.getDescripcion()));
			this.calculaTotal();

		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
          
      public void buscarComprador(){
            banderaComprador = false;
            int longitudIdentificacion;
              	  
		try{
			if (tipoComprador.equals("0") || tipoComprador.equals(" ") || tipoComprador.equals("") || tipoComprador == null){
				this.setIdentificacionDelComprador("");
				this.setRazonSocial("");
				this.setDireccionEstablecimiento("");
				this.setCorreoElectronico("");
				this.setTelefono("");
				mensajeAlerta("Mensaje del sistema","Seleccione Tipo de Comprador", "Informacion");
			}else{
				if (identificacionDelComprador != null) {
					/* "04        ";"VENTA CON RUC"
					"05        ";"VENTA CON CEDULA"
					"06        ";"VENTA CON PASAPORTE"
					"07        ";"VENTA CON CONSUMIDOR FINAL" */
					longitudIdentificacion = identificacionDelComprador.length();
					
					if (tipoComprador.trim().equals(tcRuc)) {
    		              if(longitudIdentificacion == 13){
        					banderaComprador = true;
					       }
					}
					
					if (tipoComprador.trim().equals(tcCed)){
      		            if(longitudIdentificacion == 10){
      					banderaComprador = true;
   			            }
					}

					if (tipoComprador.trim().equals(tcPas)){
     		            if(longitudIdentificacion == 10){
      					 banderaComprador = true;
				       }
					}

					if (tipoComprador.trim().equals(tcCof)){
			           if(longitudIdentificacion == 10){
      					banderaComprador = true;
				       }
					}
					
					if (banderaComprador){
						comprador = facDocumentoServicio.buscarComprador(identificacionDelComprador);
						if (comprador != null){
							this.setRazonSocial(comprador.getRazonSocial());
							this.setDireccionEstablecimiento(comprador.getDireccion());
							this.setCorreoElectronico(comprador.getEmail());
							this.setTelefono(comprador.getTelefono());
							presenta = true;
						}else{
							mensajeAlerta("Mensaje del sistema","Comprador:: El Comprador No Existe", "peligro");
						}
					} else {
						mensajeAlerta("Mensaje del sistema","Comprador:: Documento Invalido", "peligro");
					}
					
				}else{
					this.setRazonSocial("");
					this.setDireccionEstablecimiento("");
					this.setCorreoElectronico("");
					this.setTelefono("");
				//	presenta = true;
					mensajeAlerta("Mensaje del sistema","Ingrese Identificacion Comprador", "Informacion");				
				}				
			}
			
			
		}catch (Exception e) {
			String mensaje = e.getMessage();
			this.setRazonSocial("");
			this.setDireccionEstablecimiento("");
			this.setCorreoElectronico("");
			this.setTelefono("");
			//presenta = true;
			banderaComprador = false;
			if (mensaje == null){
				mensaje = " ";
			}else
			mensajeAlerta("Mensaje del sistema","Comprador:: Error en la Identificacion del Comprador " + mensaje, "alerta");
			//mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),e.getMessage());
			//FacesContext.getCurrentInstance().addMessage("Error Comprador", mensaje);
		}
	}
 
    //TODO contructor que se encarga de aunmentar el secuencial del registro
  	private String cadenaSecuencial(int secuencial, int Tamaño){
  		String cadena = "";
  		for (int i = 0; i < Tamaño; i++) 
  			if(i > (String.valueOf(secuencial).length() - 1))
  				cadena += "0";
  		cadena += secuencial;
  		return cadena;
  	}
      
  	//TODO contructor que nos sirve para redondear numeros decimales
	private double Redondear(double numero,int digitos)
	{
	   int cifras=(int) Math.pow(10,digitos);
	   return Math.rint(numero*cifras)/cifras;
	}

	//TODO contructor que se encarga de autocompletar el ruc del comprador para facilitar al usuario
	public List<String> complete_RucComprador(String parametro_ruc) throws Exception{
		List<String> resultado = new ArrayList<String>();
		resultado = facDocumentoServicio.BuscarfitroEmpresaDocumentos(parametro_ruc.trim(), empresa.getRuc().trim(), tipoComprador.trim());	

		return resultado;
	}
    	
      public void recogiendoDatos(){
    	  PantallaDetalleDocumento detDocumen;
    	  if(Evento2.equals("productoseleccionado"))
				 detDocumen = seleccionaDetalleDocumento;
			 else
				 detDocumen = (PantallaDetalleDocumento) DataTableDetalle.getRowData();
			 
			 double total = 0 , subtotal = 0, ice = 0, iva = 0, porice = 0, poriva = 0;
			 total = Redondear(detDocumen.getCantidad() * detDocumen.getPrecio(), 2);
			 subtotal = Redondear(total - detDocumen.getDescuento(), 2);
			 detDocumen.setValorSubtotal(subtotal);
			 porice = detDocumen.getPorcentajeICE();
			 poriva = detDocumen.getPorcentajeIVA(); 
			 ice = Redondear(subtotal * (porice / 100), 2);
			 iva = Redondear(subtotal * (poriva / 100), 2);
			 detDocumen.setValorIVA(iva);
			 detDocumen.setValorIce(ice);
			 detDocumen.setValorTotal(Redondear(subtotal + iva + ice, 2));
			 calculaTotal();
			 Evento2 = "";
      }
      public void calculaTotal(){
    	  try{
    		  subtotalSinImpuesto = 0; totalvalorICE = 0; propina10 = 0; totalDescuento = 0; subtotal0 = 0; subtotal12 = 0; subtotalNoIva = 0; valorTotalFactura = 0; iva12 = 0;
    	  		for (PantallaDetalleDocumento documento : listPantallaDetalleDocumento) {
    				subtotalSinImpuesto += documento.getValorSubtotal();
    				totalvalorICE += documento.getValorIce();
    				totalDescuento += documento.getDescuento();
    				if(documento.getTipoIVA() == 0)
    					subtotal0 += documento.getValorSubtotal();
    				else if(documento.getTipoIVA() == 2){
    					subtotal12 += documento.getValorSubtotal();
    					iva12 += documento.getValorIVA();
    				}
    				else if(documento.getTipoIVA() == 6)
    					subtotalNoIva += documento.getValorSubtotal();
    			}
    	  		subtotalSinImpuesto = Redondear(subtotalSinImpuesto, 2);
    	  		totalvalorICE = Redondear(totalvalorICE, 2);
    	  		totalDescuento = Redondear(totalDescuento, 2);
    	  		subtotal0 = Redondear(subtotal0, 2);
    	  		subtotal12 = Redondear(subtotal12, 2);
    	  		iva12 = Redondear(iva12, 2);
    	  		subtotalNoIva = Redondear(subtotalNoIva, 2);
    	  		
    	  		propina10 = Redondear((propina ? (subtotalSinImpuesto * 0.1) : 0), 2);
    	  		valorTotalFactura = Redondear(subtotalSinImpuesto + totalvalorICE + iva12 + propina10, 2);
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
      
      public void insertarFactura(String NombreBoton){
    	  int guardado=0, secuDa,linea, cantidadProd;
    	  String codigoProducto;
    	  	List<FacEmpresa> listaEmpresa = new ArrayList<FacEmpresa>();
			listaEmpresa.add(empresa);
			List<FacCabDocumento> cabece_documento = new ArrayList<FacCabDocumento>();
			List<FacDetDocumento> detalle_documento = new ArrayList<FacDetDocumento>();
			List<FacDetRetencione> detalle_retencion = new ArrayList<FacDetRetencione>();
			List<FacDetImpuesto> detalle_impuesto = new ArrayList<FacDetImpuesto>();
			List<FacDetAdicional> detelle_adicional = new ArrayList<FacDetAdicional>();
			List<DetalleImpuestosentidades> detalleCabecera_impuesto = new ArrayList<DetalleImpuestosentidades>();
			List<String> MotivoRazon = new ArrayList<String>();
			List<FacProducto> DetalleAdicionalproducto = new ArrayList<FacProducto>();

			
    	try{	
  			if (empresa.getRuc() ==null || 	listPantallaDetalleDocumento.size()== 0 || codEstablecimiento == "" || codEstablecimiento == null || 
  					puntoEmision == "" || puntoEmision == null || tipoComprador.equals("0")|| identificacionDelComprador == null || identificacionDelComprador.trim().equals("") || FechaDeEmision == null){ 

  				if (empresa.getRuc() == null){
  					mensajeAlerta("Mensaje del sistema","Ruc:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
  				}

  				if (codEstablecimiento == "" || codEstablecimiento == null){
  					mensajeAlerta("Mensaje del sistema","EL Establecimiento:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
  				}
  					
  				if(puntoEmision == "" || puntoEmision == null || puntoEmision == "0" ){
  					mensajeAlerta("Mensaje del sistema","Punto de Emisión:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");				
  				}

  				if(tipoComprador.equals("0")){
   	  			   mensajeAlerta("Mensaje del sistema","Tipo Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
   				}
  				
  				if(identificacionDelComprador == null || identificacionDelComprador.trim().equals("") || razonSocial == null || razonSocial.trim().equals("")){
  	  			   mensajeAlerta("Mensaje del sistema","Identificación del Comprador:: Se Encuentra Vacio, Porfavor ingreselo", "peligro");
  				}
  				
  				if(FechaDeEmision== null){
  	   			    mensajeAlerta("Mensaje del sistema","Fecha Emisión:: Esta Vacio ", "peligro");
  				}
  				
  				if (listPantallaDetalleDocumento.size()== 0){
  	   			    mensajeAlerta("Mensaje del sistema","Ingrese el Detalle de Factura ", "peligro");				
  				}

  				guardado = -2;
  			}else{
  				
  				cabDocumento = new FacCabDocumento();				
  				//////// asigno PK 	
  				FacCabDocumentoPK id = new FacCabDocumentoPK();
  				id.setRuc(empresa.getRuc());
  				id.setCodEstablecimiento(codEstablecimiento);
  				id.setCodPuntEmision(puntoEmision);
  				id.setCodigoDocumento(codigoTipoDocumento.trim());
  				id.setAmbiente(new Integer(TipoAmbiente.toString().trim().equals("D") ? 1 : 2));
  				secuencial =  generaSecuencialDocumento(empresa.getRuc(), codEstablecimiento.trim(), puntoEmision.trim(), codigoTipoDocumento.trim());
  				id.setSecuencial(cadenaSecuencial(secuencial,TamañoSecuencial));
  				cabDocumento.setId(id);
  				///////llenamos los otros campos y asignamos en el objeto
  				
  				cabDocumento.setFechaEmision(FechaDeEmision);cabDocumento.setAutorizacion("");
  				cabDocumento.setGuiaRemision(guiaDeRemision);
  				cabDocumento.setRazonSocialComprador(razonSocial);
  				cabDocumento.setIdentificacionComprador(identificacionDelComprador);
  				cabDocumento.setTotalSinImpuesto(getSubtotalSinImpuesto());
  				cabDocumento.setTotalDescuento(getTotalDescuento());
  				cabDocumento.setEmail(correoElectronico);
  				cabDocumento.setPropina(getPropina10());
  				cabDocumento.setInfoAdicional(Espacios);
  				cabDocumento.setPeriodoFiscal(Espacios);
  				cabDocumento.setRise(Espacios);
  				cabDocumento.setFechaInicioTransporte(null);
  				cabDocumento.setFechaFinTransporte(null);
  				cabDocumento.setPlaca(Espacios);
  				cabDocumento.setFecEmisionDocSustento(null);
  				cabDocumento.setMotivoRazon(Espacios);
  				cabDocumento.setIdentificacionDestinatario(Espacios);
  				cabDocumento.setRazonSocialDestinatario(Espacios);
  				cabDocumento.setDireccionDestinatario(Espacios);
  				cabDocumento.setMotivoTraslado(Espacios);
  				cabDocumento.setDocAduaneroUnico(Espacios);
  				cabDocumento.setCodEstablecimientoDest(Espacios);
  				cabDocumento.setRuta(Espacios);
  				cabDocumento.setCodDocSustento(Espacios);
  				cabDocumento.setNumDocSustento(Espacios);
  				cabDocumento.setNumAutDocSustento(Espacios);
  				cabDocumento.setFecEmisionDocSustento(null);
  				cabDocumento.setAutorizacion(Espacios);
  				cabDocumento.setFechaautorizacion(null);
  				cabDocumento.setClaveAcceso(Espacios);
  				cabDocumento.setImporteTotal(this.getValorTotalFactura());
  				cabDocumento.setTipoEmision(tipoEmision);
  				cabDocumento.setCodDocModificado(Espacios);
  				cabDocumento.setNumDocModificado(Espacios);
  				cabDocumento.setMotivoValor(0);
  				cabDocumento.setTipIdentificacionComprador(tipoComprador);
  				cabDocumento.setTipoEmision(tipoEmision);
  				cabDocumento.setPartida(Espacios);
  				cabDocumento.setDireccionDestinatario(Espacios);
  				cabDocumento.setSubtotal0(subtotal0);
  				cabDocumento.setSubtotal12(subtotal12);
  				cabDocumento.setSubtotalNoIva(subtotalNoIva);
  				cabDocumento.setTotalvalorICE(totalvalorICE);
  				cabDocumento.setIva12(iva12);
  				cabDocumento.setAutorizacion(Espacios);
  				cabDocumento.setIsActive("Y");
  				cabDocumento.setEstadoTransaccion("IN");
  				

  				for (int j= 0; j < listPantallaDetalleDocumento.size();j++){
	  					guardado = 0;
	  					linea = j+1;
	  					codigoProducto = listPantallaDetalleDocumento.get(j).getCodigoProducto();
	  					cantidadProd = listPantallaDetalleDocumento.get(j).getCantidad();
                         if (codigoProducto.trim().equals("0") || cantidadProd == 0 ){
                        	 guardado = -2; // 0: correcto; -1: problemas de conexion; -2: problemas en datos
              	  			 mensajeAlerta("Mensaje del sistema","Hay Campos, en la linea "+ linea + " de la Factura que Se Encuentras Vacios, Porfavor ingreselos", "peligro");
                        	 break;
                         }
                   }
  				
  				if (guardado == 0){
  					guardado = facDocumentoServicio.insertarCabeceraDocumento(cabDocumento);///SE EJECUTA EL METODO
  	  			  	cabece_documento.add(cabDocumento);
  	  			  	
  	  				if (guardado == 0){
	  	  				/// AHORA LLENO PARA INSERTAR DETALLE
	  	  				for (int in= 0; in < listPantallaDetalleDocumento.size();in++){
		  	  				detDocumento = new FacDetDocumento();
		  	  				detDocumentoPK = new FacDetDocumentoPK();
			  				int secuinmpuesto = 1;
			  				detDocumentoPK.setRuc(cabDocumento.getId().getRuc());
		  	  				detDocumentoPK.setCodEstablecimiento(cabDocumento.getId().getCodEstablecimiento());
		  	  				detDocumentoPK.setCodPuntEmision(cabDocumento.getId().getCodPuntEmision());
		  	  				detDocumentoPK.setSecuencial(cabDocumento.getId().getSecuencial());
		  	  				detDocumentoPK.setCodigoDocumento(cabDocumento.getId().getCodigoDocumento());
	  	  					guardado = 0;
	  	  					detDocumentoPK.setSecuencialDetalle(listPantallaDetalleDocumento.get(in).getLinea());
	  	  					detDocumento.setId(detDocumentoPK);
	  	  					detDocumento.setCodPrincipal(listPantallaDetalleDocumento.get(in).getCodigoProducto());
	  	  					detDocumento.setCodAuxiliar(listPantallaDetalleDocumento.get(in).getCodigoAdicional());
	  	  					detDocumento.setDescripcion(listPantallaDetalleDocumento.get(in).getDescripcion());
	  	  					detDocumento.setCantidad(listPantallaDetalleDocumento.get(in).getCantidad());
	  	  					detDocumento.setDescuento(listPantallaDetalleDocumento.get(in).getDescuento());
	  	  					detDocumento.setPrecioUnitario(listPantallaDetalleDocumento.get(in).getPrecio());
	  	  					detDocumento.setValorIce(listPantallaDetalleDocumento.get(in).getValorIce());
	  	  					detDocumento.setPrecioTotalSinImpuesto(listPantallaDetalleDocumento.get(in).getValorSubtotal());
	  	  					guardado = facDocumentoServicio.insertarDetalleDocumento(detDocumento);///SE EJECUTA EL METODO			
	  	  					detalle_documento.add(detDocumento);
	  	  					if (guardado != 0) break;  	  
		  	  				FacDetImpuesto detImpuesto = new FacDetImpuesto();
			  				FacDetImpuestoPK detImpuestoPK = new FacDetImpuestoPK();
			  				detImpuestoPK.setRuc(cabDocumento.getId().getRuc());
							detImpuestoPK.setCodEstablecimiento(cabDocumento.getId().getCodEstablecimiento());
							detImpuestoPK.setCodPuntEmision(cabDocumento.getId().getCodPuntEmision());
							detImpuestoPK.setSecuencial(cabDocumento.getId().getSecuencial());
							detImpuestoPK.setCodigoDocumento(cabDocumento.getId().getCodigoDocumento());
		  	  				detImpuestoPK.setCodImpuesto(2);
		  					detImpuestoPK.setSecuencialDetalle(listPantallaDetalleDocumento.get(in).getLinea());
			  				detImpuestoPK.setDetSecuencial(secuinmpuesto);
			  				detImpuesto.setId(detImpuestoPK);
			  				detImpuesto.setTipoImpuestos("IVA");
			  				detImpuesto.setCodPorcentaje(listPantallaDetalleDocumento.get(in).getTipoIVA());
			  				detImpuesto.setBaseImponible(listPantallaDetalleDocumento.get(in).getValorSubtotal());
			  				detImpuesto.setTarifa(listPantallaDetalleDocumento.get(in).getPorcentajeIVA());
			  				detImpuesto.setValor(listPantallaDetalleDocumento.get(in).getValorIVA());
			  				
			  				guardado = facDocumentoServicio.insertarDetalleDocumentoImpuesto(detImpuesto);///SE EJECUTA EL METODO	
		  					detalle_impuesto.add(detImpuesto);
		  					
		  					
		  					if(guardado != 0)
		  						break;
		  					FacProducto prod = new FacProducto();
		  					prod = facDocumentoServicio.buscarDatosProductofiltrando(Integer.parseInt(detDocumento.getCodPrincipal()));
		  					DetalleAdicionalproducto.add(prod);
		  					
		  					/** PARA EL DATO DEL VALOR DE ICE **/
		  					if(listPantallaDetalleDocumento.get(in).getTipoIce() >= 0){
			  					secuinmpuesto++;
			  					detImpuesto = new FacDetImpuesto();
				  				detImpuestoPK = new FacDetImpuestoPK();
				  				detImpuestoPK.setRuc(cabDocumento.getId().getRuc());
								detImpuestoPK.setCodEstablecimiento(cabDocumento.getId().getCodEstablecimiento());
								detImpuestoPK.setCodPuntEmision(cabDocumento.getId().getCodPuntEmision());
								detImpuestoPK.setSecuencial(cabDocumento.getId().getSecuencial());
								detImpuestoPK.setCodigoDocumento(cabDocumento.getId().getCodigoDocumento());
			  					detImpuestoPK.setCodImpuesto(3);
			  					detImpuestoPK.setSecuencialDetalle(listPantallaDetalleDocumento.get(in).getLinea());
				  				detImpuestoPK.setDetSecuencial(secuinmpuesto);
				  				detImpuesto.setId(detImpuestoPK);
				  				detImpuesto.setTipoImpuestos("ICE");
				  				detImpuesto.setCodPorcentaje(listPantallaDetalleDocumento.get(in).getTipoIce());
				  				detImpuesto.setBaseImponible(listPantallaDetalleDocumento.get(in).getValorSubtotal());
				  				detImpuesto.setTarifa(listPantallaDetalleDocumento.get(in).getPorcentajeICE());
				  				detImpuesto.setValor(listPantallaDetalleDocumento.get(in).getValorIce());  					
			  					guardado = facDocumentoServicio.insertarDetalleDocumentoImpuesto(detImpuesto);///SE EJECUTA EL METODO	
			  					detalle_impuesto.add(detImpuesto);
		  					}
		  					if(guardado != 0)
		  						break;
	  	  				}  
	
	  	  				if (guardado == 0){
		  	  				/** 
		  	  	  			 * ingresando detalle del documento adicional
		  	  	  			 * **/
	  	  	  				
	  	  	  				for (int da= 0; da < listPantallaDetalleAdicional.size();da++){
		  	  	  				detAdicional = new FacDetAdicional();
		  	  	  				detAdicionalPK = new FacDetAdicionalPK();
		  	  	  				detAdicionalPK.setRuc(cabDocumento.getId().getRuc());
		  	  	  				detAdicionalPK.setCodEstablecimiento(cabDocumento.getId().getCodEstablecimiento());
		  	  	  				detAdicionalPK.setCodPuntEmision(cabDocumento.getId().getCodPuntEmision());
		  	  	  				detAdicionalPK.setSecuencial(cabDocumento.getId().getSecuencial());
		  	  	  				detAdicionalPK.setCodigoDocumento(cabDocumento.getId().getCodigoDocumento());
	  	  	  					guardado = 0;
	  	  	  					secuDa = da+1;
	  	  	  					detAdicionalPK.setSecuencialDetAdicional(secuDa);
	  	  	  					detAdicional.setId(detAdicionalPK);
	  	  	  					detAdicional.setNombre(listPantallaDetalleAdicional.get(da).getNombre());
	                            detAdicional.setValor(listPantallaDetalleAdicional.get(da).getValor());
	  	  	  					guardado = facDocumentoServicio.insertarDetalleAdicional(detAdicional);///SE EJECUTA EL METODO
	  	  	  					detelle_adicional.add(detAdicional);
	  	  	  					if (guardado != 0) break;  	  						
	  	  	  				}
	  	  	  				if(guardado == 0)
	  	  	  					detalleCabecera_impuesto = facDocumentoServicio.detalleFiltroimpuesto(cabDocumento.getId().getSecuencial(), codigoTipoDocumento.trim());
	  	  	  				
	  	  	  				SpoolGenerarArchivoControlador generarArchivo = new SpoolGenerarArchivoControlador();
	  	  			
	  	  	  				generarArchivo.setEmpresa(listaEmpresa);
			  	  			generarArchivo.setCabece_documento(cabece_documento);
			  	  			generarArchivo.setDetalle_documento(detalle_documento);
			  	  			generarArchivo.setDetalle_retencion(detalle_retencion);
			  	  			generarArchivo.setDetalle_impuesto(detalle_impuesto);
			  	  			generarArchivo.setDetelle_adicional(detelle_adicional);
			  	  			generarArchivo.setDetalleCabecera_impuesto(detalleCabecera_impuesto);
			  	  			generarArchivo.setMotivoRazon(MotivoRazon);
			  	  			generarArchivo.setDetalleAdicionalproducto(DetalleAdicionalproducto);
			  	  			
			  	  			if(generarArchivo.generarDocumento() == 0){
			  	  				mensajeAlerta("Mensaje del sistema","Se produjo un error al generar el archivo txt", "alerta");
			  	  				return;
			  	  			}
	  	  					mensajeAlerta("Factura Electronica","Factura Generada \n" + codEstablecimiento + "-" + puntoEmision + "-" + cadenaSecuencial(secuencial,TamañoSecuencial), "Informacion");
	  	  					try {
			  	  	    		Thread.sleep(2000);
			  	       		} catch (InterruptedException e) {
			  	       			e.printStackTrace();
			  	      		}
	  	  					CargarDatos();
	  	  				}else{
	  	  					mensajeAlerta("Mensaje del sistema","Error al Generar la Factura", "alerta");								
	  	  				}
  	  				}else{		
  	  					mensajeAlerta("Mensaje del sistema","Error al Generar la Factura", "alerta");
  	  				}
  				}else{	
  					mensajeAlerta("Mensaje del sistema","Error al Generar la Factura", "alerta");
  				}
  			}
  		}
  		catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
      }
         
      
      private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
    	  FacesContext context = FacesContext.getCurrentInstance();            
  	      context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle));
      }
      public int generaSecuencialDocumento(String rucC,String codEstablecimientoC,String puntoEmisionC,String codigoDocumento){ //04: Factura
    	  int secuencialDocumento= 0;
    	try {
    	  secuencialDocumento = facDocumentoServicio.secuencialCabecera(rucC, codEstablecimientoC,puntoEmisionC, codigoDocumento);
      }catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
  	  return secuencialDocumento;    	
      }
      
      public void BorrarLinea() {
    	  PantallaDetalleDocumento detDocumentoUI = (PantallaDetalleDocumento) DataTableDetalle.getRowData();

    	  listPantallaDetalleDocumento.remove(detDocumentoUI);
    	  int contador = 0;
    	  for (PantallaDetalleDocumento detallet : listPantallaDetalleDocumento) {
			contador ++;
			detallet.setLinea(contador);
    	  }
    	  calculaTotal();
      }

      public void BorrarLineaDetAdicional() {
    	  FacDetAdicional detDocumentoUI = (FacDetAdicional) DataTableDetalleAdicional.getRowData();

    	  listPantallaDetalleAdicional.remove(detDocumentoUI);

      }
      
	
	public void llenaCombosFacGeneral(String codigoGeneral, List<SelectItem> listaItems){
		   
		try{
			listGeneral = buscarPorCodigo(codigoGeneral);
			
			
			//si viene vacia
			if (listGeneral.isEmpty()){				
				generales.setCodTabla("0");
				generales.setCodUnico("0");
				generales.setDescripcion("NO EXISTEN DATOS");
				generales.setIdGeneral(0);
				generales.setIsActive("Y");
				listGeneral.add(generales);
			}

    		listaItems.add(new SelectItem(0,"escoja una opcion"));
			for(int i=0;i<listGeneral.size();i++){			
				listaItems.add(new SelectItem(listGeneral.get(i).getCodUnico(),listGeneral.get(i).getDescripcion())); 
			}
		}catch (Exception e) {
			//FacesMessage mensaje=null;
			e.printStackTrace();
		}

	}
	
	public void cambiaPropina(){
		double valorIva, totalFactura;
		valorIva = ( this.getSubtotalSinImpuesto() - this.getTotalDescuento() ) * 0.12;
		this.setIva12(valorIva);
		if (isPropina()){
			this.setPropina10((this.getSubtotalSinImpuesto() - this.getTotalDescuento()) * 0.10);
		}else{
			this.setPropina10(0);				
		}

		totalFactura = (this.getSubtotalSinImpuesto() - this.getTotalDescuento()) + this.getIva12() + this.getPropina10(); 
		this.setValorTotalFactura(totalFactura);		
	}
	public void llenaProductos(){
		try{
			listProducto = buscarProductos();
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	
	 public void ubicarValorPuntoEmision() {  		 
		 puntoEmision = verCamposPE.getId().getCodPuntEmision().trim();
		 TipoAmbiente = verCamposPE.getTipoAmbiente();
    } 

	 public void ubicarValorEstablecimiento() {  		 
		 codEstablecimiento = verCampos.getId().getCodEstablecimiento().trim();
		 if(!codEstablecimiento.trim().equals(""))
			 visibleBotonPuntoEmision = false;
     } 
	 
	
	
	public void llenaEstablecimientos(){
		try{
			listEstablecimientos = cargaEstablecimientos();		
			//si viene vacia
			if (listEstablecimientos.isEmpty()){		
                establecimientoPK.setCodEstablecimiento("");
                establecimientoPK.setRuc("");
                establecimiento.setId(establecimientoPK);
				establecimiento.setCorreo("");
				establecimiento.setDireccionEstablecimiento("");
				establecimiento.setIsActive("0");
				establecimiento.setMensaje(" ");
				establecimiento.setPathAnexo("");
				listEstablecimientos.add(establecimiento);
			}
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}

	public void llenaPuntosEmision(){
		try{
			if (codEstablecimiento == null){
				mensajeAlerta("Mensaje del sistema","Ingrese El Establecimiento antes del Punto Emision", "Informacion");
			}else{
				listPuntosEmision = cargaPuntosEmision();		
				//si viene vacia
				if (listPuntosEmision.isEmpty()){			
	                puntoEmisionPK.setCodEstablecimiento("");
	                puntoEmisionPK.setRuc("");
	                puntoEmisionPK.setSecuencial(null);
	                puntoEmisionObj.setId(puntoEmisionPK);
	                puntoEmisionObj.setFormaEmision("");
	                puntoEmisionObj.setIsActive("0");
	                puntoEmisionObj.setTipoAmbiente("");
	                
	                listPuntosEmision.add(puntoEmisionObj);
				}
			}
			
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}

	}
	
	
	public List<FacGeneral> buscarPorMaestro (String maestro){
		List<FacGeneral> listaFacGeneral = null;
		try{

			listaFacGeneral = facDocumentoServicio.buscarDatosPorMaestro(maestro);
			
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  listaFacGeneral;
	}


	public FacGeneral getGenerales() {
		return generales;
	}

	public void setGenerales(FacGeneral generales) {
		this.generales = generales;
	}

	public List<FacGeneral> getListGeneral() {
		return listGeneral;
	}

	public void setListGeneral(List<FacGeneral> listGeneral) {
		this.listGeneral = listGeneral;
	}

	public FacCabDocumento getCabDocumento() {
		return cabDocumento;
	}

	public void setCabDocumento(FacCabDocumento cabDocumento) {
		this.cabDocumento = cabDocumento;
	}

	public List<SelectItem> getLi() {
		return li;
	}


	public void setLi(List<SelectItem> li) {
		this.li = li;
	}
	
	public List<FacCabDocumento> getListCabDocumento() {
		return listCabDocumento;
	}

	
	public void setListCabDocumento(List<FacCabDocumento> listCabDocumento) {
		this.listCabDocumento = listCabDocumento;
	}

	public FacturaServicio getfacDocumentoServicio() {
		return facDocumentoServicio;
	}

	public void setfacDocumentoServicio(FacturaServicio facDocumentoServicio) {
		this.facDocumentoServicio = facDocumentoServicio;
	}

	public String getRuc(){
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public FacEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(FacEmpresa empresa) {
		this.empresa = empresa;
	}

	public List<FacEmpresa> getListEmpresa() {
		return listEmpresa;
	}

	public void setListEmpresa(List<FacEmpresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}


	public String getCodEstablecimiento() {
		return codEstablecimiento;
	}


	public void setCodEstablecimiento(String codEstablecimiento) {
		this.codEstablecimiento = codEstablecimiento;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public int getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(int secuencial) {
		this.secuencial = secuencial;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}





	public String getGuiaDeRemision() {
		return guiaDeRemision;
	}

	public void setGuiaDeRemision(String guiaDeRemision) {
		this.guiaDeRemision = guiaDeRemision;
	}


    
	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getTipoComprador() {
		return tipoComprador;
	}

	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
	}

	public String getIdentificacionDelComprador() {
		return identificacionDelComprador;
	}


	public void setIdentificacionDelComprador(String identificacionDelComprador) {
		this.identificacionDelComprador = identificacionDelComprador;
	}


	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccionEstablecimiento() {
		return direccionEstablecimiento;
	}

	public void setDireccionEstablecimiento(String direccionEstablecimiento) {
		this.direccionEstablecimiento = direccionEstablecimiento;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<FacProducto> getListProducto() {
		return listProducto;
	}

	public void setListProducto(List<FacProducto> listProducto) {
		this.listProducto = listProducto;
	}


	public List<FacDetDocumento> getListDetalleDocumento() {
		return listDetalleDocumento;
	}

	public void setListDetalleDocumento(List<FacDetDocumento> listDetalleDocumento) {
		this.listDetalleDocumento = listDetalleDocumento;
	}

	public FacDetDocumento getDetDocumento() {
		return detDocumento;
	}
	
	public List<FacProducto> getFiltraProducto() {
		return filtraProducto;
	}
	public void setFiltraProducto(List<FacProducto> filtraProducto) {
		this.filtraProducto = filtraProducto;
	}
	public FacProducto getVerCamposPro() {
		return verCamposPro;
	}
	public void setVerCamposPro(FacProducto verCamposPro) {
		this.verCamposPro = verCamposPro;
	}
	public void setDetDocumento(FacDetDocumento detDocumento) {
		this.detDocumento = detDocumento;
	}


/*	public String getValorProducto() {
		return valorProducto;
	}

	public void setValorProducto(String valorProducto) {
		this.valorProducto = valorProducto;
	}*/

	public FacDetDocumentoPK getDetDocumentoPK() {
		return detDocumentoPK;
	}

	public void setDetDocumentoPK(FacDetDocumentoPK detDocumentoPK) {
		this.detDocumentoPK = detDocumentoPK;
	}

	public int getSecuencialDetalle() {
		return secuencialDetalle;
	}

	public void setSecuencialDetalle(int secuencialDetalle) {
		this.secuencialDetalle = secuencialDetalle;
	}

	public UIData getDataTableDetalle() {
		return DataTableDetalle;
	}

	public void setDataTableDetalle(UIData dataTableDetalle) {
		DataTableDetalle = dataTableDetalle;
	}

	public double getSubtotalSinImpuesto() {
		return subtotalSinImpuesto;
	}

	public void setSubtotalSinImpuesto(double subtotalSinImpuesto) {
		this.subtotalSinImpuesto = subtotalSinImpuesto;
	}

	public double getSubtotal12() {
		return subtotal12;
	}

	public void setSubtotal12(double subtotal12) {
		this.subtotal12 = subtotal12;
	}

	public double getSubtotal0() {
		return subtotal0;
	}

	public void setSubtotal0(double subtotal0) {
		this.subtotal0 = subtotal0;
	}

	public double getSubtotalNoIva() {
		return subtotalNoIva;
	}

	public void setSubtotalNoIva(double subtotalNoIva) {
		this.subtotalNoIva = subtotalNoIva;
	}

	public double getTotalDescuento() {
		return totalDescuento;
	}

	public void setTotalDescuento(double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public double getTotalvalorICE() {
		return totalvalorICE;
	}

	public void setTotalvalorICE(double totalvalorICE) {
		this.totalvalorICE = totalvalorICE;
	}

	public double getIva12() {
		return iva12;
	}

	public void setIva12(double iva12) {
		this.iva12 = iva12;
	}

	public double getPropina10() {
		return propina10;
	}

	public void setPropina10(double propina10) {
		this.propina10 = propina10;
	}

	public double getValorTotalFactura() {
		return valorTotalFactura;
	}

	public void setValorTotalFactura(double valorTotalFactura) {
		this.valorTotalFactura = valorTotalFactura;
	}

	public List<SelectItem> getTiposComprador() {
		return tiposComprador;
	}

	public void setTiposComprador(List<SelectItem> tiposComprador) {
		this.tiposComprador = tiposComprador;
	}

	public FacCliente getComprador() {
		return comprador;
	}

	public void setComprador(FacCliente comprador) {
		this.comprador = comprador;
	}

	public boolean isPresenta() {
		return presenta;
	}

	public void setPresenta(boolean presenta) {
		this.presenta = presenta;
	}

	public PantallaDetalleDocumento getPantallaDetalleDocumento() {
		return pantallaDetalleDocumento;
	}

	public void setPantallaDetalleDocumento(
			PantallaDetalleDocumento pantallaDetalleDocumento) {
		this.pantallaDetalleDocumento = pantallaDetalleDocumento;
	}

	public List<PantallaDetalleDocumento> getListPantallaDetalleDocumento() {
		return listPantallaDetalleDocumento;
	}

	public void setListPantallaDetalleDocumento(
			List<PantallaDetalleDocumento> listPantallaDetalleDocumento) {
		this.listPantallaDetalleDocumento = listPantallaDetalleDocumento;
	}

	public FacturaServicio getFacDocumentoServicio() {
		return facDocumentoServicio;
	}

	public void setFacDocumentoServicio(FacturaServicio facDocumentoServicio) {
		this.facDocumentoServicio = facDocumentoServicio;
	}

/*	public List<SelectItem> getEstablecimientos() {
		return establecimientos;
	}

	public void setEstablecimientos(List<SelectItem> establecimientos) {
		this.establecimientos = establecimientos;
	}*/

	public List<FacEstablecimiento> getListEstablecimientos() {
		return listEstablecimientos;
	}

	public void setListEstablecimientos(
			List<FacEstablecimiento> listEstablecimientos) {
		this.listEstablecimientos = listEstablecimientos;
	}

	public FacEstablecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(FacEstablecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public FacEstablecimientoPK getEstablecimientoPK() {
		return establecimientoPK;
	}

	public void setEstablecimientoPK(FacEstablecimientoPK establecimientoPK) {
		this.establecimientoPK = establecimientoPK;
	}

	/*public List<SelectItem> getPuntosEmision() {
		return puntosEmision;
	}

	public void setPuntosEmision(List<SelectItem> puntosEmision) {
		this.puntosEmision = puntosEmision;
	}*/

	public List<FacPuntoEmision> getListPuntosEmision() {
		return listPuntosEmision;
	}

	public void setListPuntosEmision(List<FacPuntoEmision> listPuntosEmision) {
		this.listPuntosEmision = listPuntosEmision;
	}

	public FacPuntoEmision getPuntoEmisionObj() {
		return puntoEmisionObj;
	}

	public void setPuntoEmisionObj(FacPuntoEmision puntoEmisionObj) {
		this.puntoEmisionObj = puntoEmisionObj;
	}

	public FacPuntoEmisionPK getPuntoEmisionPK() {
		return puntoEmisionPK;
	}

	public void setPuntoEmisionPK(FacPuntoEmisionPK puntoEmisionPK) {
		this.puntoEmisionPK = puntoEmisionPK;
	}

	public Date getFechaDeEmision() {
		return FechaDeEmision;
	}

	public void setFechaDeEmision(Date fechaDeEmision) {
		FechaDeEmision = fechaDeEmision;
	}

	public boolean isBanderaComprador() {
		return banderaComprador;
	}

	public void setBanderaComprador(boolean banderaComprador) {
		this.banderaComprador = banderaComprador;
	}

	public String getTcRuc() {
		return tcRuc;
	}

	public void setTcRuc(String tcRuc) {
		this.tcRuc = tcRuc;
	}

	public String getTcCed() {
		return tcCed;
	}

	public void setTcCed(String tcCed) {
		this.tcCed = tcCed;
	}

	public String getTcPas() {
		return tcPas;
	}

	public void setTcPas(String tcPas) {
		this.tcPas = tcPas;
	}

	public String getTcCof() {
		return tcCof;
	}

	public void setTcCof(String tcCof) {
		this.tcCof = tcCof;
	}
	public List<FacDetAdicional> getListPantallaDetalleAdicional() {
		return listPantallaDetalleAdicional;
	}
	public void setListPantallaDetalleAdicional(
			List<FacDetAdicional> listPantallaDetalleAdicional) {
		this.listPantallaDetalleAdicional = listPantallaDetalleAdicional;
	}
	public UIData getDataTableDetalleAdicional() {
		return DataTableDetalleAdicional;
	}
	public void setDataTableDetalleAdicional(UIData dataTableDetalleAdicional) {
		DataTableDetalleAdicional = dataTableDetalleAdicional;
	}
	public int getSecuencialDetalleAdicional() {
		return secuencialDetalleAdicional;
	}
	public void setSecuencialDetalleAdicional(int secuencialDetalleAdicional) {
		this.secuencialDetalleAdicional = secuencialDetalleAdicional;
	}
	public FacDetAdicional getDetAdicional() {
		return detAdicional;
	}
	public void setDetAdicional(FacDetAdicional detAdicional) {
		this.detAdicional = detAdicional;
	}
	public FacDetAdicionalPK getDetAdicionalPK() {
		return detAdicionalPK;
	}
	public void setDetAdicionalPK(FacDetAdicionalPK detAdicionalPK) {
		this.detAdicionalPK = detAdicionalPK;
	}
	public boolean isPropina() {
		return propina;
	}
	public void setPropina(boolean propina) {
		this.propina = propina;
	}
	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}
	public void setCodigoTipoDocumento(String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}
	public List<FacEstablecimiento> getFiltraEstablecimiento() {
		return filtraEstablecimiento;
	}
	public void setFiltraEstablecimiento(
			List<FacEstablecimiento> filtraEstablecimiento) {
		this.filtraEstablecimiento = filtraEstablecimiento;
	}
	public FacEstablecimiento getVerCampos() {
		return verCampos;
	}
	public void setVerCampos(FacEstablecimiento verCampos) {
		this.verCampos = verCampos;
	}
	public List<FacPuntoEmision> getFiltraPuntosEmision() {
		return filtraPuntosEmision;
	}
	public void setFiltraPuntosEmision(List<FacPuntoEmision> filtraPuntosEmision) {
		this.filtraPuntosEmision = filtraPuntosEmision;
	}
	public FacPuntoEmision getVerCamposPE() {
		return verCamposPE;
	}
	public void setVerCamposPE(FacPuntoEmision verCamposPE) {
		this.verCamposPE = verCamposPE;
	}
	public String getValorProducto() {
		return valorProducto;
	}
	public void setValorProducto(String valorProducto) {
		this.valorProducto = valorProducto;
	}
	public FacProducto getProducto() {
		return producto;
	}
	public void setProducto(FacProducto producto) {
		this.producto = producto;
	}
	public String getTipoAmbiente() {
		return TipoAmbiente;
	}
	public void setTipoAmbiente(String tipoAmbiente) {
		TipoAmbiente = tipoAmbiente;
	}
	public PantallaDetalleDocumento getSeleccionaDetalleDocumento() {
		return seleccionaDetalleDocumento;
	}
	public void setSeleccionaDetalleDocumento(
			PantallaDetalleDocumento seleccionaDetalleDocumento) {
		this.seleccionaDetalleDocumento = seleccionaDetalleDocumento;
	}

	public boolean isVisibleBotonPuntoEmision() {
		return visibleBotonPuntoEmision;
	}

	public void setVisibleBotonPuntoEmision(boolean visibleBotonPuntoEmision) {
		this.visibleBotonPuntoEmision = visibleBotonPuntoEmision;
	}
	
	
}
