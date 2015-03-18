package com.usuario.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;
import com.general.controladores.FacEncriptarcadenasControlador;
import com.general.entidades.FacCliente;
import com.usuario.entidades.FacLoginBitacora;
import com.usuario.entidades.FacRole;
import com.usuario.entidades.FacUsuario;
import com.usuario.entidades.FacUsuariosRole;

@Stateless
public class FacUsuarioServicio {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	private List<FacUsuario> facUsuario;

	public void insertarUsuario(FacUsuario usuario) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			em.persist(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertarUsuarioRol(FacUsuariosRole usuarioRol) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			em.persist(usuarioRol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarDatos(FacUsuario usuario) throws Exception {
		try {
			try {
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q = em.createQuery("update FacUsuario set " +
					" password = :contrasena	," +
					" isActive = :estado " +
					" where id.codUsuario = :usuario and " +
					"		rucEmpresa = :ruc_usuario and" +
					"		id.ruc = :ruc_empresa  " +
					"		");
			q.setParameter("estado", usuario.getIsActive());
			q.setParameter("contrasena", usuario.getPassword());
			
			q.setParameter("usuario", usuario.getId().getCodUsuario());
			q.setParameter("ruc_usuario", usuario.getRucEmpresa());
			q.setParameter("ruc_empresa", usuario.getId().getRuc());
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarDatosRol(FacUsuariosRole usuarioRol) throws Exception {
		try {
			try {
				Thread.sleep(1000);
					} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q = em.createQuery("update FacUsuariosRole set id.codRol= :codRol , isActive = :estado where id.codUsuario = :usuario and id.ruc = :ruc_empresa");
			q.setParameter("estado", usuarioRol.getIsActive());
			q.setParameter("codRol", usuarioRol.getId().getCodRol());
			q.setParameter("usuario", usuarioRol.getId().getCodUsuario());
			q.setParameter("ruc_empresa", usuarioRol.getId().getRuc());
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FacCliente buscarRucCliente(String Ruc) throws Exception {
		FacCliente c = null;
		try {


			Query q = em.createQuery("SELECT C FROM FacCliente C where C.id.rucCliente = :ruc");
			q.setParameter("ruc", Ruc.trim());
		
			
			c = (FacCliente) q.getSingleResult();
			return  c;
		} catch (Exception ex) {
			
			ex.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}

	public FacEmpresa buscarDatosPorRuc(String Ruc) throws Exception {
		FacEmpresa e = null;
		try {

			Query q = em.createQuery("SELECT E FROM FacEmpresa E where E.ruc= :ruc");
			q.setParameter("ruc", Ruc);
			
		
			e = (FacEmpresa) q.getSingleResult();
			
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		
		return  e;
	}

	@SuppressWarnings("unchecked")
	public List<FacUsuario> buscarTodosLosUsuario() throws Exception {
		try {
			Query q = em.createQuery("SELECT U FROM FacUsuario U ORDER BY U.id.codUsuario");
			

			return q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}

	@SuppressWarnings("unchecked")
	public FacUsuario BuscarCodUsuario(String rucUsua,String rucEmp,String tipoUsuario) throws Exception {
		FacUsuario u = new FacUsuario();
		try {
			Query q = em.createQuery("SELECT U FROM FacUsuario U where U.rucEmpresa = :rucUsuario AND U.id.ruc = :rucEmp AND U.tipoUsuario = :tipoUsua");
			q.setParameter("rucUsuario", rucUsua);
			q.setParameter("rucEmp", rucEmp);
			q.setParameter("tipoUsua", tipoUsuario.trim());
			
			List<FacUsuario> facusuario = q.getResultList();
			if(!facusuario.isEmpty()){
				u= (FacUsuario) q.getSingleResult();
				
			}else{
				u= null;
			}
		} catch (Exception e) {
			u=null;
			e.printStackTrace();
			throw new Exception("Error al Buscar");
		}
		
		return u;
		
	}
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> buscarEmpresa() throws Exception {
		Query q = null;
		try {
			q = em.createQuery("SELECT E FROM FacEmpresa E where E.isActive = 'Y'");
						
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
		
		return  q.getResultList();
	}
	
	public FacUsuariosRole buscarCodRol(String ruc,String codUsuario) throws Exception{
		FacUsuariosRole usuRol = null;
				try {
					Query q = em.createQuery("SELECT UR FROM FacUsuariosRole UR WHERE UR.id.ruc = :ruc AND UR.id.codUsuario = :codUsuario");
					q.setParameter("ruc", ruc);
					q.setParameter("codUsuario", codUsuario);
					usuRol = new FacUsuariosRole();
					usuRol = (FacUsuariosRole) q.getSingleResult();
				} catch (Exception e) {
					usuRol = null;
					return usuRol;
				}
		return usuRol;
	}

	public FacEmpresa ver_tipo(String tipo) throws Exception {
		try {
			Query q = em.createQuery("SELECT E FROM FacEmpresa E ");

			FacEmpresa e = (FacEmpresa) q.getSingleResult();

			return e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}
	}

	@SuppressWarnings("unchecked")
	public List<FacUsuario> buscarTodosDatosUsuario()throws Exception{
	    try{
			
	
			Query q =  em.createQuery("SELECT U FROM FacUsuario U ORDER BY U.id.codUsuario ") ;
                          
			     					
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
	}

	@SuppressWarnings("unchecked")
	public List<FacRole> buscarTodosRol()throws Exception{
	    try{
			
	
			Query q =  em.createQuery("SELECT R FROM FacRole R ORDER BY R.id.codUsuario") ;
                          
			     					
			return q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al Buscar el registro");
		}	
	    
	}
	public int generaSecuencial() {
		Query q;
		q = em.createQuery("SELECT count(*)  FROM FacUsuario");
		java.lang.Object valor = q.getSingleResult();
		int e = Integer.parseInt(valor.toString());
		e = e + 1;
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacUsuario> validarUsuario(FacUsuario usuario){
		try{
		
		Query q = em.createQuery("SELECT E  FROM FacUsuario E where isActive in ('Y','1') and id.codUsuario = :usuario order by autenticado desc");
		q.setParameter("usuario", usuario.getId().getCodUsuario());
		List<FacUsuario> facConsultausuario = q.getResultList();
		/*
		for (FacUsuario facUsuario : facConsultausuario) {
				return facUsuario;
		}*/
		return facConsultausuario;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String validarUsuarioMensaje(FacUsuario usuario){
		try{
			
			System.out.println("Funcion donde valida si usuario...");
			Query q = em.createQuery("SELECT E  FROM FacUsuario E where isActive in ('Y','1') and id.codUsuario = :usuario ");
			System.out.println("Voy a llenar parametros...");
			//q.setParameter("Estado", "Y");
			q.setParameter("usuario", usuario.getId().getCodUsuario());

			System.out.println("Funcion donde valida si usuario...");
			
			List<FacUsuario> facConsultausuario = q.getResultList();
			System.out.println("Llenó lista...");
			if (facConsultausuario.isEmpty())
				return "usuario no existe o usuario inactivo ";
			
			for (FacUsuario facUsuario : facConsultausuario) {
				if(FacEncriptarcadenasControlador.decrypt(facUsuario.getPassword()).trim().equals(FacEncriptarcadenasControlador.decrypt(usuario.getPassword()).trim()))
					return "ok";
				else{
					return "contraseña incorrecta";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<FacLoginBitacora> listaLoginBitacora(String p_usuario)
	{
		Query q = null;
		List<FacLoginBitacora> listaLoginBitacora = null;
		try
		{
			/*System.out.println("Antes select");
			System.out.println("Ruc "+p_ruc);
			System.out.println("Usuario "+p_usuario);*/
			q = em.createQuery("select U from FacLoginBitacora U where  U.usuario = :usuario");
			q.setParameter("usuario", p_usuario);
			listaLoginBitacora = q.getResultList();
			//System.out.println("Luego de ejecucion");
			return listaLoginBitacora;
		}
		catch(Exception e)
		{
			System.out.println("Error FacUsuarioServicio.listaLoginBitacora...");
			System.out.println(e);
			return listaLoginBitacora;
		}
	}
	
	
	public int validarLoginErroneos(FacUsuario usuario){
		int li_error=0;
		try{
			
			System.out.println("Funcion donde valida si usuario...");
			Query q = em.createQuery("SELECT E  FROM FacUsuario E where E.isActive in ('Y','1') and E.id.codUsuario = :usuario ");
			System.out.println("Voy a llenar parametros...");
			//q.setParameter("Estado", "Y");
			System.out.println("Voy a llenar parametros -1-...");
			System.out.println(usuario.getId().getCodUsuario());
			q.setParameter("usuario", usuario.getId().getCodUsuario());	
			System.out.println("Funcion donde valida si usuario...");			
			FacUsuario facusuario = (FacUsuario) q.getSingleResult();
			System.out.println("Llenó lista...");
			if (facusuario==null)
				li_error= 0;
			else
				li_error= facusuario.getLoginErroneo();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return li_error;
		
	}
	@SuppressWarnings("unchecked")
	public List<FacCliente> buscarClientes(String Ruc,String tipoUsuario){
		Query q =  null;
		try {
			q = em.createQuery("Select C from FacCliente C Where C.id.ruc = :ruc And C.tipoCliente = :tipoUsuario");
			q.setParameter("ruc", Ruc);
			q.setParameter("tipoUsuario", tipoUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q.getResultList();
	}

	

	
	public void modificarLoginError(FacUsuario usuario, int numLoginError) throws Exception {
		try {
			try {
				Thread.sleep(1000);
					} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q = em.createQuery("update FacUsuario set loginErroneo = :loginErroneo where  id.codUsuario = :usuario");
			q.setParameter("loginErroneo", numLoginError);
			q.setParameter("usuario", usuario.getId().getCodUsuario());
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int geSecuencialLoginBitacora(){
		int secuencial = 1;
		List<FacLoginBitacora> listaLoginBitacora = null;
		try {
			Query q = null;
			q = em.createQuery("select F from FacLoginBitacora F where F.id = (select max(G.id) from FacLoginBitacora G)");
			listaLoginBitacora =  q.getResultList();
			System.out.println("SecuencialLoginBitacoraMax::"+listaLoginBitacora.get(0).getId());
			Integer res = listaLoginBitacora.get(0).getId();
			if (res==null){
				secuencial = 1;
			}
			else{
				secuencial = res.intValue();
			}			
				++secuencial;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secuencial;
	}
	
	
	public void ingresaLoginBitacora(FacLoginBitacora facLoginBitacora) throws Exception {
		try {
			em.persist(facLoginBitacora);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cambiarContrasena(FacUsuario usuario)
	{
		try
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("Ruc... " + usuario.getId().getRuc());
			//System.out.println("Ruc Cliente... " + usuario.getId().getRucCliente());
			//System.out.println("Cod cliente... " + usuario.getCodCliente());
			Query q = em.createQuery("update FacUsuario set autenticado = :autenticado, password = :nuevaContrasena where id.codUsuario = :codUsuario ");// and id.codUsuario = :codigo");
			//String nuevaContrasena = FacEncriptarcadenasControlador.encrypt(p_nuevaContrasena);
			//q.setParameter("nuevaContrasena", nuevaContrasena);
			q.setParameter("nuevaContrasena", usuario.getPassword());
			q.setParameter("autenticado", "S");
			//q.setParameter("rucempresa", usuario.getId().getRuc());
			q.setParameter("codUsuario", usuario.getId().getCodUsuario());
			//q.setParameter("codigo", usuario.getId().getCodUsuario());
			q.executeUpdate();
			System.out.println("Ejecutó...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public List<FacUsuario> getFacUsuario() {
		return facUsuario;
	}

	public void setFacUsuario(List<FacUsuario> facUsuario) {
		this.facUsuario = facUsuario;
	}

}
