/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.comun.bd.Registro;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.naming.Context;
import java.rmi.RemoteException;

/**
 * Interface que estandariza la forma en que se ejecutan los servicios
 * de consulta de informaci�n a la base de datos para las clases CON. Las operacion soportadas
 * por este m�todo son: Inicializaci�n de datos (CON_INICIALIZACION), consulta de un
 * registro (CON_CONSULTA_REGISTRO) o consulta de un conjunto de registros (CON_CONSULTA_TABLA).
 */
public interface CatalogoControlConsultaIN extends CatalogoControl{
	/**
	 * Activa los servicios de alta, baja o modificaci�n de datos para las clases CON.
	 * @param parametros Par�metros por default para la clase CON.
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un par�metro a este m�todo.
	 * @param response Objeto que asiste al servlet en enviar una respuesta HTTP al cliente. El
	 * contenedor  de servlets crea un objeto HttpServletResponse y lo env�a como par�metro a este m�todo.
	 * @param config Objeto ServletConfig.
	 * @param catalogoSL Interfase remota para el EJB de cat�logos.
	 * @param contexto Objeto Context del servidor de aplicaciones.
	 * @param iTipoOperacion Tipo de operaci�n a realizar sobre la clase CON.
	 * @return Resultado de la consulta de datos y la p�gina a donde se redirecciona el control.
	 * @throws RemoteException Si hubo alg�n error en el EJB de cat�logos.
	 * @throws java.lang.Exception Si hubo alg�n error en la clase controladora.
	 */
	RegistroControl consulta (Registro parametros, HttpServletRequest request, HttpServletResponse response, ServletConfig config, CatalogoSL catalogoSL, Context contexto, int iTipoOperacion) throws RemoteException, Exception;
}