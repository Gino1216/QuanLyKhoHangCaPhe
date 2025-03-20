package DuAn.dao;

import DuAn.database.DatabaseConnection;
import DuAn.model.NhaCungCap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    private Connection connection;

    public NhaCungCapDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Sửa để trả về List<NhaCungCap> thay vì List<Object[]>
    public List<NhaCungCap> getNhaCungCapData() {
        List<NhaCungCap> data = new ArrayList<>();
        String query = "SELECT * FROM nhacungcap";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maNCC = rs.getString("maNhaCungCap");
                String tenNCC = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt"); // Đảm bảo lấy kiểu String
                String diaChi = rs.getString("diaChi");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, sdt, diaChi);
                data.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void addNhaCungCap(String maNhaCungCap, String tenNCC, String sdt, String diaChi) {
        String query = "INSERT INTO nhacungcap (maNhaCungCap, tenNhaCungCap, Sdt, diaChi) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maNhaCungCap);
            stmt.setString(2, tenNCC);
            stmt.setString(3, sdt);  // Sdt là String
            stmt.setString(4, diaChi);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNhaCungCap(String maNhaCungCap) {
        String query = "DELETE FROM nhacungcap WHERE maNhaCungCap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maNhaCungCap);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNhaCungCap(String maNhaCungCap, String tenNCC, String sdt, String diaChi) {
        String query = "UPDATE nhacungcap SET tenNhaCungCap = ?, Sdt = ?, diaChi = ? WHERE maNhaCungCap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, tenNCC);
            stmt.setString(2, sdt);  // Đảm bảo kiểu dữ liệu đồng nhất
            stmt.setString(3, diaChi);
            stmt.setString(4, maNhaCungCap);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<NhaCungCap> searchNhaCungCap(String keyword) {
        List<NhaCungCap> result = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE maNhaCungCap LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhaCungCap ncc = new NhaCungCap(
                            rs.getString("maNhaCungCap"),
                            rs.getString("tenNhaCungCap"),
                            rs.getString("diaChi"),
                            rs.getString("sdt")
                    );
                    result.add(ncc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
