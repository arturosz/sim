<%@ taglib uri="Portal" prefix="Portal" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 

<Portal:Pagina funcion="SimReporteInteresDevengadoIndividual">

	<Portal:PaginaNombre titulo="Intereses Devengados" subtitulo="Consulta del reporte"/>
	<Portal:Forma tipo='url' funcion='SimReporteInteresDevengadoIndividual' url="ProcesaReporte?Funcion=SimReporteInteresDevengadoIndividual&TipoReporte=Xls" agregaentorno="false">
		<Portal:FormaSeparador nombre="Filtros"/>
	           <Portal:Calendario2 etiqueta='Fecha inicio' contenedor='frmRegistro' controlnombre='FechaInicio' esfechasis='false'/>  
			   <Portal:Calendario2 etiqueta='Fecha fin' contenedor='frmRegistro' controlnombre='FechaFin' esfechasis='false'/>  
	
		<Portal:FormaBotones>
                      <input type="button" name="Imprimir" value="Reporte en Excel" onClick="javascript:fReporteXls();">
                      <input type="button" name="Imprimir" value="Reporte en Pdf" onClick="javascript:fReportePdf();">
        </Portal:FormaBotones>
	</Portal:Forma>
	
     <script>
         function fReporteXls(){
      		if (document.frmRegistro.FechaInicio.value == "" || document.frmRegistro.FechaFin.value == ""){
              alert ("Ingrese las fecha de b�squeda");  
	  		}else{
	           url = '/portal/ProcesaReporte?Funcion=SimReporteInteresDevengadoIndividual&TipoReporte=Xls&Bd=MySql&FechaInicio='+document.frmRegistro.FechaInicio.value+'&FechaFin='+document.frmRegistro.FechaFin.value;
              MM_openBrWindow(url,'Reporte','status=yes,scrollbars=yes,resizable=yes,width=700,height=400');
           }
        }
      
       function fReportePdf(){
  		if (document.frmRegistro.FechaInicio.value == "" || document.frmRegistro.FechaFin.value == ""){
              alert ("Ingrese las fecha de b�squeda");  
  		}else{
          url = '/portal/ProcesaReporte?Funcion=SimReporteInteresDevengadoIndividual&TipoReporte=Pdf&Bd=MySql&FechaInicio='+document.frmRegistro.FechaInicio.value+'&FechaFin='+document.frmRegistro.FechaFin.value;
          MM_openBrWindow(url,'Reporte','status=yes,scrollbars=yes,resizable=yes,width=700,height=400');
        }
  }
     </script>
     
</Portal:Pagina>     


