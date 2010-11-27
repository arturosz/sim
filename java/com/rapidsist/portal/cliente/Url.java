/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

/**
 * Clase utilitaria que proporciona m�todos para la construcci�n de url's.
 */
public class Url{

	/**
	 * Agrega un par�metro a una direcci�n Url que recibe datos de un formulario, p.e. una forma
	 * de captura de informaci�n (jsp) que env�a datos a un servlet.
	 * @param sUrl Direcci�n Url.
	 * @param sParametro Par�metro.
	 * @return Direcci�n Url con el par�metro agregado en la direcci�n.
	 */
	static public String agregaParametro(String sUrl, String sParametro){
		String sUrlFinal = sUrl;
		//VERIFICA QUE EL PARAMETRO NO SEA NULO
		if (sParametro != null){
			//VERIFICA QUE EL PARAMETRO NO ESTE VACIO
			if (!sParametro.equals("")){
				//BUSCA EN LA URL EL CARACTER "?", EL CUAL INDICA QUE LA URL POR LO MENOS
				//TIENE UN PARAMETRO ASIGNADO
				int iPrimero = sUrl.indexOf("?");
				//VERIFICA SI ENCONTRO EL CARACTER "?"
				if (iPrimero != -1) {
					//AGREGA EL PARAMETRO AL FINAL DE LA LISTA
					sUrlFinal = sUrl + "&" + sParametro;
				}
				else {
					//AGREGA EL PARAMETRO COMO EL INICIAL
					sUrlFinal = sUrl + "?" + sParametro;
				}
			}
		}
		return sUrlFinal;
	}

	/**
	 * Construye la direcci�n URL del servlet ProcesaCatalogo con sus par�metros de operaci�n
	 * y los datos del entorno del usuario.
	 * @param tag TagLib que manda llamar esta clase (generalmente se llama desde el TagLib como
	 * 'this').
	 * @param pageContext Contexto de la p�gina JSP.
	 * @param sFuncion Clave de la funci�n.
	 * @param sOperacion Clave de operacion.
	 * @param sFiltro Filtro de b�squeda.
	 * @param sParametros Par�metros que se le envian al cat�logo.
	 * @return Direcci�n URL
	 */
	public static String getUrlProcesaCatalogo(Tag tag, PageContext pageContext, String sFuncion, String sOperacion, String sFiltro, String sParametros){
		String sUrlFinal ="";
		try {
			String sRutaContexto = ( (HttpServletRequest) pageContext.getRequest()).getContextPath();

			//VERIFICA SI TIENE QUE AGREGAR AL FINAL DE LA URL EL PARAMETRO DE VENTANA
			String sParametroVentana = "";
			String sVentana = pageContext.getRequest().getParameter("Ventana");
			if (sVentana != null) {
				if (sVentana.equals("Si")) {
					sVentana = "&Ventana=Si";
				}
				else{
					sVentana = "";
				}
			}
			else {
				sVentana = "";
			}

			if (!sOperacion.equals("")){
				sOperacion = "&OperacionCatalogo=" + sOperacion;
			}
			if (!sFiltro.equals("")) {
				sFiltro = "&Filtro=" + sFiltro;
			}
			if (!sParametros.equals("")){
				String sParam = (String) ExpressionUtil.evalNotNull("out", "parametros", sParametros + "x", Object.class, tag, pageContext);
				sParametros = "&" + sParam.substring(0, sParam.length() - 1);
			}

			sUrlFinal = sRutaContexto + "/ProcesaCatalogo?Funcion=" + sFuncion + sOperacion + sFiltro + sParametros + sVentana;
		}
		catch (JspException ex) {
			ex.printStackTrace();
			sUrlFinal = "";
		}
		return sUrlFinal;
	}

	/**
	 * Le agrega a la direcci�n url el nombre de la aplicaci�n web y el par�metro de Ventana
	 * si es que viene en el request de la pagina que procesa esta clase.
	 * @param tag TagLib que manda llamar esta clase (generalmente se llama desde el TagLib como
	 * 'this').
	 * @param pageContext Contexto de la p�gina JSP.
	 * @param sUrl Direcci�n URL.
	 * @return Direcci�n URL configurada dependiendo del entorno del usuario.
	 */
	public static String url(Tag tag, PageContext pageContext, String sUrl){
		String sUrlFinal ="";

		try {
			String url = (String) ExpressionUtil.evalNotNull("out", "url", sUrl + "x", Object.class, tag, pageContext);
			url = url.substring(0, url.length() - 1);
			String sRutaContexto = ( (HttpServletRequest) pageContext.getRequest()).getContextPath();

			//VERIFICA SI TIENE QUE AGREGAR AL FINAL DE LA URL EL PARAMETRO DE VENTANA
			String sParametroVentana = "";
			String sVentana = pageContext.getRequest().getParameter("Ventana");
			if (sVentana != null) {
				if (sVentana.equals("Si")) {
					sVentana = "Ventana=Si";
				}
			}
			else {
				sVentana = "";
			}
			sUrlFinal = agregaParametro(sRutaContexto + url, sVentana);
		}
		catch (JspException ex) {
			ex.printStackTrace();
			sUrlFinal = "";
		}
		return sUrlFinal;
	}

}