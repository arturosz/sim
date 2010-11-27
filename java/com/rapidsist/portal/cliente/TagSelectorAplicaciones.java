/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import java.util.Iterator;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import com.rapidsist.portal.configuracion.Usuario;
import com.rapidsist.comun.bd.Registro;

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
public class TagSelectorAplicaciones extends TagSupport {

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
			Usuario usuario = (Usuario)pageContext.getSession().getAttribute("Usuario");
			JspWriter out = pageContext.getOut();
			out.println();
			out.println("\t\t\t<table class='tabs'>");
			out.println("\t\t\t\t<tr>");
			out.println("\t\t\t\t\t<td id='Usuario'>Bienvenido: " + usuario.sNomCompleto  + "</td>");
			if (usuario.listaAplicacionesSelector != null){
				Iterator lista = usuario.listaAplicacionesSelector.iterator();
				while (lista.hasNext()){
					Registro aplicacion = (Registro)lista.next();
					String sCveAplicacion = (String)aplicacion.getDefCampo("CVE_APLICACION");
					String sNomAplicacion = (String)aplicacion.getDefCampo("NOM_APLICACION");
					//VERIFICA SI SELECCIONA LA APLICACION ACTUAL DEL USUARIO
					if (sCveAplicacion.equals(usuario.sAplicacionActual)){
						out.println("\t\t\t\t\t<th nowrap><a href='" + sRutaContexto + "/CambiaAplicacion?CveAplicacion=" + sCveAplicacion + "'>" + sNomAplicacion + "</a></th>");
					}
					else{
						out.println("\t\t\t\t\t<td nowrap><a href='" + sRutaContexto + "/CambiaAplicacion?CveAplicacion=" + sCveAplicacion + "'>" + sNomAplicacion + "</a></td>");
					}
				}
			}
			out.println("\t\t\t\t</tr>");
			out.println("\t\t\t</table>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return(TagSupport.SKIP_BODY);
	}
}