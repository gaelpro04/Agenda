import java.util.Objects;

public class Persona {
    private String nombre;
    private int id;
    private String direccion;

    public Persona() {
        nombre = null;
        id = -1;
        direccion = null;
    }

    public Persona(String nombre, int id, String direccion) {
        this.nombre = nombre;
        this.id = id;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return this.nombre.equals(persona.getNombre()) &&
                this.id == persona.getId() &&
                this.direccion.equals(persona.getDireccion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, id, direccion);
    }
}
