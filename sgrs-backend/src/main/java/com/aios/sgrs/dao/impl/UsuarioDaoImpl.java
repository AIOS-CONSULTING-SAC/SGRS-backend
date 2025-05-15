package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.model.response.usuario.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<UsuarioResponse> listado(int tipoSQL, Integer codUsuario, Integer codEmpresa, Integer codClinete, Integer idEstado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_usuarios(?, ?, ?, ?, ?)}";
        List<UsuarioResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, codUsuario, codEmpresa, codClinete, idEstado}, rs -> {

            UsuarioResponse usuario;
            while (rs.next()) {
                usuario = new UsuarioResponse();

                usuario.setUsuario(rs.getInt(1));
                usuario.setCodEmpresa(rs.getInt(2));
                usuario.setCodCliente(rs.getInt(3));
                usuario.setCodTipoUser(rs.getInt(4));
                usuario.setCodPerfil(rs.getInt(5));
                usuario.setCodTipoDoc(rs.getInt(6));
                usuario.setNdoc(rs.getString(7));
                usuario.setNombre(rs.getString(8));
                usuario.setApellidoP(rs.getString(9));
                usuario.setApellidoM(rs.getString(10));
                usuario.setCorreo(rs.getString(11));

                usuario.setIdEstado(rs.getShort(12));

                usuario.setDescEstado(usuario.getIdEstado()==1 ? "Activo" : "Inactivo");
                usuario.setFechaRegistro(rs.getDate(13));
                listado.add(usuario);
            }

            return listado;

        });
    }
}
