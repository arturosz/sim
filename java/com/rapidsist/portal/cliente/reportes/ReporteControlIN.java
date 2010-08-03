/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente.reportes;

import java.util.Map;
import com.rapidsist.portal.catalogos.CatalogoSL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.naming.Context;
import com.rapidsist.comun.bd.Registro;

public interface ReporteControlIN {
	
	/**
	 *
	 * @param parametrosCatalogo Objeto Registro.
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un par�metro a este m�todo.
	 * @param catalogoSL Interfase remota para el EJB de cat�logos.
	 * @param contextoServidor Objeto tipo Context.
	 * @param contextoServlet  Objeto ServletContext.
	 * @throws java.lang.Exception Si hubo alg�n error en la clase controladora.
	 */

	public Map getParametros(Registro parametrosCatalogo, HttpServletRequest request, CatalogoSL catalogoSL, Context contextoServidor, ServletContext contextoServlet) throws Exception;
}