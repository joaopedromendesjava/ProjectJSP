package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBancoJSP {
	
	private static String banco = "jdbc:postgresql://localhost:5433/ProjetoJSP?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	
	public SingleConnectionBancoJSP() { // 	Quando tiver uma instancia vai conectar
		conectar();
	
	}

	private static void conectar() {
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver"); //Carrega o driver de conexão do banco
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);// para nao efetuar alteraçõe no banco sem o meu comando 

			}
		
			
		} catch (Exception e) {
			e.printStackTrace(); //mostrar qualquer erro no momento de conexão
		}
	}
	
}
