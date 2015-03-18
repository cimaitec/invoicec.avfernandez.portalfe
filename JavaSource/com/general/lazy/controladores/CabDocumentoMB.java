package com.general.lazy.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.event.CloseEvent;
import org.primefaces.model.LazyDataModel;

import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacEmpresa;
import com.general.controladores.FacEnviarMail;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.general.entidades.FacProducto;
import com.general.entidades.FacTiposDocumento;
import com.general.lazy.connection.MyTransaction;
import com.general.lazy.servicios.CabDocumentoDAO;
import com.general.lazy.wrappers.FacCabDocumento;
import com.general.servicios.FacClienteServicios;
import com.general.servicios.FacDocumentoServicios;
import com.general.servicios.FacEmpresaEmisoraServicios;
import com.general.servicios.FacGeneralServicio;
import com.general.util.DetallesAdicionalesReporte;
import com.general.util.InformacionAdicional;


@ViewScoped
@ManagedBean
public class CabDocumentoMB implements Serializable
{
	private static final long serialVersionUID = 1L;
	private LazyDataModel<FacCabDocumento> documentos = null;
	private FacCabDocumento documento;
	private FacCabDocumento[] seleccionDocu;
	
	//Fields Filters
	private String ruc; 
	private String razonSocial;
	private String rucCliente;
	
	private ArrayList<SelectItem> Tipo; // variable que recoge los tipos de documento
	private String SeleccionTipo; // variable que recoge el item selecciondo del tipo de documento
	private List<FacTiposDocumento> TipoDocumento; // variable que recoge los tipos de documento
	private String numDocumento;
	private ArrayList<SelectItem> TipoEstados; // variable que recoge los tipos de estados de los documentos
	private String SeleccionTipoEstado; // variable que recoge el item selecciondo del tipo de Estado
	private Date FechaInicio;// dato que se encarga de recoger la fecha de inicio
	private Date FechaFinal;// dato que se encarga de recoger la fecha de final
	
	private List<FacCabDocumento> listAllDocumentos;
	private FacCabDocumento seleccionDocumentos;
	
	//Filters
	private HashMap<String, String> filters = new HashMap<String, String>();
	private HashMap<String, String> Criterios = new HashMap<String, String>();
	
	private String [] selectedOptionsEmail = {"PDF","XML"}; // variable que recoge si desea enviar correo con xml o pdf 
	private FacEnviarMail claseEmail;// variable que contiene la clase de email
	private String Correos;// dato de correos que se va a enviar de las facuras
	private String rucEmpresa;
	
