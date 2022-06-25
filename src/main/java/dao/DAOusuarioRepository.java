package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBancoJSP;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOusuarioRepository {

	private Connection connection;

	public DAOusuarioRepository() {
		connection = SingleConnectionBancoJSP.getConnection();
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception{
		
		String sql = "select avg (rendamensal) as media_salarial, perfil from model_login where usuario_id = ? and dataNascimento >= ? and dataNascimento >= ? group by perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception{
		
		String sql = "select avg (rendamensal) as media_salarial, perfil from model_login where usuario_id = ? group by perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
		
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userlogado) throws Exception {

		if (objeto.isNovo()) { // Grava um novo

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, complemento, dataNascimento, rendaMensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
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
			preparaSQL.setDate(15, objeto.getDataNascimento());
			preparaSQL.setDouble(16, objeto.getRendaMensal());
			
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

			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, complemento=?, dataNascimento=?, rendaMensal=? WHERE id =  " + objeto.getId()+ ";";
			
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
			preparaSql.setDate(14, objeto.getDataNascimento());
			preparaSql.setDouble(15, objeto.getRendaMensal());

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
	
	public List<ModelLogin> consultaUsuariolistRel(Long userlogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userlogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // percorrer as linhas de resultado do SQL

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			
				retorno.add(modelLogin);
			}
			return retorno;
		}
	
	public List<ModelLogin> consultaUsuariolistRel(Long userlogado, String dataInicial, String dataFinal) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userlogado + "and dataNascimento >= ? and dataNascimento <= ? ";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // percorrer as linhas de resultado do SQL

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			
				retorno.add(modelLogin);
			}
			return retorno;
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
			// modelLogin.setSenha(resultado.getString("senha")); nao é necessaria por questoes de segurança
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
	
	public List<ModelLogin> consultaUsuariolistOffSet(String nome, Long userlogado, int offset) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper (nome) like upper(?) and useradmin is false and usuario_id = ? offset "+ offset +" limit 5 ";

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
	
	public int consultaUsuariolistTotalPaginaPaginacao(String nome, Long userlogado) throws SQLException {

		String sql = "select count(1) as total from model_login where upper (nome) like upper(?) and useradmin is false and usuario_id = ? ";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userlogado);

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
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));
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
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));

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
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));
		}
		return modelLogin;

	}
	
	public ModelLogin consultaUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
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
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendaMensal"));
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
		public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
			
			List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
			
			String sql = "select * from telefone where usuario_pai_id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idUserPai);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				
				ModelTelefone modelTelefone = new ModelTelefone();
				modelTelefone.setId(rs.getLong("id"));
				modelTelefone.setNumero(rs.getString("numero"));
				modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
				modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));
				
			retorno.add(modelTelefone);
				
		}
			
			return retorno;
		}

}