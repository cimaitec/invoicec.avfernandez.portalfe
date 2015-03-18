function mayusculas(e) {
  var textoOriginal = String.fromCharCode(e.keyCode); //recuperamos el codigo de la letra que fue pulsada
  e.keyCode = 0;//inhabilitamos el valor del cajon del texto poniendole el ascii nulo
  var textoMayuscula = textoOriginal.toUpperCase();//hacemos mayuscula la letra pulsada anteriormente
  e.keyCode = textoMayuscula.charCodeAt();//convertimos la letra mayuscula a codigo ascii para finalmente asignarlo al cajon de texto	  
  if (e.keyCode==8 || e.keyCode== 32) return true; //se evalua que los unicos caracteres validos sean espacio y backspace
    patron = /^[a-zA-zñÑ\s\w]+$/ //se asigna patron para que solo tome letras              
  
  return patron.test(textoOriginal);//se evalua si el caracter pulsado esta dentro del patron seleccionado
}

function mayusculasAndNumeros(e) {
  var textoOriginal = String.fromCharCode(e.keyCode); //recuperamos el codigo de la letra que fue pulsada
  e.keyCode = 0;//inhabilitamos el valor del cajon del texto poniendole el ascii nulo
  var textoMayuscula = textoOriginal.toUpperCase();//hacemos mayuscula la letra pulsada anteriormente
  e.keyCode = textoMayuscula.charCodeAt();//convertimos la letra mayuscula a codigo ascii para finalmente asignarlo al cajon de texto	  
  if (e.keyCode==8 || e.keyCode== 32) return true; //se evalua que los unicos caracteres validos sean espacio y backspace
  patron = /^[a-zA-zñÑ\s\w]+$/ //se asigna patron para que solo tome letras
  return patron.test(textoOriginal);//se evalua si el caracter pulsado esta dentro del patron seleccionado
}

function bloqueado(e) {
  return false;
}

function ValidarNumericos(){ 
  if(!(event.keyCode >= 48 && event.keyCode <= 57) && event.keyCode != 8 && event.keyCode != 37 && event.keyCode != 39){
    event.returnValue = false;
  }
  return;
}

function ValidaLetras(){
  if(!(event.keyCode>=65 && event.keyCode <= 90 || event.keyCode >= 97 && event.keyCode <= 122 || event.keyCode == 32)){
    event.returnValue = false;
  }
  return;
}

function ValidarCaracteres(){ 
  if(!(event.keyCode >= 41 && event.keyCode <= 90) && !(event.keyCode >= 61 && event.keyCode <= 122) && event.keyCode != 8 && event.keyCode != 37 && event.keyCode != 39){
    event.returnValue = false;
  }    
  return;
}

function ValidarNumericosDec(obj){
  if(!(event.keyCode>=48 && event.keyCode<=57) && (!(event.keyCode==46))){
    event.returnValue = false;
  }
  if(arguments[0]==null) return;
  if((event.keyCode==46) && obj.value.indexOf(".")!=-1){
    event.returnValue = false;
	return;
  }
  return;
}

function ValidarHora(obj){
  if(!(event.keyCode>=48 && event.keyCode<=57) && (!(event.keyCode==58))){
    event.returnValue = false;
  }
  if(arguments[0]==null) return;
  if((event.keyCode==58) && obj.value.indexOf(":")!=-1){
    event.returnValue = false;
	return;
  }
  return;
}

function trim(s){
  var idx  = 0;
  var idxf = 0;
  var str  = String(s);
  while (str.charAt(idx) == " " && idx < str.length)
    idx++;
  if (idx == str.length)
    str = "";
  else{
    str  = str.substring(idx,str.length);
	idxf = str.length - 1;
	while(str.charAt(idxf) == " ")
	  idxf--;
	str = str.substring(0, idxf + 1);
  }
  return str;
}

function setCaretToEnd(ctrl) {
  if(ctrl.setSelectionRange) {
    ctrl.setSelectionRange(ctrl.value.length, ctrl.value.length);
  }
  else if (ctrl.createTextRange) {
    var range = ctrl.createTextRange();
    range.moveStart('character', ctrl.value.length);
    range.select();
  }
}