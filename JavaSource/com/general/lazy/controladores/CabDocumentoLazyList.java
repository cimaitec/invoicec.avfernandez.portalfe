package com.general.lazy.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.general.lazy.connection.MyTransaction;
import com.general.lazy.servicios.CabDocumentoDAO;
import com.general.lazy.wrappers.FacCabDocumento;



public class CabDocumentoLazyList extends LazyDataModel<FacCabDocumento> {

	private static final long serialVersionUID = 1L;

	private List<FacCabDocumento> documentos;
	private List<FacCabDocumento> documentosSelec;

	private MyTransaction transaction;
	
	private CabDocumentoDAO documentoDAO;
	
	private HashMap<String, String> filtros;
	private HashMap<String, String> criterios;
	
	public CabDocumentoLazyList(HashMap<String, String> filtros, HashMap<String, String> criterios){
		this.filtros = filtros;
		this.criterios = criterios;
		documentosSelec = new ArrayList<FacCabDocumento>();
		documentosSelec.clear();	
	}
	
	@Override
	public List<FacCabDocumento> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		try {
			try {
				transaction = MyTransaction.getNewTransaction();
				documentoDAO =  new CabDocumentoDAO(transaction);
				//transaction.begin();
				// with datatable pagination limits
				/*
				if (documentos == null || documentos.isEmpty()) {
					documentos = documentoDAO.findDocumentos(startingAt, maxPerPage,  filtros, criterios);
				}else{
					int index = (startingAt/maxPerPage)+1; 
					documentos = documentoDAO.findDocumentosNative(index, maxPerPage, filtros, criterios);
				}*/
				System.out.println("startingAt::"+startingAt);
				if (startingAt==0){
					documentos = documentoDAO.findDocumentos(startingAt, maxPerPage,  filtros, criterios);
				}else{
					int index = (startingAt/maxPerPage)+1;
					documentos = documentoDAO.findDocumentosNative(index, maxPerPage, filtros, criterios);
				}
			} finally {
				//transaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set the total of players
		if(getRowCount() <= 0){
			try {
				setRowCount(documentoDAO.countDocumentosTotal(filtros, criterios));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				setRowCount(0);
			}
		}
		

		
		// set the page dize
		setPageSize(maxPerPage);

		return documentos;
	}
	
	@Override
	public void setRowIndex( int rowIndex ) {
	
	   if ( rowIndex == -1 || getPageSize() == 0 ) {
	    super.setRowIndex( -1 );
	   } else
	    super.setRowIndex( rowIndex % getPageSize() );
	}
	
	@Override
    public FacCabDocumento getRowData(String rowKey) {
        List<FacCabDocumento> cars = (List<FacCabDocumento>) getWrappedData();

        for(FacCabDocumento doc : cars) {
        	System.out.println("Prueba::"+rowKey);
        	System.out.println(doc);
        	System.out.println(doc.getId());
            if(doc.getId().toString().equals(rowKey)){
            	documentosSelec.add(doc);
            	System.out.println("return");
            	return doc;
            }
                
        }

        return null;
    }

    @Override
    public Object getRowKey(FacCabDocumento docu) {
        return docu.getId();
    }
	
	public List<FacCabDocumento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<FacCabDocumento> documentos) {
		this.documentos = documentos;
	}

	public CabDocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(CabDocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	public HashMap<String, String> getFiltros() {
		return filtros;
	}

	public void setFiltros(HashMap<String, String> filtros) {
		this.filtros = filtros;
	}

	public HashMap<String, String> getCriterios() {
		return criterios;
	}

	public void setCriterios(HashMap<String, String> criterios) {
		this.criterios = criterios;
	}

	public List<FacCabDocumento> getDocumentosSelec() {
		return documentosSelec;
	}

	public void setDocumentosSelec(List<FacCabDocumento> documentosSelec) {
		this.documentosSelec = documentosSelec;
	}
	
	

}