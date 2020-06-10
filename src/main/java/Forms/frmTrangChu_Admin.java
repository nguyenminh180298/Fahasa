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
import Forms.Utilities.TextCheck;
import static Forms.frmDangNhap.createImageIcon;
import static Forms.Utilities.TextCheck.hasOnlyNumber;
import static Forms.Utilities.TextCheck.hasVietnameseString;
import static Forms.Utilities.TextCheck.hasSpace;
import static Forms.Utilities.TextCheck.hasSpecial;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class frmTrangChu_Admin extends javax.swing.JFrame {
    
    Database db = new Database();
    //bảng chính
    Vector vtData_tsp=new Vector();
    Vector vtData_tlsp=new Vector();
    Vector vtData_tncc=new Vector();
    Vector vtData_tnv=new Vector();
    Vector vtData_ttk=new Vector();
    Vector vtData_thd=new Vector();
    //bảng tìm kiếm
    Vector vtData_tsptimkiemtheoten=new Vector();
    Vector vtData_tsptimkiemloai=new Vector();
    Vector vtData_tsptimkiemnhacc=new Vector();
    Vector vtData_tlsptimkiemtheoten=new Vector();
    Vector vtData_tncctimkiemtheoten=new Vector();
    Vector vtData_tnvtimkiemtheochucvu=new Vector();
    Vector vtData_tnvtimkiemtheoten=new Vector();
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
            ResultSet rss=st.executeQuery("SELECT * FROM sp where TenSP like '%" + ml +"%' ");
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
    
    
//---------------------------------------------TAB LOẠI SẢN PHẨM----------------------------------------------------
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
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsLSP(int selectedIndex){
        Vector vtSelectRow_displayDetailsLSP = (Vector)vtData_tlsp.get(selectedIndex);
        String MaLoai = (String)vtSelectRow_displayDetailsLSP.get(0);
        String TenLoai = (String)vtSelectRow_displayDetailsLSP.get(1);
        String Hinh = (String)vtSelectRow_displayDetailsLSP.get(2);
        
        txttenloai.setText(TenLoai);
        txthinhloai.setText(Hinh);
        
        ImageIcon icon = createImageIcon(Hinh);
        lbHinhLoai.setIcon(icon);
    }
    
    //tìm kiếm theo tên
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
    
//--------------------------------------------------TAB NHÀ CUNG CẤP-----------------------------------------------------
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
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsNCC(int selectedIndex){
        Vector vtSelectRow_displayDetailsNCC = (Vector)vtData_tncc.get(selectedIndex);
        String MaNhaCC = (String)vtSelectRow_displayDetailsNCC.get(0);
        String TenNhaCC = (String)vtSelectRow_displayDetailsNCC.get(1);
        String Hinh = (String)vtSelectRow_displayDetailsNCC.get(2);
        
        txttennhacc.setText(TenNhaCC);
        txthinhnhacc.setText(Hinh);
        
        ImageIcon icon = createImageIcon(Hinh);
        lbHinhNhaCC.setIcon(icon);
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
    
//-------------------------------TAB NHÂN VIÊN----------------------------------------------------------
    public void loadtbnv(){
        Vector vtColumn_tnv=new Vector(); 
        Vector vtRow_tnv=new Vector(); 
        vtColumn_tnv.add("Mã nhân viên");
        vtColumn_tnv.add("Tên nhân viên");
        vtColumn_tnv.add("SDT");
        vtColumn_tnv.add("Địa chỉ");
        vtColumn_tnv.add("Chức vụ");
        vtColumn_tnv.add("Email");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select nguoidung.MaNV,nguoidung.TenNV,nguoidung.SDT,nguoidung.DiaChi,chucvu.ChucVu, nguoidung.Email from nguoidung inner join chucvu on chucvu.VaiTro=nguoidung.VaiTro where chucvu.VaiTro=nguoidung.VaiTro");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tnv=new Vector();
                        vtRow_tnv.add(rss.getString(1));
                        vtRow_tnv.add(rss.getString(2));
                        vtRow_tnv.add(rss.getString(3));
                        vtRow_tnv.add(rss.getString(4)); 
                        vtRow_tnv.add(rss.getString(5));     
                        vtRow_tnv.add(rss.getString(6)); 
                        vtData_tnv.add(vtRow_tnv);                
                } 
            }tbnhanvien.setModel(new DefaultTableModel(vtData_tnv, vtColumn_tnv));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsNV(int selectedIndex){
        Vector vtSelectRow_displayDetailsNV = (Vector)vtData_tnv.get(selectedIndex);
        String MaNV = (String)vtSelectRow_displayDetailsNV.get(0);
        String TenNV = (String)vtSelectRow_displayDetailsNV.get(1);
        String SDT = (String)vtSelectRow_displayDetailsNV.get(2);
        String DiaChi = (String)vtSelectRow_displayDetailsNV.get(3);
        String VaiTro = (String)vtSelectRow_displayDetailsNV.get(4);
        String Email = (String)vtSelectRow_displayDetailsNV.get(5);
        
        
        txttennv.setText(TenNV);
        txtsdtnv.setText(SDT);
        txtdiachi.setText(DiaChi);
        comboboxchucvu.setSelectedIndex(GetIDForComboboxChucVu(VaiTro)-1);
        txtemail.setText(Email);   
    }
    
    public int GetIDForComboboxChucVu(String chucvu){
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select VaiTro from chucvu where ChucVu='"+chucvu+"'");
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
    
    public void getCBboxChucVu() {
        //kn với database mk dùng SQL SEVER 2012
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select VaiTro,ChucVu from chucvu");
            //ResultSet rss_TenLoai=st.executeQuery("select TenLoai from loaisp");
            while (rss.next()) {
                String id = rss.getString(1);
                String tenloai = rss.getString(2);
                //modelCombo.addElement(new Manufacturer(rs.getString("id"), rs.getString("Name")));
                comboboxchucvu.addItem(new ComboBoxItem(id, tenloai));
                //comboboxloai.addItem(rss.getString(1));
                cbchucvu.addItem(new ComboBoxItem(id, tenloai));
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //tìm kiếm theo chuc vu
    public void ShowTimKiemTheoChucVu(String ml){
        Vector vtColumn_tnv=new Vector(); 
        Vector vtRow_tnv=new Vector(); 
        vtColumn_tnv.add("Mã nhân viên");
        vtColumn_tnv.add("Tên nhân viên");
        vtColumn_tnv.add("SDT");
        vtColumn_tnv.add("Địa chỉ");
        vtColumn_tnv.add("Chức vụ");
        vtColumn_tnv.add("Email");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM nguoidung n, chucvu c where n.VaiTro=c.VaiTro and c.VaiTro='" + ml +"' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tnv=new Vector();
                        vtRow_tnv.add(rss.getString(1));
                        vtRow_tnv.add(rss.getString(2));
                        vtRow_tnv.add(rss.getString(3));
                        vtRow_tnv.add(rss.getString(4)); 
                        vtRow_tnv.add(rss.getString(5));     
                        vtRow_tnv.add(rss.getString(6)); 
                        vtData_tnvtimkiemtheochucvu.add(vtRow_tnv);                
                } 
            }tbnhanvientukhoa.setModel(new DefaultTableModel(vtData_tnvtimkiemtheochucvu, vtColumn_tnv));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //tìm kiếm theo nhà cung cấp
    public void ShowTimKiemTheoTenNV(String nv){
        Vector vtColumn_tnv=new Vector(); 
        Vector vtRow_tnv=new Vector(); 
        vtColumn_tnv.add("Mã nhân viên");
        vtColumn_tnv.add("Tên nhân viên");
        vtColumn_tnv.add("SDT");
        vtColumn_tnv.add("Địa chỉ");
        vtColumn_tnv.add("Chức vụ");
        vtColumn_tnv.add("Email");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("SELECT * FROM nguoidung where TenNV like '%" + nv +"%' ");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_tnv=new Vector();
                        vtRow_tnv.add(rss.getString(1));
                        vtRow_tnv.add(rss.getString(2));
                        vtRow_tnv.add(rss.getString(3));
                        vtRow_tnv.add(rss.getString(4)); 
                        vtRow_tnv.add(rss.getString(5));     
                        vtRow_tnv.add(rss.getString(6)); 
                        vtData_tnvtimkiemtheoten.add(vtRow_tnv);                
                } 
            }tbnhanvientukhoa.setModel(new DefaultTableModel(vtData_tnvtimkiemtheoten, vtColumn_tnv));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
//-------------------------------TAB TÀI KHOẢN----------------------------------------------------------   
    public void loadtbtk(){
        Vector vtColumn_ttk=new Vector(); 
        Vector vtRow_ttk=new Vector(); 
        vtColumn_ttk.add("Tài khoản");
        vtColumn_ttk.add("Mật khẩu");
        vtColumn_ttk.add("Nhân viên");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select taikhoan.TK, taikhoan.MK, nguoidung.TenNV from taikhoan inner join nguoidung on nguoidung.MaNV=taikhoan.MaNV where nguoidung.MaNV=taikhoan.MaNV");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_ttk=new Vector();
                        vtRow_ttk.add(rss.getString(1));
                        vtRow_ttk.add(rss.getString(2));
                        vtRow_ttk.add(rss.getString(3)); 
                        vtData_ttk.add(vtRow_ttk);                
                } 
            }tbtaikhoan.setModel(new DefaultTableModel(vtData_ttk, vtColumn_ttk));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    //lay du lieu tai khoan tu bang 
    public void displayDetailsTK(int selectedIndex){
        Vector vtSelectRow_displayDetailsTK = (Vector)vtData_ttk.get(selectedIndex);
        String TaiKhoan = (String)vtSelectRow_displayDetailsTK.get(0);
        String MatKhau = (String)vtSelectRow_displayDetailsTK.get(1);
        String NhanVien = (String)vtSelectRow_displayDetailsTK.get(2);
        
        txttaikhoan.setText(TaiKhoan);
        txtmatkhau.setText(MatKhau);
        comboboxnhanvien.setSelectedIndex(GetIDForComboboxNV(NhanVien)-1);
        
    }
    
    
    public int GetIDForComboboxNV(String nv){
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaNV from nguoidung where TenNV='"+nv+"'");
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
    
    public void getCBboxNV() {
        //kn với database mk dùng SQL SEVER 2012
        try {
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select MaNV,TenNV from nguoidung");
            //ResultSet rss_TenLoai=st.executeQuery("select TenLoai from loaisp");
            while (rss.next()) {
                String id = rss.getString(1);
                String tenloai = rss.getString(2);
                //modelCombo.addElement(new Manufacturer(rs.getString("id"), rs.getString("Name")));
                comboboxnhanvien.addItem(new ComboBoxItem(id, tenloai));
            }
            //cbxID.setModel(modelCombo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
//-------------------------------TAB HÓA ĐƠN----------------------------------------------------------
    public void loadtbhd(){
        Vector vtColumn_thd=new Vector(); 
        Vector vtRow_thd=new Vector(); 
        vtColumn_thd.add("Mã hóa đơn");
        vtColumn_thd.add("Khách hàng");
        vtColumn_thd.add("SDT");
        vtColumn_thd.add("Ngày đặt");
        vtColumn_thd.add("Nhân viên");
        
        try{
            Statement st=db.con.createStatement();
            ResultSet rss=st.executeQuery("select * from hoadon");
            if(rss!=null){
                    while(rss.next()){
                        vtRow_thd=new Vector();
                        vtRow_thd.add(rss.getString(1));
                        vtRow_thd.add(rss.getString(2));
                        vtRow_thd.add(rss.getString(3));
                        vtRow_thd.add(rss.getString(4)); 
                        vtRow_thd.add(rss.getString(5));
                        vtData_thd.add(vtRow_thd);                
                } 
            }tbhoadon.setModel(new DefaultTableModel(vtData_thd, vtColumn_thd));
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    
    //lay du lieu cua hang tu bang 
    public void displayDetailsHD(int selectedIndex){
        Vector vtSelectRow_displayDetailsHD = (Vector)vtData_thd.get(selectedIndex);
        String MaHD = (String)vtSelectRow_displayDetailsHD.get(0);
        String TenKH = (String)vtSelectRow_displayDetailsHD.get(1);
        String SDT = (String)vtSelectRow_displayDetailsHD.get(2);
        String NgayDat = (String)vtSelectRow_displayDetailsHD.get(3);
        String NhanVien = (String)vtSelectRow_displayDetailsHD.get(4);
        
        
        txttenkhachhang.setText(MaHD);
        txtsdt.setText(TenKH);
        txtdiachi.setText(SDT);
        txtemail.setText(NgayDat);   
        txtemail.setText(NhanVien);   
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    
    public frmTrangChu_Admin() {
        
        initComponents();
        loadtbsp();
        loadtblsp();
        loadtbncc();
        loadtbnv();
        loadtbtk();
        loadtbhd();
        getCBboxLoai();
        getCBboxNhaCC();
        getCBboxChucVu();
        getCBboxNV();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane7 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnthemsp = new javax.swing.JButton();
        btnnext1 = new javax.swing.JButton();
        btnsuasp = new javax.swing.JButton();
        btnlast1 = new javax.swing.JButton();
        btnxoasp = new javax.swing.JButton();
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
        btnchon = new javax.swing.JButton();
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
        jPanel2 = new javax.swing.JPanel();
        btnthemloaisp = new javax.swing.JButton();
        btnnext2 = new javax.swing.JButton();
        btnsualoaisp = new javax.swing.JButton();
        btnlast2 = new javax.swing.JButton();
        btnxoaloaisp = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txttukhoaloai = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        spdsloaitk = new javax.swing.JScrollPane();
        tbloaitukhoa = new javax.swing.JTable();
        spdsloai = new javax.swing.JScrollPane();
        tbloaisp = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txttenloai = new javax.swing.JTextField();
        txthinhloai = new javax.swing.JTextField();
        btnchonloai = new javax.swing.JButton();
        btnfirst2 = new javax.swing.JButton();
        btnpre2 = new javax.swing.JButton();
        btntimloaisp = new javax.swing.JButton();
        lbHinhLoai = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
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
        lbHinhNhaCC = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txttennv = new javax.swing.JTextField();
        txtsdtnv = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        comboboxchucvu = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        btnfirst5 = new javax.swing.JButton();
        btnpre5 = new javax.swing.JButton();
        btnnext5 = new javax.swing.JButton();
        btnlast5 = new javax.swing.JButton();
        btnthemnv = new javax.swing.JButton();
        btnsuanv = new javax.swing.JButton();
        btnxoanv = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txttukhoanv = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbnhanvientukhoa = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbnhanvien = new javax.swing.JTable();
        btntimten = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        cbchucvu = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbtaikhoan = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txttaikhoan = new javax.swing.JTextField();
        txtmatkhau = new javax.swing.JTextField();
        comboboxnhanvien = new javax.swing.JComboBox<>();
        btnfirst6 = new javax.swing.JButton();
        btnpre6 = new javax.swing.JButton();
        btnnext6 = new javax.swing.JButton();
        btnlast6 = new javax.swing.JButton();
        btnxoatk = new javax.swing.JButton();
        btnsuatk = new javax.swing.JButton();
        btnthemtk = new javax.swing.JButton();
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
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txttukhoanvhoadon = new javax.swing.JTextField();
        btnnext4 = new javax.swing.JButton();
        btnlast4 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        spdshdnv = new javax.swing.JScrollPane();
        tbhoadontukhoa = new javax.swing.JTable();
        btnpre4 = new javax.swing.JButton();
        btnfirst4 = new javax.swing.JButton();
        btndangxuat = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1400, 400));

        btnthemsp.setText("Thêm");
        btnthemsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemspActionPerformed(evt);
            }
        });

        btnnext1.setText(">");

        btnsuasp.setText("Sửa");
        btnsuasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaspActionPerformed(evt);
            }
        });

        btnlast1.setText(">>>");

        btnxoasp.setText("Xóa");
        btnxoasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaspActionPerformed(evt);
            }
        });

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

        btnchon.setText("Chọn");
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
                                .addComponent(btnchon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txthinhsp, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                            .addComponent(txtmota, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtgia, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboboxnhacc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
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
                .addContainerGap(30, Short.MAX_VALUE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel1);

        btnthemloaisp.setText("Thêm");
        btnthemloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemloaispActionPerformed(evt);
            }
        });

        btnnext2.setText(">");

        btnsualoaisp.setText("Sửa");
        btnsualoaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsualoaispActionPerformed(evt);
            }
        });

        btnlast2.setText(">>>");

        btnxoaloaisp.setText("Xóa");
        btnxoaloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaloaispActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Nhập từ khóa");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txttukhoaloai, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txttukhoaloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Loại sản phẩm ứng với từ khóa");

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
        spdsloaitk.setViewportView(tbloaitukhoa);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spdsloaitk, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spdsloaitk, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        spdsloai.setViewportView(tbloaisp);

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Tên loại");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setText("Hình");

        btnchonloai.setText("Chọn");
        btnchonloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchonloaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnchonloai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthinhloai, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                    .addComponent(txttenloai))
                .addGap(31, 31, 31))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttenloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthinhloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(btnchonloai))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        btnfirst2.setText("<<<");

        btnpre2.setText("<");

        btntimloaisp.setText("Tìm");
        btntimloaisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimloaispActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst2)
                .addGap(18, 18, 18)
                .addComponent(btnpre2)
                .addGap(183, 183, 183)
                .addComponent(btnnext2)
                .addGap(18, 18, 18)
                .addComponent(btnlast2)
                .addGap(231, 231, 231))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(spdsloai, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbHinhLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemloaisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsualoaisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoaloaisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btntimloaisp)
                                .addGap(0, 4, Short.MAX_VALUE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spdsloai, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(lbHinhLoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btntimloaisp)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnthemloaisp)
                        .addGap(18, 18, 18)
                        .addComponent(btnsualoaisp)
                        .addGap(20, 20, 20)
                        .addComponent(btnxoaloaisp)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext2)
                        .addComponent(btnlast2))
                    .addComponent(btnfirst2)
                    .addComponent(btnpre2))
                .addGap(75, 75, 75))
        );

        jTabbedPane1.addTab("Loại sản phẩm", jPanel2);

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

        btnchonnhacc.setText("Chọn");
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
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(btnchonnhacc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthinhnhacc, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txttennhacc)))
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst3)
                .addGap(18, 18, 18)
                .addComponent(btnpre3)
                .addGap(183, 183, 183)
                .addComponent(btnnext3)
                .addGap(18, 18, 18)
                .addComponent(btnlast3)
                .addGap(241, 241, 241))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(spdsncc, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbHinhNhaCC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemnhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsuanhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoanhacc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btntimnhacc)
                                .addGap(0, 25, Short.MAX_VALUE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spdsncc, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(lbHinhNhaCC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btntimnhacc)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnthemnhacc)
                        .addGap(18, 18, 18)
                        .addComponent(btnsuanhacc)
                        .addGap(20, 20, 20)
                        .addComponent(btnxoanhacc)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext3)
                        .addComponent(btnlast3))
                    .addComponent(btnfirst3)
                    .addComponent(btnpre3))
                .addGap(97, 97, 97))
        );

        jTabbedPane1.addTab("Nhà cung cấp", jPanel3);

        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel23.setText("Tên nhân viên");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel24.setText("SDT");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel25.setText("Địa chỉ");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel26.setText("Chức vụ");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel27.setText("Email");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(29, 29, 29)
                            .addComponent(txttennv, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtdiachi)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(txtsdtnv, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(comboboxchucvu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtemail)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttennv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsdtnv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btnfirst5.setText("<<<");

        btnpre5.setText("<");

        btnnext5.setText(">");

        btnlast5.setText(">>>");

        btnthemnv.setText("Thêm");
        btnthemnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemnvActionPerformed(evt);
            }
        });

        btnsuanv.setText("Sửa");
        btnsuanv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuanvActionPerformed(evt);
            }
        });

        btnxoanv.setText("Xóa");
        btnxoanv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoanvActionPerformed(evt);
            }
        });

        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel29.setText("Nhập tên");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(txttukhoanv, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txttukhoanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel29))
        );

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel30.setText("Nhân viên ứng với từ khóa");

        tbnhanvientukhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "SDT", "Địa chỉ", "Chức vụ"
            }
        ));
        jScrollPane2.setViewportView(tbnhanvientukhoa);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "SDT", "Địa chỉ", "Chức vụ", "Email"
            }
        ));
        tbnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbnhanvien);
        if (tbnhanvien.getColumnModel().getColumnCount() > 0) {
            tbnhanvien.getColumnModel().getColumn(3).setHeaderValue("Địa chỉ");
            tbnhanvien.getColumnModel().getColumn(4).setHeaderValue("Chức vụ");
            tbnhanvien.getColumnModel().getColumn(5).setHeaderValue("Email");
        }

        btntimten.setText("Tìm");
        btntimten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimtenActionPerformed(evt);
            }
        });

        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel32.setText("Chọn chức vụ");

        cbchucvu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbchucvuItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel32)
                .addComponent(cbchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthemnv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsuanv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnxoanv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btntimten)))))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst5)
                .addGap(18, 18, 18)
                .addComponent(btnpre5)
                .addGap(183, 183, 183)
                .addComponent(btnnext5)
                .addGap(18, 18, 18)
                .addComponent(btnlast5)
                .addGap(236, 236, 236))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btntimten)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnthemnv)
                                .addGap(18, 18, 18)
                                .addComponent(btnsuanv)
                                .addGap(20, 20, 20)
                                .addComponent(btnxoanv)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext5)
                        .addComponent(btnlast5))
                    .addComponent(btnfirst5)
                    .addComponent(btnpre5))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhân viên", jPanel17);

        tbtaikhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tài khoản", "Mật khẩu", "Nhân viên"
            }
        ));
        tbtaikhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtaikhoanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbtaikhoan);

        jPanel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel28.setText("Tài khoản");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel31.setText("Mật khẩu");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel36.setText("Nhân viên");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel23Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel23Layout.createSequentialGroup()
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(comboboxnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        btnfirst6.setText("<<<");

        btnpre6.setText("<");

        btnnext6.setText(">");

        btnlast6.setText(">>>");

        btnxoatk.setText("Xóa");
        btnxoatk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoatkActionPerformed(evt);
            }
        });

        btnsuatk.setText("Sửa");
        btnsuatk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuatkActionPerformed(evt);
            }
        });

        btnthemtk.setText("Thêm");
        btnthemtk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemtkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 972, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnthemtk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnsuatk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnxoatk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnfirst6)
                            .addGap(18, 18, 18)
                            .addComponent(btnpre6)
                            .addGap(183, 183, 183)
                            .addComponent(btnnext6)
                            .addGap(18, 18, 18)
                            .addComponent(btnlast6)
                            .addGap(196, 196, 196)))
                    .addContainerGap(31, Short.MAX_VALUE)))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(btnthemtk)
                            .addGap(18, 18, 18)
                            .addComponent(btnsuatk)
                            .addGap(20, 20, 20)
                            .addComponent(btnxoatk)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnnext6)
                            .addComponent(btnlast6))
                        .addComponent(btnfirst6)
                        .addComponent(btnpre6))
                    .addContainerGap(66, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Tài khoản", jPanel21);

        tbhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
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
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setText("Nhập từ khóa");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(txttukhoanvhoadon)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txttukhoanvhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnnext4.setText(">");

        btnlast4.setText(">>>");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel20.setText("Danh sách hóa đơn được lập bởi nhân viên");

        tbhoadontukhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Khách hàng", "SDT", "Ngày đặt", "Nhân viên"
            }
        ));
        spdshdnv.setViewportView(tbhoadontukhoa);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 113, Short.MAX_VALUE))
                    .addComponent(spdshdnv, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spdshdnv, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnpre4.setText("<");

        btnfirst4.setText("<<<");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spdshoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnfirst4)
                .addGap(18, 18, 18)
                .addComponent(btnpre4)
                .addGap(183, 183, 183)
                .addComponent(btnnext4)
                .addGap(18, 18, 18)
                .addComponent(btnlast4)
                .addGap(237, 237, 237))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(spdshoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext4)
                        .addComponent(btnlast4))
                    .addComponent(btnfirst4)
                    .addComponent(btnpre4))
                .addGap(96, 96, 96))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 977, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btndangxuat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, Short.MAX_VALUE))
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

    private void tbloaispMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbloaispMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbloaisp.getSelectedRow();
        displayDetailsLSP(selectedRow);
    }//GEN-LAST:event_tbloaispMouseClicked

    private void tbspKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbspKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN){
            int selectedIndex = tbsp.getSelectedRow();
            displayDetailsSP(selectedIndex);
        }
    }//GEN-LAST:event_tbspKeyPressed

    private void btnthemspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemspActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into sp values(?,?,?,?,?,?,?)");
            ps.setInt(1,tbsp.getRowCount()+1);
            ps.setString(2, txttensp.getText());
            ps.setString(3, txtgia.getText());
            ps.setString(4, txtmota.getText());
            ps.setString(5, txthinhsp.getText());
            ps.setString(6, comboboxloai.getItemAt(this.comboboxloai.getSelectedIndex()).get_id());
            ps.setString(7, comboboxnhacc.getItemAt(this.comboboxnhacc.getSelectedIndex()).get_id());
            if(txttensp.getText().equals("") || txtgia.getText().equals("") || txtmota.getText().equals("") || txthinhsp.getText().equals("") || hasVietnameseString(txtgia.getText()) || hasSpecial(txtgia.getText()) || hasSpace(txtgia.getText())){
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
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("update sp set TenSP=?,Gia=?,MoTa=?,Hinh=?,MaLoai=?,MaNhaCC=? where MaSP=?");
            ps.setString(7, tbsp.getValueAt(tbsp.getSelectedRow(), 0).toString());
            ps.setString(1, txttensp.getText());
            ps.setString(2, txtgia.getText());
            ps.setString(3, txtmota.getText());
            ps.setString(4, txthinhsp.getText());
            ps.setString(5, comboboxloai.getItemAt(this.comboboxloai.getSelectedIndex()).get_id());
            ps.setString(6, comboboxnhacc.getItemAt(this.comboboxnhacc.getSelectedIndex()).get_id());
            if(txttensp.getText().equals("") || txtgia.getText().equals("") || txtmota.getText().equals("") || txthinhsp.getText().equals("") || hasVietnameseString(txtgia.getText()) || hasSpecial(txtgia.getText()) || hasSpace(txtgia.getText())){
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
        // TODO add your handling code here:
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

    private void btnthemloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemloaispActionPerformed
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
    }//GEN-LAST:event_btnthemloaispActionPerformed

    private void btnsualoaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsualoaispActionPerformed
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
    }//GEN-LAST:event_btnsualoaispActionPerformed

    private void btnxoaloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaloaispActionPerformed
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
    }//GEN-LAST:event_btnxoaloaispActionPerformed

    private void btntimloaispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimloaispActionPerformed
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbloaitukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoaloai.getText();
        ShowTimKiemTheoTenLoai(ml);  
    }//GEN-LAST:event_btntimloaispActionPerformed

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

    private void btntimnhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimnhaccActionPerformed
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbnhacctukhoa.getModel();
        dtm.setRowCount(0);
        String ml = txttukhoancc.getText();
        ShowTimKiemTheoTenNhaCC(ml);  
    }//GEN-LAST:event_btntimnhaccActionPerformed

    private void tbnhaccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnhaccMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbnhacc.getSelectedRow();
        displayDetailsNCC(selectedRow);
    }//GEN-LAST:event_tbnhaccMouseClicked

    private void tbnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnhanvienMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbnhanvien.getSelectedRow();
        displayDetailsNV(selectedRow);
    }//GEN-LAST:event_tbnhanvienMouseClicked

    private void btnthemnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemnvActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into nguoidung values(?,?,?,?,?,?)");
            ps.setInt(1,tbnhanvien.getRowCount()+1);
            ps.setString(2, txttennv.getText());
            ps.setString(3, txtsdtnv.getText());
            ps.setString(4, txtdiachi.getText());
            ps.setString(5, comboboxchucvu.getItemAt(this.comboboxloai.getSelectedIndex()).get_id());
            ps.setString(6, txtemail.getText());
            if(txttennv.getText().equals("") || txtsdtnv.getText().equals("") || txtdiachi.getText().equals("") || txtemail.getText().equals("") || hasVietnameseString(txtsdtnv.getText()) || hasSpecial(txtsdtnv.getText()) || hasSpace(txtsdtnv.getText())){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbnhanvien.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbnv();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnthemnvActionPerformed

    private void btnsuanvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuanvActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("update nguoidung set TenNV=?,SDT=?,DiaChi=?,VaiTro=?,Email=? where MaNV=?");
            ps.setString(6, tbnhanvien.getValueAt(tbnhanvien.getSelectedRow(), 0).toString());
            ps.setString(1, txttennv.getText());
            ps.setString(2, txtsdtnv.getText());
            ps.setString(3, txtdiachi.getText());
            ps.setString(4, comboboxchucvu.getItemAt(this.comboboxchucvu.getSelectedIndex()).get_id());
            ps.setString(5, txtemail.getText());
            if(txttennv.getText().equals("") || txtsdtnv.getText().equals("") || txtdiachi.getText().equals("") || txtemail.getText().equals("") || hasVietnameseString(txtsdtnv.getText()) || hasSpecial(txtsdtnv.getText()) || hasSpace(txtsdtnv.getText())){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbnhanvien.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbnv();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnsuanvActionPerformed

    private void btnxoanvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoanvActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("Delete from nguoidung where MaNV=?");
            ps.setString(1, tbnhanvien.getValueAt(tbnhanvien.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(this, "Xóa nhân viên này?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps.executeUpdate();
                DefaultTableModel dtm = (DefaultTableModel) tbnhanvien.getModel();
                dtm.setRowCount(0);
                //load lại table
                loadtbnv();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnxoanvActionPerformed

    private void cbchucvuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbchucvuItemStateChanged
        // TODO add your handling code here:
        //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbnhanvientukhoa.getModel();
        dtm.setRowCount(0);
        String cv = cbchucvu.getItemAt(this.cbchucvu.getSelectedIndex()).get_id();
        ShowTimKiemTheoChucVu(cv);  
    }//GEN-LAST:event_cbchucvuItemStateChanged

    private void btntimtenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimtenActionPerformed
        // TODO add your handling code here:
         //xóa tất cả dòng trc đó
        DefaultTableModel dtm = (DefaultTableModel) tbnhanvientukhoa.getModel();
        dtm.setRowCount(0);
        String nv = txttukhoanv.getText();
        ShowTimKiemTheoTenNV(nv);  
    }//GEN-LAST:event_btntimtenActionPerformed

    private void btnchonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonActionPerformed
        // TODO add your handling code here:
        showFileChooserDemo();
    }//GEN-LAST:event_btnchonActionPerformed

    private void btnchonloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonloaiActionPerformed
        // TODO add your handling code here:
        showFileChooserLoai();
    }//GEN-LAST:event_btnchonloaiActionPerformed

    private void btnchonnhaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchonnhaccActionPerformed
        // TODO add your handling code here:
        showFileChooserNhaCC();
    }//GEN-LAST:event_btnchonnhaccActionPerformed

    private void tbtaikhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtaikhoanMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbtaikhoan.getSelectedRow();
        displayDetailsTK(selectedRow);
    }//GEN-LAST:event_tbtaikhoanMouseClicked

    private void btnxoatkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoatkActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("Delete from taikhoan where TK=?");
            ps.setString(1, tbtaikhoan.getValueAt(tbtaikhoan.getSelectedRow(), 0).toString());
            if(JOptionPane.showConfirmDialog(this, "Xóa tài khoản này?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps.executeUpdate();
                DefaultTableModel dtm = (DefaultTableModel) tbtaikhoan.getModel();
                dtm.setRowCount(0);
                //load lại table
                loadtbtk();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnxoatkActionPerformed

    private void btnsuatkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuatkActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("update taikhoan set MK=?,MaNV=? where TK=?");
            ps.setString(3, tbtaikhoan.getValueAt(tbtaikhoan.getSelectedRow(), 0).toString());
            ps.setString(1, txtmatkhau.getText());
            ps.setString(2, comboboxnhanvien.getItemAt(this.comboboxnhanvien.getSelectedIndex()).get_id());
            if(txtmatkhau.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbtaikhoan.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbtk();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                }
            }  
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnsuatkActionPerformed

    private void btnthemtkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemtkActionPerformed
        // TODO add your handling code here:
        try{
            PreparedStatement ps = db.con.prepareStatement("insert into taikhoan values(?,?,?)");
            ps.setString(1, txttaikhoan.getText());
            ps.setString(2, txtmatkhau.getText());
            ps.setString(3, comboboxnhanvien.getItemAt(this.comboboxnhanvien.getSelectedIndex()).get_id());
            if(txttaikhoan.getText().equals("") || txtmatkhau.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Kiểm tra lại thông tin. Không được để trống.");
            }
            else{
                int chk = ps.executeUpdate();
                if(chk>0){
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    //xóa tất cả dòng trc đó
                    DefaultTableModel dtm = (DefaultTableModel) tbtaikhoan.getModel();
                    dtm.setRowCount(0);
                    //load lại table
                    loadtbtk();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnthemtkActionPerformed

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
            java.util.logging.Logger.getLogger(frmTrangChu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTrangChu_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnchon;
    private javax.swing.JButton btnchonloai;
    private javax.swing.JButton btnchonnhacc;
    private javax.swing.JButton btndangxuat;
    private javax.swing.JButton btnfirst1;
    private javax.swing.JButton btnfirst2;
    private javax.swing.JButton btnfirst3;
    private javax.swing.JButton btnfirst4;
    private javax.swing.JButton btnfirst5;
    private javax.swing.JButton btnfirst6;
    private javax.swing.JButton btnlast1;
    private javax.swing.JButton btnlast2;
    private javax.swing.JButton btnlast3;
    private javax.swing.JButton btnlast4;
    private javax.swing.JButton btnlast5;
    private javax.swing.JButton btnlast6;
    private javax.swing.JButton btnnext1;
    private javax.swing.JButton btnnext2;
    private javax.swing.JButton btnnext3;
    private javax.swing.JButton btnnext4;
    private javax.swing.JButton btnnext5;
    private javax.swing.JButton btnnext6;
    private javax.swing.JButton btnpre1;
    private javax.swing.JButton btnpre2;
    private javax.swing.JButton btnpre3;
    private javax.swing.JButton btnpre4;
    private javax.swing.JButton btnpre5;
    private javax.swing.JButton btnpre6;
    private javax.swing.JButton btnsualoaisp;
    private javax.swing.JButton btnsuanhacc;
    private javax.swing.JButton btnsuanv;
    private javax.swing.JButton btnsuasp;
    private javax.swing.JButton btnsuatk;
    private javax.swing.JButton btnthemloaisp;
    private javax.swing.JButton btnthemnhacc;
    private javax.swing.JButton btnthemnv;
    private javax.swing.JButton btnthemsp;
    private javax.swing.JButton btnthemtk;
    private javax.swing.JButton btntimloaisp;
    private javax.swing.JButton btntimnhacc;
    private javax.swing.JButton btntimten;
    private javax.swing.JButton btntimtensp;
    private javax.swing.JButton btnxoaloaisp;
    private javax.swing.JButton btnxoanhacc;
    private javax.swing.JButton btnxoanv;
    private javax.swing.JButton btnxoasp;
    private javax.swing.JButton btnxoatk;
    private javax.swing.JComboBox<ComboBoxItem> cbchucvu;
    private javax.swing.JComboBox<ComboBoxItem> cbloai;
    private javax.swing.JComboBox<ComboBoxItem> cbnhacc;
    private javax.swing.JComboBox<ComboBoxItem> comboboxchucvu;
    private javax.swing.JComboBox<ComboBoxItem> comboboxloai;
    private javax.swing.JComboBox<ComboBoxItem> comboboxnhacc;
    private javax.swing.JComboBox<ComboBoxItem> comboboxnhanvien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbHinhLoai;
    private javax.swing.JLabel lbHinhNhaCC;
    private javax.swing.JLabel lbHinhSP;
    private javax.swing.JScrollPane spdshdnv;
    private javax.swing.JScrollPane spdshoadon;
    private javax.swing.JScrollPane spdsloai;
    private javax.swing.JScrollPane spdsloaitk;
    private javax.swing.JScrollPane spdsncc;
    private javax.swing.JScrollPane spdsnhacctk;
    private javax.swing.JScrollPane spdssp;
    private javax.swing.JScrollPane spdssptk;
    private javax.swing.JTable tbhoadon;
    private javax.swing.JTable tbhoadontukhoa;
    private javax.swing.JTable tbloaisp;
    private javax.swing.JTable tbloaitukhoa;
    private javax.swing.JTable tbnhacc;
    private javax.swing.JTable tbnhacctukhoa;
    private javax.swing.JTable tbnhanvien;
    private javax.swing.JTable tbnhanvientukhoa;
    private javax.swing.JTable tbsp;
    private javax.swing.JTable tbsptukhoa;
    private javax.swing.JTable tbtaikhoan;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txthinhloai;
    private javax.swing.JTextField txthinhnhacc;
    private javax.swing.JTextField txthinhsp;
    private javax.swing.JTextField txtmatkhau;
    private javax.swing.JTextField txtmota;
    private javax.swing.JTextField txtngaydat;
    private javax.swing.JTextField txtnhanvien;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txtsdtnv;
    private javax.swing.JTextField txttaikhoan;
    private javax.swing.JTextField txttenkhachhang;
    private javax.swing.JTextField txttenloai;
    private javax.swing.JTextField txttennhacc;
    private javax.swing.JTextField txttennv;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttukhoaloai;
    private javax.swing.JTextField txttukhoancc;
    private javax.swing.JTextField txttukhoanv;
    private javax.swing.JTextField txttukhoanvhoadon;
    private javax.swing.JTextField txttukhoasp;
    // End of variables declaration//GEN-END:variables
}
