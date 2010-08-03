/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.configuracion;

import java.util.LinkedList;
import java.io.Serializable;

public class AplicacionUsuario implements Serializable{

	/**
	 * Clave de la aplicaci�n.
	 */
	public String sCveAplicacion="";
	
	/**
	 * Nombre de la aplicaci�n.
	 */
	public String sNomAplicacon ="";
	
	/**
	 * Url de la aplicaci�n.
	 */
	public String sUrlAplicacion="";
	
	/**
	 * Lista de perfiles.
	 */
	public LinkedList listaPerfiles = new LinkedList();
}
