package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.EmpresaDao;
import com.aios.sgrs.model.request.empresa.EliminarEmpresaRequest;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.response.empresa.EmpresaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaDaoImpl implements EmpresaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmpresaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EmpresaResponse> listado(int tipoSQL, String razonSocial, String ruc, Integer idEstado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_empresas(?, ?, ?, ?)}";
        List<EmpresaResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, razonSocial, ruc, idEstado}, rs -> {

            EmpresaResponse empresa;
            while (rs.next()) {
                empresa = new EmpresaResponse();

                empresa.setEmpresa(rs.getInt(1));
                empresa.setRuc(rs.getString(2));
                empresa.setRazonSocial(rs.getString(3));
                empresa.setNombreComercial(rs.getString(4));

                empresa.setIdDepartamento(rs.getInt(5));
                empresa.setIdProvincia(rs.getInt(6));
                empresa.setIdDistrito(rs.getInt(7));

                empresa.setDireccion(rs.getString(8));
                empresa.setIdEstado(rs.getShort(9));
                empresa.setDescEstado(empresa.getIdEstado()==1 ? "Activo" : "Inactivo");
                empresa.setFechaRegistro(rs.getDate(10));
                listado.add(empresa);
            }

            return listado;

        });
    }

    @Override
    public boolean guardarEmpresa(GuardarEmpresaRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_empresa(?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setObject(1, request.getEmpresa(), Types.INTEGER);
            cs.setString(2, request.getRuc());
            cs.setString(3, request.getRazonSocial());
            cs.setString(4, request.getNombreComercial());

            cs.setInt(5, request.getIdDepartamento());
            cs.setInt(6, request.getIdProvincia());
            cs.setInt(7, request.getIdDistrito());

            cs.setString(8, request.getDireccion());
            cs.setInt(9, request.getIdEstado());
            cs.setInt(10, request.getUsuarioSesion());

            cs.registerOutParameter(11, Types.VARCHAR);
            cs.registerOutParameter(12, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(12));
            request.setEmpresa(cs.getInt(11 ));
            return rpta;
        }));
    }

    @Override
    public boolean eliminarEmpresa(EliminarEmpresaRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_empresa(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1,request.getEmpresa());
            cs.setString(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR);

            boolean rpta = cs.executeUpdate() == 1;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }
}
