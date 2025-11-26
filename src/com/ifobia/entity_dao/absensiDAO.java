package com.ifobia.entity_dao;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.absensi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// INI CLASS DAO (DATA ACCESS OBJECT), JADI KAYAK JEMBATAN BUAT NGOBROL SAMA DATABASE KHUSUS URUSAN ABSENSI.
public class absensiDAO {

    // 1. METHOD BUAT NGAMBIL SEMUA DATA ORANG DARI DATABASE
    public List<absensi> getSemuaMahasiswa() {
        List<absensi> list = new ArrayList<>();
        
        // AMBIL ID, NPM, SAMA NAMA DARI TABEL USER.
        String sql = "SELECT id_user, NPM, nama FROM user";
        
        // BUKA PINTU KONEKSI KE DATABASE 
        try (Connection c = database_utility.getConnection(); 
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            
            // SELAMA MASIH ADA DATA DI DATABASE (LOOPING PER BARIISS)
            while(rs.next()) {
                // MASUKIN DATA YANG DIDAPET KE  LIST OBJECT ABSENSI
                list.add(new absensi(
                    rs.getInt("id_user"), 
                    rs.getString("NPM"), 
                    rs.getString("nama")
                ));
            }
        } catch (Exception e) { 
            // KALAU ADA ERROR, print ERRORNYS DI CONSOLE
            e.printStackTrace(); 
        }
        return list;
    }
    
    // 2. METHOD BUAT NGECEK MATKUL APA YANG DIURUS SI PJ: JADI ABSEN OTOMATIS DISIMPEN BEDASARKAN MATKUL YANG DIPEGANG PJ
    public String[] getMatkulPJ(int idPj) {
        // JOIN ANTARA TABEL PJ_KELAS SAMA MATA_KULIAH. BIAR DAPET KODE MATKUL YG DIPEGANG SI PJ
        String sql = "SELECT pk.mata_kuliah, m.nama_mk " +
                     "FROM pj_kelas pk " +
                     "JOIN mata_kuliah m ON pk.mata_kuliah = m.kode_mk " +
                     "WHERE pk.id_user = ? LIMIT 1";
        
        try (Connection c = database_utility.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            // ISI TANDA TANYA (?) DI QUERY DI ATAS DENGAN ID SI PJ YANG LAGI LOGIN
            ps.setInt(1, idPj);
            ResultSet rs = ps.executeQuery();
            
            // KALAU DATANYA KETEMU, BALIKIN ARRAY ISINYA [KODE_MK, NAMA_MK]
            if (rs.next()) {
                return new String[]{ rs.getString("mata_kuliah"), rs.getString("nama_mk") };
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        return null;
    }
    
    // 3. METHOD BUAT NYIMPEN ABSENSI KE DATABASE
    public boolean simpanAbsensi(String kodeMk, int idUser, String status, String ket) {
        // "INSERT... ON DUPLICATE KEY UPDATE". SRTINYA: COBA MASUKIN DATA BARU. TAPI KALAU DATA (ORANG & HARI ITU) UDAH ADA, JANGAN ERROR, TAPI UPDATE AJA STATUSNYA. JADI BISA REVISI ABSEN.
        String sql = "INSERT INTO absensi (kode_mk, id_user, status, keterangan, tanggal)" + 
                     "VALUES (?, ?, ?, ?, CURRENT_DATE())" + 
                     "ON DUPLICATE KEY UPDATE status = VALUES(status), keterangan = VALUES(keterangan)";

        try (Connection c = database_utility.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            // ISI PARAMETER QUERY DENGAN DATA DARI FORM
            ps.setString(1, kodeMk);
            ps.setInt(2, idUser);
            ps.setString(3, status);
            ps.setString(4, ket);
            
            // JALANKAN PERINTAHNYA. KALAU BERHASIL (>0), BALIKIN TRUE.
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
    
    // ===========================================================================================================================================================
  
    // 4. METHOD BUAT NGAMBIL LIST TANGGAL BERAPA AJA YANG PERNAH ADA ABSENSI
    // INI DIPAKE BUAT BIKIN JUDUL KOLOM DI TABEL REKAP (DINAMIS).
    public List<String> getListTanggal(String kodeMk) {
        List<String> list = new ArrayList<>();
        
        // PAKE 'DISTINCT' BIAR TANGGALNYA ENGGA DOOUBLE DIURUTIN DARI YANG TERLAMA (ASC) BIAR RAPI DARI KIRI KE KANAN.
        String sql = "SELECT DISTINCT tanggal FROM absensi WHERE kode_mk = ? ORDER BY tanggal ASC";
        
        try (Connection c = database_utility.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
             
            ps.setString(1, kodeMk);
            ResultSet rs = ps.executeQuery();
            
            // MASUKIN SEMUA TANGGAL YANG DITEMUKAN KE DALAM LIST
            while (rs.next()) {
                list.add(rs.getString("tanggal"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 5. METHOD BUAT NGECEK STATUS SPESIFIK SATU ORANG DI TANGGAL TERTENTU
    public String getStatusAbsen(String kodeMk, int idUser, String tanggal) {
        String status = null;
        
        String sql = "SELECT status FROM absensi WHERE kode_mk = ? AND id_user = ? AND tanggal = ?";
        
        try (Connection c = database_utility.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
             
            ps.setString(1, kodeMk);
            ps.setInt(2, idUser);
            ps.setString(3, tanggal);
            
            ResultSet rs = ps.executeQuery();
            
            // KALAU DATANYA ADA, AMBIL  STATUSNYA (HADIR/SAKIT/IZIN/ALPHA)
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // BALIKIN STATUSNYA (ATAU NULL KALAU BELUM ABSEN HARI ITU)
        return status;
    }
    
}