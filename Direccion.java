import java.util.ArrayList;

public class Direccion {
    private final int id;
    private final int idPersona;
    private String direccion;

    public Direccion(int id,int idPersona, String direccion) {
        this.id = id;
        this.direccion = direccion;
        this.idPersona = idPersona;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
