/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import Java.Currency_Column;
import com.sun.glass.events.KeyEvent;
import java.awt.Frame;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import Class.Koneksi;
import Java.Clock;
import Java.Connect;
import Java.Currency_Column;
import Java.ListPiutangBG;
import Java.modelTabelPiutangBG;
import static UI.Pembelian_Hutang.dotConverter;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author USER
 */
public class Penjualan_PiutangBG extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_piutangBG
     */
    private ResultSet hasil;
    private Clock clock;
    private List<ListPiutangBG> list;
    private TableModel model;
    private ListPiutangBG ListPiutangBG;
    private String nama_customer;
    private String kode_customer; 
    public String totalclone;
    public int Tempharga;
    public int subtotalfix = 0;
    int kode_barang = 1;
    String kov;
    ResultSet rs, rs1, rs2, rsx = null;
    Connect connection, connection1;
    private PreparedStatement PS, PSx;
    public int subtotal1 = 0, hargajadi1 = 0, totalqty = 0, total = 0; //penjumlahan
    public int hargaRekom = 0, harga = 0, Jharga = 0, subtotal = 0, hargajadi = 0;//panggil colom tabel
    public int qty = 0;
    public String tmpLokasi, tmpKonv, tmpKodeBarang, tmpKel;
    public int tmpIdTOP, tmpKodeLokasi, tmpKodeKonv, jumlah_item;
    public double tmpHJ1, tmpHJ2, tmpKonvPcs, tmppcs, jmlKonv, jumlahTambah, jmlQty, jumlah_qty;
    private String nofaktur;
    private DefaultTableModel tabel;
    private String id, namabarang, kode_satuan;
    private int tempPrev = 0, jmlKolom = 0, kode_lokasi;
    private double tempJ, tempJQ, stok, jumlah = 0;
    private int tempL, tempK;
    private double tempAJ[], tempAL[];
    private ResultSet tempRs;
    ArrayList<String> kode_nama_arr = new ArrayList();
    ArrayList<String> kode_nama_arr1 = new ArrayList();
    private static int item = 0, item1 = 0, itemk = 0;
    private boolean tampil = true, tampil1 = true, tampilk = true;
    private boolean ini_baru = false, akhir = true;

    
    public Penjualan_PiutangBG() {
        initComponents();
        this.setLocationRelativeTo(null);

        clock = new Clock(lblTanggal, 3);
        loadCustomer();
        tampilTabel("*");
        
        ((JTextComponent) comCustomer.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item1 == 0) {
                    loadCustomer(((JTextComponent) comCustomer.getEditor().getEditorComponent()).getText());
                } else {
                    item1 = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil1) {
                            //tbl_Pembelian.editCellAt(tbl_Pembelian.getSelectedRow(), 2);
                            comCustomer.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comCustomer.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comCustomer.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comCustomer.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil1 = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil1 = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil1 = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    public void tampilTabel(String param) {
//        removeRow();
//        if (param.equals("*")) {
//            param = "";
//        }
//        DefaultTableModel model = (DefaultTableModel) TabelPiutangBG.getModel();

        int kode_customer = 0;
        String nama_awal = String.valueOf(comCustomer.getSelectedItem());
        String[] split = new String[2];
        if (comCustomer.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }

        int i = 1;
        try {
            String sql = "SELECT *, SUM(pembaran_udah_bayar) as biaya "
                    + "FROM `penjualan`, customer, salesman "
                    + "WHERE penjualan.kode_customer = customer.kode_customer "
                    + "AND penjualan.kode_salesman = salesman.kode_salesman "
                    + "AND `pembaran_udah_bayar` < 0 "
                    + "AND `faktur_bg_penjualan` != '' "
                    + "AND customer.nama_customer like '%" + split[1].trim() + "%'"
//                    + "AND customer.nama_customer like '%" + param + "%' OR customer.kode_customer like '%" + param + "%'"
                    + "GROUP BY `faktur_bg_penjualan` ORDER BY `faktur_bg_penjualan` DESC";
            this.kode_customer = split[0];
            this.nama_customer = split[1];
            System.out.println("sqlpiutangBG: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            setModel(res);
            TableColumnModel m = TabelPiutangBG.getColumnModel();
            m.getColumn(7).setCellRenderer(new Currency_Column());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    public void removeRow() {
        DefaultTableModel model1 = (DefaultTableModel) TabelPiutangBG.getModel();
        int row = TabelPiutangBG.getRowCount();
        for (int i = 0; i < row; i++) {
            model1.removeRow(0);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            int a = 0;
            list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            while (hasil.next()) {
                a++;
                this.ListPiutangBG = new ListPiutangBG();
                this.ListPiutangBG.setNomor(a);
                this.ListPiutangBG.setNo_fakturBG(hasil.getString("faktur_bg_penjualan"));
                this.ListPiutangBG.setTanggal(sdf.format(hasil.getDate("tgl_validasi_penjualan")));
                this.ListPiutangBG.setKode_customer(hasil.getInt("kode_customer"));
                this.ListPiutangBG.setNamaCustomer(hasil.getString("nama_customer"));
                this.ListPiutangBG.setKotaCustomer(hasil.getString("kota_customer"));
                this.ListPiutangBG.setKode_salesman(hasil.getInt("kode_salesman"));
                this.ListPiutangBG.setNamaSalesman(hasil.getString("nama_salesman"));
                this.ListPiutangBG.setKotaSalesman(hasil.getString("kota_salesman"));
                this.ListPiutangBG.setBiaya(hasil.getInt("biaya"));
//                int sisa = hasil.getInt("totale") - (-1*hasil.getInt("pembaran_udah_bayar"));
//                this.ListPiutangBG.setSisa(hasil.getInt("pembaran_udah_bayar"));
                list.add(ListPiutangBG);
                ListPiutangBG = null;
            }
            model = new modelTabelPiutangBG(list);
            TabelPiutangBG.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    void loadCustomer(String param) {
        if (param.equals("*")) {
            param = "";
        }
        if (param.substring(0,1).equals(" ")) {
            param = param.substring(1);
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_customer,\" - \",nama_customer) as gabung from customer "
                    + "where kode_customer ='" + param + "' OR nama_customer like '%" + param + "%'";
//            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("ini sql com kode nama " + sql);
            kode_nama_arr.clear();
            kode_nama_arr.add("");
            while (res.next()) {
                String gabung = res.getString("gabung");
                kode_nama_arr.add(gabung);
                item++;
            }
            if (item == 0) {
                item = 1;
            }
//            System.out.println("kdenamarr: " + kode_nama_arr);
            comCustomer.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
            //((JTextComponent) comSupplier.getEditor().getEditorComponent()).setText(param);
            conn.close();
            res.close();
//            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Eror -- 190" + e);
        }

    }

    void loadCustomer() {

        try {
            String sql = "select * from customer";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                //String kode = res.getString(1);
                //String name = res.getString(2);
                String name = res.getString(1) + " - " + res.getString(2);
                comCustomer.addItem(name);                
                //comCustomer.addItem(kode+" - "+name);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel20 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TabelPiutangBG = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        comCustomer = new javax.swing.JComboBox();
        jPanel21 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        TabelPiutangBG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "No. Faktur BG", "Tanggal", "Customer", "Kota", "Salesman", "Kota", "Biaya"
            }
        ));
        TabelPiutangBG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPiutangBGMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TabelPiutangBG);

        jLabel52.setText("Customer");

        comCustomer.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comCustomer.setBackground(new java.awt.Color(255, 255, 204));
        comCustomer.setEditable(true);
        comCustomer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Pilih Customer --" }));
        comCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comCustomerItemStateChanged(evt);
            }
        });
        comCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(comCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, null));
        jPanel21.setForeground(new java.awt.Color(51, 51, 51));

        jLabel93.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel93.setText("Tabel Piutang Penjualan BG");

        lblTanggal.setBackground(new java.awt.Color(255, 255, 255));
        lblTanggal.setText("tgl");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTanggal)
                .addGap(154, 154, 154))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(lblTanggal)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TabelPiutangBGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPiutangBGMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            int row = TabelPiutangBG.getSelectedRow();
            String table_click = (TabelPiutangBG.getModel().getValueAt(row, 1).toString());
            Penjualan_PiutangBG_Faktur f = new Penjualan_PiutangBG_Faktur(table_click);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_TabelPiutangBGMouseClicked

    private void comCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comCustomerActionPerformed
        try {
            tampilTabel(comCustomer.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comCustomerActionPerformed

    private void comCustomerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comCustomerItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comCustomerItemStateChanged

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
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_PiutangBG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_PiutangBG().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelPiutangBG;
    private javax.swing.JComboBox comCustomer;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblTanggal;
    // End of variables declaration//GEN-END:variables
}
