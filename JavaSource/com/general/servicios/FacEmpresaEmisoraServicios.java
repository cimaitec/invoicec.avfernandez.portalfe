package com.general.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;



@Stateless
public class FacEmpresaEmisoraServicios {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;
	
	public void insertarEmpresa(FacEmpresa empresa) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
			em.persist(empresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarDatos(FacEmpresa empresa) throws Exception {
		try {
			try {
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//em.createQuery("update FacUsuario set password = : where id.codUsuario = :usuario and id.ruc = :ruc_empresa ");
			em.merge(empresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void EliminarDatosLogico(FacEmpresa empresa) throws Exception {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Query q = em.createQuery("update FacEmpresa set isActive = :Estado where ruc = :rucempresa ");
			q.setParameter("rucempresa", empresa.getRuc());
			q.setParameter("Estado", "0");
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	@SuppressWarnings("unchecked")
	public List<FacEmpresa> BuscarDatosEmpresa()throws Exception {
		List<FacEmpresa> facEmp=null;
		try {
			Query q = em.createQuery("SELECT E FROM FacEmpresa E ORDER BY E.ruc");
			facEmp=q.getResultList();
		} catch (Exception e) {
			facEmp = null;
			e.printStackTrace();
		}
		return facEmp;
	}
	
}

