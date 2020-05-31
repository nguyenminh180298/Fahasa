/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Admin
 */
public class TextCheck {
    public static boolean hasVietnameseString(String text){
        //biến quét các trường hợp chữ tiếng việt
        String mang[]={"ă","ẵ","ẳ","ằ","ắ","ặ","â","ấ","ầ","ẩ","ẫ","ậ","à","á","ã","ạ","ả","đ","ê","ế","ề","ể","ễ","ệ","ư","ứ","ừ","ử","ữ","ự","ú","ù","ủ","ũ","ụ","ó","ò","ỏ","õ","ọ","ô","ố","ồ","ổ","ỗ","ộ","ơ","ớ","ờ","ở","ỡ","ợ","í","ì","ỉ","ĩ","ị","ý","ỳ","ỷ","ỹ","ỵ"};
        
        //chạy vòng lặp kiểm tra
        for(int i=0;i<mang.length;i++){
            if(text.contains(mang[i])){
                return true; //có chữ tiếng việt
            }
        }
        
        return false;//không có chữ tiếng việt
    }
    
    public static boolean hasSpace(String text){
        if(text.contains(" ")){
            return true;
        }
        return false;
    }
    
    public static boolean hasSpecial(String text){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        boolean b = m.find();

        if (b){
            //có ký tự đặt biệt
            return true;
        }
        return false;
    }
    
    public static boolean hasOnlyNumber(String text){
        if(text.matches("[0-9]+")){
            //chỉ có số
            return true;
        }
        return false;
    }
    public static String generateRandomString(int length){
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }
    
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
    }
}
