/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.catalogos.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControl;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.CatalogoControlActualizaIN;
import com.rapidsist.portal.cliente.RegistroControl;
import javax.naming.Context;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.rmi.RemoteException;

/**
 * Esta clase se encarga de administrar los servicios de operaci�n (alta, baja,
 * modificaci�n y consulta) del cat�logo de actividades o requisitos. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class SimCatalogoActividadRequisitoCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
				
			if (request.getParameter("IdActividadRequisito") != null && !request.getParameter("IdActividadRequisito").equals("")){
				
				parametros.addDefCampo("ID_ACTIVIDAD_REQUISITO", request.getParameter("IdActividadRequisito"));	
			}
			
			if (request.getParameter("NomActividadRequisito") != null && !request.getParameter("NomActividadRequisito").equals("")){
				
				parametros.addDefCampo("NOM_ACTIVIDAD_REQUISITO", request.getParameter("NomActividadRequisito"));
			}
			
			if (request.getParameter("AplicaA") != null && !request.getParameter("AplicaA").equals("null")){
			
				parametros.addDefCampo("APLICA_A", request.getParameter("AplicaA"));
			}
			
			if (request.getParameter("Tipo") != null && !request.getParameter("Tipo").equals("null")){	
				
				parametros.addDefCampo("TIPO", request.getParameter("Tipo"));
			}
			
			if (request.getParameter("AplicaCiclo") != null && !request.getParameter("AplicaCiclo").equals("null")){
				
				parametros.addDefCampo("APLICA_CICLO", request.getParameter("AplicaCiclo"));
			}
			
			//OBTIENE TODOS LOS REGISTROS DE LA CONSULTA
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("SimCatalogoActividadRequisito", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/CatalogosGenerales/fSimActReqCon.jsp";
		}
		
		else if (iTipoOperacion == CON_CONSULTA_REGISTRO){
			//OBTIENE SOLO EL REGISTRO SOLICITADO
			parametros.addDefCampo("ID_ETAPA_PRESTAMO",request.getParameter("IdEstatusPrestamo"));
			parametros.addDefCampo("ID_ACTIVIDAD_REQUISITO",request.getParameter("IdActividadRequisito"));
			registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("SimCatalogoActividadRequisito", parametros));
			registroControl.sPagina = "/Aplicaciones/Sim/CatalogosGenerales/fSimActReqReg.jsp?IdEstatusPrestamo="+request.getParameter("IdEstatusPrestamo");
		}
		
		if (iTipoOperacion == CON_INICIALIZACION){	
			if (request.getParameter("Filtro").equals("Inicio")){	
				registroControl.sPagina = "/Aplicaciones/Sim/CatalogosGenerales/fSimActReqCon.jsp";
			}
		}
		
		return registroControl;
	}

	/**
	 * Valida los p�rametros entrada y ejecuta los servicios de alta, baja o cambio.
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
		
		registro.addDefCampo("ID_ETAPA_PRESTAMO",request.getParameter("IdEstatusPrestamo"));
		registro.addDefCampo("ID_ACTIVIDAD_REQUISITO",request.getParameter("IdActividadRequisito"));
		registro.addDefCampo("NOM_ACTIVIDAD_REQUISITO",request.getParameter("NomActividadRequisito"));
		registro.addDefCampo("DESCRIPCION",request.getParameter("Descripcion"));
		
		if(request.getParameter("AplicaA").equals("null")){
			registro.addDefCampo("APLICA_A","");
		}else {
			registro.addDefCampo("APLICA_A",request.getParameter("AplicaA"));
		}
		
		if(request.getParameter("Tipo").equals("null")){
			registro.addDefCampo("TIPO","");
		}else {
			registro.addDefCampo("TIPO",request.getParameter("Tipo"));
		}
		
		if(request.getParameter("AplicaCiclo").equals("null")){
			registro.addDefCampo("APLICA_CICLO","");
		}else {
			registro.addDefCampo("APLICA_CICLO",request.getParameter("AplicaCiclo"));
		}
		
		//ACTUALIZA EL REGISTRO EN LA BASE DE DATOS		
		registroControl.resultadoCatalogo = catalogoSL.modificacion("SimCatalogoActividadRequisito", registro, iTipoOperacion);
		registroControl.sPagina = "/ProcesaCatalogo?Funcion=SimCatalogoActividadRequisito&OperacionCatalogo=CT&Filtro=Todos";

		return registroControl;
	}
}
