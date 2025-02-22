/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
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
        try (Connection con = this.conexion.crearConexion(); CallableStatement pstmt = con.prepareCall("call dar_baja_medico (?)")) {

            pstmt.setInt(1, idMedico);  // Establecer el parámetro del médico a eliminar
            int filasAfectadas = pstmt.executeUpdate();  // Ejecutar el procedimiento almacenado

            if (filasAfectadas > 0) {
                return true;  // Si se afectaron filas, significa que la baja fue exitosa
            } else {
                throw new PersistenciaException("No se pudo dar de baja el médico con ID: " + idMedico);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al dar de baja médico.", e);
        }
    }
}
