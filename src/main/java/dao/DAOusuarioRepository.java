package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBancoJSP;
import model.ModelLogin;

public class DAOusuarioRepository {
	
	private Connection connection;

	public DAOusuarioRepository() {
		
		 connection = SingleConnectionBancoJSP.getConnection();
		
	}
	
	public void gravarUsuario(ModelLogin objeto) throws Exception {
		
		
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement preparaSQL = connection.prepareStatement(sql);
		
		preparaSQL.setString(1, objeto.getLogin());	
		preparaSQL.setString(2, objeto.getSenha());
		preparaSQL.setString(3, objeto.getNome());
		preparaSQL.setString(4, objeto.getEmail());

		preparaSQL.execute();
		connection.commit();
		

	}
	
	/* public ModelLogin consultaUsuario(String Login) throws Exception{
		 
		 String sql= "select *from model_login where upper (login) = upper ('?')";
		 PreparedStatement statement = connection.prepareStatement(sql);
		 statement.setString(1, Login);
		 
		 
		ResultSet resultado = statement.executeQuery(); */
				 
				 
	 }
	
	

