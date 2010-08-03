/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2008 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.sim.caja.datos;

import com.rapidsist.comun.bd.Conexion2;
import com.rapidsist.portal.catalogos.OperacionAlta;
import com.rapidsist.portal.catalogos.OperacionConsultaTabla;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.ResultadoCatalogo;
import java.util.LinkedList;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Administra los accesos a la base de datos para mostrar movimientos de Caja.
 */
 
public class SimCajaMovimientoDAO extends Conexion2 implements OperacionConsultaTabla, OperacionAlta {
	
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
				"CVE_GPO_EMPRESA, \n"+
				"CVE_EMPRESA, \n"+
				"CVE_MOVIMIENTO_CAJA, \n"+
				"NOM_MOVIMIENTO_CAJA \n"+
				"FROM SIM_CAT_MOVIMIENTO_CAJA \n"+
				"WHERE CVE_GPO_EMPRESA = '" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
				"AND CVE_EMPRESA = '" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n";
		
		
		/*
		sSql =  "SELECT \n"+
				"C.CVE_GPO_EMPRESA, \n"+
				"C.CVE_EMPRESA, \n"+
				"C.ID_SUCURSAL||'-'||C.ID_CAJA ID_CAJA, \n"+
				"S.NOM_SUCURSAL ||' - '|| 'CAJA ' || C.ID_CAJA NOM_CAJA \n"+
				"FROM SIM_SUCURSAL_CAJA C, \n"+
				"SIM_CAT_SUCURSAL S \n"+
				"WHERE C.CVE_GPO_EMPRESA = '" + (String)parametros.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
				"AND C.CVE_EMPRESA = '" + (String)parametros.getDefCampo("CVE_EMPRESA") + "' \n"+
				"AND S.CVE_GPO_EMPRESA = C.CVE_GPO_EMPRESA \n"+
				"AND S.CVE_EMPRESA = C.CVE_EMPRESA \n"+
				"AND S.ID_SUCURSAL = C.ID_SUCURSAL \n"+
				"ORDER BY NOM_CAJA \n";
		*/		
				
