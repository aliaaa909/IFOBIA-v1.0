package com.ifobia.entity;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class pengumuman {
    private int id_pengumuman; // ID ini kuncinya
    private String kode_mk;
    private String judul;
    private String isi;
    private Timestamp waktu_dibuat;
    private String nama_pembuat; 
    private String nama_mk;

    // Konstruktor INSERT
    public pengumuman(String kode_mk, String judul, String isi, int id_pengumuman) {
        this.kode_mk = kode_mk;
        this.judul = judul;
        this.isi = isi;
    }
    // Konstruktor TAMPIL
    public pengumuman(int id, String judul, String isi, Timestamp waktu, String pembuat, String mk) {
        this.id_pengumuman = id;
        this.judul = judul;
        this.isi = isi;
        this.waktu_dibuat = waktu;
        this.nama_pembuat = pembuat;
        this.nama_mk = mk;
    }
    
    // Getters
    public int getId_pengumuman() { return id_pengumuman; } // <--- WAJIB ADA
    public String getKodeMk() { return kode_mk; }
    public String getJudul() { return judul; }
    public String getIsi() { return isi; }
    public String getNamaMk() { return nama_mk; }
    public String getDashboardText() { return nama_mk + " - " + judul; }
    public String getFooter() { 
        if(waktu_dibuat==null) return "";
        return "Oleh: " + nama_pembuat + " | " + new SimpleDateFormat("dd MMM HH:mm").format(waktu_dibuat); 
    }
    
   


}