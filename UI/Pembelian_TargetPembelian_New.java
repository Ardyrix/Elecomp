/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import Java.Connect;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author 7
 */
public class Pembelian_TargetPembelian_New extends javax.swing.JDialog {

    /**
     * Creates new form Pembelian_TargetPembelian_New
     */
    private Frame parent;

    ArrayList<String> kode_nama_arr = new ArrayList();
    ArrayList<String> kode_nama_b = new ArrayList();
    private static int item = 0, itemb = 0;
    private String kodeS = "", kodeB = "", kodeK = "";
    private boolean tampil = true, tampilb = true, insert = true;
    private ResultSet hasil;
    private PreparedStatement PS;
    private Connect connection;

    public Pembelian_TargetPembelian_New() {
        initComponents();
    }

    public Pembelian_TargetPembelian_New(java.awt.Frame parent, boolean modal, Connect connection, String kode) {
        super(parent, modal);
        initComponents();
        this.connection = connection;
        this.setLocationRelativeTo(null);
        this.parent = parent;
        if (kode.equals("*")) {
            txtTarget.setText(id_oto());
        } else {
            this.insert = false;
            loadEdit(kode);
        }
//        AutoCompleteDecorator.decorate(comSupplier);
//        AutoCompleteDecorator.decorate(comBarang);
//        AutoCompleteDecorator.decorate(comSatuan);
//        System.out.println("kode: "+kode);
//        loadSupplier("*");
        comSupplier.requestFocus();
        //---------------------comsupplier--------------
        //JCombobox kode barang
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//                System.out.println("insert");

                if (item == 0) {
                    loadSupplier(((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
//                            jTableTransaksi.editCellAt(jTableTransaksi.getSelectedRow(), 1);
//                            comSupplier.setSelectedItem("");
                            comSupplier.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                System.out.println("remove");
//                System.out.println(((JTextComponent) comCode.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comSupplier.getEditor().getEditorComponent()).getText();
//                System.out.println(key);
                ((JTextComponent) comSupplier.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
            }
        });
        ((JTextComponent) comSupplier.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER) {
                    tampil = false;
//                    loadcomsupplier();
//                    comBarang.requestFocus(true);
                } else if (e.getKeyCode() == com.sun.glass.events.KeyEvent.VK_DOWN) {
                    tampil = true;
//                    item = 1;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
        //---------------------combarang--------------
        //JCombobox kode barang
        ((JTextComponent) comBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//                System.out.println("insert");

                if (itemb == 0) {
                    loadBarang(((JTextComponent) comBarang.getEditor().getEditorComponent()).getText());
                } else {
                    itemb = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampilb) {
//                            jTableTransaksi.editCellAt(jTableTransaksi.getSelectedRow(), 1);
//                            comSupplier.setSelectedItem("");
                            comBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                System.out.println("remove");
//                System.out.println(((JTextComponent) comCode.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comBarang.getEditor().getEditorComponent()).getText();
//                System.out.println(key);
                ((JTextComponent) comBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
            }
        });
        ((JTextComponent) comBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampilb = true;
                if (e.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER) {
                    tampilb = false;
//                    loadcomsupplier();
//                    txtSatuan.requestFocus(true);
                } else if (e.getKeyCode() == com.sun.glass.events.KeyEvent.VK_DOWN) {
                    tampilb = true;
//                    itemb = 1;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    void loadcomsupplier() {
        String nama_awal = String.valueOf(comSupplier.getSelectedItem().toString());
        String[] split = comSupplier.getSelectedItem().toString().split(" - ");
        System.out.println("nilai comTable barang adalah " + comSupplier.getSelectedItem());
        if (comSupplier.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
//        comSupplier.removeAllItems();
        comSupplier.addItem(split[1]);
        comSupplier.setSelectedItem(split[1]);
        comBarang.requestFocus(true);
    }
//    void load_dari_nama_barang() {
////        int selectedRow = jTableTransaksi.getSelectedRow();
//        String nama_awal = String.valueOf(comSupplier.getSelectedItem());
//        String[] split = new String[2];
////        System.out.println("nilai comTable barang adalah " + comBarang.getSelectedItem());
//        if (comSupplier.getSelectedItem() != null) {
//            split = nama_awal.split("-");
//        }
//        try {
//            String sql = "select b.kode_barang,nama_barang,harga_jual_3_barang, bl.jumlah from barang b, barang_lokasi bl "
//                    + "where b.kode_barang = '" + split[0] + "' "
//                    + "and bl.kode_barang = b.kode_barang "
//                    + "and bl.kode_lokasi = 4";
////            System.out.println("jumlahhhNama: " + sql);
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString("kode_barang");
//                String nama = res.getString("nama_barang");
//                this.kode_barang = Integer.parseInt(kode);
//                loadNumberTable();
//                loadComSatuanBarang(namabarang);
//                //               loadHargaSatuanBarang();
//                String harga = res.getString("harga_jual_3_barang");
//                //               loadComTableLokasi();
//                //    String konv = comSatuan.getSelectedItem().toString();
//                //  String kode_strip = kode + "-" + nama;
//                if (selectedRow != -1) {
//                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
////                    comTableAsal.getItemAt(0);
////                    jTableTransaksi.setValueAt(comTableAsal.getItemAt(0), selectedRow, 3);
//                    loadComSatuanBarang(jTableTransaksi.getValueAt(selectedRow, 1).toString());
//                    jTableTransaksi.setValueAt(comSatuan.getSelectedItem(), selectedRow, 3);
//                    //jTableTransaksi.setValueAt(comSatuan.getItemAt(0), selectedRow, 3);
//                    jTableTransaksi.setValueAt(kode, selectedRow, 1);
//                    jTableTransaksi.setValueAt(nama, selectedRow, 2);
//                    //  jTableTransaksi.setValueAt(konv, selectedRow, 3);
//                    jTableTransaksi.setValueAt(harga, selectedRow, 5);
//                    jTableTransaksi.setValueAt(harga, selectedRow, 6);
//                    jumlah = res.getInt("jumlah");
////                    jTableTransaksi.setValueAt(res.getString("jumlah"), selectedRow, 8);
//                }
//            }
//            conn.close();
//            res.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//    }

    void loadcombarang() {
        String nama_awal = String.valueOf(comBarang.getSelectedItem().toString());
        String[] split = comBarang.getSelectedItem().toString().split(" - ");
        System.out.println("nilai comTable barang adalah " + comBarang.getSelectedItem());
        if (comBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
//        comBarang.removeAllItems();
        comBarang.addItem("aa");
        comBarang.addItem(split[1]);
        comBarang.setSelectedItem(split[1]);
    }

    public static String id_oto() {
        Calendar call = Calendar.getInstance();
        String str = String.valueOf(call.get(Calendar.YEAR));
        String kode = "TB";
        String tgl_angk = "", th = str.substring(2);
        try {
            String sql = "select kode_barang_target from barang_target "
                    + "WHERE kode_barang_target != '' order by kode_barang_target desc limit 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            String angka = "TB00";
            while (res.next()) {
                angka = res.getString(1);
            }
            if (angka.substring(2, 4).equalsIgnoreCase(th)) {
                int angkasub = Integer.parseInt(angka.substring(5)) + 1;
                String angkapad = rightPadZeros(String.valueOf(angkasub), 5);
                tgl_angk = th + "-" + angkapad;
            } else {
                tgl_angk = th + "-00001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hasil = kode + tgl_angk;
        return hasil;
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%05d", Integer.parseInt(str));
    }

    public void loadEdit(String kode) {
        System.out.println("edit: " + kode);
    }

    void loadSupplier(String param) {
        if (param.equals("*")) {
            param = "";
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_supplier,\" - \",nama_supplier) as gabung from supplier "
                    + "where kode_supplier like '%" + param + "%' OR nama_supplier like '%" + param + "%'";
//            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("ini sql com kode nama " + sql);
            kode_nama_arr.clear();
//            kode_nama_arr.add("");
            while (res.next()) {
                String gabung = res.getString("gabung");
                kode_nama_arr.add(gabung);
                item++;
            }
            if (item == 0) {
                item = 1;
            }
//            System.out.println("kdenamarr: " + kode_nama_arr);
            comSupplier.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
            ((JTextComponent) comSupplier.getEditor().getEditorComponent()).setText(param);
            conn.close();
            res.close();
//            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Eror -- 190" + e);
        }

    }

    void loadBarang(String param) {
        if (param.equals("*")) {
            param = "";
        }
        try {
//            String sql = "select * from supplier where nama_supplier like '%"+param+"%' or kode_supplier like '%"+param+"%'";
            String sql = "select concat(kode_barang,\" - \",nama_barang) as gabung from barang "
                    + "where kode_barang like '%" + param + "%' OR nama_barang like '%" + param + "%'";
//            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//            System.out.println("ini sql com kode nama baranngggggg " + sql);
            kode_nama_b.clear();
            kode_nama_b.add("");
            while (res.next()) {
                String gabung = res.getString("gabung");
                kode_nama_b.add(gabung);
                itemb++;
            }
            if (itemb == 0) {
                itemb = 1;
            }
//            System.out.println("kdenamarr: " + kode_nama_b);
            comBarang.setModel(new DefaultComboBoxModel(kode_nama_b.toArray()));
            ((JTextComponent) comBarang.getEditor().getEditorComponent()).setText(param);
            conn.close();
            res.close();
//            }
        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Eror -- 190" + e);
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

        jLabel19 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txtSatuan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtTarget = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        comSupplier = new javax.swing.JComboBox<>();
        comBarang = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel19.setText("Esc - Exit");

        jLabel2.setText("Nama Barang");

        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanKeyTyped(evt);
            }
        });

        jLabel1.setText("Kode Target");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel18.setText("F12-Save");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
        });

        txtTarget.setEditable(false);

        jLabel5.setText("Jumlah Target");

        jLabel3.setText("Supplier");

        jLabel7.setText("Keterangan");

        jLabel6.setText("Total Sekarang");

        jLabel4.setText("Satuan");

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel8.setText("F9-Clear");

        comSupplier.setEditable(true);
        comSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comSupplier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comSupplierItemStateChanged(evt);
            }
        });
        comSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comSupplierActionPerformed(evt);
            }
        });

