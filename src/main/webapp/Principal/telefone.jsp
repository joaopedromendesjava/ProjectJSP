<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>


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

					<div class="page-header">
                          <div class="page-block">
                              <div class="row align-items-center">
                                  <div class="col-md-8">
                                      <div class="page-header-title">
                                          <h5 class="m-b-10">Projeto JSP de João Pedro</h5>
                                          <p class="m-b-0">Bem Vindo, Cadastre o telefone de seu usuário!</p>
                                      </div>
                                  </div>
                                  <div class="col-md-4">
                                      <ul class="breadcrumb-title">
                                          <li class="breadcrumb-item">
                                              <a href="index.html"> <i class="fa fa-home"></i> </a>
                                          </li>
                                          <li class="breadcrumb-item"><a href="#!">Projeto Java EE</a>
                                          </li>
                                      </ul>
                                  </div>
                              </div>
                          </div>
                      </div>

						<!-- Page-header end -->
							<!-- Main-body start -->
							<div class="main-body">
						<div class="pcoded-inner-content">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-header"></div>
													<div class="card-block">
														<h4 class="sub-title">Cad.Telefone</h4>
															
															<form class="form-material" action="<%=request.getContextPath()%>/ServletTelefone" method="post" id="formFone">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}"> 
																	<span class="form-bar"></span>
																	 <label class="float-label">ID User</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input readonly="readonly" type="text" name="nome" id="nome" class="form-control"  required="required"  value="${modelLogin.nome}">
																<span class="form-bar"></span> 
																<label class="float-label">Nome</label>
																
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero" class="form-control" required="required">
																	<span class="form-bar"></span> 
																<label class="float-label">Numero</label>
																
															</div>
															
															<button class="btn btn-success btn-round waves-effect waves-light">Salvar</button>
															
														</form>	
													</div>
												</div>
											</div>
										</div>
										
										<span id="msg">${msg}</span>
										
										<div style="height: 400px; overflow: scroll;">
										<table class="table" id="tabelaresultadosview">
											<thead>
												<tr>

													<th scope="col">ID</th>
													<th scope="col">Numero</th>
													<th scope="col">Excluir</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach items="${modelTelefones}" var="f">

													<tr>
														<td><c:out value="${f.id}"></c:out></td>
														<td><c:out value="${f.numero}"></c:out></td>
														<td><a class="btn btn-danger" href="<%=request.getContextPath()%>/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}" >Excluir</a></td>             

													</tr>
												</c:forEach>

											</tbody>
										</table>
									</div>
										
									</div>

								</div>
								<!-- Page-body end -->
							</div>
							<div id="styleSelector"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	<script type="text/javascript">
	 
	$("#numero").keypress(function (event){
		return /\d/.test(String.fromCharCode(event.keyCode));
	});
	
	</script>

</body>

</html>
