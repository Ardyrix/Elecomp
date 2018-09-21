/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Java.Connect;
import Java.Currency_Column;
import Java.ListBarang;
import Java.modelTabelBarang;
import com.placeholder.PlaceHolder;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class Master_SetHargaBarang extends javax.swing.JDialog {

    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListBarang> list;
    private ListBarang listbarang;
    private TableModel model;
    private PreparedStatement PS;
    String sql, kodebrg;
    ResultSet rs;
    private int kode_barang;

    public Master_SetHargaBarang() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Master_SetHargaBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.connection = new Connect();
        tampilTabel("*");
    }


    public void holder() {
        PlaceHolder holder;
        holder = new PlaceHolder(harga1, "masukan harga 1");
        holder = new PlaceHolder(harga2, "masukan harga 2");
        holder = new PlaceHolder(harga3, "masukan harga 3");
    }

    public void cari() {
        try {
            String sql = "select * from barang where nama_barang like '%" + cari.getText() + "%'";
            hasil = connection.ambilData(sql);
        } catch (Exception e) {
            System.out.println("gagal query ini" + e);
        }
    }

    private void tampilTabel(String param) {
        String data = "";
        try {
            data = "SELECT kode_barang, nama_barang, harga_jual_1_barang, harga_jual_2_barang, harga_jual_3_barang "
                    + "FROM barang " + (param.equals("*") ? "" : "where nama_barang like '%" + param + "%'");
            hasil = connection.ambilData(data);
            //System.out.println("sukses query tampil tabel");
            setModel(hasil);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR -> " + e.getMessage());
        } finally {
//            System.out.println(data);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            list = new ArrayList<>();
            int a = 0;
            while (hasil.next()) {
                a++;
                this.listbarang = new ListBarang();
                this.listbarang.setNo(a);
                this.listbarang.setKode_barang(hasil.getInt("kode_barang"));
                this.listbarang.setNama_barang(hasil.getString("nama_barang"));
                this.listbarang.setHarga_jual_1(hasil.getInt("harga_jual_1_barang"));
                this.listbarang.setHarga_jual_2(hasil.getInt("harga_jual_2_barang"));
                this.listbarang.setHarga_jual_3(hasil.getInt("harga_jual_3_barang"));
                list.add(listbarang);
                listbarang = null;
            }
            model = new modelTabelBarang(list);
            jTable10.setModel(model);
            TableColumnModel m = jTable10.getColumnModel();
            m.getColumn(3).setCellRenderer(new Currency_Column());
            m.getColumn(4).setCellRenderer(new Currency_Column());
            m.getColumn(5).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
//    public void autoSum() {
//        int totalHutang = 0;
//        int jumlahBaris = tbl_pembelianHutang.getRowCount();
//        boolean action;
//        int hargaItem = 0;
//        int totalPotongan = 0;
//        int jumFaktur = 0;
//        noFaktur = new String[jumlahBaris];
//        hrgItem = new int[jumlahBaris];
//
//        TableModel tabelModel;
//        tabelModel = tbl_pembelianHutang.getModel();
//        for (int i = 0; i < jumlahBaris; i++) {
//            action = (boolean) tabelModel.getValueAt(i, 0);
//            if (action == true) {
//                hargaItem = Integer.valueOf(tabelModel.getValueAt(i, 6).toString());
//                hrgItem[jumFaktur] = hargaItem;
//                totalPotongan = Integer.valueOf(tabelModel.getValueAt(i, 7).toString());
//                noFaktur[jumFaktur] = String.valueOf(tabelModel.getValueAt(i, 2));
//                jumFaktur++;
//            } else {
//                hargaItem = 0;
//                totalPotongan = 0;
//            }
//            totalHutang += hargaItem;
//            potongan += totalPotongan;
//        }
//        txt_total.setText("" + totalHutang);
//
//        for (int i = 0; i < jumFaktur; i++) {
//            System.out.println("noFaktur " + i + " " + noFaktur[i] + " Harga = " + hrgItem[i]);
//        }
//        System.out.println("=================================");
//        this.jumFaktur = jumFaktur;
//        this.totalHutang = totalHutang;
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        harga1 = new javax.swing.JTextField();
        harga2 = new javax.swing.JTextField();
        harga3 = new javax.swing.JTextField();
        btnSetHarga = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        cari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Set Harga Barang");

        harga1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga1MouseClicked(evt);
            }
        });
        harga1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                harga1KeyTyped(evt);
            }
        });

        harga2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga2MouseClicked(evt);
            }
        });
        harga2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga2KeyPressed(evt);
            }
        });

        harga3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                harga3MouseClicked(evt);
            }
        });
        harga3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                harga3KeyPressed(evt);
            }
        });

        btnSetHarga.setText("Set Harga");
        btnSetHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetHargaActionPerformed(evt);
            }
        });

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "", "No.", "Kode Barang", "Nama", "Harga Jual 1", "Harga Jual 2", "Harga Jual 3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jTable10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable10KeyReleased(evt);
            }
        });
        jScrollPane14.setViewportView(jTable10);

        cari.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });

        jLabel2.setText("Kriteria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(harga1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(harga2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSetHarga)
                .addGap(225, 225, 225))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetHarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(979, 510));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void harga1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga1MouseClicked

    private void harga1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga1KeyPressed

    private void harga2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga2MouseClicked

    private void harga2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga2KeyPressed

    private void harga3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_harga3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_harga3MouseClicked

    private void harga3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga3KeyPressed

    private void btnSetHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetHargaActionPerformed
        try {
            String sql = "update barang set harga_jual_1_barang=?,harga_jual_2_barang=?,harga_jual_3_barang=? where kode_barang=? ";
            PreparedStatement p = (PreparedStatement) connection.Connect().prepareStatement(sql);
            p.setString(1, harga1.getText().toString());
            p.setString(2, harga2.getText().toString());
            p.setString(3, harga3.getText().toString());
            p.setString(4, kodebrg);
//                p.setInt(2, id);
            p.executeUpdate();
            tampilTabel("*");
            System.out.print(p);
            JOptionPane.showMessageDialog(null, "Data sukses di edit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSetHargaActionPerformed

    private void harga1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_harga1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_harga1KeyTyped

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
        // TODO add your handling code here:
        try {
            int row = jTable10.getSelectedRow();
            String table_click = (jTable10.getModel().getValueAt(row, 1).toString());
            String sql = "select * from barang where kode_barang='" + table_click + "' ";
            hasil = connection.ambilData(sql);
//            rs = PS.executeQuery();
            System.out.println("sukses query tampil tabel");
            while (hasil.next()) {

                String add1 = hasil.getString("harga_jual_1_barang");
                harga1.setText(add1);
                String add2 = hasil.getString("harga_jual_2_barang");
                harga2.setText(add2);
                String add3 = hasil.getString("harga_jual_3_barang");
                harga3.setText(add3);
                kodebrg = hasil.getString("kode_barang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable10MouseClicked

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        // TODO add your handling code here:
        tampilTabel(cari.getText().toString());
    }//GEN-LAST:event_cariKeyReleased

    private void jTable10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable10KeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
                try {
                    int row = jTable10.getSelectedRow();
                    String table_click = (jTable10.getModel().getValueAt(row, 1).toString());
                    String sql = "select * from barang where kode_barang='" + table_click + "' ";
                    hasil = connection.ambilData(sql);
//            rs = PS.executeQuery();
                    System.out.println("sukses query tampil tabel");
                    while (hasil.next()) {

                        String add1 = hasil.getString("harga_jual_1_barang");
                        harga1.setText(add1);
                        String add2 = hasil.getString("harga_jual_2_barang");
                        harga2.setText(add2);
                        String add3 = hasil.getString("harga_jual_3_barang");
                        harga3.setText(add3);
                        kodebrg = hasil.getString("kode_barang");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jTable10KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Master_SetHargaBarang dialog = new Master_SetHargaBarang(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSetHarga;
    private javax.swing.JTextField cari;
    private javax.swing.JTextField harga1;
    private javax.swing.JTextField harga2;
    private javax.swing.JTextField harga3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable10;
    // End of variables declaration//GEN-END:variables
}
