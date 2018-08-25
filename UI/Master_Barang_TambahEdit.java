
package UI;
import Class.Koneksi;
import java.sql.Connection;
import com.sun.glass.events.KeyEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Java.*;
import javax.swing.JOptionPane;
import Java.ListBarang;
import Java.TrBarang;
import java.sql.PreparedStatement;
public class Master_Barang_TambahEdit extends javax.swing.JDialog {
    
//    Object
//    private ResultSet hasil1;
    private Connect connection;
    private ResultSet hasil;
    private PreparedStatement PS;
    private ListBarang listBarang;
     private TrBarang TrBarang;
//    Var
    private boolean isEdit = false;
    private int id;
    
    
    public Master_Barang_TambahEdit() {
        initComponents();
        this.setVisible(true);
//        loadComkelompok();
//        loadComtop();
//        this.setLocationRelativeTo(null);
    }
    
    public Master_Barang_TambahEdit(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setVisible(true);
//        loadComkelompok();
//        loadComtop();
//        this.setLocationRelativeTo(null);
    }
    
    
    Master_Barang_TambahEdit(java.awt.Dialog parent, boolean modal, Connect connection,String kode){
        super(parent, modal);
        initComponents();
        this.connection = connection;
//        this.setVisible(true);
        this.id = id;
        this.listBarang = listBarang;
        this.isEdit = isEdit;
        System.out.println("vcbxcxvxxcxvxcvvxvvx");
        setContent(kode);
        loadComkelompok("*");
        loadComtop();
        loadComsatuan1();
        loadComsatuan2();
        System.out.println("bbbb1: ");
    }
            
//       Master_Barang_TambahEdit(java.awt.Dialog parent, boolean modal, ListBarang listBarang, int id) {
//        super(parent, modal);
//        initComponents();
//        this.id = id;
//        
//        this.listBarang = listBarang;
//        this.isEdit = isEdit;
//        loadComkelompok("*");
//        loadComtop();
//        loadComsatuan1();
//        loadComsatuan2();
//        setContent("kode_barang");
//        System.out.println("bbbb: ");
//      
//    }
//
//        
       
        private void setContent(String kode) {
            try{
                String sqlBarang = "SELECT * from barang where kode_barang ='"
                                +kode +"'";  
              java.sql.Connection conn = Koneksi.configDB();
              java.sql.Statement stm = conn.createStatement();
              java.sql.ResultSet res = stm.executeQuery(sqlBarang);
                System.out.println("eror a???");
                
            
            while(res.next()){
                txt_kodebarang.setText(kode);
                txt_namabarang.setText(res.getString("nama_barang"));
                txt_harga_jual1.setText(res.getString("harga_jual_1_barang"));
                txt_harga_jual2.setText(res.getString("harga_jual_2_barang"));
                txt_harga_jual3.setText(res.getString("harga_jual_3_barang"));
//                com_kelompok.setSelectedItem(res.getString("id_kelompok"));
//                com_top.addItem(res.getString("nama_top"));
//                com_satuan1.addItem(res.getString("nama_konversi"));
//                com_satuan2.addItem(res.getString("nama_konversi"));
                loadComkelompok(res.getString("id_kelompok"));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e +"ini eror");
        }
        }
       
      
//        private void setContent() {
// //            while(){
//                
////            }
//        txt_kodebarang.setText(String.valueOf(listBarang.getKode_barang()));
//        txt_namabarang.setText(listBarang.getNama_barang());
//        com_kelompok.setSelectedItem(listBarang.getId_kelompok());
//        com_top.setSelectedItem(listBarang.getId_top());
//        txt_harga_jual1.setText(String.valueOf(listBarang.getHarga_jual_1()));
//        txt_harga_jual2.setText(String.valueOf(listBarang.getHarga_jual_2()));
//        txt_harga_jual3.setText(String.valueOf(listBarang.getHarga_jual_3()));
//            System.out.println("getstat: "+listBarang.getStatus());
//         if (listBarang.getStatus()==null || listBarang.getStatus().equals("aktif")) {
//            aktif.setSelected(true);
//        } else {
//            nonaktif.setSelected(true);
//        }
//        }
//        
        
