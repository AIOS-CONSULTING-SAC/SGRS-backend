package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.ResiduoDao;
import com.aios.sgrs.model.request.residuo.EliminarResiduoRequest;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.model.response.residuo.ResiduoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResiduoDaoImpl implements ResiduoDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResiduoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ResiduoResponse> listado(int tipoSQL, int codCliente, Integer idEstado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_residuos(?, ?, ?)}";
        List<ResiduoResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, codCliente, idEstado}, rs -> {

            ResiduoResponse local;
            while (rs.next()) {
                local = new ResiduoResponse();

                local.setResiduo(rs.getInt(1));
                local.setCodCliente(rs.getInt(2));
                local.setDescripcion(rs.getString(3));
                local.setIdUnidad(rs.getInt(4));
                local.setIdEstado(rs.getShort(5));

                local.setDescEstado(local.getIdEstado()==1 ? "Activo" : "Inactivo");
                local.setFechaRegistro(rs.getDate(6));
                listado.add(local);
            }

            return listado;

        });
    }

    @Override
    public boolean guardarResiduo(GuardarResiduoRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_residuos(?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setObject(1, request.getResiduo(), Types.INTEGER);
            cs.setInt(2, request.getCodCliente());
            cs.setString(3, request.getDescripcion());
            cs.setInt(4, request.getIdUnidad());
            cs.setInt(5, request.getIdEstado());
            cs.setInt(6, request.getUsuarioSesion());

            cs.registerOutParameter(7, Types.VARCHAR);
            cs.registerOutParameter(8, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(8));
            request.setResiduo(cs.getInt(7));
            return rpta;
        }));
    }


    @Override
    public boolean eliminarResiduo(EliminarResiduoRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_residuo(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1,request.getResiduo());
            cs.setString(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }


}
