/**
 * Sistema de administraci�n de portales.
 *
 * Copyright (0c) 2003 Rapidisist S.A de C.V. Todos los derechos reservados
 */

package com.rapidsist.publicaciones.datos;

import com.rapidsist.comun.bd.Registro;
import com.rapidsist.comun.bd.Conexion2;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * Esta clase se encarga de la administraci�n de las carpetas.
 * Nota: Una secci�n representa a una carpeta f�sicamente.
 */
public class PubAdministracionCarpetas {
	private String sRuta = "";
	private LinkedList listaCarpetas = new LinkedList();
	private Registro listaCarpetasOrdenadas = new Registro();
	private LinkedList listaErrores = new LinkedList();
	private int iNumeroCarpetas = 0;

	/**
	 * Obtiene la ruta f�sicamente donde se encuentra la secci�n que se busca.
	 * @param parametros Objeto registro con los valores necesarios para poder
	 * buscar la secci�n deseada.
	 * @param conexionBd Parametro con la conexi�n a la base de datos que solo se usa de paso
	 * para otros metodos que lo utilizan
	 * @return sRutaRaizDestinoArchivo + sRutaSeccion, el primer valor representa
	 * al valor que se esta especificado en el archivo de configuraci�n xml y
	 * el segundo valor representa a la ruta de la secci�n en la base de datos,
	 * la uni�n de estas dos cadenas determina la ruta f�sica donde esta la
	 * carpeta.
	 * @throws java.lang.Exception Excepciones ocurridas durante la ejecuci�n de este m�todo localmente.
	 */
	public String getRuta(Registro parametros, Conexion2 conexionBd) throws Exception{
		//SE DETERMINA LA RUTA RAIZ DE DESTINO
		String sRutaRaizDestinoArchivo = "/Publicaciones/" + ((String)parametros.getDefCampo("CVE_GPO_EMPRESA")).toLowerCase() + "/" + ((String)parametros.getDefCampo("CVE_PORTAL")).toLowerCase();
		//SE DETERMINA LA RUTA DE LA SECCION
		String sRutaSeccion = getRutaSeccion(parametros, conexionBd).toLowerCase();
		//SI LA CARPETA NO EXISTE SE CREA
		return sRutaRaizDestinoArchivo + sRutaSeccion;
	}

