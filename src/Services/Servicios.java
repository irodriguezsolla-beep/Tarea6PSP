package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import Connector.BD_Connection;

public class Servicios {
    // Convierte una fecha de tipo java.sql.Date a un String en formato "YYYY-MM-DD"
    public static String dateToString(Date fechaSql) {
        if (fechaSql == null) return null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fechaSql);
    }

    // Convierte una cadena de texto "YYYY-MM-DD" a un objeto java.sql.Date para PostgreSQL
    public static Date stringToDate(String fechaStr) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaUtil = formato.parse(fechaStr);
            return new Date(fechaUtil.getTime()); // Obtiene los milisegundos para construir el Date de SQL
        } catch (ParseException e) {
            System.out.println("Error de formato de fecha: " + e.getMessage());
            return null;
        }
    }

    // Operación CREATE: Inserta un nuevo registro en la tabla anime
    public static void insertarAnimeParametros(String nombre, String descripcion, String fechaStr, int puntuacion) {
        String sql = "INSERT INTO anime (nome, descripcion, data, puntuacion) VALUES (?, ?, ?, ?)";

        // try-with-resources: abre la conexión y la sentencia, y las cierra automáticamente al terminar
        try (Connection conn = BD_Connection.getConnection();
             PreparedStatement Insertar = conn.prepareStatement(sql)) {

            // Asignano cada valor recibido por parámetro a su correspondiente '?' en la consulta SQL
            Insertar.setString(1, nombre);
            Insertar.setString(2, descripcion);
            Insertar.setDate(3, stringToDate(fechaStr)); // Convertimos el String a Date de SQL
            Insertar.setInt(4, puntuacion);

            Insertar.executeUpdate(); // Ejecuta la inserción en la base de datos
            System.out.println("anime insertado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Operación READ: Consulta y muestra todos los registros de la tabla
    public static void leerAnimes() {
        String sql = "SELECT nome, descripcion, data, puntuacion FROM anime";

        try (Connection conn = BD_Connection.getConnection();
             PreparedStatement Consultar = conn.prepareStatement(sql);
             ResultSet lista = Consultar.executeQuery()) { // executeQuery devuelve las filas en un ResultSet

            // fila por fila mientras existan datos (rs.next())
            while (lista.next()) {
                String nombre = lista.getString("nome");
                String descripcion = lista.getString("descripcion");
                String fecha = dateToString(lista.getDate("data"));
                int puntuacion =lista.getInt("puntuacion");

                System.out.println(nombre + " - " + descripcion + " - " + fecha + " - " + puntuacion);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Operación READ FILTRADO: Busca animes cuya puntuación sea igual o mayor al parámetro dado
    public static void leerAnimesPorPuntuacion(int puntuacionMinima) {
        String sql = "SELECT nome, descripcion, data, puntuacion FROM anime WHERE puntuacion >= ?";

        try (Connection conn = BD_Connection.getConnection();
             PreparedStatement Consultar = conn.prepareStatement(sql)) {

            Consultar.setInt(1, puntuacionMinima);

            try (ResultSet lista = Consultar.executeQuery()) {
                while (lista.next()) {
                    String nombre = lista.getString("nome");
                    String fecha = dateToString(lista.getDate("data"));
                    int puntuacion = lista.getInt("puntuacion");

                    System.out.println(nombre + " - " + fecha + " - " + puntuacion);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Operación UPDATE: Modifica la puntuación de un anime buscando por su nombre
    public static void actualizarAnimeParametros(String nombre, int nuevaPuntuacion) {
        String sql = "UPDATE anime SET puntuacion = ? WHERE nome = ?";

        try (Connection conn = BD_Connection.getConnection();
             PreparedStatement Actualizar = conn.prepareStatement(sql)) {

            Actualizar.setInt(1, nuevaPuntuacion);
            Actualizar.setString(2, nombre);

            Actualizar.executeUpdate(); // Ejecuta la actualización
            System.out.println("anime actualizado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Operación DELETE: Elimina un registro filtrando por el nombre
    public static void eliminarAnimeParametros(String nombre) {
        String sql = "DELETE FROM anime WHERE nome = ?";

        try (Connection conn = BD_Connection.getConnection();
             PreparedStatement Eliminar = conn.prepareStatement(sql)) {

            Eliminar.setString(1, nombre);

            Eliminar.executeUpdate(); // Ejecuta el borrado
            System.out.println("anime eliminado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
