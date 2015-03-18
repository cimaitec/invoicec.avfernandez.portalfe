package com.menu.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.menu.entidades.FacOpcionMenu;
import com.menu.entidades.FacRolesOpcionMenu;
import com.usuario.entidades.FacUsuariosRole;

@Stateless
public class FacOpcionMenuServicios {
	@PersistenceContext (unitName=ConfigPersistenceUnit.persistenceUnit)  
	private  EntityManager em;

	public  boolean verificaSubMenu(FacOpcionMenu menu) {
      
	 int t = 0;
	 try {
		 String sql = "select count(*) from FacOpcionMenu where codOpcionMenuPadre = :cod_hijo";
		 Query q = em.createQuery(sql);
		 q.setParameter("cod_hijo", menu.getCodOpcionMenu());
		 java.lang.Object obj = q.getSingleResult();
		 t = Integer.parseInt(String.valueOf((obj != null) ? obj : 0));
 	 } catch (Exception ex) {
 	 }
	 return (t > 0);
	 }

	 @SuppressWarnings("unchecked")
	public  List<FacOpcionMenu> buscaPorMenu(FacOpcionMenu menu) {
	 List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
	 String sql = "select E from FacOpcionMenu E where E.codOpcionMenuPadre = :cod_padre ORDER BY E.codOpcionMenu";
	 try {
		 Query q = em.createQuery(sql);
		 q.setParameter("cod_padre", menu.getCodOpcionMenu());
		 listaMenu = q.getResultList();
		 
	 } catch (Exception ex) {
	 }
	 return listaMenu;
	 }

	 public void prueba(){
		
	 }
	 
	 @SuppressWarnings("unchecked")
	public  List<FacOpcionMenu> listaTodos(List<FacRolesOpcionMenu> rolesxOpcion)throws Exception{
	 try{
		 List<FacOpcionMenu> listaMenu = new ArrayList<FacOpcionMenu>();
		 List<FacOpcionMenu> OpcionMenu = new ArrayList<FacOpcionMenu>();
			Query q = em.createQuery("select E from FacOpcionMenu E where E.codOpcionMenuPadre = :menupadre ORDER BY E.codOpcionMenu");
			q.setParameter("menupadre", "00");
			OpcionMenu =  q.getResultList();
			if(!OpcionMenu.isEmpty())
				for (FacOpcionMenu listaMenu1 :OpcionMenu) // recorriendo el listado de las opciones padre
					for(FacRolesOpcionMenu roles : rolesxOpcion)// recorriendo el rolxopcion
						if(listaMenu1.getCodOpcionMenu().trim().equals(roles.getId().getCodOpcionMenu().trim())){// si se encuentra ingresar detalle
							listaMenu.add(listaMenu1);
						 break;
					 }
				
			
			return listaMenu;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar registro");
		}
	 }
	 
	 @SuppressWarnings("unchecked")
	public List<FacUsuariosRole> buscarRolxUsuario(String loginUsuario , String rucEmpresa)throws Exception{
		 try{
			 
				Query q = em.createQuery("select E from FacUsuariosRole E where E.id.codUsuario = :loginUsuario and E.id.ruc = :rucEmpresa");
				q.setParameter("loginUsuario", loginUsuario);
				q.setParameter("rucEmpresa", rucEmpresa);
				return q.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al buscar registro");
			}
	 }
	 
	@SuppressWarnings("unchecked")
	public List<FacRolesOpcionMenu> buscarrolxOpcion(List<FacUsuariosRole> usuarioRol)throws Exception{
		 try{
			 Query q = em.createQuery("select E from FacRolesOpcionMenu E where E.id.codRol = :rol_usuario");
			 q.setParameter("rol_usuario", usuarioRol.get(0).getId().getCodRol());
			 return q.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al buscar registro");
			}
	 }
	  
	
	 @SuppressWarnings("unchecked")
	public  List<FacOpcionMenu> listatodos_menu() throws Exception{
		 try{
			 List<FacOpcionMenu> OpcionMenu = new ArrayList<FacOpcionMenu>();
				Query q = em.createQuery("select E from FacOpcionMenu E where E.codOpcionMenuPadre = :menupadre");
				q.setParameter("menupadre", "00");
				OpcionMenu =  q.getResultList();
				return OpcionMenu;
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al buscar registro");
			}
	 }
	 
	 //TODO contructor que es llamado de la pagina de mantenimiento de rol - opciones
	 @SuppressWarnings("unchecked")
	public List<FacRolesOpcionMenu> buscarrolxopcionMantenimiento(String usuarioRol)throws Exception{
		 try{
			 Query q = em.createQuery("select E from FacRolesOpcionMenu E where E.id.codRol = :rol_usuario");
			 q.setParameter("rol_usuario", usuarioRol);
			 return q.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al buscar registro");
			}
	 }
	 
	 //TODO
	 public List<String> filtrarRolesPadres(String usuarioRol)throws Exception{
		 try{			 
			 Query q = em.createQuery(" select A.id.codOpcionMenu from FacRolesOpcionMenu A " +
					 	"where A.id.codRol = :rol_usuario ");
			 q.setParameter("rol_usuario", usuarioRol);
			 List<String> listaOpcionMenu = q.getResultList();
			 String arregloOpcionMenu = "";
			 for(String rol_opcion : listaOpcionMenu)
				 arregloOpcionMenu += "'" + rol_opcion.toString().trim() + "',";
			 
			 arregloOpcionMenu = arregloOpcionMenu.substring(0, arregloOpcionMenu.length() - 1);
			 
			 Query q2 = em.createQuery(" select distinct A.codOpcionMenuPadre from FacOpcionMenu A " +
					 	"where A.codOpcionMenu not in (" + arregloOpcionMenu + ") and A.codOpcionMenuPadre <> '00' ");
			 listaOpcionMenu = q2.getResultList();
			 
			 return listaOpcionMenu;
		 }catch(Exception e){
			 e.printStackTrace();
			 throw new Exception("Error al buscar registro");
		 }
	 }
	 
	 
	 //TODO contructo que me busca toda las opciones de menu
	 @SuppressWarnings("unchecked")
	public  List<FacOpcionMenu> listatodos_opcionesMenu() throws Exception{
		 try{
			 List<FacOpcionMenu> OpcionMenu = new ArrayList<FacOpcionMenu>();
				Query q = em.createQuery("select E from FacOpcionMenu E");
				OpcionMenu =  q.getResultList();
				return OpcionMenu;
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al buscar registro");
			}
	 }
	 
	 public int EliminarRolOpcionesMenu(FacRolesOpcionMenu rolesOpciones){
			try {
				try {
			    		Thread.sleep(100);
		     		} catch (InterruptedException e) {
					e.printStackTrace();
		    		}
					Query q = em.createQuery("DELETE FROM FacRolesOpcionMenu p WHERE p.id.codRol = :rol and p.id.codOpcionMenu = :opcion");
					q.setParameter("rol",rolesOpciones.getId().getCodRol());
					q.setParameter("opcion",rolesOpciones.getId().getCodOpcionMenu());
					q.executeUpdate();
				    return 0;
		  	} catch (Exception e) {
		  		e.printStackTrace();
		  		return -1;
			}
		}
		
		public int insertarRolOpcionesMenu(FacRolesOpcionMenu rolesOpciones){
			try {
				try {
			    		Thread.sleep(100);
		     		} catch (InterruptedException e) {
					e.printStackTrace();
		    		}
			    	em.persist(rolesOpciones);
				    return 0;
		  	} catch (Exception e) {
		  		e.printStackTrace();
		  		return -1;
			}
		 }
	 
}

