package com.rapidsist.sim.reportes.reportes;

import com.rapidsist.portal.cliente.reportes.ReporteControlIN;

import java.sql.SQLException;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import com.rapidsist.comun.util.Fecha2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import com.rapidsist.portal.catalogos.CatalogoSL;
import javax.naming.Context;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.OperacionConsultaTabla;

import javax.servlet.http.HttpSession;
import com.rapidsist.portal.configuracion.Usuario;

/**
 * Realiza la consulta para obtener los datos que ser�n utilizados para generar el reporte de los int. devengados en cr�ditos grupales.
 */
public class SimReporteInteresDevengadoGrupoREP implements ReporteControlIN {

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
	public  Map getParametros(Registro parametrosCatalogo, HttpServletRequest request, CatalogoSL catalogoSL, Context contextoServidor, ServletContext contextoServlet)  throws Exception{
		Map parametros = new HashMap();

		
		String sSql = 	"SELECT DISTINCT \n"+
		"G.CVE_GPO_EMPRESA, \n"+
		"G.CVE_EMPRESA, \n"+
		"G.ID_PRESTAMO_GRUPO ID_PRESTAMO, \n"+ 
		"G.CVE_PRESTAMO_GRUPO CVE_PRESTAMO, \n"+
		"T.FECHA_HISTORICO, \n"+
		"SUM(T.IMP_INTERES_DEV_X_DIA) IMP_INTERES_DEV_X_DIA, \n"+
		"SUM(IFNULL(T.Imp_Interes_Dev_X_Dia,0)+ \n"+
		"IFNULL(T.Imp_Interes_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Interes_Extra_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Iva_Interes_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Iva_Interes_Extra_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Interes_Mora_Pagado,0)+ \n"+
		"IFNULL(T.IMP_IVA_INTERES_MORA_PAGADO,0)) IMP_INTERES_PAGADO \n"+
		"FROM \n"+ 
		"SIM_PRESTAMO P, \n"+
		"SIM_PRESTAMO_GRUPO G, \n"+
		"SIM_TABLA_AMORTIZACION T \n"+
		"WHERE P.CVE_GPO_EMPRESA = G.CVE_GPO_EMPRESA \n"+
		"AND P.CVE_EMPRESA = G.CVE_EMPRESA \n"+
		"AND P.ID_PRESTAMO_GRUPO = G.ID_PRESTAMO_GRUPO \n"+
		"AND T.CVE_GPO_EMPRESA = P.CVE_GPO_EMPRESA \n"+
		"AND T.CVE_EMPRESA = P.CVE_EMPRESA \n"+
		"AND T.ID_PRESTAMO = P.ID_PRESTAMO \n"+
		"AND T.FECHA_HISTORICO BETWEEN STR_TO_DATE('" + (String) request.getParameter("FechaInicio") + "', '%d/%m/%Y') AND STR_TO_DATE('" + (String) request.getParameter("FechaFin") + "', '%d/%m/%Y') \n"+
		"GROUP BY \n"+
		"G.CVE_GPO_EMPRESA, \n"+
		"G.CVE_EMPRESA, \n"+
		"G.ID_PRESTAMO_GRUPO, \n"+ 
		"G.CVE_PRESTAMO_GRUPO, \n"+
		"T.FECHA_HISTORICO \n"+
		"ORDER BY P.CVE_PRESTAMO, T.FECHA_HISTORICO \n";
	
		System.out.println(sSql);
	    String sTipoReporte = request.getParameter("TipoReporte");
		parametros.put("Sql", sSql);
		
		parametros.put("FechaReporte", Fecha2.formatoCorporativoHora(new Date()));
		parametros.put("NomReporte", "/Reportes/Sim/reportes/SimReporteInteresDevengado.jasper");
		parametros.put("NombreReporte", "Intereses devengados");
		                             
		
		return parametros;	
	}
}