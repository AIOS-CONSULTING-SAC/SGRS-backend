package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.EmpresaDao;
import com.aios.sgrs.dao.LocalDao;
import com.aios.sgrs.model.request.empresa.EliminarEmpresaRequest;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.request.local.EliminarLocalRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.response.empresa.EmpresaResponse;
import com.aios.sgrs.model.response.local.LocalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalDaoImpl implements LocalDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocalDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LocalResponse> listado(int tipoSQL, int codCliente, Integer idEstado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_locales(?, ?, ?)}";
        List<LocalResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, codCliente, idEstado}, rs -> {

            LocalResponse local;
            while (rs.next()) {
                local = new LocalResponse();

                local.setLocal(rs.getInt(1));
                local.setCodCliente(rs.getInt(2));
                local.setNombre(rs.getString(3));
                local.setIdDepartamento(rs.getInt(4));
                local.setIdProvincia(rs.getInt(5));
                local.setIdDistrito(rs.getInt(6));
                local.setDireccion(rs.getString(7));
                local.setIdEstado(rs.getShort(8));
                local.setDescEstado(local.getIdEstado()==1 ? "Activo" : "Inactivo");
                local.setCodUserReg(rs.getInt(9));
                local.setFechaRegistro(rs.getDate(10));
                listado.add(local);
            }

            return listado;

        });
    }

    @Override
    public boolean guardarLocal(GuardarLocalRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_locales(?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setObject(1, request.getLocal(), Types.INTEGER);
            cs.setInt(2, request.getCodCliente());
            cs.setString(3, request.getNombre());
            cs.setInt(4, request.getIdDepartamento());
            cs.setInt(5, request.getIdProvincia());
            cs.setInt(6, request.getIdDistrito());
            cs.setString(7, request.getDireccion());
            cs.setInt(8, request.getIdEstado());
            cs.setInt(9, request.getUsuarioSesion());

            cs.registerOutParameter(10, Types.VARCHAR);
            cs.registerOutParameter(11, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(11));
            request.setLocal(cs.getInt(10));
            return rpta;
        }));
    }


    @Override
    public boolean eliminarLocal(EliminarLocalRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_local(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1,request.getLocal());
            cs.setString(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }
}
