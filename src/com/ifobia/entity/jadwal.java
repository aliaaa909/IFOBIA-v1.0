package com.ifobia.entity;

public class jadwal {
    private int id_jadwal;
    private String kode_mk; 
    private String hari;
    private String jam_mulai; //FORMAT HH:MM:SS
    private String jam_selesai;
    private String ruangan;

    // VARIABEL HASIL JOINNN
    private String nama_mk;
    private String nama_dosen;
    private String waktu; // jam_mulai - jam_selesai

    // Konstruktor untuk INSERT (INPUT OLEH PJ)
    public jadwal(String kode_mk, String hari, String jam_mulai, String jam_selesai, String ruangan) {
        this.kode_mk = kode_mk;
        this.hari = hari;
        this.jam_mulai = jam_mulai;
        this.jam_selesai = jam_selesai;
        this.ruangan = ruangan;
    }

    // Konstruktor untuk SELECT (OUTPUT DI DASHBOARD)
    public jadwal(int id_jadwal, String hari, String waktu, String nama_mk, String nama_dosen, String ruangan) {
        this.id_jadwal = id_jadwal;
        this.hari = hari;
        this.waktu = waktu;
        this.nama_mk = nama_mk;
        this.nama_dosen = nama_dosen;
        this.ruangan = ruangan;
    }

    // getter --> UTUK INSERT OLEH PJ
    public String getKode_mk() { 
        return kode_mk;
    }

    public String getHari() {
        return hari; 
    }
    
    public String getJam_mulai() {
        return jam_mulai;
    }
    
    public String getJam_selesai() {
        return jam_selesai;
    }
    
    public String getRuangan() {
        return ruangan; 
    }
        
    // uNTUK DI DASHBOARD
    public int getId_jadwal() { 
        return id_jadwal; 
    }
    
    public String getNama_mk() {
        return nama_mk; 
    }
    
    public String getNama_dosen() {
        return nama_dosen; 
    }
    
    public String getWaktu() { 
        return waktu; 
    }
}