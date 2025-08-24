import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaTest {

    private static final String URL = "jdbc:mysql://localhost:3306/agenda";
    private static final String USER = "usuario1";
    private static final String PASSWORD = "superpassword";

    @Test
    void testDoConnection() throws SQLException {
        Agenda agenda = new Agenda();
        Connection conn = agenda.doConection(URL, USER, PASSWORD);
        assertNotNull(conn);
        assertFalse(conn.isClosed());
    }

    @Test
    void testGetPeopleData() throws SQLException {
        Agenda agenda = new Agenda();
        ArrayList<Persona> personas = agenda.getPeople();
        assertNotNull(personas);
    }

    @Test
    void testGetTelephoneData() throws SQLException {
        Agenda agenda = new Agenda();
        ArrayList<Telefono> telefonos = agenda.getTelephones();
        assertNotNull(telefonos);
    }

    @Test
    void testAddPersonData() throws SQLException {
        Agenda agenda = new Agenda();
        Persona persona = new Persona("Gael",-1,"Main Street");
        agenda.addPersonData(persona);
        persona.setId(agenda.getPeople().getLast().getId());
        assertEquals(persona, agenda.getPeople().getLast());

        agenda.deletePersonData(persona);
    }

    @Test
    void testAddTelephoneData() throws SQLException {
       Agenda agenda = new Agenda();
       Telefono telefono = new Telefono("999-1234567",-1,2);
       agenda.addTelephoneData(telefono);
       telefono.setId(agenda.getTelephones().getLast().getId());
       assertEquals(telefono, agenda.getTelephones().getLast());
       agenda.deleteTelephoneData(telefono);
    }

    @Test
    void testDeletePersonData() throws SQLException {
        boolean flag = false;
        Agenda agenda = new Agenda();
        Persona persona = new Persona("nombreTest",-1,"Main Street");
        agenda.addPersonData(persona);
        persona.setId(agenda.getPeople().getLast().getId());

        agenda.deletePersonData(persona);

        assertNotEquals(persona, agenda.getPeople().getLast());
    }

    @Test
    void testDeleteTelephonedata() throws SQLException {
        boolean flag = false;
        Agenda agenda = new Agenda();
        Telefono telefono = new Telefono("203-1234567", -1, 2);
        agenda.addTelephoneData(telefono);
        telefono.setId(agenda.getTelephones().getLast().getId());

        agenda.deleteTelephoneData(telefono);

        assertNotEquals(telefono, agenda.getTelephones().getLast());
    }

    @Test
    void testEditPersonData() throws SQLException {
        boolean flag = false;
        Agenda agenda = new Agenda();
        Persona persona = new Persona("testPersona", -1, "DireccionTest");
        Persona personaModified = new Persona("testPersona", -1, "DireccionNuevaTest");

        agenda.addPersonData(persona);
        String direccionVieja = agenda.getPeople().getLast().getDirecciones();

        int ID = agenda.getPeople().getLast().getId();
        personaModified.setId(ID);

        agenda.editPersonData(personaModified);
        if (!direccionVieja.equals(agenda.getPeople().getLast().getDirecciones()) && persona.getNombre().equals(agenda.getPeople().getLast().getNombre())) {
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    void testEditTelephoneData() throws SQLException {
        boolean flag = false;
        Agenda agenda = new Agenda();

        Telefono telefono = new Telefono("123-2222222", -1, 2);
        Telefono telefonoModified = new Telefono("123-2222223", -1, 2);

        agenda.addTelephoneData(telefono);
        String telefonoViejo = agenda.getTelephones().getLast().getTelefono();

        int ID = agenda.getTelephones().getLast().getId();
        telefonoModified.setId(ID);

        agenda.editTelephoneData(telefonoModified);
        if (!telefonoViejo.equals(agenda.getTelephones().getLast().getTelefono()) && telefono.getPersonaId() == agenda.getTelephones().getLast().getPersonaId()) {
            flag = true;
        }

        assertTrue(flag);
    }


}