/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.configuracion;

import java.io.Serializable;

public class Pagina implements Serializable{

	/**
	 * Clave de la p�gina.
	 */
	public String sCvePagina;
	
	/**
	 * Clave de la fuci�n.
	 */
	public String sCveFuncion;
	
	/**
	 * Nombre de la p�gina.
	 */
	public String sNomPagina;
	
	/**
	 * Bit�cora de la p�gina.
	 */
	public String sBitacoraPagina;

	public Pagina() {
	}
	public String getCvePagina(){
		return sCvePagina;
	}
	public void setCvePagina(String sCvePagina){
		this.sCvePagina = sCvePagina;
	}
	public String getCveFuncion(){
		return sCveFuncion;
	}
	public void setCveFuncion(String sCveFuncion){
		this.sCveFuncion = sCveFuncion;
	}
	public String getNomPagina(){
		return sNomPagina;
	}
	public void setNomPagina(String sNomPagina){
		this.sNomPagina = sNomPagina;
	}
	public String getBitacoraPagina(){
		return sBitacoraPagina;
	}
	public void setBitacoraPagina(String sBitacoraPagina){
		this.sBitacoraPagina = sBitacoraPagina;
	}



}
