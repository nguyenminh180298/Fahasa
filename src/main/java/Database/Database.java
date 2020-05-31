/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Phan nhut minh
 */
public class Database {
    public static Connection con;
    public Database(){
        try {
            String sUrl= "jdbc:mysql://localhost/fahasa?useUnicode=true&characterEncoding=UTF-8&Timezone=true&serverTimezone=UTC"; // chuỗi kết nối database url localhost
            con=DriverManager.getConnection(sUrl, "root", "");//gán giá trị biến kết nối với url tên đăng nhập và mật khẩu cho trang phpmyadmin
            //JOptionPane.showMessageDialog(null,"Kết nối cơ sở dữ liệu thành công.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Kết nối cơ sở dữ liệu không thành công.");
        }
    }
    
    
}
