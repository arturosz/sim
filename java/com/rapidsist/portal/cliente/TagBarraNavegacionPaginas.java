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
 * Imprime el componente de barra de navegaci�n entre p�ginas.
 */
public class TagBarraNavegacionPaginas extends TagSupport {

	/**
	 * M�todo estandar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		try{
			JspWriter out = pageContext.getOut();
			out.println();
			out.println("\t\t\t<!--BARRA DE NAVEGACION ENTRE PAGINAS -->");
			out.println("\t\t\t<div id='BarraNavegacion'>");
			//ESTA PENDIENTE POR IMPLEMENTAR ESTA FUNCIONALIDAD
			//out.println("\t\t\t<a href='#'>&nbsp;</a> &gt; Catalogo ");
			out.println("\t\t\t\t&nbsp;");
			out.println("\t\t\t</div>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return(TagSupport.SKIP_BODY);
	}
}