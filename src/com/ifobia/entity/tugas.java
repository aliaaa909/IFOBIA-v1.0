package com.ifobia.entity;

public class tugas {
    private int id_tugas;
    private String kode_mk;
    private String judul;
    private String deskripsi;
    private String deadline;
    private String isSelesai;
    private String nama_mk;
    
    /**
     * Konstruktor 1: Untuk INSERT data baru (oleh PJ)
     */
    public tugas (String kode_mk, String judul, String deskripsi, String  deadline) {
        this.kode_mk  = kode_mk;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
    }
    
    /**
     * Konstruktor 2: Untuk SELECT data (tabel Mahasiswa)
     */
    public tugas (int id_tugas, String nama_mk, String judul, String deskripsi, String deadline, String isSelesai) {
        this.id_tugas = id_tugas;
        this.nama_mk = nama_mk;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.isSelesai = isSelesai;
    }
    
    // --- TAMBAHAN KONSTRUKTOR BARU ---
    
    public tugas (int id_tugas, String nama_mk, String judul, String deskripsi, String deadline) {
        this.id_tugas = id_tugas;
        this.nama_mk = nama_mk;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
    }
    
    public int getId_tugas() { return id_tugas; }
    public String getKode_mk() { return kode_mk; }
    public String getJudul() { return judul; }
    public String getDeskripsi() { return deskripsi; }
    public String getDeadline() { return deadline; }
    public String getIsSelesai() { return isSelesai; }
    public String getNama_mk() { return nama_mk; }
    
    public String getDashboardText() {
    return String.format("%s\n  %s\n  (Deadline: %s)\n", 
        this.nama_mk, 
        this.judul, 
        this.deadline);
}
}