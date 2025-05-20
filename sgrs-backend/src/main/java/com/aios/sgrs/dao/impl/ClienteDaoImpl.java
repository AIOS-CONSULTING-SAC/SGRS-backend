package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.ClienteDao;
import com.aios.sgrs.model.request.cliente.EliminarClienteRequest;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.model.response.cliente.ClienteResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDaoImpl implements ClienteDao {

    private final JdbcTemplate jdbcTemplate;


    public ClienteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClienteResponse> listarClientes(int tipoSQL, int codEmpresa, String razonSocial, String ruc, Integer estado) throws AccesoDaoException {
        String sql = "{CALL sp_listar_clientes(?, ?,?, ?, ?)}";
        List<ClienteResponse> listado = new ArrayList<>();

        return jdbcTemplate.query(sql, new Object[]{tipoSQL, codEmpresa, razonSocial, ruc, estado}, rs -> {
            ClienteResponse cliente;
            while (rs.next()) {
                cliente = new ClienteResponse();

                cliente.setCliente(rs.getInt(1));
                cliente.setCodEmpresa(rs.getInt(2));
                cliente.setRuc(rs.getString(3));
                cliente.setRazonSocial(rs.getString(4));
                cliente.setNombreComercial(rs.getString(5));

                cliente.setIdDepartamento(rs.getInt(6));
                cliente.setIdProvincia(rs.getInt(7));
                cliente.setIdDistrito(rs.getInt(8));

                cliente.setDireccion(rs.getString(9));
                cliente.setIdEstado(rs.getShort(10));
                cliente.setDescEstado(cliente.getIdEstado() == 1 ? "Activo" : "Inactivo");
                //cliente.setFechaRegistro(rs.getDate(11));
                listado.add(cliente);
            }
            return listado;
        });
    }

    @Override
    public boolean guardarCliente(GuardarClienteRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_insertar_actualizar_cliente(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setObject(1, request.getCliente(), Types.INTEGER); // Puede ser null para insertar
            cs.setInt(2, request.getCodEmpresa());
            cs.setString(3, request.getRuc());
            cs.setString(4, request.getRazonSocial());
            cs.setString(5, request.getNombreComercial());

            cs.setInt(6, request.getIdDepartamento());
            cs.setInt(7, request.getIdProvincia());
            cs.setInt(8, request.getIdDistrito());

            cs.setString(9, request.getDireccion());
            cs.setInt(10, request.getIdEstado());
            cs.setInt(11, request.getUsuarioSesion());

            cs.registerOutParameter(12, Types.INTEGER); // cliente_id_out
            cs.registerOutParameter(13, Types.VARCHAR); // mensaje/resultado

            boolean rpta = cs.executeUpdate() >= 0;
            request.setCliente(cs.getInt(12));
            request.setMensaje(cs.getString(13));
            return rpta;
        }));
    }

    @Override
    public boolean desactivarCliente(EliminarClienteRequest request) throws AccesoDaoException {
        String sql = "{CALL sp_desactivar_cliente(?,?,?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setInt(1, request.getCliente());
            cs.setInt(2, request.getUsuarioSesion());
            cs.registerOutParameter(3, Types.VARCHAR); // mensaje

            boolean rpta = cs.executeUpdate() >= 0;
            request.setMensaje(cs.getString(3));
            return rpta;
        }));
    }
}

