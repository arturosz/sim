/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.caja.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.RegistroControl;
import javax.naming.Context;
import javax.servlet.ServletConfig;
import java.rmi.RemoteException;
import java.util.Enumeration;
import com.rapidsist.portal.configuracion.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Esta clase se encarga de administrar las operaciones (alta, baja,
 * modificaci�n y consulta) para pagos en la caja. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class SimCajaConsultaPagarCreditoCON implements CatalogoControlConsultaIN {

	/**
	 * Ejecuta los servicios de consulta del cat�logo.
	 * @param parametros Par�metros que se recogen de la sesion del usuario y se le envian a la clase CON.
	 * Estos par�metros son: CVE_GPO_EMPRESA (Clave del grupo empresa), Filtro (el valor
	 * del filtro que se debe aplicar solo si se ejecuto el componente de cat�logos con
	 * OperacionCatalogo=CT)
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un par�metro a este m�todo.
	 * @param response Objeto que provee de informaci�n del servlet sobre el response del cliente. El
	 * contenedor de servlets crea un objeto HttpServletResponse y lo env�a como un par�metro a este m�todo.
	 * @param config Objeto que provee de informaci�n del servlet sobre el ServletConfig del cliente. El
	 * contenedor de servlets crea un objeto ServletConfig y lo env�a como un par�metro a este m�todo.
	 * @param catalogoSL Instancia del Ejb CatalogoSL que ejecuta en la base de datos las
	 * operaciones especificadas en la clase CON
	 * @param contexto Objeto que contiene informaci�n acerca del entorno del servidor de
	 * aplicaciones.
	 * @param iTipoOperacion Operaci�n que debe ejecutar la clase CON. Las operaciones se encuentran
	 * especificadas en la clase {@link com.rapidsist.portal.cliente.CatalogoControl CatalogoControl}
	 * @return Resultado de la consulta y la p�gina a donde se redirecciona el control.
	 * @throws RemoteException Si se gener� un error en el Ejb CatalogoSL.
	 * @throws java.lang.Exception Si se gener� un error dentro de la clase CON.
	 */
	public RegistroControl consulta(Registro parametros, HttpServletRequest request, HttpServletResponse response, ServletConfig config, CatalogoSL catalogoSL, Context contexto, int iTipoOperacion)throws RemoteException, Exception{
		RegistroControl registroControl = new RegistroControl();
		//VERIFICA SI BUSCA TODOS LOS REGISTROS
		if (iTipoOperacion == CON_CONSULTA_TABLA){
			
			String sIdCaja = "";
			String sIdCajaSucursal = "";
			String sIdSucursal = "";
			
			sIdCajaSucursal = request.getParameter("IdCaja");
			
			sIdSucursal = sIdCajaSucursal.substring(0, sIdCajaSucursal.indexOf("-"));
			sIdCaja = sIdCajaSucursal.substring(sIdCajaSucursal.indexOf("-")+1, sIdCajaSucursal.length());
			
			parametros.addDefCampo("ID_CAJA", sIdCaja);
			parametros.addDefCampo("ID_SUCURSAL", sIdSucursal);
			parametros.addDefCampo("APLICA_A", request.getParameter("AplicaA"));
			
			if (request.getParameter("CvePrestamo") != null && !request.getParameter("CvePrestamo").equals("")){
				parametros.addDefCampo("CVE_PRESTAMO", request.getParameter("CvePrestamo"));
			}
			if (request.getParameter("Nombre") != null && !request.getParameter("Nombre").equals("")){
				parametros.addDefCampo("NOMBRE", request.getParameter("Nombre"));
			}
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("SimCajaConsultaPagarCredito", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaConPagCre.jsp";
		}else if (iTipoOperacion == CON_CONSULTA_REGISTRO){
			parametros.addDefCampo("ID_PRESTAMO", request.getParameter("IdPrestamo"));
			
			
			if (request.getParameter("AplicaA").equals("INDIVIDUAL")){
				parametros.addDefCampo("APLICA_A","INDIVIDUAL");
				registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("SimCajaConsultaPagarCredito", parametros));
				parametros.addDefCampo("CONSULTA","MOVIMIENTOS");
				registroControl.respuesta.addDefCampo("ListaEstadoCuenta", catalogoSL.getRegistros("SimPrestamoEstadoCuenta", parametros));
				parametros.addDefCampo("CONSULTA","SALDO_FECHA");
				registroControl.respuesta.addDefCampo("SaldoFecha", catalogoSL.getRegistros("SimPrestamoEstadoCuenta", parametros));
				parametros.addDefCampo("CONSULTA","RESUMEN");
				registroControl.respuesta.addDefCampo("ListaEstadoCuentaResumen", catalogoSL.getRegistros("SimPrestamoEstadoCuentaResumen", parametros));
				parametros.addDefCampo("CONSULTA","SALDO_TOTAL");
				registroControl.respuesta.addDefCampo("SaldoTotal", catalogoSL.getRegistros("SimPrestamoEstadoCuentaResumen", parametros));
				registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaPagCreInd.jsp?IdCaja="+request.getParameter("IdCaja")+"&FechaMovimiento="+request.getParameter("FechaMovimiento")+"&Respuesta="+request.getParameter("Respuesta");
			}else if (request.getParameter("AplicaA").equals("GRUPO")){
				parametros.addDefCampo("APLICA_A","GRUPO");
				registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("SimCajaConsultaPagarCredito", parametros));
				registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaPagCreGpo.jsp?IdCaja="+request.getParameter("IdCaja")+"&FechaMovimiento="+request.getParameter("FechaMovimiento")+"&Respuesta="+request.getParameter("Respuesta");
			}
		}
		else if (iTipoOperacion == CON_INICIALIZACION){
			if (request.getParameter("Filtro").equals("Inicio")){
				registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaConPagCre.jsp";
			}
		}
		return registroControl;
	}
}