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
	
	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {
		
		if(objeto.isNovo()) { //Grava um novo
			
		
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement preparaSQL = connection.prepareStatement(sql);
		
		preparaSQL.setString(1, objeto.getLogin());	
		preparaSQL.setString(2, objeto.getSenha());
		preparaSQL.setString(3, objeto.getNome());
		preparaSQL.setString(4, objeto.getEmail());

		preparaSQL.execute();
		connection.commit();
		
		}else {
			
			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			PreparedStatement preparaSql = connection.prepareStatement(sql);
			
			preparaSql.setString(1, objeto.getLogin());	
			preparaSql.setString(2, objeto.getSenha());
			preparaSql.setString(3, objeto.getNome());
			preparaSql.setString(4, objeto.getEmail());
			
			preparaSql.executeUpdate();
			connection.commit();
		
		}
		
		return this.consultaUsuario(objeto.getLogin());
		

		}
	
	public List<ModelLogin> consultaUsuariolist(String nome) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper (nome) like upper(?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1,"%" +nome+ "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
		//  modelLogin.setSenha(resultado.getString("senha")); nao é necessaria por questoes de segurança	
		
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception{
		
		ModelLogin modelLogin = new ModelLogin();
		
		 String sql= "select *from model_login where upper (login) = upper ('"+login+"')";
		 PreparedStatement statement = connection.prepareStatement(sql);
		 
		 ResultSet resultado = statement.executeQuery(); 
		
		while(resultado.next())// se tem resultado
			{
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			
		}
		return modelLogin;
				 
	 }
	
	public boolean validarLogin(String login) throws Exception{
		
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		resultado.next(); // para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");
	}
	
	public void deletarUser(String idUser) throws Exception  {
		
		String sql = "DELETE FROM public.model_login WHERE id = ?;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql); 
		
		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		
		connection.commit();
	}
	
	
	
}
