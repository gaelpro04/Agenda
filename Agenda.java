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
    private ArrayList<Persona> getPeopleData(Connection connection) {
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

    public ArrayList<Persona> getPeople() throws SQLException {
        return getPeopleData(doConection(URL,USER,PASSWORD));
    }

    public ArrayList<Telefono> getTelephones() throws SQLException {
        return getTelephonesData(doConection(URL,USER,PASSWORD));
    }

    /**
     * Metodo que obtiene todos los datos de Telefonos de la base de datos
     * @param connection
     * @return
     */
    private ArrayList<Telefono> getTelephonesData(Connection connection) {
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

    public void addPersonData(Persona persona) throws SQLException {
        addPersonData(doConection(URL,USER,PASSWORD), persona);
    }

    public void addTelephoneData(Telefono telefono) throws SQLException {
        addTelephoneData(doConection(URL,USER,PASSWORD), telefono);
    }

    /**
     * Metodo para agregar un telefono a la tabla de telefonos
     * @param connection
     * @param telefono
     */
    private void addTelephoneData(Connection connection, Telefono telefono) {
        Connection conn = connection;
        Statement stmt = null;

        updateProcessT(conn, stmt, "add", telefono);
    }

    /**
     * Metodo para agregar una persona a la base de datos
     * @param connection
     * @param persona
     */
    private void addPersonData(Connection connection, Persona persona) {
        Connection conn = connection;
        Statement stmt = null;

        updateProcessP(conn, stmt, "add", persona);
    }

    public void deletePersonData(Persona persona) throws SQLException {
        deletePersonData(doConection(URL,USER,PASSWORD), persona);
    }

    public void deleteTelephoneData(Telefono telefono) throws SQLException {
        deleteTelephoneData(doConection(URL,USER,PASSWORD), telefono);
    }

    private void deletePersonData(Connection connection, Persona persona) {
        Connection conn = connection;
        Statement stmt = null;

        updateProcessP(conn, stmt, "delete", persona);
    }

    private void deleteTelephoneData(Connection connection, Telefono telefono) {
        Connection conn = connection;
        Statement stmt = null;

        updateProcessT(conn, stmt, "delete", telefono);
    }

    private void updateProcessP(Connection conn, Statement stmt, String updateType, Persona persona) {
        try {
            String nombre1 = persona.getNombre();
            String direccion1 = persona.getDireccion();
            int ID = persona.getId();

            stmt = conn.createStatement();
            switch (updateType) {
                case "add":
                    String sql = "INSERT INTO Personas (nombre,direccion) VALUES ('" + nombre1 + "','" + direccion1 + "')";
                    stmt.executeUpdate(sql);
                    break;
                case "delete":
                    String sql1 = "DELETE FROM Personas WHERE id = " + ID;
                    stmt.executeUpdate(sql1);
                    break;
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProcessT(Connection conn, Statement stmt, String updateType, Telefono telefono) {
        try {
            int personaID = telefono.getPersonaId();
            String numero = telefono.getTelefono();
            int ID = telefono.getId();

            stmt = conn.createStatement();
            switch (updateType) {
                case "add":
                    String sql = "INSERT INTO Telefonos (personaId,telefono) VALUES (" + personaID + "," + "'" + numero + "')";
                    stmt.executeUpdate(sql);
                    break;
                case "delete":
                    String sql1 = "DELETE FROM Telefono WHERE id = " + ID;
                    stmt.executeUpdate(sql1);
                    break;
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