         void loadComkelompok(String param) {
        try {
            String sql = "select * from kelompok";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("kel: "+sql);
            while (res.next()) {
                String name = res.getString(2);
                com_kelompok.addItem(name);
                if (param.equals(res.getString("kode_kelompok"))) {
                    com_kelompok.setSelectedItem(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
         }
        
         
       void loadComtop() {
             
         try {
            String sql = "select * from top";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                com_top.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
       
        void loadComsatuan1() {
             
         try {
            String sql = "select * from konversi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                com_satuan1.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
        
         void loadComsatuan2() {
             
         try {
            String sql = "select * from konversi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                com_satuan2.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_harga_jual2 = new javax.swing.JTextField();
        txt_harga_jual1 = new javax.swing.JTextField();
        txt_hargarata2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txt_komisi_hj1 = new javax.swing.JTextField();
        txt_komisi_hj2 = new javax.swing.JTextField();
        aktif = new javax.swing.JRadioButton();
        jumlah = new javax.swing.JTextField();
        txt_namabarang = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_hitungan1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        com_satuan1 = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        txt_hitungan2 = new javax.swing.JTextField();
        com_satuan2 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        nonaktif = new javax.swing.JRadioButton();
        com_kelompok = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        com_top = new javax.swing.JComboBox<>();
        txt_kodebarang = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txt_harga_beli = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txt_harga_jual3 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        simpan = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        txt_denda_hj1 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txt_denda_hj2 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(870, 427));

        txt_harga_jual2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual2MouseClicked(evt);
            }
        });

        txt_harga_jual1.setText("\n");
        txt_harga_jual1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual1MouseClicked(evt);
            }
        });

        txt_hargarata2.setText("0");
        txt_hargarata2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hargarata2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hargarata2MouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("Harga Jual 3");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Harga Jual 2");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Gambar Produk"));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/upload.png"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(226, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Komisi Hj 1 ");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Komisi Hj 2");

