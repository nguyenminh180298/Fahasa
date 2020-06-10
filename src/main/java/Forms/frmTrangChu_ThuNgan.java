/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Class.ComboBoxItem;
import Database.Database;
import static Forms.frmDangNhap.createImageIcon;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class frmTrangChu_ThuNgan extends javax.swing.JFrame {

    Database db =new Database();
    Vector vtData_tsp=new Vector();
    Vector vtData_tlsp=new Vector();
    Vector vtData_tncc=new Vector();
    Vector vtData_tsptimkiemtheoten=new Vector();
    Vector vtData_tsptimkiemloai=new Vector();
    Vector vtData_tsptimkiemnhacc=new Vector();
    Vector vtData_tlsptimkiemtheoten=new Vector();
    Vector vtData_tncctimkiemtheoten=new Vector();
    /**
     * Creates new form frmTrangChu_Admin
     */
   
//-------------------------------TAB SẢN PHẨM----------------------------------------------------------
    public void loadtbsp(){
        Vector vtColumn_tsp=new Vector(); 
        Vector vtRow_tsp=new Vector(); 
        vtColumn_tsp.add("Mã sản phẩm");
        vtColumn_tsp.add("Tên sản phẩm");
        vtColumn_tsp.add("Giá");
        vtColumn_tsp.add("Mô tả");
        vtColumn_tsp.add("Hình");
        vtColumn_tsp.add("Loại sản phẩm");
        vtColumn_tsp.add("Nhà cung cấp");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select sp.MaSP,sp.TenSP,sp.Gia,sp.MoTa,sp.Hinh,loaisp.TenLoai,nhacungcap.TenNhaCC from sp inner join loaisp on loaisp.MaLoai=sp.MaLoai inner join nhacungcap on nhacungcap.MaNhaCC=sp.MaNhaCC where loaisp.MaLoai=sp.MaLoai && nhacungcap.MaNhaCC=sp.MaNhaCC");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tsp=new Vector();
                        vtRow_tsp.add(rss.getString(1));
                        vtRow_tsp.add(rss.getString(2));
                        vtRow_tsp.add(rss.getString(3));
                        vtRow_tsp.add(rss.getString(4)); 
                        vtRow_tsp.add(rss.getString(5));        
                        vtRow_tsp.add(rss.getString(6));        
                        vtRow_tsp.add(rss.getString(7));        
                        vtData_tsp.add(vtRow_tsp);                
                } 
            }tbsp.setModel(new DefaultTableModel(vtData_tsp, vtColumn_tsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsSP(int selectedIndex){
        Vector vtSelectRow_displayDetailsSP = (Vector)vtData_tsp.get(selectedIndex);
        String MaSP = (String)vtSelectRow_displayDetailsSP.get(0);
        String TenSP = (String)vtSelectRow_displayDetailsSP.get(1);
        String Gia = (String)vtSelectRow_displayDetailsSP.get(2);
        String MoTa = (String)vtSelectRow_displayDetailsSP.get(3);
        String Hinh = (String)vtSelectRow_displayDetailsSP.get(4);
        String Loai = (String)vtSelectRow_displayDetailsSP.get(5);
        String NhaCC = (String)vtSelectRow_displayDetailsSP.get(6);
        
        
        txttensp.setText(TenSP);
        txtgia.setText(Gia);
        txtmota.setText(MoTa);
        txthinhsp.setText(Hinh);
        comboboxloai.setSelectedIndex(GetIDForComboboxLoai(Loai)-1);
        comboboxnhacc.setSelectedIndex(GetIDForComboboxNhaCC(NhaCC)-1);      
        
        ImageIcon icon = createImageIcon(Hinh);
        lbHinhSP.setIcon(icon);
    }
    
    public int GetIDForComboboxLoai(String loai){
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaLoai from loaisp where TenLoai='"+loai+"'");
            //ResultSet rss_TenLoai=st.executeQuery("select TenLoai from loaisp");
            while (rss.next()) {
                int id = Integer.valueOf(rss.getString(1));
                System.out.println("id:"+id);
                return id;
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("id: exception");
            return 0;
        }
        System.out.println("id: outside tryctch");
        return 0;
    }
    
    public int GetIDForComboboxNhaCC(String nhacc){
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaNhaCC from nhacungcap where TenNhaCC='"+nhacc+"'");
            //ResultSet rss_TenLoai=st.executeQuery("select TenLoai from loaisp");
            while (rss.next()) {
                int id = Integer.valueOf(rss.getString(1));
                System.out.println("id:"+id);
                return id;
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("id: exception");
            return 0;
        }
        System.out.println("id: outside tryctch");
        return 0;
    }
    
    public void getCBboxLoai() {
        //kn với database mk dùng SQL SEVER 2012
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaLoai,TenLoai from loaisp");
            //ResultSet rss_TenLoai=st.executeQuery("select TenLoai from loaisp");
            while (rss.next()) {
                String id = rss.getString(1);
                String tenloai = rss.getString(2);
                //modelCombo.addElement(new Manufacturer(rs.getString("id"), rs.getString("Name")));
                comboboxloai.addItem(new ComboBoxItem(id, tenloai));
                //comboboxloai.addItem(rss.getString(1));
                cbloai.addItem(new ComboBoxItem(id, tenloai));
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getCBboxNhaCC() {
        //kn với database mk dùng SQL SEVER 2012
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaNhaCC,TenNhaCC from nhacungcap");
            while (rss.next()) {
                String id = rss.getString(1);
                String tennhacc = rss.getString(2);
                //modelCombo.addElement(new Manufacturer(rs.getString("id"), rs.getString("Name")));
                comboboxnhacc.addItem(new ComboBoxItem(id, tennhacc));
                //comboboxloai.addItem(rss.getString(1));
                cbnhacc.addItem(new ComboBoxItem(id, tennhacc));
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //tìm kiếm theo tên
    public void ShowTimKiemTheoTen(String ml){
        Vector vtColumn_tsp=new Vector(); 
        Vector vtRow_tsp=new Vector(); 
        vtColumn_tsp.add("Mã sản phẩm");
        vtColumn_tsp.add("Tên sản phẩm");
        vtColumn_tsp.add("Giá");
        vtColumn_tsp.add("Mô tả");
        vtColumn_tsp.add("Hình");
        vtColumn_tsp.add("Loại sản phẩm");
        vtColumn_tsp.add("Nhà cung cấp");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM sp where TenSP like '" + ml +"%' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tsp=new Vector();
                        vtRow_tsp.add(rss.getString(1));
                        vtRow_tsp.add(rss.getString(2));
                        vtRow_tsp.add(rss.getString(3));
                        vtRow_tsp.add(rss.getString(4)); 
                        vtRow_tsp.add(rss.getString(5));        
                        vtRow_tsp.add(rss.getString(6));        
                        vtRow_tsp.add(rss.getString(7));        
                        vtData_tsptimkiemtheoten.add(vtRow_tsp);                
                } 
            }tbsptukhoa.setModel(new DefaultTableModel(vtData_tsptimkiemtheoten, vtColumn_tsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //tìm kiếm theo loại
    public void ShowTimKiemTheoLoai(String ml){
        Vector vtColumn_tsp=new Vector(); 
        Vector vtRow_tsp=new Vector(); 
        vtColumn_tsp.add("Mã sản phẩm");
        vtColumn_tsp.add("Tên sản phẩm");
        vtColumn_tsp.add("Giá");
        vtColumn_tsp.add("Mô tả");
        vtColumn_tsp.add("Hình");
        vtColumn_tsp.add("Loại sản phẩm");
        vtColumn_tsp.add("Nhà cung cấp");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM sp S, loaisp L where L.MaLoai=S.MaLoai and L.MaLoai='" + ml +"' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tsp=new Vector();
                        vtRow_tsp.add(rss.getString(1));
                        vtRow_tsp.add(rss.getString(2));
                        vtRow_tsp.add(rss.getString(3));
                        vtRow_tsp.add(rss.getString(4)); 
                        vtRow_tsp.add(rss.getString(5));        
                        vtRow_tsp.add(rss.getString(6));        
                        vtRow_tsp.add(rss.getString(7));        
                        vtData_tsptimkiemloai.add(vtRow_tsp);                
                } 
            }tbsptukhoa.setModel(new DefaultTableModel(vtData_tsptimkiemloai, vtColumn_tsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    
    //tìm kiếm theo nhà cung cấp
    public void ShowTimKiemTheoNhaCungCap(String mncc){
        Vector vtColumn_tsp=new Vector(); 
        Vector vtRow_tsp=new Vector(); 
        vtColumn_tsp.add("Mã sản phẩm");
        vtColumn_tsp.add("Tên sản phẩm");
        vtColumn_tsp.add("Giá");
        vtColumn_tsp.add("Mô tả");
        vtColumn_tsp.add("Hình");
        vtColumn_tsp.add("Loại sản phẩm");
        vtColumn_tsp.add("Nhà cung cấp");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM sp S,nhacungcap N where N.MaNhaCC=S.MaNhaCC and N.MaNhaCC='" + mncc +"' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tsp=new Vector();
                        vtRow_tsp.add(rss.getString(1));
                        vtRow_tsp.add(rss.getString(2));
                        vtRow_tsp.add(rss.getString(3));
                        vtRow_tsp.add(rss.getString(4)); 
                        vtRow_tsp.add(rss.getString(5));        
                        vtRow_tsp.add(rss.getString(6));        
                        vtRow_tsp.add(rss.getString(7));        
                        vtData_tsptimkiemnhacc.add(vtRow_tsp);                
                } 
            }tbsptukhoa.setModel(new DefaultTableModel(vtData_tsptimkiemnhacc, vtColumn_tsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
//-------------------------------TAB SẢN PHẨM----------------------------------------------------------
    
    public frmTrangChu_ThuNgan() {
        initComponents();
        loadtbsp();
        getCBboxLoai();
        getCBboxNhaCC();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnnext1 = new javax.swing.JButton();
        btnlast1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txttukhoasp = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        spdssptk = new javax.swing.JScrollPane();
        tbsptukhoa = new javax.swing.JTable();
        spdssp = new javax.swing.JScrollPane();
        tbsp = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttensp = new javax.swing.JTextField();
        txtgia = new javax.swing.JTextField();
        txtmota = new javax.swing.JTextField();
        txthinhsp = new javax.swing.JTextField();
        comboboxloai = new javax.swing.JComboBox<>();
        comboboxnhacc = new javax.swing.JComboBox<>();
        btnfirst1 = new javax.swing.JButton();
        btnpre1 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        cbloai = new javax.swing.JComboBox<>();
        jPanel27 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        cbnhacc = new javax.swing.JComboBox<>();
        btntimtensp = new javax.swing.JButton();
        lbHinhSP = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        spdshoadon = new javax.swing.JScrollPane();
        tbhoadon = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txttenkhachhang = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtngaydat = new javax.swing.JTextField();
        txtnhanvien = new javax.swing.JTextField();
        btnnext4 = new javax.swing.JButton();
        btnlast4 = new javax.swing.JButton();
        btnpre4 = new javax.swing.JButton();
        btnfirst4 = new javax.swing.JButton();
        spcthd = new javax.swing.JScrollPane();
        tbcthd = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbtensp = new javax.swing.JComboBox<>();
        labelgia = new javax.swing.JLabel();
        txtsl = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btndangxuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1400, 300));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(987, 447));

        btnnext1.setText(">");

        btnlast1.setText(">>>");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Nhập tên");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttukhoasp, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(txttukhoasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Sản phẩm ứng với từ khóa");

        tbsptukhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá", "Mô tả", "Hình", "Loại sản phẩm", "Nhà cung cấp"
            }
        ));
        spdssptk.setViewportView(tbsptukhoa);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(spdssptk, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spdssptk, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        tbsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá", "Mô tả", "Hình", "Loại sản phẩm", "Nhà cung cấp"
            }
        ));
        tbsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbspMouseClicked(evt);
            }
        });
        tbsp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbspKeyPressed(evt);
            }
        });
        spdssp.setViewportView(tbsp);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Tên sản phẩm");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Giá");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Mô tả");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Hình");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Loại");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Nhà cung cấp");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmota)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboboxloai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txthinhsp, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(comboboxnhacc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txthinhsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(comboboxloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboboxnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        btnfirst1.setText("<<<");

        btnpre1.setText("<");

        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel33.setText("Chọn loại");

        cbloai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbloaiItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(cbloai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel33)
                .addComponent(cbloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel35.setText("Chọn nhà cung cấp");

        cbnhacc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbnhaccItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(cbnhacc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel35)
                .addComponent(cbnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btntimtensp.setText("Tìm");
        btntimtensp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimtenspActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst1)
                .addGap(18, 18, 18)
                .addComponent(btnpre1)
                .addGap(183, 183, 183)
                .addComponent(btnnext1)
                .addGap(18, 18, 18)
                .addComponent(btnlast1)
                .addGap(234, 234, 234))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(spdssp, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbHinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btntimtensp))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(spdssp, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbHinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntimtensp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext1)
                        .addComponent(btnlast1))
                    .addComponent(btnfirst1)
                    .addComponent(btnpre1))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        tbhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Khách hàng", "SDT", "Ngày đặt", "Nhân viên"
            }
        ));
        spdshoadon.setViewportView(tbhoadon);

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setText("Tên khách hàng");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setText("SDT");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel21.setText("Ngày đặt");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel22.setText("Nhân viên");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtngaydat, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttenkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtngaydat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnnext4.setText(">");

        btnlast4.setText(">>>");

        btnpre4.setText("<");

        btnfirst4.setText("<<<");

        tbcthd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "Đơn giá", "Số lượng"
            }
        ));
        spcthd.setViewportView(tbcthd);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Chi tiết hóa đơn");

        btnthem.setText("Thêm");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Tên sản phẩm");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("Giá");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setText("Số lượng");

        cbtensp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelgia.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelgia.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbtensp, 0, 289, Short.MAX_VALUE)
                    .addComponent(labelgia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsl))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbtensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(labelgia))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtsl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Danh sách hóa đơn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(btnfirst4)
                        .addGap(18, 18, 18)
                        .addComponent(btnpre4)
                        .addGap(183, 183, 183)
                        .addComponent(btnnext4)
                        .addGap(18, 18, 18)
                        .addComponent(btnlast4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spdshoadon)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnthem))
                            .addComponent(jLabel9))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(spcthd, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(spdshoadon, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addGap(93, 93, 93)
                        .addComponent(btnthem)
                        .addGap(100, 100, 100))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(spcthd, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext4)
                        .addComponent(btnlast4))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnfirst4)
                        .addComponent(btnpre4)))
                .addGap(13, 13, 13))
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel4);

        btndangxuat.setText("Đăng xuất");
        btndangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndangxuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btndangxuat)
                .addGap(0, 913, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btndangxuat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndangxuatActionPerformed
        try {
            // TODO add your handling code here:
            this.dispose();
            frmDangNhap frm = new frmDangNhap();
            frm.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(frmTrangChu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndangxuatActionPerformed

    private void tbspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbspMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbsp.getSelectedRow();
        displayDetailsSP(selectedRow);
    }//GEN-LAST:event_tbspMouseClicked

    private void tbspKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbspKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN){
            int selectedIndex = tbsp.getSelectedRow();
            displayDetailsSP(selectedIndex);
        }
    }//GEN-LAST:event_tbspKeyPressed

    private void btntimtenspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimtenspActionPerformed
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoasp.getText();
        ShowTimKiemTheoTen(ml);  
    }//GEN-LAST:event_btntimtenspActionPerformed

    private void cbloaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbloaiItemStateChanged
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String ml = cbloai.getItemAt(this.cbloai.getSelectedIndex()).get_id();
        ShowTimKiemTheoLoai(ml);    
    }//GEN-LAST:event_cbloaiItemStateChanged

    private void cbnhaccItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbnhaccItemStateChanged
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String mncc = cbnhacc.getItemAt(this.cbnhacc.getSelectedIndex()).get_id();
        ShowTimKiemTheoNhaCungCap(mncc);  
    }//GEN-LAST:event_cbnhaccItemStateChanged

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
            java.util.logging.Logger.getLogger(frmTrangChu_ThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_ThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_ThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_ThuNgan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTrangChu_ThuNgan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndangxuat;
    private javax.swing.JButton btnfirst1;
    private javax.swing.JButton btnfirst4;
    private javax.swing.JButton btnlast1;
    private javax.swing.JButton btnlast4;
    private javax.swing.JButton btnnext1;
    private javax.swing.JButton btnnext4;
    private javax.swing.JButton btnpre1;
    private javax.swing.JButton btnpre4;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimtensp;
    private javax.swing.JComboBox<ComboBoxItem> cbloai;
    private javax.swing.JComboBox<ComboBoxItem> cbnhacc;
    private javax.swing.JComboBox<String> cbtensp;
    private javax.swing.JComboBox<ComboBoxItem> comboboxloai;
    private javax.swing.JComboBox<ComboBoxItem> comboboxnhacc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelgia;
    private javax.swing.JLabel lbHinhSP;
    private javax.swing.JScrollPane spcthd;
    private javax.swing.JScrollPane spdshoadon;
    private javax.swing.JScrollPane spdssp;
    private javax.swing.JScrollPane spdssptk;
    private javax.swing.JTable tbcthd;
    private javax.swing.JTable tbhoadon;
    private javax.swing.JTable tbsp;
    private javax.swing.JTable tbsptukhoa;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txthinhsp;
    private javax.swing.JTextField txtmota;
    private javax.swing.JTextField txtngaydat;
    private javax.swing.JTextField txtnhanvien;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txtsl;
    private javax.swing.JTextField txttenkhachhang;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttukhoasp;
    // End of variables declaration//GEN-END:variables
}
