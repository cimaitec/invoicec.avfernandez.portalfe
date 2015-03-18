package com.general.controladores;

import java.util.ArrayList;
import java.util.List;


public class facVerificarIdentificacionControladores {
	
	//TODO ValidarNumeroIdentificacion
	public boolean ValidarNumeroIdentificacion(String numero){
		List<Integer> id = new ArrayList<Integer>();
		
		if(numero.length() < 10)
			return false;
		
		for(int i = 0; i < 10; i++){
			id.add(Integer.parseInt(numero.substring(i, i + 1))) ;  //Llenando la lista de números en id
		}
        if (ValidarDigitos(numero))      //Validación previa del número
        {
            if ((numero.length() == 10))  //Cédula
            {   
            	return ValidarCedula(id);  //Validando el # número de cédula
            }
            else if (numero.length() == 13) //RUC
            {
                if (Integer.parseInt(numero.substring(1, 2)) < 6)  {//Validando el # de Ruc para Entidad Natural
                	System.out.println("VAlida ruc de entida natural");
                	return ValidarCedula(id);
                }else{
                	System.out.println("VAlida ruc de entida publica");
                    return ValidarRuc(id);  //Validando el # de Ruc para Entidad Jurídica y Públicas
                }
            }
            else
            	return false;
        }
        else
            return false;

	}
	
	//TODO validar digitos
	private boolean ValidarDigitos(String numero){
		
		 int numeroProvincias = 24;
         int n1 = Integer.parseInt(numero.substring(0, 1)) * 10 + Integer.parseInt(numero.substring(1, 2)); //numero de provincvia 1 - 24

         if (n1 == 0 || n1 > numeroProvincias)                                         //si no coincide con un # de provincia retorna false
             return false;
         else if (numero.length() == 13)
         {
             if ((numero.substring(10, numero.length()) == "000") || (numero.substring(9, numero.length()) == "0000")) //el RUC para Juridicos no puenden ser 000
                 return false;                                                           //el RUC para Instituciones Públicas no puede ser 0000
         }
         else if ((Integer.parseInt(numero.substring(2, 3)) == 7) || (Integer.parseInt(numero.substring(2, 3)) == 8)) //el tercer dígito de la indentificación no puede ser 7 ni 8
             return false;

         return true;
	}
	
	//TODO Validación del Ruc para Entidades Jurídicas y Públicas
	private Boolean ValidarRuc(List<Integer> ident) {
		
		int suma = 0;
        int residuo;
        int digitoVerificador = 0;      //si el residuo del módulo es 0 el dígito verificador es 0
		
        /** Coeficientes  4 3 2 7 6 5 4 3 2 **/
        List<Integer> coeficiente = new ArrayList<Integer>();
        coeficiente.add(4);
        coeficiente.add(3);
        coeficiente.add(2);
        coeficiente.add(7);
        coeficiente.add(6);
        coeficiente.add(5);
        coeficiente.add(4);
        coeficiente.add(3);
        coeficiente.add(2);
        
        int modulo = 11; //Módulo 11 para RUC Jurídico y Público
        /** Proceso de algoritmo **/
        if (ident.get(2).intValue() == 9){      //Entidad Jurídica
            for (int i=0; i< 9; i++)  {    
            	ident.set(i, (ident.get(i).intValue() * coeficiente.get(i).intValue()));  //multiplicación de cada digito por su respectivo coeficiente

            }
        }else
        	for (int i=0; i<8; i++)        //se multiplica solo los 9 primeros dígitos
            {
                ident.set(i, (ident.get(i).intValue() * coeficiente.get(i + 1).intValue()));  //multiplicación de cada digito por su respectivo coeficiente

                if (i == 9)                 //para este caso  solo se toman encuenta los 8 primeros 
                    ident.set(i, 0);
            }
        
        for (int i=0; i<9; i++)             //suma de los valores que resultaron de la multiplicación
            suma =  suma + ident.get(i).intValue();
        
        residuo = suma % modulo;       //se calcula el módulo en este caso 11
        
        if(residuo != 0)        //si el residuo del módulo no es 0 se calcula el digito verificador
            digitoVerificador = modulo - residuo;
        
        System.out.println("Numero digitador:: " + digitoVerificador + "		verificador:: " + ident.get(9).intValue());
        if (digitoVerificador == ident.get(9).intValue())  //si el dígito verificador es igual al décimo dígito del 
        	return true;                    //número de identificación, el # es correcto (true)
        else                                //caso contrario retornamos false
        	return false;
	}
	
	//TODO Validación de la Cédula y del RUC para Entidad Natural
	private Boolean ValidarCedula(List<Integer> ident){
		int suma = 0;
        int residuo;
        int digitoVerificador = 0; //si el residuo del módulo es 0 el dígito verificador es 0
        
        /**		Coeficientes  2 1 2 1 2 1 2 1 2	**/
        List<Integer> coeficiente = new ArrayList<Integer>();
        coeficiente.add(2);
        coeficiente.add(1);
        coeficiente.add(2);
        coeficiente.add(1);
        coeficiente.add(2);
        coeficiente.add(1);
        coeficiente.add(2);
        coeficiente.add(1);
        coeficiente.add(2);
        
        int modulo = 10; //Módulo 10 para Cédula y RUC Natural
        
        
        for (int i=0; i< 9; i++)        //se multiplica solo los 9 primeros dígitos
        {
            ident.set(i, (ident.get(i).intValue() * coeficiente.get(i).intValue())); 
            
            if (ident.get(i).intValue() >= 10)         //Si el producto es >= 10 deben sumarse sus dígitos 
                ident.set(i, (ident.get(i).intValue() - 9)); //14 = 1 + 4 = 5 (14-9 = 5) 
        }

        for(int i=0; i< 9; i++)      //suma de los valores que resultaron de la multiplicación
            suma = suma + ident.get(i).intValue();  //y descomposición
        
        residuo = suma % modulo; //se cálcula el módulo en este caso 10

        if(residuo != 0)        //si el residuo del módulo no es 0 se calcula el digito verificador
            digitoVerificador = modulo - residuo;
        
        if (digitoVerificador == ident.get(9).intValue()) //si el dígito verificador es igual al décimo dígito del
            return true;                   //número de identificación, el # es correcto (true)
        else                               //caso contrario retornamos false
            return false;
	}

}
