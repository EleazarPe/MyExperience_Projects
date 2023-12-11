package org.example.servicios;

import org.example.encapsulaciones.Registro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CockroachlabsServer {

    public static void CockroachlabsServer() {

    }

    // Método para verificar si una tabla existe en la base de datos
    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }

    // Método para crear la tabla userView
    public static void createTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS userview2 (id UUID PRIMARY KEY DEFAULT gen_random_uuid(),username VARCHAR(255), timestamp TIMESTAMP)";
        try (PreparedStatement statement = conn.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }
    }

    // Método para insertar los datos en la tabla userView
// Método para insertar los datos en la tabla userView2
    public static void insertUserView(Connection conn, String username) throws SQLException {
        String insertSQL = "INSERT INTO userview2 (username, timestamp) VALUES (?, current_timestamp)";
        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar datos en la tabla userView2", e);
        }
    }
    public static List<Registro> displayUserViewData(Connection conn) {
        String selectSQL = "SELECT * FROM userView2";
        List<Registro> listaReg = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                System.out.println("id: "+id+" Username: " + username + ", Timestamp: " + timestamp);
                listaReg.add(new Registro(id,username,timestamp));
            }
            return listaReg;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al mostrar los datos de la tabla userView2");
        }
        return null;
    }

}


