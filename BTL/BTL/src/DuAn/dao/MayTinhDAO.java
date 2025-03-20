package DuAn.dao;

import DuAn.model.MayTinh;
import DuAn.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MayTinhDAO {
    private Connection connection;

    public MayTinhDAO() {
        try {
            // Sử dụng lớp DatabaseConnection để lấy kết nối
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MayTinh> getMayTinhData() {
        List<MayTinh> data = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM maytinh")) {
            while (rs.next()) {
                MayTinh mayTinh = new MayTinh(
                        rs.getString("maMay"),
                        rs.getString("tenMay"),
                        rs.getInt("soLuong"),
                        rs.getDouble("gia")
                );
                data.add(mayTinh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void addMayTinh(MayTinh mayTinh) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO maytinh (maMay, tenMay, soLuong, gia) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, mayTinh.getMaMay());
            stmt.setString(2, mayTinh.getTenMay());
            stmt.setInt(3, mayTinh.getSoLuong());
            stmt.setDouble(4, mayTinh.getGia());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMayTinh(MayTinh mayTinh) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE maytinh SET tenMay = ?, soLuong = ?, gia = ? WHERE maMay = ?")) {
            stmt.setString(1, mayTinh.getTenMay());
            stmt.setInt(2, mayTinh.getSoLuong());
            stmt.setDouble(3, mayTinh.getGia());
            stmt.setString(4, mayTinh.getMaMay());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMayTinh(String maMay) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM maytinh WHERE maMay = ?")) {
            stmt.setString(1, maMay);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MayTinh> searchMayTinh(String keyword) {
        List<MayTinh> result = new ArrayList<>();
        String sql = "SELECT * FROM maytinh WHERE maMay LIKE ?"; // Chỉ tìm kiếm theo mã máy

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword); // Chỉ truyền tham số cho mã máy

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MayTinh mayTinh = new MayTinh(
                            rs.getString("maMay"),
                            rs.getString("tenMay"),
                            rs.getInt("soLuong"),
                            rs.getDouble("gia")
                    );
                    result.add(mayTinh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}