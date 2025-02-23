/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeThong;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Thang-1007
 */
public class ProductDao {
    public static boolean insertProduct(String maMay, String tenMay, int soLuong, double donGia, String boXuLy, String ram, String boNho, String loaiMay) {
String sql = "INSERT INTO maytinh (maMay, tenMay, soLuong, gia, tenCpu, ram, rom, loaiMay, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?,1)";

        try (Connection conn = MySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maMay);
            stmt.setString(2, tenMay);
            stmt.setInt(3, soLuong);
            stmt.setDouble(4, donGia);
            stmt.setString(5, boXuLy);
            stmt.setString(6, ram);
            stmt.setString(7, boNho);
            stmt.setString(8, loaiMay);

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
    e.printStackTrace(); // In lỗi chi tiết
    JOptionPane.showMessageDialog(null, "Lỗi khi thêm vào database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
}
        return false;
    }
    
    public static void updateProduct(String maMay, String tenMay, int soLuong, double donGia,
                                     String boXuLy, String ram, String boNho, String loaiMay) {
        String sql = "UPDATE maytinh SET tenMay = ?, soLuong = ?, gia = ?, tenCpu = ?, ram = ?, rom = ?, loaiMay = ? WHERE maMay = ?";
        try (Connection conn = MySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenMay);
            stmt.setInt(2, soLuong);
            stmt.setDouble(3, donGia);
            stmt.setString(4, boXuLy);
            stmt.setString(5, ram);
            stmt.setString(6, boNho);
            stmt.setString(7, loaiMay);
            stmt.setString(8, maMay); // Điều kiện WHERE

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

    
