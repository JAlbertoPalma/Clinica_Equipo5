/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 *
 * @author Beto_
 */
public class Consulta {
    //Para separar el estado de la consulta
    public enum EstadoConsulta {
        pendiente, finalizada, noAtendida, noAsistio
    }
    
    private int idConsulta;
    private int idCita;
    private String tipo;
    private LocalDateTime fechaHora;
    private EstadoConsulta estado;
    private String tratamiento;
    private String diagnostico;

    public Consulta(int idConsulta, int idCita, String tipo, LocalDateTime fechaHora, EstadoConsulta estado, String tratamiento, String diagnostico) {
        this.idConsulta = idConsulta;
        this.idCita = idCita;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.tratamiento = tratamiento;
        this.diagnostico = diagnostico;
    }

    public Consulta(int idCita, String tipo, LocalDateTime fechaHora, EstadoConsulta estado, String tratamiento, String diagnostico) {
        this.idCita = idCita;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.tratamiento = tratamiento;
        this.diagnostico = diagnostico;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Consulta{" + "idConsulta=" + idConsulta + ", idCita=" + idCita + ", tipo=" + tipo + ", fechaHora=" + fechaHora + ", estado=" + estado + ", tratamiento=" + tratamiento + ", diagnostico=" + diagnostico + '}';
    }
    
}
