/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import com.rapidsist.comun.bd.Registro;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.rapidsist.portal.configuracion.Permisos;

/**
 * Permite la ejecuci�n del c�digo contenido dentro del taglib si el usuario
 * tiene los permisos especificados para la funci�n. Para que este tag busque los permisos de
 * la funci�n, esta se deber� precargar utilizando el tag Pagina.
 * <br><br>
 * Parametros:
 * <ul>
 *	<li>
 * funcion.- Clave de la funci�n.
 *  </li>
 *	<li>
 * operacion.- Tipo de operaci�n a verificar sobre la funci�n<br>
 * Posibles valores: consulta, modificacion, alta, baja
 *  </li>
 * </ul>
 */
public class TagPermiso extends TagSupport {
	private String sIdFuncion = "";
	private String sOperacion = "";

	/**
	 * Asigna al TagLib la funci�n que debe verificar permisos.
	 * @param sIdFuncion Clave de funci�n.
	 */
	public void setFuncion(String sIdFuncion){
		this.sIdFuncion = sIdFuncion;
	}

	/**
	 * Asigna la operaci�n que se realiza sobre la funci�n.
	 * @param sOperacion Clave de la operaci�n a realizar ('consulta', 'modificacion', 'alta', 'baja').
	 */
	public void setOperacion(String sOperacion){
		this.sOperacion = sOperacion;
	}

	/**
	 * M�todo estandar de la interfaz de TagLibs, que es disparado por el contenedor
	 * de servlets al comenzar la ejecuci�n del TagLib.
	 * @return Mensaje al contenedor de servlets que indica como manejar el flujo
	 * de ejecuci�n del TagLib y el contenido que pueda existir dentro del tag.
	 * @throws JspException Si hubo alg�n error durante la ejecuci�n del TagLib.
	 */
	public int doStartTag() throws JspException {
		int iRespuesta = TagSupport.SKIP_BODY;

		Registro funcionesPrecargadas = (Registro)pageContext.getAttribute("PermisosFunciones");

		//VERIFICA SI ENCONTRO EL OBJETO DE FUNCIONES PRECARGADAS EN LA PAGINA
		if (funcionesPrecargadas != null){
			Permisos permisoFuncion = (Permisos) funcionesPrecargadas.getDefCampo(this.sIdFuncion);

			//VERIFICA SI ENCONTRO LOS PERMISOS DE LA FUNCION
			if (permisoFuncion != null) {
				//VERIFICA SI TIENEN PERMISO DE CONSULTA
				if (sOperacion.equals("consulta")) {
					if (permisoFuncion.bConsulta) {
						iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
					}
				}
				//VERIFICA SI TIENEN PERMISO DE MODIFICACION
				else if (sOperacion.equals("modificacion")) {
					if (permisoFuncion.bModificacion) {
						iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
					}
				}
				//VERIFICA SI TIENEN PERMISO DE BAJA
				else if (sOperacion.equals("baja")) {
					if (permisoFuncion.bBaja) {
						iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
					}
				}
				//VERIFICA SI TIENEN PERMISO DE ALTA
				else if (sOperacion.equals("alta")) {
					if (permisoFuncion.bAlta) {
						iRespuesta = TagSupport.EVAL_BODY_INCLUDE;
					}
				}
			} //VERIFICA SI ENCONTRO LOS PERMISOS DE LA FUNCION
		}//VERIFICA SI ENCONTRO EL OBJETO DE FUNCIONES PRECARGADAS EN LA PAGINA
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