package com.general.controladores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentos.entidades.FacCabDocumento;
import com.documentos.entidades.FacDestinatario;
import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacDetGuiaRemision;
import com.documentos.entidades.FacDetRetencione;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.general.entidades.FacProducto;
import com.general.entidades.FacTiposDocumento;
import com.general.entidades.facDetalleDocumentoEntidad;
import com.general.servicios.FacClienteServicios;
import com.general.servicios.FacDocumentoServicios;
import com.general.servicios.FacEmpresaEmisoraServicios;
import com.general.servicios.FacGeneralServicio;
import com.general.util.DetallesAdicionalesReporte;
import com.general.util.InformacionAdicional;
import com.general.controladores.FacConsultaDocumentoSeleccControladores;
import com.ibm.icu.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ViewScoped
@ManagedBean
@SessionScoped
public class FacConsultaControladores2
{
	@EJB
	private FacDocumentoServicios facDocumentoServicios; // variable en la cual llama la clase de servicio
	@EJB
	private FacEmpresaEmisoraServicios facServEmpresa;
	@EJB
	private FacGeneralServicio facGenSer;
	@EJB
	private FacClienteServicios facCliSer;
	// metodod de variable del web
	private String ruc; // variable que recoge el ruc de la consulta
	private String RazonSocial; // variable que recoge la razon social de la consulta
	private String SeleccionTipo; // variable que recoge el item selecciondo del tipo de documento
	private Date FechaInicio;// dato que se encarga de recoger la fecha de inicio
	private Date FechaFinal;// dato que se encarga de recoger la fecha de final
	private String Correos;// dato de correos que se va a enviar de las facuras
		
	// metodos de tipo de documentos
	private FacTiposDocumento facTiposDocumento;
	private ArrayList<SelectItem> Tipo; // variable que recoge los tipos de documento
	private List<FacTiposDocumento> TipoDocumento; // variable que recoge los tipos de documento
		
	// metodo del detalle de documento
	private List<facDetalleDocumentoEntidad> DetalleDocumento; // registro de consulta de documentos
	private facDetalleDocumentoEntidad [] selectedfacDetalleDocumentoEntidad; // variable que me permite poder seleccionar toda las facturas
	private facDetalleDocumentoEntidad valorDetalleDocumento;// variable que recoge el dato seleccionado
	private String rucCliente;
	private String rucEmpresa;
	protected String loginUsuario;
	private String [] selectedOptionsEmail; // variable que recoge si desea enviar correo con xml o pdf  
	
	private FacEnviarMail claseEmail;// variable que contiene la clase de email
	
	private FacConsultaDocumentoSeleccControladores detalle_documentos_;
	private HashMap<String, String> pathReports = null;
	
	//TODO contructor que se encarga de limpiar el contenido de los campos
	public void Actualizar_paguina() {
		CargarDatos();
	}

