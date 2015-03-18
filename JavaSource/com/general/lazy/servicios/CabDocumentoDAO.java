package com.general.lazy.servicios;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.config.ConfigPersistenceUnit;
import com.general.lazy.connection.MyTransaction;
import com.general.lazy.entidades.FacCabDocumentoRepo;
import com.general.lazy.entidades.FacCabDocumentoRepoPK;
import com.general.lazy.wrappers.FacCabDocumento;
import com.general.lazy.wrappers.FacCabDocumentoPK;

public class CabDocumentoDAO implements Serializable {
	
	private EntityManager em;	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	private static final long serialVersionUID = 1L;
	private MyTransaction myTransaction;
	
	private HashMap<String, String> estados = null;
	public HashMap<String, String> tipoDocumento = null;
	public HashMap<Integer, String> ambientes = null;
	public CabDocumentoDAO(MyTransaction transaction) {
		this.myTransaction = transaction;
		
		estados = new HashMap<String, String>();
		estados.put("AT", "AUTORIZADO");
		
		
		tipoDocumento = new HashMap<String, String>();
		tipoDocumento.put("01", "FACTURA");
		tipoDocumento.put("04", "NOTA DE CREDITO");
		tipoDocumento.put("07", "COMPROBANTE DE RETENCION");
		tipoDocumento.put("FACTURA", "01");
		tipoDocumento.put("NOTA DE CREDITO", "04");
		tipoDocumento.put("COMPROBANTE DE RETENCION", "07");
		
		ambientes = new HashMap<Integer, String>();
		ambientes.put(1, "Pruebas");
		ambientes.put(2, "Produccion");
		
	}
	
