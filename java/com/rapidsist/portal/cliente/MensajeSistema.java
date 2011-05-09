/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.portal.cliente;

/**
 * Esta clase contiene los mensajes que son generados en los componentes de infraestructura
 * del portal.
 */
public class MensajeSistema {

	/**
	 * Obtiene el mensaje del sistema en base a su clave.
	 * @param iMensaje Clave del mensaje.
	 * @return Descripci�n del mensaje.
	 */
	static public String getMensajeSistema(int iMensaje){
		String sMensaje ="";

		//OBTIENE EL MENSAJE EN BASE A LA CLAVE
		switch (iMensaje){

			//MENSAJES DE ADMINISTRACION DE CATALOGOS
			case 100:
				sMensaje = "No tiene permisos de acceso a la funci�n solicitada, o bien la funci�n ha sido bloqueada temporalmente";
				break;
			case 101:
				sMensaje = "No se defini� la operaci�n a realizar sobre el cat�logo (Alta, Baja, Modifiaci�n, etc.)";
				break;
			case 102:
				sMensaje = "La operaci�n a realizar sobre el cat�logo no es v�lida.";
				break;
			case 103:
				sMensaje = "No se especific� la funci�n que deber� utilizar el procesador de cat�logos.";
				break;
			case 104:
				sMensaje = "No se envi� el parametro 'Filtro' a la operaci�n de consulta de registros (CT).";
				break;
			case 105:
				sMensaje = "No se especific� la p�gina a donde se debe enviar el control despues de almacenar el evento del usuario";
				break;
			case 106:
				sMensaje = "No se especific� la funci�n que deber� utilizar el procesador de reportes.";
				break;
			case 107:
				sMensaje = "No se encontr� la funci�n solicitada en la base de datos.";
				break;
			case 108:
				sMensaje = "No se definio el tipo de reporte que se deber� imprimir, p.e. Pdf, Excel, etc.";
				break;
			case 109:
				sMensaje = "No existe informaci�n suficiente para generar el Reporte.";
				break;
			case 110:
				sMensaje = "Se produjo un error en la clase controladora del reporte.";
				break;
		}
		return sMensaje;
	}
}