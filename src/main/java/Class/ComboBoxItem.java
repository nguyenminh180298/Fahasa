/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author Administrator
 */
public class ComboBoxItem {
    public String id="";
    public String tenloai="";
    
    public ComboBoxItem(String id,String tenloai) {
    this.id=id;
    this.tenloai=tenloai;
    }

    public String get_id(){
    return id;
    }
    public String get_tenloai(){
    return tenloai;
    }
    @Override
    public String toString() {
    return tenloai;
    }
}
