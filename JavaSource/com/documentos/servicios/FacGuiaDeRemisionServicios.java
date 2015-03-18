package com.documentos.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacCabDocumento;
import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacEmpresa;
import com.documentos.entidades.FacTransporte;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.general.entidades.FacGeneral;
import com.general.entidades.FacProducto;
import com.general.entidades.FacPuntoEmision;
import com.usuario.entidades.FacUsuariosEstablecimiento;

@Stateless
public class FacGuiaDeRemisionServicios {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	public boolean insertarCabDocumentos(FacCabDocumento cabAdicional){
		try {
				em.persist(cabAdicional);
			    return true;
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  		return false;
		}
	 }
	
	//TODO contructor que busca la dentificacion del cliente
	@SuppressWarnings("unchecked")
	public  List<String> BuscarfitroEmpresaDocumentos(String parametro_ruc, String ruc_emppresa, String TipoComprador , String Tipo) throws Exception{
		
		try{
			Query q = em.createQuery("SELECT E.id.rucCliente " +
					"	FROM FacCliente E " +
					"	where E.id.rucCliente like :ruc_comprador " +
					"	and E.id.ruc = :ruc_emppresa and E.tipoIdentificacion = :TipoComprador and E.tipoCliente  in("+ Tipo+")");
			q.setParameter("ruc_comprador", "%" + parametro_ruc);
			q.setParameter("ruc_emppresa", ruc_emppresa);
			q.setParameter("TipoComprador", TipoComprador);
			q.setFirstResult(0);
			q.setMaxResults(10);
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}
	
	//TODO contructor que busca la dentificacion del cliente
	@SuppressWarnings("unchecked")
	public  List<String> BuscarfitroEmpresaDocumentos2(String parametro_ruc, String ruc_emppresa , String Tipo) throws Exception{
		
		try{
			Query q = em.createQuery("SELECT E.id.rucCliente " +
					"	FROM FacCliente E " +
					"	where E.id.rucCliente like :ruc_comprador " +
					"	and E.id.ruc = :ruc_emppresa and E.tipoCliente  in("+ Tipo+")");
			q.setParameter("ruc_comprador", "%" + parametro_ruc);
			q.setParameter("ruc_emppresa", ruc_emppresa);
			q.setFirstResult(0);
			q.setMaxResults(10);
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	}

	public boolean insertarAdicionalCabDocumento(FacDetAdicional adicionalDoc){
		try {

		    	em.persist(adicionalDoc);
			    return true;
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  		return false;
		}
	 }
	
	//TODO contructor que se encarga de filtrar los productos
	public FacProducto buscarDatosProductofiltrando(int codigoProducto) throws Exception{
		
		try{
			Query q =  em.createQuery("SELECT P FROM FacProducto P where  P.codPrincipal =:codigo");
			q.setParameter("codigo", codigoProducto);

			return (FacProducto) q.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	public boolean insertarDetalleDocumento(FacDetDocumento detDocumento){
		try {
		    	em.persist(detDocumento);
			    return true;
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  		return false;
		}
	 }
	
	public FacEmpresa buscarDatosPorRuc(String Ruc) throws Exception {

		try {

			Query q = em.createQuery("SELECT E FROM FacEmpresa E where E.ruc= :ruc");
			q.setParameter("ruc", Ruc);

			FacEmpresa e = (FacEmpresa) q.getSingleResult();
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}

	public FacPuntoEmision buscarTipoDeAmbiente(String Ruc,String tipoDoc) throws Exception {
		FacPuntoEmision pEm = null;
		try {


			Query q = em.createQuery("SELECT P FROM FacPuntoEmision P where P.id.ruc= :ruc AND P.id.tipoDocumento = :tipoDocumentos");
			q.setParameter("ruc", Ruc);
			q.setParameter("tipoDocumentos", tipoDoc);

			pEm = (FacPuntoEmision) q.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		return pEm;
	}

	@SuppressWarnings("unchecked")
	public List<FacEstablecimiento> bucarEstsblecimiento(String ruc, String usuario) throws Exception{
		try {
			List<FacEstablecimiento> establecimientolista = new ArrayList<FacEstablecimiento>();
	    	List<FacEstablecimiento> establecimiento = new ArrayList<FacEstablecimiento>();
	    	List<FacUsuariosEstablecimiento> usuarioEstablecimiento = new ArrayList<FacUsuariosEstablecimiento>();
	    	 
			Query q =  em.createQuery("SELECT e FROM FacEstablecimiento e where e.id.ruc= :ruc and e.isActive = 'Y'") ;
                       q.setParameter("ruc", ruc);  
            establecimiento = q.getResultList();
            
            q = em.createQuery("SELECT e FROM FacUsuariosEstablecimiento e where e.id.ruc= :ruc and e.isActive = '1' and e.id.codUsuario = :usuario");
            q.setParameter("ruc", ruc); 
            q.setParameter("usuario", usuario);
            usuarioEstablecimiento = q.getResultList();
            
            for (FacEstablecimiento facestablecimiento : establecimiento) {
				for (FacUsuariosEstablecimiento facUsuariosEstablecimiento : usuarioEstablecimiento) {
					if(facUsuariosEstablecimiento.getId().getCodEstablecimiento().trim().equals(facestablecimiento.getId().getCodEstablecimiento().trim())){
						establecimientolista.add(facestablecimiento);
					}
				}
			}
            
			return establecimientolista;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacPuntoEmision> buscarPuntoEmision(String Ruc,String CodEstablecimiento,String tipoDoc){
		List<FacPuntoEmision> puntoEm = null;
		try {
			
			Query q = em.createQuery("SELECT P FROM FacPuntoEmision P WHERE P.id.ruc = :ruc AND P.id.codEstablecimiento = :codEstab AND P.id.tipoDocumento = :tipoDoc");
			q.setParameter("ruc", Ruc);
			q.setParameter("codEstab", CodEstablecimiento);
			q.setParameter("tipoDoc", tipoDoc);
			
			puntoEm = q.getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return puntoEm;
	}

	@SuppressWarnings("unchecked")
	public List<FacGeneral> buscarDatosPorCodigo(String codigo) throws Exception{
		Query q;
		try{
			
	
			q =  em.createQuery("SELECT E FROM FacGeneral E where codTabla= :codigo") ;
			q.setParameter("codigo",codigo);			
	    	 	     					

		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		return q.getResultList();		
	}
	
	public FacCliente buscarRazonSocial(String Ruc,String tipoIdentComp, String identComp,String tipoClie)throws Exception{
		FacCliente cliente = null ;
			try {
				Query q = em.createQuery("SELECT C FROM FacCliente C WHERE C.id.ruc = :ruc And C.tipoIdentificacion= :tipoIdent AND C.id.rucCliente= :rucCliente AND C.tipoCliente = :tipoCli");
				q.setParameter("ruc", Ruc);
				q.setParameter("tipoIdent", tipoIdentComp);
				q.setParameter("rucCliente", identComp);
				q.setParameter("tipoCli", tipoClie);
				
				cliente = new FacCliente();
				cliente = (FacCliente) q.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return cliente;
	}
	
	@SuppressWarnings("unchecked")
	public FacCliente buscarDatosDestinatario(String Ruc,String rucCliente){
		FacCliente cDest = null;
		
		String tipoClie = "";
		List<FacCliente> listaCl;
		try {
			tipoClie = "C";
			Query q = em.createQuery("Select C From FacCliente C Where  C.id.ruc = :ruc  And C.id.rucCliente= :rucCliente And C.tipoCliente = :tipoCliente");
			q.setParameter("ruc", Ruc);
			q.setParameter("rucCliente", rucCliente);
			
			q.setParameter("tipoCliente", tipoClie);
			listaCl = q.getResultList();
			if(listaCl.isEmpty()){
				Query qu = em.createQuery("Select C From FacCliente C Where  C.id.ruc = :ruc  And C.id.rucCliente= :rucCliente And C.tipoCliente = :tipoCliente");
				tipoClie = "P";
				qu.setParameter("ruc", Ruc);
				qu.setParameter("rucCliente", rucCliente);
				
				qu.setParameter("tipoCliente", tipoClie);
				cDest = (FacCliente) qu.getSingleResult();
			}else{
				cDest = (FacCliente) q.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cDest;
	}
	
	public int secuencial(String ruc,String codEstablecimiento,String puntoEmision, String codigoDocumento) throws Exception{
		Query q;
		java.lang.Object secu;
		int secuencia;
		try{	
			q= em.createQuery("SELECT max(c.id.secuencial)  FROM FacCabDocumento c where c.id.ruc = :rucEmpresa and c.id.codEstablecimiento = :establecimiento and" +
					" c.id.codPuntEmision = :puntoEmision and  c.id.codigoDocumento = :cod_documento ");
			q.setParameter("rucEmpresa", ruc);
			q.setParameter("establecimiento", codEstablecimiento);
			q.setParameter("puntoEmision", puntoEmision);
			q.setParameter("cod_documento", codigoDocumento);
			 secu =   q.getSingleResult();
			 secuencia = Integer.parseInt(String.valueOf((secu != null) ? secu : 7000));
			 secuencia = secuencia + 1 + 90;	 
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		return secuencia;
	}
	
public int secuencialAdicional() throws Exception{
		
		Query q;
		java.lang.Object secu;
		int secuencia;
		try{
			q= em.createQuery("SELECT count(*) FROM FacCabDocumentosAdicional ac");
			
			 secu =   q.getSingleResult();
			 secuencia = Integer.parseInt(String.valueOf(secu));
			 secuencia=secuencia+1;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
			
		}
	return secuencia;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacTransporte> buscarTransporte(String Ruc,String identificacionComprador)throws Exception{
		Query q = null;
		try {
			q = em.createQuery("Select T From FacTransporte T Where T.id.ruc= :Ruc And T.id.rucCliente = :rucCliente Order by T.id.ruc");
			q.setParameter("Ruc", Ruc);
			q.setParameter("rucCliente", identificacionComprador);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<FacProducto> buscarProductos()throws Exception{
		
		try {
			Query q = em.createQuery("Select P From FacProducto P");
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	
	}
	
	public FacProducto valores(){
		FacProducto producto=null;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return producto;
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
