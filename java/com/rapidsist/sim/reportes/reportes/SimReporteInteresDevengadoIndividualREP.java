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
 * Realiza la consulta para obtener los datos que ser�n utilizados para generar el reporte de los int. devengados en cr�ditos individuales.
 */
public class SimReporteInteresDevengadoIndividualREP implements ReporteControlIN {

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
		"T.CVE_GPO_EMPRESA, \n"+
		"T.CVE_EMPRESA, \n"+
		"T.ID_PRESTAMO, \n"+ 
		"P.CVE_PRESTAMO, \n"+
		"T.FECHA_HISTORICO, \n"+
		"T.IMP_INTERES_DEV_X_DIA, \n"+
		"IFNULL(T.Imp_Interes_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Iva_Interes_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Interes_Extra_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Iva_Interes_Extra_Pagado,0)+ \n"+
		"IFNULL(T.Imp_Interes_Mora_Pagado,0)+ \n"+
		"IFNULL(T.IMP_IVA_INTERES_MORA_PAGADO,0) IMP_INTERES_PAGADO \n"+
		"FROM SIM_TABLA_AMORTIZACION T, \n"+ 
		"SIM_PRESTAMO P \n"+
		"WHERE P.ID_PRESTAMO_GRUPO IS NULL \n"+
		"AND P.CVE_GPO_EMPRESA = T.CVE_GPO_EMPRESA \n"+
		"AND P.CVE_EMPRESA = T.CVE_EMPRESA \n"+ 
		"AND P.ID_PRESTAMO = T.ID_PRESTAMO \n"+
		"AND T.FECHA_HISTORICO BETWEEN STR_TO_DATE('" + (String) request.getParameter("FechaInicio") + "', '%d/%m/%Y') AND STR_TO_DATE('" + (String) request.getParameter("FechaFin") + "', '%d/%m/%Y') \n"+
		"ORDER BY P.CVE_PRESTAMO, T.FECHA_HISTORICO \n";
		
		
		System.out.println(sSql);
	    String sTipoReporte = request.getParameter("TipoReporte");
	    
		parametros.put("Sql", sSql);
	
		parametros.put("FechaReporte", Fecha2.formatoCorporativoHora(new Date()));
	
		parametros.put("NomReporte", "/Reportes/Sim/reportes/SimReporteInteresDevengado.jasper");
	
		parametros.put("NombreReporte", "hola");
		
		return parametros;	
	}
}