package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBancoJSP;
import dao.DaoVersionadorBanco;



@WebFilter(urlPatterns = {"/Principal/*"}) // Intercepta todas as requisições que vierem do projeto ou mapeamento
public class filterAutenticacao implements Filter {
	
	private static Connection connection;
	

    public filterAutenticacao() {

    }
    //Encerra os processos quando o servidor é parado
    //Mataria os processos de conexão com o banco     
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
				e.printStackTrace();
		}		
		
	}
	// Intercepta as requisições e as respostas no sistema
	// tudo que fizer no sistema vai passar por aqui
	// validação de autenticação
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();// url que esta sendo acessada
				
			// validar se esta logado, senao redireciona para a tela de login
			if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/Principal/ServletLogin"))
			{ //nao esta logado
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login");
				redireciona.forward(request, response);
				return; //Parar a execução e redirecionar para o login do sistema
			
			}else {
				chain.doFilter(request, response);
			}
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
				
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	// É executado quando inicia o sistema, inicia os processos quando o servidor sobe o projeto
	//Iniciar a conexão com o banco 
	public void init(FilterConfig fConfig) throws ServletException {
		
		connection = SingleConnectionBancoJSP.getConnection();
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
			for (File file : filesSql) {
			
			boolean arquivoJaRodadao = daoVersionadorBanco.arquivoSqlRodado(file.getName());
			
			if(!arquivoJaRodadao) {
				
				FileInputStream entradaArquivo = new FileInputStream(file);
				
				Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
				
				StringBuilder sql = new StringBuilder();
				while (lerArquivo.hasNext()) {
					
					sql.append(lerArquivo.nextLine());
					sql.append("\n");
			
				}
				connection.prepareStatement(sql.toString()).execute();
				daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
				
				connection.commit();
				lerArquivo.close();
			}
			
		}
		
		
	  }catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
	}
}
