package com.aios.sgrs.dao.impl;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.dao.DashboardDao;
import com.aios.sgrs.model.response.Dashboard.DashboardResponse;
import com.aios.sgrs.model.response.Dashboard.DetalleDashboard;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DashboardDaoImpl implements DashboardDao {

    private final JdbcTemplate jdbcTemplate;


    public DashboardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<DashboardResponse> listarDashboard01(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws AccesoDaoException {
        String sql = "{CALL sp_listado_dashboard_01(?, ?, ?, ?, ?)}";
        ObjectMapper mapper = new ObjectMapper();
        List<DashboardResponse> listado = new ArrayList<>();

        jdbcTemplate.query(sql, new Object[]{codCliente, anio, locales, meses, residuos}, rs -> {
            while (rs.next()) {
                DashboardResponse dashboard = new DashboardResponse();
                dashboard.setCodResiduo(rs.getInt(1));
                dashboard.setDescResiduo(rs.getString(2));
                dashboard.setCodUnidad(rs.getInt(3));
                dashboard.setDescUnidad(rs.getString(4));
                dashboard.setTotalAnio(rs.getDouble(5));

                String jsonDetalle = rs.getString(6);
                if (jsonDetalle != null && !jsonDetalle.isEmpty()) {
                    try {
                        List<DetalleDashboard> detalleList = mapper.readValue(
                                jsonDetalle,
                                new TypeReference<List<DetalleDashboard>>() {}
                        );
                        dashboard.setDetalleDashboard(detalleList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    dashboard.setDetalleDashboard(new ArrayList<>());
                }

                listado.add(dashboard);
            }
            return null;
        });

        return listado;
    }



    @Override
    public List<DashboardResponse> listarDashboard02(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws AccesoDaoException {
        String sql = "{CALL sp_listado_dashboard_02(?, ?, ?, ?, ?)}";
        ObjectMapper mapper = new ObjectMapper();
        List<DashboardResponse> listado = new ArrayList<>();

        jdbcTemplate.query(sql, new Object[]{codCliente, anio, locales, meses, residuos}, rs -> {
            while (rs.next()) {
                DashboardResponse dashboard = new DashboardResponse();
                dashboard.setCodResiduo(rs.getInt(1));
                dashboard.setDescResiduo(rs.getString(2));
                dashboard.setCodUnidad(rs.getInt(3));
                dashboard.setDescUnidad(rs.getString(4));
                dashboard.setTotalAnio(rs.getDouble(5));

                String jsonDetalle = rs.getString(6);
                if (jsonDetalle != null && !jsonDetalle.isEmpty()) {
                    try {
                        List<DetalleDashboard> detalleList = mapper.readValue(
                                jsonDetalle,
                                new TypeReference<List<DetalleDashboard>>() {}
                        );
                        dashboard.setDetalleDashboard(detalleList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    dashboard.setDetalleDashboard(new ArrayList<>());
                }

                listado.add(dashboard);
            }
            return null;
        });

        return listado;
    }

}

