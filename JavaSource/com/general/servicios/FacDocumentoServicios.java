package com.general.servicios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacCabDocumento;
import com.documentos.entidades.FacDestinatario;
import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacDetGuiaRemision;
import com.documentos.entidades.FacDetRetencione;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.*;

@Stateless
public class FacDocumentoServicios {
	@PersistenceContext (unitName=ConfigPersistenceUnit.persistenceUnit)  
	private EntityManager em;
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	@SuppressWarnings("unchecked")
	public  List<FacTiposDocumento> BuscarDatosdeTipoDocumento() throws Exception{
		
		try{
			Query q = em.createQuery("SELECT E FROM FacTiposDocumento E where isActive = :Activado");
			q.setParameter("Activado", "Y");
			System.out.println("BuscarDatosdeTipoDocumento::"+q.toString());
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacCabDocumento> MostrandoDetalleDocumento(Object [] obj) throws Exception
	{
		try
		{
			String ls_query ="";
			Query query = null;
			
			
			System.out.println("rucComprador::"+ obj[0].toString());
			System.out.println("razonSocialComp::"+"%"+obj[1].toString()+"%");
			System.out.println("codDocumentoSelecionado::"+obj[2].toString());
			System.out.println("fechaInicio::"+obj[3]);
			System.out.println("fechaFinal::"+obj[4]);
			System.out.println("rucCliente::"+obj[5].toString());
			//System.out.println("rucEmpresa::"+obj[6].toString());
			System.out.println("QUERY::"+ ls_query);
			
			System.out.println();
			
			if(!obj[2].toString().equals("06"))
			{
				ls_query = "SELECT E "+
						   "  FROM FacCabDocumento E "+
						//" where (E.identificacionComprador = :rucComprador or :rucComprador2 = :DatoVacio) " +
						//" and E.razonSocialComprador like :razonSocialComp  " +
						" where E.id.codigoDocumento = :codDocumentoSelecionado " +
						//" and E.id.codigoDocumento = :codDocumentoSelecionado " +
						" and E.fechaEmision >=  :fechaInicio " +
						" and E.fechaEmision <=  :fechaFinal " +
						" and E.estadoTransaccion = 'AT' " +
						" and E.identificacionComprador = :rucCliente " +
						//" and E.id.ruc = :rucEmpresa " +
						" order by E.id.ruc desc, E.fechaEmision desc ";
								
				query = em.createQuery(ls_query);
				/*
				if (obj[0].toString()==null){
					query.setParameter("rucComprador", "");		// parametro de identificador del comprador
					query.setParameter("rucComprador2", "");	// parametro de identificador del comprador
				}else{
					query.setParameter("rucComprador", obj[0].toString());	// parametro de identificador del comprador
					query.setParameter("rucComprador2", obj[0].toString());	// parametro de identificador del comprador
				}			
				query.setParameter("DatoVacio", "");
				*/
				/*if (obj[1].toString()==null){
					query.setParameter("razonSocialComp","%"); // parametro de la razon social
				}else{
					query.setParameter("razonSocialComp","%"+obj[1].toString()+"%"); // parametro de la razon social
				}*/
				
				query.setParameter("codDocumentoSelecionado",obj[2].toString()); // parametro del documento seleccionado
				query.setParameter("fechaInicio", Date.valueOf(obj[3].toString()));
				//Agrego un dia a lo que se haya elegido como fecha final para que se muestre en cosulta
			    Calendar cal = Calendar.getInstance();
		        cal.add(Date.valueOf(obj[4].toString()).getDay(), 1);
				
				//c.add(Calendar.DATE, 1);
				//query.setParameter("fechaFinal", Date.valueOf(obj[4].toString()));
				query.setParameter("fechaFinal", cal.getTime());

				if (obj[5].toString()==null){
					query.setParameter("rucCliente", "");// parametro de identificador del cliente
				}else{
					query.setParameter("rucCliente", obj[5].toString());// parametro de identificador del cliente
				}
				/*
				if (obj[6].toString()==null){
					query.setParameter("rucEmpresa", "");// Ruc Empresa
				}else{
					query.setParameter("rucEmpresa", obj[6].toString());// Ruc Empresa
				}*/
				return query.getResultList();
			}
			
			
			if(obj[2].toString().equals("06"))
			{
				System.out.println("  Es guia de remision...");			
			
				ls_query = "SELECT E FROM FacCabDocumento E "+
						" where (E.identificacionDestinatario = :rucComprador or :rucComprador2 = :DatoVacio) " +
						" and E.razonSocialDestinatario like :razonSocialComp  " +
						" and E.id.codigoDocumento = :codDocumentoSelecionado " +
						" and E.fechaInicioTransporte between :fechaInicio and  :fechaFinal " +
						" and E.estadoTransaccion = 'AT' " +
						" and E.identificacionDestinatario = :rucCliente " +
						" and E.id.ruc = :rucEmpresa " +
						" order by E.id.ruc desc, E.fechaInicioTransporte desc ";
				
				query = em.createQuery(ls_query);
				//query.setParameter("estado", "AT");
				if (obj[0].toString()==null){
					query.setParameter("rucComprador", "");// parametro de identificador del comprador
					query.setParameter("rucComprador2", "");// parametro de identificador del comprador
				}else{
					query.setParameter("rucComprador", obj[0].toString());// parametro de identificador del comprador
					query.setParameter("rucComprador2", obj[0].toString());// parametro de identificador del comprador
				}			
				query.setParameter("DatoVacio", "");
				
				if (obj[1].toString()==null){
					query.setParameter("razonSocialComp","%"); // parametro de la razon social
				}else{
					query.setParameter("razonSocialComp","%"+obj[1].toString()+"%"); // parametro de la razon social
				}
				
				query.setParameter("codDocumentoSelecionado",obj[2].toString()); // parametro del documento seleccionado
				query.setParameter("fechaInicio", Date.valueOf(obj[3].toString()));
				query.setParameter("fechaFinal", Date.valueOf(obj[4].toString()));

				if (obj[5].toString()==null){
					query.setParameter("rucCliente", "");// parametro de identificador del cliente
				}else{
					query.setParameter("rucCliente", obj[5].toString());// parametro de identificador del cliente
				}
				if (obj[6].toString()==null){
					query.setParameter("rucEmpresa", "");// Ruc Empresa
				}else{
					query.setParameter("rucEmpresa", obj[6].toString());// Ruc Empresa
				}
			}
			System.out.println("Y el tamaño de la guia es... "+query.getResultList().size());
			
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> listadoEmpresas(String Ruc_empresa) throws Exception{
		try{
			String stringQuery = "select E from FacEmpresa E where E.ruc = :ruc_empresa and E.isActive = :Estado";
			Query query = em.createQuery(stringQuery);
			query.setParameter("ruc_empresa", Ruc_empresa);
			query.setParameter("Estado", "Y");
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> listadoEmpresasTodas() throws Exception{
		try{
			String stringQuery = "select E from FacEmpresa E where E.isActive = :Estado";
			Query query = em.createQuery(stringQuery);
			query.setParameter("Estado", "Y");
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	public FacEmpresa listadoEmpr(String Ruc_empresa) throws Exception{
		try{
			String stringQuery = "select E from FacEmpresa E where E.ruc = :ruc_empresa and E.isActive = :Estado";
			Query query = em.createQuery(stringQuery);
			query.setParameter("ruc_empresa", Ruc_empresa);
			query.setParameter("Estado", "Y");
			FacEmpresa e = (FacEmpresa) query.getSingleResult(); 
			return e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> listadoTodasEmpresas() throws Exception{
		try{
			String stringQuery = "select E from FacEmpresa E where E.isActive = :Estado";
			Query query = em.createQuery(stringQuery);
			query.setParameter("Estado", "Y");
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	public List<facDetalleDocumentoEntidad> MotrandoDocumentoFiltrados(List<FacCabDocumento> documento_detalles,
																	   List<FacTiposDocumento> tipoDocumentos) throws Exception{
		try{
			System.out.println("MotrandoDocumentoFiltrados...");
			
			List<facDetalleDocumentoEntidad> documentoEntidad = new ArrayList<facDetalleDocumentoEntidad>();
			facDetalleDocumentoEntidad detalleEntidad = new facDetalleDocumentoEntidad();
			
			if(documento_detalles.isEmpty()){
				return documentoEntidad;
			}
			for (FacCabDocumento detalle : documento_detalles) {
					for (FacTiposDocumento documento : tipoDocumentos) {
						if (detalle.getId().getCodigoDocumento().equalsIgnoreCase(documento.getIdDocumento())) {// validando si el codigo del documento existe
							//for (FacEmpresa empresa : detalleEmpresas) {
										//if (detalle.getId().getRuc().equalsIgnoreCase(empresa.getRuc())) {// validando si el codigo del documento existe
											String formato = Integer.parseInt(detalle.getId().getCodigoDocumento()) + detalle.getId().getRuc()+detalle.getId().getCodigoDocumento()+ detalle.getId().getCodEstablecimiento() + detalle.getId().getCodPuntEmision() + detalle.getId().getSecuencial();
											String Estado = detalle.getEstadoTransaccion().trim();
											detalleEntidad.setRFCREC(detalle.getIdentificacionComprador());
											detalleEntidad.setNOMREC(detalle.getRazonSocialComprador());
											detalleEntidad.setCodDoc(documento.getIdDocumento());
											detalleEntidad.setTIPODOC(documento.getDescripcion());
											detalleEntidad.setFOLFAC(detalle.getId().getCodEstablecimiento() + "-" + detalle.getId().getCodPuntEmision() + "-" + detalle.getId().getSecuencial());
											detalleEntidad.setTOTAL(detalle.getImporteTotal());
											if(detalle.getFechaEmision() != null)
												detalleEntidad.setFECHA(Date.valueOf(String.valueOf(detalle.getFechaEmision())));
											detalleEntidad.setEDOFAC((Estado.trim().equals("IN") ? "INICIAL" : Estado.trim().equals("GE") ? "GENERADO" : Estado.trim().equals("FI") ? "FIRMADO" : Estado.trim().equals("RE") ? "RECIBIDA" : 
											Estado.trim().equals("DE") ? "DEVUELTA" : Estado.trim().equals("AT") ? "AUTORIZADO" : Estado.trim().equals("NA") ? "NO AUTORIZADO" : Estado.trim().equals("CT") ? "CONTINGENCIA" : ""));
											detalleEntidad.setPDFARC(formato + ".pdf");
											detalleEntidad.setXMLARC(formato + ".xml");
											detalleEntidad.setEmail(detalle.getEmail());
											
											//detalleEntidad.setDireccion(empresa.getPathCompFirmados());
											detalleEntidad.setFormato(formato);
											//CPA
											detalleEntidad.setCodEstablecimiento(detalle.getId().getCodEstablecimiento());
											detalleEntidad.setCodigoDocumento(detalle.getId().getCodigoDocumento());
											detalleEntidad.setCodPuntoEmision(detalle.getId().getCodPuntEmision());
											detalleEntidad.setSecuencial(detalle.getId().getSecuencial());
											detalleEntidad.setDocuAutorizacion(detalle.getDocuAutorizacion());
											detalleEntidad.setAmbiente((detalle.getId().getAmbiente().intValue()==1?"Pruebas":"Produccion"));
											//
											//vpi se agrega validacion para traer ruc del documento
											detalleEntidad.setRucEmisor(detalle.getId().getRuc());
											documentoEntidad.add(detalleEntidad);
											detalleEntidad =  new facDetalleDocumentoEntidad();
											
											
									//		break;
									//	}
									}
							
						}
					//break;
					}
			//}
			
			return documentoEntidad;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<String> BuscarfitroEmpresaDocumentos(String parametro_ruc, String rucEmpresa) throws Exception{
		
		try{
			Query q = em.createQuery("SELECT distinct E.identificacionComprador FROM FacCabDocumento E where E.id.ruc = :rucEmpresa and E.identificacionComprador like :ruc_comprador ");
			q.setParameter("rucEmpresa", rucEmpresa);
			q.setParameter("ruc_comprador", "%" + parametro_ruc);
			q.setFirstResult(0);
			q.setMaxResults(10);
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	public FacCabDocumento buscarCabDocumentoPorPk(String ruc, String codEstablecimiento, String codPuntoEmision, String secuencial, String codigoDocumento, String ambiente) throws Exception
	{	
		try{
			Query q =  em.createQuery("SELECT E FROM FacCabDocumento E where E.id.ruc = :ruc and E.id.codEstablecimiento = :codEstablecimiento " +
					"and E.id.codPuntEmision = :codPuntoEmision and E.id.secuencial = :secuencial and E.id.codigoDocumento = :codigoDocumento and E.id.ambiente = :ambiente") ;
			q.setParameter("ruc", ruc);
			q.setParameter("codEstablecimiento", codEstablecimiento);
			q.setParameter("codPuntoEmision", codPuntoEmision);
			q.setParameter("secuencial", secuencial);
			q.setParameter("codigoDocumento", codigoDocumento);			
			q.setParameter("ambiente", (ambiente.equals("Pruebas")?1:2));			
			FacCabDocumento e = (FacCabDocumento)q.getSingleResult(); 					
			return e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	public List<FacDetDocumento> buscarDetDocumentoPorFk(String ruc, String codEstablecimiento, String codPuntoEmision, String secuencial, String codigoDocumento,String ambiente) throws Exception
	{
		try{
			System.out.println("ruc: " + ruc);
			System.out.println("codEstablecimiento: " + codEstablecimiento);
			System.out.println("codPuntoEmision: " + codPuntoEmision);
			System.out.println("secuencial: " + secuencial);
			System.out.println("codigoDocumento: " + codigoDocumento);
					
			List<FacDetDocumento> lstFactDetDocumento = new ArrayList<FacDetDocumento>();
			Query q =  em.createQuery("SELECT E FROM FacDetDocumento E where E.id.ruc = :param1 and E.id.codEstablecimiento = :param2 and E.id.codPuntEmision = :param3 and E.id.secuencial = :param4 and E.id.codigoDocumento = :param5 and E.id.ambiente = :param6") ;
			q.setParameter("param1", ruc);
			q.setParameter("param2", codEstablecimiento);
			q.setParameter("param3", codPuntoEmision);
			q.setParameter("param4", secuencial);
			q.setParameter("param5", codigoDocumento);					
			q.setParameter("param6", (ambiente.equals("Pruebas")?1:2));				
			lstFactDetDocumento = q.getResultList();
			return lstFactDetDocumento;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	// INI HFU
	public List<FacDetRetencione> buscarDetRetencionPorFk(String ruc, String codEstablecimiento, String codPuntoEmision, String secuencial, String codigoDocumento,String ambiente) throws Exception
	{
		try{
			List<FacDetRetencione> lstFactDetDocumento = new ArrayList<FacDetRetencione>();
			Query q =  em.createQuery("SELECT E FROM FacDetRetencione E where E.id.ruc = :param1 and E.id.codEstablecimiento = :param2 and E.id.codPuntEmision = :param3 and E.id.secuencial = :param4 and E.id.codigoDocumento = :param5 and E.id.ambiente = :param6") ;
			q.setParameter("param1", ruc);
			q.setParameter("param2", codEstablecimiento);
			q.setParameter("param3", codPuntoEmision);
			q.setParameter("param4", secuencial);
			q.setParameter("param5", codigoDocumento);
			q.setParameter("param6", (ambiente.equals("Pruebas")?1:2));
			lstFactDetDocumento = q.getResultList();
			return lstFactDetDocumento;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	// FIN HFU

	public List<FacDetAdicional> buscarDetDocumentoAdicPorFk(String ruc, String codEstablecimiento, String codPuntoEmision, String secuencial, String codigoDocumento, String ambiente) throws Exception
	{
		try{
			List<FacDetAdicional> lstFactDetDocumento = new ArrayList<FacDetAdicional>();
			Query q =  em.createQuery("SELECT E FROM FacDetAdicional E where E.id.ruc = :ruc and E.id.codEstablecimiento = :codEstablecimiento and E.id.codPuntEmision = :codPuntoEmision and E.id.secuencial = :secuencial and E.id.codigoDocumento = :codigoDocumento and E.id.ambiente = :ambiente") ;
			q.setParameter("ruc", ruc);
			q.setParameter("codEstablecimiento", codEstablecimiento);
			q.setParameter("codPuntoEmision", codPuntoEmision);
			q.setParameter("secuencial", secuencial);
			q.setParameter("codigoDocumento", codigoDocumento);			
			q.setParameter("ambiente", (ambiente.equals("Pruebas")?1:2));
			lstFactDetDocumento = q.getResultList();
			return lstFactDetDocumento;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	public FacProducto buscarProductoPorId(String codPrincipal) throws Exception
	{
		try{
			Query q =  em.createQuery("SELECT E FROM FacProducto E where E.codPrincipal = 4"); //:codPrincipal") ;
			//q.setParameter("codPrincipal", Integer.getInteger(codPrincipal));		
			FacProducto e = (FacProducto)q.getSingleResult(); 					
			return e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	public FacEstablecimiento buscarCodEstablecimiento(String ruc, String codEstablecimiento){
		FacEstablecimiento busqueda =null;
		try {
			Query q = em.createQuery("select ES from FacEstablecimiento ES where ES.id.ruc= :Ruc AND ES.id.codEstablecimiento = :codEstablecimiento");
			q.setParameter("Ruc", ruc);
			q.setParameter("codEstablecimiento", codEstablecimiento);
			busqueda = (FacEstablecimiento) q.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("tu error es ...: "+e);
			return null;
		}
		return busqueda;
	}

	public List<FacDestinatario> buscarDestinatarios(int ambiente, String Ruc, String codEst, String codPuntEm, String secuencial, String codDoc)
    {
		System.out.println("-- INCICIO ReporteServicio.buscarDatosDestinatarios --");
		List<FacDestinatario> destinatarios = null;
		String sql = "";
		
		System.out.println(" Ambiente:::"+ambiente);
		System.out.println(" Ruc:::"+Ruc);
		System.out.println(" codEst:::"+codEst);
		System.out.println(" codPuntEm:::"+codPuntEm);
		System.out.println(" secuencial:::"+secuencial);
		System.out.println(" codDoc:::"+codDoc);
		
		//sql = "SELECT * FROM fac_destinatario where ambiente=? and \"Ruc\" = ? AND \"codEstablecimiento\" = ? AND \"CodPuntEmision\" = ? AND secuencial = ? ";
		sql = "select D from FacDestinatario D  "
				+ "where D.id.ambiente 			 = :ambiente "
				+ "  and D.id.ruc 				 = :ruc "
				+ "  and D.id.codEstablecimiento = :establecimiento "
				+ "	 and D.id.codPuntEmision = :puntoEmision "
				+ "  and D.id.secuencial 	 = :secuencial ";
		try
		{
			Query query = em.createQuery(sql);
			query.setParameter("ambiente", ambiente);
			query.setParameter("ruc", Ruc);
			query.setParameter("establecimiento", codEst);
			query.setParameter("puntoEmision", codPuntEm);
			query.setParameter("secuencial", secuencial);
			
			destinatarios = query.getResultList();
			//destinatario.setListDetallesGuiaRemision(buscarDetallesDestinatarios(destinatario));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al buscar el registro");
		}
		
		System.out.println("-- FIN ReporteServicio.buscarDatosDestinatarios --");
		return destinatarios;
    }
	
	public List<FacDetGuiaRemision> buscarDetalleDestinatarios(int ambiente, String Ruc, String codEst, String codPuntEm, String secuencial, String identificacion)
    {
		System.out.println("-- INCICIO ReporteServicio.buscarDatosDestinatarios --");
		List<FacDetGuiaRemision> detallesDestinatarios = null;
		String sql = "";
		
		System.out.println(" Ambiente:::"+ambiente);
		System.out.println(" Ruc:::"+Ruc);
		System.out.println(" codEst:::"+codEst);
		System.out.println(" codPuntEm:::"+codPuntEm);
		System.out.println(" secuencial:::"+secuencial);
		System.out.println(" identificacion:::"+identificacion);
		
		//sql = "SELECT * FROM fac_destinatario where ambiente=? and \"Ruc\" = ? AND \"codEstablecimiento\" = ? AND \"CodPuntEmision\" = ? AND secuencial = ? ";
				
		sql = "select D from FacDetGuiaRemision D  "
				+ "where D.id.ambiente 			 = :ambiente "
				+ "  and D.id.ruc 				 = :ruc "
				+ "  and D.id.codEstablecimiento = :establecimiento "
				+ "	 and D.id.codPuntEmision = :puntoEmision "
				+ "  and D.id.secuencial 	 = :secuencial "
				+ "  and D.id.identificacionDestinatario = :identificacion";
		try
		{
			Query query = em.createQuery(sql);
			query.setParameter("ambiente", ambiente);
			query.setParameter("ruc", Ruc);
			query.setParameter("establecimiento", codEst);
			query.setParameter("puntoEmision", codPuntEm);
			query.setParameter("secuencial", secuencial);
			query.setParameter("identificacion", identificacion);
			
			detallesDestinatarios = query.getResultList();
			//destinatario.setListDetallesGuiaRemision(buscarDetallesDestinatarios(destinatario));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al buscar el registro");
		}
		
		System.out.println("-- FIN ReporteServicio.buscarDatosDestinatarios --");
		return detallesDestinatarios;
    }
}
