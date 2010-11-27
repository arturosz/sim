/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */
package com.rapidsist.publicaciones.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.CatalogoControlActualizaIN;
import com.rapidsist.portal.cliente.RegistroControl;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.rmi.RemoteException;
import javax.servlet.http.HttpSession;
import com.rapidsist.portal.configuracion.Usuario;


/**
 * Esta clase se encarga de administrar las operaciones (alta, baja,
 * modificaci�n y consulta) del cat�logo de personas. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class PubEditorHtmlCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
	 * especificadas en la clase {@link com.rap|sist.portal.cliente.CatalogoControl CatalogoControl}
	 * @return Resultado de la consulta y la p�gina a donde se redirecciona el control.
	 * @throws RemoteException Si se gener� un error en el Ejb CatalogoSL.
	 * @throws java.lang.Exception Si se gener� un error dentro de la clase CON.
	 */
	public RegistroControl consulta(Registro parametros, HttpServletRequest request, HttpServletResponse response, ServletConfig config, CatalogoSL catalogoSL, Context contexto, int iTipoOperacion)throws RemoteException, Exception{
		RegistroControl registroControl = new RegistroControl();

		//VERIFICA SI BUSCA TODOS LOS REGISTROS
		if (iTipoOperacion == CON_CONSULTA_TABLA){

			//VERIFICA SI SE ENVIO EL PARAMETRO CVE PUBLICACION
			if (request.getParameter("CvePubliHtml") != null && !request.getParameter("CvePubliHtml").equals("")){
				parametros.addDefCampo("CVE_PUB_HTML", request.getParameter("CvePubliHtml"));
			}

			//VERIFICA SI SE ENVIO EL PARAMETRO NOMBRE PUBLICACION
			if (request.getParameter("NomPublHtml") != null && !request.getParameter("NomPublHtml").equals("")){
				parametros.addDefCampo("NOM_PUB_HTML", request.getParameter("NomPublHtml"));
			}
			
			//INDICA QUE VA A LA DAO PARA HACER LA CONSULTA
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("PublicacionesEditorHTML", parametros));
			
			//INDICA A DONDE IR� AL TERMINAR LA CONSULTA
			registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublEditorHtmlCon.jsp";

		}
		
		else if (iTipoOperacion == CON_INICIALIZACION){
			
			//DIRECCCIONA A LA JSP DONDE SE MOSTRAR� LA PANTALLA DE CAPTRUA
			registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublEditorHtmlReg.jsp";
		}
		
		else if (iTipoOperacion == CON_CONSULTA_REGISTRO){

			//OBTIENE SOLO EL REGISTRO SOLICITADO
			parametros.addDefCampo("CVE_PUB_HTML",request.getParameter("CvePubliHtml"));
			registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("PublicacionesEditorHTML", parametros));
			registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublEditorHtmlReg.jsp";

		}

		return registroControl;
	}

	/**
	 * ValCvea los p�rametros de entrada y ejecuta los servicios de alta, baja o cambio.
	 * @param registro Parametros que se recogen de la sesion del usuario y se le envian a la clase CON.
	 * Estos par�metros son: CVE_GPO_EMPRESA (Clave del grupo empresa), CVE_USUARIO_BITACORA (clave
	 * del usuario que realiza la operacion), RegistroOriginal (registro leCveo originalmente y
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
	 * @param contexto Objeto que contiene informaci�n acerca del entorno del servCveor de
	 * aplicaciones.
	 * @param iTipoOperacion Operaci�n que debe ejecutar la clase CON. Las operaciones se encuentran
	 * especificadas en la clase {@link com.rapCvesist.portal.cliente.CatalogoControl CatalogoControl}
	 * @return Respuesta del servicio de alta, baja o cambio y la p�gina a donde
	 * se redirecciona el control.
	 * @throws RemoteException Si se gener� un error en el Ejb CatalogoSL.
	 * @throws java.lang.Exception Si se gener� un error dentro de la clase CON.
	 */
	
	public RegistroControl actualiza(Registro registro, HttpServletRequest request, HttpServletResponse response, ServletConfig config, CatalogoSL catalogoSL, Context contexto, int iTipoOperacion)throws RemoteException, Exception{
	RegistroControl registroControl = new RegistroControl();
	
		//OBTIENE LOS DATOS DEL USUARIO
		HttpSession session = request.getSession(true);
		Usuario usuario = (Usuario)session.getAttribute("Usuario");
		String sCvePortal = usuario.sCvePortal;
		registro.addDefCampo("CVE_PORTAL", sCvePortal);

		//OBTIENE PARAMETROS DE LA PUBLICACION HTML
		
		//OBTIENE LA CVE PUBLICACI�N
		registro.addDefCampo("CVE_PUB_HTML", request.getParameter("CvePubHtml")!= null ? request.getParameter("CvePubHtml") : "");
	
		//OBTIENE EL NOMBRE PUBLICACION
		registro.addDefCampo("NOM_PUB_HTML", request.getParameter("NomPubHtml")!= null ? request.getParameter("NomPubHtml") : "");
		
		//OBTIENE EL C�DIGO HTML
		registro.addDefCampo("CODIGO_HTML", request.getParameter("CodigoHtml")!= null ? request.getParameter("CodigoHtml") : "");
		String sCveGpoEmpresa = request.getParameter("CveGpoEmpresa");
		
		//INDICA A DONDE IR� AL TERMINAR LA CONSULTA
		registroControl.sPagina = "/ProcesaCatalogo?Funcion=PublicacionesEditorHTML&OperacionCatalogo=CT&CveGpoEmpresa=" + sCveGpoEmpresa + "&Filtro=SinFiltro";

		//ACTUALIZA EL REGISTRO EN LA BASE DE DATOS
		registroControl.resultadoCatalogo = catalogoSL.modificacion("PublicacionesEditorHTML", registro, iTipoOperacion);
		return registroControl;
	}
}