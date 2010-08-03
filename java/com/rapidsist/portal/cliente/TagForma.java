/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import com.rapidsist.comun.bd.Registro;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Imprime el componente forma de captura.
 * <br><br>
 * Par�metros:
 * <ul>
 *	<li>
 * tipo.- Tipo de procesamiento que deber� tener la forma de captura.<br>
 * Posibles valores: url (Env�a el contenido de la forma a la URL especificada en el parametro url),
 *  catalogo (Env�a el contenido de la forma al componente de cat�logos.
 *  </li>
 *	<li>
 * url.- Establece la direcci�n url que deber� procesar el contenido de la forma (este par�metro
 * solo es utilizado si se emplea el par�metro tipo='url')
 *  </li>
 *	<li>
 * funcion.- Clave de la funci�n (este par�metro solo es utilizado si se emplea el par�metro
 * tipo='catalogo')
 *  </li>
 *	<li>
 * operacion.- Clave de la operaci�n a ejecutar sobre el componente de cat�logos.(este par�metro
 *  solo es utilizado si se emplea el par�metro tipo='catalogo')
 *  </li>
 *	<li>
 * filtro.- Filtro de datos para el componente de cat�logos (este par�metro
 *  solo es utilizado si se emplea el par�metro tipo='catalogo')
 *  </li>
 *	<li>
 * parametros.- Par�metros extras para el componente de cat�logos (este par�metro
 *  solo es utilizado si se emplea el par�metro tipo='catalogo')
 *  </li>
 *	<li>
 * agregaentorno.- Indica si se agrega a la direcci�n url el par�metro de Ventana y el nombre
 * de la aplicaci�n web. Este parametro se utiliza cuando deseamos ir a una direcci�n fuera de la
 * aplicaci�n web en donde se ejecuta el portal.<br>
 * Valor por default: true.
 *  </li>
 * </ul>
 */
public class TagForma extends TagSupport {

	String sTipo="";
	String sUrl = "";
	String sFuncion="";
	String sOperacion="";
	String sFiltro="";
	String sParametros ="";
	String sColumnas ="1";
	String sAgregaentorno ="true";
	String sOperacionCatalogo = "";
	String sEnviaArchivos = "false";

	public void setTipo(String sTipo){
		this.sTipo = sTipo;
	}

	public void setUrl(String sUrl){
		this.sUrl = sUrl;
	}

	public void setColumnas(String sTipo){
		this.sTipo = sTipo;
	}

	public void setFuncion(String sFuncion){
		this.sFuncion = sFuncion;
	}

	public void setOperacion(String sOperacion){
		this.sOperacion = sOperacion;
	}

	public void setFiltro(String sFiltro){
		this.sFiltro = sFiltro;
	}

	public void setParametros(String sParametros){
		this.sParametros = sParametros;
	}

	public void setAgregaentorno(String sAgregaentorno){
		this.sAgregaentorno = sAgregaentorno;
	}

	public void setEnviaarchivos(String sEnviaArchivos){
		this.sEnviaArchivos = sEnviaArchivos;
	}

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		int iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
		String sRutaContexto = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
		try {
			sOperacionCatalogo = pageContext.getRequest().getParameter("OperacionCatalogo");

			//INICIALIZA EL CONTADOR DE RENGLONES DENTRO DE LA FORMA
			pageContext.setAttribute("NumRenglonesForma", new Integer(0));
			JspWriter out = pageContext.getOut();
			out.println("");
			out.println("");
			out.println("\t\t\t\t\t\t\t<!--FORMA DE CAPTURA-->");
			if (sTipo.equals("url")){
				if (sAgregaentorno.equals("false")){
					//SI DESEA ENVIAR ARCHIVOS SE LE AGREGA AL FORM LA PROPIEDAD ENCTYPE
					if (sEnviaArchivos.equals("true")){
						out.println("\t\t\t\t\t\t\t\t<form class='FormularioCaptura' name=\"frmRegistro\" method=\"post\" action=\"" + sUrl + " enctype=\"multipart/form-data\">");
					}//TERMINA SI ENVIA ARCHIVOS
					else{
						//SI NO DESEA ENVIAR ARCHIVOS DEBE MOSTRAR EL FORM NORMAL
						out.println("\t\t\t\t\t\t\t\t<form class='FormularioCaptura' name=\"frmRegistro\" method=\"post\" action=\"" + sUrl + "\">");
					}//TERMINA DE OTRA FORMA SI NO DESEA ENVIAR ARCHIVOS
				}
			}
			else{
				//SI DESEA ENVIAR ARCHIVOS SE LE AGREGA AL FORM LA PROPIEDAD ENCTYPE
				if (sEnviaArchivos.equals("true")){
					//OBTIENE EL OBJETO REGISTRO CON EL OBJETIVO DE ESTABLECER LA OPERACION SOBRE
					//EL CATALOGO
					Registro registro = (Registro)pageContext.getRequest().getAttribute("registro");
					if (registro == null) {
						out.println("\t\t\t\t\t\t\t\t<form class='FormularioCaptura' name=\"frmRegistro\" method=\"post\" action=\"" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, sOperacion, sFiltro, sParametros) + "&OperacionCatalogo=AL\" enctype=\"multipart/form-data\">");
					}
					else {
						out.println("\t\t\t\t\t\t\t\t<form class='FormularioCaptura' name=\"frmRegistro\" method=\"post\" action=\"" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, sOperacion, sFiltro, sParametros) + "&OperacionCatalogo=MO\" enctype=\"multipart/form-data\">");
					}
				}//TERMINA SI ENVIA ARCHIVOS
				else{
					//SI NO DESEA ENVIAR ARCHIVOS DEBE MOSTRAR EL FORM NORMAL
					out.println("\t\t\t\t\t\t\t\t<form class='FormularioCaptura' name=\"frmRegistro\" method=\"post\" action=\"" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, sOperacion, sFiltro, sParametros) + "\">");
				}//TERMINA DE OTRA FORMA SI NO DESEA ENVIAR ARCHIVOS
			}//VERIFICA SI EL TIPO DE FORMA ES URL
			if (sTipo.equals("busqueda")){
				out.println("\t\t\t\t\t\t\t\t\t<h4>Filtro de b�squeda</h4>");
				out.println("\t\t\t\t\t\t\t\t\t<table id=\"TablaBusqueda\" border='0' cellspacing='2' cellpadding='3'>");
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return iRespuesta;
	}

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, el cual es disparado por el contenedor
	 * de servlets al finalizar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como debe evaluar el resto
	 * de una p�gina JSP una vez terminado el TagLib.
	 */
	public int doEndTag(){
		try {
			JspWriter out = pageContext.getOut();
			out.println();

			//VERIFICA SI LA FORMA "NO" ES TIPO DE BUSQUEDA
			if (!sTipo.equals("busqueda")){
				//OBTIENE EL OBJETO REGISTRO CON EL OBJETIVO DE ESTABLECER LA OPERACION SOBRE
				//EL CATALOGO
				Registro registro = (Registro)pageContext.getRequest().getAttribute("registro");

				String sCadenaOperacionCatalogo = null;
				if (sOperacionCatalogo != null) {
					if (sOperacionCatalogo.equals("IN") || sOperacionCatalogo.equals("AL")) {
						sCadenaOperacionCatalogo = "\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"OperacionCatalogo\" value=\"AL\">";
					}
				}

				if (sCadenaOperacionCatalogo == null) {
					if (sTipo.equals("catalogo")) {
						if (registro == null) {
							out.println("\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"OperacionCatalogo\" value=\"AL\">");
						}
						else {
							out.println("\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"OperacionCatalogo\" value=\"MO\">");
						}
					}
				}
				else {
					out.println(sCadenaOperacionCatalogo);
				}
			}
			else if (sTipo.equals("busqueda")){
				out.println("\t\t\t\t\t\t\t\t\t</table>");
				out.println("\t\t\t\t\t\t\t\t\t<div class='SeccionBotones'>");
				out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"submit\" name=\"cmdBusqueda\" value=\"B&uacute;squeda\">&nbsp;<input type=\"button\" name=\"cmdLimpia\" value=\"Limpia\" onClick=\"limpia();\" >");
				out.println("\t\t\t\t\t\t\t\t\t</div>");
			}
			out.println("\t\t\t\t\t\t\t\t</form>");
			out.println("\t\t\t\t\t\t\t<br/>");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		return TagSupport.EVAL_PAGE;
	}
}