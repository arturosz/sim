/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.personas.datos;

import com.rapidsist.comun.bd.Conexion2;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.OperacionAlta; 
import com.rapidsist.portal.catalogos.OperacionBaja; 
import com.rapidsist.portal.catalogos.OperacionModificacion; 
import com.rapidsist.portal.catalogos.OperacionConsultaRegistro; 
import com.rapidsist.portal.catalogos.OperacionConsultaTabla;
import com.rapidsist.portal.catalogos.ResultadoCatalogo;
import java.util.LinkedList;
import java.sql.SQLException;

/**
 * Administra los accesos a la base de datos para el c�nyuge o exc�nyuge del cliente.
 */
 
public class SimClienteExConyugeDAO extends Conexion2 implements OperacionAlta, OperacionBaja, OperacionModificacion, OperacionConsultaRegistro, OperacionConsultaTabla  {

	/**
	 * Obtiene un conjunto de registros en base ael filtro de b�squeda.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el conjunto
	 * de registros deseados.
	 * @return Lista de registros.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public LinkedList getRegistros(Registro parametros) throws SQLException{
	
		sSql =  "SELECT \n"+
			"	TP.CVE_GPO_EMPRESA, \n"+
			"	TP.CVE_EMPRESA, \n"+
			"	TP.ID_PERSONA, \n"+
			"	TP.CVE_TIPO_PERSONA, \n"+
			"       P.NOM_COMPLETO, \n"+
			"       P.RFC, \n"+
			"	P.ID_EX_CONYUGE \n"+
			"FROM SIM_TIPO_PERSONA TP, \n"+
			"     RS_GRAL_PERSONA P \n"+
			"WHERE TP.CVE_GPO_EMPRESA = '" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
			"AND TP.CVE_EMPRESA = '" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n"+
			"AND TP.CVE_TIPO_PERSONA = 'CLIENTE' \n"+
			"AND P.CVE_GPO_EMPRESA = TP.CVE_GPO_EMPRESA \n"+
			"AND P.CVE_EMPRESA = TP.CVE_EMPRESA \n"+
			"AND P.ID_PERSONA = TP.ID_PERSONA \n"+
			"AND P.FECHA_BAJA_LOGICA IS NULL \n";
			
		if (parametros.getDefCampo("ID_PERSONA") != null) {
			sSql = sSql + "AND TP.ID_PERSONA = '" + (String) parametros.getDefCampo("ID_PERSONA") + "' \n";
		}
		if (parametros.getDefCampo("NOM_COMPLETO") != null) {
			sSql = sSql + "AND UPPER(P.NOM_COMPLETO) LIKE '%" + ((String) parametros.getDefCampo("NOM_COMPLETO")).toUpperCase()  + "%' \n";
		}
		if (parametros.getDefCampo("RFC") != null) {
			sSql = sSql + " AND P.RFC LIKE '%" + (String) parametros.getDefCampo("RFC") + "%' \n";
		}
	
		sSql = sSql + " ORDER BY ID_PERSONA \n";
	
		ejecutaSql();
		return getConsultaLista();
	}

	/**
	 * Obtiene un registro tomando como base la llave primaria.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el registro
	 * deseado.
	 * @return Los campos del registro.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public Registro getRegistro(Registro parametros) throws SQLException{
		sSql = "SELECT \n"+
				 " P.CVE_GPO_EMPRESA, \n"+
				 " P.CVE_EMPRESA, \n"+
				 " P.ID_PERSONA, \n"+
				 " P.ID_EX_CONYUGE, \n"+
				 " P.AP_PATERNO, \n"+
				 " P.AP_MATERNO, \n"+
				 " P.NOMBRE_1, \n" +
				 " P.NOMBRE_2, \n" +
				 " P.NOM_COMPLETO, \n" +
				 " P.SEXO, \n" +
				 " P.ESTADO_CIVIL, \n" +
				 " P.IDENTIFICACION_OFICIAL, \n" +
				 " P.NUM_IDENTIFICACION_OFICIAL \n" +
			" FROM RS_GRAL_PERSONA P \n" +
			" WHERE P.ID_PERSONA  = (SELECT \n" +
			"			ID_EX_CONYUGE \n" +
			"			FROM \n" +
			"			RS_GRAL_PERSONA \n" +
			"			WHERE ID_PERSONA = '" + (String) parametros.getDefCampo("ID_PERSONA") + "') \n" +
			" AND P.CVE_GPO_EMPRESA = '" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n" +
			" AND P.CVE_EMPRESA = '" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n" ;
			
		ejecutaSql();
		return this.getConsultaRegistro();
	}

	/**
	 * Inserta un nuevo registro.
	 * @param registro Campos del nuevo registro.
	 * @return Objeto que contiene el resultado de la ejecuci�n de este m�todo.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo alta(Registro registro) throws SQLException{
		ResultadoCatalogo resultadoCatalogo = new ResultadoCatalogo();
		resultadoCatalogo.Resultado = new Registro();
	
		String sIdExConyuge = "";
		
		//OBTENEMOS EL SEQUENCE
		sSql = "SELECT SQ01_RS_GRAL_PERSONA.nextval as ID_PERSONA FROM DUAL";
		ejecutaSql();
		if (rs.next()){
			sIdExConyuge = rs.getString("ID_PERSONA");
		}

		sSql = "INSERT INTO RS_GRAL_PERSONA ( "+
				"CVE_GPO_EMPRESA, \n" +
				"CVE_EMPRESA, \n" +
				"ID_PERSONA, \n" +
				"AP_PATERNO, \n" +
				"AP_MATERNO, \n" +
				"NOMBRE_1, \n" +
				"NOMBRE_2, \n" +
				"NOM_COMPLETO, \n" +
				"SEXO, \n" +
				"FECHA_NACIMIENTO, \n" +
				"IDENTIFICACION_OFICIAL, \n" +
				"NUM_IDENTIFICACION_OFICIAL, \n" +
				"B_PERSONA_FISICA) \n" +
			" VALUES (" +
				"'" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "', \n" +
				"'" + (String)registro.getDefCampo("CVE_EMPRESA") + "', \n" +
				sIdExConyuge + ", \n" +
				"'" + (String)registro.getDefCampo("AP_PATERNO_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("AP_MATERNO_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("NOMBRE_1_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("NOMBRE_2_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("NOM_COMPLETO_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("SEXO_EX_CONYUGE") + "', \n" +
				" TO_DATE('" + registro.getDefCampo("FECHA_NACIMIENTO_EX_CONYUGE") + "','DD/MM/YYYY'), \n" +
				"'" + (String)registro.getDefCampo("IDENTIFICACION_OFICIAL_EX_CONYUGE") + "', \n" +
				"'" + (String)registro.getDefCampo("NUM_IDENTIFICACION_OFICIAL_EX_CONYUGE") + "', \n" +
				"'V') \n" ;
		
		//VERIFICA SI NO SE DIO DE ALTA EL REGISTRO
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}
		resultadoCatalogo.Resultado.addDefCampo("ID_EX_CONYUGE", sIdExConyuge);
		
		sSql =  "INSERT INTO SIM_TIPO_PERSONA ( "+
			"CVE_GPO_EMPRESA, \n" +
			"CVE_EMPRESA, \n" +
			"ID_PERSONA, \n" +
			"CVE_TIPO_PERSONA) \n" +		
			" VALUES (" +
			"'" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "', \n" +
			"'" + (String)registro.getDefCampo("CVE_EMPRESA") + "', \n" +
			sIdExConyuge + ", \n" +
			"'EXCONYUGE') \n" ;
			
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}
		
		sSql =  "UPDATE RS_GRAL_PERSONA SET "+
				"ID_EX_CONYUGE = '" +sIdExConyuge + "' \n" +
			"WHERE ID_PERSONA = '" + (String)registro.getDefCampo("ID_PERSONA")+"' \n" +
			"AND CVE_GPO_EMPRESA = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA")+"' \n" +
			"AND CVE_EMPRESA = '" + (String)registro.getDefCampo("CVE_EMPRESA")+"' \n" ;
		
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}
		
		return resultadoCatalogo;
	}

	/**
	 * Modifica un registro.
	 * @param registro Campos del registro a modificar.
	 * @return Objeto que contiene el resultado de la ejecuci�n de este m�todo.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo modificacion(Registro registro) throws SQLException{
		ResultadoCatalogo resultadoCatalogo = new ResultadoCatalogo();
		
		sSql =  "UPDATE RS_GRAL_PERSONA SET "+
				"AP_PATERNO = '" + (String)registro.getDefCampo("AP_PATERNO") + "', \n" +
				"AP_MATERNO = '" + (String)registro.getDefCampo("AP_MATERNO") + "', \n" +
				"NOMBRE_1 = '" + (String)registro.getDefCampo("NOMBRE_1") + "', \n" +
				"NOMBRE_2 = '" + (String)registro.getDefCampo("NOMBRE_2") + "', \n" +
				"NOM_COMPLETO = '" + (String)registro.getDefCampo("NOM_COMPLETO") + "', \n" +
				"SEXO = '" + (String)registro.getDefCampo("SEXO") + "', \n" +
				"FECHA_NACIMIENTO = TO_DATE('" + (String)registro.getDefCampo("FECHA_NACIMIENTO")  + "','DD/MM/YYYY'), \n" +
				"IDENTIFICACION_OFICIAL	= '" + (String)registro.getDefCampo("IDENTIFICACION_OFICIAL") + "', \n" +
				"NUM_IDENTIFICACION_OFICIAL = '" + (String)registro.getDefCampo("NUM_IDENTIFICACION_OFICIAL") + "' \n" +
			"WHERE ID_PERSONA = '" + (String)registro.getDefCampo("ID_EX_CONYUGE")+ "' \n" +
			"AND ID_PERSONA = '" + (String)registro.getDefCampo("ID_PERSONA")+ "' \n" +
			"AND CVE_GPO_EMPRESA = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA")+"' \n" +
			"AND CVE_EMPRESA = '" + (String)registro.getDefCampo("CVE_EMPRESA")+"' \n" ;
			
		//VERIFICA SI NO SE ACTUALIZ� EL REGISTRO
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}
		
		return resultadoCatalogo;
	}

	/**
	 * Borra un registro.
	 * @param registro Llave primaria.
	 * @return Objeto que contiene el resultado de la ejecuci�n de este m�todo.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo baja(Registro registro) throws SQLException{
		ResultadoCatalogo resultadoCatalogo = new ResultadoCatalogo();
		sSql =  " UPDATE RS_GRAL_PERSONA SET " +
		   	" FECHA_BAJA_LOGICA     = SYSDATE \n" +
		   	" WHERE ID_PERSONA 	='" + (String)registro.getDefCampo("ID_PERSONA") + "' \n" +
		   	" AND CVE_GPO_EMPRESA   ='" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "' \n" +
		   	" AND CVE_EMPRESA       ='" + (String)registro.getDefCampo("CVE_EMPRESA") + "' \n";
		//VERIFICA SI DIO DE ALTA EL REGISTRO
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}
		return resultadoCatalogo;
	}
}