/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JOptionPane;
import Class.Koneksi;
import Java.Connect;
import static UI.Master_Mutasi_PermintaanMutasiAntarGudang.dotConverter;
import com.sun.glass.events.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author USER
 */
public class Master_MutasiBarangAntarGudangBerdasarkanPermintaan extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_penjualan
     */
    private Connect connection;
    int lokasi_asal, lokasi_tujuan, jumlah_Item = 0, jumlah_Qty = 0;
    String no_bukti;
    String lokasi[] = {"PUSAT", "GUD63", "", "TOKO", "TENGAH", "UTARA"};

    ArrayList<String> kode_nama_arr = new ArrayList();
    private static int item = 0;
    private boolean tampil = true;

    public Master_MutasiBarangAntarGudangBerdasarkanPermintaan() {
        initComponents();
        this.setLocationRelativeTo(null);
        lebarKolom();
        
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Permintaan_MutasiAG_detail.editCellAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1);
                            comTableKode.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
        //kode JCombobox sampai sini

        //JCombobox Nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboNama(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Permintaan_MutasiAG_detail.editCellAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 2);
                            comTableBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    public Master_MutasiBarangAntarGudangBerdasarkanPermintaan(String noBukti) {
        initComponents();
        this.setVisible(true);
        loadTable(noBukti);
        loadNumberTable();
        //AutoCompleteDecorator.decorate(comTableBarang);
        //AutoCompleteDecorator.decorate(comTableKode);
        lebarKolom();
        //loadComTableBarang();
        //loadComTableKode();

        //JCombobox kode barang
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboKode(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Permintaan_MutasiAG_detail.editCellAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1);
                            comTableKode.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableKode.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comKodeBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableKode.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
        //kode JCombobox sampai sini

        //JCombobox Nama barang
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");

                if (item == 0) {
                    loadComboNama(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                } else {
                    item = 0;
                }
                Runnable doHighlight = new Runnable() {
                    @Override
                    public void run() {
                        if (tampil) {
                            tbl_Permintaan_MutasiAG_detail.editCellAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 2);
                            comTableBarang.setPopupVisible(true);
                        }

                    }
                };
                SwingUtilities.invokeLater(doHighlight);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                System.out.println(((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText());
                String key = ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).getText();
                System.out.println(key);
                //((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");

            }

        });
        ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                tampil = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tampil = true;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    public void lebarKolom() {
        TableColumn column;
        tbl_Permintaan_MutasiAG_detail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(0);
        column.setPreferredWidth(40);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(1);
        column.setPreferredWidth(180);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(2);
        column.setPreferredWidth(190);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(3);
        column.setPreferredWidth(80);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(4);
        column.setPreferredWidth(80);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(5);
        column.setPreferredWidth(80);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(6);
        column.setPreferredWidth(70);
        column = tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(7);
        column.setPreferredWidth(80);
    }

    public void loadTable(String noBukti) {
        removeRow();
        System.out.println(noBukti);
        no_bukti = noBukti;
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
        int i = 1;
        try {
            String no_bukti = noBukti;

            String sql = "select * FROM mutasi_antar_gudang_detail gd "
                    + "JOIN mutasi_antar_gudang g "
                    + "ON gd.no_bukti = g.no_bukti "
                    + "JOIN konversi k "
                    + "ON k.kode_konversi = gd.satuan "
                    + "WHERE g.no_bukti = '" + no_bukti + "' ORDER BY nama_barang ASC";
            System.out.println("sqlll: " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();

            /*
             String satuan ;
             String sql2 = "select k.nama_konversi FROM konversi k, mutasi_antar_gudang_detail gd "
             + "WHERE k.kode_konversi =  gd.satuan "
             + "AND gd.no_bukti = '"+no_bukti +"'";
             Statement statsatuan = conn.createStatement();
             java.sql.ResultSet ress = statsatuan.executeQuery(sql2);
             satuan = ress.getString("nama_konversi");
             System.out.println("satuan = "+satuan);
             */
            java.sql.ResultSet res = stat.executeQuery(sql);

            int x = 1;
            while (res.next()) {
                System.out.println(x);
                lokasi_asal = Integer.parseInt(res.getString("kode_lokasi_asal"));
                lokasi_tujuan = Integer.parseInt(res.getString("kode_lokasi_tujuan"));
                model.addRow(new Object[]{
                    x++,
                    res.getString("kode_barang"),
                    res.getString("nama_barang"),
                    lokasi[lokasi_asal - 1],
                    res.getString("nama_konversi"),
                    res.getString("qty"),
                    res.getString("dikirim"),
                    lokasi[lokasi_tujuan - 1]
                });
                lokasi_asal = Integer.parseInt(res.getString("kode_lokasi_asal"));
                txt_noBukti.setText(no_bukti);
                txt_tgl.setText(res.getString("tanggal"));
                txt_noPermintaan.setText(res.getString("no_permintaan"));
                txt_JumItem.setText("Jumlah Item : " + res.getString("jumlah_item"));
                txt_JumQty.setText("Jumlah Qty : " + res.getString("jumlah_qty"));
                txt_Keterangan.setText(res.getString("keterangan"));
                comLokasiAsal.setSelectedItem(lokasi[lokasi_asal - 1]);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror nih= " + e);
        }

    }

    void loadNumberTable() {
        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Permintaan_MutasiAG_detail.setValueAt(nomor + ".", a, 0);
        }

    }

    public void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
        int row = tbl_Permintaan_MutasiAG_detail.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    void loadComTableBarang() {
        try {
            String sql = "select b.kode_barang, b.nama_barang from barang b, barang_lokasi bl "
                    + "where b.kode_barang = bl.kode_barang "
                    + "and bl.kode_lokasi = '" + lokasi_asal
                    + "' order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String name = res.getString("nama_barang");
                comTableBarang.addItem(kode + " -- " + name);
            }
            System.out.println("barang lokasi " + lokasi_asal);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    void loadComTableKode() {
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String name = res.getString("nama_barang");
                comTableKode.addItem(kode + " -- " + name);
            }
            System.out.println("kode barang" + lokasi_asal);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }

    /*
     void loadComTableSatuan() {
     try {
     String sql = "select * from konversi k, barang_konversi bk, barang b "
     + "where b.kode_barang = bk.kode_barang "
     + "and bk.kode_konversi = k.kode_konversi "
     + "and b.nama_barang = '" + comTableBarang.getSelectedItem() + "' order by bk.kode_barang_konversi asc";
     System.out.println("sts: " + sql);
     java.sql.Connection conn = (Connection) Koneksi.configDB();
     java.sql.Statement stm = conn.createStatement();
     java.sql.ResultSet res = stm.executeQuery(sql);
     comTableKonv.removeAllItems();
     while (res.next()) {
     String name = res.getString("nama_konversi");
     if (res.getString("identitas_konversi").toString().equalsIgnoreCase("1")) {
     System.out.println("ya: " + name);
     comTableKonv.setSelectedItem(name);
     }
     comTableKonv.addItem(name);
     }
     } catch (Exception e) {
     JOptionPane.showMessageDialog(null, "Eror" + e);
     e.printStackTrace();
     }
     }
     */
    private void removeSelectedRows(JTable table) {
        int Hapus = 1;
        Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus baris ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) this.tbl_Permintaan_MutasiAG_detail.getModel();
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }

    void loadTxtTableQty() {
        try {
            String sql = "select sum(jumlah) from barang_lokasi bl "
                    + "where bl.kode_lokasi =  '" + tbl_Permintaan_MutasiAG_detail.getValueAt(0, 3)
                    + "' and bl.kode_barang = '" + comTableKode.getSelectedItem() + "'";
            System.out.println("sts: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            txt_Qty.setText(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }

    void seleksiLokasi() {
        if (comLokasiAsal.getSelectedItem() == "PUSAT") {
            lokasi_asal = 1;
        } else if (comLokasiAsal.getSelectedItem() == "GUD63") {
            lokasi_asal = 2;
        } else if (comLokasiAsal.getSelectedItem() == "TOKO") {
            lokasi_asal = 4;
        } else if (comLokasiAsal.getSelectedItem() == "TENGAH") {
            lokasi_asal = 5;
        } else if (comLokasiAsal.getSelectedItem() == "UTARA") {
            lokasi_asal = 6;
        }
    }

    public void SaveData() {
        try {
            Koneksi Koneksi = new Koneksi();
            Connection con = Koneksi.configDB();
            Statement st = con.createStatement();
            int row = 0;
            int jumlah_data = tbl_Permintaan_MutasiAG_detail.getRowCount();
            System.out.println(jumlah_data);
            int jumlah_item = 0;
            double jumlah_qty = 0;
            boolean cukup = true;
            String kode_barang = "";
            String kode_konversi = "";
            String status = null;
            //Memasukkan data perbaris ke tabel mutasi detail
            for (int i = 0; i < jumlah_data; i++) {
                //Konversi satuan
                String satuan = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(i, 4));
                String identitas_konversi = satuan.substring(0, 1);
                String sql1 = "select kode_konversi from barang_konversi where kode_barang = '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 1) + "' && identitas_konversi = '" + identitas_konversi + "'";
                Statement st1 = con.createStatement();
                java.sql.ResultSet res1 = st1.executeQuery(sql1);
                while (res1.next()) {
                    kode_konversi = res1.getString(1);
                }
                res1.close();

                //Cek ketersediaan barang di gudang tertentu
                if (i == 0) {
                    for (int j = 0; j < jumlah_data; j++) {
                        //Konversi lokasi cek
                        String sql3a = "select kode_lokasi from lokasi where kode_lokasi = '" + tbl_Permintaan_MutasiAG_detail.getValueAt(0, 3) + "'";
                        Statement st3a = con.createStatement();
                        java.sql.ResultSet res3a = st3a.executeQuery(sql3a);
                        String lokasi_cek = "";
                        while (res3a.next()) {
                            lokasi_cek = res3a.getString(1);
                        }
                        res3a.close();

                        //Konversi satuan cek
                        String satuan_cek = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 4));
                        String identitas_konversi_cek = satuan.substring(0, 1);
                        String sql1a = "select kode_konversi from barang_konversi where kode_barang = '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 1) + "' && identitas_konversi = '" + identitas_konversi + "'";
                        Statement st1a = con.createStatement();
                        java.sql.ResultSet res1a = st1a.executeQuery(sql1a);
                        String kode_konversi_cek = "";
                        while (res1a.next()) {
                            kode_konversi_cek = res1a.getString(1);
                        }
                        res1a.close();

                        double qty = Double.parseDouble(String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 5)));

                        //qty dikali jumlah_konversi
                        double qty_sesudah = qty_konversi(String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 1)), kode_konversi_cek, qty);

                        String sql4 = "select * from barang_lokasi where kode_barang ='" + String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 1)) + "' and kode_lokasi = '" + lokasi_cek + "'";
                        System.out.println(sql4);
                        java.sql.Statement stm4 = con.createStatement();
                        java.sql.ResultSet res4 = stm4.executeQuery(sql4);
                        System.out.println("qty_sesduah " + qty_sesudah);

                        while (res4.next()) {
                            int stok = Integer.parseInt(res4.getString("jumlah"));
                            System.out.println("stok " + stok);
                            kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 1));
                            if (qty_sesudah > stok) {
                                cukup = false;
                                //keluar while
                                break;
                            }
                        }
                        if (!cukup) {
                            //keluar pengecekan stok
                            break;
                        }
                        res4.close();
                    }
                }
                if (!cukup) {
                    //keluar for untuk input semua baris
                    break;
                } else {

                    //Input kode_konversi
                    int kode = 0;
                    String sqlkode = "select kode_konversi from konversi WHERE nama_konversi = '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 4) + "'";
                    System.out.println(sqlkode);
                    Statement statkode = con.createStatement();
                    java.sql.ResultSet reskode = statkode.executeQuery(sqlkode);
                    while (reskode.next()) {
                        kode = Integer.parseInt(reskode.getString("kode_konversi"));
                    }
                    reskode.close();

                    //Memasukkan baris ke-i
                    double jumlah = Double.parseDouble(String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(i, 5)));
                    double qty_sesudah = qty_konversi(String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(i, 1)), kode_konversi, jumlah);

                    String sql = "UPDATE mutasi_antar_gudang_detail SET "
                            + "kode_barang = '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 1).toString() + "', "
                            + "nama_barang= '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 2).toString() + "', "
                            + "kode_lokasi_asal= '" + lokasi_asal + "', "
                            + "satuan= '" + kode + "', "
                            + "qty= '" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 5).toString() + "', "
                            + "dikirim='" + tbl_Permintaan_MutasiAG_detail.getValueAt(i, 6).toString() + "', "
                            + "kode_lokasi_tujuan='" + lokasi_tujuan + "' "
                            + "WHERE no_bukti ='" + no_bukti + "'";
                    System.out.println("update detail " + i);
                    System.out.println(sql);
                    row = st.executeUpdate(sql);
                    if (!tbl_Permintaan_MutasiAG_detail.getValueAt(i, 5).toString().equals(tbl_Permintaan_MutasiAG_detail.getValueAt(i, 6).toString())) {
                        status = "ON PROGRESS";
                    } else {
                        status = "OPEN";
                    }

                }
            }
            //Memasukkan data ke mutasi gudang umum
            if (cukup) {
                String date = txt_tgl.getText();
                String sql = "UPDATE mutasi_antar_gudang "
                        + "SET user ='" + "3" + "',"
                        + "keterangan = '" + txt_Keterangan.getText() + "',"
                        + "jumlah_item = '" + jumlah_Item + "',"
                        + "jumlah_qty = '" + jumlah_Qty + "',"
                        + "status = '" + status + "'"
                        + "WHERE no_bukti = '" + no_bukti + "'";
                System.out.println(sql);
                row = st.executeUpdate(sql);
            } else {
                JOptionPane.showMessageDialog(null, "Stok barang " + kode_barang + " di gudang " + tbl_Permintaan_MutasiAG_detail.getValueAt(0, 3) + " tidak cukup");
            }
            if (row >= 1) {
                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang dimasukkan");
        }
    }

    double qty_konversi(String kode_barang, String kode_konversi, double qty) {
        double hasil = 0;
        try {
            String sql = "select * from barang_konversi where kode_barang='" + kode_barang + "' and kode_konversi ='" + kode_konversi + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            double jumlah_konversi = 0;
            while (res.next()) {
                jumlah_konversi = Double.parseDouble(res.getString("jumlah_konversi"));
            }
            hasil = jumlah_konversi * qty;
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
        return hasil;

    }

    public void ClearData() {
        DefaultTableModel dm = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        dm.addRow(new Object[]{"", "", "", lokasi[lokasi_asal - 1], "", "", "", lokasi[lokasi_tujuan - 1]});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox();
        comTableKode = new javax.swing.JComboBox();
        txt_Qty = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Permintaan_MutasiAG_detail = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        label_Save = new javax.swing.JLabel();
        label_Clear = new javax.swing.JLabel();
        label_Delete = new javax.swing.JLabel();
        label_Exit = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        label_NamaKasir = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txt_Keterangan = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        txt_tgl = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        txt_noBukti = new javax.swing.JTextField();
        txt_noPermintaan = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        comLokasiAsal = new javax.swing.JComboBox<>();
        txt_JumItem = new javax.swing.JTextField();
        txt_JumQty = new javax.swing.JTextField();

        comTableBarang.setEditable(true);
        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        comTableKode.setEditable(true);
        comTableKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKodeActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Tanggal");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("Lokasi Asal");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.white, java.awt.Color.white));

        tbl_Permintaan_MutasiAG_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Asal", "Satuan (1/2/3)", "Jumlah", "Dikirim", "Tujuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Permintaan_MutasiAG_detail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAG_detailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_Permintaan_MutasiAG_detailKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Permintaan_MutasiAG_detail);
        if (tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumnCount() > 0) {
            tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(comTableKode));
            tbl_Permintaan_MutasiAG_detail.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(comTableBarang));
        }

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        label_Save.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        label_Save.setText("F12 - Save");
        label_Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_SaveMouseClicked(evt);
            }
        });

        label_Clear.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        label_Clear.setText("F9 - Clear");
        label_Clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ClearMouseClicked(evt);
            }
        });

        label_Delete.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        label_Delete.setText("F5-Delete");

        label_Exit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        label_Exit.setText("Esc - Exit");
        label_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ExitMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        label_NamaKasir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_NamaKasir.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        txt_Keterangan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Keterangan");

        jSeparator4.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txt_tgl.setEditable(false);
        txt_tgl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tglActionPerformed(evt);
            }
        });

        jSeparator10.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel27.setBackground(new java.awt.Color(255, 153, 102));
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel27.setText("Load Permintaan");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        txt_noBukti.setEditable(false);
        txt_noBukti.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_noBukti.setEnabled(false);
        txt_noBukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noBuktiActionPerformed(evt);
            }
        });

        txt_noPermintaan.setEditable(false);
        txt_noPermintaan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_noPermintaan.setEnabled(false);
        txt_noPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noPermintaanActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setText("No.Permintaan");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 204));
        jLabel15.setText("No.Bukti");

        comLokasiAsal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PUSAT", "GUD63", "TOKO", "TENGAH", "UTARA" }));
        comLokasiAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comLokasiAsalActionPerformed(evt);
            }
        });

        txt_JumItem.setBackground(new java.awt.Color(0, 0, 0));
        txt_JumItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_JumItem.setForeground(new java.awt.Color(255, 204, 0));
        txt_JumItem.setText("Jumlah Item");
        txt_JumItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_JumItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_JumItemMouseClicked(evt);
            }
        });
        txt_JumItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_JumItemActionPerformed(evt);
            }
        });

        txt_JumQty.setBackground(new java.awt.Color(0, 0, 0));
        txt_JumQty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_JumQty.setForeground(new java.awt.Color(255, 204, 0));
        txt_JumQty.setText("Jumlah Qty");
        txt_JumQty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_JumQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_JumQtyMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(32, 32, 32)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_tgl)
                                            .addComponent(comLokasiAsal, 0, 152, Short.MAX_VALUE))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addGap(20, 20, 20)
                                                .addComponent(txt_noPermintaan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txt_noBukti, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(label_Save)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_Clear)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_Delete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_Exit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel27)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_NamaKasir))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_JumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_JumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_Save)
                    .addComponent(label_Exit)
                    .addComponent(label_Delete)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Clear)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(comLokasiAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_noBukti, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txt_noPermintaan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_JumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_JumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_NamaKasir, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tglActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tglActionPerformed

    private void txt_noBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noBuktiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noBuktiActionPerformed

    private void txt_noPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noPermintaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noPermintaanActionPerformed

    private void comLokasiAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comLokasiAsalActionPerformed
        seleksiLokasi();
        System.out.println("lokasi " + lokasi_asal);
        for (int i = 0; i < tbl_Permintaan_MutasiAG_detail.getRowCount(); i++) {
            tbl_Permintaan_MutasiAG_detail.setValueAt(lokasi[lokasi_asal - 1], i, 3);
        }
    }//GEN-LAST:event_comLokasiAsalActionPerformed

    private void txt_JumItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_JumItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_JumItemMouseClicked

    private void txt_JumItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_JumItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_JumItemActionPerformed

    private void txt_JumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_JumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_JumQtyMouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        Master_Mutasi_PermintaanMutasiAntarGudang mmp = new Master_Mutasi_PermintaanMutasiAntarGudang();
        mmp.setVisible(true);
        mmp.setFocusable(true);
    }//GEN-LAST:event_jLabel27MouseClicked
    void load_dari_nama_barang() {
        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
        String nama_awal = String.valueOf(comTableBarang.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableBarang.getSelectedItem());
        if (comTableBarang.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");
                loadNumberTable();
                String kode_strip = kode + "-" + nama;
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode, selectedRow, 1);
                    tbl_Permintaan_MutasiAG_detail.setValueAt(nama, selectedRow, 2);
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }
    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        load_dari_nama_barang();

