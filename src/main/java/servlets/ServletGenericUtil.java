package servlets;

import java.io.Serializable;

import dao.DAOusuarioRepository;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private DAOusuarioRepository daousuarioRepository = new DAOusuarioRepository();
	
		
	public Long getUserlogado(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		String usuariologado = (String) session.getAttribute("usuario");
		
		return daousuarioRepository.consultaUsuarioLogado(usuariologado).getId();
		
		
	}
	
	
		public ModelLogin getUserlogadoobjet(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		String usuariologado = (String) session.getAttribute("usuario");
		
		return daousuarioRepository.consultaUsuarioLogado(usuariologado);
		
		
	}

}
