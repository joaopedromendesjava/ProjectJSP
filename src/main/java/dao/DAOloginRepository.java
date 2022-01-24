package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.SingleConnectionBancoJSP;
import model.ModelLogin;

public class DAOloginRepository {
	
	private Connection connection;
	
	public DAOloginRepository() {
		connection = SingleConnectionBancoJSP.getConnection();
		
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from Model_Login where upper(Login) = upper(?) and upper(Senha) = upper(?) ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true; //autenticado
			
		}
		return false; // nao autenticado
	}
}
