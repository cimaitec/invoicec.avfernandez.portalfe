package com.documentos.controladores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.documentos.entidades.DetalleImpuestosentidades;
import com.documentos.entidades.FacCabDocumento;
import com.documentos.entidades.FacDetAdicional;
import com.documentos.entidades.FacDetDocumento;
import com.documentos.entidades.FacDetImpuesto;
import com.documentos.entidades.FacDetRetencione;
import com.documentos.entidades.FacEmpresa;
import com.general.entidades.FacProducto;

@SessionScoped
@ManagedBean
public class SpoolGenerarArchivoControlador {

	private List<FacEmpresa> empresa;
	private List<FacCabDocumento> cabece_documento;
	private List<FacDetDocumento> detalle_documento;
	private List<FacDetRetencione> detalle_retencion;
	private List<FacDetImpuesto> detalle_impuesto;
	private List<FacDetAdicional> detelle_adicional;
	private List<DetalleImpuestosentidades> detalleCabecera_impuesto;
	private List<String> MotivoRazon;
	private List<FacProducto> DetalleAdicionalproducto;

	/* para generar el archivo de texto */
    FileReader fr = null;
    String Espacios = "";
    File archivo;
    BufferedWriter bfw;
    String lineaTXT;
    String VE="VE",IT="IT",IC="IC",T="T",TI="TI",DE="DE",IM="IM",DA="DA",IA="IA",TIR = "TIR", MO = "MO", DEST = "DEST";//SON LOS INDICADORES DE LAS LINEAS
    String separador="|", saltoLinea=System.getProperty("line.separator");
    String rutaGuardar,nombreArchivo;//el nombre del archivo se concatena tipoDocumento+ruc+codestablecimiento+codemision+secuencia
    
