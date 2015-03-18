package com.usuario.servicios;



import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacCliente;
import com.general.entidades.FacEstablecimiento;
import com.usuario.entidades.FacUsuario;
import com.usuario.entidades.FacUsuariosEstablecimiento;



@Stateless
public class FacUsuarioEstablecimientoServicio {
	@PersistenceContext (unitName=ConfigPersistenceUnit.persistenceUnit)  
	private  EntityManager em;
	
	public  void insertarUsuarioEstablecimiento(FacUsuariosEstablecimiento usuarioEstablecimiento) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			em.persist(usuarioEstablecimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void ElminadoLogicoUsuarioEstablecimiento(FacUsuariosEstablecimiento usuarioEstablecimiento) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q =  em.createQuery("update FacUsuariosEstablecimiento set isActive = '0' " +
					"WHERE id.ruc = :ruc and id.codEstablecimiento = :establecimiento and id.codUsuario =:usuario") ;
            q.setParameter("ruc", usuarioEstablecimiento.getId().getRuc().trim());
            q.setParameter("usuario", usuarioEstablecimiento.getId().getCodUsuario().trim());
            q.setParameter("establecimiento", usuarioEstablecimiento.getId().getCodEstablecimiento().trim());
            q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void ModificarUsuarioEstablecimiento(FacUsuariosEstablecimiento usuarioEstablecimiento) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q =  em.createQuery("update FacUsuariosEstablecimiento set isActive = :estado , tipoAcceso = :acceso " +
					"WHERE id.ruc = :ruc and id.codEstablecimiento = :establecimiento and id.codUsuario =:usuario") ;
            q.setParameter("ruc", usuarioEstablecimiento.getId().getRuc().trim());
            q.setParameter("usuario", usuarioEstablecimiento.getId().getCodUsuario().trim());
            q.setParameter("establecimiento", usuarioEstablecimiento.getId().getCodEstablecimiento().trim());
            q.setParameter("estado", usuarioEstablecimiento.getIsActive());
            q.setParameter("acceso", usuarioEstablecimiento.getTipoAcceso());
            q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FacUsuario> buscarTodosDatosUsuario(String ruc)throws Exception{
	    List<FacUsuario> usuario = null;
		try{
			
	
			Query q =  em.createQuery("SELECT U FROM FacUsuario U WHERE U.id.ruc = :ruc") ;
            q.setParameter("ruc", ruc);
			     					
			usuario= q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacCliente> buscarTodosDatosUsuariocliente(String ruc)throws Exception{
	    List<FacCliente> usuario = null;
		try{
			
	
			Query q =  em.createQuery("SELECT U FROM FacCliente U WHERE U.id.ruc = :ruc") ;
            q.setParameter("ruc", ruc);
			     					
			usuario= q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> buscarDatosPorRuc() throws Exception {
		List<FacEmpresa> e = null;
		try {

			Query q = em.createQuery("SELECT E FROM FacEmpresa E where E.isActive = 'Y' ");
			
			e = q.getResultList();
						
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		
		return  e;
	}
	
	public FacUsuario buscarRucPorCodigo(String codusuario)throws Exception{
		FacUsuario usuario= null ;
		try {
			usuario= new FacUsuario();
			Query q = em.createQuery("SELECT U FROM FacUsuario U WHERE U.id.codUsuario = :codUsuario");
			q.setParameter("codUsuario",codusuario );
			usuario = (FacUsuario) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacUsuariosEstablecimiento> buscarTodosDatosUsarioEstablecimiento()throws Exception{
	    try{
			
	
			Query q =  em.createQuery("SELECT ue FROM FacUsuariosEstablecimiento ue ") ;
                          
			     					
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEstablecimiento> buscarTodosDatosEstablecimiento(String ruc)throws Exception{
	    try{
			
	
			Query q =  em.createQuery("SELECT e FROM FacEstablecimiento e where e.id.ruc = :Ruc ORDER BY e.id.codEstablecimiento") ;
            q.setParameter("Ruc", ruc);
			
            
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
	}
	
	public FacUsuariosEstablecimiento verificarDatosUE(String ruc,String codUsuario, String tipoEstablecimiento)throws Exception{
		FacUsuariosEstablecimiento facUE = null;
				try {
					Query q = em.createQuery("Select ue FacUsuariosEstablecimiento ue where ue.id.ruc=:ruc and ue.id.codUsuario = :codusuario and u.id.codEstablecimiento= :codEstablecimiento");
					q.setParameter("ruc", ruc);
					q.setParameter("codusuario", codUsuario);
					q.setParameter("codEstablecimiento", tipoEstablecimiento);
					facUE = (FacUsuariosEstablecimiento) q.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("no se encotro al Buscar el registro");
				}	
				
				return facUE;
	}
	
	public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
	