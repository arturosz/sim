/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.configuracion;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Almacena la informaci�n relacionada a un usuario.
 */
public class Usuario implements Serializable{

	/**
	 * Clave del usuario.
	 */
	public String sCveUsuario="";

	/**
	 * Nombre completo del usuario.
	 */
	public String sNomCompleto="";

	/**
	 * Clave instituci�n.
	 */
	public String sIdInstitucion;

	/**
	 * Nombre instituci�n.
	 */
	public String sNomInstitucion;

	/**
	 * Clave de la persona.
	 */
	public String sIdPersona="";

	/**
	 * Bandera que indica si el usuario esta autentificado.
	 */
	public boolean bValidado=false;

	/**
	 * Clave de la aplicaci�n que el usuario esta utilizando.
	 */
	public String sAplicacionActual = "";

	/**
	 * Url del encabezado.
	 */
	public String sUrlEncabezado = "";

	/**
	 * Url del pie de pagina.
	 */
	public String sUrlPiePagina = "";

	/**
	 * Url de la pagina del men� aplicaci�n.
	 */
	public String sUrlMenuAplicacion = "";

	/**
	 * Url del inicio de la secci�n contenido de la p�gina.
	 */
	public String sUrlContenidoInicio = "";

	/**
	 * Url del final de la secci�n contenido de la p�gina.
	 */
	public String sUrlContenidoFin = "";

	/**
	 * Nombre ventana browser.
	 */
	public String sNombreVentanaBrowser = "";

	/**
	 * Url de la hoja de estilo.
	 */
	public String sUrlHojaEstilo;
	
	/**
	 * Clave del grupo empresa.
	 */
	public String sCveGpoEmpresa;
	
	/**
	 * Clave de la empresa.
	 */
	public String sCveEmpresa;
	
	/**
	 * Nombre del alias.
	 */
	public String sNomAlias;
	
	/**
	 * Nombre localidad.
	 */
	public String sLocalidad;
	
	/**
	 * Lista de aplicaciones.
	 */
	public LinkedList listaAplicaciones;
	
	/**
	 * Lista de aplicaciones selector.
	 */
	public LinkedList listaAplicacionesSelector;
	
	/**
	 * Configuraci�n de aplicaciones.
	 */
	public HashMap configuracionAplicaciones;
	
	/**
	 * Url del directorio default.
	 */
	public String sUrlDirectorioDefault;
	
	/**
	 * Clave del perfil actual.
	 */
	public String sCvePerfilActual;
	
	/**
	 * Url de la aplicaci�n actual.
	 */
	public String sAplicacionActualUrl;
	
	/**
	 * Url de la aplicaci�n default.
	 */
	public String sAplicacionDefaultUrl;
	
	/**
	 * Selector de aplicaciones.
	 */
	public String sSelectorAplicaciones;
	
	/**
	 * Nombre de la aplici�n web.
	 */
	public String sNomAplicacionWeb;
	
	/**
	 * Clave del portal.
	 */
	public String sCvePortal;
	
	/**
	 * Clave del portal default.
	 */
	public String sCvePortalDefault;
	
	/**
	 * Tipo de letra de la empresa.
	 */
	public String sTipoLetraEmpresa;
	
	/**
	 * Tipo de letra del portal.
	 */
	public String sTipoLetraPortal;
	
	/**
	 * Tipo de letra de la aplicaci�n.
	 */
	public String sTipoLetraAplicacion;
	
	/**
	 * Tipo de letra.
	 */
	public String sTipoLetra;
	
	/**
	 * Nombre ventana browser
	 */
	public boolean bSoloMayusculas;

	/**
	 * Nombre ventana browser
	 */
	public String getUrlDirectorioDefault(){
		return sUrlDirectorioDefault;
	}
	
	/**
	 * Tipo de men�.
	 */
	public String sTipoMenu;
	
	/**
	 * Lista color del men�
	 */
	public LinkedList listaMenuColor;
}