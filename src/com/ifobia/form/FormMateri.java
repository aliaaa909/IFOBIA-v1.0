/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ifobia.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.ifobia.entity_dao.materiDAO;
import com.ifobia.database_util.database_utility; // Pastikan import ini ada
import com.ifobia.entity.mataKuliah;
import com.ifobia.entity.materi;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import static java.nio.file.Files.list;
import static java.util.Collections.list;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andhika Eka Pratama
 */
public class FormMateri extends javax.swing.JPanel {
    
    private materiDAO dao;
    private DefaultComboBoxModel<mataKuliah> comboModel;
    
    private int currentUserId = 1; 
    
    // VARIABEL PENAMPUNG TEMPORARY
    private File tempFile = null;
    private String tempLink = null;
    private String modeUpload = "";
    
    
    public FormMateri() {
        initComponents();
        
        
        dao = new materiDAO();
        comboModel = new DefaultComboBoxModel<>();
        jComboBoxMatkul.setModel(comboModel);
        
        jTextField1.setEditable(false); 
        txtJudul.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contoh: Pertemuan 1 - Pengantar Algoritma");
        txtCari.putClientProperty(com.formdev.flatlaf.FlatClientProperties.PLACEHOLDER_TEXT, "Cari Judul / Matkul...");
        txtCari.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, "arc:10"); // Biar agak bulat

        loadComboMatkul();
        loadTableMateri();
    
    }
    
       private void loadComboMatkul() {
        comboModel.removeAllElements();
        List<mataKuliah> list = dao.getAllMataKuliah();
        for (mataKuliah m : list) {
            comboModel.addElement(m);
        }
    }
    
    private void aksiPilihFile() {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FormUploadFileMateri dialog = new FormUploadFileMateri(parentFrame, true);
        dialog.setVisible(true);
        

        File hasil = dialog.getFile();
        
        if (hasil != null) {
            //SIMPEN DI VARIABEL TEMP DULU
            this.tempFile = hasil;
            this.modeUpload = "FILE";
            

            this.tempLink = null; 
            
            //TAMPILIN NAMANYA DI TEXT FIELDS
            jTextField1.setText(hasil.getName());
        }
    }

    // --- 2. LOGIKA TOMBOL "INPUT LINK" ---
    private void aksiInputLink() {
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FormUploadLinkMateri dialog = new FormUploadLinkMateri(parentFrame, true);
        dialog.setVisible(true);
        
        // Ambil hasil dari dialog
        String hasil = dialog.getLink();
        
        if (hasil != null && !hasil.isEmpty()) {
            // Simpan ke variabel sementara
            this.tempLink = hasil;
            this.modeUpload = "LINK";
            
            // Reset variabel file biar gak bentrok
            this.tempFile = null;
            
            // TAMPILKAN DI JTEXTFIELD1
            jTextField1.setText(hasil);
        }
    }

    private void prosesBagikanMateri() {
        // Validasi Input Dasar
        String judul = txtJudul.getText();
        if (jComboBoxMatkul.getSelectedItem() == null) {
             JOptionPane.showMessageDialog(this, "Pilih mata kuliah dulu!"); return;
        }
        String matkulRaw = jComboBoxMatkul.getSelectedItem().toString();
        String kodeMk = matkulRaw.split("-")[0].trim(); 

        if (judul.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul harus diisi!"); return;
        }
        
        if (modeUpload.equals("")) {
             JOptionPane.showMessageDialog(this, "Belum ada materi yang dipilih (File/Link)!");
             return;
        }

        boolean sukses = false;

        // EKSEKUSI SIMPAN KE DATABASE
        com.ifobia.entity.user u = com.ifobia.main.FormMenuUtama.getLoggedInUser();


        if (modeUpload.equals("Link") && tempLink != null) {
            materi m = new materi(kodeMk, judul, u.getId_user(), tempLink);

            // Panggil fungsi uploadLink di DAO
            sukses = dao.uploadLink(m);

        } else if (modeUpload.equals("File") && tempFile != null) {
            materi m = new materi(kodeMk, judul, tempFile.getName(), u.getId_user());
            
            sukses = dao.uploadFile(m, tempFile);
        }

        // HASIL AKHIR
        if (sukses) {
            JOptionPane.showMessageDialog(this, "Upload Success! Materi berhasil dibagikan.");
            loadTableMateri(); 
            resetForm();       
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan materi ke database.");
        }
    }

    private void resetForm() {
        txtJudul.setText("");
        jTextField1.setText(""); 
        tempFile = null;
        tempLink = null;
        modeUpload = "";
        jComboBoxMatkul.setSelectedIndex(0);
    }