		ejecutaSql();
		return getConsultaLista();
	}
	

	/**
	 * Inserta un registro.
	 * @param registro Campos del nuevo registro.
	 * @return Objeto que contiene el resultado de la ejecuci�n de este m�todo.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo alta(Registro registro) throws SQLException{
		ResultadoCatalogo resultadoCatalogo = new ResultadoCatalogo();
		
		String sTxrespuesta = "";
		
		String sIdPreMovimiento = "";
		String sFLiquidacion = "";
		String sFechaMovmiento = "";
		String sIdCuentaVista = "";
			
		//OBTENEMOS EL SEQUENCE
		sSql = "SELECT SQ01_PFIN_PRE_MOVIMIENTO.nextval as ID_PREMOVIMIENTO FROM DUAL";
		ejecutaSql();
			
		if (rs.next()){
			sIdPreMovimiento = rs.getString("ID_PREMOVIMIENTO");
		}
			
		sSql = " SELECT TO_CHAR(TO_DATE('" + (String)registro.getDefCampo("FECHA_MOVIMIENTO") + "','DD-MM-YYYY'),'DD-MON-YYYY') F_LIQUIDACION FROM DUAL";
		ejecutaSql();
			
		if (rs.next()){
			sFLiquidacion = rs.getString("F_LIQUIDACION");
		
		}
			
		//OBTENEMOS EL SEQUENCE
		sSql =  "SELECT ID_CUENTA \n"+
			"FROM PFIN_CUENTA \n"+
			"WHERE CVE_GPO_EMPRESA = '" + (String)registro.getDefCampo("CVE_GPO_EMPRESA") + "' \n"+
			"AND CVE_EMPRESA = '" + (String)registro.getDefCampo("CVE_EMPRESA") + "' \n"+
			"AND CVE_TIP_CUENTA = 'VISTA' \n"+
			"AND SIT_CUENTA = 'AC' \n"+
			"AND ID_TITULAR = (SELECT ID_CLIENTE FROM SIM_PRESTAMO WHERE ID_PRESTAMO = '" + (String)registro.getDefCampo("ID_PRESTAMO") + "') \n";
		
		ejecutaSql();
			
		if (rs.next()){
			sIdCuentaVista = rs.getString("ID_CUENTA");
		}
			
		String sCveGpoEmpresa = (String)registro.getDefCampo("CVE_GPO_EMPRESA");
		String sCveEmpresa = (String)registro.getDefCampo("CVE_EMPRESA");
		String sIdPreMovi = sIdPreMovimiento;
		String sFMovimiento = sFLiquidacion;
		String sIdCuenta = sIdCuentaVista;
		String sIdPrestamo = (String)registro.getDefCampo("ID_PRESTAMO");
		String sCveDivisa = "MXP";
		String sCveOperacion = "TEDEPEFE";
		String sImpNeto = (String)registro.getDefCampo("IMP_NETO");
		String sCveMedio = "PRESTAMO";
		String sCveMercado = "PRESTAMO";
		String sNota = "Prueba";
		String sIdGrupo = "";
		String sCveUsuario = (String)registro.getDefCampo("CVE_USUARIO");
			
		System.out.println("sCveGpoEmpresa"+sCveGpoEmpresa);
		System.out.println("sCveEmpresa"+sCveEmpresa);
		System.out.println("sIdPreMovimiento"+sIdPreMovi);
		System.out.println("sFMovimiento"+sFMovimiento);
		System.out.println("sIdCuenta"+sIdCuenta);
		System.out.println("sIdPrestamo"+sIdPrestamo);
		System.out.println("sCveDivisa"+sCveDivisa);
		System.out.println("sCveOperacion"+sCveOperacion);
		System.out.println("sImpNeto"+sImpNeto);
		System.out.println("sCveMedio"+sCveMedio);
		System.out.println("sCveMercado"+sCveMercado);
		System.out.println("sNota"+sNota);
		System.out.println("sIdGrupo"+sIdGrupo);
		System.out.println("sCveUsuario"+sCveUsuario);
			
		CallableStatement sto = conn.prepareCall("begin PKG_PROCESOS.pGeneraPreMovto(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
		sto.setString(1, (String)registro.getDefCampo("CVE_GPO_EMPRESA"));
		sto.setString(2, (String)registro.getDefCampo("CVE_EMPRESA"));
		sto.setString(3, sIdPreMovi);
		sto.setString(4, sFMovimiento);
		sto.setString(5, sIdCuenta);
		sto.setString(6, (String)registro.getDefCampo("ID_PRESTAMO"));
		sto.setString(7, sCveDivisa);
		sto.setString(8, sCveOperacion);
		sto.setString(9, (String)registro.getDefCampo("IMP_NETO"));
		sto.setString(10, sCveMedio);
		sto.setString(11, sCveMercado);
		sto.setString(12, sNota);
		sto.setString(13, sIdGrupo);
		sto.setString(14, (String)registro.getDefCampo("CVE_USUARIO"));
		sto.registerOutParameter(15, java.sql.Types.VARCHAR);
		
		//EJECUTA EL PROCEDIMIENTO ALMACENADO
		sto.execute();
		sTxrespuesta = sto.getString(15);
		sto.close();
			
		// SE AGREGA LA RESPUESTA
		System.out.println("sTxrespuesta"+sTxrespuesta);
		//registro.addDefCampo("RESPUESTA",sTxrespuesta);
	
		//resultadoCatalogo.Resultado = registro;
			
		CallableStatement sto1 = conn.prepareCall("begin dbms_output.put_line(PKG_PROCESADOR_FINANCIERO.pProcesaMovimiento(?,?,?,?,?,?,?)); end;");
			
		sto1.setString(1, (String)registro.getDefCampo("CVE_GPO_EMPRESA"));
		sto1.setString(2, (String)registro.getDefCampo("CVE_EMPRESA"));
		sto1.setString(3, sIdPreMovi);
		sto1.setString(4, "PV");
		sto1.setString(5, (String)registro.getDefCampo("CVE_USUARIO"));
		sto1.setString(6, "F");
		sto1.registerOutParameter(7, java.sql.Types.VARCHAR);
	
		//EJECUTA EL PROCEDIMIENTO ALMACENADO
		sto1.execute();
		sTxrespuesta  = sto1.getString(7);
		sto1.close();
			
		// SE AGREGA LA RESPUESTA
		System.out.println("sTxrespuesta"+sTxrespuesta);
			
		CallableStatement sto2 = conn.prepareCall("begin PKG_CREDITO.paplicapagocredito(?,?,?,?,?); end;");
			
		sto2.setString(1, (String)registro.getDefCampo("CVE_GPO_EMPRESA"));
		sto2.setString(2, (String)registro.getDefCampo("CVE_EMPRESA"));
		sto2.setString(3, (String)registro.getDefCampo("ID_PRESTAMO"));
		sto2.setString(4, (String)registro.getDefCampo("CVE_USUARIO"));
		sto2.registerOutParameter(5, java.sql.Types.VARCHAR);
	
		//EJECUTA EL PROCEDIMIENTO ALMACENADO
		sto2.execute();
		sTxrespuesta  = sto2.getString(5);
		sto2.close();
			
		// SE AGREGA LA RESPUESTA
		System.out.println("sTxrespuesta"+sTxrespuesta);
		//registro.addDefCampo("RESPUESTA" ,sTxrespuesta);
		//resultadoCatalogo.Resultado = registro;
		
		return resultadoCatalogo;
	}
}