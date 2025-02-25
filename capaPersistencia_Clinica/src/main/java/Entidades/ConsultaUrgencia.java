/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author pablo
 */
public class ConsultaUrgencia {
    private int idCitasUrgencia;
    private int idCita;
    private String folio;
    private String nombreMedico;
    private LocalTime horaInicioConsulta;
    private LocalTime horaFinConsulta;

    public ConsultaUrgencia() {
    }

    public ConsultaUrgencia(int idCitasUrgencia, int idCita, String folio, String nombreMedico, LocalTime horaInicioConsulta, LocalTime horaFinConsulta) {
        this.idCitasUrgencia = idCitasUrgencia;
        this.idCita = idCita;
        this.folio = folio;
        this.nombreMedico = nombreMedico;
        this.horaInicioConsulta = horaInicioConsulta;
        this.horaFinConsulta = horaFinConsulta;
    }

    public ConsultaUrgencia(int idCita, String folio, String nombreMedico, LocalTime horaInicioConsulta, LocalTime horaFinConsulta) {
        this.idCita = idCita;
        this.folio = folio;
        this.nombreMedico = nombreMedico;
        this.horaInicioConsulta = horaInicioConsulta;
        this.horaFinConsulta = horaFinConsulta;
    }
    
    public ConsultaUrgencia(String nombreMedico, LocalTime horaInicioConsulta, LocalTime horaFinConsulta) {
        this.nombreMedico = nombreMedico;
        this.horaInicioConsulta = horaInicioConsulta;
        this.horaFinConsulta = horaFinConsulta;
    }

    public int getIdCitasUrgencia() {
        return idCitasUrgencia;
    }

    public void setIdCitasUrgencia(int idCitasUrgencia) {
        this.idCitasUrgencia = idCitasUrgencia;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public LocalTime getHoraInicioConsulta() {
        return horaInicioConsulta;
    }

    public void setHoraInicioConsulta(LocalTime horaInicioConsulta) {
        this.horaInicioConsulta = horaInicioConsulta;
    }

    public LocalTime getHoraFinConsulta() {
        return horaFinConsulta;
    }

    public void setHoraFinConsulta(LocalTime horaFinConsulta) {
        this.horaFinConsulta = horaFinConsulta;
    }

    @Override
    public String toString() {
        return "ConsultaUrgencia{" + "idCitasUrgencia=" + idCitasUrgencia + ", idCita=" + idCita + ", folio=" + folio + ", nombreMedico=" + nombreMedico + ", horaInicioConsulta=" + horaInicioConsulta + ", horaFinConsulta=" + horaFinConsulta + '}';
    }
}
