package com.ifobia.entity_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.user;

/**
 *
 * @author LENOVO
 */

public class userDAO {
    
    public user getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username=?";
        
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery(); 
            
            if(rs.next()) {

                return new user( 
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("ROLE")
                );
            }
        } catch(Exception e){ 
            
             e.printStackTrace(); 
        }

        return null;
    }

    // GET USER BY ID
    public user getUserById(int id) {
    
        String sql = "SELECT * FROM user WHERE id_user=?";
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new user(
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("ROLE")
                );
            }
        } catch(SQLException e){ e.printStackTrace(); }
        return null;
    }

    // ADD USER
    public boolean addUser(user user) {
        String sql = "INSERT INTO user(nama, username, password, ROLE) VALUES(?,?,?,?)";
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getNama());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            return ps.executeUpdate() > 0;
        } catch(SQLException e){ e.printStackTrace(); }
        return false;
    }

    
    /**
     * METHOD BARU: Mengambil ID semua user yang ROLEnya 'Mahasiswa'
     */
    public List<Integer> getAllStudentIds() {
        List<Integer> studentIds = new ArrayList<>();
 
        String sql = "SELECT id_user FROM user";
        
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                studentIds.add(rs.getInt("id_user"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIds;
    }
}