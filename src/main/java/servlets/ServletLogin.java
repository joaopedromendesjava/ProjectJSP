package servlets;

import java.io.IOException;

import dao.DAOloginRepository;
import dao.DAOusuarioRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelLogin;

//O chamado Controller sao as servlets ou ServletLoginController
@WebServlet(urlPatterns = {"/Principal/ServletLogin","/ServletLogin" }) // mapeamento de url que vem da tela
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DAOloginRepository daologinRepository = new DAOloginRepository();
	private DAOusuarioRepository daousuarioRepository = new DAOusuarioRepository();
	
	public ServletLogin() {
		
	}

	// Recebe os dados pela url em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String acao = request.getParameter("acao");
		
		if(acao != null && acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			
			request.getSession().invalidate();// invalida sessao
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}
		
		else {
			doPost(request, response);
		}
		
	}

	// Recebe os dados enviados por um formulario
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String Login = request.getParameter("Login");
		String Senha = request.getParameter("Senha");
		String url = request.getParameter("url");

		try {
		
		if (Login != null && !Login.isEmpty() && Senha != null && !Senha.isEmpty()) {

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(Login);
			modelLogin.setSenha(Senha);

			if (daologinRepository.validarAutenticacao(modelLogin)) { // simulando login

					modelLogin = daousuarioRepository.consultaUsuarioLogado(Login);
				
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());
					
					if (url == null || url.equals("null")) {
						url = "Principal/Principal.jsp";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente");
					redirecionar.forward(request, response);

				}
		
		} else {
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e senha para ter acesso novamente");
			redirecionar.forward(request, response);

			}
		} catch (Exception e) {
		
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}
		

}
