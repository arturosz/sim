/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.usuarios.cliente;

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
import java.util.LinkedList;
import java.util.Enumeration;

/**
 * Esta clase se encarga de administrar los servicios de detalle operaci�n (alta, baja,
 * modificaci�n y consulta) del cat�logo de Submarca. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class SimUsuarioSucursalCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
		
		//OBTIENE SOLO EL REGISTRO SOLICITADO
		if (iTipoOperacion == CON_CONSULTA_TABLA){
			//RECUPERA CAMPOS
			parametros.addDefCampo("CVE_USUARIO",request.getParameter("CveUsuario"));
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("SimUsuarioSucursal", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/Usuarios/fSimUsuSuc.jsp?CveUsuario="+request.getParameter("CveUsuario")+"&IdPersona="+request.getParameter("IdPersona");
		}
		
		else if (iTipoOperacion == CatalogoControl.CON_INICIALIZACION){
			//RECUPERA CAMPOS
			if (request.getParameter("Filtro").equals("Alta")){
			
			parametros.addDefCampo("CVE_USUARIO",request.getParameter("CveUsuario"));
			parametros.addDefCampo("ID_SUCURSAL",request.getParameter("IdSucursal"));
			parametros.addDefCampo("CVE_GPO_EMPRESA",request.getParameter("CveGpoEmpresa"));
		    registroControl.respuesta.addDefCampo("ListaSucursal", catalogoSL.getRegistros("SimCatalogoSucursal", parametros));

			registroControl.sPagina = "/Aplicaciones/Sim/CatalogosGenerales/fSimUsuarioSucursalReg.jsp?CveUsuario="+request.getParameter("CveUsuario");	
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
		
		LinkedList listaSucursales = null;
		String sIdSucursal = new String();
		Registro registroSucursal = null;
		
		registro.addDefCampo("CVE_USUARIO", request.getParameter("CveUsuario"));
		
		String[] sIdSucursales = request.getParameterValues("IdSucursal");
		
		if (sIdSucursales != null) {
			for (int iNumParametro = 0; iNumParametro < sIdSucursales.length; iNumParametro++) {
				//VERIFICA SI LA LISTA DE APLICACIONES ESTA INICIALIZADA
				if (listaSucursales == null) {
					listaSucursales = new LinkedList();
				}
				//OBTIENE LA CLAVE DE LA APLICACION
				sIdSucursal = sIdSucursales[iNumParametro];
				
				if (request.getParameter("SucursalAlta" + sIdSucursal) != null) {
					registroSucursal = new Registro();
					registroSucursal.addDefCampo("ID_SUCURSAL", sIdSucursal);
					listaSucursales.add(registroSucursal);
				}else if (request.getParameter("SucursalBaja" + sIdSucursal) != null) {
					registroSucursal = new Registro();
					registroSucursal.addDefCampo("ID_SUCURSAL", sIdSucursal);
					listaSucursales.add(registroSucursal);
				}
			}
		}
		
		if (listaSucursales != null) {
			registro.addDefCampo("ListaSucursales", listaSucursales);

			//DA DE ALTA O BAJA LAS FUNCIONES EN LA BASE DE DATOS
			registroControl.resultadoCatalogo = catalogoSL.modificacion("SimUsuarioSucursal", registro, iTipoOperacion);
		}
		
		registroControl.sPagina = "/ProcesaCatalogo?Funcion=SimUsuarioEmpresa&OperacionCatalogo=CR&CveUsuario="+request.getParameter("CveUsuario")+"&IdPersona="+request.getParameter("IdPersona");
		return registroControl;
	}
}
