/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Coloca un separador de datos dentro de una forma de captura.
 * <br><br>
 * Parametros:
 * <ul>
 *	<li>
 * nombre.- T�tulo del separador de datos.
 *  </li>
 * </ul>
 */
public class TagFormaSeparador extends TagSupport {

	String sNombre ="";

	public void setNombre(String sNombre){
		this.sNombre = sNombre;
	}

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		try{
			JspWriter out = pageContext.getOut();
			out.println();
			int iNumRenglonesForma= ((Integer)pageContext.getAttribute("NumRenglonesForma")).intValue();
			if (iNumRenglonesForma == 0){
				out.println("\t\t\t\t\t\t\t\t\t<h4>" + sNombre + "</h4>");
				out.println("\t\t\t\t\t\t\t\t\t<table border='0' cellspacing='2' cellpadding='3'>");
				pageContext.setAttribute("NumRenglonesForma", new Integer(1));
			}
			else{
				out.println("\t\t\t\t\t\t\t\t\t</table>");
				out.println("\t\t\t\t\t\t\t\t\t<h4>" + sNombre + "</h4>");
				out.println("\t\t\t\t\t\t\t\t\t<table border='0' cellspacing='2' cellpadding='3'>");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return(TagSupport.SKIP_BODY);
	}
}