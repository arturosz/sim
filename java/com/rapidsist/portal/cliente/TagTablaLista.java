/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.configuracion.Permisos;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Imprime una tabla html que muestra el resultado de una consulta.
 * <br><br>
 * Parametros:
 * <ul>
 *	<li>
 * maestrodetallefuncion.- Clave de la funci�n que representa la tabla. Indica que esta tabla
 * es parte de un p�gina tipo maestro-detalle, por lo
 * que si se encuentra en modo de alta o inicializaci�n (OperacionCatalogo=AL o IN) no se
 * mostrar� el contenido de la tabla, as� tambi�n si el usuario no tiene los sufientes
 * permisos no mostrar� la informaci�n. Para que evaluaci�n de los permisos sobre la clave
 * de funci�n se ejecute, esta funci�n deber� ser precargada en el tag Pagina. Este atributo
 * puede ir vaci� por lo que no se considerar� la tabla como maestro-detalle.<br>
 *  </li>
 *	<li>
 * tipo.- Tipo de tabla a pintar.<br>
 * Posibles valores: Alta (Coloca el bot�n de alta si el usuario tiene permisos de alta
 * para la funci�n.), consulta (Suprime la regi�n para los botones).
 *  </li>
 *	<li>
 * botontipo.- Define la operaci�n que se ejecuta al pintar la barra de botones de la tabla.<br>
 * Posibles valores: catalogo (ejecuta el componente de cat�logos)
 *  </li>
 *	<li>
 * nombre.- T�tulo de la tabla.
 *  </li>
 *	<li>
 * funcion.- Clave de la funci�n (este par�metro solo es utilizado si se emplea el par�metro
 * botontipo='catalogo')
 *  </li>
 *	<li>
 * operacion.- Clave de la operaci�n a ejecutar sobre el componente de cat�logos.(este par�metro
 *  solo es utilizado si se emplea el par�metro botontipo='catalogo')
 *  </li>
 *	<li>
 * filtro.- Filtro de datos para el componente de cat�logos (este par�metro
 *  solo es utilizado si se emplea el par�metro botontipo='catalogo')
 *  </li>
 *	<li>
 * parametros.- Par�metros extras para el componente de cat�logos (este par�metro
 *  solo es utilizado si se emplea el par�metro botontipo='catalogo')
 *  </li>
 * </ul>
 */
public class TagTablaLista extends TagSupport {

	String sMaestroDetalleFuncion ="";
	String sTipo="";
	String sBotontipo="";
	String sNombre="";
	String sFuncion="";
	String sOperacion="";
	String sFiltro="";
	String sUrl= "";
	String sParametros ="";
	int iRespuesta =0;
	Permisos permisoFuncion = null;

	/**
	 * @param sMaestroDetalleFuncion Clave de la funci�n que representa la tabla
	 */
	public void setMaestrodetallefuncion(String sMaestroDetalleFuncion){
		this.sMaestroDetalleFuncion = sMaestroDetalleFuncion;
	}

	/**
	 * @param sTipo Tipo de tabla a pintar.
	 */
	public void setTipo(String sTipo){
		this.sTipo = sTipo;
	}

	/**
	 * @param sBotontipo Bot�n.
	 */
	public void setBotontipo(String sBotontipo){
		this.sBotontipo = sBotontipo;
	}

	/**
	 * @param sNombre T�tulo de la tabla.
	 */
	public void setNombre(String sNombre){
		this.sNombre = sNombre;
	}

	/**
	 * @param sFuncion Clave de la funci�n.
	 */
	public void setFuncion(String sFuncion){
		this.sFuncion = sFuncion;
	}

	/**
	 * @param sOperacion Clave de la operaci�n a ejecutar sobre el componente de cat�logos.
	 */
	public void setOperacion(String sOperacion){
		this.sOperacion = sOperacion;
	}

	/**
	 * @param sFiltro Filtro de datos para el componente de cat�logos.
	 */
	public void setFiltro(String sFiltro){
		this.sFiltro = sFiltro;
	}

	/**
	 * @param sUrl Url.
	 */
	public void setUrl(String sUrl){
		this.sUrl = sUrl;
	}