	//TODO contructor general que se encarga de cargar los datos primordiales
	public void CargarDatos(){
		System.out.println("CargarDatos... ");
		try{
				
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
			if(sesion.getAttribute("Ruc_Cliente") != null)
			{
				rucCliente = sesion.getAttribute("Ruc_Cliente").toString();
				//loginUsuario = sesion.getAttribute("Cod_Cliente").toString();
				//rucEmpresa = sesion.getAttribute("Ruc_Empresa").toString(); 
			    RazonSocial = sesion.getAttribute("Nombre_Cliente").toString();
			    //System.out.println("rucCliente::"+rucCliente);
			    //System.out.println("rucEmpresa::"+rucEmpresa);
			}
			else{
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
				//System.out.println("Cod_Cliente" + loginUsuario);
				ctx.redirect(ctxPath + "/paginas/consulta/cuenta/login.jsf");
			}
			setCorreos("");
			setRazonSocial(RazonSocial);
			setRuc(rucCliente);
			setSeleccionTipo("01");
			FechaFinal =new Date();
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        cal.add(Calendar.DATE,-7);
		        

			FechaInicio = cal.getTime();
			facTiposDocumento = new FacTiposDocumento();
			valorDetalleDocumento = new facDetalleDocumentoEntidad();
			selectedfacDetalleDocumentoEntidad = null;
			llenarCombo();
			llenarDetalleDocumento();
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	//TODO contructor que se llena el detalle de los documentos
	public void llenarDetalleDocumento()
	{
		try
		{
			System.out.println("llenarDetalleDocumento... ");
			List<facDetalleDocumentoEntidad> DetalleDocumento2; // registro de consulta de documentos
			List<FacCabDocumento> documento_detalles;//registro de detalle de documentos
			List<FacTiposDocumento> tipoDocumentos;// registro de tipos de documento
			List<FacEmpresa> detalleEmpresas; // registro de empresas
			DetalleDocumento = new ArrayList<facDetalleDocumentoEntidad>(); // inicializando DetalleDocumento
			detalle_documentos_ = new FacConsultaDocumentoSeleccControladores();
			
			documento_detalles = BuscarDatosdeDetalleDocumento(new Object[]{ruc,RazonSocial,SeleccionTipo, 
																			((FechaInicio == null)? FechaInicio: (new java.text.SimpleDateFormat("yyyy-MM-dd").format(FechaInicio))),
																			((FechaFinal == null)? FechaFinal: (new java.text.SimpleDateFormat("yyyy-MM-dd").format(FechaFinal))),
																			rucCliente, rucEmpresa});
			tipoDocumentos = buscarPorCodigo();
			//detalleEmpresas = fac_empresas();
			if (documento_detalles!=null)
			{
				DetalleDocumento2 = MotrandoDocumentoFiltrados(documento_detalles,tipoDocumentos);
				for (facDetalleDocumentoEntidad detalle : DetalleDocumento2)
				{
					DetalleDocumento.add(new facDetalleDocumentoEntidad(
																	detalle.getRFCREC(),
																	detalle.getNOMREC(),
																	detalle.getCodDoc(),
																	detalle.getTIPODOC(),
																	detalle.getFOLFAC(),
																	detalle.getTOTAL(),
																	detalle.getFECHA(),
																	detalle.getEDOFAC(),
																	detalle.getPDFARC(),
																	detalle.getXMLARC(),
																	detalle.getEmail(),
																	detalle.getDireccion(),
																	detalle.getFormato(),
																	detalle.getCodEstablecimiento(),
																	detalle.getCodPuntoEmision(),
																	detalle.getCodigoDocumento(),
																	detalle.getSecuencial(),
																	detalle.getDocuAutorizacion(),
																	detalle.getAmbiente(),
																	detalle.getRucEmisor()
																	));
					
					detalle_documentos_ = new FacConsultaDocumentoSeleccControladores(DetalleDocumento);
				}
			}
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	//TODO mostrando filtrtados de documentos para el display del usuario
	private List<facDetalleDocumentoEntidad> MotrandoDocumentoFiltrados(List<FacCabDocumento> documento_detalles,List<FacTiposDocumento> tipoDocumentos) {
		List<facDetalleDocumentoEntidad> detalledocumento = null;
		System.out.println("MotrandoDocumentoFiltrados...");
		try{			
			detalledocumento = facDocumentoServicios.MotrandoDocumentoFiltrados(documento_detalles,tipoDocumentos);      
				
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  detalledocumento;
	}
	
	//TODO listado de detalle de documento
	private List<FacCabDocumento> BuscarDatosdeDetalleDocumento(Object [] obj){
		List<FacCabDocumento> detalledocumento = null;
			
		try{			
			detalledocumento = facDocumentoServicios.MostrandoDetalleDocumento(obj);         
	
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  detalledocumento;
	}

	//TODO contructr de lista de las empresas
	public List<FacEmpresa> fac_empresas() {
		List<FacEmpresa> detalledocumento = null;
			
		try{			
			detalledocumento = facDocumentoServicios.listadoEmpresas(rucEmpresa);        
	
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		return  detalledocumento;
	}
	
	//TODO contructor que se encarga de llenar los combox
	public void llenarCombo(){
		Tipo= new ArrayList<SelectItem>();
		System.out.println("llenarCombo...");
		try{
			TipoDocumento = buscarPorCodigo();
				
			if(TipoDocumento.isEmpty()){
				facTiposDocumento.setIdDocumento(null);
				facTiposDocumento.setDescripcion("NO HAY DATOS");
				facTiposDocumento.setFormatoTexto("");
				facTiposDocumento.setFormatoXML("");
				facTiposDocumento.setIsActive("Y");
				TipoDocumento.add(facTiposDocumento);
			}
			for(int i = 0;i<TipoDocumento.size();i++)
				Tipo.add(new SelectItem(TipoDocumento.get(i).getIdDocumento(), TipoDocumento.get(i).getDescripcion()));
				
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	//TODO contructor de cargar combo 
	public List<FacTiposDocumento> buscarPorCodigo(){
		System.out.println("buscarPorCodigo...");
		List<FacTiposDocumento> listaFacGeneral = null;
			
		try{
			listaFacGeneral = facDocumentoServicios.BuscarDatosdeTipoDocumento();           
			
		}catch (Exception e) {
			e.printStackTrace();
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
			return  listaFacGeneral;
	}
	
	//TODO contructor que se encarga de cargar el archivo seleccionado sea pdf o xml
	public void AbrirArchivo(String Archivo , String direccion){
		try 
		{
			//String Documento = "//192.168.32.90/DataExpress/jzurita/firmados/0992531940001010010100000603720130225.xml";
			if(Archivo.contains(".pdf") && !valorDetalleDocumento.getEDOFAC().toString().trim().equals("AUTORIZADO")){
				mensajeAlerta("Mensaje del sistema", "No se encuentra el archivo pdf porque el documento no esta AUTORIZADO", "peligro");
				return;
			}
			String Documento = direccion + Archivo;
			Documento.replace("/", "\"");
			System.out.println("DOCUMENTO::"+direccion + Archivo);
			File ficheroXLS = new File(Documento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FileInputStream fis = new FileInputStream(ficheroXLS);
			byte[] bytes = new byte[1000];
			int read = 0;

			if (!ctx.getResponseComplete()) {
			   String fileName = ficheroXLS.getName();
			   String contentType = "application/" + (Archivo.contains(".pdf") ? "pdf" : "xml");
			   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
			   response.setContentType(contentType);
			   response.setHeader("Content-Disposition","attachment;filename=" + fileName);
			   ServletOutputStream out = response.getOutputStream();

			   while ((read = fis.read(bytes)) != -1) {
			        out.write(bytes, 0, read);
			   }

			   out.flush();
			   out.close();
			   ctx.responseComplete();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		 	FacesContext context = FacesContext.getCurrentInstance();  
	        context.addMessage(null, new FacesMessage("Successful", "Hello " + "No se encuentra el archivo seleccionado"));  
		}
	}
	
	//TODO contructor que se encarga de autocompletar el ruc del comprador para facilitar al usuario
	public List<String> complete_RucComprador(String parametro_ruc) throws Exception{
		List<String> resultado = new ArrayList<String>();
		resultado = facDocumentoServicios.BuscarfitroEmpresaDocumentos(parametro_ruc, rucEmpresa);		
		return resultado;
	}
	
	//TODO Zipear archivos seleccionado
	private void Zippear(String[] pFile, String pZipFile) throws Exception {
		// objetos en memoria
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ZipOutputStream zipos = null;
		// fichero contenedor del zip
		if(pFile[0] != "" || pFile[1] != ""){
			fos =  new FileOutputStream(pZipFile);
			// fichero comprimido
			zipos = new ZipOutputStream(fos);
		}
		// buffer
		byte[] buffer = new byte[1024];
		try {
			// fichero a comprimir
			for (int i = 0; i < pFile.length; i++) {
				if(pFile[i] != ""){
					fis = new FileInputStream(pFile[i]);
				
					ZipEntry zipEntry = new ZipEntry(new File(pFile[i]).getName());
					zipos.putNextEntry(zipEntry);
					int len = 0;
					// zippear
					while ((len = fis.read(buffer, 0, 1024)) != -1)
						zipos.write(buffer, 0, len);
					// volcar la memoria al disco
					zipos.flush();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// cerramos los files
			if(zipos != null && fis != null && fos != null){
			zipos.close();
			fis.close();
			fos.close();
			}
		} // end try
	} // end Zippear
	
	public void pdfMail(facDetalleDocumentoEntidad valorDetalleDocumento) throws Throwable{ 
		System.out.println("::PDF::");
		System.out.println("CodDoc::"+valorDetalleDocumento.getCodDoc());
		System.out.println("RucPdf::"+rucEmpresa);
		
		valorDetalleDocumento.setCodigoDocumento(valorDetalleDocumento.getCodDoc());
		System.out.println("CodEstablecimiento::"+valorDetalleDocumento.getCodEstablecimiento());
		System.out.println("CodigoDocumento::"+valorDetalleDocumento.getCodigoDocumento());
		System.out.println("CodPuntoEmision::"+valorDetalleDocumento.getCodPuntoEmision());
		System.out.println("Secuencial::"+valorDetalleDocumento.getSecuencial());
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
		System.out.println("pathReports::"+pathReports.toString());
		System.out.println("pathReport::"+pathReport);
		
		FileInputStream is = null;
	    JRDataSource dataSource = null;
		List<FacDetDocumento> lstFactDetDocumento = new ArrayList<FacDetDocumento>();
		List<FacDetAdicional> lstFactDetAdictDocumento = new ArrayList<FacDetAdicional>();
		List<InformacionAdicional> infoAdicional = new ArrayList<InformacionAdicional>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		
		lstFactDetDocumento = facDocumentoServicios.buscarDetDocumentoPorFk(rucEmpresa,valorDetalleDocumento.getCodEstablecimiento(),
				 	 valorDetalleDocumento.getCodPuntoEmision(), 
			 		 valorDetalleDocumento.getSecuencial(),
					 valorDetalleDocumento.getCodigoDocumento(),
					 valorDetalleDocumento.getAmbiente());
		
		lstFactDetAdictDocumento = facDocumentoServicios.buscarDetDocumentoAdicPorFk(rucEmpresa,valorDetalleDocumento.getCodEstablecimiento(),
			 	 valorDetalleDocumento.getCodPuntoEmision(), 
		 		 valorDetalleDocumento.getSecuencial(),
				 valorDetalleDocumento.getCodigoDocumento(),
				 valorDetalleDocumento.getAmbiente());
		
		
		
		for (FacDetDocumento detDocumento : lstFactDetDocumento) {
			FacProducto producto = new FacProducto();
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(detDocumento.getCodPrincipal());
            /*
            producto = facDocumentoServicios.buscarProductoPorId(detDocumento.getCodPrincipal().trim());
            detAd.setDetalle1(producto.getAtributo1());
            detAd.setDetalle2(producto.getAtributo2());
            detAd.setDetalle3(producto.getAtributo3());
            */
            detAd.setCodigoPrincipal(detDocumento.getCodAuxiliar());
            detAd.setDescuento(String.valueOf(detDocumento.getDescuento()));
            detAd.setCodigoAuxiliar(detDocumento.getCodAuxiliar());
            
            detAd.setDescripcion(detDocumento.getDescripcion());
            detAd.setCantidad(String.valueOf(detDocumento.getCantidad()));
            detAd.setPrecioTotalSinImpuesto(String.valueOf(detDocumento.getPrecioTotalSinImpuesto()));
            detAd.setPrecioUnitario(String.valueOf(detDocumento.getPrecioUnitario()));
            detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);            
            detallesAdiciones.add(detAd);		
		}
		
		String name = valorDetalleDocumento.getCodigoDocumento() + valorDetalleDocumento.getCodEstablecimiento() + valorDetalleDocumento.getCodPuntoEmision() + valorDetalleDocumento.getRucEmisor() + valorDetalleDocumento.getSecuencial();
       
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);  
        //String pathReport = "C:\\DataExpress\\DMIRO\\generales\\Jasper\\factura.jasper";
        pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
        //String pathReport = "/reportes/factura.jasper"; 
        
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);  
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(valorDetalleDocumento), obtenerInfoFactura(lstFactDetAdictDocumento,valorDetalleDocumento)), beanCollectionDataSource);  
         
        File dirTemp = new File(System.getProperty("java.io.tmpdir"));
		if(!dirTemp.exists())
			dirTemp.mkdirs();				
		String pdf = dirTemp + dirTemp.separator+ valorDetalleDocumento.getPDFARC();
		
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdf);
          
    }
	
	//TODO contructor que se encarga de enviar los correos
	public void EnviarCorreos(String Evento) throws Exception{
		Boolean correosnoEnviados = false;
		String subjectMensaje = "";
		claseEmail = new FacEnviarMail();
		// verifica si se a seleccionado un documento del registro
		
		if(selectedfacDetalleDocumentoEntidad.length == 0){
			mensajeAlerta("Mensaje del sistema","Debe seleccionar algun documento a adjuntar", "informacion");
			return;
		}
		// validando si se a ingresado el formato re los correos
		String [] listaCorreo = Correos.split(",");

		for (int i = 0; i < listaCorreo.length; i++){ 
			if(!listaCorreo[i].toString().trim().equals(""))
				if(!claseEmail.validar_correo(listaCorreo[i])){
					mensajeAlerta("Mensaje del sistema","El correo :" + listaCorreo[i] + " \n no se encuentra en el formato", "peligro");
					mensajeAlerta("Mensaje del sistema","Por favor corregir el correo", "peligro");
					return;
				}
		}
		FacEmpresa empresas = facDocumentoServicios.listadoEmpr(rucEmpresa); 
		
			if(selectedOptionsEmail.length == 0){/// verifica si a seleccionado el check del archivo pdf
				mensajeAlerta("Mensaje del sistema","Por favor debe seleccionar que tipo de archivo desea adjuntar", "Informacion");
				return;
			}
			
			for(facDetalleDocumentoEntidad seleccionDocumentos: selectedfacDetalleDocumentoEntidad){
				String contenidoMensaje = facGenSer.buscarDatosGeneralPadre("114").getDescripcion();
				String nombreEmpresaMesaje =  facGenSer.buscarDatosGeneralPadre("115").getDescripcion();
				String portal =  facGenSer.buscarDatosGeneralPadre("116").getDescripcion();
				StringBuffer body = new StringBuffer();
				
				contenidoMensaje = contenidoMensaje.replace("|NOMEMAIL|", nombreEmpresaMesaje).toString();
				contenidoMensaje = contenidoMensaje.replace("|DOCFECHAGENERA|", seleccionDocumentos.getFECHA().toString()).toString();
				contenidoMensaje = contenidoMensaje.replace("|FECHA|", (seleccionDocumentos.getFECHA().toString()));
				contenidoMensaje = contenidoMensaje.replace("|NODOCUMENTO|", (seleccionDocumentos.getCodEstablecimiento()+"-"+seleccionDocumentos.getCodPuntoEmision()+"-"+seleccionDocumentos.getSecuencial()));	
				contenidoMensaje = contenidoMensaje.replace("|HELPDESK|", "");
				
				contenidoMensaje = contenidoMensaje.replace("|CLIENTE|", seleccionDocumentos.getNOMREC());
				System.out.println("RucEmpresa::"+rucEmpresa);		
				System.out.println("RucCliente::"+seleccionDocumentos.getRFCREC());
				try{
					FacCliente facCli = facCliSer.buscaUsuarioEmpresa(seleccionDocumentos.getRFCREC(), rucEmpresa);
					if (facCli!=null)
						contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", String.valueOf(facCli.getCodCliente()));
					else
						contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", String.valueOf(facCli.getCodCliente()));
				}catch (Exception e){
					contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", "");
				}
				
				contenidoMensaje = contenidoMensaje.replace("|PORTAL|", portal);
				String ls_tipoDocumento ="";				
				if (seleccionDocumentos.getCodDoc().equals("01"))
					ls_tipoDocumento ="Factura";
				if (seleccionDocumentos.getCodDoc().equals("04"))
					ls_tipoDocumento ="Nota de Credito";
				if (seleccionDocumentos.getCodDoc().equals("07"))
					ls_tipoDocumento ="Comprobante de Retencion";
				if (seleccionDocumentos.getCodDoc().equals("06"))
					ls_tipoDocumento ="Guia de Remision";
				contenidoMensaje = contenidoMensaje.replace("|TIPODOCUMENTO|", ls_tipoDocumento);
				
				body.append(contenidoMensaje);
				/*
				body.append("Estimado(a) cliente;  <br>");
				body.append("Acaba de recibir su documento electrónico generado el " + seleccionDocumentos.getFECHA());
				body.append("<br><br>Saludos cordiales, ");
				body.append("<br>Cima IT, ");
				body.append("<br><br>Servicio proporcionado por DataExpress Internacional");
				body.append("<br><br>Tel. 593 04 2280217");
				*/
				try {
					this.pdfMail(seleccionDocumentos);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String pdf = "",xml = "";
				for (int i = 0; i < selectedOptionsEmail.length; i++) {
					File ficheroXLS = new File("");
					
					//File dirTemp = new File("C:/DataExpress/DMIRO/firmados/");
					File dirTemp = new File(System.getProperty("java.io.tmpdir"));
					if(!dirTemp.exists())
						dirTemp.mkdirs();
					
					if(selectedOptionsEmail[i].toString().trim().equals("PDF"))
					{
						pdf = dirTemp + dirTemp.separator+ seleccionDocumentos.getPDFARC();
						
						System.out.println("VPI - Ruta pdf :"+pdf);
						ficheroXLS = new File(pdf);
						if (ficheroXLS.exists())
						if(!ficheroXLS.isFile())
							pdf = "";
					}
					else
					{	
						xml = dirTemp + dirTemp.separator+ seleccionDocumentos.getXMLARC();
						stringToArchivo(xml, seleccionDocumentos.getDocuAutorizacion());
						System.out.println("VPI - Ruta pdf :"+xml);
						ficheroXLS = new File(xml);
						if (ficheroXLS.exists())
						if(!ficheroXLS.isFile())
							xml = "";
					}
				}
				String Adjuntararchivos[];
				File dirTemp = new File(System.getProperty("java.io.tmpdir"));
				String NombreArchivoZepado = System.getProperty("java.io.tmpdir") + dirTemp.separator+ seleccionDocumentos.getFOLFAC() + ".zip";
				if(Evento.trim().equals("ZIP")){
					NombreArchivoZepado.replace("/", "\"");
					Zippear(new String[]{pdf, xml}, NombreArchivoZepado);
					Adjuntararchivos = new String[]{NombreArchivoZepado};
				}else
				{
					Adjuntararchivos = new String[]{pdf, xml};
				}
				if(xml.toString().trim() != "" || pdf.toString().trim() != ""){
					subjectMensaje = facGenSer.buscarDatosGeneralPadre("113").getDescripcion();
					subjectMensaje = subjectMensaje.replace("|NOMEMAIL|", nombreEmpresaMesaje).toString();
					subjectMensaje = subjectMensaje.replace("|NUMDOC|", seleccionDocumentos.getFOLFAC()).toString();
					System.out.println("Content Message :"+contenidoMensaje);
					System.out.println("Subject Message :"+subjectMensaje);
					
					claseEmail.enviar(empresas, //empresas
									  seleccionDocumentos.getEmail() + ((Correos.toString().trim().equals("")) ? Correos: "," + Correos), //toAddress
									  "",//ccAddress
									  "",//bccAddresss
									  subjectMensaje,//subject
									  //"Documento electrónico No:" + seleccionDocumentos.getFOLFAC() + " de Cima IT",//subject
									  true, //xisHTMLFormat
									  body, //body
									  false,//debug
									  Adjuntararchivos////Adjuntararchivos
									  );
				
					
				}else
					correosnoEnviados = true;
				if(Evento.trim().equals("ZIP")){
					File da = new File(NombreArchivoZepado);
					da.delete();
				}
			}
			if(correosnoEnviados)
				mensajeAlerta("Mensaje del sistema","Algunos documentos no se encuentra el archivo, y no se realizo el envio del correo", "Informacion");
			
		mensajeAlerta("Mensaje del sistema","Correos enviados", "informacion");
	}
	
	public void EnviarCorreos2(String Evento) throws Exception{
		Boolean correosnoEnviados = false;
		claseEmail = new FacEnviarMail();
		// verifica si se a seleccionado un documento del registro
		
		if(selectedfacDetalleDocumentoEntidad.length == 0){
			mensajeAlerta("Mensaje del sistema","Debe seleccionar algun documento a adjuntar", "informacion");
			return;
		}
		// validando si se a ingresado el formato re los correos
		String [] listaCorreo = Correos.split(",");

		for (int i = 0; i < listaCorreo.length; i++){ 
			if(!listaCorreo[i].toString().trim().equals(""))
				if(!claseEmail.validar_correo(listaCorreo[i])){
					mensajeAlerta("Mensaje del sistema","El correo :" + listaCorreo[i] + " \n no se encuentra en el formato", "peligro");
					mensajeAlerta("Mensaje del sistema","Por favor corregir el correo", "peligro");
					return;
				}
		}
		FacEmpresa empresas = facDocumentoServicios.listadoEmpr(rucEmpresa); 
		
			if(selectedOptionsEmail.length == 0){/// verifica si a seleccionado el check del archivo pdf
				mensajeAlerta("Mensaje del sistema","Por favor debe seleccionar que tipo de archivo desea adjuntar", "Informacion");
				return;
			}
			
			for(facDetalleDocumentoEntidad seleccionDocumentos: selectedfacDetalleDocumentoEntidad){
				StringBuffer body = new StringBuffer();
				body.append("Estimado(a) cliente;  <br>");
				body.append("Acaba de recibir su documento electrónico generado el " + seleccionDocumentos.getFECHA());
				body.append("<br><br>Saludos cordiales, ");
				body.append("<br>Cima IT, ");
				body.append("<br><br>Servicio proporcionado por DataExpress Internacional");
				body.append("<br><br>Tel. 593 04 2280217");
				
				String pdf = "",xml = "";
				for (int i = 0; i < selectedOptionsEmail.length; i++) {
					File ficheroXLS = new File("");
					if(selectedOptionsEmail[i].toString().trim().equals("PDF"))
					{
						pdf = empresas.getPathCompFirmados() + seleccionDocumentos.getPDFARC();
						ficheroXLS = new File(pdf);
						if(!ficheroXLS.isFile())
							pdf = "";
					}
					else
					{	
						xml = empresas.getPathCompFirmados() + seleccionDocumentos.getXMLARC();
						ficheroXLS = new File(xml);
						if(!ficheroXLS.isFile())
							xml = "";
					}
				}
				String Adjuntararchivos[];
				String NombreArchivoZepado = "C:/" + seleccionDocumentos.getFOLFAC() + ".zip";
				if(Evento.trim().equals("ZIP")){
					NombreArchivoZepado.replace("/", "\"");
					Zippear(new String[]{pdf, xml}, NombreArchivoZepado);
					Adjuntararchivos = new String[]{NombreArchivoZepado};
				}else
				{
					Adjuntararchivos = new String[]{pdf, xml};
				}
				if(xml.toString().trim() != "" || pdf.toString().trim() != "")
					claseEmail.enviar(empresas, //empresas
									  seleccionDocumentos.getEmail() + ((Correos.toString().trim().equals("")) ? Correos: "," + Correos), //toAddress
									  "",//ccAddress
									  "",//bccAddresss
									  "Documento electrónico No:" + seleccionDocumentos.getFOLFAC() + " de Cima IT",//subject
									  true, //xisHTMLFormat
									  body, //body
									  false,//debug
									  Adjuntararchivos////Adjuntararchivos
									  );
				else
					correosnoEnviados = true;
				if(Evento.trim().equals("ZIP")){
					File da = new File(NombreArchivoZepado);
					da.delete();
				}
			}
			if(correosnoEnviados)
				mensajeAlerta("Mensaje del sistema","Algunos documentos no se encuentra el archivo, y no se realizo el envio del correo", "Informacion");
			
		mensajeAlerta("Mensaje del sistema","Correos enviados", "informacion");
	}
	
	//TODO contructor de mensaje de alerta
	private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
		 FacesContext context = FacesContext.getCurrentInstance();            
	      context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle)); 
     }
	
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getRazonSocial() {
		return RazonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	
	public Date getFechaInicio() {
		return FechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		FechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return FechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		FechaFinal = fechaFinal;
	}

	public String getCorreos() {
		return Correos;
	}

	public void setCorreos(String correos) {
		Correos = correos;
	}

	public FacDocumentoServicios getFacDocumentoServicios() {
		return facDocumentoServicios;
	}
	
	public void setFacDocumentoServicios(FacDocumentoServicios facDocumentoServicios) {
		this.facDocumentoServicios = facDocumentoServicios;
	}

	public FacTiposDocumento getFacTiposDocumento() {
		return facTiposDocumento;
	}

	public void setFacTiposDocumento(FacTiposDocumento facTiposDocumento) {
		this.facTiposDocumento = facTiposDocumento;
	}
	
	public ArrayList<SelectItem> getTipo() {
		return Tipo;
	}

	public void setTipo(ArrayList<SelectItem> tipo) {
		Tipo = tipo;
	}

	public List<FacTiposDocumento> getTipoDocumento() {
		return TipoDocumento;
	}

	public void setTipoDocumento(List<FacTiposDocumento> tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}
	
	public String getSeleccionTipo() {
		return SeleccionTipo;
	}

	public void setSeleccionTipo(String seleccionTipo) {
		SeleccionTipo = seleccionTipo;
	}

	public List<facDetalleDocumentoEntidad> getDetalleDocumento() {
		return DetalleDocumento;
	}
	
	public void setDetalleDocumento(List<facDetalleDocumentoEntidad> detalleDocumento) {
		DetalleDocumento = detalleDocumento;
	}
	
	public facDetalleDocumentoEntidad [] getSelectedfacDetalleDocumentoEntidad() {
		return selectedfacDetalleDocumentoEntidad;
	}

	public void setSelectedfacDetalleDocumentoEntidad(
			facDetalleDocumentoEntidad [] selectedfacDetalleDocumentoEntidad) {
		this.selectedfacDetalleDocumentoEntidad = selectedfacDetalleDocumentoEntidad;
	}

	public facDetalleDocumentoEntidad getValorDetalleDocumento() {
		return valorDetalleDocumento;
	}

	public void setValorDetalleDocumento(facDetalleDocumentoEntidad valorDetalleDocumento) {
		this.valorDetalleDocumento = valorDetalleDocumento;
	}

	public String [] getSelectedOptionsEmail() {
		return selectedOptionsEmail;
	}

	public void setSelectedOptionsEmail(String [] selectedOptionsEmail) {
		this.selectedOptionsEmail = selectedOptionsEmail;
	}
	
	public FacConsultaDocumentoSeleccControladores getDetalle_documentos_prueba() {
		return detalle_documentos_;
	}

	public void setDetalle_documentos_prueba(
			FacConsultaDocumentoSeleccControladores detalle_documentos_prueba) {
		this.detalle_documentos_ = detalle_documentos_prueba;
	}
	
	public void pdf2() throws Throwable
	{
		FileInputStream is = null;
	    JRDataSource dataSource = null;
		List<FacDetDocumento> lstFactDetDocumento = new ArrayList<FacDetDocumento>();
		List<FacDetAdicional> lstFactDetAdictDocumento = new ArrayList<FacDetAdicional>();
		List<InformacionAdicional> infoAdicional = new ArrayList<InformacionAdicional>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		
		lstFactDetDocumento = facDocumentoServicios.buscarDetDocumentoPorFk(rucEmpresa,
																			valorDetalleDocumento.getCodEstablecimiento(),
																		 	valorDetalleDocumento.getCodPuntoEmision(), 
																	 		valorDetalleDocumento.getSecuencial(),
																			valorDetalleDocumento.getCodigoDocumento(),
																			valorDetalleDocumento.getAmbiente());
		
		lstFactDetAdictDocumento = facDocumentoServicios.buscarDetDocumentoAdicPorFk(rucEmpresa,
																					 valorDetalleDocumento.getCodEstablecimiento(),
																					 valorDetalleDocumento.getCodPuntoEmision(), 
																					 valorDetalleDocumento.getSecuencial(),
																					 valorDetalleDocumento.getCodigoDocumento(),
																					 valorDetalleDocumento.getAmbiente());		
		for (FacDetAdicional detAdic : lstFactDetAdictDocumento) {			
			InformacionAdicional infoAd = new InformacionAdicional();	        
	        infoAd.setNombre(detAdic.getNombre());
	        infoAd.setNombre(detAdic.getValor());
	        infoAdicional.add(infoAd);
		}
		
		for (FacDetDocumento detDocumento : lstFactDetDocumento) {
			FacProducto producto = new FacProducto();
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(detDocumento.getCodPrincipal());
            producto = facDocumentoServicios.buscarProductoPorId(detDocumento.getCodPrincipal().trim());
            detAd.setDetalle1(producto.getAtributo1());
            detAd.setDetalle2(producto.getAtributo2());
            detAd.setDetalle3(producto.getAtributo3());
            detAd.setDescuento(String.valueOf(detDocumento.getDescuento()));
            detAd.setCodigoAuxiliar(detDocumento.getCodAuxiliar());
            detAd.setDescripcion(detDocumento.getDescripcion());
            detAd.setCantidad(String.valueOf(detDocumento.getCantidad()));
            detAd.setPrecioTotalSinImpuesto(String.valueOf(detDocumento.getPrecioTotalSinImpuesto()));
            detAd.setPrecioUnitario(String.valueOf(detDocumento.getPrecioUnitario()));
            detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);            
            detallesAdiciones.add(detAd);		
		}
		
		String name = valorDetalleDocumento.getCodigoDocumento() + valorDetalleDocumento.getCodEstablecimiento() + valorDetalleDocumento.getCodPuntoEmision() + ruc + valorDetalleDocumento.getSecuencial();
       
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);  
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/factura.jasper");  
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(valorDetalleDocumento), obtenerInfoFactura()), beanCollectionDataSource);  
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name+".pdf");  
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete();
    }
	
	
	public void pdf() throws Throwable
	{
		if(valorDetalleDocumento.getCodigoDocumento().equals("01") || valorDetalleDocumento.getCodigoDocumento().equals("04"))
			generaReporteFactura();
		//if(valorDetalleDocumento.getCodigoDocumento().equals("04"))
			//generarReporteCred();
		if(valorDetalleDocumento.getCodigoDocumento().equals("06"))
			generaReporteGuiaRemision();
		if(valorDetalleDocumento.getCodigoDocumento().equals("07"))
			generarReporteRetencion();
    }
	
	public void generaReporteFactura() throws Throwable
	{
		System.out.println("::PDF::");
		System.out.println("CodDoc::"+valorDetalleDocumento.getCodDoc());
		System.out.println("RucPdf::"+valorDetalleDocumento.getRucEmisor());
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(','); 
        DecimalFormat df = new DecimalFormat("###,##0.00",otherSymbols);
		
		valorDetalleDocumento.setCodigoDocumento(valorDetalleDocumento.getCodDoc());
		System.out.println("CodEstablecimiento::"+valorDetalleDocumento.getCodEstablecimiento());
		System.out.println("CodigoDocumento::"+valorDetalleDocumento.getCodigoDocumento());
		System.out.println("CodPuntoEmision::"+valorDetalleDocumento.getCodPuntoEmision());
		System.out.println("Secuencial::"+valorDetalleDocumento.getSecuencial());
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
		System.out.println("pathReports::"+pathReports.toString());
		System.out.println("pathReport::"+pathReport);
		
		//FileInputStream is = null;
	    //JRDataSource dataSource = null;
		List<FacDetDocumento> lstFactDetDocumento = new ArrayList<FacDetDocumento>();
		List<FacDetAdicional> lstFactDetAdictDocumento = new ArrayList<FacDetAdicional>();
		List<InformacionAdicional> infoAdicional = new ArrayList<InformacionAdicional>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		
		lstFactDetDocumento = facDocumentoServicios.buscarDetDocumentoPorFk(valorDetalleDocumento.getRucEmisor(),
																			valorDetalleDocumento.getCodEstablecimiento(),
																			valorDetalleDocumento.getCodPuntoEmision(), 
																			valorDetalleDocumento.getSecuencial(),
																			valorDetalleDocumento.getCodigoDocumento(),
																			valorDetalleDocumento.getAmbiente());
		
		lstFactDetAdictDocumento = facDocumentoServicios.buscarDetDocumentoAdicPorFk(valorDetalleDocumento.getRucEmisor(),
																					 valorDetalleDocumento.getCodEstablecimiento(),
																					 valorDetalleDocumento.getCodPuntoEmision(), 
																					 valorDetalleDocumento.getSecuencial(),
																					 valorDetalleDocumento.getCodigoDocumento(),
																					 valorDetalleDocumento.getAmbiente());
		
		for (FacDetDocumento detDocumento : lstFactDetDocumento)
		{
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(detDocumento.getCodPrincipal());
            detAd.setCodigoAuxiliar(detDocumento.getCodAuxiliar());
            detAd.setDescuento(df.format(detDocumento.getDescuento()));
            detAd.setDescripcion(detDocumento.getDescripcion());
            detAd.setCantidad(df.format(detDocumento.getCantidad()));
            detAd.setPrecioTotalSinImpuesto(df.format(detDocumento.getPrecioTotalSinImpuesto()));
            detAd.setPrecioUnitario(df.format(detDocumento.getPrecioUnitario()));
            detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);
            detallesAdiciones.add(detAd);
		}
		
		//String name = valorDetalleDocumento.getCodigoDocumento() + valorDetalleDocumento.getCodEstablecimiento() + valorDetalleDocumento.getCodPuntoEmision() + valorDetalleDocumento.getRucEmisor() + valorDetalleDocumento.getSecuencial();
		String name = valorDetalleDocumento.getPDFARC();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);  
        pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());        
        //String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);  
        String reportPath = pathReport;
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(valorDetalleDocumento), obtenerInfoFactura(lstFactDetAdictDocumento,valorDetalleDocumento)), beanCollectionDataSource);  
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name+".pdf");  
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete();
	}
	
	
	
	public void generarReporteRetencion() throws Throwable
	{
		valorDetalleDocumento.setCodigoDocumento(valorDetalleDocumento.getCodDoc());
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
		
		FileInputStream is = null;
	    JRDataSource dataSource = null;
		List<FacDetRetencione> lstFactDetDocumento = new ArrayList<FacDetRetencione>();
		List<FacDetAdicional> lstFactDetAdictDocumento = new ArrayList<FacDetAdicional>();
		List<InformacionAdicional> infoAdicional = new ArrayList<InformacionAdicional>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		
		List detallesAdicional = new ArrayList();
		
		DetallesAdicionalesReporte detalles = new DetallesAdicionalesReporte();
		
		lstFactDetDocumento = facDocumentoServicios.buscarDetRetencionPorFk(rucEmpresa,
																			valorDetalleDocumento.getCodEstablecimiento(),
																			valorDetalleDocumento.getCodPuntoEmision(),
																			valorDetalleDocumento.getSecuencial(),
																			valorDetalleDocumento.getCodigoDocumento(),
																			valorDetalleDocumento.getAmbiente());
		
		lstFactDetAdictDocumento = facDocumentoServicios.buscarDetDocumentoAdicPorFk(rucEmpresa,
																					 valorDetalleDocumento.getCodEstablecimiento(),
																					 valorDetalleDocumento.getCodPuntoEmision(), 
																					 valorDetalleDocumento.getSecuencial(),
																					 valorDetalleDocumento.getCodigoDocumento(),
																					 valorDetalleDocumento.getAmbiente());
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		DecimalFormat df = new DecimalFormat("###,##0.00", otherSymbols);
		
		for (FacDetRetencione detDocumento : lstFactDetDocumento)
		{
			String comprobante = "";
			if (detDocumento.getCodDocSustento().trim().equals("01")) comprobante = "FACTURA";
			if (detDocumento.getCodDocSustento().trim().equals("04")) comprobante = "NOTA DE CREDITO";
			if (detDocumento.getCodDocSustento().trim().equals("05")) comprobante = "NOTA DE DEBITO";
			if (detDocumento.getCodDocSustento().trim().equals("06")) comprobante = "GUIA DE REMISION";
			
			detalles = new DetallesAdicionalesReporte();
			detalles.setBaseImponible(Double.parseDouble(df.format(detDocumento.getBaseImponible())));
			detalles.setNombreComprobante(comprobante);
			detalles.setFechaEmisionCcompModificado(detDocumento.getFechaEmisionDocSustento());
			detalles.setNumeroComprobante(String.valueOf(detDocumento.getNumDocSustento()));
			
			System.out.println(" PorcentajeRetencion..." + detDocumento.getPorcentajeRetencion());
			//detalles.setPorcentajeRetencion(String.valueOf(detDocumento.getPorcentajeRetencion()));
			detalles.setPorcentajeRetener(String.valueOf((detDocumento.getPorcentajeRetencion())));
			detalles.setValorRetenido(String.valueOf((detDocumento.getValor())));
			
			boolean flagIva = false;
			String descripcion = "";
			
			System.out.println(" CodImpuesto..." + detDocumento.getId().getCodImpuesto());
			if (detDocumento.getId().getCodImpuesto().toString().equals("1")){
				 descripcion = "RET. FTE";
			}
			if (detDocumento.getId().getCodImpuesto().toString().equals("2")){
				 flagIva = true;
				 descripcion = "IVA";
			}
			if (detDocumento.getId().getCodImpuesto().toString().equals("6")){
				 descripcion = "ISD";
			}
			if (flagIva)
			{
				if (detDocumento.getCodPorcentaje().equals("1")){
					detalles.setPorcentajeRetener(String.valueOf("721"));
				}
				if (detDocumento.getCodPorcentaje().equals("2")){
					detalles.setPorcentajeRetener(String.valueOf("723"));
				}
				if (detDocumento.getCodPorcentaje().equals("3")){
					detalles.setPorcentajeRetener(String.valueOf("725"));
				}
			}else{
				detalles.setPorcentajeRetener(String.valueOf((detDocumento.getPorcentajeRetencion())));
			}
			//general = this.servicio.buscarNombreCodigo(String.valueOf(((FacDetRetencione)detRetencion.get(i)).getCodImpuesto()), "29");							 
			detalles.setNombreImpuesto(descripcion);							 
			detallesAdicional.add(detalles);	
		 
			
			
			//FacProducto producto = new FacProducto();
            //DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            //detAd.setBaseImponible(detDocumento.getBaseImponible());
            
            /*detAd.setCodigoPrincipal(detDocumento getCodPrincipal());
            detAd.setCodigoAuxiliar(detDocumento.getCodAuxiliar());
            detAd.setDescuento(String.valueOf(detDocumento.getDescuento()));
            detAd.setDescripcion(detDocumento.getDescripcion());
            detAd.setCantidad(String.valueOf(detDocumento.getCantidad()));
            detAd.setPrecioTotalSinImpuesto(String.valueOf(detDocumento.getPrecioTotalSinImpuesto()));
            detAd.setPrecioUnitario(String.valueOf(detDocumento.getPrecioUnitario()));*/
            
			//detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);
            //detallesAdiciones.add(detAd);
		}
		
		String name = valorDetalleDocumento.getCodigoDocumento() + valorDetalleDocumento.getCodEstablecimiento() + valorDetalleDocumento.getCodPuntoEmision() + ruc + valorDetalleDocumento.getSecuencial();
		
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdicional);  
        pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());        
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);  
        //JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(), obtenerInfoFactura(lstFactDetAdictDocumento)), beanCollectionDataSource);  
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(valorDetalleDocumento), obtenerInfoComprobanteRetencion(lstFactDetAdictDocumento)), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name+".pdf");  
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete();
		
	}
	
	
	/*
    public String generarReporteCred() throws Throwable
    {
	   FileInputStream is = null;
	   JRDataSource dataSource = null;
	   List detallesAdiciones = new ArrayList();
	   List infoAdicional = new ArrayList();
	   List detDocumento = new ArrayList();
	   List detAdicional = new ArrayList();
	   
	   detDocumento = emite.getInfEmisor().getListDetDocumentos();
	   DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	   otherSymbols.setDecimalSeparator('.');
	   otherSymbols.setGroupingSeparator(','); 
	   DecimalFormat df = new DecimalFormat("###,##0.00",otherSymbols);
		   
		   if (detDocumento!= null){
			   if (!detDocumento.isEmpty()){
				   for (int i = 0; i < emite.getInfEmisor().getListDetDocumentos().size(); i++) {
					   DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
					   detAd.setCodigoPrincipal(emite.getInfEmisor().getListDetDocumentos().get(i).getCodigoPrincipal());

					   detAd.setDescuento(df.format((emite.getInfEmisor().getListDetDocumentos().get(i).getDescuento())));
					   detAd.setCodigoAuxiliar(emite.getInfEmisor().getListDetDocumentos().get(i).getCodigoAuxiliar());
					   detAd.setDescripcion(emite.getInfEmisor().getListDetDocumentos().get(i).getDescripcion());
					   detAd.setCantidad(String.valueOf(emite.getInfEmisor().getListDetDocumentos().get(i).getCantidad()));
					   detAd.setPrecioTotalSinImpuesto(df.format(emite.getInfEmisor().getListDetDocumentos().get(i).getPrecioTotalSinImpuesto()));
					   detAd.setPrecioUnitario(df.format(new Double(emite.getInfEmisor().getListDetDocumentos().get(i).getPrecioUnitario())));
					   detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);
					   detallesAdiciones.add(i, detAd);
				   }
			   }
		   }
	   }

		   dataSource = new JRBeanCollectionDataSource(detallesAdiciones);
		   System.out.println("urlReporte::"+urlReporte);
		   is = new FileInputStream(urlReporte);
		   JasperPrint reporte_view = JasperFillManager.fillReport(is, obtenerMapaParametrosReportes(obtenerParametrosInfoTributaria(emite), obtenerInfoCredito(emite)), dataSource);
		   System.out.println("numcred::"+numcred);
		   JasperExportManager.exportReportToPdfFile(reporte_view, numcred);
	  
	   
	  
	   return numcred;
   }*/
	
	
	public void generaReporteGuiaRemision() throws Throwable
	{
		valorDetalleDocumento.setCodigoDocumento(valorDetalleDocumento.getCodDoc());
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
		
		List<FacDestinatario> listaDestinatarios = new ArrayList<FacDestinatario>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		
		listaDestinatarios = this.facDocumentoServicios.buscarDestinatarios((valorDetalleDocumento.getAmbiente().equals("Pruebas")?1:2),
																			rucEmpresa,
																			valorDetalleDocumento.getCodEstablecimiento(),
																		 	valorDetalleDocumento.getCodPuntoEmision(), 
																	 		valorDetalleDocumento.getSecuencial(),
																			valorDetalleDocumento.getCodigoDocumento());
		
		for(int i=0; i<listaDestinatarios.size(); i++)
		{
			List<FacDetGuiaRemision> listaDetGuia = this.facDocumentoServicios.buscarDetalleDestinatarios(listaDestinatarios.get(i).getId().getAmbiente(),
																										  listaDestinatarios.get(i).getId().getRuc(),
																										  listaDestinatarios.get(i).getId().getCodEstablecimiento(),
																										  listaDestinatarios.get(i).getId().getCodPuntEmision(),
																										  listaDestinatarios.get(i).getId().getSecuencial(),
																										  listaDestinatarios.get(i).getId().getIdentificacionDestinatario());
			for(FacDetGuiaRemision detGuia: listaDetGuia)
			{
				DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
				detAd.setCodigoPrincipal(detGuia.getCodigoInterno());
				detAd.setCodigoAuxiliar(detGuia.getCodigoAdicional());
				detAd.setDescripcion(detGuia.getDescripcion());
				detAd.setCantidad(String.valueOf(detGuia.getCantidad()));
				detallesAdiciones.add(detAd);
			}
		}
		
		String name = valorDetalleDocumento.getCodigoDocumento() + valorDetalleDocumento.getCodEstablecimiento() + valorDetalleDocumento.getCodPuntoEmision() + ruc + valorDetalleDocumento.getSecuencial();
		List guiaLista = new ArrayList();
		
		//JRDataSource dataSource = new JRBeanCollectionDataSource(guiaLista);
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);
        pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(valorDetalleDocumento), obtenerInfoGuia()), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name+".pdf");  
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete();  
	}
	
	
	private Map<String, Object> obtenerInfoGuia(/*List<FacDetAdicional> lstFactDetAdictDocumento*/)
	{
		Map param = new HashMap();
		FacCabDocumento cabDoc = new FacCabDocumento();
		List infoAdicional = new ArrayList();
		List detAdicional = new ArrayList();
		List detalles = new ArrayList();
		try {
			System.out.println("-- INICIO ReporteUtil.obtenerInfoGR --");			
			cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(rucEmpresa,
																   valorDetalleDocumento.getCodEstablecimiento(),
																   valorDetalleDocumento.getCodPuntoEmision(), 
																   valorDetalleDocumento.getSecuencial(),
																   valorDetalleDocumento.getCodigoDocumento(),
																   valorDetalleDocumento.getAmbiente());
			
			//detAdicional = this.servicio.buscarDetAdicional(this.Ruc, this.codEst, this.codPuntEm, this.codDoc, this.secuencial);
			
			if (cabDoc != null)
			{
				param.put("RS_TRANSPORTISTA", cabDoc.getRazonSocialComprador());
			    param.put("RUC_TRANSPORTISTA", cabDoc.getIdentificacionComprador());
			    param.put("FECHA_EMISION", cabDoc.getFechaEmision());
			    param.put("GUIA", cabDoc.getGuiaRemision());
			    param.put("VALOR_TOTAL", Double.valueOf(cabDoc.getImporteTotal()));
			    param.put("IVA", Double.valueOf(cabDoc.getIva12()));
			    param.put("IVA_0", Double.valueOf(cabDoc.getSubtotal0()));
			    param.put("IVA_12", Double.valueOf(cabDoc.getSubtotal12()));
			    param.put("ICE", Double.valueOf(cabDoc.getTotalvalorICE()));
			    param.put("NO_OBJETO_IVA", Double.valueOf(cabDoc.getSubtotalNoIva()));
			    param.put("SUBTOTAL", Double.valueOf(cabDoc.getTotalSinImpuesto()));
			    param.put("PROPINA", Double.valueOf(cabDoc.getPropina()));
			    param.put("TOTAL_DESCUENTO", Double.valueOf(cabDoc.getTotalDescuento()));
			    param.put("PLACA", cabDoc.getPlaca());
			    param.put("PUNTO_PARTIDA", cabDoc.getPartida());
			    param.put("FECHA_INI_TRANSPORTE", cabDoc.getFechaInicioTransporte());
			    param.put("FECHA_FIN_TRANSPORTE", cabDoc.getFechaFinTransporte());
			    //param.put("FECHA_AUTORIZACION", cabDoc.getFechaautorizacion()==null?"":cabDoc.getFechaautorizacion());
			    param.put("FECHA_AUTORIZACION", cabDoc.getFechaAutorizado()==null?"":cabDoc.getFechaAutorizado());
			    param.put("GUIA", cabDoc.getId().getCodEstablecimiento()+"-"+cabDoc.getId().getCodPuntEmision()+"-"+cabDoc.getId().getSecuencial());
				   
				param.put("PUNTO_PARTIDA", cabDoc.getPartida());
				   
				List<FacDestinatario> listaDestinatarios = new ArrayList();
				//listaDestinatarios = this.servicio.buscarDatosDestinatarios(emite.getInfEmisor().getAmbiente(), this.Ruc, this.codEst, this.codPuntEm, this.codDoc, this.secuencial);
				
				listaDestinatarios = this.facDocumentoServicios.buscarDestinatarios((valorDetalleDocumento.getAmbiente().equals("Pruebas")?1:2),
																					rucEmpresa,
																					valorDetalleDocumento.getCodEstablecimiento(),
																				 	valorDetalleDocumento.getCodPuntoEmision(), 
																			 		valorDetalleDocumento.getSecuencial(),
																					valorDetalleDocumento.getCodigoDocumento());
				
				if (!listaDestinatarios.isEmpty())
				{
					for (int i = 0; i < listaDestinatarios.size(); i++)
					{
						param.put("RUC_DESTINATARIO", listaDestinatarios.get(i).getId().getIdentificacionDestinatario());
						param.put("RS_DESTINATARIO", listaDestinatarios.get(i).getRazonSocialDestinatario());
						param.put("DIRECCION_DESTINATARIO", listaDestinatarios.get(i).getDireccion());
						param.put("MOTIVO_TRASLADO", listaDestinatarios.get(i).getMotivoTraslado());
						param.put("NUM_DOC_ADUANERO", listaDestinatarios.get(i).getDocAduanero()==null?"":listaDestinatarios.get(i).getDocAduanero());
						param.put("NUM_DOC_SUSTENTO", listaDestinatarios.get(i).getNumDocSustentoDest()==null?"":listaDestinatarios.get(i).getNumDocSustentoDest());
						param.put("NUM_AUT_DOC_SUSTENTO", listaDestinatarios.get(i).getNumAutDocSustDest()==null?"":listaDestinatarios.get(i).getNumAutDocSustDest());
						param.put("FECHA_EMISION_DOC_SUTENTO", listaDestinatarios.get(i).getFechEmisionDocSustDest()==null?"":listaDestinatarios.get(i).getFechEmisionDocSustDest());
						   
						String lsTipoDoc ="";
						if(listaDestinatarios.get(i).getCodDocSustentoDest()!=null)
						{
							if(listaDestinatarios.get(i).getCodDocSustentoDest().equals("01"))
								lsTipoDoc = "Factura";
							else if(listaDestinatarios.get(i).getCodDocSustentoDest().equals("04"))
								lsTipoDoc = "Nota de Crédito";
							else if(listaDestinatarios.get(i).getCodDocSustentoDest().equals("07"))
								lsTipoDoc = "Comprobante Retención";
							else if(listaDestinatarios.get(i).getCodDocSustentoDest().equals("06"))
								lsTipoDoc = "Guía Remisión";
						}
						param.put("TIP_DOC_SUSTENTO", lsTipoDoc);
					}
				}
				
				if (!detAdicional.isEmpty()) {
					for (int i = 0; i < detAdicional.size(); i++) {
						InformacionAdicional infoAdic = new InformacionAdicional();
						infoAdic.setNombre(((FacDetAdicional)detAdicional.get(i)).getNombre());
						infoAdic.setValor(((FacDetAdicional)detAdicional.get(i)).getValor());
						infoAdicional.add(i, infoAdic);
					}
					param.put("INFO_ADICIONAL", infoAdicional);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("-- FIN ReporteUtil.obtenerInfoGR --");
		return param;
	}
	
	public void xml(facDetalleDocumentoEntidad valor) throws Throwable{
		/*
		System.out.println("::PDF::");
		System.out.println("CodDoc::"+valor.getXMLARC());
		System.out.println("CodDoc::"+valor.getCodDoc());
		System.out.println("RucPdf::"+rucEmpresa);
		String nameDocument = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/"+valor.getXMLARC());//"C:\\DataExpress\\DMIRO\\";
		String xmlString = valorDetalleDocumento.getDocuAutorizacion(); //obtengo la cadena
		String Documento = nameDocument;
		Documento.replace("/", "\"");
		
		File ficheroXLS = stringToArchivo(nameDocument, xmlString);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FileInputStream fis = new FileInputStream(ficheroXLS);
		byte[] bytes = new byte[1000];
		int read = 0;

		if (!ctx.getResponseComplete()) {
		   String fileName = ficheroXLS.getName();
		   String contentType = "application/" + (nameDocument.contains(".pdf") ? "pdf" : "xml");
		   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
		   response.setContentType(contentType);
		   response.setHeader("Content-Disposition","attachment;filename=" + fileName);
		   ServletOutputStream outxml = response.getOutputStream();

		   while ((read = fis.read(bytes)) != -1) {
			   outxml.write(bytes, 0, read);
		   }

		   outxml.flush();
		   outxml.close();
		   ctx.responseComplete();
		}
		*/
		System.out.println("::PDF::");
		System.out.println("CodDoc::"+valor.getXMLARC());
		System.out.println("CodDoc::"+valor.getCodDoc());
		System.out.println("RucPdf::"+rucEmpresa);
		/*
		//valorDetalleDocumento.setCodigoDocumento(valorDetalleDocumento.getCodDoc());
		System.out.println("CodEstablecimiento::"+valorDetalleDocumento.getCodEstablecimiento());
		System.out.println("CodigoDocumento::"+valorDetalleDocumento.getCodigoDocumento());
		System.out.println("CodPuntoEmision::"+valorDetalleDocumento.getCodPuntoEmision());
		System.out.println("Secuencial::"+valorDetalleDocumento.getSecuencial());
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(valorDetalleDocumento.getCodigoDocumento());
		System.out.println("pathReports::"+pathReports.toString());
		System.out.println("pathReport::"+pathReport);*/
		//String nameDocument = valorDetalleDocumento.getCodDoc()+valorDetalleDocumento.getCodEstablecimiento()+valorDetalleDocumento.getCodPuntoEmision()+valorDetalleDocumento.getSecuencial();
		//pathReport = "/reportes/"
	    String nameDocument = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/"+valor.getXMLARC());//"C:\\DataExpress\\DMIRO\\";
		//String nameDocument = pathReport+rucEmpresa+".xml";
		String xmlString = valor.getDocuAutorizacion(); //obtengo la cadena
		BufferedWriter out = new BufferedWriter(new FileWriter(nameDocument));
        out.write(xmlString);
        out.flush();
        out.close();
		String Documento = nameDocument;
		Documento.replace("/", "\"");
		
		File ficheroXLS = new File(Documento);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FileInputStream fis = new FileInputStream(ficheroXLS);
		byte[] bytes = new byte[1000];
		int read = 0;

		if (!ctx.getResponseComplete()) {
		   String fileName = ficheroXLS.getName();
		   String contentType = "application/" + (nameDocument.contains(".pdf") ? "pdf" : "xml");
		   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
		   response.setContentType(contentType);
		   response.setHeader("Content-Disposition","attachment;filename=" + fileName);
		   ServletOutputStream outxml = response.getOutputStream();

		   while ((read = fis.read(bytes)) != -1) {
			   outxml.write(bytes, 0, read);
		   }

		   outxml.flush();
		   outxml.close();
		   ctx.responseComplete();
		}
		
	}
	
	public static File stringToArchivo(String rutaArchivo, String contenidoArchivo)
	  {
	    FileOutputStream fos = null;
	    File archivoCreado = null;
	    try
	    {
	    	System.out.println("error::"+rutaArchivo);
	      fos = new FileOutputStream(rutaArchivo);
	      OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
	      for (int i = 0; i < contenidoArchivo.length(); i++) {
	        out.write(contenidoArchivo.charAt(i));
	      }
	      out.close();

	      archivoCreado = new File(rutaArchivo);
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	      int i;
	      System.out.println( ex.getMessage());
	      return null;
	    } finally {
	      try {
	        if (fos != null)
	          fos.close();
	      }
	      catch (Exception ex) {
	    	  ex.printStackTrace();
	    	  System.out.println( ex.getMessage());
	      }
	    }
	    return archivoCreado;
	  }
	
	private Map<String, Object> obtenerParametrosInfoTriobutaria(facDetalleDocumentoEntidad valorDetalleDocumento)
	{
		Map param = new HashMap();
	 
	    FacCabDocumento cabDoc = new FacCabDocumento();
	    FacEmpresa empresa = new FacEmpresa();
	    FacEstablecimiento establecimiento = new FacEstablecimiento();
	    empresa=facServEmpresa.verificarRuc(valorDetalleDocumento.getRucEmisor());
	    try
	    {
	    	cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(valorDetalleDocumento.getRucEmisor(),
	    		   												   valorDetalleDocumento.getCodEstablecimiento(),
	    		   												   valorDetalleDocumento.getCodPuntoEmision(), 
	    		   												   valorDetalleDocumento.getSecuencial(),
	    		   												   valorDetalleDocumento.getCodigoDocumento(),
	    		   												   valorDetalleDocumento.getAmbiente());
	    	if (cabDoc != null) {
	    		empresa = facDocumentoServicios.listadoEmpr(valorDetalleDocumento.getRucEmisor());
	    		establecimiento = facDocumentoServicios.buscarCodEstablecimiento(valorDetalleDocumento.getRucEmisor(),valorDetalleDocumento.getCodEstablecimiento());
	    		param.put("RUC", valorDetalleDocumento.getRucEmisor());
	    		param.put("CLAVE_ACC", (cabDoc.getClaveAcceso().trim().equals("")) || (cabDoc.getClaveAcceso() == null) ? "SIN CLAVE" : cabDoc.getClaveAcceso());
	    		param.put("RAZON_SOCIAL", empresa.getRazonSocial());
	    		param.put("NOM_COMERCIAL", empresa.getRazonComercial());
	    		param.put("DIR_MATRIZ", empresa.getDireccionMatriz());
	    		param.put("SUBREPORT_DIR", "C://resources//reportes//");
	    		param.put("TIPO_EMISION", cabDoc.getTipoEmision().trim().equals("1") ? "NORMAL" : "CONTINGENCIA");
	    		param.put("NUM_AUT", (cabDoc.getAutorizacion() == null) || (cabDoc.getAutorizacion().equals("")) ? null : cabDoc.getAutorizacion());
	    		param.put("FECHA_AUT", cabDoc.getFechaAutorizado() == null ? null : cabDoc.getFechaAutorizado());
	    		param.put("NUM_FACT", cabDoc.getId().getCodEstablecimiento() + "-" + cabDoc.getId().getCodPuntEmision() + "-" + cabDoc.getId().getSecuencial());	         
	    		param.put("AMBIENTE", cabDoc.getId().getAmbiente().intValue() == 1 ? "PRUEBA" : "PRODUCCION");
	    		param.put("DIR_SUCURSAL", establecimiento.getDireccionEstablecimiento());
	    		param.put("CONT_ESPECIAL", empresa.getContribEspecial());
	    		//param.put("LLEVA_CONTABILIDAD", cabDoc.getObligadoContabilidad());
	         
	         if (empresa.getObligContabilidad()!=null){
		         if ((empresa.getObligContabilidad().equals("S"))||(empresa.getObligContabilidad().equals("SI")))
		         param.put("LLEVA_CONTABILIDAD", "SI");
		         if ((empresa.getObligContabilidad().equals("N"))||(empresa.getObligContabilidad().equals("NO")))
			         param.put("LLEVA_CONTABILIDAD", "NO");
	         }else{
	        	 param.put("LLEVA_CONTABILIDAD", "NO");
	         }
	         
	         if (cabDoc.getId().getCodigoDocumento().equals("04")){
	        	 param.put("NUM_DOC_MODIFICADO", (cabDoc.getNumDocSustento() == null) || (cabDoc.getNumDocSustento().equals("")) ? "" : cabDoc.getNumDocSustento());
	        	 //param.put("FECHA_EMISION_DOC_SUSTENTO", cabDoc.getFechaEmisionDocSustento() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(cabDoc.getFechaEmisionDocSustento()));
	         }
	         /*
	         String logoPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/imagenes/logo.png");
	         param.put("LOGO", logoPath);
	         */
	         if (empresa.getPathLogoEmpresa() != null){
	        	 if (empresa.getPathLogoEmpresa().length()>0)
	        	 param.put("LOGO", empresa.getPathLogoEmpresa());
	         }
	         
	       //VPI se agregan campos para reporte
	         param.put("TELEFONO_LOCAL",establecimiento.getTelefono());
	         param.put("CORREO_RETENCION", establecimiento.getCorreo());
	         param.put("MOTIVO", cabDoc.getMotivoRazon());
	         
	       }
	     }
	     catch (Exception e) { e.printStackTrace(); }
	 
	     return param;
	   }
	
	
	private Map<String, Object> obtenerInfoFactura(List<FacDetAdicional> lstFactDetAdictDocumento,facDetalleDocumentoEntidad valorDetalleDocumento)
	{
	     Map param = new HashMap();
	     FacCabDocumento cabDoc = new FacCabDocumento();
	     SimpleDateFormat dateFormat = 
  	            new SimpleDateFormat("dd/MM/yyyy");
	     try {
	       cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(valorDetalleDocumento.getRucEmisor(),valorDetalleDocumento.getCodEstablecimiento(),
																 	 valorDetalleDocumento.getCodPuntoEmision(), 
															 		 valorDetalleDocumento.getSecuencial(),
																	 valorDetalleDocumento.getCodigoDocumento(),
																	 valorDetalleDocumento.getAmbiente());
	       if (cabDoc != null) {
	         param.put("RS_COMPRADOR", cabDoc.getRazonSocialComprador());
	         param.put("RUC_COMPRADOR", cabDoc.getIdentificacionComprador());
	         param.put("FECHA_EMISION", dateFormat.format(cabDoc.getFechaEmision()));
	         param.put("GUIA", cabDoc.getGuiaRemision());
	         
	         /*
	         if (valorDetalleDocumento.getCodigoDocumento().equals("01"))
	        	 param.put("VALOR_TOTAL", Double.valueOf(cabDoc.getImporteTotal()));
	         if (valorDetalleDocumento.getCodigoDocumento().equals("04")){
	        	 double total = cabDoc.getSubtotal12()+cabDoc.getSubtotalNoIva()+cabDoc.getSubtotal0()+cabDoc.getIva12();
	        	 param.put("VALOR_TOTAL", total);
	         }
	         */
	         DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	         otherSymbols.setDecimalSeparator('.');
	         otherSymbols.setGroupingSeparator(','); 
	         DecimalFormat df = new DecimalFormat("###,##0.00",otherSymbols);
	         if (valorDetalleDocumento.getCodigoDocumento().equals("01")){
	        	 //double total = Double.valueOf(cabDoc.getImporteTotal())+cabDoc.getSubtotal12()+cabDoc.getSubtotalNoIva()+cabDoc.getSubtotal0()+cabDoc.getIva12()+cabDoc.getTotalvalorICE();
	        	 ///VPI se comenta xq se duplica total
	        	 double total = cabDoc.getImporteTotal();
	        	 param.put("VALOR_TOTAL", df.format(total));
	         }
	        	
	         if (valorDetalleDocumento.getCodigoDocumento().equals("04")){
	        	 //double total = cabDoc.getSubtotal12()+cabDoc.getSubtotalNoIva()+cabDoc.getSubtotal0()+cabDoc.getIva12()+cabDoc.getTotalvalorICE();
	        	///VPI se comenta xq se duplica total
	        	 double total = cabDoc.getImporteTotal();
	        	 param.put("VALOR_TOTAL", df.format(total));
	         }
	         if (valorDetalleDocumento.getCodigoDocumento().equals("07")){
	        	 dateFormat = 
	        	            new SimpleDateFormat("MM/yyyy");
	        	 param.put("EJERCICIO_FISCAL",dateFormat.format(cabDoc.getFechaEmision()));
	         }
	         param.put("IVA", df.format(cabDoc.getIva12()));
	         //emite.getInfEmisor().getTotalSinImpuestos()
	         param.put("IVA_0", (Double.valueOf(cabDoc.getSubtotal0())==0?"0.00":df.format(cabDoc.getSubtotal0())));
	         //param.put("IVA_12", (Double.valueOf(cabDoc.getSubtotal12())==0?"0.00":Double.valueOf(cabDoc.getSubtotal12())==0));	// HFU
	         param.put("IVA_12", (Double.valueOf(cabDoc.getSubtotal12())==0?"0.00":df.format(cabDoc.getSubtotal12())));	// HFU
	         param.put("ICE", (Double.valueOf(cabDoc.getTotalvalorICE())==0?"0.00":df.format(cabDoc.getTotalvalorICE())));
	         param.put("NO_OBJETO_IVA", (Double.valueOf(cabDoc.getSubtotalNoIva())==0?"0.00":df.format(cabDoc.getSubtotalNoIva())));
	         param.put("SUBTOTAL", (Double.valueOf(cabDoc.getTotalSinImpuesto())==0?"0.00":df.format(cabDoc.getTotalSinImpuesto())));
	         param.put("PROPINA", (Double.valueOf(cabDoc.getPropina())==0?"0.00":df.format(cabDoc.getPropina())));
	         param.put("TOTAL_DESCUENTO", (Double.valueOf(cabDoc.getTotalDescuento())==0?"0.00":df.format(cabDoc.getTotalDescuento())));
	         
	         param.put("FECHA_EMISION_DOC_SUSTENTO", cabDoc.getFechaEmisionDocSustento());
			 //param.put("NUM_DOC_MODIFICADO", cabDoc.getNumDocSustento());
			 
			 param.put("FECHA_AUTORIZACION", cabDoc.getFechaAutorizado()==null?"":cabDoc.getFechaAutorizado());
			 
			 
			 String lsTipoDocModificado ="";
				System.out.println("  TIPO DOC MODIFICADO... " + cabDoc.getCodDocModificado());
				if(cabDoc.getCodDocModificado()!=null)
				{
				   if(cabDoc.getCodDocModificado().equals("01"))
					   lsTipoDocModificado = "Factura";
				   else if(cabDoc.getCodDocModificado().equals("04"))
					   lsTipoDocModificado = "Nota de Crédito";
				   else if(cabDoc.getCodDocModificado().equals("07"))
					   lsTipoDocModificado = "Comprobante Retención";
				   else if(cabDoc.getCodDocModificado().equals("06"))
					   lsTipoDocModificado = "Guía Remisión";
			    }
				param.put("TIP_DOC_MODIFICADO", lsTipoDocModificado);
	       }
	       
	         if (cabDoc.getInfoAdicional()!= null)
			 {
	  	       for (FacDetAdicional detAdic : lstFactDetAdictDocumento) {							
			        param.put(detAdic.getNombre(), detAdic.getValor());
				}
			 }
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     return param;
	}
		
	private Map<String, Object> obtenerInfoFactura()
	   {
	     Map param = new HashMap();
	 
	     FacCabDocumento cabDoc = new FacCabDocumento();
	     
	     try {
	       cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(rucEmpresa,valorDetalleDocumento.getCodEstablecimiento(),
																 	 valorDetalleDocumento.getCodPuntoEmision(), 
															 		 valorDetalleDocumento.getSecuencial(),
																	 valorDetalleDocumento.getCodigoDocumento(),
																	 valorDetalleDocumento.getAmbiente());
	       if (cabDoc != null) {
	         param.put("RS_COMPRADOR", cabDoc.getRazonSocialComprador());
	         param.put("RUC_COMPRADOR", cabDoc.getIdentificacionComprador());
	         param.put("FECHA_EMISION", cabDoc.getFechaEmision());
	         param.put("GUIA", cabDoc.getGuiaRemision());
	         param.put("VALOR_TOTAL", Double.valueOf(cabDoc.getImporteTotal()));
	         param.put("IVA", Double.valueOf(cabDoc.getIva12()));
	         //emite.getInfEmisor().getTotalSinImpuestos()
	         param.put("IVA_0", Double.valueOf(cabDoc.getSubtotal0()));
	         param.put("IVA_12", Double.valueOf(cabDoc.getSubtotal12()));
	         param.put("ICE", Double.valueOf(cabDoc.getTotalvalorICE()));
	         param.put("NO_OBJETO_IVA", Double.valueOf(cabDoc.getSubtotalNoIva()));
	         param.put("SUBTOTAL", Double.valueOf(cabDoc.getTotalSinImpuesto()));
	         param.put("PROPINA", Double.valueOf(cabDoc.getPropina()));
	         param.put("TOTAL_DESCUENTO", Double.valueOf(cabDoc.getTotalDescuento()));
	       }
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     return param;
	   }
	
	private Map<String, Object> obtenerInfoComprobanteRetencion(List<FacDetAdicional> lstFactDetAdictDocumento)
	{
		Map param = new HashMap();
	    FacCabDocumento cabDoc = new FacCabDocumento();
	    try
	    {
	    	cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(rucEmpresa,valorDetalleDocumento.getCodEstablecimiento(),
																  valorDetalleDocumento.getCodPuntoEmision(), 
																  valorDetalleDocumento.getSecuencial(),
																  valorDetalleDocumento.getCodigoDocumento(),
																  valorDetalleDocumento.getAmbiente());
	        if (cabDoc != null)
	        {
	        	param.put("PERIODO_FISCAL", cabDoc.getPeriodoFiscal());
	        	param.put("RS_COMPRADOR", cabDoc.getRazonSocialComprador());
	         
	        	param.put("RUC_COMPRADOR", cabDoc.getIdentificacionComprador());
	        	param.put("FECHA_EMISION", cabDoc.getFechaEmision());
	        	param.put("GUIA", cabDoc.getGuiaRemision());
	        	param.put("FECHA_AUTORIZACION", cabDoc.getFechaAutorizado()==null?"":cabDoc.getFechaAutorizado());
	        	param.put("nombreImpuesto", "xxx");
	        	
		       
	        	if (valorDetalleDocumento.getCodigoDocumento().equals("01"))
	        		param.put("VALOR_TOTAL", Double.valueOf(cabDoc.getImporteTotal()));
	        	if (valorDetalleDocumento.getCodigoDocumento().equals("04")){
	        		double total = cabDoc.getSubtotal12()+cabDoc.getSubtotalNoIva()+cabDoc.getSubtotal0()+cabDoc.getIva12();
	        		param.put("VALOR_TOTAL", total);
	        	}
	        	
	        	/*
	        	param.put("IVA", Double.valueOf(cabDoc.getIva12()));
	        	//emite.getInfEmisor().getTotalSinImpuestos()
	        	param.put("IVA_0", (Double.valueOf(cabDoc.getSubtotal0())==0?"0.00":Double.valueOf(cabDoc.getSubtotal0())));
	        	//param.put("IVA_12", (Double.valueOf(cabDoc.getSubtotal12())==0?"0.00":Double.valueOf(cabDoc.getSubtotal12())==0));	// HFU
	        	param.put("IVA_12", (Double.valueOf(cabDoc.getIva12())==0?"0.00":Double.valueOf(cabDoc.getIva12())));	// HFU
	        	param.put("ICE", (Double.valueOf(cabDoc.getTotalvalorICE())==0?"0.00":Double.valueOf(cabDoc.getTotalvalorICE())));
	        	param.put("NO_OBJETO_IVA", (Double.valueOf(cabDoc.getSubtotalNoIva())==0?"0.00":Double.valueOf(cabDoc.getSubtotalNoIva())));
	        	param.put("SUBTOTAL", (Double.valueOf(cabDoc.getTotalSinImpuesto())==0?"0.00":Double.valueOf(cabDoc.getTotalSinImpuesto())));
	        	param.put("PROPINA", (Double.valueOf(cabDoc.getPropina())==0?"0.00":Double.valueOf(cabDoc.getPropina())));
	        	param.put("TOTAL_DESCUENTO", (Double.valueOf(cabDoc.getTotalDescuento())==0?"0.00":Double.valueOf(cabDoc.getTotalDescuento())));
	         
	        	param.put("FECHA_EMISION_DOC_SUSTENTO", cabDoc.getFechaEmisionDocSustento());
	        	param.put("NUM_DOC_MODIFICADO", cabDoc.getNumDocModificado());
			 
	        	param.put("FECHA_AUTORIZACION", cabDoc.getFechaAutorizado()==null?"":cabDoc.getFechaAutorizado());
			 
	        	String lsTipoDocModificado ="";
				System.out.println("  TIPO DOC MODIFICADO... " + cabDoc.getCodDocModificado());
				if(cabDoc.getCodDocModificado()!=null)
				{
				   if(cabDoc.getCodDocModificado().equals("01"))
					   lsTipoDocModificado = "Factura";
				   else if(cabDoc.getCodDocModificado().equals("04"))
					   lsTipoDocModificado = "Nota de Crédito";
				   else if(cabDoc.getCodDocModificado().equals("07"))
					   lsTipoDocModificado = "Comprobante Retención";
				   else if(cabDoc.getCodDocModificado().equals("06"))
					   lsTipoDocModificado = "Guía Remisión";
			    }
				param.put("TIP_DOC_MODIFICADO", lsTipoDocModificado);
				*/
	        }
	        
	        for (FacDetAdicional detAdic : lstFactDetAdictDocumento) {
		        param.put(detAdic.getNombre(), detAdic.getValor());
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return param;
	}
	
	private Map<String, Object> obtenerMapaParametrosReportes(Map<String, Object> mapa1, Map<String, Object> mapa2)
	{
		mapa1.putAll(mapa2);
	    return mapa1;
	}

	public String getRucCliente() {
		return rucCliente;
	}

	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}

	public String getRucEmpresa() {
		return rucEmpresa;
	}

	public void setRucEmpresa(String rucEmpresa) {
		this.rucEmpresa = rucEmpresa;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public HashMap<String, String> getPathReports() {
		return pathReports;
	}

	public void setPathReports(HashMap<String, String> pathReports) {
		this.pathReports = pathReports;
	}
	
	
	
}