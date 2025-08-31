import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class Agenda
{
    private AgendaSQL agendaSQL;

    public ArrayList<Persona> getPeople() throws SQLException {
        return agendaSQL.getPeopledataFromSQL();
    }

    public ArrayList<Direccion> getDirecciones() throws SQLException {
        return agendaSQL.getDireccionDataFromSQL();
    }


    public ArrayList<Telefono> getTelephones() throws SQLException {
        return agendaSQL.getTelephonesDataFromSQL();
    }


    public void addPerson(Persona persona) throws SQLException {
        agendaSQL.addPersonDataFromSQL(persona);
    }

    public void addTelephone(Telefono telefono) throws SQLException {
        agendaSQL.addTelephoneDataFromSQL(telefono);
    }

    public void addDireccion(Direccion direccion) throws SQLException {
        agendaSQL.addDireccionDataFromSQL(direccion);
    }

    public void deletePerson(Persona persona) throws SQLException {
        agendaSQL.deletePersonDataFromSQL(persona);
    }

    public void deleteTelephone(Telefono telefono) throws SQLException {
        agendaSQL.deleteTelephoneDataFromSQL(telefono);
    }

    public void deleteDireccion(Direccion direccion) throws SQLException {
        agendaSQL.deleteDireccionDataFromSQL(direccion);
    }

    public void editPerson(Persona persona) throws SQLException {
        agendaSQL.editPersonDataFromSQL(persona);
    }

    public void editTelephone(Telefono telefono) throws SQLException {
        agendaSQL.editTelephoneDataFromSQL(telefono);
    }

    public String arrayToString(ArrayList<Direccion> direcciones) {
        return agendaSQL.arrayToString(direcciones);
    }

    public ArrayList<Direccion> stringToArray(String direcciones, int idPersona) throws SQLException {
        return agendaSQL.stringToArray(direcciones, idPersona);
    }
}
