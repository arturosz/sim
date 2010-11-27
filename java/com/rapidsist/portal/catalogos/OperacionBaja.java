/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.catalogos;

import com.rapidsist.comun.bd.Registro;
import java.sql.SQLException;

/**
 * Define las operaciones b�sicas que se pueden realizar en una clase DAO
 */
public interface OperacionBaja {

	/**
	 * Borra un registro.
	 * @param registro Llave primaria.
	 * @return Clave del mensaje en caso de que la operaci�n fallara<br>
	 * 0 - Si la operaci�n tuvo �xito.
	 * @throws SQLException Si se genera un error al accesar la base de datos.
	 */
	public ResultadoCatalogo baja(Registro registro) throws SQLException;

}