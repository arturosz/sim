/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import com.rapidsist.portal.configuracion.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;


/**
 * Servlet que transfiere el control a la p�gina de inicio del portal por default.
 */
public class InicioS extends HttpServlet {

	/**
	 * Transfiere el control a la p�gina de inicio del portal por default.
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un par�metro a este m�todo.
	 * @param response Objeto que asiste al servlet en enviar una respuesta HTTP al cliente. El
	 * contenedor  de servlets crea un objeto HttpServletResponse y lo env�a como par�metro a este m�todo.
	 */
	public void service (HttpServletRequest request, HttpServletResponse response){

		try{
			//SE VERIFICARA SI HA CADUCADO LA SESION DEL USUARIO
			HttpSession session = SesionUsuario.compruebaSesionUsuario(request, response);
			if (session == null){
				return;
			}

			//VERIFICA SI YA SE CARGO LA CONFIGURACION DEL USUARIO
			Usuario usuario = (Usuario)session.getAttribute("Usuario");
			if (usuario != null) {
				response.sendRedirect(request.getContextPath() + usuario.sAplicacionDefaultUrl);
			}//VERIFICA SI YA SE CARGO LA CONFIGURACION DEL USUARIO
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
