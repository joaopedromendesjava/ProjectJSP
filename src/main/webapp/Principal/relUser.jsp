<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>
    <link rel="icon" href="<%=request.getContextPath()%>/assets/images/logo.png" type="image/x-icon">


  <body>
  <!-- Pre-loader start -->
  <jsp:include page="theme-loader.jsp"></jsp:include>

  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                 
                 <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                 
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                     <jsp:include page="page-header.jsp"></jsp:include>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-header"></div>
													<div class="card-block">
														<h2 class="sub-title">Relat?rio de Usu?rio</h2>
								
															<form class="form-material" action="<%=request.getContextPath()%>/ServletUsuarioController" method="get" id="formUser">

															<input type="hidden" id= "acaoRelatorioImprimirTipo" name="acao" value="imprimirRelatorioUser">	
																	
															<div class="form-row align-items-center">
															
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataInicial">Data Inicial</label>
																		<input value="${dataInicial}" type="text" class="form-control" id="dataInicial" name ="dataInicial">
																</div>
																
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataFinal">Data Final</label>
																		<input value="${dataFinal}" type="text" class="form-control" id="dataFinal" name ="dataFinal"> 
																	
																</div>
																
																<div class="col-auto">
																	<button type="button" onclick="ImprimirHTML();" class="btn btn-primary mb-2">Imprimir Relat?rio</button>
																	<button type="button" onclick="ImprimirPDF();" class="btn btn-primary mb-2">Imprimir em PDF</button>
																	<button type="button" onclick="ImprimirExcel();" class="btn btn-primary mb-2">Imprimir em Excel</button>
																</div>
															</div>
															
														</form>

														<div style="height: 400px; overflow: scroll;">
															<table class="table" id="tabelaresultadosview">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${listaUser}" var="mL">
																	<tr>
																		<td><c:out value="${mL.id}"></c:out></td>
																		<td><c:out value="${mL.nome}"></c:out></td>
																	</tr>
																		<c:forEach items="${mL.telefones}" var="fone">
																		<tr>
																			<td/>
																			<th scope="colgroup">Telefone:</th><td style="font-size: 11px;"><c:out value="${fone.numero}"></c:out></td> 
																		</tr>
																	
																		</c:forEach>
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>
										</div>

									</div>
									<!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
   <jsp:include page="javascriptfile.jsp"></jsp:include> 
   <script type="text/javascript">
   
   function ImprimirHTML() {

	   document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';
	   $("#formUser").submit();
}
   function ImprimirPDF() {
	   
	   document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';
	   $("#formUser").submit();
	   return false;
}
 function ImprimirExcel() {
	   
	   document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioExcel';
	   $("#formUser").submit();
	   return false;
   
 } 
   
   $( function() {
		  
		  $("#dataInicial").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Ter?a','Quarta','Quinta','Sexta','S?bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S?b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar?o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr?ximo',
			    prevText: 'Anterior'
			});
	} );
   
   $( function() {
		  
		  $("#dataFinal").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Ter?a','Quarta','Quinta','Sexta','S?bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S?b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar?o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr?ximo',
			    prevText: 'Anterior'
			});
	} );
   
   
   
   
   </script>
   
</body>

</html>
	