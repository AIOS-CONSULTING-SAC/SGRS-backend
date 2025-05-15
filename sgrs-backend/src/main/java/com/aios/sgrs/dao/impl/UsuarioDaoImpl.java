package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean guardarUsuario(GuardarUsuarioRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {

            cs.setObject(1, request.getIdUsuario(), Types.INTEGER);
            cs.setInt(2, request.getIdEmpresa());
            cs.setInt(3, request.getIdcliente());
            cs.setInt(4, request.getIdTipoUser());
            cs.setInt(5, request.getIdPerfil());
            cs.setInt(6, request.getIdTipoDoc());
            cs.setString(7, request.getNdoc());
            cs.setString(8, request.getNombre());
            cs.setString(9, request.getApellidoP());
            cs.setString(10, request.getApellidoM());
            cs.setString(11, request.getCorreo());
            cs.setString(12, request.getPassword());
            cs.setInt(13, request.getIdEstado());
            cs.setInt(14, request.getUsuarioSesion());


            cs.registerOutParameter(15, Types.VARCHAR);
            cs.registerOutParameter(16, Types.VARCHAR);


            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(16));
            request.setIdUsuario(cs.getInt(15));
            return rpta;
        }));
    }

    @Override
    public UsuarioLogeadoResponse iniciarSesion(String usuario, String password) throws AccesoDaoException {
        String sql = "CALL sp_iniciar_sesion(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{usuario, password},
                (rs, rowNum) -> {
                    UsuarioLogeadoResponse r = new UsuarioLogeadoResponse();
                    r.setResultado(rs.getInt("resultado"));
                    r.setMensaje(rs.getString("mensaje"));
                    r.setUsuarioId(rs.getInt("usuario_id"));
                    r.setNombres(rs.getString("nombres"));
                    r.setApellidoP(rs.getString("apellidoP"));
                    r.setApellidoM(rs.getString("apellidoM"));
                    r.setPassword(rs.getString("password"));
                    r.setTipoUsuarioId(rs.getInt("tipo_usuario_id"));
                    r.setRolId(rs.getObject("rol_id") != null ? rs.getInt("rol_id") : null);
                    r.setEmpresaId(rs.getObject("empresa_id") != null ? rs.getInt("empresa_id") : null);
                    return r;
                }
        );
    }
}
