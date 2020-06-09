/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Database.Database;
import Forms.Utilities.TextCheck;
import static Forms.Utilities.TextCheck.hasSpace;
import static Forms.Utilities.TextCheck.hasSpecial;
import static Forms.Utilities.TextCheck.hasVietnameseString;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import Forms.frmTrangChu_Admin;
import Forms.frmTrangChu_QLKho;
import Forms.frmTrangChu_ThuNgan;
import Forms.frmTrangChu_KeToan;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Admin
 */
public class frmDangNhap extends javax.swing.JFrame {

    /**
     * Creates new form frmDangNhap
     */
    Database db =new Database();
    
    //hàm hỗ trợ gán ảnh cho label
    public static ImageIcon createImageIcon(String path) {
        if (path != null) {
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public frmDangNhap() throws IOException {
        initComponents();
        
        
        getContentPane().setBackground( Color.white );
        setTitle("Fahasa");
        setIconImage(ImageIO.read(new File("C:\\Users\\Van\\Documents\\NetBeansProjects\\Fahasa\\src\\main\\java\\Forms\\resources\\fahasa-logo-rectangle1.png"))
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTitle = new javax.swing.JLabel();
        txtPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JLabel();
        edtUsername = new javax.swing.JTextField();
        ImageIcon icon = createImageIcon("C:\\Users\\Administrator\\Documents\\NetBeansProjects\\Fahasa\\src\\main\\java\\Forms\\resources\\fahasa-logo-200.png");
        imgIcon = new javax.swing.JLabel(icon);
        btnLogin = new javax.swing.JButton();
        txtForgotpass = new javax.swing.JLabel();
        btnRestorepage = new javax.swing.JButton();
        edtPassword = new javax.swing.JPasswordField();
        ckbShowpassword = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(964, 465));

        txtTitle.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        txtTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitle.setText("TRANG ĐĂNG NHẬP");
        txtTitle.setAlignmentY(0.0F);

        txtPassword.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtPassword.setText("Mật khẩu:");

        txtUsername.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtUsername.setText("Tên đăng nhập:");

        btnLogin.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnLogin.setText("Đăng nhập");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });

        txtForgotpass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtForgotpass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtForgotpass.setText("Bạn quên mật khẩu hay tên đăng nhập? Bấm vào nút dưới đây");

        btnRestorepage.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnRestorepage.setText("Khôi phục thông tin đăng nhập");
        btnRestorepage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRestorepageMouseClicked(evt);
            }
        });

        ckbShowpassword.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        ckbShowpassword.setText(" Hiện mật khẩu");
        ckbShowpassword.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ckbShowpasswordItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(imgIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtForgotpass, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(edtPassword)
                            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnRestorepage, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckbShowpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckbShowpassword))
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtForgotpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRestorepage, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        if(edtUsername.getText().toString().equals("")){
            //ô tên đăng nhập rỗng
            JOptionPane.showMessageDialog(null,"Không được để trống tên đăng nhập","Lỗi tên đăng nhập",JOptionPane.ERROR_MESSAGE);
        }else{
            //không rỗng

            //UIManager.put("OptionPane.okButtonText", "Đóng thông báo");
            if(edtPassword.getPassword().length==0){
                //Ô mật khẩu bị trống
                JOptionPane.showMessageDialog(null,"Không được để trống mật khẩu","Lỗi mật khẩu",JOptionPane.ERROR_MESSAGE);
            }else{
                //không rỗng

                //kiểm tra chuỗi nhập có dấu hay không
                if(hasVietnameseString(edtUsername.getText().toString().toLowerCase())){
                    //có dấu
                    JOptionPane.showMessageDialog(null,"Tên đăng nhập không được ghi chữ có dấu","Lỗi tên đăng nhập",JOptionPane.ERROR_MESSAGE);
                }else{
                    //ko dấu

                    //kiểm tra password chữ có dấu
                    String passText = new String(edtPassword.getPassword()).toLowerCase();
                    if(hasVietnameseString(passText)){
                        //có dấu
                        JOptionPane.showMessageDialog(null,"Mật khẩu không được ghi chữ có dấu","Lỗi mật khẩu",JOptionPane.ERROR_MESSAGE);
                    }else{
                        //ko dấu

                        //dịch tên nút của thông báo
                        UIManager.put("OptionPane.cancelButtonText", "Huỷ");
                        UIManager.put("OptionPane.okButtonText", "Bỏ khoảng trắng");

                        //kiểm tra tên đăng nhập có khoảng trống
                        if(hasSpace(edtUsername.getText().toString())){
                            //có khoảng trống

                            int result = JOptionPane.showConfirmDialog(null,"Tên đăng nhập không được có khoảng trắng. Bạn có muốn bỏ chúng?","Lỗi tên đăng nhập",JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                //loại bỏ khoảng trắng

                                String bokt=new String(edtUsername.getText()).replaceAll("\\s+","");
                                edtUsername.setText(bokt);
                            }
                        }else{
                            //ko co khoảng trắng

                            //kiểm tra mật khẩu có khoảng trống
                            if(hasSpace(new String(edtPassword.getPassword()))){
                                //có khoảng trống

                                int result = JOptionPane.showConfirmDialog(null,"Mật khẩu không được có khoảng trắng. Bạn có muốn bỏ chúng?","Lỗi mật khẩu",JOptionPane.OK_CANCEL_OPTION);
                                if (result == JOptionPane.OK_OPTION) {
                                    //loại bỏ khoảng trắng

                                    String bokt=new String(edtPassword.getPassword()).replaceAll("\\s+","");
                                    edtPassword.setText(bokt);
                                }
                            }else{
                                //ko co khoảng trắng

                                //kiểm tra tên đăng nhập có ký tự đặt biệt
                                if(hasSpecial(new String(edtUsername.getText()))){
                                    //có ký tự đặt biệt

                                    //dịch tên nút của thông báo
                                    UIManager.put("OptionPane.cancelButtonText", "Huỷ");
                                    UIManager.put("OptionPane.okButtonText", "Bỏ ký tự đặt biệt");

                                    int result = JOptionPane.showConfirmDialog(null,"Tên đăng nhập không được có ký tự đặt biệt. Bạn có muốn bỏ chúng?","Lỗi tên đăng nhập",JOptionPane.OK_CANCEL_OPTION);
                                    if (result == JOptionPane.OK_OPTION) {
                                        //loại bỏ khoảng trắng

                                        String output = new String(edtUsername.getText()).replaceAll("[^a-zA-Z0-9]", "");
                                        edtUsername.setText(output);
                                    }
                                }else{
                                    //ko có ký tự đặt biệt
                                    try {


                                        //chạy và kiểm tra xem tài khoản đăng nhập có tồn tại
                                        Statement st=db.con.createStatement();
                                        ResultSet rs = st.executeQuery("select a.MaNV,b.TenNV,b.VaiTro from taikhoan a, nguoidung b where a.TK='"+new String(edtUsername.getText())+"' and a.MK='"+new String(edtPassword.getPassword())+"' and a.MaNV=b.MaNV");
                                        ResultSetMetaData md = rs.getMetaData();

                                        //dịch nút cho thông báo
                                        UIManager.put("OptionPane.okButtonText", "Đóng");

                                        //kiểm tra số dòng lấy được có phải =1 hay không
                                        if (!rs.next()) {
                                            //RỖNG
                                            JOptionPane.showMessageDialog(null,"Thông tin đăng nhập không chính xác","Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);

                                        } else {
                                            //CÓ

                                            JOptionPane.showMessageDialog(null, "Đăng nhập thành công! Chào mừng bạn đã trở lại "+rs.getString("TenNV")+".", "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                                            //kiểm tra vai trò để điều hướng
                                            switch(Integer.valueOf(rs.getString("VaiTro"))){
                                                case 1:
                                                    //frmTrangchu_Admin

                                                    //thông báo
                                                    this.dispose();
                                                    frmTrangChu_Admin frm = new frmTrangChu_Admin();
                                                    frm.setVisible(true);
                                                    break;
                                                case 2:
                                                    this.dispose();
                                                    //frmTrangchu_KeToan
                                                    
                                                    frmTrangChu_KeToan frm1 = new frmTrangChu_KeToan();
                                                    frm1.setVisible(true);
                                                    
                                                    break;        
                                                case 3:
                                                    //frmTrangchu_ThuNgan
                                                    this.dispose();
                                                    
                                                    frmTrangChu_ThuNgan frm2 = new frmTrangChu_ThuNgan();
                                                    frm2.setVisible(true);    
                                                    

                                                    break;
                                                case 4:
                                                    //frmTrangchu_QLKho
                                                    this.dispose();
                                                    
                                                    frmTrangChu_QLKho frm3 = new frmTrangChu_QLKho();
                                                    frm3.setVisible(true);    
                                                    

                                                    break;
                                                default:
                                                    //frmNotfound
                                                    JOptionPane.showMessageDialog(null, "Đăng nhập không thanh công do nhóm người dùng của bạn hiện không khả dụng trong hệ thống"+rs.getString("TenNV")+".", "Lỗi vai trò", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        Logger.getLogger(frmDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } 
    }//GEN-LAST:event_btnLoginMouseClicked

    private void ckbShowpasswordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ckbShowpasswordItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            edtPassword.setEchoChar((char)0);
        }else{
            edtPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_ckbShowpasswordItemStateChanged

    private void btnRestorepageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRestorepageMouseClicked
        // TODO add your handling code here:
        this.dispose();
        frmKhoiPhucTaiKhoan frm=new frmKhoiPhucTaiKhoan();
        frm.setVisible(true);
    }//GEN-LAST:event_btnRestorepageMouseClicked

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
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frmDangNhap().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRestorepage;
    private javax.swing.JCheckBox ckbShowpassword;
    private javax.swing.JPasswordField edtPassword;
    private javax.swing.JTextField edtUsername;
    private javax.swing.JLabel imgIcon;
    private javax.swing.JLabel txtForgotpass;
    private javax.swing.JLabel txtPassword;
    private javax.swing.JLabel txtTitle;
    private javax.swing.JLabel txtUsername;
    // End of variables declaration//GEN-END:variables
}
