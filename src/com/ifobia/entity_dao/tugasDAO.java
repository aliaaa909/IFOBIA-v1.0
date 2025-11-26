package com.ifobia.entity_dao;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.mataKuliah; 
import com.ifobia.entity.tugas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class tugasDAO {
    
    public tugasDAO() {
    }
    
    /**
     * 1. Mengambil semua mata kuliah (untuk ComboBox)
     */
    public List<mataKuliah> getAllMataKuliah() {
        List<mataKuliah> daftarMatkul = new ArrayList<>();
        String sql = "SELECT * FROM mata_kuliah"; 

        try (Connection conn = database_utility.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mataKuliah matkul = new mataKuliah(
                    rs.getString("kode_mk"),
                    rs.getString("nama_mk"),
                    rs.getString("nama_dosen")
                );
                daftarMatkul.add(matkul);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarMatkul;
    }
    
    /**
     * 2. Menambah tugas baru 
     * Logika: Ambil SEMUA user langsung di sini, lalu bagikan tugas.
     */
    public boolean tambahTugas(tugas t) {
        String sqlTugas = "INSERT INTO tugas (kode_mk, judul, deskripsi, deadline) VALUES (?, ?, ?, ?)";
        // Insert status 'Belum' untuk siapapun yang dapet tugas
        String sqlStatus = "INSERT INTO status_tugas (id_user, id_tugas, isSelesai) VALUES (?, ?, 'Belum')";
        
        Connection conn = null;
        PreparedStatement psTugas = null;
        PreparedStatement psStatus = null;
        ResultSet generatedKeys = null;
        Statement stmtUser = null; // Buat ngambil user
        ResultSet rsUser = null;
        
        try {
            conn = database_utility.getConnection();
            conn.setAutoCommit(false); // Mulai transaksi

            // --- LANGKAH 1: SIMPAN TUGAS UTAMA ---
            psTugas = conn.prepareStatement(sqlTugas, Statement.RETURN_GENERATED_KEYS);
            psTugas.setString(1, t.getKode_mk());
            psTugas.setString(2, t.getJudul());
            psTugas.setString(3, t.getDeskripsi());
            psTugas.setString(4, t.getDeadline()); 
            psTugas.executeUpdate();
            
            // Ambil ID tugas yang baru dibuat
            generatedKeys = psTugas.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idTugasBaru = generatedKeys.getInt(1);
                
                // --- LANGKAH 2: AMBIL SEMUA ID USER (LANGSUNG DI SINI) ---
                List<Integer> semuaUserIds = new ArrayList<>();
                stmtUser = conn.createStatement();
                rsUser = stmtUser.executeQuery("SELECT id_user FROM user");
                
                while (rsUser.next()) {
                    semuaUserIds.add(rsUser.getInt("id_user"));
                }
                
                if (semuaUserIds.isEmpty()) {
                    System.out.println("WARNING: Tabel user kosong! Tidak ada yang dikirimi tugas.");
                }

                // --- LANGKAH 3: BAGIKAN TUGAS KE SEMUA ORANG ---
                psStatus = conn.prepareStatement(sqlStatus);
                
                for (Integer idUser : semuaUserIds) {
                    psStatus.setInt(1, idUser);       // ID Orang (PJ/Mhs)
                    psStatus.setInt(2, idTugasBaru);  // ID Tugas
                    psStatus.addBatch(); 
                }
                psStatus.executeBatch(); // Eksekusi massal
            }
            
            conn.commit(); // Simpan permanen
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            javax.swing.JOptionPane.showMessageDialog(null, "Error DAO: " + e.getMessage());
            return false;
        } finally {
            try { if (rsUser != null) rsUser.close(); } catch (SQLException e) {}
            try { if (stmtUser != null) stmtUser.close(); } catch (SQLException e) {}
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) {}
            try { if (psTugas != null) psTugas.close(); } catch (SQLException e) {}
            try { if (psStatus != null) psStatus.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
    
    /**
     * 3. Mengambil tugas milik USER TERTENTU (Dipakai Mahasiswa & PJ)
     */
    // tugasDAO.java

/**
 * 2. Mengambil semua tugas untuk user tertentu (yang belum selesai)
 * Dipakai di FormTugas dan FormDashboard
 */
public List<tugas> getAllTugas(int id_user) {
        List<tugas> daftarTugas = new ArrayList<>();
        
        // Hapus klausa WHERE isSelesai untuk menampilkan SEMUA tugas (Selesai dan Belum Selesai)
       String sql = "SELECT t.id_tugas, m.nama_mk, t.judul, t.deskripsi, t.deadline, st.isSelesai " +
                     "FROM tugas t " +
                     "JOIN mata_kuliah m ON t.kode_mk = m.kode_mk " +
                     "LEFT JOIN status_tugas st ON t.id_tugas = st.id_tugas AND st.id_user = ? " +
                     // URUTKAN UTAMA: Tugas BELUM SELESAI (NULL/Belum) duluan, baru tugas SELESAI
                     "ORDER BY " + 
                     "CASE WHEN st.isSelesai = 'Selesai' THEN 1 ELSE 0 END ASC, " + 
                     "t.deadline ASC"; // Urutan kedua: Berdasarkan deadline terdekat

        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_user); // Set id_user untuk klausa LEFT JOIN
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Jika status di DB NULL, kita anggap 'Belum'
                String status = rs.getString("isSelesai");
                if (status == null) status = "Belum";
                
                tugas t = new tugas(
                    rs.getInt("id_tugas"),
                    rs.getString("nama_mk"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getString("deadline"),
                    status // Sertakan status untuk ditampilkan di FormTugas
                );
                daftarTugas.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTugas;
    }

    /**
     * 4. Mengambil SEMUA TUGAS 
     */
    public List<tugas> getAllTugasForAdmin() {
        List<tugas> daftarTugas = new ArrayList<>();
        String sql = "SELECT t.id_tugas, m.nama_mk, t.judul, t.deskripsi, t.deadline " +
                     "FROM tugas t " +
                     "JOIN mata_kuliah m ON t.kode_mk = m.kode_mk " +
                     "ORDER BY t.deadline DESC"; 
                     
        try (Connection conn = database_utility.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tugas t = new tugas(
                    rs.getInt("id_tugas"),
                    rs.getString("nama_mk"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getString("deadline")
                );
                daftarTugas.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTugas;
    }
    
    // tugasDAO.java

/**
 * 6. Menghapus tugas berdasarkan ID Tugas
 * HATI-HATI: Hapus juga di status_tugas
 */
public boolean hapusTugas(int id_tugas) {
    Connection conn = null;
    try {
        conn = database_utility.getConnection();
        conn.setAutoCommit(false); // Mulai transaksi
        
        // 1. Hapus data terkait di tabel status_tugas (jika ada)
        String sqlStatus = "DELETE FROM status_tugas WHERE id_tugas = ?";
        try (PreparedStatement psStatus = conn.prepareStatement(sqlStatus)) {
            psStatus.setInt(1, id_tugas);
            psStatus.executeUpdate(); 
            // Tidak perlu cek > 0 karena mungkin belum ada status
        }
        
        // 2. Hapus tugas utama di tabel tugas
        String sqlTugas = "DELETE FROM tugas WHERE id_tugas = ?";
        boolean berhasilHapusTugas;
        try (PreparedStatement psTugas = conn.prepareStatement(sqlTugas)) {
            psTugas.setInt(1, id_tugas);
            berhasilHapusTugas = psTugas.executeUpdate() > 0;
        }
        
        conn.commit(); // Commit transaksi
        return berhasilHapusTugas;
        
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback(); // Rollback jika ada error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    /**
     * 5. Menandai tugas selesai
     */
    public boolean tandaiSelesai(int id_user, int id_tugas) {
       String sqlUpdate = "UPDATE status_tugas SET isSelesai = 'Selesai' WHERE id_user = ? AND id_tugas = ?";
        String sqlInsert = "INSERT INTO status_tugas (id_user, id_tugas, isSelesai) VALUES (?, ?, 'Selesai')";

        Connection conn = null;
        try {
            conn = database_utility.getConnection();
            
            // LANGKAH 1: Coba UPDATE status yang sudah ada
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, id_user);
                psUpdate.setInt(2, id_tugas);
                int updatedRows = psUpdate.executeUpdate();

                if (updatedRows > 0) {
                    return true; // Berhasil di-UPDATE
                }
            }
            
            // LANGKAH 2: Jika tidak ada baris yang di-UPDATE, berarti status belum ada. Lakukan INSERT.
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, id_user);
                psInsert.setInt(2, id_tugas);
                return psInsert.executeUpdate() > 0; // Berhasil di-INSERT
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<tugas> searchTugas(String key, int idUser) {
       List<tugas> daftarTugas = new ArrayList<>();
        
        String searchKey = "%" + key + "%"; 

        // Ubah SELECT untuk mengambil status (st.isSelesai)
        String sql = "SELECT t.id_tugas, m.nama_mk, t.judul, t.deskripsi, t.deadline, st.isSelesai " + 
                     "FROM tugas t " +
                     "JOIN mata_kuliah m ON t.kode_mk = m.kode_mk " +
                     "LEFT JOIN status_tugas st ON t.id_tugas = st.id_tugas AND st.id_user = ? " +
                     // Hapus filter status agar hasil pencarian menampilkan SEMUA status
                     "WHERE m.nama_mk LIKE ? OR t.judul LIKE ? " + 
                     "ORDER BY t.deadline ASC";

        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);      
            ps.setString(2, searchKey); 
            ps.setString(3, searchKey); 

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("isSelesai");
                    if (status == null) status = "Belum";
                    
                    tugas t = new tugas( // Menggunakan konstruktor 6 argumen
                        rs.getInt("id_tugas"),
                        rs.getString("nama_mk"),
                        rs.getString("judul"),
                        rs.getString("deskripsi"),
                        rs.getString("deadline"),
                        status // Tambah status
                    );
                    daftarTugas.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTugas;
    
}
    
}