//
//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
//
//        try {
//            /*
//            String kalimat = comTableKode.getSelectedItem().toString();
//            String[] kata = kalimat.split(" -- ");
//            for (int i = 0; i < kata.length; i++) {
//                System.out.println(kata[i]);
//            }
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[0], 0, 1);
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[1], 0, 2);
//             */
//
//            String sql = "select * from barang where kode_barang = '" + comTableBarang.getSelectedItem().toString() + "'";
//            System.out.println(comTableBarang.getSelectedItem());
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                //      loadComTableSatuan();
//                //      String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(brg, selectedRow, 2);
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode, selectedRow, 1);
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
////         comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
////         tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 2);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//
//        /*
//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
//
//        try {
//            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString("kode_barang");
//                String barang = res.getString("nama_barang");
//                //              loadComTableSatuan();
//                //            String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode, selectedRow, 1);
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(barang, selectedRow, 1);
//                    
//                    //            tbl_Permintaan_MutasiAG_detail.setValueAt(konv, selectedRow, 4);
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
//                //            comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //          tbl_Permintaan_MutasiAG_detail.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 4);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//         */
    }//GEN-LAST:event_comTableBarangActionPerformed

    private void label_ClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ClearMouseClicked
        ClearData();
    }//GEN-LAST:event_label_ClearMouseClicked

    private void tbl_Permintaan_MutasiAG_detailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_detailKeyPressed

        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
        System.out.println(selectedRow);
        //int selectedRow = tbl_tambah_mutasi.getSelectedRow();
        //int baris = tbl_tambah_mutasi.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
                if (tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 5).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 4).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 3).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 2).toString().equals("")
                        || tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1).toString().equals("")) {
                    throw new NullPointerException();
                } else {

                    int jumlah = 0, jumlahQty = 0;
                    int qty = 0;
                    int i = tbl_Permintaan_MutasiAG_detail.getRowCount();

                    for (int j = 0; j < i; j++) {
                        jumlahQty += Integer.parseInt(tbl_Permintaan_MutasiAG_detail.getValueAt(j, 5).toString());
                    }
                    jumlah_Item = i;
                    jumlah_Qty = jumlahQty;
                    txt_JumItem.setText("Jumlah Item : " + String.valueOf(i));
                    txt_JumQty.setText("Jumlah Qty : " + String.valueOf(jumlahQty));

                    model.addRow(new Object[]{"", "", "", "", "", "", "", ""});
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
            } else if (evt.getKeyCode() == KeyEvent.VK_F5) {
                if (tbl_Permintaan_MutasiAG_detail.getRowCount() - 1 == -1) {
                    JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
                } else {
                    removeSelectedRows(tbl_Permintaan_MutasiAG_detail);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
                int simpan_data = 1;
                simpan_data = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menyimpan data ?", "konfirmasi", JOptionPane.YES_NO_OPTION);
                if (simpan_data == 0) {
                    SaveData();
                }

            } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
                ClearData();
            } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
                if (tbl_Permintaan_MutasiAG_detail.getRowCount() - 1 == -1) {
                    model.addRow(new Object[]{"", "", "", "", "", "", "", ""});
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_1 && tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '1' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_Permintaan_MutasiAG_detail.setValueAt(sat2, tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_2 && tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '2' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = sat;
                        tbl_Permintaan_MutasiAG_detail.setValueAt(sat2, tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_3 && tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 4) {
                System.out.println("ini alt");
                String kode_barang = String.valueOf(tbl_Permintaan_MutasiAG_detail.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 1));
                try {
                    String sql = "select nama_konversi from konversi k join barang_konversi bk on bk.kode_konversi = k.kode_konversi where bk.identitas_konversi = '3' and bk.kode_barang = '" + kode_barang + "'";
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    while (res.next()) {
                        String sat = res.getString("nama_konversi");
                        String sat2 = "2. " + sat;
                        tbl_Permintaan_MutasiAG_detail.setValueAt(sat2, tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 4);
                        System.out.println(sat2);
                    }
                    res.close();
                    conn.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 1 || tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 2 || tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 3 || tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 6)) {
                InputMap im = tbl_Permintaan_MutasiAG_detail.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(down, im.get(f2));
                System.out.println("asd");

            } else if (evt.getKeyCode() == KeyEvent.VK_DOWN && (tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 0 || tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 4 || tbl_Permintaan_MutasiAG_detail.getSelectedColumn() == 5)) {
                InputMap im = tbl_Permintaan_MutasiAG_detail.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
                KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                im.put(f2, null);
                im.put(down, null);
                System.out.println("fgh");
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Data harus lengkap");

        }

        loadNumberTable();

        /*
         DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
         int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
         int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
         int jumlah = 0;
         int qty = 0;

         TableModel tabelModel;
         tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
         jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 5).toString());
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
         jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 5).toString());
         if (tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getSelectedRow(), 5).toString().equals("0")) {
         JOptionPane.showMessageDialog(null, "Data Tidak Boleh kosong", "", 2);
         } else {
         if (Integer.parseInt(tabelModel.getValueAt(tbl_Permintaan_MutasiAG_detail.getRowCount() - 1, 5).toString()) != 0) {
         model.addRow(new Object[]{"", "", "", tabelModel.getValueAt(0, 3).toString(), "", "", "", tabelModel.getValueAt(0, 7).toString()});
         }
         }
         } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
         if (tbl_Permintaan_MutasiAG_detail.getRowCount() - 1 == -1) {
         JOptionPane.showMessageDialog(null, "Data didalam tabel telah tiada.", "", 2);
         } else {
         removeSelectedRows(tbl_Permintaan_MutasiAG_detail);
         }
         } else if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
         if (tbl_Permintaan_MutasiAG_detail.getRowCount() - 1 == -1) {
         model.addRow(new Object[]{"", "", "", "", "", "", "", ""});
         }
         }
         loadNumberTable();
         */
    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_detailKeyPressed

    private void label_SaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_SaveMouseClicked
        SaveData();
    }//GEN-LAST:event_label_SaveMouseClicked

    private void tbl_Permintaan_MutasiAG_detailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_Permintaan_MutasiAG_detailKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_Permintaan_MutasiAG_detail.getModel();
        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();

        TableModel tabelModel;
        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
        loadNumberTable();

    }//GEN-LAST:event_tbl_Permintaan_MutasiAG_detailKeyReleased
    void loadComboKode(String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' OR nama_barang like '%" + key + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode nama " + sql);
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
                    comTableKode.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comTableKode.getEditor().getEditorComponent()).setText(key);
                    conn.close();
                    res.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    void loadComboNama(String key) {
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                System.out.println("ini load combo nama");
                try {
                    String sql = "select concat(kode_barang,\"-\",nama_barang) as gabung from barang where kode_barang ='" + key + "' OR nama_barang like '%" + key + "%'";
                    System.out.println(sql);
                    java.sql.Connection conn = (Connection) Koneksi.configDB();
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet res = stm.executeQuery(sql);
                    System.out.println("ini sql com kode nama " + sql);
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
                    comTableBarang.setModel(new DefaultComboBoxModel(kode_nama_arr.toArray()));
                    ((JTextComponent) comTableBarang.getEditor().getEditorComponent()).setText(key);
                    conn.close();
                    res.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eror" + e);
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    void load_dari_kode_barang() {
        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
        String nama_awal = String.valueOf(comTableKode.getSelectedItem());
        String[] split = new String[2];
        System.out.println("nilai comTable barang adalah " + comTableKode.getSelectedItem());
        if (comTableKode.getSelectedItem() != null) {
            split = nama_awal.split("-");
        }
        try {
            String sql = "select kode_barang,nama_barang from barang where kode_barang = '" + split[0] + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString("kode_barang");
                String nama = res.getString("nama_barang");

                loadNumberTable();
                if (selectedRow != -1) {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode, selectedRow, 1);
                    tbl_Permintaan_MutasiAG_detail.setValueAt(nama, selectedRow, 2);
                }
            }
            conn.close();
            res.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
            e.printStackTrace();
        }
    }


    private void comTableKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKodeActionPerformed
        load_dari_kode_barang();

