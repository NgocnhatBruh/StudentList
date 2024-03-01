/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vovanngoc_n21dcat035;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import static vovanngoc_n21dcat035.GiaoDienChinh.dtm;
import static vovanngoc_n21dcat035.GiaoDienChinh.jTable_SinhVien;
/**
 *
 * @author ngocn
 */
public class XuLySQL {
    static byte loiLuu = 0;
    static boolean loiXoa;
    static boolean loiTim;
    static byte loiSua = 0;
    static Vector<Object> SinhVien;
    
    
    
    public static void laySV() {
        Connection ketNoi = KetNoiSQL.KetNoi();
        String sql = "select * from SINHVIEN";
        Vector<Object> vt;

        try {
            dtm = new DefaultTableModel();
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Tạo mô hình dữ liệu và đặt tên cột
            dtm.addColumn("MASV");
            dtm.addColumn("HOTEN");
            dtm.addColumn("NGAYSINH");
            dtm.addColumn("GIOITINH");
            dtm.addColumn("SDT");
            dtm.addColumn("MAIL");

            while (rs.next()) {
                vt = new Vector<>();
                vt.add(rs.getString("MASV"));
                vt.add(rs.getString("HOTEN"));
                vt.add(rs.getString("NGAYSINH"));
                vt.add(rs.getByte("GIOITINH"));
                vt.add(rs.getString("SDT"));
                vt.add(rs.getString("MAIL"));
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
            ketNoi.close();
            System.out.println("Lay du lieu thanh cong!!!");
        } catch (Exception e) {
            System.out.println("Lỗi lấy!!!");
        }

        jTable_SinhVien.setModel(dtm); // Đặt mô hình dữ liệu cho JTable sau khi đã cập nhật dtm

    }
    
    public static void luuSV(String maSV, String hoTen, String ns, byte gt, String sdt, String mail){
        Connection ketNoi = KetNoiSQL.KetNoi();
        String sql = "insert into SINHVIEN values (?, ?, ?, ?, ?, ?)";
        
        try {
            for (int i = 0; i < dtm.getRowCount(); i++) {
            if(sdt.equals(dtm.getValueAt(i, 4))){
                loiLuu = 2;
                return;
            } 
            if(mail.equals(dtm.getValueAt(i, 5))){
                loiLuu = 3;
                return;
            } 
        }
            dtm = new DefaultTableModel();
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ps.setString(1, maSV.toUpperCase());
            ps.setString(2, chuanHoaTen(hoTen));
            ps.setString(3, ns);
            ps.setByte(4, gt);
            ps.setString(5, sdt);
            ps.setString(6, mail);
            ps.executeUpdate();

            System.out.println("Luu du lieu thanh cong!!!");
        } catch (Exception e) {
            loiLuu = 1;
            System.out.println("Lỗi lưu!!!");
        }
    }
    
    public static void xoaSV(String maSV){
        Connection ketNoi = KetNoiSQL.KetNoi();
        String sql = "delete from SINHVIEN where maSV = ?";
        loiXoa = false;
        
        try {
            dtm = new DefaultTableModel();
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            
            ps.setString(1, maSV.toUpperCase());

            System.out.println("Xoa du lieu thanh cong!!!");
            if (ps.executeUpdate() == 0) loiXoa = true;
        } catch (Exception e) {
            System.out.println("Lỗi Xóa!!!");
        }
    }
    
    public static void timSV(String maSV) {
        Connection ketNoi = KetNoiSQL.KetNoi();
        String sql = "SELECT * FROM SINHVIEN WHERE MASV = ?";
        loiTim = false;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                SinhVien = new Vector<>();
                SinhVien.add(rs.getString("MASV"));
                SinhVien.add(rs.getString("HOTEN"));
                SinhVien.add(rs.getString("SDT"));
                SinhVien.add(rs.getString("MAIL"));
            } else {
                loiTim = true;
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Loi Tim!!!");
        }
    }
    
    
    public static void suaSV(String maSV, String hoTen, String ns, byte gt, String sdt, String mail) {
        Connection ketNoi = KetNoiSQL.KetNoi();
        String sql = "UPDATE SINHVIEN SET HOTEN = ?, NGAYSINH = ?, GIOITINH = ?, SDT = ?, MAIL = ? WHERE MASV = ?";

        try {
            for (int i = 0; i < dtm.getRowCount(); i++) {
                if(!maSV.equals(dtm.getValueAt(i, 0)) &&
                        sdt.equals(dtm.getValueAt(i, 4))){
                    loiSua = 1;
                    return;
                } 
                if(!maSV.equals(dtm.getValueAt(i, 0)) &&
                        mail.equals(dtm.getValueAt(i, 5))){
                    loiSua = 2;
                    return;
                } 

            }
            
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            
            ps.setString(1, chuanHoaTen(hoTen));
            ps.setString(2, ns);
            ps.setByte(3, gt);
            ps.setString(4, sdt);
            ps.setString(5, mail);
            ps.setString(6, maSV);
            
            ps.executeUpdate();

            System.out.println("Luu thanh cong!!!");    
        } catch (Exception e) {
            System.out.println("Lỗi Sửa!!!");
        }
    }
    

    
    public static String chuanHoaTen(String hoTen) {

        hoTen = hoTen.trim(); // xóa khoảng trắng đầu và cuối.

        char[] chars = hoTen.toCharArray();// chuyến String -> char.

            
        chars[0] = Character.toUpperCase(chars[0]);// Viết hoa ký tự đầu.

        // Chữ đầu sau dấu cách viết hoa.
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == ' ') {
                chars[i] = Character.toUpperCase(chars[i]);
            } else {
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }
        
        hoTen = new String(chars); // chuyển Char -> String.
        hoTen = hoTen.replaceAll("\\s+", " ");//Xóa khoảng trắng thừa.
        return hoTen;
    }
}
