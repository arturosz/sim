/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

/**
 * Imprime una direcci�n URL en una p�gina HTML con la confiugraci�n del portal del usuario.
 * <br><br>
 * Par�metros:
 * <ul>
 *	<li>
 * tipo.- Tipo de procesamiento que deber� tener la forma direcci�n URL especificiada en los
 * par�metros.<br>
 * Posibles valores: url (Coloca la direcci�n URL especificada en el par�metro url en la p�gina
 * html), catalogo (Se presenta en la p�gina una referencia con el tag "a"  y se llama al
 * componente de cat�logos si la ventana no fue llamada con el parametro Ventana, de lo
 * contrario se hace una llamada a la funcion RegresaDatos, el cual regresa el control
 * a la ventana padre que hizo la llamada), catalogo-ventana (se abre en una nueva p�gina
 * el cat�logo).
 *  </li>
 *	<li>
 * url.- Direcci�n URL (este par�metro solo es utilizado si se emplea el par�metro tipo='url')
 *  </li>
 *	<li>
 * nombreliga.- Nombre de la liga que aparece en la p�gina.
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
 * parametrosregreso.- Par�metros que se regresan a la ventana padre (solo se utiliza si la
 * p�gina es llamada utilizando el parametro Ventana)
 *  </li>
 *	<li>
 * clonarregistro.- Indica que debe pintar junto a la liga el icono de clonaci�n de registro
 * (este par�metro solo es utilizado si se emplea el par�metro tipo='catalogo'). <br/>
 * Posibles valores: true, false.
 *  </li>
 * </ul>
 */
public class TagUrl extends TagSupport {
	String sTipo = "";
	String sUrl ="";
	String sNombreLiga ="";
	String sFuncion="";
	String sOperacion="";
	String sFiltro="";
	String sParametros ="";
	String sParametrosregreso ="";
	String sNomventana= "ventana";
	boolean bClonarRegistro = false;

	/**
	 * @param sTipo Tipo de procesamiento que deber� tener la forma direcci�n URL especificiada en los
	 * par�metros.
	 */
	public void setTipo(String sTipo){
		this.sTipo = sTipo;
	}
	
	/**
	 * @param sUrl Direcci�n URL.
	 */
	public void setUrl(String sUrl){
		this.sUrl = sUrl;
	}

	/**
	 * @param sOperacion Clave de la operaci�n a ejecutar sobre el componente de cat�logos.
	 */
	public void setOperacion(String sOperacion){
		this.sOperacion = sOperacion;
	}

	/**
	 * @param sFuncion Clave de la funci�n.
	 */
	public void setFuncion(String sFuncion){
		this.sFuncion = sFuncion;
	}

	/**
	 * @param sFiltro Filtro de datos para el componente de cat�logos.
	 */
	public void setFiltro(String sFiltro){
		this.sFiltro = sFiltro;
	}

	/**
	 * @param sLiga Nombre de la liga que aparece en la p�gina.
	 */
	public void setNombreliga(String sLiga){
		this.sNombreLiga = sLiga;
	}

	/**
	 * @param sParametros Par�metros extras para el componente de cat�logos.
	 */
	public void setParametros(String sParametros){
		this.sParametros = sParametros;
	}

	/**
	 * @param sParametrosregreso Par�metros que se regresan a la ventana padre.
	 */
	public void setParametrosregreso(String sParametrosregreso){
		this.sParametrosregreso = sParametrosregreso;
	}

	/**
	 * @param sNomventana Nombre de la ventana.
	 */
	public void setNomventana(String sNomventana){
		this.sNomventana = sNomventana;
	}

	/**
	 * @param sClonarRegistro Indica que debe pintar junto a la liga el icono de clonaci�n de registro.
	 */
	public void setClonarregistro(String sClonarRegistro){
		this.bClonarRegistro = sClonarRegistro.equals("true") ? true : false;
	}

	/**
	 * M�todo estandar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		try{

			String sRutaContexto = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			String sUrlFinal = "";
			String sParam = "";

			if (!sNombreLiga.equals("")){
				String sParamNomLiga = (String) ExpressionUtil.evalNotNull("out", "nombreliga", sNombreLiga + "x", Object.class, this, pageContext);
				sNombreLiga = sParamNomLiga.substring(0, sParamNomLiga.length() - 1);
			}

			JspWriter out = pageContext.getOut();
			//VERIFICA SI EL TIPO ES URL
			if (sTipo.equals("url")){
				out.print( "<a href=\"" + Url.url(this, pageContext, sUrl) + "\">" + sNombreLiga + "</a>");
			}
			else if (sTipo.equals("catalogo")){
				String sVentana = ((HttpServletRequest)pageContext.getRequest()).getParameter("Ventana");
				//VERIFICA SI SE ENVIA A LA PAGINA EL PARAMETRO VENTANA
				if (sVentana != null){
					if (sVentana.equals("Si")) {
						//EVALUAMOS LOS PARAMETROS QUE SE REGRESAN A LA VENTANA PADRE
						sParam = (String) ExpressionUtil.evalNotNull("out", "parametrosregreso", sParametrosregreso + "x", Object.class, this, pageContext);
						sParametrosregreso = sParam.substring(0, sParam.length() - 1);
						sUrlFinal = "<a href=\"javascript:RegresaDatos(" + sParametrosregreso + ")\">" + sNombreLiga + "</a>";
					}
				}

				//VERIFICA SI NO SE ENVIO EL PARAMETRO VENTANA A LA PAGINA JSP.
				if (sUrlFinal.equals("")){
					sUrlFinal = "<a href=\"" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, sOperacion, sFiltro, sParametros) + "\">" + sNombreLiga + "</a>";
					//VERIFICA SI CLONA EL REGISTRO
					if (bClonarRegistro){
						sUrlFinal = "<a href=\"" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, "CL", sFiltro, sParametros) + "\"><img class='ClonarRegistroImg'></a>" + " " + sUrlFinal;
					}
				}
				out.print(sUrlFinal);
			}
			else if (sTipo.equals("catalogo-ventana")){
				String sUrlTemp = Url.url(this, pageContext, sUrl);
				sUrlFinal = "<a href=\"javascript:MM_openBrWindow('" + Url.agregaParametro(sUrlTemp, "Ventana=Si") + "','" + sNomventana + "','scrollbars=yes,resizable=yes,width=1000,height=500') \">" + sNombreLiga + "</a>";
				out.print(sUrlFinal);
			}
			else if (sTipo.equals("regresa-datos")){
				String sVentana = ((HttpServletRequest)pageContext.getRequest()).getParameter("Ventana");
				//VERIFICA SI SE ENVIA A LA PAGINA EL PARAMETRO VENTANA
				if (sVentana != null){
					if (sVentana.equals("Si")) {
						//EVALUAMOS LOS PARAMETROS QUE SE REGRESAN A LA VENTANA PADRE
						sParam = (String) ExpressionUtil.evalNotNull("out", "parametrosregreso", sParametrosregreso + "x", Object.class, this, pageContext);
						sParametrosregreso = sParam.substring(0, sParam.length() - 1);
						sUrlFinal = "<a href=\"javascript:RegresaDatos(" + sParametrosregreso + ")\">" + sNombreLiga + "</a>";
					}
				}
				else{
					sUrlFinal = sNombreLiga;
				}
				out.print(sUrlFinal);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return(TagSupport.SKIP_BODY);
	}
}