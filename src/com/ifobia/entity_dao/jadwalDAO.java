package com.ifobia.entity_dao;

import com.ifobia.database_util.database_utility;
import com.ifobia.entity.jadwal;
import com.ifobia.entity.mataKuliah;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
// import java.sql.Statement; // Dihapus karena tidak terpakai

public class jadwalDAO {

    /**
     * 1. Mengambil informasi Mata Kuliah yang diampu oleh PJ (Penanggung Jawab) tertentu.
     * Digunakan pada FormKelolaJadwal.
     * @param idPj ID User dari PJ yang sedang login.
     * @return Array String [kode_mk, nama_mk] jika ditemukan, atau null jika tidak.
     */
    public String[] getMatkulPJ(int idPj) {
        // JOIN antara tabel pj_kelas dan mata_kuliah
        String sql = "SELECT pk.mata_kuliah, m.nama_mk " +
                     "FROM pj_kelas pk " +
                     "JOIN mata_kuliah m ON pk.mata_kuliah = m.kode_mk " +
                     "WHERE pk.id_user = ? LIMIT 1";

        try (Connection c = database_utility.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idPj);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Balikin [KODE_MK, NAMA_MK]
                return new String[]{ rs.getString("mata_kuliah"), rs.getString("nama_mk") };
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * 2. Mengambil jadwal yang sudah ada berdasarkan kode mata kuliah.
     * Digunakan untuk mengisi form saat PJ ingin mengedit jadwal.
     * @param kodeMk Kode Mata Kuliah yang ingin diambil jadwalnya.
     * @return Objek jadwal jika ditemukan, atau null.
     */
    public jadwal getJadwalByKode(String kodeMk) {
        String sql = "SELECT * FROM jadwal WHERE kode_mk = ?";
        try (Connection c = database_utility.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kodeMk);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Menggunakan konstruktor: (kode_mk, hari, jam_mulai, jam_selesai, ruangan)
                // Catatan: Di sini kita menggunakan String untuk jam_mulai/selesai, karena data
                // dari database sudah Time/String yang bisa diproses di entity jadwal.
                return new jadwal(
                    rs.getString("kode_mk"),
                    rs.getString("hari"),
                    rs.getString("jam_mulai"), // Ambil sebagai String
                    rs.getString("jam_selesai"),// Ambil sebagai String
                    rs.getString("ruangan")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * 3. Mengambil semua jadwal untuk ditampilkan di Dashboard.
     * Data digabungkan dengan Mata Kuliah dan diurutkan berdasarkan Hari dan Jam.
     * @return List objek jadwal.
     */
    public List<jadwal> getAlljadwalForDashboard() {
        List<jadwal> daftarjadwal = new ArrayList<>();

        String sql = "SELECT " +
                     "    j.id_jadwal, " +
                     "    j.hari, " +
                     "    CONCAT(TIME_FORMAT(j.jam_mulai, '%H:%i'), ' - ', TIME_FORMAT(j.jam_selesai, '%H:%i')) AS waktu, " +
                     "    m.nama_mk, " +
                     "    m.nama_dosen, " +
                     "    j.ruangan AS ruangan " + 
                     "FROM jadwal j " +
                     "JOIN mata_kuliah m ON j.kode_mk = m.kode_mk " +
                     // Mengurutkan Hari: Senin, Selasa, ..., Minggu, dan Jam Mulai
                     "ORDER BY FIELD(j.hari, 'Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'), j.jam_mulai";

        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql); // Ubah Statement ke PreparedStatement
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                jadwal jadwal = new jadwal(
                    rs.getInt("id_jadwal"),
                    rs.getString("hari"),
                    rs.getString("waktu"),
                    rs.getString("nama_mk"),
                    rs.getString("nama_dosen"),
                    rs.getString("ruangan")
                );
                daftarjadwal.add(jadwal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return daftarjadwal;
    }

    /**
     * 4. Mengambil semua Mata Kuliah (untuk ComboBox di Form Admin/PJ).
     * @return List objek mataKuliah.
     */
    public List<mataKuliah> getAllMataKuliah() {
        List<mataKuliah> daftarMatkul = new ArrayList<>();
        String sql = "SELECT * FROM mata_kuliah";

        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql); // Ubah Statement ke PreparedStatement
             ResultSet rs = ps.executeQuery()) {

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
     * 5. Menambah jadwal baru ke database.
     * @param jadwal Objek jadwal yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean tambahjadwal(jadwal jadwal) {
        String sql = "INSERT INTO jadwal (kode_mk, hari, jam_mulai, jam_selesai, ruangan) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jadwal.getKode_mk());
            ps.setString(2, jadwal.getHari());
            ps.setTime(3, Time.valueOf(jadwal.getJam_mulai()));
            ps.setTime(4, Time.valueOf(jadwal.getJam_selesai()));
            ps.setString(5, jadwal.getRuangan());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 6. Mengupdate jadwal jika sudah ada, atau menambah baru jika belum ada (Upsert logic).
     * @param jadwal Objek jadwal yang akan diupdate/disimpan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateJadwal(jadwal jadwal) {

        // 1. Kita cek dulu, apakah jadwal untuk MK ini sudah ada?
        if (cekJadwalExist(jadwal.getKode_mk())) {
            // Kalo sudah ada, kita UPDATE (Perbarui)
            String sql = "UPDATE jadwal SET hari = ?, jam_mulai = ?, jam_selesai = ?, ruangan = ? WHERE kode_mk = ?";

            try (Connection conn = database_utility.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, jadwal.getHari());
                ps.setTime(2, Time.valueOf(jadwal.getJam_mulai()));
                ps.setTime(3, Time.valueOf(jadwal.getJam_selesai()));
                ps.setString(4, jadwal.getRuangan());
                ps.setString(5, jadwal.getKode_mk()); // Where clause

                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            // Kalo belum ada, barulah kita INSERT (Tambah baru)
            return tambahjadwal(jadwal);
        }
    }

    /**
     * 7. Method bantuan untuk mengecek apakah jadwal sudah ada untuk kode mata kuliah tertentu.
     * @param kodeMk Kode Mata Kuliah.
     * @return true jika sudah ada, false jika belum.
     */
    private boolean cekJadwalExist(String kodeMk) {
        String sql = "SELECT count(*) FROM jadwal WHERE kode_mk = ?";
        try (Connection conn = database_utility.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeMk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}