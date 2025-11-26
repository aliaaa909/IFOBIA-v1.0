package com.ifobia.entity;

// Ganti nama class dari 'mataKuliah' menjadi 'MataKuliah' (Best practice)
public class mataKuliah {
    private String kode_mk;
    private String nama_mk;
    private String nama_dosen;

    // Konstruktor
    public mataKuliah(String kode_mk, String nama_mk, String nama_dosen) {
        this.kode_mk = kode_mk;
        this.nama_mk = nama_mk;
        this.nama_dosen = nama_dosen;
    }

    // --- Getters ---
    public String getKode_mk() {
        return kode_mk;
    }

    public String getNama_mk() {
        return nama_mk;
    }

    public String getNama_dosen() {
        return nama_dosen;
    }

    // --- PENTING UNTUK COMBOBOX (DROPDOWN) ---
    // Ini membuat dropdown menampilkan nama matkul,
    // bukan "com.ifobia.entity.MataKuliah@1a2b3c"
    @Override
    public String toString() {
        return this.nama_mk; // Tampilkan nama_mk di ComboBox
    }
}