package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.ParametroDao;
import com.aios.sgrs.model.request.parametro.EliminarParametroRequest;
import com.aios.sgrs.model.request.parametro.GuardarParametroRequest;
import com.aios.sgrs.model.response.parametro.ParametroResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParametroDaoImpl implements ParametroDao {

    private final JdbcTemplate jdbcTemplate;

    public ParametroDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ParametroResponse> listarParametros(Integer tipoSQL, Integer codModulo, Integer codOpcion, Integer codPrefijo, String desc1, String desc2, String desc3, Integer int01, Integer int02, Integer estado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_parametros(?,?,?,?,?,?,?,?,?,?)}";
        List<ParametroResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, codModulo, codOpcion, codPrefijo, desc1, desc2, desc3, int01, int02, estado}, rs -> {
            ParametroResponse parametro;
            while (rs.next()) {
                parametro = new ParametroResponse();

                parametro.setParametro(rs.getInt(1));
                parametro.setCodModulo(rs.getInt(2));
                parametro.setDescModulo(rs.getString(3));
                parametro.setCodOpcion(rs.getInt(4));
                parametro.setDescOpcion(rs.getString(5));
                parametro.setPrefijo(rs.getInt(6));
                parametro.setCorrelativo(rs.getInt(7));
                parametro.setDescripcion1(rs.getString(8));
                parametro.setDescripcion2(rs.getString(9));
                parametro.setDescripcion3(rs.getString(10));
                parametro.setEntero01(rs.getInt(11));
                parametro.setEntero02(rs.getInt(12));
                parametro.setIdEstado(rs.getShort(13));
                parametro.setDescEstado(parametro.getIdEstado() == 1 ? "Activo" : "Inactivo");
                parametro.setCodUserReg(rs.getInt(14));
                parametro.setFechaRegistro(rs.getDate(15));

                listado.add(parametro);
            }
            return listado;
        });
    }

    @Override
    public boolean guardarParametro(GuardarParametroRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_parametro(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setObject(1, request.getParametro(), Types.INTEGER); // Puede ser null para insertar
            cs.setInt(2, request.getCodModulo());
            cs.setString(3, request.getDescModulo());
            cs.setInt(4, request.getCodOpcion());
            cs.setString(5, request.getDescOpcion());
            cs.setInt(6, request.getPrefijo());
            cs.setInt(7, request.getCorrelativo());
            cs.setObject(8, request.getDesc01(), Types.VARCHAR);
            cs.setObject(9, request.getDesc02(), Types.VARCHAR);
            cs.setObject(10, request.getDesc03(), Types.VARCHAR);
            cs.setObject(11, request.getInt01(), Types.INTEGER);
            cs.setObject(12, request.getInt02(), Types.INTEGER);

            cs.setInt(13, request.getIdEstado());
            cs.setInt(14, request.getUsuarioSesion());

            cs.registerOutParameter(15, Types.INTEGER); // cliente_id_out
            cs.registerOutParameter(16, Types.VARCHAR); // mensaje/resultado

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(16));
            request.setParametro(cs.getInt(15));
            return rpta;
        }));
    }

    @Override
    public boolean desactivarParametro(EliminarParametroRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_parametro(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1, request.getParametro());
            cs.setInt(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR); // mensaje

            boolean rpta = cs.executeUpdate() >= 0;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }
}

