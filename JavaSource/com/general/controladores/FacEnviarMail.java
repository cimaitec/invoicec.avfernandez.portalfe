package com.general.controladores;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;

import com.documentos.entidades.FacEmpresa;
import com.sun.mail.util.MailSSLSocketFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 


public class FacEnviarMail {

	Properties properties;
	private String autentificacion = "SSL";
	private String host = null;
	private String user;
	private String password = null;
	private final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/*
	 * 	ruc_empresa = variable que contiene el ruc de la empresa
	 *  senderAddress = la direccion de correo de la persona que envia el mensaje
	 *  toAddress = la direccion de correo de la persona que recibira el mensaje
	 *  ccAddress = la direccion de correo de la persona que recibira el mensaje en copia
	 *  bccAddress = la direccion de correo de la persona que recibira el emnsaje en copia oculta
	 *  subject = titulo del mensaje
	 *  xisHTMLFormat = variable booleana que indica si el mensaje es con formato HTML o texto simple
	 *  body = cuerpo del mensaje
	 *  debug = variable booleana que indica a la API si debe hacer el "debugging", es decir, una serie detallando cada paso que se ejecuta. Esta variable seguramente será igual a true durante los primeros usos de la clase, luego no tiene mucho sentido dejarla activa. 
	 *	Adjuntararchivos = variable que contiene la ruta de los archivos que desea adjuntar en el correo
	 * */
	public boolean enviar(			FacEmpresa empresa,
									String toAddress,		
									String ccAddress,
									String bccAddress,	
									String subject,				
									Boolean xisHTMLFormat,	
									StringBuffer body,
									Boolean debug,		
									String [] Adjuntararchivos){
		
		MimeMultipart multipart = new MimeMultipart();
		Session session=null;
		host = empresa.getServidorSMTP();
		user = empresa.getUserSMTP();
		password = empresa.getPassSMTP();
		
		System.out.println("============================ VPI");
		System.out.println(host);
		System.out.println(user);
		System.out.println(password);
		
		
		
/*
		properties = new Properties();
		properties.put("mail.smtp.host", empresa.getServidorSMTP());// varaible que contiene el servidor de correo de salida
		properties.put("mail.smtp.port", empresa.getPuertoSMTP());// varaible que contiene el pruerto del correo
		properties.put("mail.smtp.auth", empresa.getSslSMTP());// variable que contiene si se requiere de autentificación de SMTP.
		properties.setProperty("mail.user", empresa.getUserSMTP());// variable que es el usuario de la conexión.
		properties.setProperty("mail.password", empresa.getPassSMTP());// variable que el el password de la conexión.

		Session session = Session.getDefaultInstance(properties, null);
		session.setDebug(debug); */
		
		
		try {
			Properties properties = System.getProperties();
			//autentificacion ="SSL";
			if (autentificacion.equals("NONE")){
				properties.put("mail.smtp.host",host);
				properties.put("mail.smtp.socketFactory.port", "25");
				properties.put("mail.smtp.auth", "no");
				session = Session.getDefaultInstance(properties,null);
			}
			if (autentificacion.equals("NORMAL")){
				properties.put("mail.smtp.host",host);
				properties.put("mail.smtp.socketFactory.port", "25");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.stmp.user" , user);
				properties.put("mail.smtp.password", password);
				//prop.put("mail.smtp.starttls.enable", "true");
				//To use SSL
		        //props.put("mail.smtp.socketFactory.port", "465");
		        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.put("mail.smtp.port", "25");
		        //props.put("mail.smtp.auth", "true");
		        //props.put("mail.smtp.port", "465");
		        session = Session.getInstance(properties,
						  new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(user, password);}});
			}
			if (autentificacion.equals("TLS")){
				properties.put("mail.smtp.host",host);
				properties.put("mail.smtp.socketFactory.port", "25");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.stmp.user" , user);
				properties.put("mail.smtp.password", password);
				
				//To use SSL
		        //props.put("mail.smtp.socketFactory.port", "465");
		        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.put("mail.smtp.port", "25");
		        //props.put("mail.smtp.auth", "true");
		        //props.put("mail.smtp.port", "465");
		        
		        session = Session.getInstance(properties,
						  new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(user, password);}});
				//session = Session.getDefaultInstance(prop,null);
			}
			if (autentificacion.equals("SSL")){				
				MailSSLSocketFactory sf = null;
		        try {
		            sf = new MailSSLSocketFactory();
		        } catch (Exception e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		            throw new Exception("Error en SSL Mail Socket Factory "+e1.getMessage());
		        }		        
		        /*
				MailSSLSocketFactoryMailing sf = null;
		        try {
		            sf = new MailSSLSocketFactoryMailing();
		        } catch (Exception e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
		        */
		        sf.setTrustAllHosts(true);
		        properties.put("mail.smtp.ssl.socketFactory", sf);
				
		        properties.put("mail.smtp.host",host);
		        properties.put("mail.smtp.socketFactory.port", "25");
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.stmp.user" , user);
		        properties.put("mail.smtp.password", password);
		        properties.put("mail.smtp.starttls.enable", "true");
				//To use SSL
		        properties.put("mail.smtp.socketFactory.port", "465");
		        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		        properties.put("mail.smtp.port", "25");
		        //props.put("mail.smtp.auth", "true");
		        //props.put("mail.smtp.port", "465");
		        session = Session.getInstance(properties,
						  new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(user, password);}});
			}

		      MimeMessage msg = new MimeMessage(session);
		      msg.setFrom(new InternetAddress(empresa.getEmailEnvio()));
		      msg.setRecipients(Message.RecipientType.TO, toAddress);
		      msg.setRecipients(Message.RecipientType.CC, ccAddress);
		      msg.setRecipients(Message.RecipientType.BCC, bccAddress);
		      msg.addHeader("Disposition-Notification-To", toAddress);
		      msg.setSubject(subject);
		      msg.setSentDate(new Date());
		      // BODY
		      MimeBodyPart mbp = new MimeBodyPart();
		      if(xisHTMLFormat){
		         mbp.setContent(body.toString(), "text/html");
		      }
		      else{
		         mbp.setText(body.toString());
		      }
		      
		      multipart.addBodyPart(mbp);
		     
		      
		      for (int i = 0; i < Adjuntararchivos.length; i++) {
		    	  if(Adjuntararchivos[i] != ""){
		    		  BodyPart adjunto =  new MimeBodyPart();
			    	  adjunto.setDataHandler(new DataHandler(new FileDataSource(Adjuntararchivos[i])));
			    	  adjunto.setFileName((new File(Adjuntararchivos[i])).getName());
			    	  multipart.addBodyPart(adjunto);
			    	  msg.setContent(multipart);
			  	  }
			}
		      Transport.send(msg);
		}
	  catch (Exception mex){
		System.err.println("VPI - error"+ mex.getMessage());
	     return false;
	   }
	   return true;
	}
	
	public boolean validar_correo(String email){
		 // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}

}

