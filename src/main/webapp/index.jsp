<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<body>
	<div>
	
	/*planejo colocar uma imagem aqui para pagina toda*/
		
	</div>
</body>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<title>Projeto em JSP</title>

<style type="text/css">
form {
	position: absolute;
	top: 40%;
	left: 30%;
}

h5 {
	position: absolute;
	top: 30%;
	left: 37%;
}

.msg {
	position: absolute;
	top: 83%;
	left: 40%;
	font-size: 15px;
	color: Red;/*Cor da letra*/
    background-color: white; /*Cor do fundo */
    border-color: white;/*Cor da borda*/
}
	</style>	
	</head>
<body>

	<h5>Bem vindo ao meu Projeto em JSP</h5>

	<form action="<%=request.getContextPath()%>/ServletLogin" method="post"
		class="row g-3 needs-validation" novalidate>

		<input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">

		<div class="md-3">
			<label class="form-label">Login</label>
			<input class="form-control"name="Login" type="text" required="required">
		
		<div class="invalid-feedback">
       		 Informe o Login
      	</div>
		</div>

		<div class="md-3">
			<label class="form-label">Senha</label> 
			<input class="form-control" name="Senha" type="password" required="required">
		<div class="invalid-feedback">
       		 Informe a Senha 
      	</div>
		</div>


		<input type="submit" value="Acessar" class="btn btn-primary">


	</form>

	<h5 class="msg">${msg}</h5>

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>
	<script type="text/javascript">
		//Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()
	</script>
</body>
</html>