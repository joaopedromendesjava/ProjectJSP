package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBancoJSP {
	
	private static String banco = "jdbc:postgresql://ec2-3-224-8-189.compute-1.amazonaws.com:5432/d69on7ve58m7rm?sslmode=require&autoReconnect=true";
	private static String user = "ivqgchdforhylu";
	private static String senha = "b64a543bb5c658c66e7d0ed00d5c65165bfd903069df5831a31baf644dde6128";
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
