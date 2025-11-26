package com.ifobia.entity_dao;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.pengumuman;
import com.ifobia.entity.mataKuliah;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class pengumumanDAO {

    // 1. Ambil Matkul
    public List<mataKuliah> getAllMataKuliah() {
        List<mataKuliah> list = new ArrayList<>();
        try (Connection c = database_utility.getConnection(); ResultSet rs = c.createStatement().executeQuery("SELECT * FROM mata_kuliah")) {
            while(rs.next()) list.add(new mataKuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), ""));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Simpan Pengumuman
    public boolean tambahPengumuman(pengumuman p, int id_user) {
        String sql = "INSERT INTO pengumuman (kode_mk, judul, isi, id_user) VALUES (?, ?, ?, ?)";
        try (Connection c = database_utility.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getKodeMk()); 
            ps.setString(2, p.getJudul()); 
            ps.setString(3, p.getIsi()); 
            ps.setInt(4, id_user);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 3. Ambil Data LENGKAP (Buat Dashboard & Menu Mahasiswa)
    public List<pengumuman> getPengumumanLengkap() {
        List<pengumuman> list = new ArrayList<>();
        String sql = "SELECT p.id_pengumuman, p.judul, p.isi, p.waktu_dibuat, u.nama, m.nama_mk FROM pengumuman p JOIN user u ON p.id_user=u.id_user JOIN mata_kuliah m ON p.kode_mk=m.kode_mk ORDER BY p.waktu_dibuat DESC";
        try (Connection c = database_utility.getConnection(); ResultSet rs = c.createStatement().executeQuery(sql)) {
            while(rs.next()) {
                list.add(new pengumuman(
                    rs.getInt("id_pengumuman"), rs.getString("judul"), rs.getString("isi"), rs.getTimestamp("waktu_dibuat"), rs.getString("nama"), rs.getString("nama_mk")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    //BIAR YG TAMPIL DI TABEL FORM KELOLA PENGUMUMAN CUMA PENGUMUMAN YG DIBUAT SM SI PJ AJA
    public List<pengumuman> getPengumumanByPj(int id_user) {
        List<pengumuman> list = new ArrayList<>();
        // Query ini memfilter WHERE p.id_user = ?
        String sql = "SELECT p.id_pengumuman, p.judul, p.isi, p.waktu_dibuat, u.nama, m.nama_mk " +
                     "FROM pengumuman p " +
                     "JOIN user u ON p.id_user=u.id_user " +
                     "JOIN mata_kuliah m ON p.kode_mk=m.kode_mk " +
                     "WHERE p.id_user = ? " + // <--- Filter user
                     "ORDER BY p.waktu_dibuat DESC";
        
        try (Connection c = database_utility.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, id_user);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(new pengumuman(
                    rs.getInt("id_pengumuman"), 
                    rs.getString("judul"), 
                    rs.getString("isi"), 
                    rs.getTimestamp("waktu_dibuat") , 
                    rs.getString("nama"),
                    rs.getString("nama_mk")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // --- 5. INI UNTUK HAPUS ---
    public boolean hapusPengumuman(int id) {
        String sql = "DELETE FROM pengumuman WHERE id_pengumuman=?";
        try (Connection c = database_utility.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            // executeUpdate return jumlah baris yg dihapus. Kalau > 0 berarti berhasil.
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
}