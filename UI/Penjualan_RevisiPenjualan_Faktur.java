/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author USER
 */
public class Penjualan_RevisiPenjualan_Faktur extends javax.swing.JFrame {

    public String totalclone;
    public Float Tempharga;
    public int subtotalfix = 0;
    int kode_barang = 0;
    String kov;
    public int subtotal1 = 0, hargajadi1 = 0, totalqty = 0, total = 0; //penjumlahan
    public int jumlah = 0, hargaRekom = 0, Jharga = 0, subtotal = 0, hargajadi = 0;//panggil colom tabel
    public int qty = 0;

    public Penjualan_RevisiPenjualan_Faktur() {
        initComponents();
        this.setLocationRelativeTo(null);
        AutoCompleteDecorator.decorate(comCustomer);
//        AutoCompleteDecorator.decorate(comSatuan);
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comTableKonv);
        AutoCompleteDecorator.decorate(comTableLokasi);
        AutoCompleteDecorator.decorate(comTableHarga);
        loadCustomer();
        loadComOrder();
        tanggal_jam_sekarang();
        loadComTableBarang();
        loadComOrder();
        loadComTableLokasi();
        loadNumberTable();
        AturlebarKolom();

    }

    void loadCustomer() {

        try {
            String sql = "select * from customer";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
                comCustomer.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
//    void loadComSatuan(){
//        try{
//            String sql = "select * from konversi";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()){
//                String nama = res.getString(2);
//                comSatuan.addItem(nama);
//            }
//        }catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//        }
//}

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by kode_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                comTableBarang.addItem(name);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    public void loadComOrder() {
        try {
            String sql = "SELECT * FROM `order` ORDER BY order.no_faktur_order ASC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[2];
                order[0] = res.getString(2);
                comOrder.addItem((String) order[0]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from lokasi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadNumberTable() {
        int baris = tbl_Penjualan.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Penjualan.setValueAt(nomor + ".", a, 0);
        }

    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Penjualan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Penjualan.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Penjualan.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);
        column = tbl_Penjualan.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = tbl_Penjualan.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Penjualan.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tbl_Penjualan.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = tbl_Penjualan.getColumnModel().getColumn(6);
        column.setPreferredWidth(90);
        column = tbl_Penjualan.getColumnModel().getColumn(7);
        column.setPreferredWidth(90);
        column = tbl_Penjualan.getColumnModel().getColumn(8);
        column.setPreferredWidth(70);
        column = tbl_Penjualan.getColumnModel().getColumn(9);
        column.setPreferredWidth(90);

    }

    void BersihField() {
        txt_faktur.setText("");
        txt_keterangan.setText("");
        txt_Staff.setText("");
    }

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR_OF_DAY);
                    int menit = cal.get(Calendar.MINUTE);
//                  int detik = cal.get(Calendar.SECOND);
                    txt_tanggal.setText(tahun + " - " + (bulan + 1) + " - " + hari + " " + jam + ":" + menit);

                }
            }
        };
        p.start();
    }

    static String rptabel(String b) {
        b = b.replace(",", "");
        b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
        return b;
    }

    static String rptabelkembali(String b) {
        b = b.replace(",", "");

        return b;
    }

    public void HitungSemua() {
        if (tbl_Penjualan.getRowCount() >= 1) {
            subtotalfix = 0;
            for (int i = tbl_Penjualan.getRowCount() - 1; i > -1; i--) {
                int x = Integer.parseInt(tbl_Penjualan.getValueAt(i, 9).toString());
                subtotalfix += x;
            }
            txt_tbl_total.setText(String.valueOf(subtotalfix));
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

        comTableBarang = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        comTableHarga = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comTop = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_Save = new javax.swing.JLabel();
        lbl_Clear = new javax.swing.JLabel();
        lbl_Print = new javax.swing.JLabel();
        lbl_Exit = new javax.swing.JLabel();
        lbl_Prev = new javax.swing.JLabel();
        lbl_Next = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        txt_faktur = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        comSales = new javax.swing.JComboBox<>();
        txt_Staff = new javax.swing.JTextField();
        txt_keterangan = new javax.swing.JTextField();
        txt_tbl_total = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        txt_item = new javax.swing.JTextField();
        txt_jumQty = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        lbl_Sj = new javax.swing.JLabel();
        JLunas = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        comCustomer = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Penjualan = new javax.swing.JTable();
        comOrder = new javax.swing.JComboBox<>();

        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableHargaActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Customer");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 204));
        jLabel15.setText("No. Faktur");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 204));
        jLabel16.setText("No. Order");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setText("T.O.P");

        comTop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TUNAI", "30 HARI", "120 HARI", "45 HARI", "60 HARI", "75 HARI", "90 HARI", "14 HARI", "7 HARI" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Keterangan Verifikasi");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 204));
        jLabel23.setText("Staff");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setText("Tanggal");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Total");

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));

        lbl_Save.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        lbl_Save.setText("F12 - Save");
        lbl_Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_SaveMouseClicked(evt);
            }
        });

        lbl_Clear.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        lbl_Clear.setText("F9 - Clear");

        lbl_Print.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        lbl_Print.setText("Print");
        lbl_Print.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_PrintAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lbl_Print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_PrintMouseClicked(evt);
            }
        });

        lbl_Exit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        lbl_Exit.setText("Esc - Exit");

        lbl_Prev.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        lbl_Prev.setText("Prev");

        lbl_Next.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        lbl_Next.setText("Next");

        jButton1.setText(">");
        jButton1.setAlignmentY(0.0F);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_nama.setBackground(new java.awt.Color(184, 238, 184));
        txt_nama.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_nama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_namaMouseClicked(evt);
            }
        });

        txt_alamat.setBackground(new java.awt.Color(184, 238, 184));
        txt_alamat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_alamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_alamatMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 0, 0));
        jLabel29.setText("Nama");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 0, 0));
        jLabel30.setText("Alamat");

        txt_tanggal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tanggal.setEnabled(false);
        txt_tanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_tanggalMouseClicked(evt);
            }
        });

        txt_faktur.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_faktur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_fakturMouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 204));
        jLabel31.setText("Salesman");

        comSales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BON TOKO", "AGUS", "AAA" }));

        txt_Staff.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_Staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_StaffMouseClicked(evt);
            }
        });

        txt_keterangan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_keterangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_keteranganMouseClicked(evt);
            }
        });

        txt_tbl_total.setBackground(new java.awt.Color(204, 255, 204));
        txt_tbl_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_tbl_totalMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Kasir");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox1.setText("LGSG CETAK");

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox2.setText("NON DENDA");

        txt_item.setBackground(new java.awt.Color(0, 0, 0));
        txt_item.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_item.setForeground(new java.awt.Color(255, 204, 0));
        txt_item.setText("Jumlah Item");
        txt_item.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_itemMouseClicked(evt);
            }
        });
        txt_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_itemActionPerformed(evt);
            }
        });

        txt_jumQty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumQty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumQty.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumQty.setText("Jumlah Qty");
        txt_jumQty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumQtyMouseClicked(evt);
            }
        });
        txt_jumQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumQtyActionPerformed(evt);
            }
        });

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setText("Verifikasi Administrasi");
        jCheckBox3.setMargin(new java.awt.Insets(2, 0, 2, 2));

        lbl_Sj.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lbl_Sj.setForeground(new java.awt.Color(204, 153, 0));
        lbl_Sj.setText("Print Surat Jalan");

        JLunas.setBackground(new java.awt.Color(153, 0, 0));
        JLunas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JLunas.setForeground(new java.awt.Color(255, 255, 51));
        JLunas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JLunas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        JLunas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLunasMouseClicked(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        comCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Masukkan Nama Customer --" }));
        comCustomer.setToolTipText("");
        comCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comCustomerActionPerformed(evt);
            }
        });

        tbl_Penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "J.Harga", "Harga", "Rekom Harga", "Sub total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Penjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_Penjualan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PenjualanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_PenjualanKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Penjualan);
        if (tbl_Penjualan.getColumnModel().getColumnCount() > 0) {
            tbl_Penjualan.getColumnModel().getColumn(0).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(1).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(2).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comTableBarang));
            tbl_Penjualan.getColumnModel().getColumn(3).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Penjualan.getColumnModel().getColumn(4).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_Penjualan.getColumnModel().getColumn(5).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(6).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comTableHarga));
            tbl_Penjualan.getColumnModel().getColumn(7).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(8).setResizable(false);
            tbl_Penjualan.getColumnModel().getColumn(9).setResizable(false);
        }

        comOrder.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih No Order --" }));
        comOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(comCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addGap(10, 10, 10))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel16))
                                        .addGap(13, 13, 13))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel17)
                                        .addGap(21, 21, 21)
                                        .addComponent(comTop, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comSales, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addGap(25, 25, 25)
                                .addComponent(txt_Staff))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addGap(180, 180, 180)
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_item, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_Save)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Clear)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Print)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Sj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Exit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Prev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Next))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(458, 458, 458)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(JLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel25)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_Save)
                            .addComponent(lbl_Clear)
                            .addComponent(lbl_Print)
                            .addComponent(lbl_Exit)
                            .addComponent(lbl_Prev)
                            .addComponent(lbl_Next)
                            .addComponent(lbl_Sj))
                        .addGap(8, 8, 8)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17)))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel16)
                    .addComponent(jLabel23)
                    .addComponent(txt_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(txt_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jCheckBox3))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox1)
                        .addComponent(jCheckBox2)
                        .addComponent(txt_item, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaMouseClicked

    private void txt_alamatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_alamatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatMouseClicked

    private void txt_tanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tanggalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tanggalMouseClicked

    private void txt_fakturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_fakturMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fakturMouseClicked

    private void txt_StaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_StaffMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StaffMouseClicked

    private void txt_keteranganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_keteranganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keteranganMouseClicked

    private void txt_tbl_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tbl_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tbl_totalMouseClicked

    private void txt_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_itemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemMouseClicked

    private void txt_jumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyMouseClicked

    private void lbl_PrintAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_PrintAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_PrintAncestorAdded

    private void lbl_PrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_PrintMouseClicked
//        Penjualan_RevisiPenjualan_Faktur_Print ppp = new Penjualan_RevisiPenjualan_Faktur_Print();
//        ppp.setVisible(true);
    }//GEN-LAST:event_lbl_PrintMouseClicked

    private void JLunasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLunasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JLunasMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Penjualan_KotakHistoriPenjualan pkhb = new Penjualan_KotakHistoriPenjualan();
        pkhb.setVisible(true);
        pkhb.setFocusable(true);
        this.setFocusable(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void comCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comCustomerActionPerformed
        try {
            String sql = "select * from customer where nama_customer = '" + comCustomer.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String namaSales = res.getString(2);
                String alamat = res.getString(4);
                txt_nama.setText(namaSales);
                txt_alamat.setText(alamat);
//              txt_rekSupply.setText(rek);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comCustomerActionPerformed

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        int baris = tbl_Penjualan.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        int selectedRow = tbl_Penjualan.getSelectedRow();

        try {
            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String lokasi = "Pusat";
                String testjumlah = "0";
                if (selectedRow != -1) {
                    tbl_Penjualan.setValueAt(kode, selectedRow, 1);
                    tbl_Penjualan.setValueAt(lokasi, selectedRow, 3);
                    tbl_Penjualan.setValueAt(testjumlah, selectedRow, 5);
                    tbl_Penjualan.setValueAt(testjumlah, selectedRow, 8);
                    tbl_Penjualan.setValueAt(testjumlah, selectedRow, 9);
                }
//                loadComSatuan(tbl_Penjualan.getValueAt(selectedRow, 1).toString());
//               tbl_Penjualan.setValueAt(loadComSatuan.getSelectedItem(), selectedRow, 3);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror aaa" + e);
        }
//          try{
//            String sql = "select l.kode_lokasi, l.nama_lokasi from lokasi l, barang_lokasi bl where bl.kode_barang = '" + kode_barang + "'";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()){
//                String lokasi = res.getString(2);
//                tbl_Penjualan.setValueAt(lokasi, selectedRow, 3);
//                System.out.println("ret" +lokasi);
//            }
//        }
//        catch(Exception e){System.out.println(e);}
//        comTableHarga.removeAllItems();
        try {
            for (int i = 0; i < baris; i++) {
                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            }
            String sql = "SELECT harga_jual_1_barang,harga_jual_2_barang,harga_jual_3_barang FROM barang WHERE kode_barang ='" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
//          String jharga = res.getString(2);
//          int selectedRow = tbl_Penjualan.getSelectedRow();
            while (res.next()) {
                String Harga1 = res.getString(1);
                comTableHarga.addItem(Harga1);
                String Harga2 = res.getString(2);
                comTableHarga.addItem(Harga2);
                String Harga3 = res.getString(3);
                comTableHarga.addItem(Harga3);
                System.out.println("h2: " + Harga2);
                Tempharga = Float.valueOf(Harga2);
                tbl_Penjualan.setValueAt(Harga2, selectedRow, 6);
            }

        } catch (Exception e) {
            //           JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            //e.printStackTrace();
        }
        comTableKonv.removeAllItems();
        try {
            String sql = "select k.nama_konversi, k.kode_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String Konv = "";
//            System.out.println("test =" +sql);
//            System.out.println("k =" +kode_barang);
//            System.out.println("Select =" + comTableKonv.getSelectedItem());
            while (res.next()) {
                Konv = res.getString(1);
                comTableKonv.addItem(Konv);
                System.out.println("Konv =" + Konv);
                tbl_Penjualan.setValueAt(comTableKonv.getSelectedItem(), selectedRow, 4);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            e.printStackTrace();
        }
        try {
        } catch (Exception e) {
        }


    }//GEN-LAST:event_comTableBarangActionPerformed

    private void comTableHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableHargaActionPerformed

    }//GEN-LAST:event_comTableHargaActionPerformed

    private void tbl_PenjualanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyPressed
        int kode_barang = 0;
        int baris = tbl_Penjualan.getRowCount();
        int selectedRow = tbl_Penjualan.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter
            TableModel tabelModel;
            tabelModel = tbl_Penjualan.getModel();
            jumlah = Integer.parseInt(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 5).toString());
            System.out.println("Jumlah :" + jumlah);
            if (comTableHarga.getSelectedIndex() >= -1) {
                comTableHarga.removeAllItems();
                Jharga = Integer.parseInt(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 8).toString());
                System.out.println("ambil harga field 8: " + Jharga);
                subtotal1 = jumlah * Jharga;
                tabelModel.setValueAt(subtotal1, tbl_Penjualan.getSelectedRow(), 9);
            }
            if (tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 8).toString().equals("0")) {
                hargaRekom = Integer.parseInt(tabelModel.getValueAt(tbl_Penjualan.getSelectedRow(), 6).toString());
                System.out.println("ambil harga field 6: " + hargaRekom);
                subtotal = jumlah * hargaRekom;
                // Tempharga = Float.valueOf(harga1);
                tabelModel.setValueAt(subtotal, tbl_Penjualan.getSelectedRow(), 9);
            }

            HitungSemua();
            //if (Integer.parseInt(tabelModel.getValueAt(tbl_Penjualan.getRowCount(), 9).toString()) != 0) {
            totalqty += jumlah;
            model.addRow(new Object[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0",});
            //}
//
            txt_item.setText("" + tbl_Penjualan.getRowCount());
            txt_jumQty.setText("" + totalqty);
        }
        loadNumberTable();
    }//GEN-LAST:event_tbl_PenjualanKeyPressed

    private void tbl_PenjualanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PenjualanKeyReleased

    }//GEN-LAST:event_tbl_PenjualanKeyReleased

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_Penjualan.getModel();
        int baris = tbl_Penjualan.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Penjualan.getModel();
        int kode_barang = 0;
        try {
            for (int i = 0; i < baris; i++) {
                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            }
            String sql = "select nama_konversi, jumlah_konversi, identitas_konversi from barang_konversi bk, konversi k where bk.kode_konversi = k.kode_konversi and bk.kode_barang ='" + kode_barang + "'";
            System.out.println("sql: " + sql);
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                System.out.println("y1 " + comTableKonv.getSelectedIndex() + " a: " + res.getInt(3));
                if (comTableKonv.getSelectedIndex() == res.getInt(3) - 1) {
                    System.out.println("y2 " + res.getFloat(2) + " t: " + Tempharga);
                    float temp = Tempharga * res.getFloat(2);
                    System.out.println("error: " + temp);
                    model.setValueAt(String.valueOf(temp), tbl_Penjualan.getSelectedRow(), 7);

                }
//               System.out.println("evt: "+comTableKonv.getSelectedIndex());
//                String konvTemp = res.getString(2);
//                int selectedRow = tbl_Penjualan.getSelectedRow();
//                if ((comTableKonv.getSelectedIndex() != 0) && ((comTableKonv.getSelectedIndex() + 1) == res.getInt(3))) {
//                    float temp = Tempharga * res.getInt(1);
//                    model.setValueAt(String.valueOf(temp), selectedRow, 6);
//                    System.out.println(">0 " + temp);
//                } 

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pilih Barang dan Harga Terlebih Dahulu !");
        }
    }//GEN-LAST:event_comTableKonvActionPerformed

    private void comOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comOrderActionPerformed

    }//GEN-LAST:event_comOrderActionPerformed

    private void lbl_SaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SaveMouseClicked
