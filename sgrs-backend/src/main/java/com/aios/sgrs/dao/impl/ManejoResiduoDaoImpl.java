package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.ManejoResiduoDao;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManejoResiduoDaoImpl implements ManejoResiduoDao {

    private final JdbcTemplate jdbcTemplate;


    public ManejoResiduoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ManejoResiduoResponse> listarManejoResiduos(Integer codCliente, Integer anio, String locales, Integer estado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_manejo_residuos_detalle(?, ?, ?, ?)}";
        List<ManejoResiduoResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{codCliente, anio, locales, estado}, rs -> {
            ManejoResiduoResponse manejoResiduo;
            while (rs.next()) {
                manejoResiduo = new ManejoResiduoResponse();

                manejoResiduo.setCodCliente(rs.getInt(1));
                manejoResiduo.setDescCliente(rs.getString(2));
                manejoResiduo.setCodLocal(rs.getInt(3));
                manejoResiduo.setDescLocal(rs.getString(4));
                manejoResiduo.setCodResiduo(rs.getInt(5));
                manejoResiduo.setDescResiduo(rs.getString(6));
                manejoResiduo.setCodUnidad(rs.getInt(7));
                manejoResiduo.setDescUnidad(rs.getString(8));

                manejoResiduo.setMes01(rs.getDouble(9));
                manejoResiduo.setMes02(rs.getDouble(10));
                manejoResiduo.setMes03(rs.getDouble(11));
                manejoResiduo.setMes04(rs.getDouble(12));
                manejoResiduo.setMes05(rs.getDouble(13));
                manejoResiduo.setMes06(rs.getDouble(14));
                manejoResiduo.setMes07(rs.getDouble(15));
                manejoResiduo.setMes08(rs.getDouble(16));
                manejoResiduo.setMes09(rs.getDouble(17));
                manejoResiduo.setMes10(rs.getDouble(18));
                manejoResiduo.setMes11(rs.getDouble(19));
                manejoResiduo.setMes12(rs.getDouble(20));
                manejoResiduo.setTotal(rs.getDouble(21));

                listado.add(manejoResiduo);
            }
            return listado;
        });
    }

    @Override
    public boolean guardarManejoResiduo(GuardarManejoResiduoRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_guardar_manejo_residuo(?,?,?,?,?,?,?,?)}";

        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1, request.getCodLocal());
            cs.setInt(2, request.getCodResiduo());
            cs.setInt(3, request.getAnio());

            String detalleJson = null;
            try {
                detalleJson = new ObjectMapper()
                        .writeValueAsString(request.getDetalle());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            cs.setString(4, detalleJson);

            cs.setInt(5, request.getIdEstado());
            cs.setInt(6, request.getUsuarioSesion());

            cs.registerOutParameter(7, Types.INTEGER);
            cs.registerOutParameter(8, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() >= 0;
            request.setMensaje(cs.getString(8));
            return rpta;
        }));
    }

}

