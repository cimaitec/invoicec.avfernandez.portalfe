// Variables para setear
onload=function() 
{
	cAyuda=document.getElementById("mensajesAyuda");
	cNombre=document.getElementById("ayudaTitulo");
	cTex=document.getElementById("ayudaTexto");
	divTransparente=document.getElementById("transparencia");
	divMensaje=document.getElementById("transparenciaMensaje");
	form=document.getElementById("formulario");
	urlDestino="mail.php";
	
	claseNormal="input";
	claseError="inputError";
	
	ayuda=new Array();

	ayuda["Codigo"]="Código del Tipo de Mantenimiento. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre Tipo Mantenimiento"]="Nombre del Tipo de Mantenimiento. De 4 a 50 Caracteres. OBLIGATORIO";
	ayuda["Frecuencia"]="Frecuencia de Tiempo Con la Que se Ejecutaria. Valor Numerico";
	ayuda["Numero Periodos"]="Numero de Periodos para las Frecuencias ";
	ayuda["Activo"]=" Estado del Registro";
	
	ayuda["Codigo Tipo Radiobase"]="Código del Tipo de Radiobase. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre Tipo Radiobase"]="Nombre del Tipo de Radiobase. De 4 a 50 Caracteres. OBLIGATORIO";
	ayuda["Peso"]="Peso del Tipo de Radiobase. Valor Numerico";


	ayuda["Codigo Provincia"]="Código de la Provincia. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre Provincia"]="Nombre de la Provincia. De 4 a 50 Caracteres. OBLIGATORIO";

	ayuda["Codigo Etapa"]="Código de la Etapa. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre Etapa"]="Nombre de la Etapa. De 4 a 50 Caracteres. OBLIGATORIO";
	ayuda["Proceso"]="Proceso en el Que Interviene la Etapa. De 4 a 50 Caracteres. OBLIGATORIO";	

	ayuda["Codigo Tipo Antena"]="Código del Tipo de Antena. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre Tipo Antena"]="Nombre del Tipo de Antena. De 4 a 50 Caracteres. OBLIGATORIO";
	ayuda["Peso Tipo Antena"]="Puede ser Decimal, maneja el peso del Tipo de Antena";	
	ayuda["Diametro Tipo Antena"]="Puede ser Decimal.maneja el Diametro del Tipo de Antena";	

	ayuda["Codigo Mantenimiento"]="Código Secuencial Generado, para el Control de Mantenimiento Tecnico de Radiobases";		
	ayuda["Radiobase"]="La Radiobase sobre la cual se realizara el mantenimiento, se seleccion de un popup.";				
	ayuda["Tipo Mantenimiento"]="El Tipo de Mantenimiento que se dará sobre una radiobase seleccionada, su seleccion es desde un popup.";						

	ayuda["Frecuencia Mantemiento"]="La Frecuencia es Un Valor Fijo, no es de ingreso, el mismo se muestra al seleccionar el tipo de mantenimiento";						
	ayuda["Numero Periodos Mantenimiento"]="no es de ingreso, el mismo se muestra al seleccionar el tipo de mantenimiento";						

	ayuda["Ultima Fecha Mantenimiento"]="Se Debe Ingresar, en base a esta fecha se calcula la proxima fecha de mantenimiento, ademas si no es ingresada se pondra un estatus de Pendiente ,NO OBLIGATORIA";								
	ayuda["Proxima Fecha Mantenimiento"]="Se Calcula en base a la ultima fecha de mantenimiento,NO OBLIGATORIA";									
	
	ayuda["Estatus"]="El Estatus del Mantenimiento Varia Automaticamente, dependiendo de si es ingresada la Fecha Ultima de Mantenimiento Tendra un Estatud de Ejecutado, caso contratio se Ubicara Pendiente";									

	ayuda["region"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, region donde esta instalada la radiobase";
	ayuda["zona"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Zona donde esta instalada la radiobase";
	ayuda["provincia"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Provincia donde esta instalada la radiobase";
	ayuda["canton"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Canton donde esta instalada la radiobase";
	ayuda["direccion"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Direccion donde esta instalada la radiobase";
	ayuda["fecha integracion"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Fecha en la que se instalo la Radiobase";
	ayuda["tipoRadiobase"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Tipo de Radiobase";
	ayuda["altura"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Altura de la radiobase";
	ayuda["contratista obra civil"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Persona Autorizada Para la Realizacion de la Obra Civil";
	ayuda["contratista estructura"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Persona Autorizada Para la Realizacion de la Estructura";
	
	ayuda["entidad seguimiento"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Entidad Autorizada Para Realizar las Auditorias";
	ayuda["entidad emisora"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, La Entidad Que Autoriza el Funcionamiento de la Radiobase";
	ayuda["coordenadas Latitud"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Coordenadas Geograficas que ubican a la radiobase expresada en latitud";
	ayuda["coordenadas Longitud"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Coordenadas Geograficas que ubican a la radiobase expresada en longitud";
	ayuda["Coordenada UTM(X)"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Coordenadas UTM Horizontal";
	ayuda["Coordenada UTM(Y)"]="SE PRESENTA Luego de Seleccionar la Radiobase como Dato Informativo, Coordenadas UTM Vertical";
	
	ayuda["Codigo Antena"]="Código de Antena Secuencial Generado";
	ayuda["Tipo Antena"]="Tipo de Antena Que Se Va a Instalar, el mismo Setea los Campos de Diametro y Peso de Antena. CAMPO OBLIGATORIA";
	ayuda["Azimut"]="Azimut. Campo OBLIGATORIO";
	ayuda["Altura Antena"]="OBLIGATORIO, Necesario para el calculo del Momento Efectivo de Trabajo";
	
	ayuda["Modelo Antena"]="Ingreso del Modelo de Antena";
	ayuda["Area Frontal"]="OBLIGATORIO, Necesario para el calculo del Momento Efectivo de Trabajo";
	ayuda["Diametros Antena"]="Se Presenta de acuerdo al tipo de Antena que seleccione";
	ayuda["Momento Efectivo Trabajo"]="Se Calcula en base a la multiplicacion de area por la altura";
	ayuda["Peso Antena"]="Se Presenta de acuerdo al tipo de Antena que seleccione";
	ayuda["Observacion"]="NO OBLIGATORIA, permite ingresar la obsevacion";
		
	ayuda["Momento Total"]="Sumatoria de los Momentos Efectivos de Trabajo de Cada Antena Instalada";
	ayuda["Peso Total"]="Sumatoria de los Pesos de Cada Antena Intalada";
	ayuda["Momento Total Nuevo"]="Sumatoria de los Momentos Efectivos de Trabajo de las Antenas Que estan Pendientes de Instalacion";
	ayuda["Peso Total Nuevo"]="Sumatoria de los Pesos de las Antenas Que estan Pendientes de Instalacion";
	ayuda["% Uso Torre Viento"]="Porcentaje de Uso de las Torres en viento,calculado en base a la sumatoria de los totales de momentos efectivos de trabajo dividido para el momento total de diseño";
	ayuda["% Uso Torre Peso"]="Porcentaje de Uso de las Torres en peso,calculado en base a la sumatoria de los totales de pesos dividido para el peso de diseño";
	ayuda["Altura Torre"]="Altura que tiene la Radiobase";
	ayuda["Velocidad de Viento"]="Velocidad de Viento que existe donde la Radiobase Esta Instalada";
	
	preCarga("ok.gif", "loading.gif", "error.gif");
	
	 // Jessenia Zavala                 
    ayuda["Codigo Frecuencia"]="Código de Frecuencia. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Frecuencia"]="Nombre de la Frecuencia. De 4 a 50 Caracteres. OBLIGATORIO";
                    
    ayuda["Codigo Rol"]="Código del Rol. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Rol"]="Nombre del Rol. De 4 a 50 Caracteres. OBLIGATORIO";
                   
    ayuda["Codigo Proceso"]="Código del Proceso. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Proceso"]="Nombre del Proceso. De 4 a 50 Caracteres. OBLIGATORIO";
                    
    ayuda["Codigo Zona"]="Código de la Zona. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Zona"]="Nombre de la Zona. De 4 a 50 Caracteres. OBLIGATORIO";
    ayuda["Region Zona"]="Region que contiene a la Zona. Valor Numerico";
                    
    ayuda["Codigo Region"]="Código de la Region. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Region"]="Nombre de la Region. De 4 a 50 Caracteres. OBLIGATORIO";
    
    ayuda["Codigo Auditoria"]="Código de la Auditoria. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Auditoria"]="Nombre de la Auditoria. De 4 a 50 Caracteres. OBLIGATORIO";
    ayuda["Entidad Auditoria"]="Entidad que Emite Auditoria. Valor Numerico";
    ayuda["Region Auditoria"]="Region a realizarse la Auditoria. Valor Numerico";
    ayuda["Ciudad Auditoria"]="Ciudad a realizarse la Auditoria. Valor Numerico";
    ayuda["FrcCum Auditoria"]="Frecuencia del Cumplimiento de Auditoria. Valor Numerico";
    ayuda["PrdCum Auditoria"]="Periodo del Cumplimiento de Auditoria. Valor Numerico";
    ayuda["FrcVenc Auditoria"]="Frecuencia del Vencimiento de Auditoria. Valor Numerico";
    ayuda["PrcVenc Auditoria"]="Periodo del Vencimiento de Auditoria. Valor Numerico";
    
    //Nestor Rodriguez
    ayuda["Busqueda Codigo"]="Ingresar el Código a buscar y presionar Enter. Los criterios pueden trabajar conjuntamente";
    ayuda["Busqueda Nombre"]="Ingresar el Nombre a buscar y presionar Enter. Los criterios pueden trabajar conjuntamente";
        
    // SAMUEL MUÑOZ    
    ayuda["Codigo Alarma"]="Código de Alarma. SE GENERA AUTOMATICAMENTE";
	ayuda["Descripcion"]="Descripcion de Alarma. DESCRIPCION DE LA ALARMA";
	ayuda["Frecuencia"]="Frecuencia con la que se dispara la alrma. FRECUENCIA DE LA ALARMA";
    ayuda["Periodos Frecuencia"]="Periodos de Frecuencia. PERIODOS FRECUENCIA";
    ayuda["Notificacion"]="Notificacion de la Alarma. MOMENTO EN EL QUE SE DISPARA LA ALARMA";
    ayuda["Mensaje"]="Mensaje. MENSAJE QUE SE ENVIA";
    ayuda["Medio de Envio"]="Medio de Envio. MEDIO POR EL CUAL VA A SER ENVIADO EL MENSAJE";
    ayuda["Mostrar Observacion"]="Mostrar Observacion por Pantalla. SI SE DESEA MOSTRAR INFORMACION POR PANTALLA";
    ayuda["Activo"]=" Estado del Registro";



	ayuda["Codigo Actividad"]="Código de Actividad. SE GENERA AUTOMATICAMENTE";
	ayuda["Nombre"]="Descripcion de la Actividad. DESCRIPCION O NOMBRE DE LA ACTIVIDAD";
	ayuda["Alarma Previa"]="Código Alarma Previa. ALARMA QUE SE LANZA ANTES DE CUMPLIR CON LA ACTIVIDAD";
    ayuda["Alarma Vencimiento"]="Código Alarma Vencimiento. ALARMA QUE SE LANZA CUANDO EL TIEMPO DE CUMPLIMIENTO DE LA ACTIVIDAD HA VENCIDO";
    ayuda["Activo"]=" Estado del Registro";
    
    
    
    ayuda["Codigo Canton"]="Código de Canton. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Canton"]="Nombre del Canton. NOMBRE QUE SE LE ASIGNA AL CANTON";
    ayuda["Provincia"]="Provincia del Canton. PROVINCIA AL QUE PERTENECE EL CANTON";
    ayuda["Activo"]=" Estado del Registro";
    
    
    ayuda["Codigo Opcion"]="Código de Opcion. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre Opcion"]="Nombre de la Opcion. NOMBRE QUE SE LE ASIGNA A La OPCION";
    ayuda["Ruta Opcion"]="Ruta de la Opcion. RUTA EN LA QUE SE ENCUENTRA LA OPCION";
    ayuda["Opcion Padre"]="Opcion Origen. OPCION DE LA CUAL SE DERIVA";
    ayuda["Nivel"]="Nivel. NIVEL DE LA OPCION";
    ayuda["Activo"]=" Estado del Registro";
    
    
    ayuda["Codigo Usuario"]="Código de Usuario. SE GENERA AUTOMATICAMENTE";
    ayuda["Persona"]="Persona asociada al usuario. PERSONA LIGADA AL USUARIO";
    ayuda["Contraseña"]="Contraseña del Usuario. CONTRASEÑA DEL USUARIO";
    ayuda["Fecha de Expiracion"]="Fecha de expiracion de la contraseña. FECHA EN LA QUE LA CONTRASEÑA EXPIRA";
    ayuda["Dias de Cambio de Clave"]="Días para cambiar la clave. DIAS EN LAS QUE SE CAMBIA LA CONTRASEÑA";
    ayuda["Activo"]=" Estado del Registro";
    
    ayuda["Codigo Tipo Estructura"]="Código de Tipo Estructura. SE GENERA AUTOMATICAMENTE";
    ayuda["Nombre"]="Nombre de Tipo Estructura. NOMBRE QUE SE LE ASIGNA A TIPO ESTRUCTURA";
    ayuda["Activo"]=" Estado del Registro";
}

function preCarga()
{
	imagenes=new Array();
	for(i=0; i<arguments.length; i++)
	{
		imagenes[i]=document.createElement("img");
		imagenes[i].src=arguments[i];
	}
}

function nuevoAjax()
{ 
	var xmlhttp=false; 
	try 
	{ 
		// No IE
		xmlhttp=new ActiveXObject("Msxml2.XMLHTTP"); 
	}
	catch(e)
	{ 
		try
		{ 
			// IE 
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
		} 
		catch(E) { xmlhttp=false; }
	}
	if (!xmlhttp && typeof XMLHttpRequest!="undefined") { xmlhttp=new XMLHttpRequest(); } 
	return xmlhttp; 
}

function limpiaForm()
{
	for(i=0; i<=4; i++)
	{
		form.elements[i].className=claseNormal;
	}
	//Comentado por Nestor ... se agrego la etiqueta formulario:
	///document.getElementById("formulario:inputComentario").className=claseNormal;
}

function campoError(campo)
{
	campo.className=claseError;
	error=1;
}

function ocultaMensaje()
{
	divTransparente.style.display="none";
}

function muestraMensaje(mensaje)
{
	divMensaje.innerHTML=mensaje;
	divTransparente.style.display="block";
}

function eliminaEspacios(cadena)
{
	// Funcion para eliminar espacios delante y detras de cada cadena
	while(cadena.charAt(cadena.length-1)==" ") cadena=cadena.substr(0, cadena.length-1);
	while(cadena.charAt(0)==" ") cadena=cadena.substr(1, cadena.length-1);
	return cadena;
}

function validaLongitud(valor, permiteVacio, minimo, maximo)
{
	var cantCar=valor.length;
	if(valor=="")
	{
		if(permiteVacio) return true;
		else return false;
	}
	else
	{
		if(cantCar>=minimo && cantCar<=maximo) return true;
		else return false;
	}
}

function validaCorreo(valor)
{
	var reg=/(^[a-zA-Z0-9._-]{1,30})@([a-zA-Z0-9.-]{1,30}$)/;
	if(reg.test(valor)) return true;
	else return false;
}

function validaForm()
{
	limpiaForm();
	error=0;
	
	//Comentado por Nestor ... se creo getElementById y txt:
	
	txtNombre = document.getElementById('frmTipo:idNombreTM');	
	var nombre=eliminaEspacios(txtNombre.value);
	
	txtFrecuencia = document.getElementById('frmTipo:txtFrecuenciaId');
	var frecuencia=eliminaEspacios(txtFrecuencia.value);
	
	txtNumeroPeriodos = document.getElementById('frmTipo:txtNumeroPeriodos');	
	var periodos=eliminaEspacios(txtNumeroPeriodos.value);
	alert(periodos);
	//txtActivo = document.getElementById('frmTipo:idActivoTM')	
	//var comentarios=eliminaEspacios(txtActivo.value);
	
	if(!validaLongitud(nombre, 0, 4, 50)) campoError(txtNombre);
	if(!validaLongitud(frecuencia, 1, 4, 50)) campoError(txtFrecuencia);
	if(!validaLongitud(periodos, 1, 4, 50)) campoError(txtNumeroPeriodos);
	//if(!validaCorreo(correo)) campoError(txtCorreo);
	//if(!validaLongitud(comentarios, 0, 5, 500)) campoError(txtComentarios);
	
	if(error==1)
	{
		var texto="<img src='imagenes/error.gif' alt='Error'><br><br>Error: revise los campos en rojo.<br><br><button style='width:45px; height:18px; font-size:10px;' onClick='ocultaMensaje()' type='button'>Ok</button>";
		muestraMensaje(texto);
	}
	else
	{
		var texto="<img src='imagenes/loading.gif' alt='Enviando'><br>Enviando. Por favor espere.<br><br><button style='width:60px; height:18px; font-size:10px;' onClick='ocultaMensaje()' type='button'>Ocultar</button>";
		muestraMensaje(texto);
		//poner el proceso a ejecutar
		var texto="<img src='imagenes/ok.gif' alt='Ok'><br>Gracias por su mensaje.<br>Le responderemos a la brevedad.<br><br><button style='width:45px; height:18px; font-size:10px;' onClick='ocultaMensaje()' type='button'>Ok</button>";
		muestraMensaje(texto);
	}
}

// Mensajes de ayuda

if(navigator.userAgent.indexOf("MSIE")>=0) navegador=0;
else navegador=1;

function colocaAyuda(event)
{
	if(navegador==0)
	{
		var corX=window.event.clientX+document.documentElement.scrollLeft;
		var corY=window.event.clientY+document.documentElement.scrollTop;
	}
	else
	{
		var corX=event.clientX+window.scrollX;
		var corY=event.clientY+window.scrollY;
	}
	cAyuda.style.top=corY+20+"px";
	cAyuda.style.left=corX+15+"px";
}

function ocultaAyuda()
{
	cAyuda.style.display="none";
	if(navegador==0) 
	{
		document.detachEvent("onmousemove", colocaAyuda);
		document.detachEvent("onmouseout", ocultaAyuda);
	}
	else 
	{
		document.removeEventListener("mousemove", colocaAyuda, true);
		document.removeEventListener("mouseout", ocultaAyuda, true);
	}
}

function muestraAyuda(event, campo)
{
	colocaAyuda(event);
	
	if(navegador==0) 
	{ 
		document.attachEvent("onmousemove", colocaAyuda); 
		document.attachEvent("onmouseout", ocultaAyuda); 
	}
	else 
	{
		document.addEventListener("mousemove", colocaAyuda, true);
		document.addEventListener("mouseout", ocultaAyuda, true);
	}
	
	cNombre.innerHTML=campo;
	cTex.innerHTML=ayuda[campo];
	cAyuda.style.display="block";
}


var colorOriCelda;
var filaClick;
var colorFilaClick;
var estiloGridMove='estilosGridMove';
var estiloGridClick='estilosGridClick';

function cambiarFilaOriginal() {
  currentRow=window.event.srcElement.parentNode.parentNode;
  if(currentRow != filaClick){
    colorOriCelda=currentRow.className;
    currentRow.className=estiloGridMove;
  }  
}

function volverFilaOriginal() {
  currentRow=window.event.srcElement.parentNode.parentNode;
  if(currentRow != filaClick)
    currentRow.className=colorOriCelda;
}

function seleccionarFila() {                                 
  currentRow=window.event.srcElement.parentNode.parentNode;
  if(filaClick != currentRow && filaClick != null)
    filaClick.className=colorFilaClick;
  filaClick=currentRow;
  colorFilaClick=colorOriCelda;
  currentRow.className=estiloGridClick;
}
