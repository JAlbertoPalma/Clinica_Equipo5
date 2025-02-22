/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Medico.EspecialidadMedico;

/**
 *
 * @author pablo
 */
public class MedicoViejoDTO {
    private int idMedico;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private EspecialidadMedico especialidad;
    private String cedulaProfesional;
    private boolean estaActivo;
    private int idUsuario;

    public MedicoViejoDTO() {
    }

    public MedicoViejoDTO(int idMedico, String nombre, String apellidoPaterno, String apellidoMaterno, EspecialidadMedico especialidad, String cedulaProfesional, boolean estaActivo, int idUsuario) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estaActivo = estaActivo;
        this.idUsuario = idUsuario;
    }

    public MedicoViejoDTO(String nombre, String apellidoPaterno, String apellidoMaterno, EspecialidadMedico especialidad, String cedulaProfesional, boolean estaActivo, int idUsuario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estaActivo = estaActivo;
        this.idUsuario = idUsuario;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public EspecialidadMedico getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadMedico especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "MedicoViejoDTO{" + "idMedico=" + idMedico + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", especialidad=" + especialidad + ", cedulaProfesional=" + cedulaProfesional + ", estaActivo=" + estaActivo + ", idUsuario=" + idUsuario + '}';
    }
}