	/**
	 * @param sParametros Par�metros extras para el componente de cat�logos.
	 */
	public void setParametros(String sParametros){
		this.sParametros = sParametros;
	}

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
		try {
			//VERIFICA EL TIPO DE TABLA Y LOS PERMISOS DE EJECUCION
			if (!sMaestroDetalleFuncion.equals("")){
				String sOperacionCatalogo =pageContext.getRequest().getParameter("OperacionCatalogo");
				//VERIFICA SI LA PAGINA TIENE EL ATRIBUTO OperacionCatalogo
				if (sOperacionCatalogo != null){
					//VERIFICA SI ES ALTA O INICILIZACION
					if (sOperacionCatalogo.equals("AL") || sOperacionCatalogo.equals("IN")){
						iRespuesta= TagSupport.SKIP_BODY;
					}
					else{
						Registro funcionesPrecargadas = (Registro)pageContext.getAttribute("PermisosFunciones");
						//VERIFICA SI ENCONTRO EL OBJETO DE FUNCIONES PRECARGADAS EN LA PAGINA
						if (funcionesPrecargadas != null){
							permisoFuncion = (Permisos) funcionesPrecargadas.getDefCampo(sMaestroDetalleFuncion);
							if (permisoFuncion != null){
								if (permisoFuncion.bConsulta == false) {
									iRespuesta = TagSupport.SKIP_BODY;
								}
							}
							else{
								iRespuesta = TagSupport.SKIP_BODY;
							}
						}
						else{
							iRespuesta= TagSupport.SKIP_BODY;
						}//VERIFICA SI ENCONTRO EL OBJETO DE FUNCIONES PRECARGADAS EN LA PAGINA
					}//VERIFICA SI ES ALTA O INICILIZACION
				}//VERIFICA SI LA PAGINA TIENE EL ATRIBUTO OperacionCatalogo
			}//VERIFICA EL TIPO DE TABLA Y LOS PERMISOS DE EJECUCION

			//VERIFICA SI SE PUEDE IMPRIMIR LA TABLA
			if (iRespuesta ==TagSupport.EVAL_BODY_INCLUDE){
				JspWriter out = pageContext.getOut();
				out.println();
				if(!sNombre.equals("")){
					out.println("\t\t\t\t\t\t\t\t<h3>" + sNombre + "</h3>");
				}
				out.println("\t\t\t\t\t\t\t<table border='0'>");
				pageContext.setAttribute("PintaRenglonColor", new Boolean(false));
			}//VERIFICA SI SE PUEDE IMPRIMIR LA TABLA
		}
		catch (Exception ex) {
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
			if (iRespuesta == TagSupport.SKIP_BODY){
				return TagSupport.EVAL_PAGE;
			}

			JspWriter out = pageContext.getOut();
			if (sTipo.equals("alta")){
				out.println();
				out.println("\t\t\t\t\t\t\t</table>");
				out.println("\t\t\t\t\t\t\t<div class='SeccionBotones'>");

				//IMPRIME LOS CONTROLES DE NAVEGACION DE LA TABLA

				boolean bImprimeBoton = true;
				//VERIFICA SI LA TABLA ES MAESTRO-DETALLE
				if (!sMaestroDetalleFuncion.equals("")){
					if (!permisoFuncion.bAlta){
						bImprimeBoton = false;
					}
				}
				else{
					Permisos permiso = (Permisos)pageContext.getAttribute("permiso");
					if (!permiso.bAlta){
						bImprimeBoton = false;
					}
				}

				//IMPRIME EL BOTON DE ALTA
				if(bImprimeBoton){
					if (sBotontipo.equals("catalogo")) {
						out.println("\t\t\t\t\t\t\t\t<input type=\"button\" name=\"btnOperaciones\" value=\"Alta\" onClick=\"MM_goToURL('parent','" + Url.getUrlProcesaCatalogo(this, pageContext, sFuncion, sOperacion, sFiltro, sParametros) + "')\" >");
					}
					else {
						out.println("\t\t\t\t\t\t\t\t<input type=\"button\" name=\"btnOperaciones\" value=\"Alta\" onClick=\"MM_goToURL('parent','" + Url.url(this, pageContext, sUrl) + "')\" >");
					}
				}

				out.println("\t\t\t\t\t\t\t</div>");
			}
			else{
				out.println("\t\t\t\t\t\t\t</table>");
			}
			out.println("\t\t\t\t\t\t\t<br>");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return TagSupport.EVAL_PAGE;
	}
}