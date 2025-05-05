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
        String sql = "{CALL [PROD].[SQL_PRODUCTO](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1, request.getIdUsuario());
            cs.setInt(2, request.getIdEmpresa());
            cs.setString(3, request.getCorreo());
            cs.setString(4, request.getPassword());
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setString(6, request.getUsuarioSesion());

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(9));
            request.setIdUsuario(cs.getInt(1));
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
                    r.setApellidos(rs.getString("apellidos"));
                    r.setPassword(rs.getString("password"));
                    r.setTipoUsuarioId(rs.getInt("tipo_usuario_id"));
                    r.setRolId(rs.getObject("rol_id") != null ? rs.getInt("rol_id") : null);
                    r.setEmpresaId(rs.getObject("empresa_id") != null ? rs.getInt("empresa_id") : null);
                    return r;
                }
        );
    }
}
