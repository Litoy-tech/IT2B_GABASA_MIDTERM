package com.gabasa.dao;

import com.gabasa.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean insertVehicles(ScenarioEntity vehicle){
        String sql = "INSERT INTO vehicles (model_name, plate_no, daily_rate, status) VALUES(?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, vehicle.model_name());
            ps.setString(2, vehicle.plate_no());
            ps.setDouble(3, vehicle.daily_rate());
            ps.setString(4, vehicle.status());
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> findAll(){
        List<ScenarioEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER by id";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                list.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("model_name"),
                        rs.getString("plate_no"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")));
            }

        }catch (SQLException e){
            System.out.println("Fetch failed: " + e.getMessage());
        }
        return list;
    }

}
