/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Usuario;

/**
 *
 * @author Beto_
 */
public class UsuarioNuevoDTO {
    private String correo;
    private String cedulaProfesional;
    private String contrasenia;
    private Usuario.TipoUsuario tipo;

    public UsuarioNuevoDTO() {
    }

    public UsuarioNuevoDTO(String correo, String cedulaProfesional, String contrasenia, Usuario.TipoUsuario tipo) {
        this.correo = correo;
        this.cedulaProfesional = cedulaProfesional;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    public UsuarioNuevoDTO(String correo, String contrasenia, Usuario.TipoUsuario tipo) {
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
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

    public Usuario.TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(Usuario.TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UsuarioViejoDTO{" + "correo=" + correo + ", cedulaProfesional=" + cedulaProfesional + ", contrasenia=" + contrasenia + ", tipo=" + tipo + '}';
    }
}
