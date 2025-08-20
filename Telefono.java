import java.util.Objects;

public class Telefono {
    private String telefono;
    private int id;
    private int personaId;

    public Telefono() {
        telefono = null;
        id = -1;
        personaId = -1;
    }

    public Telefono(String telefono, int id, int personaId) {
        this.telefono = telefono;
        this.id = id;
        this.personaId = personaId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // misma referencia
        if (!(o instanceof Telefono)) return false; // no es Persona
        Telefono telefono = (Telefono) o; // casteamos
        return this.telefono.equals(telefono.getTelefono()) &&
                this.personaId ==  telefono.getPersonaId() &&
                this.id == telefono.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(telefono,personaId,id);
    }
}
