/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */


package com.rapidsist.portal.configuracion;

import java.io.Serializable;

/**
 * Almacena los atributos de una opci�n de un men�.
 */
public class MenuOpcion implements Serializable{

	/**
	 * Clave de opci�n de men�.
	 */
	public String sIdOpcion = "";

	/**
	 * Clave de funci�n asociada a la opci�n.
	 */
	public String sIdFuncion = "";

	/**
	 * Nombre de la opci�n en el men�.
	 */
	public String sNomOpcion = "";

	/**
	 * Direcci�n a la que apunta la opci�n del men�.
	 */
	public String sUrl = "";

	/**
	 * Bandera que informa si la opci�n est� bloqueada.
	 */
	public String sBloqueado = "";

	/**
	 * Clave de la opci�n padre.
	 */
	public String sIdOpcionPadre = "";

	/**
	 * Clave de la aplicaci�n asociada a la opci�n.
	 */
	public String sIdAplicacion  = "";

	/**
	 * Posici�n de la opci�n dentro del men�.
	 */
	public String sNumPosicion   = "";

	/**
	 * Clave del folio de la bit�cora.
	 */
	public String sFolBitacora   = "";

	/**
	 * Situaci�n del registro.
	 */
	public String sSituacion     = "";

	/**
	 * Constructor del objeto MenuOpcion.
	 * @param sIdOpcion Clave opcion menu.
	 * @param sIdFuncion Clave funci�n.
	 * @param sNomOpcion Nombre de la opci�n.
	 * @param sUrl Direcci�n URL.
	 */
	public MenuOpcion(String sIdOpcion, String sIdFuncion, String sNomOpcion, String sUrl){
		this.sIdOpcion = sIdOpcion;
		this.sIdFuncion = sIdFuncion;
		this.sNomOpcion = sNomOpcion;
		this.sUrl = sUrl;
	}
}