	@EJB
	private FacDocumentoServicios facDocumentoServicios; // variable en la cual llama la clase de servicio
	@EJB
	private FacGeneralServicio facGenSer;
	@EJB
	private FacClienteServicios facCliSer;
	@EJB
	private FacEmpresaEmisoraServicios facServEmpresa;
	
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
			    razonSocial = sesion.getAttribute("Nombre_Cliente").toString();
			    //System.out.println("rucCliente::"+rucCliente);
			    //System.out.println("rucEmpresa::"+rucEmpresa);
			}
			else{
				ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
				String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
				//System.out.println("Cod_Cliente" + loginUsuario);
				ctx.redirect(ctxPath + "/paginas/consulta/cuenta/login.jsf");
			}
			setRazonSocial(razonSocial);
			setRuc(rucCliente);
		}catch (Exception e) {
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}
	
	public void seleccionListDocumentosPdf(){
		if (seleccionDocu!=null){
			if (seleccionDocu.length==0){
				//System.out.println("No hay Datos Seleccionados.");
				FacesMessage mensaje=null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,"Favor Seleccione Por lo menos un Documento.",null);
				FacesContext.getCurrentInstance().addMessage(null, mensaje);				
			}else{
				HashMap<String, String> tipoDocumento = null;
				HashMap<String, Integer> ambientes = null;
	
				tipoDocumento = new HashMap<String, String>();
				tipoDocumento.put("FACTURA", "01");
				tipoDocumento.put("NOTA DE CREDITO", "04");
				tipoDocumento.put("COMPROBANTE DE RETENCION", "07");
				tipoDocumento.put("GUIAS DE REMISIÓN", "06");
					
				ambientes = new HashMap<String, Integer>();
				ambientes.put("Pruebas", 1);
				ambientes.put("Produccion", 2);
	
				
				for(int i = 0; i< seleccionDocu.length;i++){
					System.out.println("secuencial::"+seleccionDocu[i].getId().getSecuencial());
					
					seleccionDocu[i].getId().setAmbiente(ambientes.get(seleccionDocu[i].getId().getAmbiente()).toString());
					seleccionDocu[i].getId().setCodigoDocumento(tipoDocumento.get(seleccionDocu[i].getId().getCodigoDocumento()));
					
					
				}
			}
		}else{
			//System.out.println("No hay Datos Seleccionados is Null.");
			FacesMessage mensaje=null;
			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,"Favor Seleccione Por lo menos un Documento.",null);
			FacesContext.getCurrentInstance().addMessage(null, mensaje);

		}
	}
	
	@PostConstruct
	public void inicializarView(){
		llenarCombo();
		CargarDatos();
		//buscarPorCodigo();
	}
	
	public void xml(FacCabDocumento doc) throws Throwable{ 
		File dirTemp = new File(System.getProperty("java.io.tmpdir"));
		CabDocumentoDAO cabDao = new CabDocumentoDAO(null);
		String codTipoDocumento = cabDao.tipoDocumento.get(doc.getId().getCodigoDocumento());
	    doc.getId().setCodigoDocumento(codTipoDocumento);
	    
		String name =	(doc.getId().getAmbiente().equals("Pruebas")?1:2) + 
				   		doc.getId().getRuc() + 
				   		doc.getId().getCodigoDocumento()+ 
				   		doc.getId().getCodEstablecimiento()+ 
				   		doc.getId().getCodPuntEmision()+ 
				   		doc.getId().getSecuencial()+".xml";
		
		if(!dirTemp.exists())
			dirTemp.mkdirs();
		try{
		File dirFile = new File(dirTemp + dirTemp.separator+name);
		if(dirFile.exists())
			dirFile.exists();
		}catch(Exception e){
			;;
		}
		
		String nameDocument = (dirTemp + dirTemp.separator+name);//"C:\\DataExpress\\DMIRO\\";
		System.out.println("File Xml::"+nameDocument);

		stringToArchivo(nameDocument, doc.getDocuAutorizacion());
		
        
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
	
	
	public String pdf(FacCabDocumento doc, String tipo){
	    CabDocumentoDAO cabDao = new CabDocumentoDAO(null);
	    String codTipoDocumento = cabDao.tipoDocumento.get(doc.getId().getCodigoDocumento());
	    doc.getId().setCodigoDocumento(codTipoDocumento);
	    
		System.out.println(doc.getId().getAmbiente() + 
						   doc.getId().getRuc() + 
						   doc.getId().getCodigoDocumento()+ 
						   doc.getId().getCodEstablecimiento()+ 
						   doc.getId().getCodPuntEmision()+ 
						   doc.getId().getSecuencial());
		HashMap<String, String> pathReports = null;
		pathReports = facGenSer.buscarDatosGeneralPadreHash("103");
		String pathReport = pathReports.get(doc.getId().getCodigoDocumento());
		System.out.println("pathReports::"+pathReports.toString());
		System.out.println("pathReport::"+pathReport);
		
		List<FacDetDocumento> lstFactDetDocumento = new ArrayList<FacDetDocumento>();
		List<FacDetAdicional> lstFactDetAdictDocumento = new ArrayList<FacDetAdicional>();
		List<InformacionAdicional> infoAdicional = new ArrayList<InformacionAdicional>();
		List<DetallesAdicionalesReporte> detallesAdiciones = new ArrayList<DetallesAdicionalesReporte>();
		rucEmpresa = doc.getId().getRuc();
		try
		{
			lstFactDetDocumento = facDocumentoServicios.buscarDetDocumentoPorFk(rucEmpresa,
																				doc.getId().getCodEstablecimiento(),
																				doc.getId().getCodPuntEmision(), 
																				doc.getId().getSecuencial(),
																				doc.getId().getCodigoDocumento(),
																				doc.getId().getAmbiente());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			lstFactDetAdictDocumento = facDocumentoServicios.buscarDetDocumentoAdicPorFk(rucEmpresa,
																						 doc.getId().getCodEstablecimiento(),
																						 doc.getId().getCodPuntEmision(), 
																						 doc.getId().getSecuencial(),
																						 doc.getId().getCodigoDocumento(),
																						 doc.getId().getAmbiente());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (FacDetDocumento detDocumento : lstFactDetDocumento) {
			//FacProducto producto = new FacProducto();
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(detDocumento.getCodPrincipal());
            //detAd.setCodigoPrincipal(detDocumento.getCodAuxiliar());
            detAd.setDescuento(String.valueOf(detDocumento.getDescuento()));
            detAd.setCodigoAuxiliar(detDocumento.getCodAuxiliar());
            detAd.setDescripcion(detDocumento.getDescripcion());
            detAd.setCantidad(String.valueOf(detDocumento.getCantidad()));
            detAd.setPrecioTotalSinImpuesto(String.valueOf(detDocumento.getPrecioTotalSinImpuesto()));
            detAd.setPrecioUnitario(String.valueOf(detDocumento.getPrecioUnitario()));
            detAd.setInfoAdicional(infoAdicional.isEmpty() ? null : infoAdicional);            
            detallesAdiciones.add(detAd);		
		}
		if(tipo.equals("mail")){
			String namePdf =(doc.getId().getAmbiente().equals("Pruebas")?1:2) + 
					   doc.getId().getRuc() + 
					   doc.getId().getCodigoDocumento()+ 
					   doc.getId().getCodEstablecimiento()+ 
					   doc.getId().getCodPuntEmision()+ 
					   doc.getId().getSecuencial()+".pdf";
			File dirTemp = new File(System.getProperty("java.io.tmpdir"));
			if(!dirTemp.exists())
				dirTemp.mkdirs();
			String name = (dirTemp + dirTemp.separator+namePdf);;
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);  
	        //pathReport = "/reportes/"+pathReports.get(doc.getId().getCodigoDocumento());
	        //String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);  
			String reportPath = pathReports.get(doc.getId().getCodigoDocumento());
	        JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(doc), obtenerInfoFactura(lstFactDetAdictDocumento,doc)), beanCollectionDataSource);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				JasperExportManager.exportReportToPdfFile(jasperPrint, name);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return name;
		}else{
			String name = (doc.getId().getAmbiente().equals("Pruebas")?1:2) + 
					   doc.getId().getRuc() + 
					   doc.getId().getCodigoDocumento()+ 
					   doc.getId().getCodEstablecimiento()+ 
					   doc.getId().getCodPuntEmision()+ 
					   doc.getId().getSecuencial()+".pdf";
	        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesAdiciones);  
	        //pathReport = "/reportes/"+pathReports.get(doc.getId().getCodigoDocumento());
	        //String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathReport);  
	        String reportPath = pathReports.get(doc.getId().getCodigoDocumento());
	        JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reportPath, obtenerMapaParametrosReportes(obtenerParametrosInfoTriobutaria(doc), obtenerInfoFactura(lstFactDetAdictDocumento,doc)), beanCollectionDataSource);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name);  
	        ServletOutputStream servletOutputStream = null;
			try {
				servletOutputStream = httpServletResponse.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        try {
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        FacesContext.getCurrentInstance().responseComplete();  
	        /*
	        try {
				servletOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	        return name;
		}
	}
	
	private Map<String, Object> obtenerParametrosInfoTriobutaria(FacCabDocumento doc)
	   {
	     Map param = new HashMap();
	     String pathSubReport= null;
	     com.documentos.entidades.FacCabDocumento cabDoc = new com.documentos.entidades.FacCabDocumento();
	     FacEmpresa empresa = new FacEmpresa();
	     FacEstablecimiento establecimiento = new FacEstablecimiento();
	     empresa=facServEmpresa.verificarRuc(doc.getId().getRuc());
	     
	     try {
	       cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(doc.getId().getRuc(),
	    		   												  doc.getId().getCodEstablecimiento(),
	    		   												  doc.getId().getCodPuntEmision(), 
	    		   												  doc.getId().getSecuencial(),
	    		   												  doc.getId().getCodigoDocumento(),
	    		   												  doc.getId().getAmbiente());
	       if (cabDoc != null) {
	         empresa = facDocumentoServicios.listadoEmpr(rucEmpresa);
	         establecimiento = facDocumentoServicios.buscarCodEstablecimiento(rucEmpresa, doc.getId().getCodEstablecimiento());
	         param.put("RUC", rucEmpresa);
	         param.put("CLAVE_ACC", (cabDoc.getClaveAcceso().trim().equals("")) || (cabDoc.getClaveAcceso() == null) ? "SIN CLAVE" : cabDoc.getClaveAcceso());
	         param.put("RAZON_SOCIAL", empresa.getRazonSocial());
	         param.put("NOM_COMERCIAL", empresa.getRazonComercial());
	         param.put("DIR_MATRIZ", empresa.getDireccionMatriz());
		         if(pathSubReport==null){
		        	 pathSubReport = "";
		         }
	         param.put("SUBREPORT_DIR", pathSubReport);
	         param.put("TIPO_EMISION", cabDoc.getTipoEmision().trim().equals("1") ? "NORMAL" : "CONTINGENCIA");
	         param.put("NUM_AUT", (cabDoc.getAutorizacion() == null) || (cabDoc.getAutorizacion().equals("")) ? "" : cabDoc.getAutorizacion());
	         param.put("FECHA_AUT", cabDoc.getFechaAutorizado() == null ? "" : cabDoc.getFechaAutorizado());
	         param.put("NUM_FACT", cabDoc.getId().getCodEstablecimiento() + "-" + cabDoc.getId().getCodPuntEmision() + "-" + cabDoc.getId().getSecuencial());
	         if (cabDoc.getId().getCodigoDocumento().equals("04")){
	        	 param.put("NUM_DOC_MODIFICADO", (cabDoc.getNumDocSustento() == null) || (cabDoc.getNumDocSustento().equals("")) ? "" : cabDoc.getNumDocSustento());
	        	 //param.put("FECHA_EMISION_DOC_SUSTENTO", cabDoc.getFechaEmisionDocSustento() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(cabDoc.getFechaEmisionDocSustento()));
	        	 param.put("FECHA_EMISION_DOC_SUSTENTO", cabDoc.getFechaEmisionDocSustento());
	         }
	         param.put("AMBIENTE", cabDoc.getId().getAmbiente().intValue() == 1 ? "PRUEBA" : "PRODUCCION");
	         param.put("DIR_SUCURSAL", establecimiento.getDireccionEstablecimiento());
	         param.put("CONT_ESPECIAL", empresa.getContribEspecial());
	         //param.put("LLEVA_CONTABILIDAD", cabDoc.getObligadoContabilidad()); 
	         //CPA
	         if (empresa.getObligContabilidad()!=null){
		         if ((empresa.getObligContabilidad().equals("S"))||(empresa.getObligContabilidad().equals("SI")))
		         param.put("LLEVA_CONTABILIDAD", "SI");
		         if ((empresa.getObligContabilidad().equals("N"))||(empresa.getObligContabilidad().equals("NO")))
			         param.put("LLEVA_CONTABILIDAD", "NO");
	         }else{
	        	 param.put("LLEVA_CONTABILIDAD", "NO");
	         }
	         
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
	
	private Map<String, Object> obtenerInfoFactura(List<FacDetAdicional> lstFactDetAdictDocumento, FacCabDocumento doc)
	{
		Map param = new HashMap();
	    com.documentos.entidades.FacCabDocumento cabDoc = new com.documentos.entidades.FacCabDocumento();
	    try
	    {
	       cabDoc = facDocumentoServicios.buscarCabDocumentoPorPk(rucEmpresa,
	    		   												  doc.getId().getCodEstablecimiento(),
	    		   												  doc.getId().getCodPuntEmision(), 
	    		   												  doc.getId().getSecuencial(),
	    		   												  doc.getId().getCodigoDocumento(),
	    		   												  doc.getId().getAmbiente());
	       if (cabDoc != null)
	       {
	         param.put("RS_COMPRADOR", cabDoc.getRazonSocialComprador());
	         param.put("RUC_COMPRADOR", cabDoc.getIdentificacionComprador());
	         param.put("FECHA_EMISION", cabDoc.getFechaEmision());
	         param.put("GUIA", cabDoc.getGuiaRemision());
	         DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	         otherSymbols.setDecimalSeparator('.');
	         otherSymbols.setGroupingSeparator(','); 
	         DecimalFormat df = new DecimalFormat("###,##0.00",otherSymbols);
	         if (doc.getId().getCodigoDocumento().equals("01")){
	        	 double total = cabDoc.getImporteTotal();
	        	 param.put("VALOR_TOTAL", df.format(total));
	         }
	        	
	         if (doc.getId().getCodigoDocumento().equals("04")){
	        	 double total = cabDoc.getImporteTotal();
	        	 param.put("VALOR_TOTAL", df.format(total));
	         }
	         param.put("IVA", df.format(Double.valueOf(cabDoc.getIva12())));
	         param.put("IVA_0", (Double.valueOf(cabDoc.getSubtotal0())==0?"0.00":df.format(Double.valueOf(cabDoc.getSubtotal0()))));
	         param.put("IVA_12", (Double.valueOf(cabDoc.getSubtotal12())==0?"0.00":df.format(Double.valueOf(cabDoc.getSubtotal12()))));
	         param.put("ICE", (Double.valueOf(cabDoc.getTotalvalorICE())==0?"0.00":df.format(Double.valueOf(cabDoc.getTotalvalorICE()))));
	         param.put("NO_OBJETO_IVA", (Double.valueOf(cabDoc.getSubtotalNoIva())==0?"0.00":df.format(Double.valueOf(cabDoc.getSubtotalNoIva()))));
	         param.put("SUBTOTAL", (Double.valueOf(cabDoc.getTotalSinImpuesto())==0?"0.00":df.format(Double.valueOf(cabDoc.getTotalSinImpuesto()))));
	         param.put("PROPINA", (Double.valueOf(cabDoc.getPropina())==0?"0.00":df.format(Double.valueOf(cabDoc.getPropina()))));
	         param.put("TOTAL_DESCUENTO", (Double.valueOf(cabDoc.getTotalDescuento())==0?"0.00":df.format(Double.valueOf(cabDoc.getTotalDescuento()))));
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
	
	//TODO contructor que se encarga de llenar los combos	
	public void llenarCombo(){
			Tipo= new ArrayList<SelectItem>();
				
			try{
				TipoDocumento = buscarPorCodigo();
				for(int i = 0;i<TipoDocumento.size();i++)
					Tipo.add(new SelectItem(TipoDocumento.get(i).getIdDocumento(), TipoDocumento.get(i).getDescripcion()));
					
			}catch (Exception e) {
				FacesMessage mensaje=null;
				mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			}
			TipoEstados= new ArrayList<SelectItem>();
			TipoEstados.add(new SelectItem("AT", "AUTORIZADO"));
	}

	//TODO contructor de cargar combo 
	public List<FacTiposDocumento> buscarPorCodigo(){
				
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
	
	public ArrayList<SelectItem> getTipo() {
		return Tipo;
	}

	public void setTipo(ArrayList<SelectItem> tipo) {
		Tipo = tipo;
	}

	public String getSeleccionTipo() {
		return SeleccionTipo;
	}

	public void setSeleccionTipo(String seleccionTipo) {
		SeleccionTipo = seleccionTipo;
	}

	public List<FacTiposDocumento> getTipoDocumento() {
		return TipoDocumento;
	}

	public void setTipoDocumento(List<FacTiposDocumento> tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}

	public FacDocumentoServicios getFacDocumentoServicios() {
		return facDocumentoServicios;
	}

	public void setFacDocumentoServicios(FacDocumentoServicios facDocumentoServicios) {
		this.facDocumentoServicios = facDocumentoServicios;
	}

	public FacCabDocumento getDocumento() {
		return documento;
	}
	
	public LazyDataModel<FacCabDocumento> getAllDocumentos() {
		//ArrayList<String, String> filtros = new ArrayList<String, String>();
		filters.clear();
		Criterios.clear();
		String numDocumentoTmp ="";
		if (ruc !=null){
			if ((ruc.trim()).length()>=10){
				filters.put("\"identificacionComprador\"", ruc.trim());
				Criterios.put("\"identificacionComprador\"", "=");
			}
		}
		/*
		if (razonSocial !=null){
			if (razonSocial.trim().length()>0){
				filters.put("\"razonSocialComprador\"", razonSocial);
				Criterios.put("\"razonSocialComprador\"", " like");
			}
		}		
		*/
		if (SeleccionTipo !=null){
			if (SeleccionTipo.trim().length()>0){
				filters.put("\"CodigoDocumento\"", SeleccionTipo);
				Criterios.put("CodigoDocumento", "=");
			}
		}
		if (numDocumento !=null){
			 numDocumentoTmp = numDocumento.replace("-", "");
			if (numDocumentoTmp.trim().length()>14){
				String CodEstablecimiento = numDocumentoTmp.substring(0, 3);
				String CodPuntEmision = numDocumentoTmp.substring(3, 6);
				String secuencial = numDocumentoTmp.substring(6, 15);
				filters.put("CodEstablecimiento",CodEstablecimiento);
				Criterios.put("CodEstablecimiento", "=");
				filters.put("CodPuntEmision",CodPuntEmision);
				Criterios.put("CodPuntEmision", "=");
				filters.put("secuencial",secuencial);
				Criterios.put("secuencial", "=");
				
			}
		}
		if (SeleccionTipoEstado !=null){
			if (SeleccionTipoEstado.trim().length()>0){
				if (!SeleccionTipoEstado.equals("AA")){
				filters.put("\"ESTADO_TRANSACCION\"", SeleccionTipoEstado);
				Criterios.put("\"ESTADO_TRANSACCION\"", "=");
				}
			}
		}
	if (numDocumentoTmp.trim().equals("")){	
		String ls_dateFormat = "yyyyMMMdd";
		if (FechaInicio !=null){
				filters.put("fechaEmision", new java.text.SimpleDateFormat(ls_dateFormat).format(FechaInicio));
				Criterios.put("fechaEmision", ">=");
		}
		if (FechaFinal !=null){
			filters.put("fechaEmision", new java.text.SimpleDateFormat(ls_dateFormat).format(FechaFinal));
			Criterios.put("fechaEmision", "<=");
		}
	}	
		if (documentos == null) {
			documentos = new CabDocumentoLazyList(filters, Criterios);
				
		}
		return documentos;
	}
	
	public void findDocumentos()
	{
		filters.clear();
		Criterios.clear();
		String numDocumentoTmp = "";
		if (ruc !=null){
			if ((ruc.trim()).length()>=5){
				filters.put("\"identificacionComprador\"", (ruc.trim()));
				Criterios.put("\"identificacionComprador\"", "like");
				//Criterios.put("\"identificacionComprador\"", "=");
			}
		}
		/*
		if (razonSocial !=null){
			if (razonSocial.trim().length()>0){
				filters.put("\"razonSocialComprador\"", razonSocial);
				Criterios.put("\"razonSocialComprador\"", "like");
			}
		}*/
		System.out.println("seleccion de tipo "+ SeleccionTipo);
		if (SeleccionTipo !=null){
			if (SeleccionTipo.trim().length()>0){
				filters.put("\"CodigoDocumento\"", SeleccionTipo);
				Criterios.put("\"CodigoDocumento\"", "=");
			}
		}
		if (numDocumento !=null){
			numDocumentoTmp = numDocumento.replace("-", "");
			if (numDocumentoTmp.trim().length()>14){
				String CodEstablecimiento = numDocumentoTmp.substring(0, 3);
				String CodPuntEmision = numDocumentoTmp.substring(3, 6);
				String secuencial = numDocumentoTmp.substring(6, 15);
				filters.put("CodEstablecimiento",CodEstablecimiento);
				Criterios.put("CodEstablecimiento", "=");
				filters.put("CodPuntEmision",CodPuntEmision);
				Criterios.put("CodPuntEmision", "=");
				filters.put("secuencial",secuencial);
				Criterios.put("secuencial", "=");
				
			}
		}
		if (SeleccionTipoEstado !=null){
			if (SeleccionTipoEstado.trim().length()>0){
				if (!SeleccionTipoEstado.equals("AA")){
					filters.put("\"ESTADO_TRANSACCION\"", SeleccionTipoEstado);
					Criterios.put("\"ESTADO_TRANSACCION\"", "=");
				}
			}
		}
		if (numDocumentoTmp.trim().equals("")){		
			String ls_dateFormat = "yyyyMMMdd";
			if (FechaInicio !=null){
					filters.put("fechaEmision", new java.text.SimpleDateFormat(ls_dateFormat).format(FechaInicio));
					Criterios.put("fechaEmision", ">=");
			}
			if (FechaFinal !=null){
				filters.put("fechaEmision", new java.text.SimpleDateFormat(ls_dateFormat).format(FechaFinal));
				Criterios.put("fechaEmision", "<=");
			}
		}
		documentos = new CabDocumentoLazyList(filters, Criterios);
	}
	
	public void findConsultaDoc()
	{
		filters.clear();
		Criterios.clear();
		String numDocumentoTmp = "";
		String mensaje = "";
		boolean flagConsulta = true;
		if (ruc !=null){
			if (ruc.length()>=5){
				filters.put("\"identificacionComprador\"", ruc);
				Criterios.put("\"identificacionComprador\"", "like");
				//Criterios.put("\"identificacionComprador\"", "=");
			}
		}else{
			mensaje = "Debe ingresar la Identificación";
			mensajeSistema(mensaje, FacesMessage.SEVERITY_ERROR);
			flagConsulta = false;
		}
		
		System.out.println("seleccion de tipo "+ SeleccionTipo);
		if (SeleccionTipo !=null){
			if (SeleccionTipo.trim().length()>0){
				filters.put("\"CodigoDocumento\"", SeleccionTipo);
				Criterios.put("\"CodigoDocumento\"", "=");
			}
		}
		if (numDocumento !=null){
			numDocumentoTmp = numDocumento.replace("-", "");
			if (numDocumentoTmp.trim().length()>14){
				String CodEstablecimiento = numDocumentoTmp.substring(0, 3);
				String CodPuntEmision = numDocumentoTmp.substring(3, 6);
				String secuencial = numDocumentoTmp.substring(6, 15);
				filters.put("CodEstablecimiento",CodEstablecimiento);
				Criterios.put("CodEstablecimiento", "=");
				filters.put("CodPuntEmision",CodPuntEmision);
				Criterios.put("CodPuntEmision", "=");
				filters.put("secuencial",secuencial);
				Criterios.put("secuencial", "=");
				
			}
			flagConsulta = false;
		}else{
			mensaje = "Debe ingresar el numero del documento";
			mensajeSistema(mensaje, FacesMessage.SEVERITY_ERROR);
			flagConsulta = false;
		}
		if(flagConsulta){
			documentos = new CabDocumentoLazyList(filters, Criterios);
		}
	}
	

	
	//TODO contructor que se encarga de enviar los correos
	public void EnviarCorreos(String Evento, FacCabDocumento seleccionDocumentos, int index) throws Exception
	{
		Boolean correosnoEnviados = false;
		String subjectMensaje = "";
		String namePdf="";
		claseEmail = new FacEnviarMail();
		// verifica si se a seleccionado un documento del registro
		if(documentos.getRowCount() == 0){
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
		String contenidoMensaje = facGenSer.buscarDatosGeneralPadre("114").getDescripcion();
		String nombreEmpresaMesaje =  facGenSer.buscarDatosGeneralPadre("115").getDescripcion();
		String portal =  facGenSer.buscarDatosGeneralPadre("116").getDescripcion();
		StringBuffer body = new StringBuffer();
		contenidoMensaje = contenidoMensaje.replace("|NOMEMAIL|", nombreEmpresaMesaje).toString();
		contenidoMensaje = contenidoMensaje.replace("|DOCFECHAGENERA|", seleccionDocumentos.getFechaEmision().toString()).toString();
		contenidoMensaje = contenidoMensaje.replace("|FECHA|", (seleccionDocumentos.getFechaEmision().toString()));
		contenidoMensaje = contenidoMensaje.replace("|NODOCUMENTO|", (seleccionDocumentos.getId().getCodEstablecimiento()+"-"+seleccionDocumentos.getId().getCodPuntEmision()+"-"+seleccionDocumentos.getId().getSecuencial()));	
		contenidoMensaje = contenidoMensaje.replace("|HELPDESK|", "");
		//contenidoMensaje = contenidoMensaje.replace("|CLIENTE|", seleccionDocumentos.get);
		/*
		try{
			FacCliente facCli = facCliSer.buscaUsuarioEmpresa(seleccionDocumentos.getIdentificacionComprador(), rucEmpresa);
			
			if (facCli!=null)
				contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", facCli.getCodCliente());
			else
				contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", facCli.getCodCliente());
		}catch (Exception e){
			contenidoMensaje = contenidoMensaje.replace("|CODCLIENTE|", "");
		}
		*/
		contenidoMensaje = contenidoMensaje.replace("|PORTAL|", portal);
		String ls_tipoDocumento ="";				
		if (seleccionDocumentos.getId().getCodigoDocumento().equals("01"))
			ls_tipoDocumento ="Factura";
		if (seleccionDocumentos.getId().getCodigoDocumento().equals("04"))
			ls_tipoDocumento ="Nota de Credito";
		if (seleccionDocumentos.getId().getCodigoDocumento().equals("07"))
			ls_tipoDocumento ="Comprobante de Retencion";
		if (seleccionDocumentos.getId().getCodigoDocumento().equals("06"))
			ls_tipoDocumento ="Guia de Remisión";
		contenidoMensaje = contenidoMensaje.replace("|TIPODOCUMENTO|", ls_tipoDocumento);
		body.append(contenidoMensaje);
		String pdf = "",xml = "";
		for (int i = 0; i < selectedOptionsEmail.length; i++) {
			File ficheroXLS = new File("");
			File dirTemp = new File(System.getProperty("java.io.tmpdir"));
			if(!dirTemp.exists())
				dirTemp.mkdirs();
			if(selectedOptionsEmail[i].toString().trim().equals("PDF"))
			{
				try {	namePdf=pdf(seleccionDocu[index],"mail");	} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pdf = dirTemp + dirTemp.separator+namePdf;
				ficheroXLS = new File(pdf);
				if(!ficheroXLS.isFile())
					pdf = "";
			}
			else
			{	
				//xml = dirTemp + dirTemp.separator+ seleccionDocumentos.getXMLARC();
				//ArchivoUtils.stringToArchivo(xml, seleccionDocumentos.getXmlAutorizacion());
				System.out.println("VPI - Ruta pdf :"+xml);
				ficheroXLS = new File(xml);
				if(!ficheroXLS.isFile())
					xml = "";
			}
		}
		String Adjuntararchivos[];
		//String NombreArchivoZepado = "C:/" + seleccionDocumentos.getFOLFAC() + ".zip";
		if(Evento.trim().equals("ZIP")){
			//NombreArchivoZepado.replace("/", "\"");
			//Zippear(new String[]{pdf, xml}, NombreArchivoZepado);
			//Adjuntararchivos = new String[]{NombreArchivoZepado};
		}else
		{
			Adjuntararchivos = new String[]{pdf, xml};
		}
		if(xml.toString().trim() != "" || pdf.toString().trim() != "")
		{
			subjectMensaje = facGenSer.buscarDatosGeneralPadre("113").getDescripcion();
			subjectMensaje = subjectMensaje.replace("|NOMEMAIL|", nombreEmpresaMesaje).toString();
			//subjectMensaje = subjectMensaje.replace("|NUMDOC|", seleccionDocumentos.getFOLFAC()).toString();
			System.out.println("Content Message :"+contenidoMensaje);
			System.out.println("Subject Message :"+subjectMensaje);
			/*
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
							  );*/
		
			
		}else
			correosnoEnviados = true;
		if(Evento.trim().equals("ZIP")){
			/*File da = new File(NombreArchivoZepado);
			da.delete();
			*/
		}
		//if(correosnoEnviados)
		//mensajeAlerta("Mensaje del sistema","Algunos documentos no se encuentra el archivo, y no se realizo el envio del correo", "Informacion");
				
	}
	
		//TODO Zipear archivos seleccionado
		private void Zippear(String[] pFile, String pZipFile) throws Exception
		{
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
	//TODO contructor de mensaje de alerta
	 private void mensajeAlerta(String mensajeVentana, String mensajeDetalle, String tipo) {
		 FacesContext context = FacesContext.getCurrentInstance();            
	      context.addMessage(null, new FacesMessage((tipo.toString().trim().equals("alerta") ? FacesMessage.SEVERITY_ERROR : tipo.toString().trim().equals("peligro") ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_INFO),mensajeVentana, mensajeDetalle)); 
     }
	
		public static File stringToArchivo(String rutaArchivo, String contenidoArchivo){
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
		      int i;
		      System.out.println(ex.getMessage());
		      return null;
		    } finally {
		      try {
		        if (fos != null)
		          fos.close();
		      }
		      catch (Exception ex) {
		    	  System.out.println(ex.getMessage());
		      }
		    }
		    return archivoCreado;
		  } 
	 
	public FacCabDocumento getPlayer() {
		if(documento == null){
			documento = new FacCabDocumento();
		}
		
		return documento;
	}

	
	public void mensajeSistema (String Mensaje , Severity tipo){
		FacesMessage mensaje= new FacesMessage(tipo,Mensaje, null);
		FacesContext.getCurrentInstance().addMessage("frm1", mensaje);	
	}
	
	
	public void setDocumento(FacCabDocumento player) {
		this.documento = player;
	}

	public LazyDataModel<FacCabDocumento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(LazyDataModel<FacCabDocumento> documentos) {
		this.documentos = documentos;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public HashMap<String, String> getFilters() {
		return filters;
	}

	public void setFilters(HashMap<String, String> filters) {
		this.filters = filters;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public ArrayList<SelectItem> getTipoEstados() {
		return TipoEstados;
	}

	public void setTipoEstados(ArrayList<SelectItem> tipoEstados) {
		TipoEstados = tipoEstados;
	}

	public String getSeleccionTipoEstado() {
		return SeleccionTipoEstado;
	}

	public void setSeleccionTipoEstado(String seleccionTipoEstado) {
		SeleccionTipoEstado = seleccionTipoEstado;
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

	public HashMap<String, String> getCriterios() {
		return Criterios;
	}

	public void setCriterios(HashMap<String, String> criterios) {
		Criterios = criterios;
	}

	public String[] getSelectedOptionsEmail() {
		return selectedOptionsEmail;
	}

	public void setSelectedOptionsEmail(String[] selectedOptionsEmail) {
		this.selectedOptionsEmail = selectedOptionsEmail;
	}

	public List<FacCabDocumento> getListAllDocumentos() {
		return listAllDocumentos;
	}

	public void setListAllDocumentos(List<FacCabDocumento> listAllDocumentos) {
		this.listAllDocumentos = listAllDocumentos;
	}

	public FacCabDocumento getSeleccionDocumentos() {
		return seleccionDocumentos;
	}

	public void setSeleccionDocumentos(FacCabDocumento seleccionDocumentos) {
		this.seleccionDocumentos = seleccionDocumentos;
	}

	public FacCabDocumento[] getSeleccionDocu() {
		return seleccionDocu;
	}

	public void setSeleccionDocu(FacCabDocumento[] seleccionDocu) {
		this.seleccionDocu = seleccionDocu;
	}
	
	
}