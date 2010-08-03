/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.prestamo.datos;

import com.rapidsist.comun.bd.Conexion2;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.OperacionConsultaTabla;
import com.rapidsist.portal.catalogos.ResultadoCatalogo;
import java.util.LinkedList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Administra los accesos a la base de datos para los documentos del pr�stamo.
 */
 
public class SimPrestamoDocumentacionDAO extends Conexion2 implements OperacionConsultaTabla {

	/**
	 * Obtiene un conjunto de registros en base al filtro de b�squeda.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el conjunto
	 * de registros deseados.
	 * @return Lista de registros.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public LinkedList getRegistros(Registro parametros) throws SQLException{
		//PREPARA LA CONSULTA QUE VA A HACER EN LA BD, PARA TRAER LOS REGISTROS DE BANCOS
		
		sSql = " SELECT \n"+
			"	PD.CVE_GPO_EMPRESA, \n"+
			"	PD.CVE_EMPRESA, \n"+
			"	PD.ID_PRESTAMO, \n"+
			"	PD.ID_DOCUMENTO, \n"+
			"	CD.NOM_DOCUMENTO, \n"+
			"	CD.ID_REPORTE, \n"+
			"	R.NOM_REPORTE, \n"+
			"	R.CVE_FUNCION \n"+
			" FROM SIM_PRESTAMO_DOCUMENTACION PD, \n"+
			"      SIM_CAT_DOCUMENTO CD, \n"+
			"      SIM_CAT_REPORTE R \n"+
			" WHERE PD.CVE_GPO_EMPRESA ='" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n" +
			" AND PD.CVE_EMPRESA ='" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n"+
			" AND PD.ID_PRESTAMO ='" + (String)parametros.getDefCampo("ID_PRESTAMO") + "' \n"+
			" AND CD.CVE_GPO_EMPRESA = PD.CVE_GPO_EMPRESA \n" +
			" AND CD.CVE_EMPRESA = PD.CVE_EMPRESA \n"+
			" AND CD.ID_DOCUMENTO = PD.ID_DOCUMENTO \n"+
			" AND R.CVE_GPO_EMPRESA = CD.CVE_GPO_EMPRESA \n"+
			" AND R.CVE_EMPRESA = CD.CVE_EMPRESA \n"+
			" AND R.ID_REPORTE = CD.ID_REPORTE \n";
		
		ejecutaSql();
		return getConsultaLista();
	}
}