/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (0c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.publicaciones.cliente;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.CatalogoSL;
import com.rapidsist.portal.cliente.CatalogoControlConsultaIN;
import com.rapidsist.portal.cliente.CatalogoControlActualizaIN;
import com.rapidsist.portal.cliente.RegistroControl;
import com.rapidsist.portal.configuracion.Usuario;
import com.rapidsist.publicaciones.datos.PublicacionSL;
import com.rapidsist.publicaciones.datos.PublicacionSLHome;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.naming.InitialContext;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

/**
 * Esta clase se encarga de administrar los servicios de operaci�n (alta, baja,
 * modificaci�n y consulta) del cat�logo de secciones. Esta clase es llamada por
 * el servlet {@link CatalogoS CatalogoS}.
 */
public class PublSeccionCON implements CatalogoControlConsultaIN, CatalogoControlActualizaIN{

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
		//RECUPERA LA SESION DEL USUARIO
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("Usuario");
		//AGREGA LA CLAVE DEL PORTAL Y DEL USUARIO DE LA SESION DEL USUARIO
		parametros.addDefCampo("CVE_PORTAL", usuario.sCvePortal);
		parametros.addDefCampo("CVE_USUARIO", usuario.sCveUsuario);
		boolean bParametrosFiltro = false;
		//VERIFICA SI BUSCA TODOS LOS REGISTROS
		if (iTipoOperacion == CON_CONSULTA_TABLA){
			//VERIFICA SI SE ENVIO EL PARAMETRO CATEGORIA
			//OBTIENE TODOS LOS REGISTROS DE LA CONSULTA
			registroControl.respuesta.addDefCampo("ListaGrupoResponsable", catalogoSL.getRegistros("PublicacionesGrupoResponsable", parametros));
			
			registroControl.respuesta.addDefCampo("ListaBusqueda", catalogoSL.getRegistros("PublicacionesSeccion", parametros));
			
			registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublSeccionCon.jsp";
		}
		else if (iTipoOperacion == CON_CONSULTA_REGISTRO){
			//OBTIENE SOLO EL REGISTRO SOLICITADO
			parametros.eliminaCampo("Filtro");
			parametros.addDefCampo("Filtro", "Total");
			parametros.addDefCampo("CVE_SECCION",request.getParameter("CveSeccion"));
			registroControl.respuesta.addDefCampo("ListaGrupoResponsable", catalogoSL.getRegistros("PublicacionesGrupoResponsable", parametros));
			registroControl.respuesta.addDefCampo("ListaSeccion", catalogoSL.getRegistros("PublicacionesSeccion", parametros));
			registroControl.respuesta.addDefCampo("registro", catalogoSL.getRegistro("PublicacionesSeccion", parametros));
			registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublSeccionReg.jsp";
		}
		else if (iTipoOperacion == CON_INICIALIZACION){
			if (request.getParameter("Filtro").equals("Alta")){
				parametros.eliminaCampo("Filtro");
				parametros.addDefCampo("Filtro", "Total");
				registroControl.respuesta.addDefCampo("ListaGrupoResponsable", catalogoSL.getRegistros("PublicacionesGrupoResponsable", parametros));
				registroControl.respuesta.addDefCampo("ListaSeccion", catalogoSL.getRegistros("PublicacionesSeccion", parametros));
				registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublSeccionReg.jsp";
			}else{
				registroControl.respuesta.addDefCampo("ListaGrupoResponsable", catalogoSL.getRegistros("PublicacionesGrupoResponsable", parametros));
				registroControl.sPagina = "/Aplicaciones/Publicaciones/fPublSeccionCon.jsp";
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
		//RECUPERA LA SESION DEL USUARIO
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("Usuario");
		//AGREGA LA CLAVE DEL PORTAL Y DEL USUARIO DE LA SESION DEL USUARIO
		registro.addDefCampo("CVE_PORTAL", usuario.sCvePortal);
		registro.addDefCampo("CVE_USUARIO", usuario.sCveUsuario);
		String sCveSeccion = request.getParameter("CveSeccion");
		//SE OMITEN ESPACIOS EN BLANCO
		sCveSeccion = sCveSeccion.trim();
		registro.addDefCampo("CVE_SECCION",sCveSeccion);
		registro.addDefCampo("NOM_SECCION",request.getParameter("NomSeccion"));
		registro.addDefCampo("CVE_SECCION_PADRE",request.getParameter("CveSeccionPadre").equals("null") ? "" : request.getParameter("CveSeccionPadre"));
		registro.addDefCampo("CVE_GPO_RESP",request.getParameter("CveGpoResp"));
		registro.addDefCampo("B_BLOQUEADO",request.getParameter("Bloqueado"));
		//ACTUALIZA EL REGISTRO EN LA BASE DE DATOS
		try{
			//CUALQUIER MODIFICACI�N A LAS SECCIONES RELACIONADAS A UN PERFIL EL OBJETO EN EL
			//JDNI SE BORRA Y SE RECONSTRUYE CUANDO EL USUARIO DESEA CONSULTAR ENTRA A PEQUES-CAMPEONES
			Context context = new InitialContext();
			Object referencia = contexto.lookup("java:comp/env/ejb/PublicacionSL");
			PublicacionSLHome publicacionHome = (PublicacionSLHome)PortableRemoteObject.narrow(referencia, PublicacionSLHome.class);
			PublicacionSL publicacion = publicacionHome.create();
			//ACTUALIZA EL REGISTRO EN LA BASE DE DATOS
			registroControl.resultadoCatalogo = catalogoSL.modificacion("PublicacionesSeccion", registro, iTipoOperacion);
			//LOS VALORES QUE SE UTILIZAN PARA BORRAR EL OBJETO EN EL JNDI SON:
			//-CVE_GPO_EMPRESA
			//-CVE_PORTAL
			//-CVE_USUARIO
			//ESTE METODO SE ENCARGA DE DETERMINAR EL PERFIL DE PUBLICACION DE LA PERSONA
			//QUE ESTA FIRMADA PARA BUSCAR EL OBJETO EN EL JNDI Y BORRARLO
			boolean bExito = publicacion.BorraObjetoJNDI(usuario.sCveGpoEmpresa, usuario.sCvePortal, usuario.sCveUsuario);
			registroControl.sPagina = "/ProcesaCatalogo?Funcion=PublicacionesSeccion&OperacionCatalogo=CT&Filtro=Todos";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return registroControl;
	}
}