	/**
	 * Find players in the DB
	 * 
	 * @param startingAt the first "row" db that the query will search
	 * @param maxPerPage the amount of records allowed per "trip" in the DB
	 * @return a players java.util.List
	 * @throws SystemException 
	 * @throws NotSupportedException 
	 * @throws HeuristicRollbackException 
	 * @throws HeuristicMixedException 
	 * @throws RollbackException 
	 * @throws IllegalStateException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings("unchecked")
	public List<FacCabDocumento> findDocumentos(int startingAt, int maxPerPage, HashMap<String, String> filters, HashMap<String, String> criterios) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		EntityManager em = myTransaction.getEntityManager();
		List resultQuery = null;
		try{		
			
			String filtroQuery = getFiltrosQuery(filters, criterios,0);
			
			// regular query that will search for players in the db
			Query query = em.createNativeQuery("Select distinct Ruc, CodEstablecimiento, CodPuntEmision, secuencial, CodigoDocumento, ambiente, autorizacion, claveAcceso, codDocModificado, "+ 
					" numDocSustento, ESTADO_TRANSACCION, fecEmisionDocSustento, fechaautorizacion,fechaEmision,fechaEmisionDocSustento, guiaRemision, "+ 
					" identificacionComprador,importeTotal,razonSocialComprador, tipIdentificacionComprador, tipoEmision, claveAccesoContigente,claveContingencia, "+
					" docuAutorizacion, fechaAutorizado "+
					" from  fac_cab_documentos " + filtroQuery +
					//" ORDER BY fechaEmision,codigoDocumento, CodEstablecimiento, CodPuntEmision, secuencial DESC ");
					" ORDER BY fechaEmision DESC,codigoDocumento, CodEstablecimiento, CodPuntEmision, secuencial DESC ");
			System.out.println("startingAt::"+startingAt);
			System.out.println("maxPerPage::"+maxPerPage);
			query.setFirstResult(startingAt);
			query.setMaxResults(maxPerPage);
			
			Iterator<String> keySetIterator = filters.keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				System.out.println("key::"+key+"::filtro::"+filters.get(key).trim());
				query.setParameter(key, filters.get(key).trim());
			}
			resultQuery = query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return getResultListDocumentos(resultQuery);
	}
	public String getFiltrosQuery(HashMap<String, String> filters, HashMap<String, String> criterios, int flag){
		String filtroQuery = "";
		String filtroQueryTmp = "";
		Iterator<String> keySetIterator = filters.keySet().iterator();
		int idx = 0;
		String criterio ="";
		String ls_iniFilters = "";
		if (flag == 1){
			ls_iniFilters=" and "; 
		}else{
			ls_iniFilters=" where ";
		}
		
		if ((filters!=null)&&(criterios!=null)){
		while(keySetIterator.hasNext()){
		  idx ++;
		  String key = keySetIterator.next();
		  if (criterios.containsKey(key)){
			  criterio = criterios.get(key);
		  }
		  if(criterio.equals("")){
			  criterio = "=";
		  }
		  if (idx==1){
			  
			  if (criterio.equals("like")){
				  filtroQueryTmp = ls_iniFilters + key.toString().trim() + " like '%'+:" + key.toString().trim()+"+'%'";
			  }else{
				  filtroQueryTmp = ls_iniFilters + key.toString().trim() + " "+criterio+" :" + key.toString().trim();
			  }
			  			  
		  }else{
			  if (criterio.equals("like")){
				  filtroQueryTmp = filtroQueryTmp + " and " + key.toString().trim() + "like '%'+:" + key.toString().trim()+"+'%'";
			  }else{
				  filtroQueryTmp = filtroQueryTmp + " and " + key.toString().trim() + " "+criterio+" :" + key.toString().trim();
			  }
		  }
		}
		filtroQuery = filtroQueryTmp;
		}
		return filtroQuery;
	}
	
	public List<FacCabDocumento> findDocumentosNative(int index, int maxPerPage, HashMap<String, String> filters, HashMap<String, String> criterios) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		EntityManager em = myTransaction.getEntityManager();
		List resultQuery = null;
		try{
		
			HashMap<String, String> estados = new HashMap<String, String>();
			estados.put("AT", "AUTORIZADO");

			
			Iterator<String> keySetIterator = filters.keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				System.out.println(key+"-"+filters.get(key).toString().trim());
			}
			String filtroQuery = getFiltrosQuery(filters, criterios,0);
			// regular query that will search for players in the db
			Query query = em.createNativeQuery("Select Ruc, CodEstablecimiento, CodPuntEmision, secuencial, CodigoDocumento, " +
											   "  ambiente, autorizacion, claveAcceso, codDocModificado, numDocSustento, " +
											   "  ESTADO_TRANSACCION, fecEmisionDocSustento, fechaautorizacion, fechaEmision, "+
											   "  fechaEmisionDocSustento, guiaRemision, identificacionComprador, importeTotal, "+
											   "  razonSocialComprador, tipIdentificacionComprador, tipoEmision, claveAccesoContigente, "+
											   "  claveContingencia, docuAutorizacion, fechaAutorizado FROM "+
											   " (SELECT ROW_NUMBER() OVER( ORDER BY datos.fechaEmision DESC,datos.codigoDocumento,datos.CodEstablecimiento,datos.CodPuntEmision,datos.secuencial DESC) AS Row, " +
											   "  Ruc, CodEstablecimiento, CodPuntEmision, secuencial, CodigoDocumento, " +
											   "  ambiente, autorizacion, claveAcceso, codDocModificado, numDocSustento, " +
											   "  ESTADO_TRANSACCION, fecEmisionDocSustento, fechaautorizacion, fechaEmision, "+
											   "  fechaEmisionDocSustento, guiaRemision, identificacionComprador, importeTotal, "+
											   "  razonSocialComprador, tipIdentificacionComprador, tipoEmision, claveAccesoContigente, "+
											   "  claveContingencia, docuAutorizacion, fechaAutorizado "+
													 " FROM "+
													 " (Select distinct Ruc, CodEstablecimiento, CodPuntEmision, secuencial, CodigoDocumento, " +
											   "  ambiente, autorizacion, claveAcceso, codDocModificado, numDocSustento, " +
											   "  ESTADO_TRANSACCION, fecEmisionDocSustento, fechaautorizacion, fechaEmision, "+
											   "  fechaEmisionDocSustento, guiaRemision, identificacionComprador, importeTotal, "+
											   "  razonSocialComprador, tipIdentificacionComprador, tipoEmision, claveAccesoContigente, "+
											   "  claveContingencia, docuAutorizacion, fechaAutorizado from fac_cab_documentos "+filtroQuery+" ) as datos) as dat" +
													 " where dat.Row between  :indexIni and :indexFin ");
			int indexIni = ((index-1)*maxPerPage)+1;
			int indexFin = ((index)*maxPerPage);
			System.out.println("index::"+index);
			System.out.println("maxPerPage::"+maxPerPage);
			System.out.println("indexIni::"+indexIni);
			System.out.println("indexFin::"+indexFin);
			
			query.setParameter("indexIni", indexIni);
			query.setParameter("indexFin", indexFin);		
			
			keySetIterator = filters.keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				System.out.println("key::"+key.toString());
				System.out.println("value::"+filters.get(key).toString().trim());
				query.setParameter(key, filters.get(key).toString().trim());
			}
			resultQuery = query.getResultList();
			
		
		}catch(Exception e){			
			e.printStackTrace();
		}
		return getResultListDocumentos(resultQuery);
	}
	
	/**
	 * Sum the number of players in the DB
	 * 
	 * @return an int with the total
	 * @throws SystemException 
	 * @throws HeuristicRollbackException 
	 * @throws HeuristicMixedException 
	 * @throws RollbackException 
	 * @throws IllegalStateException 
	 * @throws SecurityException 
	 */
	public int countDocumentosTotal(HashMap<String, String> filters,HashMap<String, String> criterios) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		//EntityManager em = myTransaction.getEntityManager();
		//Query query = em.createQuery("select COUNT(p) from FacCabDocumento p");
		//Query query = em.createQuery("select COUNT(p.id.ruc) from FacCabDocumentoRepo p");
		EntityManager em = myTransaction.getEntityManager();
		Integer result = 0;
		try{			
			/*String filtroQuery = "";
			String filtroQueryTmp = "";
			Iterator<String> keySetIterator = filters.keySet().iterator();
			int idx = 0;
			while(keySetIterator.hasNext()){
			  idx ++;
			  String key = keySetIterator.next();
			  if (idx==1){
				  filtroQueryTmp = " where " + key + " = :" + key.trim();			  
			  }else{
				  filtroQueryTmp = filtroQueryTmp + " and " + key + " = :" + key.trim();
			  }
			}
			filtroQuery = filtroQueryTmp;
			*/
			String filtroQuery = getFiltrosQuery(filters, criterios,0);
			
			Query query = em.createNativeQuery("select COUNT(1) from fac_cab_documentos "+ filtroQuery );
			
			Iterator<String> keySetIterator = filters.keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				query.setParameter(key, filters.get(key).trim());
			}
			
