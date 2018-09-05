/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Clock;
import Java.Connect;
import Java.ListPegawai;
import Java.ListPiutang;
import Java.modelTabelPegawai;
import Java.modelTabelPiutang;
import static UI.Pembelian_Hutang.dotConverter;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author USER
 */
public class Penjualan_Piutang extends javax.swing.JFrame {

    private Connect connection;
    private ResultSet hasil;
    private ArrayList<ListPiutang> list;
    private ListPiutang listPiutang;
    private TableModel model;
    private PreparedStatement PS;
    private int totalHutang = 0;
    private int jumFaktur = 0;
    private String[] noFaktur;
    private int[] hrgItem;
    private Clock clock;
    private String kode_customer;
    private String nama_customer;

    ArrayList<String> kode_nama_arr = new ArrayList();
    ArrayList<String> kode_nama_arr1 = new ArrayList();
    ArrayList<String> kode_nama_arrk = new ArrayList();
    private static int item = 0, item1 = 0, itemk = 0;
    private boolean tampil = true, tampil1 = true, tampilk = true;
    private boolean ini_baru = false, akhir = true;

    
    
    /**
     * Creates new form Penjualan_piutang
     */
    public Penjualan_Piutang() {
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
    private String currency_convert(int nilai) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }

    public void tampilTabel(String param) {
        removeRow();
        
        int kode_customer = 0;
        String nama_awal = String.valueOf(comCustomer.getSelectedItem());
        String[] split = new String[2];
        if (comCustomer.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }

//        if (param.equals("*")) {
//            param = "";
//        }
        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        int i = 1;
        try {
            String sql = "SELECT `no_faktur_penjualan`, `tgl_penjualan`, `pembaran_udah_bayar`, `pembayaran_aktif` "
                    + "FROM `penjualan`, customer "
                    + "WHERE penjualan.kode_customer = customer.kode_customer "
                    + "AND `pembaran_udah_bayar` < 0 "
                    //                    + "AND `faktur_bg_penjualan` = '' "
                    + "AND customer.nama_customer like '%" + split[1].trim() + "%'"
                    + "GROUP BY `no_faktur_penjualan` ORDER BY `tgl_penjualan` DESC";
            this.kode_customer = split[0];
            this.nama_customer = split[1];
            System.out.println("sql: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                int total = res.getInt("pembayaran_aktif"), sisautang = res.getInt("pembaran_udah_bayar");
                model.addRow(new Object[]{
                    false,
                    i++,
                    res.getString("no_faktur_penjualan"),
                    dotConverter(res.getString("tgl_penjualan")),
                    currency_convert(total),
                    currency_convert(sisautang),});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        int row = jTable7.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    private void setModel(ResultSet hasil) {
        try {
            int a = 0;
            list = new ArrayList<>();
            while (hasil.next()) {
                a++;
                this.listPiutang = new ListPiutang();
                this.listPiutang.setNomor(a);
                this.listPiutang.setNo_faktur(hasil.getString("no_faktur_penjualan"));
                this.listPiutang.setTanggal(hasil.getString("tgl_validasi_penjualan"));
                this.listPiutang.setBiaya(hasil.getInt("pembayaran_aktif"));
//                int sisa = hasil.getInt("totale") - (-1*hasil.getInt("pembaran_udah_bayar"));
                this.listPiutang.setSisa(hasil.getInt("pembaran_udah_bayar"));
                list.add(listPiutang);
                listPiutang = null;
            }
            model = new modelTabelPiutang(list);
            jTable7.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

     void loadCustomer(String param) {
        if (param.equals("*")) {
            param = "";
        }
        if (param.substring(0, 1).equals(" ")) {
            param = param.substring(1);
        }
        try {
//            String sql = "select * from cutomer where nama_customer like '%"+param+"%' or kode_customer like '%"+param+"%'";
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
//      private void comCustomerItemStateChanged(java.awt.event.ItemEvent evt) {                                             
//        tampil = false;
//    }    
      
      
     void loadCustomer() {

        try {
            String sql = "select * from customer";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(1) + " - " + res.getString(2);
                comCustomer.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void autoSum() {
        int totalHutang = 0;
        int jumlahBaris = jTable7.getRowCount();
        boolean action;
        int hargaItem = 0;
        int jumFaktur = 0;
        noFaktur = new String[jumlahBaris];
        hrgItem = new int[jumlahBaris];

        TableModel tabelModel;
        tabelModel = jTable7.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            action = (boolean) tabelModel.getValueAt(i, 0);
            if (action == true) {
                hargaItem = Integer.valueOf(tabelModel.getValueAt(i, 5).toString());
                hrgItem[jumFaktur] = hargaItem;
                noFaktur[jumFaktur] = String.valueOf(tabelModel.getValueAt(i, 2));
                jumFaktur++;
            } else {
                hargaItem = 0;
            }
            totalHutang += hargaItem;
        }
        txt_total.setText("" + totalHutang);

        this.jumFaktur = jumFaktur;
        this.totalHutang = totalHutang;

    }

    void selectAll() {
        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        if (chk_pilihSemua.isSelected()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(true, i, 0);
            }
        } else {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(false, i, 0);
            }
        }
        autoSum();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel16 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jSeparator29 = new javax.swing.JSeparator();
        chk_pilihSemua = new javax.swing.JCheckBox();
        txtSales = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        comCustomer = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "No", "No. Faktur Penjualan", "Tanggal", "Biaya", "Sisa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(1).setResizable(false);
            jTable7.getColumnModel().getColumn(1).setPreferredWidth(10);
            jTable7.getColumnModel().getColumn(2).setResizable(false);
            jTable7.getColumnModel().getColumn(3).setResizable(false);
            jTable7.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton14.setBackground(new java.awt.Color(71, 166, 227));
        jButton14.setText("Bayar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel52.setText("Customer");

        jLabel87.setText("Nominal");

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        jSeparator29.setForeground(new java.awt.Color(204, 204, 204));

        chk_pilihSemua.setText("Pilih Semua");
        chk_pilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_pilihSemuaActionPerformed(evt);
            }
        });

        txtSales.setBackground(new java.awt.Color(184, 238, 184));
        txtSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSalesMouseClicked(evt);
            }
        });
        txtSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalesActionPerformed(evt);
            }
        });

        jLabel1.setText("Sales");

        comCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Pilih Pustomer---" }));
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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator29)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addGap(18, 18, 18)
                                .addComponent(comCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSales, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chk_pilihSemua))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel87)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel87)
                        .addComponent(jButton14)
                        .addComponent(txtSales, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52)
                        .addComponent(comCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk_pilihSemua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, null));
        jPanel17.setForeground(new java.awt.Color(51, 51, 51));

        jLabel57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel57.setText("Tabel Hutang Penjualan");

        lblTanggal.setBackground(new java.awt.Color(255, 255, 255));
        lblTanggal.setText("tgl");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTanggal)
                .addGap(91, 91, 91))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTanggal))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
