package co.com.cardinalscale.autopesotruck.Entidades;

import java.io.Serializable;

public class EnUsuario  implements Serializable{

    private int Id;
    private String NombreDeUsuario;
    private String Clave;
    private String Nombres;
    private String Apellidos;
    private String Estado;
    private int Imagen;
    private byte[] Foto;



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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        Foto = foto;
    }
}
