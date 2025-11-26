package com.ifobia.entity;

public class absensi {
    private int id_user;
    private String NPM;
    private String nama;
    private String status;
    private String keterangan;


    public absensi(int id_user, String NPM, String nama) {
        this.id_user = id_user;
        this.NPM = NPM;
        this.nama = nama;
    }

    // GETTTTTTERRRRRRRRRRRR
    
    public int getIdUser() { 
        return id_user;
    }
    
    public String getNPM() { 
        return NPM; 
    }
    
    public String getNama() {
        return nama; 
    }
    
    public void setStatus(String s) {
        this.status = s; 
    }
    
    public void setKeterangan(String k) {
        this.keterangan = k; 
    }
    
}