/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Beto_
 */
public class Usuario{
    
    //Para separar el tipo de usuario
    public enum TipoUsuario {
    Paciente, Medico
    }
    
    private int idUsuario;
    private String correo;
    private String cedulaProfesional;
    private String contrasenia;
    private TipoUsuario tipo;

    //Constructor vacio
    public Usuario() {
    }

    //Constructor cn todos los atributos
    public Usuario(int idUsuario, String correo, String cedulaProfesional, String contrasenia, TipoUsuario tipo) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.cedulaProfesional = cedulaProfesional;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }
    
    //Constructor con todos los atricutos menos el ID
    public Usuario(String correo, String cedulaProfesional, String contrasenia, TipoUsuario tipo) {
        this.correo = correo;
        this.cedulaProfesional = cedulaProfesional;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    //Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    
    //ToString
    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", correo=" + correo + ", cedulaProfesional=" + cedulaProfesional + ", contrasenia=" + contrasenia + ", tipo=" + tipo + '}';
    }
    
}
