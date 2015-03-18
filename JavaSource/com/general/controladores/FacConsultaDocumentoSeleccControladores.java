package com.general.controladores;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.general.entidades.facDetalleDocumentoEntidad;

public class FacConsultaDocumentoSeleccControladores extends ListDataModel<facDetalleDocumentoEntidad> implements SelectableDataModel<facDetalleDocumentoEntidad> {
	  public FacConsultaDocumentoSeleccControladores() {
	    }  
	  
	    public FacConsultaDocumentoSeleccControladores(List<facDetalleDocumentoEntidad> data) {  
	        super(data);  
	    }  
	      
	    @SuppressWarnings("unchecked")
		@Override  
	    public facDetalleDocumentoEntidad getRowData(String rowKey) {  
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
	          
	        List<facDetalleDocumentoEntidad> cars = (List<facDetalleDocumentoEntidad>) getWrappedData();  
	          
	        for(facDetalleDocumentoEntidad car : cars) {  
	            if(car.getFOLFAC().equals(rowKey))  
	                return car;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(facDetalleDocumentoEntidad car) {  
	        return car.getFOLFAC();  
	    }  
}
