package com.rapidsist.herramientasconfiguracion.reportes;

import com.rapidsist.portal.cliente.reportes.ReporteControlIN;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import com.rapidsist.comun.util.Fecha2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import com.rapidsist.portal.catalogos.CatalogoSL;
import javax.naming.Context;
import com.rapidsist.comun.bd.Registro;

import javax.servlet.http.HttpSession;
import com.rapidsist.portal.configuracion.Usuario;

/**
 * Realiza la consulta para obtener los datos que ser�n utilizados para generar el reporte de cat�logo de mensajes del sistema.
 */
public class RepCatMensajesSistemaREP implements ReporteControlIN {

	/**
	 * Obtiene la consulta en la base de datos y par�metros que ser�n utilizados por el reporte.
	 * @param parametrosCatalogo Par�metros que se le env�an al m�todo .
	 * @param request Objeto que provee de informaci�n al servlet sobre el request del cliente. El
	 * contenedor de servlets crea un objeto HttpServletRequest y lo env�a como un par�metro a este m�todo.
	 * @param catalogoSL Instancia del Ejb CatalogoSL que ejecuta en la base de datos las
	 * operaciones especificadas en la clase CON.
	 * @param contextoServidor Objeto que contiene informaci�n acerca del entorno del servidor de
	 * aplicaciones.
	 * @param contextoServlet Objeto que contiene informaci�n acerca del entorno del servlet. 
	 * @return Map objeto que contiene los datos para generar el reporte.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public Map getParametros(Registro parametrosCatalogo, HttpServletRequest request, CatalogoSL catalogoSL, Context contextoServidor, ServletContext contextoServlet)  throws Exception{
		Map parametros = new HashMap();

		//OBTIENE LOS DATOS DEL USUARIO
		HttpSession session = request.getSession(true);
		Usuario usuario = (Usuario)session.getAttribute("Usuario");
		String sCveUsuario = usuario.sCveUsuario;
		String sCvePortal = usuario.sCvePortal;
		parametros.put("CveUsuario", sCveUsuario);
		
		//OBTIENE LOS DATOS DEL GRUPO DE EMPRESA
		Registro resultado = new Registro();
        resultado.addDefCampo("RegistroGrupoEmpresa", catalogoSL.getRegistro("HerramientasConfiguracionGrupoEmpresas", parametrosCatalogo));
		String sNombreGrupoEmpresa  = (String)((Registro)resultado.getDefCampo("RegistroGrupoEmpresa")).getDefCampo("NOM_GPO_EMPRESA");
		parametros.put("NomEntidad", sNombreGrupoEmpresa);	
		
		//OBTIENE LOS DATOS DEL PORTAL
		Registro registroPortal = new Registro();
		parametrosCatalogo.addDefCampo("CVE_PORTAL", sCvePortal);
		registroPortal = catalogoSL.getRegistro("HerramientasConfiguracionPortal", parametrosCatalogo);
        String sNombrePortal = (String)registroPortal.getDefCampo("NOM_PORTAL");
		parametros.put("NombrePortal", sNombrePortal);
		
		String sSql= "SELECT \n"+
						"CVE_MENSAJE, \n"+
						"TIPO_MENSAJE, \n"+
						"TX_MENSAJE \n"+
					 "FROM \n"+
						"RS_CONF_MENSAJE \n"+
					 "WHERE 1=1 \n";						
							
		if (request.getParameter("CveMensaje") != null && !request.getParameter("CveMensaje").equals("") && !request.getParameter("CveMensaje").equals("null") ){
			sSql = sSql + "AND UPPER(CVE_MENSAJE) LIKE '%" + ((String)request.getParameter("CveMensaje")).toUpperCase() +"%'  \n";
			parametros.put("CveMensaje", request.getParameter("CveMensaje"));
		}

		if (request.getParameter("Tipo") != null && !request.getParameter("Tipo").equals("") && !request.getParameter("Tipo").equals("null") ){
			sSql = sSql + "AND UPPER(TIPO_MENSAJE) LIKE '%" + ((String)request.getParameter("Tipo")).toUpperCase() +"%'  \n";
			parametros.put("Tipo", request.getParameter("Tipo"));
		}
		if (request.getParameter("Mensaje") != null && !request.getParameter("Mensaje").equals("") && !request.getParameter("Mensaje").equals("null") ){
			sSql = sSql + "AND UPPER(TX_MENSAJE) LIKE '%" +  ((String)request.getParameter("Mensaje")).toUpperCase() +"%'  \n";
			parametros.put("Mensaje", request.getParameter("Mensaje"));
		}
		
		sSql = sSql + "ORDER BY CVE_MENSAJE \n";

		parametros.put("Sql", sSql);
		parametros.put("FechaReporte", Fecha2.formatoCorporativoHora(new Date()));
		parametros.put("NomReporte", "/Reportes/Herramientas/RepMensajesSistema.jasper");
		return parametros;		
	}
}
