package Dao;

import static Config.Mysql.closeConnection;
import static Config.Mysql.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.Account;

public class DaoUse {

    // Kiểm tra đăng nhập và trả về đối tượng Account nếu đăng nhập thành công
    public static Account checkLogin(String username, String password) {
        Connection c = getConnection();
        if (c == null) {
            return null; // Nếu không kết nối được thì trả về null
        }
        
        String sql = "SELECT * FROM taikhoan WHERE username = ? AND pass = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu có kết quả trả về, thông tin đăng nhập đúng
                int role = rs.getInt("MaQuyen");  // Lấy giá trị role từ cơ sở dữ liệu

                // Lưu thông tin tài khoản vào đối tượng Account
                Account account = new Account(
                    rs.getString("username"),
                    rs.getString("pass"),
                    role
                );

                // Trả về tài khoản và role
                return account;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập!");
            e.printStackTrace();
        } finally {
            closeConnection(c);
        }
        
        return null; // Nếu không tìm thấy tài khoản, trả về null
    }
}
