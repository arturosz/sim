/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.rapidsist.portal.configuracion.Permisos;

/**
 * Permite la ejecuci�n del c�digo contenido dentro del taglib si el usuario
 * tiene el permiso de baja de la funci�n especificada en la p�gina.
 */
public class TagPermisoBaja extends TagSupport {

	/**
	 * M�todo estandar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		int iRespuesta = TagSupport.SKIP_BODY;

		Permisos permiso = (Permisos)pageContext.getAttribute("permiso");
		if (permiso.bBaja){
			iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
		}
		return(iRespuesta);
	}

	/**
	 * M�todo est�ndar de la interfaz de TagLibs, el cual es disparado por el contenedor
	 * de servlets al finalizar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como debe evaluar el resto
	 * de una p�gina JSP una vez terminado el TagLib.
	 */
	public int doEndTag(){
		return TagSupport.EVAL_PAGE;
	}
}