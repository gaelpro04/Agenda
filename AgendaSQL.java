import java.sql.*;
import java.util.ArrayList;

public class AgendaSQL implements InterfazAgenda {
    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USER = "usuario1";
    private static final String PASSWORD = "superpassword";

    @Override
    public Connection doConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public void addDireccionDataFromSQL(Direccion direccion) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessD(conn,stmt,"add", direccion);
    }

    @Override
    public void addPerson(Persona persona) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessPD(conn, stmt, "add", persona);
    }

    @Override
    public void addTelephone(Telefono telefono) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessT(conn, stmt, "add", telefono);
    }

    @Override
    public void deletePerson(Persona persona) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessPD(conn, stmt, "delete", persona);
    }

    @Override
    public void deleteTelephone(Telefono telefono) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessT(conn, stmt, "delete", telefono);
    }

    @Override
    public void deleteDireccion(Direccion direccion) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;

        updateProcessD(conn, stmt, "delete", direccion);
    }

    @Override
    public void editPerson(Persona persona) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;
        int ID = persona.getId();

        try {
            /// ////////////////////
            //AQUI ME QUEDE
            stmt = conn.createStatement();
            String dataNombre = persona.getNombre();

            String sql = "UPDATE Personas SET nombre =" + "'" + dataNombre + "' WHERE id =" + "'" + ID + "'";
            stmt.executeUpdate(sql);

            ArrayList<String> direccionesBD = new ArrayList<>();
            String sqlSelect = "SELECT direccion FROM Direcciones WHERE idPersona = " + persona.getId();
            ResultSet rs = stmt.executeQuery(sqlSelect);
            while (rs.next()) {
                direccionesBD.add(rs.getString("direccion"));
            }
            rs.close();

            ArrayList<String> direccionesStringPersona = new ArrayList<>();
            for (int i = 0; i < persona.getDirecciones().size(); i++) {
                direccionesStringPersona.add(persona.getDirecciones().get(i).getDireccion());
            }

            ArrayList<String> direccionEliminar = new ArrayList<>();
            for (String direccionString : direccionesBD) {
                if (!direccionesStringPersona.contains(direccionString)) {
                    direccionEliminar.add(direccionString);
                }

            }

            ArrayList<String> direccionesAgregar = new ArrayList<>();
            for (String direccionSP : direccionesStringPersona) {
                if (!direccionesBD.contains(direccionSP)) {
                    direccionesAgregar.add(direccionSP);
                }
            }

            for (String eliminar : direccionEliminar) {
                deleteDireccion(new Direccion(getDirecciones().getLast().getId(), persona.getId(), eliminar));
            }

            for (String agregar : direccionesAgregar) {
                addDireccionDataFromSQL(new Direccion(getDirecciones().getLast().getId(), persona.getId(), agregar));
            }

            conn.close();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void editTelephone(Telefono telefono) throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;
        int ID = telefono.getId();

        try {
            stmt = conn.createStatement();
            String data = telefono.getTelefono();

            String sql = "UPDATE Telefonos SET telefono =" + "'" + data + "' WHERE id =" + "'" + ID + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Persona> getPeople() throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Persona> personas = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id AS personaId, nombre FROM Personas");

            while (rs.next()) {

                //Se obtiene los datos de la primera fila
                int id = rs.getInt("personaId");
                String nombre = rs.getString("nombre");

                ArrayList<Direccion> direccionesPersona = new ArrayList<>();
                Statement stmtDir = conn.createStatement();
                String sqlDir = "SELECT id AS direccionId, direccion FROM Direcciones WHERE idPersona = " + id;
                ResultSet rsDir = stmtDir.executeQuery(sqlDir);
                while (rsDir.next()) {
                    int newID = rsDir.getInt("direccionId");
                    String dir = rsDir.getString("direccion");
                    direccionesPersona.add(new Direccion(newID, id, dir));
                }
                rsDir.close();
                stmtDir.close();

                //Se aniade al Arraylist
                personas.add(new Persona(nombre, id, direccionesPersona));
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

    @Override
    public ArrayList<Direccion> getDirecciones() throws SQLException {
        Connection conn = doConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Direccion> direcciones = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Direcciones");

            while (rs.next()) {
                int id = rs.getInt("id");
                int idPersona = rs.getInt("idPersona");
                String direccion = rs.getString("direccion");


                direcciones.add(new Direccion(id,idPersona, direccion));
            }

            rs.close();
            stmt.close();
            return direcciones;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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

    @Override
    public ArrayList<Telefono> getTelefonos() throws SQLException {
        Connection conn = doConnection();
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

    private void updateProcessD(Connection conn, Statement stmt, String updateType, Direccion direccion) {
        try {
            String direccion1 = direccion.getDireccion();
            int idPersona = direccion.getId();

            stmt = conn.createStatement();
            switch (updateType) {
                case "add":
                    String sql = "INSERT INTO direcciones (idPersona, direccion) VALUES ("
                            + idPersona + ", '" + direccion1 + "')";

                    stmt.executeUpdate(sql);
                    break;
                case "delete":
                    String sql1 = "DELETE FROM direcciones WHERE IdPersona = " + direccion.getIdPersona() + " " +
                            "AND direccion = '" + direccion.getDireccion()+ "'";
                    stmt.executeUpdate(sql1);
                    break;
            }
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProcessPD(Connection conn, Statement stmt, String updateType, Persona persona) {
        try {
            String nombre1 = persona.getNombre();
            String direccion1 = arrayToString(persona.getDirecciones());
            direccion1 = direccion1.replace(" ", "");
            int ID = persona.getId();

            stmt = conn.createStatement();
            switch (updateType) {
                case "add":

                    String sql = "INSERT INTO Personas (nombre) VALUES ('" + nombre1 + "')";
                    stmt.executeUpdate(sql);

                    ArrayList<Direccion> direcciones = getDirecciones();
                    ArrayList<Direccion> direccionesPersona = persona.getDirecciones();
                    for (int i = 0; i < direccionesPersona.size(); i++) {
                        Direccion direccion = direccionesPersona.get(i);
                        int ultimaID = direcciones.getLast().getId();
                        addDireccionDataFromSQL(new Direccion(ultimaID+1,persona.getId(),direccion.getDireccion()));
                    }



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
                    String sql1 = "DELETE FROM Telefonos WHERE id = " + ID;
                    stmt.executeUpdate(sql1);
                    break;
                case "edit":
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



    @Override
    public void addDireccion(Direccion direccion) throws SQLException {

    }
}
