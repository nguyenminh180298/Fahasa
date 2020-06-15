/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Class.ComboBoxItem;
import Database.Database;
import static Forms.Utilities.TextCheck.hasOnlyChar;
import static Forms.Utilities.TextCheck.hasSpace;
import static Forms.Utilities.TextCheck.hasSpecial;
import static Forms.Utilities.TextCheck.hasVietnameseString;
import static Forms.frmDangNhap.createImageIcon;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class frmTrangChu_QLKho extends javax.swing.JFrame {
    
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
    
    //-------------------------------------------------TAB LOAI SP-------------------------------------
    public void loadtblsp(){
        Vector vtColumn_tlsp=new Vector(); 
        Vector vtRow_tlsp=new Vector(); 
        vtColumn_tlsp.add("Mã loại");
        vtColumn_tlsp.add("Tên lọai");
        vtColumn_tlsp.add("Hình");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select * from loaisp");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tlsp=new Vector();
                        vtRow_tlsp.add(rss.getString(1));
                        vtRow_tlsp.add(rss.getString(2));
                        vtRow_tlsp.add(rss.getString(3)); 
                        vtData_tlsp.add(vtRow_tlsp);                
                } 
            }tbloaisp.setModel(new DefaultTableModel(vtData_tlsp, vtColumn_tlsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsLSP(int selectedIndex){
        Vector vtSelectRow_displayDetailsLSP = (Vector)vtData_tlsp.get(selectedIndex);
        String MaLoai = (String)vtSelectRow_displayDetailsLSP.get(0);
        String TenLoai = (String)vtSelectRow_displayDetailsLSP.get(1);
        String Hinh = (String)vtSelectRow_displayDetailsLSP.get(2);
        
        txttenloai.setText(TenLoai);
        txthinhloai.setText(Hinh);
    }
    public void ShowTimKiemTheoTenLoai(String ml){
        Vector vtColumn_tlsp=new Vector(); 
        Vector vtRow_tlsp=new Vector(); 
        vtColumn_tlsp.add("Mã loại");
        vtColumn_tlsp.add("Tên loại");
        vtColumn_tlsp.add("Hình");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM loaisp where TenLoai like '" + ml +"%' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tlsp=new Vector();
                        vtRow_tlsp.add(rss.getString(1));
                        vtRow_tlsp.add(rss.getString(2));
                        vtRow_tlsp.add(rss.getString(3));   
                        vtData_tlsptimkiemtheoten.add(vtRow_tlsp);                
                } 
            }tbloaitukhoa.setModel(new DefaultTableModel(vtData_tlsptimkiemtheoten, vtColumn_tlsp));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    //----------------------------------------------------TAB NHA CUNG CAP------------------------------
    public void loadtbncc(){
        Vector vtColumn_tncc=new Vector(); 
        Vector vtRow_tncc=new Vector(); 
        vtColumn_tncc.add("Mã nhà cung cấp");
        vtColumn_tncc.add("Tên nhà cung cấp");
        vtColumn_tncc.add("Hình");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select * from nhacungcap");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tncc=new Vector();
                        vtRow_tncc.add(rss.getString(1));
                        vtRow_tncc.add(rss.getString(2));
                        vtRow_tncc.add(rss.getString(3));    
                        vtData_tncc.add(vtRow_tncc);                
                } 
            }tbnhacc.setModel(new DefaultTableModel(vtData_tncc, vtColumn_tncc));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsNCC(int selectedIndex){
        Vector vtSelectRow_displayDetailsNCC = (Vector)vtData_tncc.get(selectedIndex);
        String MaNhaCC = (String)vtSelectRow_displayDetailsNCC.get(0);
        String TenNhaCC = (String)vtSelectRow_displayDetailsNCC.get(1);
        String Hinh = (String)vtSelectRow_displayDetailsNCC.get(2);
        
        txttennhacc.setText(TenNhaCC);
        txthinhnhacc.setText(Hinh);
        
    }
    
    //tìm kiếm theo tên
    public void ShowTimKiemTheoTenNhaCC(String ml){
        Vector vtColumn_tncc=new Vector(); 
        Vector vtRow_tncc=new Vector(); 
        vtColumn_tncc.add("Mã nhà cung cấp");
        vtColumn_tncc.add("Tên nhà cung cấp");
        vtColumn_tncc.add("Hình");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM nhacungcap where TenNhaCC like '" + ml +"%' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tncc=new Vector();
                        vtRow_tncc.add(rss.getString(1));
                        vtRow_tncc.add(rss.getString(2));
                        vtRow_tncc.add(rss.getString(3));   
                        vtData_tncctimkiemtheoten.add(vtRow_tncc);                
                } 
            }tbnhacctukhoa.setModel(new DefaultTableModel(vtData_tncctimkiemtheoten, vtColumn_tncc));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    /**
     * Creates new form frmTrangChu_QLKho
     */
    public frmTrangChu_QLKho() {
        initComponents();
        loadtbsp();
        loadtblsp();
        loadtbncc();
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
        btnchon = new javax.swing.JButton();
        btnthemsp = new javax.swing.JButton();
        btnsuasp = new javax.swing.JButton();
        btnxoasp = new javax.swing.JButton();
        btnfirst1 = new javax.swing.JButton();
        btnpre1 = new javax.swing.JButton();
        btnnext1 = new javax.swing.JButton();
        btnlast1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        spdssptk = new javax.swing.JScrollPane();
        tbsptukhoa = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        cbnhacc = new javax.swing.JComboBox<>();
        jPanel25 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        cbloai = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txttukhoasp = new javax.swing.JTextField();
        btntimtensp = new javax.swing.JButton();
        lbHinhSP = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnthemloai = new javax.swing.JButton();
        btnnext4 = new javax.swing.JButton();
        btnsualoai = new javax.swing.JButton();
        btnlast4 = new javax.swing.JButton();
        btnxoaloai = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txttukhoaloai = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        spdsloaitk1 = new javax.swing.JScrollPane();
        tbloaitukhoa = new javax.swing.JTable();
        spdsloai1 = new javax.swing.JScrollPane();
        tbloaisp = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txttenloai = new javax.swing.JTextField();
        txthinhloai = new javax.swing.JTextField();
        btnchonloai = new javax.swing.JButton();
        btnfirst4 = new javax.swing.JButton();
        btnpre4 = new javax.swing.JButton();
        btntimloaisp = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        spdsncc = new javax.swing.JScrollPane();
        tbnhacc = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txttennhacc = new javax.swing.JTextField();
        txthinhnhacc = new javax.swing.JTextField();
        btnchonnhacc = new javax.swing.JButton();
        btnthemnhacc = new javax.swing.JButton();
        btnsuanhacc = new javax.swing.JButton();
        btnxoanhacc = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txttukhoancc = new javax.swing.JTextField();
        btnnext3 = new javax.swing.JButton();
        btnlast3 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        spdsnhacctk = new javax.swing.JScrollPane();
        tbnhacctukhoa = new javax.swing.JTable();
        btnpre3 = new javax.swing.JButton();
        btnfirst3 = new javax.swing.JButton();
        btntimnhacc = new javax.swing.JButton();
        btndangxuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1400, 300));

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

        btnchon.setText("Chon");
        btnchon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboboxloai, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(btnchon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txthinhsp))
                            .addComponent(txtgia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(txtmota, javax.swing.GroupLayout.Alignment.LEADING)
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
                .addGap(8, 8, 8)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txthinhsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnchon))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(comboboxloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboboxnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        btnthemsp.setText("Thêm");
        btnthemsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemspActionPerformed(evt);
            }
        });

        btnsuasp.setText("Sửa");
        btnsuasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaspActionPerformed(evt);
            }
        });

        btnxoasp.setText("Xóa");
        btnxoasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaspActionPerformed(evt);
            }
        });

        btnfirst1.setText("<<<");

        btnpre1.setText("<");

        btnnext1.setText(">");

        btnlast1.setText(">>>");

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

        btntimtensp.setText("Tìm");
        btntimtensp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimtenspActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst1)
                .addGap(18, 18, 18)
                .addComponent(btnpre1)
                .addGap(183, 183, 183)
                .addComponent(btnnext1)
                .addGap(18, 18, 18)
                .addComponent(btnlast1)
                .addGap(234, 234, 234))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spdssp, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbHinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsuasp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoasp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btntimtensp))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spdssp, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbHinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnthemsp))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btntimtensp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnsuasp)
                                .addGap(20, 20, 20)
                                .addComponent(btnxoasp))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext1)
                        .addComponent(btnlast1))
                    .addComponent(btnfirst1)
                    .addComponent(btnpre1))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        btnthemloai.setText("Thêm");
        btnthemloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemloaiActionPerformed(evt);
            }
        });

        btnnext4.setText(">");

        btnsualoai.setText("Sửa");
        btnsualoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsualoaiActionPerformed(evt);
            }
        });

        btnlast4.setText(">>>");

        btnxoaloai.setText("Xóa");
        btnxoaloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaloaiActionPerformed(evt);
            }
        });

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setText("Nhập từ khóa");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(txttukhoaloai, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txttukhoaloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setText("Loại sản phẩm ứng với từ khóa");

        tbloaitukhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại", "Tên loại", "Hình"
            }
        ));
        spdsloaitk1.setViewportView(tbloaitukhoa);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spdsloaitk1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spdsloaitk1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbloaisp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại", "Tên loại", "Hình"
            }
        ));
        tbloaisp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbloaispMouseClicked(evt);
            }
        });
        spdsloai1.setViewportView(tbloaisp);

        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setText("Tên loại");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel20.setText("Hình");

        btnchonloai.setText("Chon");
        btnchonloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchonloaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnchonloai, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthinhloai))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(txttenloai, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttenloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(30, 30, 30)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthinhloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(btnchonloai))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        btnfirst4.setText("<<<");

        btnpre4.setText("<");

        btntimloaisp.setText("Tìm");
        btntimloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimloaispActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spdsloai1, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemloai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsualoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoaloai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btntimloaisp)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst4)
                .addGap(18, 18, 18)
                .addComponent(btnpre4)
                .addGap(183, 183, 183)
                .addComponent(btnnext4)
                .addGap(18, 18, 18)
                .addComponent(btnlast4)
                .addGap(231, 231, 231))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spdsloai1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(btntimloaisp)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnthemloai)
                        .addGap(18, 18, 18)
                        .addComponent(btnsualoai)
                        .addGap(20, 20, 20)
                        .addComponent(btnxoaloai)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext4)
                        .addComponent(btnlast4))
                    .addComponent(btnfirst4)
                    .addComponent(btnpre4))
                .addGap(75, 75, 75))
        );

        jTabbedPane1.addTab("Loại sản phẩm", jPanel4);

        tbnhacc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhà cung cấp", "Tên nhà cung cấp", "Hình"
            }
        ));
        tbnhacc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbnhaccMouseClicked(evt);
            }
        });
        spdsncc.setViewportView(tbnhacc);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("Tên nhà cung cấp");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setText("Hình");

        btnchonnhacc.setText("Chon");
        btnchonnhacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchonnhaccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txttennhacc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(btnchonnhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthinhnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttennhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(30, 30, 30)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthinhnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(btnchonnhacc))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        btnthemnhacc.setText("Thêm");
        btnthemnhacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemnhaccActionPerformed(evt);
            }
        });

        btnsuanhacc.setText("Sửa");
        btnsuanhacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuanhaccActionPerformed(evt);
            }
        });

        btnxoanhacc.setText("Xóa");
        btnxoanhacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoanhaccActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setText("Nhập từ khóa");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(txttukhoancc, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txttukhoancc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnnext3.setText(">");

        btnlast3.setText(">>>");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setText("Nhà cung cấp ứng với từ khóa");

        tbnhacctukhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhà cung cấp", "Tên nhà cung cấp", "Hình"
            }
        ));
        spdsnhacctk.setViewportView(tbnhacctukhoa);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spdsnhacctk, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spdsnhacctk, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnpre3.setText("<");

        btnfirst3.setText("<<<");

        btntimnhacc.setText("Tìm");
        btntimnhacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimnhaccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spdsncc, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemnhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsuanhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoanhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btntimnhacc)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst3)
                .addGap(18, 18, 18)
                .addComponent(btnpre3)
                .addGap(183, 183, 183)
                .addComponent(btnnext3)
                .addGap(18, 18, 18)
                .addComponent(btnlast3)
                .addGap(241, 241, 241))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(spdsncc, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(btnthemnhacc)
                        .addGap(18, 18, 18)
                        .addComponent(btnsuanhacc)
                        .addGap(20, 20, 20)
                        .addComponent(btnxoanhacc))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(btntimnhacc)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext3)
                        .addComponent(btnlast3))
                    .addComponent(btnfirst3)
                    .addComponent(btnpre3))
                .addGap(97, 97, 97))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Nhà cung cấp", jPanel3);

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
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btndangxuat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void tbloaispMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbloaispMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbloaisp.getSelectedRow();
        displayDetailsLSP(selectedRow);
    }//GEN-LAST:event_tbloaispMouseClicked

    private void tbspKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbspKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN){
            int selectedIndex = tbsp.getSelectedRow();
            displayDetailsLSP(selectedIndex);
        }
    }//GEN-LAST:event_tbspKeyPressed

    private void tbspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbspMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbsp.getSelectedRow();
        displayDetailsSP(selectedRow);
    }//GEN-LAST:event_tbspMouseClicked

    private void btnthemspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemspActionPerformed
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into sp values(?,?,?,?,?,?,?)");
            ps.setInt(1,tbsp.getRowCount()+1);
            ps.setString(2, txttensp.getText());
            ps.setString(3, txtgia.getText());
            ps.setString(4, txtmota.getText());
            ps.setString(5, txthinhsp.getText());
            ps.setString(6, comboboxloai.getItemAt(this.comboboxloai.getSelectedIndex()).get_id());
            ps.setString(7, comboboxnhacc.getItemAt(this.comboboxnhacc.getSelectedIndex()).get_id());
            if(txttensp.getText().equals("") || txtgia.getText().equals("") || txtmota.getText().equals("") || txthinhsp.getText().equals("") || hasVietnameseString(txtgia.getText()) || hasSpecial(txtgia.getText()) || hasSpace(txtgia.getText()) || hasOnlyChar(txtgia.getText())){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống, giá không được khác số");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbsp.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbsp();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                } 
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnthemspActionPerformed

    private void btnsuaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaspActionPerformed
        try{
            PreparedStatement ps = db.con.prepareStatement("update sp set TenSP=?,Gia=?,MoTa=?,Hinh=?,MaLoai=?,MaNhaCC=? where MaSP=?");
            ps.setString(7, tbsp.getValueAt(tbsp.getSelectedRow(), 0).toString());
            ps.setString(1, txttensp.getText());
            ps.setString(2, txtgia.getText());
            ps.setString(3, txtmota.getText());
            ps.setString(4, txthinhsp.getText());
            ps.setString(5, comboboxloai.getItemAt(this.comboboxloai.getSelectedIndex()).get_id());
            ps.setString(6, comboboxnhacc.getItemAt(this.comboboxnhacc.getSelectedIndex()).get_id());
            if(txttensp.getText().equals("") || txtgia.getText().equals("") || txtmota.getText().equals("") || txthinhsp.getText().equals("") || hasVietnameseString(txtgia.getText()) || hasSpecial(txtgia.getText()) || hasSpace(txtgia.getText()) || hasOnlyChar(txtgia.getText())){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống, riêng giá hãy chắc chắn rằng bạn nhập số");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbsp.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbsp();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnsuaspActionPerformed

    private void btnxoaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaspActionPerformed
         try{
            PreparedStatement ps = db.con.prepareStatement("Delete from sp where MaSP=?");
            ps.setString(1, tbsp.getValueAt(tbsp.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(this, "Xóa sản phẩm này?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps.executeUpdate();
                DefaultTableModel dtm = (DefaultTableModel) tbsp.getModel();
                dtm.setRowCount(0);
                //load lại table
                loadtbsp();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnxoaspActionPerformed

    private void btntimnhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimnhaccActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tbnhacctukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoancc.getText();
        ShowTimKiemTheoTenNhaCC(ml);  
    }//GEN-LAST:event_btntimnhaccActionPerformed

    private void btnthemnhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemnhaccActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into nhacungcap values(?,?,?)");
            ps.setInt(1,tbnhacc.getRowCount()+1);
            ps.setString(2, txttennhacc.getText());
            ps.setString(3, txthinhnhacc.getText());
            if(txttennhacc.getText().equals("") || txthinhnhacc.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbnhacc.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbncc();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnthemnhaccActionPerformed

    private void btnsuanhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuanhaccActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("update nhacungcap set TenNhaCC=?,Hinh=? where MaNhaCC=?");
            ps.setString(3, tbnhacc.getValueAt(tbnhacc.getSelectedRow(), 0).toString());
            ps.setString(1, txttennhacc.getText());
            ps.setString(2, txthinhnhacc.getText());
            if(txttennhacc.getText().equals("") || txthinhnhacc.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbnhacc.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbncc();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }  
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnsuanhaccActionPerformed

    private void btnxoanhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoanhaccActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("Delete from nhacungcap where MaNhaCC=?");
            ps.setString(1, tbnhacc.getValueAt(tbnhacc.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(this, "Xóa nhà cung cấp này?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps.executeUpdate();
                DefaultTableModel dtm = (DefaultTableModel) tbnhacc.getModel();
                dtm.setRowCount(0);
                //load lại table
                loadtbncc();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnxoanhaccActionPerformed

    private void btntimtenspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimtenspActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoasp.getText();
        ShowTimKiemTheoTen(ml);  
    }//GEN-LAST:event_btntimtenspActionPerformed

    private void btntimloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimloaispActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) tbloaitukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoaloai.getText();
        ShowTimKiemTheoTenLoai(ml);  
    }//GEN-LAST:event_btntimloaispActionPerformed
    
    private void btnchonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonActionPerformed
        // TODO add your handling code here:
        showFileChooserDemo();
    }//GEN-LAST:event_btnchonActionPerformed
    private void showFileChooserLoai(){
      final JFileChooser  fileDialog = new JFileChooser();
      int returnVal = fileDialog.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               java.io.File file = fileDialog.getSelectedFile();
               txthinhloai.setText(file.getPath().replaceAll("/", "\\"));
            }
            else{
               txthinhloai.setText("");           
            }      
    }
    private void btnchonloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonloaiActionPerformed
        // TODO add your handling code here:
        showFileChooserLoai();
    }//GEN-LAST:event_btnchonloaiActionPerformed

    private void btnthemloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemloaiActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into loaisp values(?,?,?)");
            ps.setInt(1,tbloaisp.getRowCount()+1);
            ps.setString(2, txttenloai.getText());
            ps.setString(3, txthinhloai.getText());
            if(txttenloai.getText().equals("") || txthinhloai.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbloaisp.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtblsp();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnthemloaiActionPerformed

    private void btnsualoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsualoaiActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("update loaisp set TenLoai=?,Hinh=? where MaLoai=?");
            ps.setString(3, tbloaisp.getValueAt(tbloaisp.getSelectedRow(), 0).toString());
            ps.setString(1, txttenloai.getText());
            ps.setString(2, txthinhloai.getText());
            if(txttenloai.getText().equals("") || txthinhloai.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbloaisp.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtblsp();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnsualoaiActionPerformed

    private void btnxoaloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaloaiActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("Delete from loaisp where MaLoai=?");
            ps.setString(1, tbloaisp.getValueAt(tbloaisp.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(this, "Xóa loại sản phẩm này?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps.executeUpdate();
                DefaultTableModel dtm = (DefaultTableModel) tbloaisp.getModel();
                dtm.setRowCount(0);
                //load lại table
                loadtblsp();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnxoaloaiActionPerformed
    private void showFileChooserNhaCC(){
      final JFileChooser  fileDialog = new JFileChooser();
      int returnVal = fileDialog.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               java.io.File file = fileDialog.getSelectedFile();
               txthinhnhacc.setText(file.getPath().replaceAll("/", "\\"));
            }
            else{
               txthinhnhacc.setText("");           
            }      
    }
    private void btnchonnhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonnhaccActionPerformed
        // TODO add your handling code here:
        showFileChooserNhaCC(); 
    }//GEN-LAST:event_btnchonnhaccActionPerformed

    private void cbloaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbloaiItemStateChanged
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String ml = cbloai.getItemAt(this.cbloai.getSelectedIndex()).get_id();
        ShowTimKiemTheoLoai(ml);   
    }//GEN-LAST:event_cbloaiItemStateChanged

    private void cbnhaccItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbnhaccItemStateChanged
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String mncc = cbnhacc.getItemAt(this.cbnhacc.getSelectedIndex()).get_id();
        ShowTimKiemTheoNhaCungCap(mncc);   
    }//GEN-LAST:event_cbnhaccItemStateChanged

    private void tbnhaccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnhaccMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbnhacc.getSelectedRow();
        displayDetailsNCC(selectedRow);
    }//GEN-LAST:event_tbnhaccMouseClicked
    private void showFileChooserDemo(){
      final JFileChooser  fileDialog = new JFileChooser();
      int returnVal = fileDialog.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               java.io.File file = fileDialog.getSelectedFile();
               txthinhsp.setText(file.getPath().replaceAll("/", "\\"));
            }
            else{
               txthinhsp.setText("" );           
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
            java.util.logging.Logger.getLogger(frmTrangChu_QLKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_QLKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_QLKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_QLKho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTrangChu_QLKho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnchon;
    private javax.swing.JButton btnchonloai;
    private javax.swing.JButton btnchonnhacc;
    private javax.swing.JButton btndangxuat;
    private javax.swing.JButton btnfirst1;
    private javax.swing.JButton btnfirst3;
    private javax.swing.JButton btnfirst4;
    private javax.swing.JButton btnlast1;
    private javax.swing.JButton btnlast3;
    private javax.swing.JButton btnlast4;
    private javax.swing.JButton btnnext1;
    private javax.swing.JButton btnnext3;
    private javax.swing.JButton btnnext4;
    private javax.swing.JButton btnpre1;
    private javax.swing.JButton btnpre3;
    private javax.swing.JButton btnpre4;
    private javax.swing.JButton btnsualoai;
    private javax.swing.JButton btnsuanhacc;
    private javax.swing.JButton btnsuasp;
    private javax.swing.JButton btnthemloai;
    private javax.swing.JButton btnthemnhacc;
    private javax.swing.JButton btnthemsp;
    private javax.swing.JButton btntimloaisp;
    private javax.swing.JButton btntimnhacc;
    private javax.swing.JButton btntimtensp;
    private javax.swing.JButton btnxoaloai;
    private javax.swing.JButton btnxoanhacc;
    private javax.swing.JButton btnxoasp;
    private javax.swing.JComboBox<ComboBoxItem> cbloai;
    private javax.swing.JComboBox<ComboBoxItem> cbnhacc;
    private javax.swing.JComboBox<ComboBoxItem> comboboxloai;
    private javax.swing.JComboBox<ComboBoxItem> comboboxnhacc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbHinhSP;
    private javax.swing.JScrollPane spdsloai1;
    private javax.swing.JScrollPane spdsloaitk1;
    private javax.swing.JScrollPane spdsncc;
    private javax.swing.JScrollPane spdsnhacctk;
    private javax.swing.JScrollPane spdssp;
    private javax.swing.JScrollPane spdssptk;
    private javax.swing.JTable tbloaisp;
    private javax.swing.JTable tbloaitukhoa;
    private javax.swing.JTable tbnhacc;
    private javax.swing.JTable tbnhacctukhoa;
    private javax.swing.JTable tbsp;
    private javax.swing.JTable tbsptukhoa;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txthinhloai;
    private javax.swing.JTextField txthinhnhacc;
    private javax.swing.JTextField txthinhsp;
    private javax.swing.JTextField txtmota;
    private javax.swing.JTextField txttenloai;
    private javax.swing.JTextField txttennhacc;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttukhoaloai;
    private javax.swing.JTextField txttukhoancc;
    private javax.swing.JTextField txttukhoasp;
    // End of variables declaration//GEN-END:variables
}
