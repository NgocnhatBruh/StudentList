/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vovanngoc_n21dcat035;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ngocn
 */
public class KetNoiSQL {
    public static Connection KetNoi(){
        Connection ketNoi = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        String connectionUrl = "jdbc:sqlserver://ACER:1433;databaseName=DSSINHVIEN;user=sa;password=123;"
                             + "encrypt=true;trustServerCertificate=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            System.out.println("OK!!!");
            ketNoi = DriverManager.getConnection(connectionUrl);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println("Deo OK!!!");
            e.printStackTrace();
        }
        
        return ketNoi;
    }
    public static void main(String[] args) {
        
        String connectionUrl = "jdbc:sqlserver://ACER:1433;databaseName=DSSINHVIEN;user=sa;password=123;"
                             + "encrypt=true;trustServerCertificate=true";

        try (Connection conn = DriverManager.getConnection(connectionUrl); Statement stmt = conn.createStatement();) {
            System.out.println("OK!!!");
            
            String SQL = "SELECT * FROM SINHVIEN";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("MASV") + " " + rs.getString("HOTEN"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println("Deo OK!!!");
            e.printStackTrace();
        }
    }
}