//        JOptionPane.showMessageDialog(null, "index: "+);
        int index = comCustomer.getSelectedIndex();
        if (index <= 0) {
            JOptionPane.showMessageDialog(null, "Pilih customer terlebih dahulu");
        } else if (txt_total.getText().equals("") || txt_total.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Centang terlebih dahulu faktur yang akan dibayarkan!");
        } else {
            Penjualan_Piutang_Bayar ppb = new Penjualan_Piutang_Bayar(totalHutang, noFaktur, hrgItem, jumFaktur, comCustomer.getSelectedItem().toString(), txtSales.getText().toString());
            ppb.setVisible(true);
            ppb.setFocusable(true);
//            dispose();
        }
    }                                         

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void txtSalesMouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void txtSalesActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void comCustomerActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int kode_customer = 0;
        TableModel tabelModel;
        String nama_awal = String.valueOf(comCustomer.getSelectedItem());
        String[] split = new String[2];
        System.out.println("Nilai comCustomer adalah " + comCustomer.getSelectedItem());
        if (comCustomer.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select s.nama_salesman, c.nama_customer, c.kode_customer from customer c, salesman s "
                    + "where c.kode_salesman=s.kode_salesman "
                    + "and c.kode_customer = '" + split[0].trim() + "'";
            this.kode_customer = split[0];
            System.out.println(sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("saaaql: "+sql);
            while (res.next()) {
                String name = res.getString(1);
//                comCustomer.setName(split[1]);
                txtSales.setText(name);
            }
//            System.out.println(comCustomer.getSelectedItem().toString());
            DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(false, i, 0);
            }
            autoSum();
            tampilTabel(comCustomer.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }                                           

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {                                     
        autoSum();
    }                                    

    private void chk_pilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {                                               
        selectAll();
    }                                              

    private void comCustomerItemStateChanged(java.awt.event.ItemEvent evt) {                                             
       tampil = false;
    }                                            

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
            java.util.logging.Logger.getLogger(Penjualan_Piutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Piutang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_Piutang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox chk_pilihSemua;
    private javax.swing.JComboBox<String> comCustomer;
    private javax.swing.JButton jButton14;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JTable jTable7;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTextField txtSales;
    private javax.swing.JTextField txt_total;
    // End of variables declaration                   
}
