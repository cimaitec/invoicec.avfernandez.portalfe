package com.webServices.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteWS
{
	private String line = "";
	private String hostname;
	private String namespace;
	private String path;
	private int port;
	private String metodo;
	private ArrayList parametros;
	private ArrayList valores;
	private String soapAction;
	private String postInfo;
	private String charset;
	private int timeout=60000;
	private String errorGeneral;
	
	public void setHostname(String hostname)
	{
		this.hostname=hostname;
	}
	
	public void setPort(int port)
	{
		this.port=port;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}

	public void setNameSpace(String namespace)
	{
		this.namespace = namespace;
	}
		
	public void setPostInfo(String metodo, ArrayList  parametros, ArrayList valores)
	{
		this.metodo = metodo;
		this.parametros = parametros;
		this.valores = valores;
	}

	public void setPostInfo(String _postInfo) {
        this.postInfo = _postInfo;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        if (charset==null )
            charset="US-ASCII";
        return charset;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }
	
    public void setErrorGeneral(String errorGeneral) {
        this.errorGeneral = errorGeneral;
    }
	
    public String getErrorGeneral() {
        return errorGeneral;
    }
	
    public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
    
    public String doPost()
	{
		StringBuffer respuesta = new StringBuffer();
        try
        {
        	try
        	{
        		System.out.println(" Inicio doPost()...");
        		
        		Socket sock = new Socket();
				SocketAddress socketaddress = new InetSocketAddress(hostname, port);
				boolean conectado = false;
                try{
					sock.connect(socketaddress, timeout);
                    conectado = true;
                }catch(Exception e)
                {
                	System.out.println(e);
                }
				
				if (conectado)
                {
					System.out.println(" Conectado...");
					System.out.println(" El charset name "+charset);
					
					sock.setSoTimeout(timeout);
					BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), charset));
					wr.write("POST "+path + " HTTP/1.1\r\n");
					wr.write("Host: "+hostname+"\r\n");
					wr.write("Content-Length: " + postInfo.length() + "\r\n");
					wr.write("Content-Type: text/xml; charset=\""+charset+"\"\r\n");
					wr.write("SOAPAction: \""+soapAction+"\"\r\n");
					wr.write("\r\n");
					wr.write(postInfo);
					wr.write("\r\n");
					wr.flush();
					BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				  
					String line="";
					StringBuffer header = new StringBuffer();
                    while ((line = rd.readLine()).length()>1 ) {
						header.append(line);
                        header.append("\n");
                    }
					
                    int content_length = obtenerTamanoContenido(header);
                    int value=0; 
                    // reads to the end of the stream 
                    int caracteres_leidos = 0;
                           
                    while( caracteres_leidos< content_length &&( (value = rd.read()) != -1 )   )
                    {
						// converts int to character
                        char c = (char)value;
                        respuesta.append(c);
                        caracteres_leidos++;
                    }
				  
					respuesta.append("\n");
					wr.close();
					rd.close();
                         
                    errorGeneral = obtieneMensajeErrorGeneral(header, respuesta);
                          
                    if (errorGeneral.length()>1)
                        respuesta.append(header);
                    }
                }catch (SocketTimeoutException ste)
                {
                	System.out.println(" La excepcion de socket time...");
                	ste.printStackTrace();
                	errorGeneral="Timeout al procesar el requerimiento";  }		
        }catch (Exception e)
        {
        	System.out.println(e);
        	errorGeneral="\"Error al procesar el requerimiento "+e.getMessage()+" "+e.toString();
        	e.printStackTrace();
        }		

        extraeRequerimiento(respuesta);
                
        
		return    respuesta.toString();
	}
	
	private String obtieneMensajeErrorGeneral(StringBuffer header, StringBuffer respuesta)
	{
		StringBuffer mensajeError= new StringBuffer();
        if (header.toString().startsWith("HTTP/1.1 500"))
            mensajeError.append("Error 500 (Error interno del servidor)");
            
        if (header.toString().startsWith("HTTP/1.1 404"))
            mensajeError.append("Error 404  (Pagina no encontrada)");                          
        
        return mensajeError.toString();
    }
	
	private int obtenerTamanoContenido(StringBuffer header)
	{
		int retorno = 0;
        try
        {
            String s = header.toString();
            Pattern p = Pattern.compile("Content-Length: (\\d*\\d)");
            Matcher m = p.matcher(s);
            m.find();
            String text = m.group(1);
            retorno = Integer.parseInt(text);        
        }catch(Exception e){}
        return retorno;
    }
	
	private void extraeRequerimiento(StringBuffer respuesta)
	{
		// System.out.println(respuesta);
	    if (respuesta.indexOf("<?xml version=")!=-1)
	    	respuesta.delete(0, respuesta.indexOf("<?xml version="));
	    if (respuesta.indexOf("HTTP/1.1 200")!=-1)
	    	respuesta.delete(0, respuesta.indexOf("<"));
	}
	

}
