/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ifobia.database_util;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class database_utility {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ifobia_db", // Nama DB
                "root", ""); // Atur user/pass sesuai database-mu
        } catch (Exception e) {
            
            e.printStackTrace();
            return null;
        }
    }
}
