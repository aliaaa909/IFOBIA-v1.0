package com.ifobia.entity;

public class materi {
    private int id_materi;
    private String nama_uploader;
    private String kode_mk; 
    private String nama_mk;
    private String judul;
    private String tipe; 
    private String konten_link;
    private String nama_file;
    private String tanggal;

    // Konstruktor TAMPIL (Lengkap)
    public materi(int id, String uploader, String kode, String namaMk, String judul, String tipe, String link, String filename, String tgl) {
        this.id_materi = id;
        this.nama_uploader = uploader;
        this.kode_mk = kode;
        this.nama_mk = namaMk;
        this.judul = judul;
        this.tipe = tipe;
        this.konten_link = link;
        this.nama_file = filename;
        this.tanggal = tgl;
    }
    
    // Konstruktor UPLOAD (Link)
    public materi(String kodeMk, String judul, int idUser, String link   ) {
        this.kode_mk = kodeMk;
        this.judul = judul;
        this.tipe = "Link";
        this.id_user = idUser;
        this.konten_link = link;
    }

    // Konstruktor UPLOAD (File)
    public materi(String kodeMk, String judul, String filename, int idUser) {
        this.kode_mk = kodeMk;
        this.judul = judul;
        this.tipe = "File";
        this.nama_file = filename;
        this.id_user = idUser;
    }

    // GETTERS
    public int getId() { 
        return id_materi;
    }
    
    public String getUploader() {
        return nama_uploader; 
    }
    
    public String getKodeMk() {
        return kode_mk; 
    }
    
    public String getNamaMk() {
        return nama_mk; 
    }
    
    public String getJudul() {
        return judul; 
    }
    
    public String getTipe() {
        return tipe; 
    }
    public String getLink() {
        return konten_link; 
    }
    
    public String getNamaFile() {
        return nama_file; 
    }
    
    public String getTanggal() {
        return tanggal; 
    }
    
   

    public void setIdMateri(int idMateri) { 
        this.id_materi = idMateri;
    }
    
    public void setUploader(String uploader) {
        this.nama_uploader = uploader; 
    }
    
    public void setKodeMk(String kodeMk) {
        this.kode_mk = kodeMk; 
    }
    
    public void setNamaMk(String namaMk) {
        this.nama_mk = namaMk; 
    }
    
    public void setJudul(String judul) {
        this.judul = judul; 
    }
    
    public void setTipe(String tipe) {
        this.tipe = tipe; 
    }
    
    public void setLink(String link) {
        this.konten_link = link; 
    }
    
    public void setNamaFile(String namaFile) {
        this.nama_file = namaFile; 
    }
    
    public void setTanggal(String tgl) {
        this.tanggal = tgl; 
    }
    
    public void setIdUser(int idUser) { 
        this.id_user = idUser; 
    }
    
    public materi() {}
    
    
    private int id_user;
    
    public int getIdUser() {
        return id_user; 
    }
}