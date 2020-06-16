/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Class.ComboBoxItem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.Database;
import static Forms.frmDangNhap.createImageIcon;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Administrator
 */
public class frmTrangChu_KeToan extends javax.swing.JFrame {

    Database db =new Database();
    Vector vtData_tsp=new Vector();
    Vector vtData_tsptimkiemtheoten=new Vector();
    Vector vtData_tsptimkiemloai=new Vector();
    Vector vtData_tsptimkiemnhacc=new Vector();
    private CategoryDataset dataset;
    /**
     * Creates new form frmTrangChu_KeToan
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
    
    
    public frmTrangChu_KeToan() {
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
        btntimsptheoloai = new javax.swing.JButton();
        btntimsptheoncc = new javax.swing.JButton();
        lbHinhSP = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        pnlReport = new javax.swing.JPanel();
        btndangxuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1400, 300));

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

        btntimsptheoloai.setText("Tìm");

        btntimsptheoncc.setText("Tìm");

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
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btntimtensp)
                                    .addComponent(btntimsptheoloai)
                                    .addComponent(btntimsptheoncc)))
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntimsptheoloai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntimsptheoncc))
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
            .addGap(0, 972, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setText("Tìm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pnlReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 18))); // NOI18N
        pnlReport.setLayout(new javax.swing.BoxLayout(pnlReport, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlReport, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlReport, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanel2.add(jPanel4);

        jTabbedPane1.addTab("Thống kê", jPanel2);

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
                .addGap(0, 894, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btndangxuat)
                .addGap(0, 527, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 22, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void btntimtenspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimtenspActionPerformed
        // TODO add your handling code here:
         //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbsptukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoasp.getText();
        ShowTimKiemTheoTen(ml);  
    }//GEN-LAST:event_btntimtenspActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String January = "0";
        
        try {
            Statement st = db.con.createStatement();
          
            ResultSet Jan = st.executeQuery("SELECT TongTien from hoadon WHERE MONTH(NgayDat)=6");
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            /*while(Jan.next()) {
                //January = Jan.getString(1) == null ? "0" : Jan.getString(1);
                
            } */
            if(Jan!=null){
                System.out.println(""+Jan.getString(1));
            }
            dataset.setValue(Integer.parseInt(Jan.getString(1)),"Tổng Doanh Thu","Tháng 1");
            
            
            
           
            /*dataset.setValue(14,"Tổng Doanh Thu","Tháng 1");
            dataset.setValue(12,"Tổng Doanh Thu","Tháng 2");s
            dataset.setValue(14,"Tổng Doanh Thu","Tháng 3");
            dataset.setValue(42,"Tổng Doanh Thu","Tháng 4");
            dataset.setValue(21,"Tổng Doanh Thu","Tháng 5");
            dataset.setValue(23,"Tổng Doanh Thu", "Tháng 6");
            dataset.setValue(11,"Tổng Doanh Thu","Tháng 7");
            dataset.setValue(12,"Tổng Doanh Thu","Tháng 8");
            dataset.setValue(21,"Tổng Doanh Thu","Tháng 9");
            dataset.setValue(31,"Tổng Doanh Thu","Tháng 10");
            dataset.setValue(12,"Tổng Doanh Thu","Tháng 11");
            dataset.setValue(34,"Tổng Doanh Thu","Tháng 12");*/

            JFreeChart jchart = ChartFactory.createBarChart("Thống Kê Doanh Thu", "Năm 2020", "Doanh Thu", dataset, PlotOrientation.VERTICAL, true , true , false);

            CategoryPlot plot = jchart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.black);

            ChartPanel chartPanel = new ChartPanel(jchart);

            ChartFrame chartFrm = new ChartFrame("Thống Kê Doanh Thu", jchart, true);
            chartFrm.setVisible(true);
            chartFrm.setSize(500, 400);

            pnlReport.removeAll();
            pnlReport.add(chartPanel);
            pnlReport.updateUI();
            
        } catch (SQLException ex) {
            System.out.println("Loi: "  + ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(frmTrangChu_KeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_KeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_KeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_KeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTrangChu_KeToan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndangxuat;
    private javax.swing.JButton btnfirst1;
    private javax.swing.JButton btnlast1;
    private javax.swing.JButton btnnext1;
    private javax.swing.JButton btnpre1;
    private javax.swing.JButton btntimsptheoloai;
    private javax.swing.JButton btntimsptheoncc;
    private javax.swing.JButton btntimtensp;
    private javax.swing.JComboBox<ComboBoxItem> cbloai;
    private javax.swing.JComboBox<ComboBoxItem> cbnhacc;
    private javax.swing.JComboBox<ComboBoxItem> comboboxloai;
    private javax.swing.JComboBox<ComboBoxItem> comboboxnhacc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbHinhSP;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JScrollPane spdssp;
    private javax.swing.JScrollPane spdssptk;
    private javax.swing.JTable tbsp;
    private javax.swing.JTable tbsptukhoa;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txthinhsp;
    private javax.swing.JTextField txtmota;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttukhoasp;
    // End of variables declaration//GEN-END:variables
}
