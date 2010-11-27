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
 * Realiza la consulta para obtener los datos que ser�n utilizados para generar el reporte del estado de cuenta grupal.
 */
public class SimReporteEstadoCuentaGrupoREP implements ReporteControlIN {

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

		String sCvePrestamo = request.getParameter("CvePrestamo");
		
		String sSql = 	"SELECT  \n"+
		"C.CVE_GPO_EMPRESA, \n"+
	    "C.CVE_EMPRESA, \n"+
	    "TO_CHAR(P.F_MEDIO,'DD/MM/YYYY') F_MEDIO, \n"+
	    "C.ID_PRESTAMO, \n"+
	    "C.Cve_Prestamo, \n"+ 
	    "C.Cve_Nombre, \n"+
	    "C.NOMBRE, \n"+
	    "C.Id_Producto, \n"+
	    "C.NUM_CICLO, \n"+
	    "C.Nom_Producto, \n"+
	    "C.NOM_METODO, \n"+
	    "C.ID_PERIODICIDAD_PRODUCTO, \n"+
	    "C.PERIODICIDAD_PRODUCTO, \n"+
	    "C.Plazo, \n"+
	    "C.DIA_SEMANA_PAGO, \n"+
	    "C.Valor_Tasa, \n"+
	    "C.Id_Periodicidad_Tasa, \n"+
	    "C.Periodicidad_Tasa, \n"+
	    "Case \n"+
	    "When C.Periodicidad_Tasa = 'Mensual' \n"+
	    "Then C.Valor_Tasa * 12 \n"+
	    "Else (C.VALOR_TASA / (Select Dias \n"+ 
	    "      From Sim_Cat_Periodicidad \n"+
	    "      Where Id_Periodicidad = (Select Id_Periodicidad_Tasa \n"+ 
	    "      From V_Credito \n"+
	    "      Where Cve_Prestamo = '" + (String)request.getParameter("CvePrestamo") + "')) * 30) *12 \n"+
	    "END As TASA_ANUAL, \n"+
	    "C.ID_SUCURSAL, \n"+ 
	    "C.Nom_Sucursal, \n"+ 
	    "C.DIRECCION_SUCURSAL, \n"+ 
	    "C.Tasa_Sucursal, \n"+
	    "TO_CHAR(Nvl(C.Fecha_Real,C.Fecha_Entrega),'DD/MM/YYYY') FECHA_INICIO, \n"+
	    "C.FECHA_FIN, \n"+
	    "C.MONTO_AUTORIZADO, \n"+
	    "C.CARGO_INICIAL, \n"+
	    "NVL(C.MONTO_AUTORIZADO,0) + NVL(C.CARGO_INICIAL,0) MONTO_PRESTADO, \n"+
	    "AA.CUOTA_INTERES, \n"+
		"AB.CUOTA_CAPITAL, \n"+
		"AC.CUOTA_ACCESORIO, \n"+
	    "Ad.Imp_Pago_Hoy Pago_Recargo, \n"+
	    "-(AE.IMP_SALDO_HOY) IMP_SALDO_HOY, \n"+
		"  CASE \n"+
		"    WHEN -(AE.IMP_SALDO_HOY) > 0 \n"+
		"    THEN 0 \n"+
		"    ELSE -(AE.IMP_SALDO_HOY) \n"+
		"  END As SALDO_VENCIDO \n"+
		  "From V_CREDITO C, \n"+
		"PFIN_PARAMETRO P, \n"+
		 " (SELECT CVE_GPO_EMPRESA, \n"+
		"		    CVE_EMPRESA, \n"+
		"		    ID_PRESTAMO_GRUPO, \n"+
		"		    AVG(INTERES) CUOTA_INTERES \n"+
		"		  FROM V_TABLA_AMORTIZACION_GRUPAL \n"+
		"		  GROUP BY CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRESTAMO_GRUPO \n"+
		"		  ORDER BY ID_PRESTAMO_GRUPO \n"+
		"		  ) AA, \n"+
		"		  (SELECT CVE_GPO_EMPRESA, \n"+
		"		    CVE_EMPRESA, \n"+
		"		    ID_PRESTAMO_GRUPO, \n"+
		"		    AVG(IMP_CAPITAL_AMORT) CUOTA_CAPITAL \n"+
		"		  FROM V_TABLA_AMORTIZACION_GRUPAL \n"+
		"		  GROUP BY CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRESTAMO_GRUPO \n"+
		"		  ORDER BY ID_PRESTAMO_GRUPO \n"+
		"		  ) AB, \n"+
		"		  (SELECT CVE_GPO_EMPRESA, \n"+
		"		    CVE_EMPRESA, \n"+
		"		    ID_PRESTAMO_GRUPO, \n"+
		"		    AVG(IMP_ACCESORIO) CUOTA_ACCESORIO \n"+
		"		  FROM V_TABLA_AMORTIZACION_GRUPAL \n"+
		"		   GROUP BY CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRESTAMO_GRUPO \n"+
		"		  ORDER BY ID_PRESTAMO_GRUPO \n"+
		"		  ) AC, \n"+
		  "  (SELECT CVE_GPO_EMPRESA, \n"+
		  "    CVE_EMPRESA, \n"+
		  "    ID_PRESTAMO, \n"+
		  "    SUM(IMP_PAGO_HOY) IMP_PAGO_HOY \n"+
		  "  FROM V_SIM_PRESTAMO_GPO_RES_EDO_CTA \n"+
		  "  WHERE DESC_MOVIMIENTO IN ('Pago Tard�o','Pago Pago Tard�o') \n"+
		  "  GROUP BY CVE_GPO_EMPRESA, \n"+
		  "    CVE_EMPRESA, \n"+
		  "    ID_PRESTAMO \n"+
		  "  Order By Id_Prestamo \n"+
		  "  ) AD, \n"+
		  "  (SELECT CVE_GPO_EMPRESA, \n"+
		  "    CVE_EMPRESA, \n"+
		  "    Id_Prestamo, \n"+
		  "    SUM(IMP_SALDO_HOY) IMP_SALDO_HOY \n"+
		  "  From V_SIM_PRESTAMO_GPO_RES_EDO_CTA \n"+
		  "  WHERE DESC_MOVIMIENTO IN ('Pago Tard�o','Pago Pago Tard�o','Seguro Deudor','Pago Seguro Deudor','Capital','Pago Capital','Inter�s', 'Inter�s Extra', 'Iva De Intereses', 'Iva Interes Extra', 'Pago Inter�s', 'Pago Inter�s Extra', 'Pago Iva De Intereses', 'Pago Iva Interes Extra') \n"+
		  "  GROUP BY CVE_GPO_EMPRESA, CVE_EMPRESA, Id_Prestamo \n"+
		  "  Order By Id_Prestamo \n"+ 
		  "  ) AE \n"+
		  "Where  Aplica_A = 'GRUPO' \n"+
		  "AND P.CVE_GPO_EMPRESA = C.CVE_GPO_EMPRESA \n"+
		  "AND P.CVE_EMPRESA = P.CVE_EMPRESA \n"+
		  "AND AA.CVE_GPO_EMPRESA (+)   = C.CVE_GPO_EMPRESA \n"+ 
		  "AND AA.CVE_EMPRESA (+)       = C.CVE_EMPRESA \n"+
		  "And AA.ID_PRESTAMO_GRUPO (+)       = C.Id_Prestamo \n"+
		  "AND AB.CVE_GPO_EMPRESA (+)   = C.CVE_GPO_EMPRESA \n"+
		  "AND AB.CVE_EMPRESA (+)       = C.CVE_EMPRESA \n"+ 
		  "AND AB.ID_PRESTAMO_GRUPO (+)       = C.Id_Prestamo \n"+
		  "AND AC.CVE_GPO_EMPRESA (+)   = C.CVE_GPO_EMPRESA \n"+
		  "AND AC.CVE_EMPRESA (+)       = C.CVE_EMPRESA \n"+
		  "AND AC.ID_PRESTAMO_GRUPO (+)       = C.Id_Prestamo \n"+
		  "AND AD.CVE_GPO_EMPRESA (+)   = C.CVE_GPO_EMPRESA \n"+
		  "AND AD.CVE_EMPRESA (+)       = C.CVE_EMPRESA \n"+
		  "And Ad.Id_Prestamo (+)       = C.Id_Prestamo \n"+
		  "AND AE.CVE_GPO_EMPRESA (+)   = C.CVE_GPO_EMPRESA \n"+
		  "And AE.Cve_Empresa (+)       = C.Cve_Empresa \n"+ 
		  "And AE.Id_Prestamo (+)       = C.Id_Prestamo \n";
	 
		
		if (!sCvePrestamo.equals("")){
			sSql = sSql + "AND C.Cve_Prestamo = '" + (String)request.getParameter("CvePrestamo") + "'\n";
		}
	
		System.out.println("Estado de cuenta grupal"+sSql);
							
	    String sTipoReporte = request.getParameter("TipoReporte");
		parametros.put("Sql", sSql);
		
		parametros.put("FechaReporte", Fecha2.formatoCorporativoHora(new Date()));
		parametros.put("PathLogotipo", contextoServlet.getRealPath("/Portales/Sim/CrediConfia/img/CrediConfia.bmp"));
		parametros.put("NomReporte", "/Reportes/Sim/reportes/SimReporteEstadoCuentaGrupo.jasper");
		parametros.put("Subreporte1", contextoServlet.getRealPath("/Reportes/Sim/reportes/SimReporteEstadoCuentaMovGrupo.jasper"));
		parametros.put("Subreporte2", contextoServlet.getRealPath("/Reportes/Sim/reportes/SimReporteEstadoCuentaResumenGrupo.jasper"));
		parametros.put("NombreReporte", "Estado de Cuenta");
		                             
		
		return parametros;	
	}
}