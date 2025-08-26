import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Persona {
    private String nombre;
    private int id;
    private ArrayList<Direccion> direcciones;

    public Persona() {
        nombre = null;
        id = -1;
        direcciones = null;
    }

    public Persona(String nombre, int id, ArrayList<Direccion> direcciones) {
        this.nombre = nombre;
        this.id = id;
        this.direcciones = direcciones;
    }

    public Persona(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        direcciones = new ArrayList<>();
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

    public ArrayList<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public void addDireccion(Direccion direccion) {
        direcciones.add(direccion);
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
