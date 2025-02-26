/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.Cita;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author pablo
 */
public class CitaNuevaDTO {
    private int idMedico;
    private int idPaciente;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Cita.EstadoCita estado;

    public CitaNuevaDTO() {
    }

    public CitaNuevaDTO(int idMedico, int idPaciente, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Cita.EstadoCita estado) {
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Cita.EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(Cita.EstadoCita estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CitaNuevaDTO{" + "idMedico=" + idMedico + ", idPaciente=" + idPaciente + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", estado=" + estado + '}';
    }    
    
}
