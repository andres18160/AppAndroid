package co.com.cardinalscale.autopesotruck.Entidades;

public class EnUsuario {

    private int Id;
    private String NombreDeUsuario;
    private String Clave;
    private String Nombres;
    private String Apellidos;


    public int get_id() {
        return Id;
    }

    public void set_id(int _id) {
        this.Id = _id;
    }

    public String getNombreDeUsuario() {
        return NombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        NombreDeUsuario = nombreDeUsuario;
    }
    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }
}
