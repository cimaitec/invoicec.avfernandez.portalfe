package com.usuario.servicios;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.usuario.entidades.FacRole;


@Stateless
public class FacRolesServicio {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	public void insert(FacRole roles) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
			em.persist(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarDatos(FacRole rol) throws Exception {
		try {
			try {
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//em.createQuery("update FacUsuario set password = : where id.codUsuario = :usuario and id.ruc = :ruc_empresa ");
			em.merge(rol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FacRole verificarCodRol(String codRol){
		FacRole rol=null;
		try {
			
			Query q = em.createQuery("SELECT R FROM FacRole R WHERE codRol = :codRoles");
			q.setParameter("codRoles", codRol);
			rol = (FacRole) q.getSingleResult();
			
		} catch (Exception e) {
			rol = null;
		}
		
		return rol;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacRole> buscarDatos(){
		List<FacRole> roles = null;
				try {
					Query q = em.createQuery("SELECT R FROM FacRole R");
					roles = q.getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
		return roles;
	}
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
