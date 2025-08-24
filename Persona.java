import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Persona {
    private String nombre;
    private int id;
    private String direcciones;

    public Persona() {
        nombre = null;
        id = -1;
        direcciones = null;
    }

    public Persona(String nombre, int id, String direcciones) {
        this.nombre = nombre;
        this.id = id;
        this.direcciones = direcciones;
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

    public String getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return this.nombre.equals(persona.getNombre()) &&
                this.id == persona.getId() &&
                this.direcciones.equals(persona.getDirecciones());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, id, direcciones);
    }
}