        txt_komisi_hj1.setText("0");
        txt_komisi_hj1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_komisi_hj1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_komisi_hj1MouseClicked(evt);
            }
        });
        txt_komisi_hj1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_komisi_hj1ActionPerformed(evt);
            }
        });

        txt_komisi_hj2.setText("0");
        txt_komisi_hj2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_komisi_hj2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_komisi_hj2MouseClicked(evt);
            }
        });
        txt_komisi_hj2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_komisi_hj2ActionPerformed(evt);
            }
        });

        aktif.setBackground(new java.awt.Color(255, 153, 0));
        aktif.setSelected(true);
        aktif.setText("ACTIVE");

        jumlah.setBackground(new java.awt.Color(0, 0, 0));
        jumlah.setForeground(new java.awt.Color(255, 255, 0));
        jumlah.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jumlahMouseClicked(evt);
            }
        });

        txt_namabarang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_namabarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_namabarangMouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("TOP");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("Pre Order");

        txt_hitungan1.setText("0");
        txt_hitungan1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hitungan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hitungan1MouseClicked(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("Satuan 2");

        com_satuan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "BOX", "BTL", "DOS", "DRM", "DS", "DSN", "GDL", "GLN", "GN", "GR", "GRM", "GROUP", "GRS", "JRG", "KG", "KLG", "KODI", "KRG", "KTK", "LBR", "LJR", "LTR", "ML", "MTR", "ONS", "PACK", "PAIL", "PAK", "PCS", "PIL", "PRESS", "PSG", "PTI", "ROL", "SAK", "SET", "SLF", "SLP", " ", " " }));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setText("Hitungan 2");

        txt_hitungan2.setText("0");
        txt_hitungan2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_hitungan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_hitungan2MouseClicked(evt);
            }
        });

        com_satuan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "BOX", "BTL", "DOS", "DRM", "DS", "DSN", "GDL", "GLN", "GN", "GR", "GRM", "GROUP", "GRS", "JRG", "KG", "KLG", "KODI", "KRG", "KTK", "LBR", "LJR", "LTR", "ML", "MTR", "ONS", "PACK", "PAIL", "PAK", "PCS", "PIL", "PRESS", "PSG", "PTI", "ROL", "SAK", "SET", "SLF", "SLP", " ", " " }));

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel56.setText("Harga Jual 1");

        nonaktif.setBackground(new java.awt.Color(255, 153, 0));
        nonaktif.setText("DEACTIVE");
        nonaktif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nonaktifMouseClicked(evt);
            }
        });
        nonaktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonaktifActionPerformed(evt);
            }
        });

        com_kelompok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_kelompokActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel57.setText("Kode");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel58.setText("Nama");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setText("Kelompok");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setText("Satuan 1");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText("Hitungan 1");

        com_top.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_topActionPerformed(evt);
            }
        });

        txt_kodebarang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_kodebarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_kodebarangMouseClicked(evt);
            }
        });

        jTextField31.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jTextField31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField31MouseClicked(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("Harga Rata2");

        txt_harga_beli.setText("0");
        txt_harga_beli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_beliMouseClicked(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setText("Harga Beli");

        txt_harga_jual3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_harga_jual3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_harga_jual3MouseClicked(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel64.setText("Esc-Exit");
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        simpan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        simpan.setText("F12-Save");
        simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                simpanMouseClicked(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setText("Jumlah");

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel54.setText("Group");

        jCheckBox1.setText("Group");

        txt_denda_hj1.setText("0");
        txt_denda_hj1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_denda_hj1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_denda_hj1MouseClicked(evt);
            }
        });
        txt_denda_hj1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_denda_hj1ActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Denda Hj1");

        txt_denda_hj2.setText("0");
        txt_denda_hj2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_denda_hj2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_denda_hj2MouseClicked(evt);
            }
        });
        txt_denda_hj2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_denda_hj2ActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setText("Denda Hj2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(com_satuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(com_top, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(com_kelompok, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                        .addComponent(jTextField31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(com_satuan2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel48))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txt_harga_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_komisi_hj1)
                                                    .addComponent(txt_hitungan1)
                                                    .addComponent(txt_komisi_hj2)
                                                    .addComponent(txt_hitungan2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txt_hargarata2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jCheckBox1))
                                            .addGap(2, 2, 2))
                                        .addComponent(jumlah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aktif)
                        .addGap(18, 18, 18)
                        .addComponent(nonaktif)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel59)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel64))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_harga_jual1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_harga_jual3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_harga_jual2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel69)
                                            .addComponent(jLabel65)))))
                            .addComponent(jLabel52)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel55)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_denda_hj1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel61)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_denda_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
            .addComponent(jSeparator1)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_denda_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel61)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_denda_hj1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel55)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel48)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel49)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel54)
                                                .addGap(16, 16, 16)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel62)
                                                    .addComponent(com_satuan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(com_kelompok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(9, 9, 9)
                                                .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(com_top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel53))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel59)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel51)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel50)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel60)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(com_satuan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel52))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_harga_jual1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel63))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_harga_jual2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel65))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel69)
                                    .addComponent(txt_harga_jual3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel44)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(txt_komisi_hj2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_komisi_hj1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox1)
                                .addGap(5, 5, 5)
                                .addComponent(txt_hitungan1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_hitungan2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_hargarata2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_harga_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(aktif)
                            .addComponent(nonaktif))))
                .addGap(14, 14, 14)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 90, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(882, 561));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_harga_jual2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual2MouseClicked

    private void txt_harga_jual1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual1MouseClicked

    private void txt_hargarata2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hargarata2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargarata2MouseClicked

    private void txt_komisi_hj1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_komisi_hj1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj1MouseClicked

    private void txt_komisi_hj1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_komisi_hj1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj1ActionPerformed

    private void txt_komisi_hj2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_komisi_hj2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj2MouseClicked

    private void txt_komisi_hj2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_komisi_hj2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_komisi_hj2ActionPerformed

    private void jumlahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jumlahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahMouseClicked

    private void txt_namabarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namabarangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangMouseClicked

    private void txt_hitungan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hitungan1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hitungan1MouseClicked

    private void txt_hitungan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_hitungan2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hitungan2MouseClicked

    private void nonaktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonaktifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nonaktifActionPerformed

    private void com_topActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_topActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_topActionPerformed

    private void txt_kodebarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_kodebarangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarangMouseClicked

    private void jTextField31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField31MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31MouseClicked

    private void txt_harga_beliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_beliMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_beliMouseClicked

    private void txt_harga_jual3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_harga_jual3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga_jual3MouseClicked

    private void simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_simpanMouseClicked
                 if (!isEdit) {
            this.listBarang.setKode_barang(this.id);
            }
         this.listBarang.setNama_barang(txt_namabarang.getText());
         this.listBarang.setId_kelompok(com_kelompok.getSelectedItem().toString());
         this.listBarang.setId_top(com_top.getSelectedItem().toString());
         this.listBarang.setId_konversi(com_satuan1.getSelectedItem().toString());
         this.listBarang.setId_konversi(com_satuan2.getSelectedItem().toString());
         this.listBarang.setHarga_jual_1(Double.parseDouble(txt_harga_jual1.getText().toString()));
         this.listBarang.setHarga_jual_1(Double.parseDouble(txt_harga_jual2.getText().toString()));
         this.listBarang.setHarga_jual_1(Double.parseDouble(txt_harga_jual3.getText().toString()));
