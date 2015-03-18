package com.login.controladores;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class Captcha {
	
	public void check(ActionEvent e){
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Your Code Is Correct !",null));
	}
	
	//public String check(ActionEvent e){
	//	FacesContext.getCurrentInstance().addMessage(null,
	//			new FacesMessage(FacesMessage.SEVERITY_INFO, "Your Code Is Correct !",null));
	//	return "/paginas/Consulta_Documentos/Con_Documento2.jsf";
	//}
	
	//ReturnPagina = "/paginas/Consulta_Documentos/Con_Documento2.jsf";
}