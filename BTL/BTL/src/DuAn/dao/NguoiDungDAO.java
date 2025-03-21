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
    public NguoiDung kiemTraDangNhap(String username) {
        String sql = "SELECT * FROM Account WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setUserName(rs.getString("username"));
                nguoiDung.setPassword(rs.getString("password")); // Mật khẩu đã băm
                return nguoiDung;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //kiem tra dang nhap neu k dung Bryct
//    public NguoiDung kiemTraDangNhap(String username, String password) {
//        String query = "SELECT * FROM account WHERE userName = ? AND password = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return new NguoiDung(
//                        rs.getString("fullName"),
//                        rs.getString("userName"),
//                        rs.getString("password"),
//                        rs.getString("role")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}