/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.caja.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.CatalogoControlActualizaIN;
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
 * modificaci�n y consulta) para la entrega de prestamos individuales. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class SimCajaDesembolsoIndividualCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
			parametros.addDefCampo("ID_CAJA_SUCURSAL", sIdCajaSucursal);
			sIdSucursal = sIdCajaSucursal.substring(0, sIdCajaSucursal.indexOf("-"));
			
			parametros.addDefCampo("ID_SUCURSAL", sIdSucursal);
			
			if (request.getParameter("CvePrestamo") != null && !request.getParameter("CvePrestamo").equals("")){
				parametros.addDefCampo("CVE_PRESTAMO", request.getParameter("CvePrestamo"));
			}
			if (request.getParameter("NomCompleto") != null && !request.getParameter("NomCompleto").equals("")){
				parametros.addDefCampo("NOM_COMPLETO", request.getParameter("NomCompleto"));
			}
			
			registroControl.respuesta.addDefCampo("ListaDesembolsoIndividual", catalogoSL.getRegistros("SimCajaDesembolsoIndividual", parametros));
			registroControl.respuesta.addDefCampo("ListaDesembolsoIndividualTransaccion", catalogoSL.getRegistros("SimCajaDesembolsoIndividualTransaccion", parametros));
			
			registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaDesInd.jsp?IdCaja="+request.getParameter("IdCaja");
		}
		else if (iTipoOperacion == CON_INICIALIZACION){
			if (request.getParameter("Filtro").equals("Inicio")){
				registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaDesInd.jsp?IdCaja="+request.getParameter("IdCaja")+"&IdTransaccion="+request.getParameter("IdTransaccion");
			}else if (request.getParameter("Filtro").equals("Alta")){
			
				registroControl.sPagina = "/Aplicaciones/Sim/Caja/fSimCajaMov.jsp?IdCaja="+request.getParameter("IdCaja");
			}
		}
		return registroControl;
	}

	/**
	 * Valida los p�rametros de entrada y ejecuta los servicios de alta, baja o cambio.
	 * @param registro Parametros que se recogen de la sesion del usuario y se le envian a la clase CON.
	 * Estos par�metros son: CVE_GPO_EMPRESA (Clave del grupo empresa), CVE_USUARIO_BITACORA (clave
	 * del usuario que realiza la operacion), RegistroOriginal (registro leido originalmente y
	 * se utiliza cuando se ejecuta la operaci�n de modificacion y se verifica que no se hallan
	 * realizado modificaciones al registro).
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
	 * @return Respuesta del servicio de alta, baja o cambio y la p�gina a donde
	 * se redirecciona el control.
	 * @throws RemoteException Si se gener� un error en el Ejb CatalogoSL.
	 * @throws java.lang.Exception Si se gener� un error dentro de la clase CON.
	 */
	public RegistroControl actualiza(Registro registro, HttpServletRequest request, HttpServletResponse response, ServletConfig config, CatalogoSL catalogoSL, Context contexto, int iTipoOperacion)throws RemoteException, Exception{
		RegistroControl registroControl = new RegistroControl();

		Registro monto = new Registro();
		String sMonto = "";
		
		String sIdCaja = "";
		String sIdCajaSucursal = "";
		String sIdSucursal = "";
		String sIdTransaccion = "";
		
		registro.addDefCampo("ID_PRESTAMO", request.getParameter("IdPrestamo"));
		
		//monto = catalogoSL.getRegistro("SimCajaDesembolsoIndividual", registro);
		
		//sMonto = (String)monto.getDefCampo("MONTO_AUTORIZADO");
		
		//RECUPERA LA SESION DEL USUARIO
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("Usuario");
		//AGREGA LA CLAVE DEL PORTAL Y DEL USUARIO DE LA SESION DEL USUARIO
		registro.addDefCampo("CVE_USUARIO_CAJERO", usuario.sCveUsuario);
		//registro.addDefCampo("MONTO", sMonto);
		
		sIdCajaSucursal = request.getParameter("IdCaja");
		
		sIdSucursal = sIdCajaSucursal.substring(0, sIdCajaSucursal.indexOf("-"));
		sIdCaja = sIdCajaSucursal.substring(sIdCajaSucursal.indexOf("-")+1, sIdCajaSucursal.length());
		
		registro.addDefCampo("ID_CAJA", sIdCaja);
		registro.addDefCampo("ID_SUCURSAL", sIdSucursal);
		registro.addDefCampo("CVE_MOVIMIENTO_CAJA","DESIND");
		
		registroControl.resultadoCatalogo = catalogoSL.modificacion("SimCajaDesembolsoIndividual", registro, iTipoOperacion);
		sIdTransaccion = (String) registroControl.resultadoCatalogo.Resultado.getDefCampo("ID_TRANSACCION");
		
		registroControl.sPagina = "/ProcesaCatalogo?Funcion=SimCajaDesembolsoIndividual&OperacionCatalogo=CT&Filtro=Todos&IdCaja="+request.getParameter("IdCaja")+"&IdTransaccion="+sIdTransaccion;
		return registroControl;
	}
}