private void loadTableMateri() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        model.setRowCount(0);
        model.setColumnCount(0);
        
        model.setColumnIdentifiers(new Object[] {
            "Mata Kuliah",     // Kolom 0 (Tampil)
            "Detail Materi",   // Kolom 1 (Tampil - HTML)
            "ID_Hidden",       // Kolom 2 (Sembunyi)
            "Tipe_Hidden",     // Kolom 3 (Sembunyi)
            "Konten_Hidden"    // Kolom 4 (Sembunyi - Link/Path Asli)
        });
        
        // Ambil data dari DAO
        List<materi> list = dao.getAllMateri();
        
        for (materi m : list) {
            String icon = m.getTipe().equals("Link") ? "🔗" : "📄";
            String kontenTampil = m.getTipe().equals("Link") ? m.getLink() : m.getNamaFile();
            if (kontenTampil == null) {
                kontenTampil = ""; 
            }
            
            // HTML FORMATTTING
            String html = "<html>" +
                          "<b style='font-size:110%'>" + m.getJudul() + "</b><br>" + 
                          "<span style='color:blue'>" + icon + " " + m.getTipe() + "</span><br>" +
                          "<i style='color:gray'>Uploaded by: " + m.getUploader() + " | " + m.getTanggal() + "</i>" +
                          "</html>";
            
            model.addRow(new Object[] {
                m.getNamaMk(),   
                html,           
                m.getId(),  
                m.getTipe(),     
                kontenTampil     
            });
        }
        
        // SETTING TAMPILAN TABEL
        jTable1.setRowHeight(90); 
        
            if (jTable1.getColumnModel().getColumnCount() > 0) {
            
            // --- KOLOM 0: MATA KULIAH (Kecil) ---
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(150); // Cukup segini
            
            // --- KOLOM 1: DETAIL MATERI (Sedang) ---
            // JANGAN dikosongin/dibebasin. Kita kasih angka juga.
            jTable1.getColumnModel().getColumn(1).setMinWidth(200);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(250); // Jangan kegedean
            
            // --- KOLOM RAHASIA (TETAP SEMBUNYI) ---
            for (int i = 2; i <= 4; i++) {
                jTable1.getColumnModel().getColumn(i).setMinWidth(0);
                jTable1.getColumnModel().getColumn(i).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(i).setWidth(0);
            }
        }
        }
            
    

    private void aturStatusTombol() {
        if ("File".equals(modeUpload)) {
            // Kalo lagi mode FILE:
            btnFile.setEnabled(true);       // Tombol File tetap nyala (biar bisa ganti file)
            btnLink.setEnabled(false);      // Tombol Link MATI
            btnFile.setText("Ganti File");  // Ubah teks biar jelas
            
        } else if ("Link".equals(modeUpload)) {
            // Kalo lagi mode LINK:
            btnFile.setEnabled(false);      // Tombol File MATI
            btnLink.setEnabled(true);       // Tombol Link tetap nyala
            btnLink.setText("Ganti Link");  // Ubah teks biar jelas
            
        } else {
            // Kalo NETRAL (Belum pilih apa-apa / Habis Reset):
            btnFile.setEnabled(true);
            btnLink.setEnabled(true);
            btnFile.setText("Pilih File");  // Balikin teks asli
            btnLink.setText("Input Link");  // Balikin teks asli
            jTextField1.setText("");        // Kosongin text field
        }
    }
    
    private void tampilkanPencarian(String keyword) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // 1. Bersihkan Tabel
        model.setRowCount(0);
        
        // 2. Ambil Data dari DAO (Method Cari)
        List<materi> list = dao.cariMateri(keyword);
        
        // 3. Looping & Format HTML (Sama persis kayak loadTableMateri kamu)
        for (materi m : list) {
            String icon = m.getTipe().equals("Link") ? "🔗" : "📄";
            String kontenTampil = m.getTipe().equals("Link") ? m.getLink() : m.getNamaFile();
            
            String html = "<html>" +
                          "<b style='font-size:110%'>" + m.getJudul() + "</b><br>" + 
                          "<span style='color:blue'>" + icon + " " + m.getTipe() + "</span><br>" +
                          "<i style='color:gray'>Uploaded by: " + m.getUploader() + " | " + m.getTanggal() + "</i>" +
                          "</html>";
            
            model.addRow(new Object[] {
                m.getNamaMk(),   // Col 0
                html,            // Col 1 (Detail)
                m.getId(),       // Col 2 (ID)
                m.getTipe(),     // Col 3 (Tipe)
                kontenTampil     // Col 4 (Konten)
            });
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialog1 = new javax.swing.JDialog();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jComboBoxMatkul = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnFile = new javax.swing.JButton();
        btnLink = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(13, 71, 161));
        jButton1.setFont(new java.awt.Font("Leelawadee", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Bagikan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
                prosesBagikanMateri(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 288, -1, -1));

        jPanel2.add(jComboBoxMatkul, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 106, 234, -1));

        jLabel2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel2.setText("Mata Kuliah");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 85, -1, -1));
        jPanel2.add(txtJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 154, 234, -1));

        jLabel3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel3.setText("Judul");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 134, -1, -1));

        jTextField1.setEditable(false);
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 254, 234, -1));

        jLabel4.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel4.setText("Materi");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 187, -1, -1));

        btnFile.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        btnFile.setText("Upload File");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });
        jPanel2.add(btnFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 208, 108, -1));

        btnLink.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        btnLink.setText("Upload Link");
        btnLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinkActionPerformed(evt);
            }
        });
        jPanel2.add(btnLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 208, 108, -1));

        jLabel11.setFont(new java.awt.Font("Leelawadee", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Upload Materi Baru");
        jLabel11.setToolTipText("");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 35, -1, -1));

        btnReset.setBackground(new java.awt.Color(255, 0, 51));
        btnReset.setFont(new java.awt.Font("Leelawadee", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Batal");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
                btnResetprosesBagikanMateri(evt);
            }
        });
        jPanel2.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 288, -1, -1));

        jLabel9.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        jLabel9.setText("Materi Pembelajaran");

        jButton2.setBackground(new java.awt.Color(0, 51, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cari");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mata Kuliah", "Judul", "Materi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel9)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                
    // --- 1. VALIDASI MATKUL (CASTING OBJECT) ---
    Object selectedObj = jComboBoxMatkul.getSelectedItem();
    
    if (selectedObj == null || selectedObj instanceof String) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih Mata Kuliah dulu!");
        return;
    }
    
    com.ifobia.entity.mataKuliah mk = (com.ifobia.entity.mataKuliah) selectedObj;
    String kodeMk = mk.getKode_mk(); 
    
    // --- 2. VALIDASI JUDUL & KONTEN ---
    String judul = txtJudul.getText();
    String konten = jTextField1.getText(); 
    
    if (judul.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Judul Materi masih kosong!"); return;
    }
    if (konten.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Belum ada Link/File yang dipilih!"); return;
    }
    
    // --- 3. PROSES SIMPAN KE DATABASE ---
    com.ifobia.entity.user u = com.ifobia.main.FormMenuUtama.getLoggedInUser();
    boolean sukses = false;
    
    if (modeUpload.equals("Link") && tempLink != null) {
        materi m = new materi(kodeMk, judul, u.getId_user(), tempLink);
        
        sukses = dao.uploadLink(m);
        
    } else if (modeUpload.equals("File") && tempFile != null) {
        materi m = new materi(kodeMk, judul, tempFile.getName(), u.getId_user());
        
        sukses = dao.uploadFile(m, tempFile);
    }
    
    // --- 4. CEK HASIL ---
    if (sukses) {
        javax.swing.JOptionPane.showMessageDialog(this, "Berhasil Membagikan Materi!");
        // Reset Form biar bersih
        txtJudul.setText("");
        jTextField1.setText("");
        tempLink = null;
        tempFile = null;
        modeUpload = "";
        
        // Refresh Tabel biar data baru muncul
        loadTableMateri(); 
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan ke database.");
    }

    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileActionPerformed
                          
        // 1. Buka Dialog File
        java.awt.Frame parent = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FormUploadFileMateri dialog = new FormUploadFileMateri(parent, true);
        dialog.setVisible(true);
        
        // 2. Ambil File Hasil
        File hasilFile = dialog.getFile();
        
        if (hasilFile != null) {
            // TAMPILKAN NAMA FILE DI TEXTFIELD1
            jTextField1.setText(hasilFile.getName());
            
            // SIMPAN DI VARIABEL SEMENTARA
            this.tempFile = hasilFile;
            this.modeUpload = "File";
            
            // Reset mode link
            this.tempLink = null;
        }
        aturStatusTombol();
    
    }//GEN-LAST:event_btnFileActionPerformed

    private void btnLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinkActionPerformed
                                        
        // 1. Buka Dialog Link
        java.awt.Frame parent = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FormUploadLinkMateri dialog = new FormUploadLinkMateri(parent, true);
        dialog.setVisible(true);
        
        // 2. Ambil Hasil Inputan
        String hasilLink = dialog.getLink();
        
        if (hasilLink != null && !hasilLink.isEmpty()) {
            // TAMPILKAN DI TEXTFIELD1
            jTextField1.setText(hasilLink);
            
            // SIMPAN DI VARIABEL SEMENTARA
            this.tempLink = hasilLink;
            this.modeUpload = "Link";
            
            // Reset mode file biar gak bentrok
            this.tempFile = null; 
        }
        
        aturStatusTombol();
    
    }//GEN-LAST:event_btnLinkActionPerformed

    private void prosesBagikanMateri(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prosesBagikanMateri
        // TODO add your handling code here:
    }//GEN-LAST:event_prosesBagikanMateri

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
                                        
        // Reset Semua Variabel
        this.modeUpload = "";
        this.tempFile = null;
        this.tempLink = null;
        
        // Panggil method buat nyalain semua tombol lagi
        aturStatusTombol();
          // TODO add your handling code here:
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnResetprosesBagikanMateri(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetprosesBagikanMateri
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetprosesBagikanMateri

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
                                       
        String key = txtCari.getText();
        
        if (key.isEmpty()) {
            loadTableMateri(); // Kalau kosong, balikin ke semua data
        } else {
            tampilkanPencarian(key); // Kalau ada isi, cari datanya
        }
    
    }//GEN-LAST:event_txtCariKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // Cek baris mana yang diklik user
        int row = jTable1.getSelectedRow();
        if (row == -1) return; // Kalau gak ada yg diklik, stop

        String idStr = jTable1.getValueAt(row, 2).toString(); // Ambil ID
        int idMateri = Integer.parseInt(idStr);

        String tipe = jTable1.getValueAt(row, 3).toString();

        Object objKonten = jTable1.getValueAt(row, 4);

        String konten = (objKonten != null) ? objKonten.toString().trim() : "";

        if (konten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Link atau File materi tidak ditemukan (Kosong)!");
            return;
        }

        int jawab = JOptionPane.showConfirmDialog(this,
            "Ingin mengakses materi ini?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (jawab == JOptionPane.YES_OPTION) {

            if (tipe.equalsIgnoreCase("Link")) {
                try {
                    String url = konten;

                    // Validasi Protokol HTTP/HTTPS
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }

                    // Ganti spasi dengan %20 agar tidak error URI Syntax
                    url = url.replace(" ", "%20");

                    // Debugging (Cek output di Netbeans bagian bawah)
                    System.out.println("Mencoba membuka URL: " + url);

                    // Cara 1: Desktop API (Paling Aman)
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(url));
                    }
                    // Cara 2: Windows Fallback (Jika Cara 1 gagal)
                    else {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                    }

                } catch (Exception e) {
                    e.printStackTrace(); // Tampilkan error lengkap di console
                    JOptionPane.showMessageDialog(this,
                        "Gagal membuka link!\nError: " + e.getMessage() +
                        "\n\nPastikan link valid (contoh: google.com)",
                        "Error Link", JOptionPane.ERROR_MESSAGE);
                }

                // LOGIKA: Kalau FILE -> Download
            } else {
                javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
                fc.setSelectedFile(new File(konten)); // Kasih nama default

                // Munculin dialog "Save As"
                if (fc.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
                    String pathSimpan = fc.getSelectedFile().getAbsolutePath();

                    // Panggil DAO buat sedot file dari database
                    if (dao.downloadFile(idMateri, pathSimpan)) {
                        JOptionPane.showMessageDialog(this, "Download Berhasil!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal Download (Cek Database).");
                    }
                }
            }
        }

    }//GEN-LAST:event_jTable1MouseClicked


    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnLink;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<mataKuliah> jComboBoxMatkul;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJudul;
    // End of variables declaration//GEN-END:variables



}
