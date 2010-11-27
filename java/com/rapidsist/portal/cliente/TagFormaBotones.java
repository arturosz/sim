/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Taglib que especifica el inicio y el fin de la zona de botones para la forma de captura de
 * datos.
 */
public class TagFormaBotones extends TagSupport {

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		int iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
		try {
			JspWriter out = pageContext.getOut();
			out.println();

			//String sTagPadre = this.getParent().getClass().getName();
			out.println("\t\t\t\t\t\t\t\t\t</table>");
			out.println("\t\t\t\t\t\t\t\t\t<div class='SeccionBotones'>");
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
			out.println("\t\t\t\t\t\t\t\t\t</div>");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return TagSupport.EVAL_PAGE;
	}
}