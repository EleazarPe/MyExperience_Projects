package Logico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
	public static  Connection getConexion() {
		String connectionUrl = "jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=SistemaArrendamiento4;user=sa;password=123456";
	
		try {
        	Connection con = DriverManager.getConnection(connectionUrl); 
        	return con;
   
        }
        catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
	}
	
}