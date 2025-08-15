import java.sql.*;
import java.util.ArrayList;

public class Agenda
{
    //Strings que nos permite acceder a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USER = "usuario1";
    private static final String PASSWORD = "superpassword";

    /**
     * Metodo capaaz de hacer la conexion Java a base de datos.
     */
    public Connection doConection(String url, String user, String password) throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }

    /**
     * Metodo que obtiene todos los datos de personas de la base de datos
     * @param connection
     * @return
     */
    public ArrayList<Persona> getPeopleData(Connection connection) {
        Connection conn = connection;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Persona> personas = new ArrayList<>();

        try {
            //Enviamos una consulta SQL a la base de datos
            stmt = conn.createStatement();

            //Resultado de la consulta, aqui contienen el contenido de Personas en la
            //base de datos
            rs = stmt.executeQuery("SELECT * FROM Personas");

            //rs.next() permite verificar si hay siguientes datos
            while (rs.next()) {

                //Se obtiene los datos de la primera fila
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");

                //Se aniade al Arraylist
                personas.add(new Persona(nombre, id, direccion));
            }

            rs.close();
            stmt.close();

            return personas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo que obtiene todos los datos de Telefonos de la base de datos
     * @param connection
     * @return
     */
    public ArrayList<Telefono> getTelephonesData(Connection connection) {
        Connection conn = connection;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Telefono> telefonos = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Telefonos");

            while (rs.next()) {
                int id = rs.getInt("id");
                int personaId = rs.getInt("personaId");
                String telefono = rs.getString("telefono");

                telefonos.add(new Telefono(telefono, id, personaId));
            }

            rs.close();
            stmt.close();
            return telefonos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
               if (rs != null) rs.close();
               if (stmt != null) stmt.close();
               if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTelephoneData(Connection connection, Telefono telefono) {

    }

    public void addPersonData(Connection connection, Persona persona) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ToStrings de cada tabla

    public String toStringPersonas() throws SQLException {
        StringBuilder builder = new StringBuilder();
        Connection conn = doConection(URL,USER,PASSWORD);
        ArrayList<Persona> personas = getPeopleData(conn);

        for (int i = 0; i < personas.size(); ++i) {
            builder.append("ID: " + personas.get(i).getId() + " NOMBRE: " + personas.get(i).getNombre() + " DIRECCION: "
            + personas.get(i).getDireccion() + "\n");
        }

        return builder.toString();
    }

    public String toStringTelefonos() throws SQLException {
        StringBuilder builder = new StringBuilder();
        Connection conn = doConection(URL,USER,PASSWORD);
        ArrayList<Telefono> telefonos = getTelephonesData(conn);

        for (int i = 0; i < telefonos.size(); ++i) {
            builder.append("ID: " + telefonos.get(i).getId() + " PERSONAID: " + telefonos.get(i).getPersonaId() + " TELEFONO: "
                    + telefonos.get(i).getTelefono() + "\n");
        }

        return builder.toString();
    }
}
