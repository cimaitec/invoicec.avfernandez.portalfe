package com.general.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.config.ConfigPersistenceUnit;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacGeneral;

@Stateless
public class FacGeneralServicio {
	@PersistenceContext(unitName = ConfigPersistenceUnit.persistenceUnit)
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public FacGeneral buscarDatosGeneralPrimerHijo(String padre){
		FacGeneral generalHijo= null;
		List<FacGeneral> generalHijos= null;
		try {
			Query q = em.createQuery("select E from FacGeneral E where E.codTabla = :padre ");
			q.setParameter("padre", padre);
			System.out.println("padre::"+padre);
			//generalHijo = (FacGeneral) q.getSingleResult();
			generalHijos = q.getResultList();
			generalHijo=generalHijos.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("tu erro es ..."+e);
			return null;
		}
		return generalHijo;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FacGeneral> buscarDatosGeneralHijo(String padre){
		List<FacGeneral> generalHijos= null;
		try {
			Query q = em.createQuery("select E from FacGeneral E where E.codTabla = :padre ");
			q.setParameter("padre", padre);
			System.out.println("padre::"+padre);
			generalHijos = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("tu error es ..."+e);
			return null;
		}
		return generalHijos;
	}
	
	public FacGeneral buscarDatosGeneralPadre(String padre){
		FacGeneral generalPadre =null;
		try {
			Query q = em.createQuery("select ES from FacGeneral ES where ES.idGeneral = :padre ");
			q.setParameter("padre", Integer.parseInt(padre));
			System.out.println("padre::"+padre);
			generalPadre = (FacGeneral) q.getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("tu error es ...: "+e);
			return null;
		}
		return generalPadre;
	}
	
	
	public HashMap<String, String> buscarDatosGeneralPadreHash(String codTabla){
		HashMap<String, String> MapGeneralJasper = null;
		List<FacGeneral> generalPadreHash= null;
		try {
			Query q = em.createQuery("select ES from FacGeneral ES where ES.codTabla = :codTabla ");
			q.setParameter("codTabla", codTabla);
			System.out.println("codTabla::"+codTabla);
			generalPadreHash = q.getResultList();
			if (generalPadreHash != null){			
				if (generalPadreHash.size()>0){
					MapGeneralJasper = new HashMap<String, String>();
					for (int i=0; i<generalPadreHash.size();i++){
						MapGeneralJasper.put(generalPadreHash.get(i).getCodUnico().trim(), generalPadreHash.get(i).getDescripcion());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("tu erro es ...: "+e);
			return null;
		}
		return MapGeneralJasper;
	}
	
}
