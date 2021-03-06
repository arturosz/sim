<%@ taglib uri="Portal" prefix="Portal" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<Portal:Pagina funcion="SimCajaMovimiento">
	<Portal:PaginaNombre titulo="Movimientos" subtitulo=""/>
	
	<Portal:Forma tipo='catalogo' funcion='SimCajaMovimiento'>
		<Portal:FormaSeparador nombre="Movimientos"/>
		
		<Portal:FormaElemento etiqueta='Movimientos' control='selector' controlnombre='CveMovimientoCaja' controlvalor='${requestScope.registro.campos["CVE_MOVIMIENTO_CAJA"]}' editarinicializado='true' obligatorio='true' campoclave="CVE_MOVIMIENTO_CAJA" campodescripcion="NOM_MOVIMIENTO_CAJA" datosselector='${requestScope.ListaMovimientosCaja}'/>

		<input type="hidden" name="IdCaja" value='<c:out value='${param.IdCaja}'/>' />
		<Portal:FormaBotones>
			<input type="button" name="Aceptar"  value="Aceptar" onclick='javascript:fAceptar()'>
		</Portal:FormaBotones>
	</Portal:Forma>	
	
	<script>
		function fAceptar(){
			if (document.frmRegistro.CveMovimientoCaja.value == "DESIND"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaDesembolsoIndividual&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "DESGPO"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaDesembolsoGrupal&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccionGrupo=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "PAGOIND"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaConsultaPagarCredito&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&AplicaA=INDIVIDUAL";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "PAGOGPO"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaConsultaPagarCredito&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&AplicaA=GRUPO&IdTransaccion=null&TxRespuesta=0&TxPregunta=0&PagoTotal=0";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "DOTCAJ"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaDotacion&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "CANPAGO"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaCancelacionPago&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value;
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "RETCAJ"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaRetiro&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "CORTE"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaCorte&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdMovimiento="+document.frmRegistro.CveMovimientoCaja.value;
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "RETSUC"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaRetiroSucursal&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "RECSUC"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaRecepcionSucursal&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "RETCAJCEN"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaRetiroCentral&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "RECCAJCEN"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaRecepcionCentral&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}else if (document.frmRegistro.CveMovimientoCaja.value == "TRABAN"){
				document.frmRegistro.action="ProcesaCatalogo?Funcion=SimCajaTranspasoBanco&OperacionCatalogo=IN&Filtro=Inicio&IdCaja="+document.frmRegistro.IdCaja.value+"&IdTransaccion=null";
				document.frmRegistro.submit();
			}
		}
	</script>
	
</Portal:Pagina>