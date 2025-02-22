/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class MedicoDAO implements IMedicoDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(MedicoDAO.class.getName());

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean darBajaMedico(int idMedico) throws PersistenciaException {
        try (Connection con = this.conexion.crearConexion()) {
            con.setAutoCommit(false); // Inicia transacción

            // Eliminar cita primero
            try (PreparedStatement pstmtCitas = con.prepareStatement("DELETE FROM citas WHERE id_medico = ?")) {
                pstmtCitas.setInt(1, idMedico);
                pstmtCitas.executeUpdate();
            }

            // Luego eliminar el médico
            try (PreparedStatement pstmtMedico = con.prepareStatement("DELETE FROM medicos WHERE id = ?")) {
                pstmtMedico.setInt(1, idMedico);
                int filasAfectadas = pstmtMedico.executeUpdate();

                if (filasAfectadas == 0) {
                    con.rollback(); // Revertir si no se encontró el médico
                    throw new PersistenciaException("No se encontró el médico con ID: " + idMedico);
                }

                con.commit(); // Confirmar cambios
                return true;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar médico.", e);
        }
    }

}
