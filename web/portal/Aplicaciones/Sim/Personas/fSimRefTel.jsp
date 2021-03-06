<%@ taglib uri="Portal" prefix="Portal" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<Portal:Pagina funcion="SimReferenciaTelefono">
	<Portal:PaginaNombre titulo="Tel&eacute;fonos de la Referencia del Cliente" subtitulo="Modificaci&oacute;n de datos" subtituloalta="Alta de datos"/>
	<Portal:Forma tipo='catalogo' funcion='SimReferenciaTelefono' parametros='IdPersona=${param.IdPersona}&IdReferencia=${param.IdReferencia}&IdTelefono=${param.IdTelefono}&IdDomicilio=${param.IdDomicilio}&IdExConyuge=${param.IdExConyuge}'>
		<Portal:FormaSeparador nombre="Datos"/>
		
		<Portal:FormaElemento etiqueta='Tel&eacute;fono' control='Texto' controlnombre='Telefono' controlvalor='${requestScope.registro.campos["TELEFONO"]}' controllongitud='20' controllongitudmax='20' editarinicializado='true' obligatorio='true' validadato='numerico'/>
		<Portal:FormaElemento etiqueta='Descripción del Tel&eacute;fono' control='selector' controlnombre='IdDescTel' controlvalor='${requestScope.registro.campos["ID_DESC_TEL"]}' editarinicializado='true' obligatorio='true' campoclave="ID_DESC_TEL" campodescripcion="NOM_DESC_TEL" datosselector='${requestScope.ListaDescripcionTelefono}'/>
		
		<Portal:FormaBotones>
			<Portal:FormaBotonAltaModificacion/>
			<Portal:FormaBotonBaja/>
		</Portal:FormaBotones>
	</Portal:Forma>
</Portal:Pagina>	
