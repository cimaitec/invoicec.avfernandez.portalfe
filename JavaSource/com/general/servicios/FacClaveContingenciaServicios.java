package com.general.servicios;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacClavescontingencia;

@Stateless
public class FacClaveContingenciaServicios {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	// consulta de la empresa y verifica si esta activa
	public FacEmpresa verificarRuc(String ruc){
		FacEmpresa empresa=null;
		try {
			Query q = em.createQuery("SELECT E FROM FacEmpresa E WHERE E.ruc = :ruc");
			q.setParameter("ruc", ruc);
			empresa = (FacEmpresa) q.getSingleResult();
		} catch (Exception e) {
			empresa = null;
			e.printStackTrace();
		}
		
		return empresa;
	}
	
	public int lengthTabla(){
		Object secu;
		int secuencia=0;
		try {
			
			Query q = em.createQuery("select Max(idclavecontingencia) FROM FacClavescontingencia");
			secu = q.getSingleResult();
			secuencia = Integer.parseInt(String.valueOf(secu));
			secuencia = secuencia+1;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		
		return secuencia;
	}
	
	public void IngresarClaveContingencia(FacClavescontingencia claves){
		try {
			em.persist(claves);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
