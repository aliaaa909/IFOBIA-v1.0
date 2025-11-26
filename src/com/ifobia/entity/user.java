package com.ifobia.entity;

/**
 *
 * @author LENOVO
 */
public class user {
    private int id_user;
       private String nama;
       private String nim;
       private String kelas;
       private String username;
       private String password;
       private String role;

       // Ini konstruktor 7 parameter yang SUDAH ADA
       public user(int id_user, String nama, String nim, String kelas, String username, String password, String role) {
       this.id_user = id_user;
       this.nama = nama;
       this.nim = nim;
       this.kelas = kelas;
       this.username = username;
       this.password = password;
       this.role = role;
       }
       
       // ===============================================
       // == TAMBAHKAN KONSTRUKTOR 5 PARAMETER INI (FIX) ==
       public user(int id_user, String nama, String username, String password, String role) {
           this.id_user = id_user;
           this.nama = nama;
           this.username = username;
           this.password = password;
           this.role = role;
           
           // Set nilai default untuk field yang tidak ada
           this.nim = ""; 
           this.kelas = "";
       }
       // ===============================================


       // Getter dan Setter
       public int getId_user() {
           return id_user;
       }
       
       // (sisa getter dan setter kamu...)
       
       public void setId_user(int id_user) {
           this.id_user = id_user;
       }

       public String getNama() {
           return nama;
       }

       public void setNama(String nama) {
           this.nama = nama;
       }

       public String getNim() {
           return nim;
       }

       public void setNim(String nim) {
           this.nim = nim;
       }

       public String getKelas() {
           return kelas;
       }

       public void setKelas(String kelas) {
           this.kelas = kelas;
       }

       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }

       public String getRole() {
           return role;
       }

       public void setRole(String role) {
           this.role = role;
       }
}