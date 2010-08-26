/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.prestamo.datos;

import com.rapidsist.comun.bd.Conexion2;
import com.rapidsist.portal.catalogos.OperacionConsultaTabla;
import com.rapidsist.portal.catalogos.OperacionConsultaRegistro;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.ResultadoCatalogo;
import java.util.LinkedList;
import java.sql.SQLException;

/**
 * Administra los accesos a la base de datos para comites.
 */
 
public class SimPrestamoAsesorCreditoDAO extends Conexion2 implements OperacionConsultaTabla, OperacionConsultaRegistro {

	/**
	 * Obtiene un conjunto de registros en base a el filtro de b�squeda.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el conjunto
	 * de registros deseados.
	 * @return Lista de registros.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public LinkedList getRegistros(Registro parametros) throws SQLException{
		//PREPARA LA CONSULTA QUE VA A HACER EN LA BD, PARA TRAER LOS REGISTROS QUE COINCIDAN CON LAS CONDICIONES
		
		sSql =  "SELECT \n"+
				"A.CVE_GPO_EMPRESA, \n"+
		  		"A.CVE_EMPRESA, \n"+
		  		"A.ID_SUCURSAL, \n"+ 
		  		"A.CVE_USUARIO, \n"+ 
		  		"P.NOM_COMPLETO, \n"+
		  		"S.NOM_SUCURSAL \n"+
				"FROM SIM_USUARIO_ACCESO_SUCURSAL A, \n"+
		  		"SIM_USUARIO_ACCESO_SUCURSAL B, \n"+
		  		"RS_GRAL_USUARIO U, \n"+
		  		"RS_GRAL_PERSONA P, \n"+
		  		"SIM_CAT_SUCURSAL S \n"+
				"WHERE A.CVE_GPO_EMPRESA = '" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
		 		"AND A.CVE_EMPRESA = '" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n"+
				"AND B.CVE_GPO_EMPRESA = A.CVE_GPO_EMPRESA \n"+
				 "AND B.CVE_EMPRESA = A.CVE_EMPRESA \n"+
				 "AND B.ID_SUCURSAL = A.ID_SUCURSAL \n"+
				 "AND B.CVE_USUARIO = '" + (String)parametros.getDefCampo("CVE_USUARIO") + "' \n"+
				 "AND U.CVE_GPO_EMPRESA = A.CVE_GPO_EMPRESA \n"+
				 "AND U.CVE_EMPRESA = A.CVE_EMPRESA \n"+
				 "AND U.CVE_USUARIO = A.CVE_USUARIO \n"+
				 "AND U.CVE_PUESTO = 'AseSuc' \n"+
				 "AND P.CVE_GPO_EMPRESA = U.CVE_GPO_EMPRESA \n"+
				 "AND P.CVE_EMPRESA = U.CVE_EMPRESA \n"+ 
				 "AND P.ID_PERSONA = U.ID_PERSONA \n"+
				 "AND S.CVE_GPO_EMPRESA = A.CVE_GPO_EMPRESA \n"+
				 "AND S.CVE_EMPRESA = A.CVE_EMPRESA \n"+ 
				 "AND S.ID_SUCURSAL = A.ID_SUCURSAL \n";
		
			/*		
			if (parametros.getDefCampo("CLAVE_USUARIO") != null) {
				sSql = sSql + " AND UPPER(U.CVE_USUARIO) LIKE '%" + ((String) parametros.getDefCampo("CLAVE_USUARIO")).toUpperCase()  + "%' \n";
			}
			
			if (parametros.getDefCampo("NOM_COMPLETO") != null) {
				sSql = sSql + " AND UPPER(P.NOM_COMPLETO) LIKE '%" + ((String) parametros.getDefCampo("NOM_COMPLETO")).toUpperCase()  + "%' \n";
			}
			*/
		
			sSql = sSql + "ORDER BY NOM_COMPLETO \n";
		
			System.out.println("muestran todos lo usuarios de cualquier sucursal"+sSql);
			
		ejecutaSql();
		return getConsultaLista();
	}
	
	/**
	 * Obtiene un registro en base a una llave primaria.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el registro
	 * deseado.
	 * @return Los campos del registro.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public Registro getRegistro(Registro parametros) throws SQLException{
		sSql =  "SELECT \n"+
				   "U.CVE_GPO_EMPRESA, \n" +
				   "U.CVE_EMPRESA, \n" +
				   "U.CVE_USUARIO, \n"+
				   "P.NOM_COMPLETO \n"+	
				" FROM RS_GRAL_USUARIO U, \n"+
				"      RS_GRAL_PERSONA P \n"+
				" WHERE U.CVE_GPO_EMPRESA ='" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
				" AND U.CVE_EMPRESA ='" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n"+
				" AND P.CVE_GPO_EMPRESA = U.CVE_GPO_EMPRESA \n"+
				" AND P.CVE_EMPRESA = U.CVE_EMPRESA \n"+
				" AND P.ID_PERSONA = U.ID_PERSONA \n"+
				" AND U.CVE_USUARIO = '" + (String)parametros.getDefCampo("CVE_USUARIO") + "' \n";		
		
		ejecutaSql();
		return this.getConsultaRegistro();
	}
}