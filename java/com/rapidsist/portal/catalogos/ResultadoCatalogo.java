/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.catalogos;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.configuracion.Mensaje;
import java.io.Serializable;

/**
 * Resultado de la ejecuci�n de los m�todos alta, baja y modificaci�n de la clase DAO
 * sobre la base de datos.
 */
public class ResultadoCatalogo implements Serializable{
	/**
	 * Posibles valores generados por los m�todos alta, baja o m�dificaci�n
	 */
	public Registro Resultado = null;
	/**
	 * Posible mensaje generado al ejecutar los m�todos de alta, baja o modificaci�n.
	 */
	public Mensaje mensaje = new Mensaje();
}