        comBarang.setEditable(true);
        comBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comBarangItemStateChanged(evt);
            }
        });
        comBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(47, 47, 47)
                                .addComponent(txtKet, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel2))
                                        .addGap(39, 39, 39))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(31, 31, 31)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTotal)
                                    .addComponent(txtTarget)
                                    .addComponent(txtSatuan)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(comSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(jLabel18))
                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        char vChar = evt.getKeyChar();
        if (!(Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
        if (vChar == KeyEvent.VK_ENTER) {
            int jml = Integer.valueOf(txtJumlah.getText());
            if (jml > 0) {
                txtTotal.requestFocus(true);
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kurang dari nol");
            }
        }
    }//GEN-LAST:event_txtJumlahKeyTyped

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        char vChar = evt.getKeyChar();
        if (!(Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
        if (vChar == KeyEvent.VK_ENTER) {
            int jml = Integer.valueOf(txtTotal.getText());
            if (jml >= 0) {
                txtKet.requestFocus(true);
            } else {
                JOptionPane.showMessageDialog(null, "Total tidak boleh kurang dari nol");
            }
        }
    }//GEN-LAST:event_txtTotalKeyTyped

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        System.out.println("Masukin adata");
        try {
            if (insert) {
                System.out.println("Sebelum input ");
                System.out.println(txtJumlah.getText());
                String data = "INSERT INTO barang_target(kode_barang_target, kode_barang, kode_barang_konversi, kode_supplier, jumlah_target, tgl_target, "
                        + "total_target, ket_target) "
                        + "VALUES ('" + txtTarget.getText() + "', '" + kodeB + "', '" + kodeK + "', '" + kodeS + "', "
                        + "'" + txtJumlah.getText() + "', '" + getCurrentTimeStamp() + "', '" + txtTotal.getText() + "', '" + txtKet.getText() + "' )";
                System.out.println("dataaaaaa: " + data);
                connection = new Connect();
                connection.simpanData(data);
//                System.out.println("dataaaaaa: "+data);
            } else {
                String data = "update barang_target set kode_barang = '" + kodeB + "', kode_barang_konversi = '" + kodeK + "', kode_supplier = '" + kodeS + "', "
                        + "jumlah_target = '" + txtJumlah + "', tglEdit_target = '" + getCurrentTimeStamp() + "', "
                        + "total_target= '" + txtTotal + "', ket_target= '" + txtKet + "' "
                        + "where kode_barang_target = '" + txtTarget + "' ";
                connection.simpanData(data);
            }
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel18MouseClicked

    private void txtSatuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyTyped
        char vChar = evt.getKeyChar();
        evt.consume();
        if (vChar == KeyEvent.VK_1) {
            //            System.out.println("satu");
            setSatuan("1");
        } else if (vChar == KeyEvent.VK_2) {
            //            System.out.println("dua");
            setSatuan("2");
        } else if (vChar == KeyEvent.VK_ENTER) {
            //            System.out.println("enter");
            txtJumlah.requestFocus(true);
        } else {
            //            System.out.println("lain");
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanKeyTyped

    private void comBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBarangActionPerformed
//        tampilb = false;
        loadcombarang();
//        txtSatuan.requestFocus(true);
    }//GEN-LAST:event_comBarangActionPerformed

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
//        tampil = false;
        loadcomsupplier();
//        comBarang.requestFocus(true);
    }//GEN-LAST:event_comSupplierActionPerformed

    private void comSupplierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comSupplierItemStateChanged
tampil = false;
    }//GEN-LAST:event_comSupplierItemStateChanged

    private void comBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comBarangItemStateChanged
tampilb = false;
    }//GEN-LAST:event_comBarangItemStateChanged
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private void setSatuan(String identitas) {
        System.out.println("and bk.kodebarang = " + kodeB);
        try {
            //satuan
            String sqlSatuan = "select k.kode_konversi, nama_konversi, bk.kode_barang_konversi, bk.jumlah_konversi "
                    + "from barang_konversi bk, konversi k "
                    + "where bk.kode_konversi = k.kode_konversi "
                    + "and bk.kode_barang = '" + kodeB + "' "
                    + "and bk.identitas_konversi ='" + identitas + "'";
//            System.out.println("sqlsatuan: " + sqlSatuan);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sqlSatuan);
//            vSatuan.removeAllItems();
            System.out.println(sqlSatuan);
            while (res.next()) {
                String name = res.getString("nama_konversi");
//                System.out.println("name: " + name);
                kodeK = res.getString("kode_barang_konversi");
                System.out.println("Hasil " + name);
                txtSatuan.setText(name);
//                vHarga.setText(String.valueOf(Integer.valueOf(harga) * res.getInt("jumlah_konversi")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("targetbeli new /setSatuan 516" + e);
        }
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
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_New.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_New.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_New.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_TargetPembelian_New.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_TargetPembelian_New().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comBarang;
    private javax.swing.JComboBox<String> comSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtSatuan;
    private javax.swing.JTextField txtTarget;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
