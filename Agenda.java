import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class Agenda
{
    private InterfazAgenda agendaSQL;

    public Agenda(InterfazAgenda agendaSQL) {
        this.agendaSQL = agendaSQL;
    }

    public ArrayList<Persona> getPeople() throws SQLException {
        return agendaSQL.getPeople();
    }

    public ArrayList<Direccion> getDirecciones() throws SQLException {
        return agendaSQL.getDirecciones();
    }


    public ArrayList<Telefono> getTelephones() throws SQLException {
        return agendaSQL.getTelefonos();
    }


    public void addPerson(Persona persona) throws SQLException {
        agendaSQL.addPerson(persona);
    }

    public void addTelephone(Telefono telefono) throws SQLException {
        agendaSQL.addTelephone(telefono);
    }

    public void addDireccion(Direccion direccion) throws SQLException {
        agendaSQL.addDireccion(direccion);
    }

    public void deletePerson(Persona persona) throws SQLException {
        agendaSQL.deletePerson(persona);
    }

    public void deleteTelephone(Telefono telefono) throws SQLException {
        agendaSQL.deleteTelephone(telefono);
    }

    public void deleteDireccion(Direccion direccion) throws SQLException {
        agendaSQL.deleteDireccion(direccion);
    }

    public void editPerson(Persona persona) throws SQLException {
        agendaSQL.editPerson(persona);
    }

    public void editTelephone(Telefono telefono) throws SQLException {
        agendaSQL.editTelephone(telefono);
    }

    public ArrayList<Direccion> stringToArray(String direcciones, int idPersona) throws SQLException {
        ArrayList<Direccion> direccionesNuevas = new ArrayList<>();
        String[] direccionString = direcciones.split(",");

        for (int i = 0; i < direccionString.length; i++) {
            direccionesNuevas.add(new Direccion(-1,idPersona, direccionString[i]));
        }

        return direccionesNuevas;
    }

    public String arrayToString(ArrayList<Direccion> direcciones) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < direcciones.size(); i++) {
            if (i == direcciones.size() - 1) {
                sb.append(direcciones.get(i).getDireccion());
            } else {
                sb.append(direcciones.get(i).getDireccion() + ",");
            }
        }

        return sb.toString();
    }
}