    public int generarDocumento(){

    	try{
    		nombreArchivo = cabece_documento.get(0).getId().getCodigoDocumento().trim() + empresa.get(0).getRuc() + cabece_documento.get(0).getId().getCodEstablecimiento().trim() + cabece_documento.get(0).getId().getCodPuntEmision().trim() + cabece_documento.get(0).getId().getSecuencial() + ".txt";	
	    	lineaTXT= VE + separador + cabece_documento.get(0).getId().getCodigoDocumento().trim() + separador + "Comprobante" + separador + "1.0.0" + separador + saltoLinea;
	        //					IT		|					ambiente								|					tipoEmision							|				razonSocial						|			nombreComercial						|			ruc						|								claveAcceso										|					codDoc										|					estab												|							ptoEmi									|				secuencial									|				dirMatriz							|				emails			|
	        lineaTXT = lineaTXT + IT + separador + cabece_documento.get(0).getId().getAmbiente() + separador + cabece_documento.get(0).getTipoEmision().trim() + separador + empresa.get(0).getRazonSocial() + separador + empresa.get(0).getRazonComercial() + separador + empresa.get(0).getRuc().trim() + separador + cabece_documento.get(0).getClaveAcceso().trim() + separador + cabece_documento.get(0).getId().getCodigoDocumento() + separador + cabece_documento.get(0).getId().getCodEstablecimiento() + separador + cabece_documento.get(0).getId().getCodPuntEmision() + separador + cabece_documento.get(0).getId().getSecuencial() + separador + empresa.get(0).getDireccionMatriz() + separador + cabece_documento.get(0).getEmail() + separador + saltoLinea;
	          
	        //					IC				|						fechaEmision															|			dirEstablecimiento					|			contribuyenteEspecial					|			obligadoContabilidad					|				tipoIdentificacionComprador										|						guiaRemision						|					razonSocialComprador						|				identificacionComprador								|	moneda			|						rise				|							codDocModificado					|					numDocModificado						|																			fechaEmisionDocSustentoNota
	        lineaTXT = lineaTXT + IC + separador + new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFechaEmision()) + separador + empresa.get(0).getDireccionMatriz() + separador + empresa.get(0).getContribEspecial() + separador + empresa.get(0).getObligContabilidad().trim() + separador + cabece_documento.get(0).getTipIdentificacionComprador().trim() + separador + cabece_documento.get(0).getGuiaRemision() + separador + cabece_documento.get(0).getRazonSocialComprador() + separador + cabece_documento.get(0).getIdentificacionComprador() + separador + "DOLAR" + separador + cabece_documento.get(0).getRise() + separador + cabece_documento.get(0).getCodDocModificado() + separador + cabece_documento.get(0).getNumDocModificado() + separador + ((cabece_documento.get(0).getFecEmisionDocSustento() == null) ? Espacios : new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFecEmisionDocSustento())) + 
	        	 //		|					valorModificacion				|								motivo							|					periodoFiscal						|						dirPartida					|											razonSocialTransportista																				|															tipoIdentificacionTransportista																				|														rucTransportista																						| 			aux1		|			aux2		|															fechaIniTransporte																											|																			fechaFinTransporte 																						|			placa								|
	        	 separador + cabece_documento.get(0).getMotivoValor() + separador + cabece_documento.get(0).getMotivoRazon().trim() + separador + cabece_documento.get(0).getPeriodoFiscal() + separador + cabece_documento.get(0).getPartida() + separador + (cabece_documento.get(0).getId().getCodigoDocumento().trim().equals("06") ? cabece_documento.get(0).getRazonSocialComprador() : Espacios) + separador + (cabece_documento.get(0).getId().getCodigoDocumento().trim().equals("06") ? cabece_documento.get(0).getTipIdentificacionComprador().trim() : Espacios) + separador + (cabece_documento.get(0).getId().getCodigoDocumento().trim().equals("06") ? cabece_documento.get(0).getIdentificacionComprador().trim() : Espacios) + separador + Espacios + separador + Espacios + separador + ((cabece_documento.get(0).getFechaInicioTransporte() == null) ? Espacios : new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFechaInicioTransporte())) + separador + ((cabece_documento.get(0).getFechaFinTransporte() == null) ? Espacios : new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFechaFinTransporte())) + separador + cabece_documento.get(0).getPlaca() + separador + saltoLinea;
	
	        //						T		|						subtotal12					|							subtotal0					|							subtotalNoSujeto			|					totalSinImpuestos						|							totalDescuento				|						ICE									|							IVA12				|							importeTotal				|							propina					|						importeAPagar					|
	        lineaTXT = lineaTXT + T + separador + cabece_documento.get(0).getSubtotal12() + separador + cabece_documento.get(0).getSubtotal0() + separador + cabece_documento.get(0).getSubtotalNoIva() + separador + cabece_documento.get(0).getTotalSinImpuesto() + separador + cabece_documento.get(0).getTotalDescuento() + separador + cabece_documento.get(0).getTotalvalorICE() + separador + cabece_documento.get(0).getIva12() + separador + cabece_documento.get(0).getImporteTotal() + separador + cabece_documento.get(0).getPropina() + separador + cabece_documento.get(0).getImporteTotal() + separador + saltoLinea;
	         
	         
	        /** LLENANDO DETALLE DE RETENCIONES **/
	        for (FacDetRetencione cab_impuesto : detalle_retencion) {
	         //						  TIR		|							codigo					|				codigoRetencion					|						baseImponible		|					porcentajeRetener					|				valorRetenido		|				codDocSustento							|							numDocSustento					|									fechaEmisionDocSustento													|
	        	lineaTXT = lineaTXT + TIR + separador +  cab_impuesto.getId().getCodImpuesto() + separador + cab_impuesto.getCodPorcentaje() + separador + cab_impuesto.getBaseImponible() + separador + cab_impuesto.getPorcentajeRetencion() + separador + cab_impuesto.getValor() + separador + cabece_documento.get(0).getCodDocSustento() + separador + cabece_documento.get(0).getNumDocSustento() + separador + new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFechaEmisionDocSustento()) + separador + saltoLinea;
	        }
	         
	        /** LLENANDO EL RESUME DE DETALLE DE IMPUESTO **/

	         for (int i = 0; i < detalleCabecera_impuesto.size(); i++) {
	        	 //						TI		|									Codigo							|						CodigoPorcentaje					|					Tarifa									|							BaseImponible						|									Valor				|							Impuestos							|
	        	 lineaTXT = lineaTXT + TI + separador + detalleCabecera_impuesto.get(i).getCodImpuesto() + separador + detalleCabecera_impuesto.get(i).getCodPorcentaje() + separador + detalleCabecera_impuesto.get(i).getTarifa() + separador + detalleCabecera_impuesto.get(i).getBaseImponible() + separador + detalleCabecera_impuesto.get(i).getValor() + separador + detalleCabecera_impuesto.get(i).getTipoImpuestos() + separador + saltoLinea;
	         }
	         
	        /** LLENANDO MOTIVO Y RAZON	**/
	        for(String lisMotivoRazon: MotivoRazon){
	        	String []ArregloMotivo = lisMotivoRazon.split(",");
	        	//						MO		|			motivoRazon					|				motivoValor|
	        	lineaTXT = lineaTXT + MO + separador + ArregloMotivo[0].trim() + separador + ArregloMotivo[1].trim() + separador + saltoLinea;
	        }
	        
	        /** LLENANDO DESTINO DEL PRODUCTO PARA GUIA DE REMISION	
	         * por ahora se esta dejando quedado el codigo de Guia de Remision para un futuro majenar varios destinatarios que cada uno puede tener vario detalle**/
	        if(cabece_documento.get(0).getId().getCodigoDocumento().trim().equals("06")){
		        //					DEST		|		codigoremision		|			identificacionDestinatario					|				razonSocialDestinatario								|					dirDestinatario								|							motivoTraslado				|						docAduaneroUnico					|							codEstabDestino							|						ruta					|							codDocSustento					|
		        lineaTXT = lineaTXT + DEST + separador + "1" +separador + cabece_documento.get(0).getIdentificacionDestinatario() + separador + cabece_documento.get(0).getRazonSocialDestinatario() + separador + cabece_documento.get(0).getDireccionDestinatario() + separador + cabece_documento.get(0).getMotivoTraslado() + separador + cabece_documento.get(0).getDocAduaneroUnico() + separador + cabece_documento.get(0).getCodEstablecimientoDest() + separador + cabece_documento.get(0).getRuta() + separador + cabece_documento.get(0).getCodDocSustento() + separador + 
		        		// 				numDocSustento						|							numAutDocSustento				|																			fechaEmisionDocSustento																							|
		        		cabece_documento.get(0).getNumDocSustento() + separador + cabece_documento.get(0).getNumAutDocSustento() + separador +  ((cabece_documento.get(0).getFechaEmisionDocSustento() == null) ? Espacios : new SimpleDateFormat("dd/MM/yyyy").format(cabece_documento.get(0).getFechaEmisionDocSustento())) + separador + saltoLinea;
	        }
	        
	        /** DETALLLE DEL DOCUMENTO
	         * ,Lo mismo para el detalle se le esta dejando quemado el codigo del destinarario hasta un futuro manejar varios detalle por cada destinatario **/
	        for (FacDetDocumento detDocumento : detalle_documento){ 
	        	// 						DE									|	codigoDestinatario												 	|							lineaDetalle				|						codigoPrincipal				|			codigoAuxiliar				|					descripcion					|				cantidad				|				precioUnitario					|				descuento				|					precioTotalSinImpuesto			|
	        	lineaTXT = lineaTXT + DE + (cabece_documento.get(0).getId().getCodigoDocumento().trim().equals("06") ? separador + "1" : "") + separador + detDocumento.getId().getSecuencialDetalle() + separador + detDocumento.getCodPrincipal() + separador + detDocumento.getCodAuxiliar() + separador + detDocumento.getDescripcion() + separador + detDocumento.getCantidad() + separador + detDocumento.getPrecioUnitario() + separador + detDocumento.getDescuento() + separador + detDocumento.getPrecioTotalSinImpuesto() + separador + saltoLinea;
	        	for (FacDetImpuesto facDetImpuesto : detalle_impuesto) {
	        		if(detDocumento.getId().getSecuencialDetalle() == (facDetImpuesto.getId().getSecuencialDetalle()) && facDetImpuesto.getId().getCodImpuesto() == 2){
						// 					  IM		|							lineaDetalle				|					impuestoSecuencial					|					impuestoCodigo				|			impuestoCodigoPorcentaje			|				impuestoBaseImponible			|					impuestoTarifa				|				impuestoValor			|				tipoImpuestos					|
	        			lineaTXT = lineaTXT + IM + separador + detDocumento.getId().getSecuencialDetalle() + separador + facDetImpuesto.getId().getDetSecuencial() + separador + facDetImpuesto.getId().getCodImpuesto() + separador + facDetImpuesto.getCodPorcentaje() + separador + facDetImpuesto.getBaseImponible() + separador + 		facDetImpuesto.getTarifa() + separador + facDetImpuesto.getValor() + separador + facDetImpuesto.getTipoImpuestos() + separador + saltoLinea;
	        		}
	        		if(detDocumento.getId().getSecuencialDetalle() == (facDetImpuesto.getId().getSecuencialDetalle()) && facDetImpuesto.getId().getCodImpuesto() == 3){
						// 					  IM		|							lineaDetalle				|						impuestoSecuencial					|					impuestoCodigo				|			impuestoCodigoPorcentaje			|				impuestoBaseImponible			|					impuestoTarifa				|				impuestoValor			|				tipoImpuestos					|
	        			lineaTXT = lineaTXT + IM + separador + detDocumento.getId().getSecuencialDetalle() + separador + facDetImpuesto.getId().getDetSecuencial() + separador + facDetImpuesto.getId().getCodImpuesto() + separador + facDetImpuesto.getCodPorcentaje() + separador + facDetImpuesto.getBaseImponible() + separador + 		facDetImpuesto.getTarifa() + separador + facDetImpuesto.getValor() + separador + facDetImpuesto.getTipoImpuestos() + separador + saltoLinea;
	        		}
	        	 }
	        	for(FacProducto facproducto : DetalleAdicionalproducto){
	        		if(Integer.parseInt(detDocumento.getCodPrincipal()) == (facproducto.getCodPrincipal())){
	        			//							DA		|					codigoSecuencialDetalle					|						detAdicionalNombre															|												detAdicionalValor							|
	        			if(!facproducto.getAtributo1().trim().equals(""))
	        				lineaTXT = lineaTXT + DA + separador + detDocumento.getId().getSecuencialDetalle() + separador + (facproducto.getAtributo1().trim().equals("") ? Espacios : facproducto.getAtributo1()) + separador + (facproducto.getValor1().trim().equals("") ? Espacios : facproducto.getValor1()) + separador + saltoLinea;
	        			if(!facproducto.getAtributo2().trim().equals(""))
	        				lineaTXT = lineaTXT + DA + separador + detDocumento.getId().getSecuencialDetalle() + separador +  (facproducto.getAtributo2().trim().equals("") ? Espacios : facproducto.getAtributo2()) + separador + (facproducto.getValor2().trim().equals("") ? Espacios : facproducto.getValor2()) + separador + saltoLinea;
	        			if(!facproducto.getAtributo3().trim().equals(""))
	        				lineaTXT = lineaTXT + DA + separador + detDocumento.getId().getSecuencialDetalle() + separador + (facproducto.getAtributo3().trim().equals("") ? Espacios : facproducto.getAtributo3()) + separador + (facproducto.getValor3().trim().equals("") ? Espacios : facproducto.getValor3()) + separador + saltoLinea;
	        		} 
	        	}
	        }
	         
	        /** LLENANDO DETALLE ADICIONAL **/
	        for (FacDetAdicional detAdicional : detelle_adicional){
	         //						  IA	 |					infoAdicionalSecuencial						|			infoAdicionalNombre			|			infoAdicionalValor			|
	        	lineaTXT = lineaTXT + IA + separador + detAdicional.getId().getSecuencialDetAdicional() + separador + detAdicional.getNombre() + separador + detAdicional.getValor() + separador + saltoLinea;
	        }
	        creaArchivo();
	        return 1;
    	}catch (Exception e) {
  			e.printStackTrace();
  			return 0;
		}
    }
    
  //TODO creando archivo txt
    private void creaArchivo(){
  	  try{
			//rutaGuardar="C:/";
          rutaGuardar = empresa.get(0).getPathCompGenerados();
          rutaGuardar = rutaGuardar.trim()+nombreArchivo;
          archivo = new File (rutaGuardar);
          /*Validamos si el archivo existe si no existe lo creamos con el  * metodo createNewFile */
          if(!archivo.exists()) archivo.createNewFile();
          // Abrimos el archivo
          fr = new FileReader (archivo); 
  		  
          bfw=new BufferedWriter(new FileWriter(rutaGuardar));
          //Escribimos en el archivo
          bfw.append(lineaTXT);
          bfw.flush();
          bfw.newLine();// crea una nueva linea
          fr.close();
          bfw.close();
  	  }
      catch(IOException e){
    	  e.printStackTrace();
      }
  	  catch (Exception e) {
  			FacesMessage mensaje=null;
  			mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,e.getMessage(),null);
  			FacesContext.getCurrentInstance().addMessage(null, mensaje);
  	  }
      finally{
           try {
            //Cerramos el archivo
             fr.close();
           }
           catch (IOException ex) { 
                                 
           }
      }    
    }

	public List<FacEmpresa> getEmpresa() {
		return empresa;
	}

	public void setEmpresa(List<FacEmpresa> empresa) {
		this.empresa = empresa;
	}

	public List<FacCabDocumento> getCabece_documento() {
		return cabece_documento;
	}

	public void setCabece_documento(List<FacCabDocumento> cabece_documento) {
		this.cabece_documento = cabece_documento;
	}

	public List<FacDetDocumento> getDetalle_documento() {
		return detalle_documento;
	}

	public void setDetalle_documento(List<FacDetDocumento> detalle_documento) {
		this.detalle_documento = detalle_documento;
	}

	public List<FacDetRetencione> getDetalle_retencion() {
		return detalle_retencion;
	}

	public void setDetalle_retencion(List<FacDetRetencione> detalle_retencion) {
		this.detalle_retencion = detalle_retencion;
	}

	public List<FacDetImpuesto> getDetalle_impuesto() {
		return detalle_impuesto;
	}

	public void setDetalle_impuesto(List<FacDetImpuesto> detalle_impuesto) {
		this.detalle_impuesto = detalle_impuesto;
	}

	public List<FacDetAdicional> getDetelle_adicional() {
		return detelle_adicional;
	}

	public void setDetelle_adicional(List<FacDetAdicional> detelle_adicional) {
		this.detelle_adicional = detelle_adicional;
	}  
	
	public List<DetalleImpuestosentidades> getDetalleCabecera_impuesto() {
		return detalleCabecera_impuesto;
	}

	public void setDetalleCabecera_impuesto(
			List<DetalleImpuestosentidades> detalleCabecera_impuesto) {
		this.detalleCabecera_impuesto = detalleCabecera_impuesto;
	}

	public List<String> getMotivoRazon() {
		return MotivoRazon;
	}

	public void setMotivoRazon(List<String> motivoRazon) {
		MotivoRazon = motivoRazon;
	}

	public List<FacProducto> getDetalleAdicionalproducto() {
		return DetalleAdicionalproducto;
	}

	public void setDetalleAdicionalproducto(
			List<FacProducto> detalleAdicionalproducto) {
		DetalleAdicionalproducto = detalleAdicionalproducto;
	}
	
}
