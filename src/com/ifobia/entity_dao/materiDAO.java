package com.ifobia.entity_dao;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.mataKuliah;
import com.ifobia.entity.materi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class materiDAO {

    
        public List<mataKuliah> getAllMataKuliah() {
        List<mataKuliah> list = new ArrayList<>();
            try (Connection c = database_utility.getConnection(); 
                    ResultSet rs = c.createStatement().executeQuery("SELECT * FROM mata_kuliah")) {
                while(rs.next()) list.add(new mataKuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), ""));
            } catch (Exception e) { 
                e.printStackTrace();
            }
            return list;
        }
        
    
        // MBIL SEMUA KODE MATKUL 
        public List<String> getAllKodeMatkul() {
            List<String> list = new ArrayList<>();
            String sql = "SELECT kode_mk, nama_mk FROM mata_kuliah ORDER BY nama_mk ASC";
            try (Connection c = database_utility.getConnection(); ResultSet rs = c.createStatement().executeQuery(sql)) {
                while(rs.next()) list.add(rs.getString("kode_mk") + " - " + rs.getString("nama_mk"));
            } catch (Exception e) { e.printStackTrace(); }
            return list;
        }

        // UNTUK UPLOAD LINK
            public boolean uploadLink(materi m) {
                String sql = "INSERT INTO materi (kode_mk, judul, tipe, id_user, konten_link) VALUES (?, ?, ?, ?, ?)";

                try (Connection c = database_utility.getConnection(); 
                        PreparedStatement ps = c.prepareStatement(sql)) {
                    
                            ps.setString(1, m.getKodeMk());
                            ps.setString(2, m.getJudul());
                            ps.setString(3, "Link");        
                            ps.setInt(4, m.getIdUser());
                            ps.setString(5, m.getLink());

                            return ps.executeUpdate() > 0;
                    } catch (Exception e)  { e.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Error Upload Link: " + e.getMessage());
                        return false; }
            }

            public boolean uploadFile(materi m, File fileAsli) {
                String sql = "INSERT INTO materi (id_user, kode_mk, judul, tipe, nama_file, file_data) VALUES (?, ?, ?, 'File', ?, ?)";
                try (Connection c = database_utility.getConnection(); 
                        PreparedStatement ps = c.prepareStatement(sql); FileInputStream fis = new FileInputStream(fileAsli)) {
                    
                            ps.setInt(1, m.getIdUser()); ps.setString(2, m.getKodeMk());
                            ps.setString(3, m.getJudul()); ps.setString(4, fileAsli.getName());
                            ps.setBinaryStream(5, fis, (int) fileAsli.length());
                            return ps.executeUpdate() > 0;
                            
                } catch (Exception e) { e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null, "Error Upload File: " + e.getMessage());
                    return false; }
            }

            public boolean downloadFile(int idMateri, String pathTujuan) {
                String sql = "SELECT file_data FROM materi WHERE id_materi = ?";
                
                try (Connection c = database_utility.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, idMateri); ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        InputStream is = rs.getBinaryStream("file_data");
                        FileOutputStream fos = new FileOutputStream(pathTujuan);
                        byte[] buffer = new byte[1024]; int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) fos.write(buffer, 0, bytesRead);
                        fos.close(); is.close(); return true;
                        
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return false;
            }



    public List<materi> getAllMateri() {
        List<materi> list = new ArrayList<>();
        String sql = "SELECT m.*, mk.nama_mk, u.nama " +
                     "FROM materi m " +
                     "JOIN mata_kuliah mk ON m.kode_mk = mk.kode_mk " +
                     "JOIN user u ON m.id_user = u.id_user " +
                     "ORDER BY m.tanggal_upload DESC";
                     
        try (Connection c = database_utility.getConnection(); 
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            
            while(rs.next()) {
                // Ambil nama file atau link tergantung tipe
                String konten = rs.getString("tipe").equals("Link") ? 
                                rs.getString("konten_link") : 
                                rs.getString("nama_file");
                
                // Gunakan Konstruktor TAMPIL (sesuaikan urutan di Entity materi.java kamu)
                list.add(new materi(
                    rs.getInt("id_materi"),
                    rs.getString("nama"),   
                    rs.getString("kode_mk"),
                    rs.getString("nama_mk"), 
                    rs.getString("judul"),
                    rs.getString("tipe"),
                    rs.getString("konten_link"),
                    rs.getString("nama_file"),
                    rs.getString("tanggal_upload")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // METHOD CARI MATERI (Berdasarkan Judul / Nama Matkul)
    public List<materi> cariMateri(String keyword) {
        List<materi> list = new ArrayList<>();
        
        // Query: Cari di JUDUL atau NAMA MATKUL yang mengandung kata kunci
        String sql = "SELECT m.*, mk.nama_mk, u.nama " +
                     "FROM materi m " +
                     "JOIN mata_kuliah mk ON m.kode_mk = mk.kode_mk " +
                     "JOIN user u ON m.id_user = u.id_user " +
                     "WHERE m.judul LIKE ? OR mk.nama_mk LIKE ? " +
                     "ORDER BY m.tanggal_upload DESC";
                     
        try (Connection c = database_utility.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                String konten = rs.getString("tipe").equals("Link") ? 
                                rs.getString("konten_link") : 
                                rs.getString("nama_file");
                
                list.add(new materi(
                    rs.getInt("id_materi"),
                    rs.getString("nama"),
                    rs.getString("kode_mk"),
                    rs.getString("nama_mk"),
                    rs.getString("judul"),
                    rs.getString("tipe"),
                    rs.getString("konten_link"),
                    rs.getString("nama_file"),
                    rs.getString("tanggal_upload")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
}