package DuAn.dao;

import DuAn.model.NguoiDung;
import DuAn.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NguoiDungDAO {
    private Connection connection;

    public NguoiDungDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra đăng nhập
    public NguoiDung kiemTraDangNhap(String username, String password) {
        String query = "SELECT * FROM account WHERE userName = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Trong thực tế, nên so sánh mật khẩu đã mã hóa
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NguoiDung(
                        rs.getString("fullName"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}