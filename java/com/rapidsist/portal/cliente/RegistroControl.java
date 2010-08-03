/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

import java.util.LinkedList;
import com.rapidsist.comun.bd.Registro;
import com.rapidsist.portal.catalogos.ResultadoCatalogo;

/**
 * Esta clase tiene por objetivo regresar una respuesta �nica de todas las clases
 * controladoras (CON) al servlet ProcesaCatalogoS, la cual contiene el resultado sobre
 * la operaci�n realizada en el cat�logo, as� como la p�gina a donde se debe
 * transferir el control.
 */
public class RegistroControl {
	/**
	 * P�gina a donde se desea transferir el control.
	 */
	public String sPagina ="";
	/**
	 * Estructura que agrupa el resultado de la operaci�n realizada sobre el cat�logo.
	 */
	public LinkedList lista  = null;

	/**
	 * Objeto que contiene la respuesta de la clase CON. Esta respuesta puede agrupar
	 * diferentes objetos LinkedList gracia a que este objeto est� basado en un HashMap.
	 */
	public Registro respuesta = new Registro();

	/**
	 * Respuesta del EJB de cat�logos.
	 */
	public ResultadoCatalogo resultadoCatalogo = null;
}