//        try {
//            Koneksi Koneksi = new Koneksi();
//            Connection con = Koneksi.configDB();
//            
//            Statement st = con.createStatement();
//            String sql = "insert into penjualan( no_faktur_pembelian, no_faktur_supplier_pembelian, tgl_penjualan,  tgl_nota_supplier_pembelian,  discon_persen, discon_rp, keterangan_pembelian)"
//                    + "value('" + txt_noNota.getText() + "','" + txt_inv.getText() + "','" + txt_tgl.getText() + "','" + date + "','" + txt_diskon.getText() + "','" + txt_diskonRp.getText() + "','" + txt_ket.getText() + "');";
//           System.out.println(sql);
//            int row = st.executeUpdate(sql);
//
//            if (row == 1) {
//                JOptionPane.showMessageDialog(null, "data sudah ditambahkan ke database", "informasi", JOptionPane.INFORMATION_MESSAGE);
//                con.close();
//            }
//
//            sql = "insert into supplier( nama_supplier, alamat_supplier, rekening_supplier)"
//                    + "value('" + txt_nmSupply.getText() + "','" + txt_almtSupply.getText() + "','" + txt_rekSupply.getText() + "');";
////            System.out.println(sql);
//            st.executeUpdate(sql);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "data tidak dimasukkan ke database" + e, "informasi", JOptionPane.INFORMATION_MESSAGE);
//        } finally {
//
//        }

    }//GEN-LAST:event_lbl_SaveMouseClicked

    private void txt_jumQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyActionPerformed

    private void txt_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_itemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemActionPerformed

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
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_RevisiPenjualan_Faktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_RevisiPenjualan_Faktur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JLunas;
    private javax.swing.JComboBox<String> comCustomer;
    private javax.swing.JComboBox<String> comOrder;
    private javax.swing.JComboBox<String> comSales;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableHarga;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JComboBox<String> comTop;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbl_Clear;
    private javax.swing.JLabel lbl_Exit;
    private javax.swing.JLabel lbl_Next;
    private javax.swing.JLabel lbl_Prev;
    private javax.swing.JLabel lbl_Print;
    private javax.swing.JLabel lbl_Save;
    private javax.swing.JLabel lbl_Sj;
    private javax.swing.JTable tbl_Penjualan;
    private javax.swing.JTextField txt_Staff;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_faktur;
    private javax.swing.JTextField txt_item;
    private javax.swing.JTextField txt_jumQty;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_tbl_total;
    // End of variables declaration//GEN-END:variables
}
