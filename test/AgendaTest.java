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
        ArrayList<Direccion> direccionTest = new ArrayList<>();
        direccionTest.add(new Direccion(agenda.getDirecciones().getLast().getId()+1, agenda.getPeople().getLast().getId()+1, "Main Street"));
        Persona persona = new Persona("Gael",agenda.getPeople().getLast().getId()+1,direccionTest);
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
        ArrayList<Direccion> direccionTest = new ArrayList<>();
        direccionTest.add(new Direccion(agenda.getDirecciones().getLast().getId()+1, agenda.getPeople().getLast().getId()+1, "Main Street"));
        Persona persona = new Persona("nombreTest",agenda.getPeople().getLast().getId()+1,direccionTest);
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

        // Persona inicial con una dirección
        ArrayList<Direccion> direccionesViejas = new ArrayList<>();
        direccionesViejas.add(new Direccion(agenda.getDirecciones().getLast().getId()+1,agenda.getPeople().getLast().getId()+1,"DireccionTest"));
        Persona persona = new Persona("testPersona", agenda.getPeople().getLast().getId()+1, direccionesViejas);

        // Persona modificada con una nueva dirección
        ArrayList<Direccion> direccionesNuevas = new ArrayList<>();
        direccionesNuevas.add(new Direccion(agenda.getDirecciones().getLast().getId()+1,agenda.getPeople().getLast().getId()+1,"DireccionNuevaTest"));
        Persona personaModified = new Persona("testPersona", agenda.getPeople().getLast().getId()+1, direccionesNuevas);

        // Agregamos persona original a la agenda
        agenda.addPersonData(persona);

        // Obtenemos id real asignado
        int ID = agenda.getPeople().getLast().getId();
        personaModified.setId(ID);

        // Guardamos la dirección vieja
        String direccionVieja = agenda.getPeople().getLast().getDirecciones().getFirst().getDireccion();

        // Editamos
        agenda.editPersonData(personaModified);

        // Nueva dirección en DB/Agenda
        String direccionNueva = agenda.getPeople().getLast().getDirecciones().getFirst().getDireccion();

        // Comprobamos que cambió la dirección pero no el nombre
        if (!direccionVieja.equals(direccionNueva) &&
                persona.getNombre().equals(agenda.getPeople().getLast().getNombre())) {
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