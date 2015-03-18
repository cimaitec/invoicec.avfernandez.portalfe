package com.general.util;

public class InformacionAdicional
{
  private String valor;
  private String nombre;

  public InformacionAdicional(String valor, String nombre)
  {
    this.valor = valor;
    this.nombre = nombre;
  }

  public InformacionAdicional()
  {
  }

  public String getValor()
  {
    return this.valor;
  }

  public void setValor(String valor)
  {
    this.valor = valor;
  }

  public String getNombre()
  {
    return this.nombre;
  }

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
}