//        
//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
//
//        try {
//            /*
//            String kalimat = comTableKode.getSelectedItem().toString();
//            String[] kata = kalimat.split(" -- ");
//            for (int i = 0; i < kata.length; i++) {
//                System.out.println(kata[i]);
//            }
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[0], 0, 1);
//            tbl_Permintaan_MutasiAG_new.setValueAt(kata[1], 0, 2);
//            */
//            
//            String sql = "select * from barang where kode_barang = '" + comTableKode.getSelectedItem().toString() + "'";
//            System.out.println(comTableKode.getSelectedItem());
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString(1);
//                String brg = res.getString(4);
//                //      loadComTableSatuan();
//                //      String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(brg, selectedRow, 2);
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode , selectedRow, 1);
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
////         comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
////         tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 2);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//
//
//        
//        /*
//        int kode_barang = 0;
//        int baris = tbl_Permintaan_MutasiAG_detail.getRowCount();
//        TableModel tabelModel;
//        tabelModel = tbl_Permintaan_MutasiAG_detail.getModel();
//        int selectedRow = tbl_Permintaan_MutasiAG_detail.getSelectedRow();
//
//        try {
//            String sql = "select * from barang where kode_barang = '" + comTableKode.getSelectedItem() + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String kode = res.getString("kode_barang");
//                String brg = res.getString("nama_barang");
//                //      loadComTableSatuan();
//                //      String konv = comTableKonv.getSelectedItem().toString();
//
//                if (selectedRow != -1) {
//                    //     tbl_Permintaan_MutasiAG_new.setValueAt(konv, selectedRow, 3);
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(kode, selectedRow, 2);
//                    tbl_Permintaan_MutasiAG_detail.setValueAt(brg, selectedRow, 2);
//
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            String Konv = "";
//            while (res.next()) {
//                Konv = res.getString(1);
////         comTableKonv.addItem(Konv);
//                System.out.println("Konv =" + Konv);
//                //                System.out.println("echo : " + tbl_Pembelian.getValueAt(selectedRow, 2).toString());
////         tbl_Permintaan_MutasiAG_new.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 2);
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
//            e.printStackTrace();
//        }
//        */

    }//GEN-LAST:event_comTableKodeActionPerformed

    private void label_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ExitMouseClicked
        this.dispose();
    }//GEN-LAST:event_label_ExitMouseClicked

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiBarangAntarGudangBerdasarkanPermintaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiBarangAntarGudangBerdasarkanPermintaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiBarangAntarGudangBerdasarkanPermintaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_MutasiBarangAntarGudangBerdasarkanPermintaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_MutasiBarangAntarGudangBerdasarkanPermintaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comLokasiAsal;
    private javax.swing.JComboBox comTableBarang;
    private javax.swing.JComboBox comTableKode;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel label_Clear;
    private javax.swing.JLabel label_Delete;
    private javax.swing.JLabel label_Exit;
    private javax.swing.JLabel label_NamaKasir;
    private javax.swing.JLabel label_Save;
    private javax.swing.JTable tbl_Permintaan_MutasiAG_detail;
    private javax.swing.JTextField txt_JumItem;
    private javax.swing.JTextField txt_JumQty;
    private javax.swing.JTextField txt_Keterangan;
    private javax.swing.JTextField txt_Qty;
    private javax.swing.JTextField txt_noBukti;
    private javax.swing.JTextField txt_noPermintaan;
    private javax.swing.JTextField txt_tgl;
    // End of variables declaration//GEN-END:variables
}
