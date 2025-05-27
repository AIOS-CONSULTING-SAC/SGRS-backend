package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.request.usuario.EliminarUsuarioRequest;
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
        String sql = "{CALL sp_insertar_actualizar_usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {

            cs.setObject(1, request.getIdUsuario(), Types.INTEGER);
            cs.setInt(2, request.getIdEmpresa());
            cs.setObject(3, request.getIdCliente(), Types.INTEGER);
            cs.setInt(4, request.getIdTipoUser());
            cs.setObject(5, request.getIdPerfil(), Types.INTEGER);
            cs.setInt(6, request.getIdTipoDoc());
            cs.setString(7, request.getNdoc());
            cs.setString(8, request.getNombre());
            cs.setString(9, request.getApellidoP());
            cs.setString(10, request.getApellidoM());
            cs.setString(11, request.getTelefono());
            cs.setString(12, request.getCorreo());
            //cs.setObject(13, request.getNombre().replace(" ", "") + request.getApellidoP(), Types.VARCHAR);
            cs.setString(13, request.getPassword());
            cs.setInt(14, request.getIdEstado());
            cs.setInt(15, request.getUsuarioSesion());


            cs.registerOutParameter(16, Types.VARCHAR);
            cs.registerOutParameter(17, Types.VARCHAR);


            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(17));
            request.setIdUsuarioOut(cs.getInt(16));
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
                usuario.setDescEmpresa(rs.getString(3));
                usuario.setCodCliente(rs.getInt(4));
                usuario.setDescCliente(rs.getString(5));
                usuario.setCodTipoUser(rs.getInt(6));
                usuario.setDescTipoUser(rs.getString(7));
                usuario.setCodPerfil(rs.getInt(8));
                usuario.setDescPerfil(rs.getString(9));
                usuario.setCodTipoDoc(rs.getInt(10));
                usuario.setNdoc(rs.getString(11));
                usuario.setNombre(rs.getString(12));
                usuario.setApellidoP(rs.getString(13));
                usuario.setApellidoM(rs.getString(14));
                usuario.setTelefono(rs.getString(15));
                usuario.setCorreo(rs.getString(16));

                usuario.setIdEstado(rs.getShort(17));

                usuario.setDescEstado(usuario.getIdEstado()==1 ? "Activo" : "Inactivo");
                usuario.setFechaRegistro(rs.getDate(18));
                listado.add(usuario);
            }

            return listado;

        });
    }


    @Override
    public boolean eliminarUsuario(EliminarUsuarioRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_usuario(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1,request.getUsuario());
            cs.setInt(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }

}
