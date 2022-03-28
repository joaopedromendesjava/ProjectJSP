package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBancoJSP;
import model.ModelLogin;

public class DAOusuarioRepository {

	private Connection connection;

	public DAOusuarioRepository() {

		connection = SingleConnectionBancoJSP.getConnection();

	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userlogado) throws Exception {

		if (objeto.isNovo()) { // Grava um novo

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, complemento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement preparaSQL = connection.prepareStatement(sql);

			preparaSQL.setString(1, objeto.getLogin());
			preparaSQL.setString(2, objeto.getSenha());
			preparaSQL.setString(3, objeto.getNome());
			preparaSQL.setString(4, objeto.getEmail());
			preparaSQL.setLong(5, userlogado);
			preparaSQL.setString(6, objeto.getPerfil());
			preparaSQL.setString(7, objeto.getSexo());
			preparaSQL.setString(8, objeto.getCep());
			preparaSQL.setString(9, objeto.getLogradouro());
			preparaSQL.setString(10, objeto.getBairro());
			preparaSQL.setString(11, objeto.getLocalidade());
			preparaSQL.setString(12, objeto.getUf());
			preparaSQL.setString(13, objeto.getNumero());
			preparaSQL.setString(14, objeto.getComplemento());
			
			
			preparaSQL.execute();
			connection.commit();
			
		 if (objeto.getFotouser() != null && ! objeto.getFotouser().isEmpty()) {
			
				sql = "update model_login set fotouser = ?,extensaofotouser =? where login = ? ";
				preparaSQL = connection.prepareStatement(sql);
				preparaSQL.setString(1, objeto.getFotouser());
				preparaSQL.setString(2, objeto.getExtensaofotouser());
				preparaSQL.setString(3, objeto.getLogin());
			
				preparaSQL.execute();
				connection.commit();
			}
				

		} else {

			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, complemento=? WHERE id =  " + objeto.getId()+ ";";
			
			PreparedStatement preparaSql = connection.prepareStatement(sql);

			preparaSql.setString(1, objeto.getLogin());
			preparaSql.setString(2, objeto.getSenha());
			preparaSql.setString(3, objeto.getNome());
			preparaSql.setString(4, objeto.getEmail());
			preparaSql.setString(5, objeto.getPerfil());
			preparaSql.setString(6, objeto.getSexo());
			preparaSql.setString(7, objeto.getCep());
			preparaSql.setString(8, objeto.getLogradouro());
			preparaSql.setString(9, objeto.getBairro());
			preparaSql.setString(10, objeto.getLocalidade());
			preparaSql.setString(11, objeto.getUf());
			preparaSql.setString(12, objeto.getNumero());
			preparaSql.setString(13, objeto.getComplemento());
			

			preparaSql.executeUpdate();
			connection.commit();

			
			if (objeto.getFotouser() != null && ! objeto.getFotouser().isEmpty()) {
				
					sql = "update model_login set fotouser = ?, extensaofotouser =? where login = ? ";
					preparaSql = connection.prepareStatement(sql);
					preparaSql.setString(1, objeto.getFotouser());
					preparaSql.setString(2, objeto.getExtensaofotouser());
					preparaSql.setLong(3, objeto.getId());
				
					preparaSql.execute();
					connection.commit();
				}
		}

		return this.consultaUsuario(objeto.getLogin(), userlogado);

	}
	
	public List<ModelLogin> consultaUsuariolistPaginado(Long userlogado, Integer offset ) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userlogado + "order by nome offset "+ offset +" limit 5 ";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // percorrer as linhas de resultado do SQL

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha")); nao é necessaria por
			// questoes de segurança
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));


			retorno.add(modelLogin);
		}
		
		return retorno;
		
	}
	
	public int totalPagina(Long userlogado) throws Exception{

		String sql = "select count(1) as total from model_login where usuario_id = " + userlogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
			
		Double porpagina = 5.0;
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
		
	}
	
	public List<ModelLogin> consultaUsuariolist(Long userlogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userlogado + "limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // percorrer as linhas de resultado do SQL

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha")); nao é necessaria por
			// questoes de segurança
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));


			retorno.add(modelLogin);
		}

		return retorno;
	}

	public List<ModelLogin> consultaUsuariolist(String nome, Long userlogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper (nome) like upper(?) and useradmin is false and usuario_id = ? limit 5 ";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userlogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // percorrer as linhas de resultado do SQL

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha")); nao é necessaria por
			// questoes de segurança
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
				

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public ModelLogin consultaUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select *from model_login where upper (login) = upper ('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// se tem resultado

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("Numero"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			
		}
		return modelLogin;

	}

	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select *from model_login where upper (login) = upper ('" + login + "') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// se tem resultado

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("Numero"));
			modelLogin.setComplemento(resultado.getString("complemento"));


		}
		return modelLogin;

	}

	public ModelLogin consultaUsuario(String login, Long userlogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select *from model_login where upper (login) = upper ('" + login + "') and useradmin is false and usuario_id = " + userlogado;
				
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// se tem resultado

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("Numero"));
			modelLogin.setComplemento(resultado.getString("complemento"));

		}
		return modelLogin;

	}

	public ModelLogin consultaUsuarioID(String id, Long userlogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select *from model_login where id = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userlogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// se tem resultado

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("Uf"));
			modelLogin.setNumero(resultado.getString("Numero"));
			modelLogin.setComplemento(resultado.getString("complemento"));


		}
		return modelLogin;

	}

	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		resultado.next(); // para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");
	}

	public void deletarUser(String idUser) throws Exception {

		String sql = "DELETE FROM public.model_login WHERE id = ? and useradmin is false";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();

		connection.commit();
	}

}
