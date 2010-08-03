/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.herramientasconfiguracion.datos;

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
 * Administra los accesos a la base de datos para el cat�logo de portales.
 */
 
public class ColoresDAO extends Conexion2 implements OperacionConsultaRegistro, OperacionModificacion, OperacionAlta, OperacionBaja {

	/**
	 * Obtiene un registro en base a una llave primaria.
	 * @param parametros Par�metros que se le env�an a la consulta para obtener el registro
	 * deseado.
	 * @return Los campos del registro.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public Registro getRegistro(Registro registro) throws SQLException{
		sSql = "SELECT * FROM RS_CONF_MENU_COLOR \n"+
		 	   "WHERE CVE_GPO_EMPRESA  = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "' \n" +
		 	   " AND CVE_PORTAL = '" + (String)registro.getDefCampo("CVE_PORTAL") + "' \n" ;
		 	   System.out.println("Obtiene los colores del menu"+sSql);
		ejecutaSql();
		return this.getConsultaRegistro();
		
	}
	
	/**
	 * Inserta un registro.
	 * @param registro Campos del nuevo registro.
	 * @return Objeto que contiene el resultado de la ejecuci�n de este m�todo.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo alta(Registro registro) throws SQLException {
		
		ResultadoCatalogo resultadoCatalogo = new ResultadoCatalogo();
		sSql = "INSERT INTO RS_CONF_MENU_COLOR( \n" +
			"CVE_GPO_EMPRESA, \n" +
			"CVE_PORTAL) \n" +
			"VALUES ( \n" +
			"'" + (String) registro.getDefCampo("CVE_GPO_EMPRESA") + "',\n" +
			"'" + (String) registro.getDefCampo("CVE_PORTAL") + "')\n" ;
		
		//VERIFICA SI NO SE DIO DE ALTA EL REGISTRO
		if (ejecutaUpdate() == 0) {
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
		sSql = "UPDATE RS_CONF_MENU_COLOR SET \n" +
		 	   "NORMAL_COLOR  ='" + (String)registro.getDefCampo("NORMAL_COLOR") + "',\n" +
		 	   "ON_COLOR  ='" + (String)registro.getDefCampo("ON_COLOR") + "',\n" +
		 	   "DOWN_COLOR  ='" + (String)registro.getDefCampo("DOWN_COLOR") + "', \n" +
		 	   "BORDER_COLOR_DARK  ='" + (String)registro.getDefCampo("BORDER_COLOR_DARK") + "', \n" +
		 	   "BORDER_COLOR_LIGHT  ='" + (String)registro.getDefCampo("BORDER_COLOR_LIGHT") + "', \n" +
		 	   "FONT_LIGHT  ='" + (String)registro.getDefCampo("FONT_LIGHT") + "', \n" +
		 	   "FONT_DARK     ='" + (String)registro.getDefCampo("FONT_DARK") + "' \n" +
		 	   "WHERE CVE_GPO_EMPRESA  = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "' \n" +
		 	   " AND CVE_PORTAL = '" + (String)registro.getDefCampo("CVE_PORTAL") + "' \n" ;

		//VERIFICA SI NO SE MODIFICO EL REGISTRO
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
		
		//BORRA LA PUBLICACION
		sSql = "DELETE FROM RS_CONF_MENU_COLOR \n " +
					" WHERE CVE_GPO_EMPRESA = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
					" AND CVE_PORTAL = '" + (String)registro.getDefCampo("CVE_PORTAL") + "' \n";
		
		//VERIFICA SI DIO DE ALTA EL REGISTRO
		if (ejecutaUpdate() == 0){
			resultadoCatalogo.mensaje.setClave("CATALOGO_NO_OPERACION");
		}

		return resultadoCatalogo;
	}		

}
