package com.general.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacEstablecimiento;

@Stateless
public class FacEstablecimientoServicios {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	
	public  void insertEstablecimiento(FacEstablecimiento establecimiento) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			em.persist(establecimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarDatos(FacEstablecimiento establecimiento) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			em.merge(establecimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> bucarRuc(){
		List<FacEmpresa> empresa= null;
		try {
			//Query q = em.createQuery("select E from FacEmpresa E where E.isActive = 'Y' ORDER BY E.ruc");
			Query q = em.createQuery("select E from FacEmpresa E where E.isActive = 'Y' ");
			empresa = q.getResultList();
		} catch (Exception e) {
			System.out.println("tu erro es ..."+e);
		}
		
		return empresa;
	}
	
	@SuppressWarnings("unchecked")
	public List<FacEstablecimiento> buscardatosEstablecimiento(){
		List<FacEstablecimiento> establecimiento= null;
		try {
			//Query q = em.createQuery("select ES from FacEstablecimiento ES ORDER BY ES.id.codEstablecimiento");
			Query q = em.createQuery("select ES from FacEstablecimiento ES ");
			establecimiento = q.getResultList();
		} catch (Exception e) {
			System.out.println("tu erro es ..."+e);
		}
		return establecimiento;
	}
	
	public FacEstablecimiento buscarCodEstablecimiento(String ruc, String codEstablecimiento){
		FacEstablecimiento busqueda =null;
		try {
			Query q = em.createQuery("select ES from FacEstablecimiento ES where ES.id.ruc= :Ruc AND ES.id.codEstablecimiento= :codEstablecimiento");
			q.setParameter("Ruc", ruc);
			q.setParameter("codEstablecimiento", codEstablecimiento);
			busqueda = (FacEstablecimiento) q.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("tu erro es ...: "+e);
			return null;
		}
		return busqueda;
	}
	
}
