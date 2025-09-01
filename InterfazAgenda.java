import java.util.ArrayList;
import java.sql.*;

//Se supone que se crea esta clase para que si alguna vez sen ecesita cambiar
//el metodo de utilizar SQL, simplemente se haga con esta intefaz.
public interface InterfazAgenda {
    Connection doConnection() throws SQLException;
    ArrayList<Persona> getPeople() throws SQLException;
    ArrayList<Direccion> getDirecciones() throws SQLException;
    ArrayList<Telefono> getTelefonos() throws SQLException;
    void addPerson(Persona persona) throws SQLException;
    void addTelephone(Telefono telefono) throws SQLException;
    void addDireccion(Direccion direccion) throws SQLException;
    void deletePerson(Persona persona) throws SQLException;
    void deleteTelephone(Telefono telefono) throws SQLException;
    void deleteDireccion(Direccion direccion) throws SQLException;
    void editPerson(Persona persona) throws SQLException;
    void editTelephone(Telefono telefono) throws SQLException;
}
