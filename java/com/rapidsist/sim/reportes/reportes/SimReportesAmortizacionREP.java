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
 * Realiza la consulta para obtener los datos que ser�n utilizados para generar el reporte de cat�logo de aplicaciones.
 */
public class SimReportesAmortizacionREP implements ReporteControlIN {

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
		
		String sClave = request.getParameter("CvePrestamoGrupo");
		
		System.out.println("sClave:"+sClave);
		
		String sSql = 		"SELECT  \n"+
							  "CVE_GPO_EMPRESA,\n"+
							  "CVE_EMPRESA,\n"+
							  "ID_PRESTAMO_GRUPO,\n"+
							  "CVE_PRESTAMO_GRUPO,\n"+
							  "NUM_PAGO_AMORTIZACION,\n"+
							  "FECHA_AMORTIZACION,\n"+
							  "TO_NUMBER (IMP_SALDO_INICIAL, '999G999D0000') IMP_SALDO_INICIAL,\n"+
							  "TASA_INTERES,\n"+
							  "INTERES,\n"+
							  "IMP_CAPITAL_AMORT,\n"+
							  "IMP_PAGO,\n"+
							  "IMP_ACCESORIO,\n"+
							  "PAGO_TOTAL,\n"+
							  "TO_NUMBER (IMP_SALDO_FINAL, '999G999D0000') IMP_SALDO_FINAL\n"+
							"FROM \n"+
							  "V_TABLA_AMORTIZACION_GRUPAL\n";
		                   
		
		                    if (sClave != null && !sClave.equals("") && !sClave.equals("null") ){								
								sSql = sSql + "WHERE CVE_PRESTAMO_GRUPO = '" + sClave + "' \n";
							}
		                    
							 sSql = sSql +
						
							 "ORDER BY  NUM_PAGO_AMORTIZACION\n";
							
							 System.out.println("*****************Paso por aqui****************:"+sSql);
		
	    String sTipoReporte = request.getParameter("TipoReporte");
	    System.out.println("TipoReporte:"+sTipoReporte);
		parametros.put("Sql", sSql);
		parametros.put("FechaReporte", Fecha2.formatoCorporativoHora(new Date()));
		parametros.put("NomReporte", "/Reportes/Sim/reportes/SimReportesAmortizacion.jasper");
		parametros.put("Subreporte1", contextoServlet.getRealPath("/Reportes/Sim/reportes/SimReportesAmortizacion_subreport.jasper"));
		parametros.put("Subreporte2", contextoServlet.getRealPath("/Reportes/Sim/reportes/SimReportesAmortizacion_subreport2.jasper"));
		parametros.put("NombreReporte", "rep"+sClave);
		                             
		
		return parametros;		
	}
}