	/**
	 * Obtiene la ruta f�sica en disco duro de una seccion
	 * @param parametros Valores que permiten realizar la consulta
	 * @param conexionBd Objeto de tipo Conexion2
	 * @return sRuta Cadena con la ruta de la secci�n
	 */
	public String getRutaSeccion(Registro parametros, Conexion2 conexionBd){
		LinkedList lista = null;
		String sRuta = "/";
		Registro registro = new Registro();
		try {
			//OBTIENE LOS PERMISOS DE ACCESO A UNA FUNCION
			ConstruyeSeccion construyeSeccion = new ConstruyeSeccion();
			//ESTE PARAMETRO INDICA QUE SOLO VA A DETERMINA LA RUTA DE UNA SECCION
			parametros.addDefCampo("DETERMINA_RUTA_SECCION", "SI");
			//ASIGNA EL MENU CONSTRUIDO A LA APLICACION
			lista = construyeSeccion.getSeccion(conexionBd, parametros);
			//VERIFICA SI LA CONSULTA TRAE REGISTROS
			if (lista != null){
				//AGREGA A CADA REGISTRO ENCONTRADO EN LA CONSULTA UN CAMPO
				//QUE PERMITE CAMBIAR EL COLOR EN LA TABLA DONDE SE DESPLIEGA
				//EL RESULTADO
				Iterator listaRegistros = lista.iterator();
				while (listaRegistros.hasNext()){
					registro = (Registro)listaRegistros.next();
					sRuta += ((String)registro.getDefCampo("CVE_SECCION")).trim()+"/";
				}//WHILE
			} // LISTA != NULL
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sRuta;
	}
	

	/**
	 * Este m�todo se encarga de obtener de la ruta que se obtuvo una lista
	 * @param parametros Objeto registro con el conjunto de datos necesario para obtener la ruta
	 * @param conexionBd Objeto de tipo Conexion2
	 * @return listaRuta Lista con la ruta
	 * @throws java.lang.Exception
	 */
	public LinkedList ConstruyeListaRuta(Registro parametros, Conexion2 conexionBd) throws Exception{
		LinkedList listaRuta = new LinkedList();
		//SE DETERMINA LA RUTA COMPLETA DONDE SE CREARA LA NUEVA CARPETA(SECCION)
		String sRuta = this.getRuta(parametros, conexionBd);
		String sDelimitador = "/";
		//SE DESCOMPONE LA CADENA Y LA OBTIENE UNA LISTA
		StringTokenizer st = new StringTokenizer(sRuta, sDelimitador);
		String sCarpeta = "";
		//MIENTRAS NO TERMINE LA LISTA
		while (st.hasMoreTokens()) {
			sCarpeta = st.nextToken();
			listaRuta.add(sCarpeta);
		}//TERMINA MIENTRAS NO TERMINE LA LISTA
		return listaRuta;
	}

	/**
	 * Este m�todo se encarga de llamar al m�todo que construye la ruta completa
	 * que representa la secci�n que se acaba de crear y una vez obtenida crea
	 * las carpetas resultantes de la lista, si las carpetas ya existen no las
	 * sobre escribre.
	 * @param parametros Objeto Registro con los par�metros necesarios para obtener
	 * la lista que se busca.
	 * @param conexionBd Objeto de tipo Conexion2
	 * @return bExito "Falso" si las secciones no se pudieron crear, "True" si
	 * las secciones si se pudieron crear.
	 * @throws java.lang.Exception Excepciones ocurridas durante la ejecuci�n de este m�todo localmente.
	 */
	public boolean CrearSeccion(Registro parametros, Conexion2 conexionBd) throws Exception{
		boolean bExito = false;
		int iNumErrores = 0;
		//SE OBTIENE UNA LISTA ORDENADA DE LOS DIRECTORIOS QUE DEBE EXISTIR EN LA M�QUINA
		LinkedList listaRuta = ConstruyeListaRuta(parametros, conexionBd);
		Iterator ilistaRuta = listaRuta.iterator();
		String sDirectorio = "/";
		//MIENTRAS NO TERMINE LA LA LISTA DE DIRECTORIOS
		while (ilistaRuta.hasNext() ){
			sDirectorio += ((String)ilistaRuta.next())+"/";
			//SE ASOCIA LA RUTA QUE SE BUSCA
			java.io.File fCarpetaServidor = new java.io.File(sDirectorio);
			//SI NO EXISTE LA CARPETA QUE SE BUSCA SE CREA
			if (!fCarpetaServidor.exists()) {
				bExito = fCarpetaServidor.mkdir();
				if (!bExito){
					iNumErrores++;
				}
			}//TERMINA SI NO EXISTE LA CARPETA QUE SE BUSCA
		}//TERMINA MIENTRAS NO TERMINE LA LISTA DE DIRECTORIOS
		return iNumErrores>0 ? false: true;
	}

	/**
	 * M�todo encargado de cambiar las carpetas de un lugar f�sicamente o otro
	 * @param parametros Registro con los datos necesario para poder realizar esta operaci�n
	 * @return bExito "True" si todo lo realiz� con �xito y "False" si algo fall�.
	 * @throws java.lang.Exception Excepciones ocurridas durante la ejecuci�n de este m�todo localmente.
	 *
	 * XCOPY source [destination] [/A | /M] [/D[:date]] [/P] [/S [/E]] [/W] [/C] [/I] [/Q] [/F] [/L] [/H] [/R] [/T] [/U] [/K] [/N]
	 * source Specifies the file(s) to copy.
	 * destination  Specifies the location and/or name of new files.
	 * /A Copies files with the archive attribute set, doesn't change the attribute.
	 * /M Copies files with the archive attribute set, turns off the archive attribute.
	 * /D:date Copies files changed on or after the specified date. If no date is given, copies only those files whose source time is newer than the destination time.
	 * /P Prompts you before creating each destination file.
	 * /S Copies directories and sub directories except empty ones.
	 * /E Copies directories and sub directories, including empty ones. Same as /S /E. May be used to modify /T.
	 * /W Prompts you to press a key before copying.
	 * /C Continues copying even if errors occur.
	 * /I If destination does not exist and copying more than one file, assumes that destination must be a directory.
	 * /Q Does not display file names while copying.
	 * /F Displays full source and destination file names while copying.
	 * /L Displays files that would be copied.
	 * /H Copies hidden and system files also.
	 * /R Overwrites read-only files.
	 * /T Creates directory structure, but does not copy files. Does not include empty directories or sub directories. /T /E includes empty directories and sub directories.
	 * /U Updates the files that already exist in destination.
	 * /K Copies attributes. Normal xcopy will reset read-only attributes.
	 * /Y Overwrites existing files without prompting.
	 * /-Y Prompts you before overwriting existing files.
	 * /N Copy using the generated short names.
	 */
	public boolean ModificarSeccion(Registro parametros) throws Exception{
		boolean bExito = false;
		try {
			java.lang.Runtime proc = java.lang.Runtime.getRuntime();
			proc.exec("xcopy C:\\p1 \\p2\\ /A /E /C");
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return bExito;
	}

	/**
	 * Este m�todo se encarga de borrar las carpetas de una secci�n
	 * @param parametros Objeto Registro
	 * @return bExito "Falso" si las secci�n se pud� borrar, "True" si
	 * las secciones no se pudo borrar.
	 * @throws java.lang.Exception Excepciones ocurridas durante la ejecuci�n de este m�todo localmente.
	 */
	public LinkedList BorrarSeccion(Registro parametros) throws Exception{
		LinkedList listaRuta = new LinkedList();
		//SI LA LISTA SE DETERMINA EN LA CLASE CON ANTES DE QUE SE BORRE LA SECCION
		//PORQUE UNA VEZ BORRADA YA NO SE PUEDE DETERMINAR EL ORIGEN DE LA CARPETA

		//SE OBTIENE UNA LISTA ORDENADA DE LOS DIRECTORIOS QUE DEBE EXISTIR EN LA M�QUINA
		listaRuta = (LinkedList)parametros.getDefCampo("LISTA") ;

		Iterator ilistaRuta = listaRuta.iterator();
		String sDirectorio = "/";
		//MIENTRAS NO TERMINE LA LA LISTA DE DIRECTORIOS
		while (ilistaRuta.hasNext() ){
			sDirectorio += ((String)ilistaRuta.next())+"/";
			//SE ASOCIA LA RUTA QUE SE BUSCA
		}//TERMINA MIENTRAS NO TERMINE LA LISTA DE DIRECTORIOS

		iNumeroCarpetas = 0; //INICIALIZA LA VARIABLE QUE CUENTA EL NUMERO DE CARPETAS
		//OBTIENE LA ESTRUCTURA DE DIRECTORIOS QUE DEPENDEN DEL DIRECTORIO ESPECIFICADO
		//Y BORRA LOS ARCHIVOS CONTENIDOS EN LA ESTRUCTURA ENCONTRADA
		ObtienerEstructuraDirectorios(sDirectorio, false, true); //RUTA, INDICA SI ES CONSULTA, INDICA SI SE BORRAN LOS ARCHIVOS DE LA ESTRUCTURA

		//AGREGA A LA LISTA EL PRIMER DIRECTORIO QUE SE DEBE BORRAR
		for (int i=this.iNumeroCarpetas; i >= 1; i--) {
			//BORRA LOS ARCHIVOS DE LAS CARPETAS HIJAS
			BorrarCarpeta((String)listaCarpetasOrdenadas.getDefCampo(String.valueOf(i)));
		}
		return listaErrores;
	}

	/**
	 * M�todo recursivo que obtiene la ruta de la secci�n deseada y borra todos los archivos
	 * encontrados por las carpetas que pasa. Este m�todo se utiliza cuando se borra una
	 * secci�n.
	 * @param sDirectorio Cadena que indica la ruta completa de la secci�n.
	 * @param bDeseaConsultar Valor l�gico que indica si consulta y que no borre
	 * @param bDeseaBorrar Valor l�gico que indica borra.
	 * buscar la secci�n deseada y borrarla
	 * @return bExito "Falso" si las carpetas no se pudieron borrar, "True" si se pudieron borrar.
	 * @throws java.lang.Exception Excepciones ocurridas durante la ejecuci�n de este m�todo localmente.
	 */
	public boolean ObtienerEstructuraDirectorios(String sDirectorio, boolean bDeseaConsultar, boolean bDeseaBorrar) throws Exception{
		boolean bExito = false;
		String sCarpeta = "";
		java.io.File fCarpetaServidor = new java.io.File(sDirectorio);
		String sListaArchivos[]	= fCarpetaServidor.list();
		//SE AGREGA LA CARPETA PADRE A LA LISTA
		if (iNumeroCarpetas == 0){
			iNumeroCarpetas++;
			listaCarpetasOrdenadas.addDefCampo(String.valueOf(iNumeroCarpetas), sDirectorio);
		}
		//PARA BORRAR LOS ARCHIVOS DE LA CARPETA PADRE
		if (bDeseaBorrar){
			BorrarArchivos(sDirectorio);
		}
		//PARA LA LISTA DE ARCHIVOS ENCONTRADA
		for (int i = 0; i < sListaArchivos.length; i++) {
			java.io.File fArchivoContenido = new java.io.File(sDirectorio + sListaArchivos[i]);
			if (fArchivoContenido.isDirectory()) {
				//FORMA EL NUEVO DIRECTORIO QUE ENCONTRO
				sCarpeta = sDirectorio + sListaArchivos[i]+"/";
				this.listaCarpetas.add(sCarpeta);
				iNumeroCarpetas++;
				listaCarpetasOrdenadas.addDefCampo(String.valueOf(iNumeroCarpetas), sCarpeta);
				if (bDeseaBorrar){
					BorrarArchivos(sCarpeta);
				}
				ObtienerEstructuraDirectorios(sCarpeta, false, true);
			}
		} //TERMINA PARA i HASTA QUE NO HAYA MAS ARCHIVOS QUE BUSCAR
		return bExito;
	}

	/**
	 * Borra los archivos contenidos en la ra�z de la ruta especificada
	 * @param sDirectorio Espeficifa la ruta ra�z
	 * @return true Si se borro el archivo y false si no se pudo borrar
	 * @throws java.lang.Exception
	 */
	public boolean BorrarArchivos(String sDirectorio) throws Exception{
		boolean bExito = false;
		int iNumErrores = 0;
		java.io.File fCarpetaServidor = new java.io.File(sDirectorio);
		String sListaArchivos[]	= fCarpetaServidor.list();
		//PARA LA LISTA DE ARCHIVOS ENCONTRADA
		for (int i = 0; i < sListaArchivos.length; i++) {
			java.io.File fArchivoContenido = new java.io.File(sDirectorio + sListaArchivos[i]);
			//SI ES UN ARCHIVO SE BORRA
			if (fArchivoContenido.isFile()) {
				//SE ALMACENA EL RESULTADO DE LA OPERACI�N DE BORRADO
				bExito = fArchivoContenido.delete();
				//SI NO PUDO BORRAR EL ARCHIVO
				if (!bExito){
					this.listaErrores.add(fArchivoContenido.getAbsoluteFile());
					//INCREMENTA EL CONTADOR DE ERRORES
					iNumErrores ++;
				}
			}
		} //TERMINA PARA i HASTA QUE NO HAYA MAS ARCHIVOS QUE BUSCAR

		return iNumErrores>0 ? false: true;
	}

	/**
	 * Borra una carpeta
	 * @param sDirectorio Indica la ruta ra�z de la carpeta que debe borrar
	 * @return true Si la carpeta se pudo borrar y false si no lo pudo hacer
	 * @throws java.lang.Exception
	 */
	public boolean BorrarCarpeta(String sDirectorio) throws Exception{
		boolean bExito = false;
		int iNumErrores = 0;
		java.io.File fCarpetaServidor = new java.io.File(sDirectorio);
		//SI ES UNA CARPETA SE BORRA
		if (fCarpetaServidor.isDirectory()) {
			//SE ALMACENA EL RESULTADO DE LA OPERACI�N DE BORRADO
			bExito = fCarpetaServidor.delete();
			//SI NO PUDO BORRAR EL ARCHIVO
			if (!bExito){
				this.listaErrores.add(sDirectorio);
				//INCREMENTA EL CONTADOR DE ERRORES
				iNumErrores ++;
			}
		}
		return iNumErrores>0 ? false: true;
	}

	/**
	 * Este m�todo borra un archivo en la ruta especificada.
	 * @param parametros Objeto Registro con sDirectorio con la cadena con la
	 * ruta donde se encuentra el archivo y un par�metro m�s que indica al m�todo
	 * encargado del determina la ruta de las secciones que s�lo determine una ruta y
	 * no varias como actualmente lo puede hacer.
	 * @param conexionBd Objeto de tipo Conexion2
	 * @return bExito True, cuando se borr� el archivo, False, cuando no se borr�.
	 * @throws java.lang.Exception
	 */
	public boolean BorrarArchivo(Registro parametros, Conexion2 conexionBd) throws Exception{
		boolean bExito = false;
		//PARAMETRO NECESARIO PARA OBTENER SOLO LA RUTA DE LA SECCION
		parametros.addDefCampo("DETERMINA_RUTA_SECCION", "SI");
		String sRuta = this.getRuta(parametros, conexionBd) + (String)parametros.getDefCampo("URL_PUBLICACION");
		java.io.File sArchivoServidor = new java.io.File(sRuta);
		if (sArchivoServidor.isFile()){
			bExito = sArchivoServidor.delete();
		}
		return bExito;
	}

	/**
	 * Este m�todo borra una publicacion sin determinar la ruta especificada porque este dato ya esta calculado.
	 * @param sRuta Cadena con la ruta donde se encuentra el archivo que se desea borrar.
	 * @return bExito True, cuando se borr� el archivo, False, cuando no se borr�.
	 * @throws java.lang.Exception
	 */
	public boolean BorrarPublicacion(String sRuta) throws Exception{
		boolean bExito = false;
		java.io.File sArchivoServidor = new java.io.File(sRuta);
		//if (sArchivoServidor.isFile()){
			bExito = sArchivoServidor.delete();
		//}
		return bExito;
	}
}
