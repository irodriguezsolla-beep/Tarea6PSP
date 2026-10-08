package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD_Connection {

    public static Connection getConnection() {
        String url = "jdbc:postgresql://10.217.71.152:5432/probas";
        String usuario = "postgres";
        String contrasena = "admin";

        Connection conn = null;

        try {
            // Se abre la conexión SIN el paréntesis en el try
            conn = DriverManager.getConnection(url, usuario, contrasena);
            if (conn != null) {
                System.out.println("Conexión establecida con éxito a PostgreSQL.");
            }
        } catch (SQLException error) {
            System.out.println("Error de conexión: " + error.getMessage());
        }

        return conn;
    }
}
