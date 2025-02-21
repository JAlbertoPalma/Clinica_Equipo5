/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Usuario;
import Exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface IUsuarioDAO {
    public Usuario agregarUsuario(Usuario usuario)throws PersistenciaException;
    
    public Usuario obtenerUsuarioPorCorreo(String correo);
    
    public List<Usuario> obtenerUsuarios();
}
