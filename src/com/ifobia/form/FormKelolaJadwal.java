package com.ifobia.form;


import com.formdev.flatlaf.FlatClientProperties;
import com.ifobia.entity_dao.jadwalDAO;
import com.ifobia.entity.jadwal;
import com.ifobia.entity.mataKuliah;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormKelolaJadwal extends javax.swing.JPanel {

    private jadwalDAO dao;
    private DefaultTableModel tableModel;
    
    
    
    private String kodeMkAmpuan = null; 
    private String namaMkAmpuan = null;


    public FormKelolaJadwal() {
        initComponents();       

        // INITIALIZE BACKEND
        dao = new jadwalDAO();
        
            tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "ID", "Hari", "Waktu", "Mata Kuliah", "Dosen", "Ruangan"
            }
        );
        
        txtJamMulai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "00:00");
        txtJamSelesai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "00:00");
        jTable1.setModel(tableModel);
        
        
        cekMatkulPj();
    }
    
    private String validasiWaktu(String waktu) {
        // Hapus spasi di awal/akhir
        waktu = waktu.trim(); 
        
        try {
            // 1. Coba validasi dengan format HH:mm:ss
            java.sql.Time.valueOf(waktu);
            return waktu; // Format sudah benar (Contoh: 08:00:00)
            
        } catch (IllegalArgumentException e) {
            
            // 2. Kalau gagal, coba validasi dengan format HH:mm (dengan menambahkan ":00" di belakang)
            try {
                String waktuDenganDetik = waktu + ":00";
                java.sql.Time.valueOf(waktuDenganDetik);
                return waktuDenganDetik; // Format diperbaiki (Contoh: 08:00 menjadi 08:00:00)
                
            } catch (IllegalArgumentException ex) {
                
                // 3. Kalau masih gagal juga, berarti input user salah
                return null; 
            }
        }
    }
    
    private void cekMatkulPj() {
        // Ambil user yang login
        com.ifobia.entity.user pj = com.ifobia.main.FormMenuUtama.getLoggedInUser();
        
        if (pj != null) {
            // Tanya ke Database: User ini pegang matkul apa?
            String[] infoMatkul = dao.getMatkulPJ(pj.getId_user());
            
            if (infoMatkul != null) {
                kodeMkAmpuan = infoMatkul[0]; 
                namaMkAmpuan = infoMatkul[1];
                
                lblInfoMatkul.setText("Mata Kuliah " + namaMkAmpuan);
                loadJadwalForm();
            } 
        } 
    }

    //DATA DR DAO DIAMBIL DAN DIMASUKKAN KE TABEL DI FORM ENIH
    private void loadJadwalForm() {
        tableModel.setRowCount(0); 
        List<jadwal> daftarjadwal = dao.getAlljadwalForDashboard(); 

        for (jadwal jadwal : daftarjadwal) {
            Object[] row = {
                jadwal.getId_jadwal(), 
                jadwal.getHari(),
                jadwal.getWaktu(),
                jadwal.getNama_mk(),
                jadwal.getNama_dosen(),
                jadwal.getRuangan()
            };
            tableModel.addRow(row);
        }
        
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
    }
    
   

    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblInfoMatkul1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtJamMulai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRuangan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBoxHari = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtJamSelesai = new javax.swing.JTextField();
        lblInfoMatkul2 = new javax.swing.JLabel();
        lblInfoMatkul = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setLayout(new java.awt.GridBagLayout());

        lblInfoMatkul1.setFont(new java.awt.Font("Leelawadee", 1, 36)); // NOI18N
        lblInfoMatkul1.setText("Kelola Jadwal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(50, 50, 0, 0);
        add(lblInfoMatkul1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Hari");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Waktu");

        txtJamMulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJamMulaiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ruangan");

        jButton1.setBackground(new java.awt.Color(13, 71, 161));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tambah Jadwal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBoxHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu" }));
        jComboBoxHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxHariActionPerformed(evt);
            }
        });

        jLabel6.setText("s.d.");

        lblInfoMatkul2.setFont(new java.awt.Font("Leelawadee", 1, 18)); // NOI18N
        lblInfoMatkul2.setText("Perbarui Jadwal");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxHari, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtJamMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtJamSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuangan)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblInfoMatkul2)))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(lblInfoMatkul2)
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jComboBoxHari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJamMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtJamSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(52, 52, 52))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 12, 113, 43);
        add(jPanel2, gridBagConstraints);

        lblInfoMatkul.setFont(new java.awt.Font("Leelawadee", 1, 18)); // NOI18N
        lblInfoMatkul.setText("Mata Kuliah bla bla");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 50, 0, 0);
        add(lblInfoMatkul, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setShowGrid(true);
        jScrollPane2.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 436;
        gridBagConstraints.ipady = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(21, 50, 113, 0);
        add(jScrollPane2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      if (kodeMkAmpuan == null) return; // Cegah error
        
        // Ambil Data dari Inputan
        String hari = jComboBoxHari.getSelectedItem().toString();
        String jamMulai = txtJamMulai.getText();
        String jamSelesai = txtJamSelesai.getText();
        String ruangan = txtRuangan.getText();
        
        // Validasi Validasi Jam (Method validasi waktu kamu yg lama pake aja)
        String validMulai = validasiWaktu(jamMulai);
        String validSelesai = validasiWaktu(jamSelesai);
        
        if (validMulai == null || validSelesai == null) {
            JOptionPane.showMessageDialog(this, "Format Jam Salah! Gunakan HH:mm (Contoh 08:00)");
            return;
        }
        
        // Bikin Object Jadwal (Pakai kodeMkAmpuan yg otomatis tadi)
        jadwal j = new jadwal(kodeMkAmpuan, hari, validMulai, validSelesai, ruangan);
        
        // Panggil DAO Update (Logika Update/Insert di DAO kamu sblmnya udah bagus)
        if (dao.updateJadwal(j)) {
            JOptionPane.showMessageDialog(this, "Jadwal Berhasil Disimpan!");
            loadJadwalForm();
            // Refresh tabel kalau ada tabel rekap
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan jadwal.");
        }
    
   
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void jComboBoxHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxHariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxHariActionPerformed

    private void txtJamMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJamMulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJamMulaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxHari;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblInfoMatkul;
    private javax.swing.JLabel lblInfoMatkul1;
    private javax.swing.JLabel lblInfoMatkul2;
    private javax.swing.JTextField txtJamMulai;
    private javax.swing.JTextField txtJamSelesai;
    private javax.swing.JTextField txtRuangan;
    // End of variables declaration//GEN-END:variables



}
