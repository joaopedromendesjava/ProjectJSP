package servlets;

import java.io.IOException;
import dao.DAOusuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;


@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private DAOusuarioRepository daousuarioRepository = new DAOusuarioRepository();
	
	
    public ServletUsuarioController() {
         
    	
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		doPost(request, response);
	
				
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		try {
		
		String id = request.getParameter("id");
		String Nome = request.getParameter("Nome");
		String Email = request.getParameter("Email");
		String Login = request.getParameter("Login");
		String Senha = request.getParameter("Senha");
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(Nome);
		modelLogin.setEmail(Email);
		modelLogin.setLogin(Login);
		modelLogin.setSenha(Senha);
		
		daousuarioRepository.gravarUsuario(modelLogin);
				
		request.setAttribute("modolLogin", modelLogin);
		request.setAttribute("msg", "Operação realizada com sucesso");
				
		request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);
		
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
		}	
		
	}

}
