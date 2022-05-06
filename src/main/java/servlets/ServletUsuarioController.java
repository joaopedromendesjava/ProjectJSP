package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOusuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOusuarioRepository daousuarioRepository = new DAOusuarioRepository();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");

				daousuarioRepository.deletarUser(idUser);

				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolist(super.getUserlogado(request));
				request.setAttribute("modelLogins", modelLogins);

				request.setAttribute("msg", "Excluido com sucesso!");
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");

				daousuarioRepository.deletarUser(idUser);

				response.getWriter().write("Excluido com sucesso!");

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelLogin> dadosJsonUser = daousuarioRepository.consultaUsuariolistOffSet(nomeBusca, super.getUserlogado(request),Integer.parseInt(pagina));

				ObjectMapper objectMapper = new ObjectMapper();
				
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina",""+ daousuarioRepository.consultaUsuariolistTotalPaginaPaginacao(nomeBusca, super.getUserlogado(request)));
				response.getWriter().write(json);

			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");
	
				List<ModelLogin> dadosJsonUser = daousuarioRepository.consultaUsuariolist(nomeBusca, super.getUserlogado(request));
	
				ObjectMapper objectMapper = new ObjectMapper();
				
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina",""+ daousuarioRepository.consultaUsuariolistTotalPaginaPaginacao(nomeBusca, super.getUserlogado(request)));
				response.getWriter().write(json);

			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String id = request.getParameter("id");

				ModelLogin modelLogin = daousuarioRepository.consultaUsuarioID(id, super.getUserlogado(request));

				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolist(super.getUserlogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modolLogin", modelLogin);
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolist(super.getUserlogado(request));

				request.setAttribute("msg", "Usuários carregados");
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				
				String idUser = request.getParameter("id");

				ModelLogin modelLogin = daousuarioRepository.consultaUsuarioID(idUser, super.getUserlogado(request));
			
				if(modelLogin.getFotouser() != null && modelLogin.getFotouser().isEmpty()) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
					
				}
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolistPaginado(this.getUserlogado(request), offset);
			
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);
			
			}
			
			else {
				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolist(super.getUserlogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				request.getRequestDispatcher("Principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}

	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
	
			try {
	
				String msg = "Operação realizada com sucesso";
	
				String id = request.getParameter("id");
				String Nome = request.getParameter("nome");
				String Email = request.getParameter("email");
				String Login = request.getParameter("login");
				String Senha = request.getParameter("senha");
				String perfil = request.getParameter("perfil");
				String sexo = request.getParameter("sexo");
				String cep = request.getParameter("cep");
				String logradouro = request.getParameter("logradouro");
				String bairro = request.getParameter("bairro");
				String localidade = request.getParameter("localidade");
				String uf = request.getParameter("uf");
				String numero = request.getParameter("numero");
				String complemento = request.getParameter("complemento");
	
				ModelLogin modelLogin = new ModelLogin();
	
				modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
				modelLogin.setNome(Nome);
				modelLogin.setEmail(Email);
				modelLogin.setLogin(Login);
				modelLogin.setSenha(Senha);
				modelLogin.setPerfil(perfil);
				modelLogin.setSexo(sexo);
				modelLogin.setCep(cep);
				modelLogin.setLogradouro(logradouro);
				modelLogin.setBairro(bairro);
				modelLogin.setLocalidade(localidade);
				modelLogin.setUf(uf);
				modelLogin.setNumero(numero);
				modelLogin.setComplemento(complemento);
				
				if(ServletFileUpload.isMultipartContent(request)) {
					
					Part part = request.getPart("fileFoto"); // pega a foto da tela 
					
					if(part.getSize() > 0) {
					
					byte[] foto = IOUtils.toByteArray(part.getInputStream()); // converte imagem para byte
					String imagemBase64 = "data:image/"+ part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto); // recebe a imagem em byte
					
					modelLogin.setFotouser(imagemBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				
					}
				}
						
	
				if (daousuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
	
					msg = "Já existe este usuario com o mesmo Login, informe outro login";
	
				} else {
	
					if (modelLogin.isNovo()) {
						msg = "Gravado com sucesso";
	
					} else {
						msg = "Atualizado com sucesso";
					}
	
					modelLogin = daousuarioRepository.gravarUsuario(modelLogin, super.getUserlogado(request));
				}
	
				List<ModelLogin> modelLogins = daousuarioRepository.consultaUsuariolist(super.getUserlogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("msg", msg);
				request.setAttribute("modolLogin", modelLogin);
				request.setAttribute("totalPagina", daousuarioRepository.totalPagina(this.getUserlogado(request)));
				RequestDispatcher redirecionar = request.getRequestDispatcher("Principal/usuario.jsp");
	
				redirecionar.forward(request, response);
	
			} catch (Exception e) {
	
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
	
			}
	
		}
	
	}