//         this.listBarang.setStatus(aktif.isSelected() ? 1 : 0);



//        this.dispose();
                                       
    }//GEN-LAST:event_simpanMouseClicked

    private void nonaktifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nonaktifMouseClicked
//        int deactive = JOptionPane.showConfirmDialog(null,"Apakah Stok di Ubah Menjadi 0 ?", "Konfimasi Perubahan Status" ,JOptionPane.OK_CANCEL_OPTION);
//        if(deactive == JOptionPane.OK_OPTION){
//        JOptionPane.showMessageDialog(null, "Data sudah ditolak.");
//        }
    }//GEN-LAST:event_nonaktifMouseClicked

    private void txt_denda_hj1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_denda_hj1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj1MouseClicked

    private void txt_denda_hj1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_denda_hj1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj1ActionPerformed

    private void txt_denda_hj2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_denda_hj2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj2MouseClicked

    private void txt_denda_hj2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_denda_hj2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_denda_hj2ActionPerformed

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
//         this.dispose();
    }//GEN-LAST:event_jLabel64MouseClicked

    private void com_kelompokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_kelompokActionPerformed
        
    }//GEN-LAST:event_com_kelompokActionPerformed

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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Barang_TambahEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Master_Barang_TambahEdit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton aktif;
    private javax.swing.JComboBox<String> com_kelompok;
    private javax.swing.JComboBox<String> com_satuan1;
    private javax.swing.JComboBox<String> com_satuan2;
    private javax.swing.JComboBox<String> com_top;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jumlah;
    private javax.swing.JRadioButton nonaktif;
    private javax.swing.JLabel simpan;
    private javax.swing.JTextField txt_denda_hj1;
    private javax.swing.JTextField txt_denda_hj2;
    private javax.swing.JTextField txt_harga_beli;
    private javax.swing.JTextField txt_harga_jual1;
    private javax.swing.JTextField txt_harga_jual2;
    private javax.swing.JTextField txt_harga_jual3;
    private javax.swing.JTextField txt_hargarata2;
    private javax.swing.JTextField txt_hitungan1;
    private javax.swing.JTextField txt_hitungan2;
    private javax.swing.JTextField txt_kodebarang;
    private javax.swing.JTextField txt_komisi_hj1;
    private javax.swing.JTextField txt_komisi_hj2;
    private javax.swing.JTextField txt_namabarang;
    // End of variables declaration//GEN-END:variables
}
