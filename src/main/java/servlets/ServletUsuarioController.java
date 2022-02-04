package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
			
		try {
		
			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");	
				
				daousuarioRepository.deletarUser(idUser);
				
				request.setAttribute("msg", "Excluido com sucesso!");
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);	
			
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					
					String idUser = request.getParameter("id");	
					
					daousuarioRepository.deletarUser(idUser);
										
					response.getWriter().write("Excluido com sucesso!");
					
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");	
				
				List<ModelLogin> dadosJsonUser = daousuarioRepository.consultaUsuariolist(nomeBusca);
				
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			}else {
						request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);
					}
			
			
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
		
		}
	
				
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		try {
		
		String msg = "Opera��o realizada com sucesso";
			
		String id = request.getParameter("id");
		String Nome = request.getParameter("nome");
		String Email = request.getParameter("email");
		String Login = request.getParameter("login");
		String Senha = request.getParameter("senha");
		
		ModelLogin modelLogin = new ModelLogin();
		
		
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(Nome);
		modelLogin.setEmail(Email);
		modelLogin.setLogin(Login);
		modelLogin.setSenha(Senha);
		
		if(daousuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null ) {
				
				msg = "J� existe este usuario com o mesmo Login, informe outro login";
			
			}else {
				
				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso";	
				
				}else {
					msg = "Atualizado com sucesso";
				}
			
				modelLogin = daousuarioRepository.gravarUsuario(modelLogin);
			}
		
		request.setAttribute("msg", msg);
		request.setAttribute("modolLogin", modelLogin);
	   	RequestDispatcher redirecionar = request.getRequestDispatcher("Principal/usuario.jsp");
			
		
		
		redirecionar.forward(request, response);
		
		}catch (Exception e) {
			
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
		}	
		
	}

}
