/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.comite.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControl;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.CatalogoControlActualizaIN;
import com.rapidsist.portal.cliente.RegistroControl;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * Esta clase se encarga de administrar los servicios de detalle operaci�n (alta, baja,
 * modificaci�n y consulta) de los montos por cliente del pr�stamo. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class SimComiteClienteMontoEstatusCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
		//OBTIENE SOLO EL REGISTRO SOLICITADO
		if (iTipoOperacion == CON_CONSULTA_TABLA){
			parametros.addDefCampo("ID_PRESTAMO",request.getParameter("IdPrestamo"));
			parametros.addDefCampo("ID_PRODUCTO",request.getParameter("IdProducto"));
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("SimPrestamoMontoCliente", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/Prestamo/fSimPreMonCliCon.jsp?IdPrestamo="+request.getParameter("IdPrestamo")+"&IdProducto="+request.getParameter("IdProducto");	
		}
		else if (iTipoOperacion == CON_CONSULTA_REGISTRO){
			//RECUPERA CAMPOS
			parametros.addDefCampo("ID_PRODUCTO",request.getParameter("IdProducto"));
			parametros.addDefCampo("ID_PRESTAMO",request.getParameter("IdPrestamo"));
			parametros.addDefCampo("ID_ACTIVIDAD_REQUISITO",request.getParameter("IdActividadRequisito"));
			registroControl.respuesta.addDefCampo("ListaEstatusPrestamo", catalogoSL.getRegistros("SimCatalogoEstatusPrestamo", parametros));
			registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("SimPrestamoActividadRequisito", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/Prestamo/fSimPreActReqReg.jsp?IdPrestamo="+request.getParameter("IdPrestamo")+"&IdProducto="+request.getParameter("IdProducto");
		}
		else if (iTipoOperacion == CatalogoControl.CON_INICIALIZACION){
			//RECUPERA CAMPOS
			if (request.getParameter("Filtro").equals("Alta")){
				parametros.addDefCampo("ID_PERSONA",request.getParameter("IdPersona"));
				registroControl.respuesta.addDefCampo("ListaPeriodicidad", catalogoSL.getRegistros("SimCatalogoPeriodicidad", parametros));
				registroControl.sPagina = "/Aplicaciones/Sim/Personas/fSimCliAdeReg.jsp?IdPersona="+request.getParameter("IdPersona");	
			}
		}
		return registroControl;
	}

	/**
	 * Valida los p�rametros entrada y ejecuta los servicios de alta, baja o cambio.
	 * @param registro Parametros que se recogen de la sesion del usuario y se le envian a la clase CON.
	 * Estos p�rametros son: CVE_GPO_EMPRESA (Clave del grupo empresa), CVE_USUARIO_BITACORA (clave
	 * del usuario que realiza la operacion), RegistroOriginal (registro leido originalmente y
	 * se utiliza cuando se ejecuta la Operaci�n de modificacion y se verifica que no se hallan
	 * realizado modificaciones al registro).
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un p�rametro a este m�todo.
	 * @param response Objeto que provee de informaci�n del servlet sobre el response del cliente. El
	 * contenedor de servlets crea un objeto HttpServletResponse y lo env�a como un p�rametro a este m�todo.
	 * @param config Objeto que provee de informaci�n del servlet sobre el ServletConfig del cliente. El
	 * contenedor de servlets crea un objeto ServletConfig y lo env�a como un p�rametro a este m�todo.
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
		
		LinkedList listaMontos = null;
		String sMontoAutorizado = new String();
		String sIdEstatusCliente = new String();
		String sCliente = new String();
		Enumeration lista = request.getParameterNames();

		//OBTIENE EL ARREGLO CON LAS APLICACIONES A PROCESAR
		String[] sMontos = request.getParameterValues("MontoAutorizado");
		String[] sIdEstatus = request.getParameterValues("Estatus");
		String[] sIdCliente = request.getParameterValues("IdCliente");
		//VERIFICA SI ENCONTRO EL ARREGLO DE APLICACIONES
		if (sMontos != null) {
			
			for (int iNumParametro = 0; iNumParametro < sMontos.length; iNumParametro++) {
				//VERIFICA SI LA LISTA DE APLICACIONES ESTA INICIALIZADA
				if (listaMontos == null) {
					listaMontos = new LinkedList();
				}
				//OBTIENE LA CLAVE DE LA APLICACION
				sMontoAutorizado = sMontos[iNumParametro];
				sIdEstatusCliente = sIdEstatus[iNumParametro];
				sCliente = sIdCliente[iNumParametro];
				
				registro.addDefCampo("MONTO_AUTORIZADO",sMontoAutorizado);
				registro.addDefCampo("ESTATUS",sIdEstatusCliente);
				registro.addDefCampo("ID_CLIENTE",sCliente);
				registro.addDefCampo("ID_PRESTAMO", request.getParameter("IdPrestamo"));
				
				//ACTUALIZA EL REGISTRO EN LA BASE DE DATOS
				registroControl.resultadoCatalogo = catalogoSL.modificacion("SimComiteClienteMontoEstatus", registro, 1);//MODIFICACION	
			}
		}
		
		//INDICA A DONDE IRA AL TERMINAR LA ACTUALIZACION
		registroControl.sPagina = "/ProcesaCatalogo?Funcion=SimComitePrestamo&OperacionCatalogo=CR&IdPrestamo="+request.getParameter("IdPrestamo");
		
		
		return registroControl;
	}
}