			String ls_result = query.getSingleResult().toString();		
			result = Integer.parseInt(ls_result);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	public List<FacCabDocumento> getResultListDocumentos(List tmpQuery){		
		if (tmpQuery.size()>0){		
			List<FacCabDocumento> listDocumentos = null;
			List<FacCabDocumento> listmp = null;
			FacCabDocumento temp = null;
			FacCabDocumentoPK tempPk = null;
			
			//VPI format 
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	         otherSymbols.setDecimalSeparator('.');
	         otherSymbols.setGroupingSeparator(','); 
			DecimalFormat nf = new DecimalFormat("###,##0.00",otherSymbols);
			
			listDocumentos = new ArrayList<FacCabDocumento>();
			for (int i = 0; i<tmpQuery.size();i++){
			//for ( FacCabDocumento cab : listmp){
				temp = new FacCabDocumento();
				Object [] tmpObj = (Object[]) tmpQuery.get(i);
				tempPk = new FacCabDocumentoPK();
				tempPk.setRuc((String) tmpObj[0]);
				tempPk.setCodEstablecimiento((String) tmpObj[1]);
				tempPk.setCodPuntEmision((String) tmpObj[2]);
				tempPk.setSerie((String) tmpObj[1]+"-"+(String) tmpObj[2]+"-"+(String) tmpObj[3]);
				tempPk.setSecuencial((String) tmpObj[3]);
				tempPk.setCodigoDocumento(tipoDocumento.get((String) tmpObj[4].toString().trim()));
				tempPk.setAmbiente(ambientes.get((Integer) tmpObj[5]));
				temp.setId(tempPk);
				temp.setAutorizacion((String) tmpObj[6]);
				temp.setClaveAcceso((String) tmpObj[7]);
				temp.setCodDocModificado((String) tmpObj[8]);
				temp.setCodDocSustento((String) tmpObj[9]);
				String estadoTransaccion = estados.get(tmpObj[10].toString().trim());
				temp.setEstadoTransaccion(estadoTransaccion);
				temp.setFecEmisionDocSustento((Date) tmpObj[11]);
				temp.setFechaautorizacion((Date) tmpObj[12]);
				temp.setFechaEmision(df.format((Date) tmpObj[13]));
				temp.setFechaEmisionDocSustento((String) tmpObj[14]);
				temp.setGuiaRemision((String) tmpObj[15]);
				temp.setIdentificacionComprador((String) tmpObj[16]);
				temp.setImporteTotal(nf.format((Double) tmpObj[17]));
				temp.setRazonSocialComprador((String) tmpObj[18]);
				temp.setTipIdentificacionComprador((String) tmpObj[19]);
				temp.setTipoEmision((String) tmpObj[20]);
				temp.setClaveAccesoContigente((String) tmpObj[21]);
				temp.setClaveContingencia((String) tmpObj[22]);
				temp.setDocuAutorizacion((String) tmpObj[23]);
				temp.setFechaAutorizado((String) tmpObj[24]);
				listDocumentos.add(temp);			
			}
			return listDocumentos;
		}else{
			return null;
		}
	}
}