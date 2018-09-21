/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import Java.Currency_Column;
import Java.ListSetBarang;
import Java.modelTabelSetBarang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Master_SetHargaBarang_SetHarga extends javax.swing.JFrame {
    
    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListSetBarang> list;
    private ListSetBarang listbarang;
    private TableModel model;
    private PreparedStatement PS;
    String sql, kodebrg;
    ResultSet rs;
    private int kode_barang;
//    Master_SetHargaBarang shb = new Master_SetHargaBarang();

    /**
     * Creates new form NewJFrame
     */
    public Master_SetHargaBarang_SetHarga() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public Master_SetHargaBarang_SetHarga(java.awt.Frame parent, boolean modal) {
//        super(parent, modal);
        initComponents();
        this.connection = new Connect();
//        tampilTabel("*");
    }
    
//    private void tampilTabel(String param) {
////        System.out.println("yesss");
//        String data = "";
//        try {
//            data = "SELECT kode_barang, nama_barang, harga_jual_1_barang, harga_jual_2_barang, harga_jual_3_barang "
//                    + "FROM barang " + (param.equals("*") ? "" : "where nama_barang like '%" + param + "%'");
//            hasil = connection.ambilData(data);
////            System.out.println("sukses query tampil tabel");
//            setModel(hasil);
//
//        } catch (Exception e) {
//            System.out.println("ERROR -> " + e.getMessage());
//        } finally {
////            System.out.println(data);
//        }
//    }
    
//    private void setModel(ResultSet hasil) {
//            Master_SetHargaBarang shb = new Master_SetHargaBarang();
//        try {
//            list = new ArrayList<>();
//            int a = 0;
//            while (hasil.next()) {
//                a++;
//                this.listbarang = new ListSetBarang();
//                this.listbarang.setNomor(a);
//                this.listbarang.setKode_barang(hasil.getInt("kode_barang"));
//                this.listbarang.setNama_barang(hasil.getString("nama_barang"));
//                this.listbarang.setHarga_jual_1_barang(hasil.getInt("harga_jual_1_barang"));
//                this.listbarang.setHarga_jual_2_barang(hasil.getInt("harga_jual_2_barang"));
//                this.listbarang.setHarga_jual_3_barang(hasil.getInt("harga_jual_3_barang"));
//                list.add(listbarang);
//                listbarang = null;
//            }
//            model = new modelTabelSetBarang(list);
//            shb.tbl_setHargaBarang.setModel(model);
//            TableColumnModel m = shb.tbl_setHargaBarang.getColumnModel();
//            m.getColumn(3).setCellRenderer(new Currency_Column());
//            m.getColumn(4).setCellRenderer(new Currency_Column());
//            m.getColumn(5).setCellRenderer(new Currency_Column());
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel72 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        harga3 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        harga1 = new javax.swing.JTextField();
        harga2 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        btsimpan = new javax.swing.JButton();
        btclose = new javax.swing.JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel72.setText("Rincian Barang");

        harga3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harga3ActionPerformed(evt);
            }
        });

        jLabel74.setText("Harga Jual 2");

        jLabel73.setText("Harga Jual 1");

        jLabel75.setText("Harga Jual 3");

        btsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        btsimpan.setText("Simpan");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        btclose.setText("Close");
        btclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(harga2)
                            .addComponent(harga1)
                            .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(3, 3, 3))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btclose))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel72)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel72)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btsimpan)
                    .addComponent(btclose))
                .addGap(20, 20, 20))
        );

        setSize(new java.awt.Dimension(416, 263));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void harga3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harga3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga3ActionPerformed

    private void btcloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btcloseActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        try {
            String sql = "update barang set harga_jual_1_barang=?,harga_jual_2_barang=?,harga_jual_3_barang=? where kode_barang=? ";
            PreparedStatement p = (PreparedStatement) connection.Connect().prepareStatement(sql);
            p.setString(1, harga1.getText().toString());
            p.setString(2, harga2.getText().toString());
            p.setString(3, harga3.getText().toString());
            p.setString(4, kodebrg);
//                p.setInt(2, id);
            p.executeUpdate();
//            tampilTabel("*");
            System.out.print(p);
            JOptionPane.showMessageDialog(null, "Data sukses di edit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btsimpanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang_SetHarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang_SetHarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang_SetHarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_SetHargaBarang_SetHarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Master_SetHargaBarang_SetHarga frame = new Master_SetHargaBarang_SetHarga(new javax.swing.JFrame(), true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btclose;
    private javax.swing.JButton btsimpan;
    public javax.swing.JTextField harga1;
    public javax.swing.JTextField harga2;
    public javax.swing.JTextField harga3;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
