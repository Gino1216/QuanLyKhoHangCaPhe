package DuAn.dao;

import DuAn.database.DatabaseConnection;
import DuAn.model.PhieuNhap;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private Connection connection;

    public PhieuNhapDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách phiếu nhập, trả về List<PhieuNhap>
    public List<PhieuNhap> getPhieuNhapData() {
        List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
        String query = "SELECT * FROM phieunhap";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PhieuNhap phieuNhap = new PhieuNhap(
                        rs.getString("maPhieu"),
                        rs.getTimestamp("thoiGianTao").toLocalDateTime(),
                        rs.getString("nguoiTao"),
                        rs.getString("maNhaCungCap"),
                        rs.getDouble("tongTien")
                );
                danhSachPhieuNhap.add(phieuNhap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachPhieuNhap;
    }

    // Kiểm tra mã nhà cung cấp có tồn tại không
    public boolean kiemTraMaNCC(String maNCC) {
        String query = "SELECT COUNT(*) FROM nhacungcap WHERE maNhaCungCap = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm phiếu nhập
    public void addPhieuNhap(String maPhieu, String nguoiTao, String maNhaCungCap, double tongTien) {
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        String query = "INSERT INTO phieunhap (maPhieu, thoiGianTao, nguoiTao, maNhaCungCap, tongTien) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maPhieu);
            stmt.setTimestamp(2, Timestamp.valueOf(thoiGianHienTai));
            stmt.setString(3, nguoiTao);
            stmt.setString(4, maNhaCungCap);
            stmt.setDouble(5, tongTien);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa phiếu nhập theo mã
    public void deletePhieuNhap(String maPhieu) {
        String query = "DELETE FROM phieunhap WHERE maPhieu = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maPhieu);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xóa phiếu nhập thành công!");
            } else {
                System.out.println("Không tìm thấy phiếu nhập để xóa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật phiếu nhập
    public void updatePhieuNhap(String maPhieu, String nguoiTao, String maNhaCungCap, double tongTien) {
        String query = "UPDATE phieunhap SET nguoiTao = ?, maNhaCungCap = ?, tongTien = ? WHERE maPhieu = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nguoiTao);
            stmt.setString(2, maNhaCungCap);
            stmt.setDouble(3, tongTien);
            stmt.setString(4, maPhieu);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật phiếu nhập thành công!");
            } else {
                System.out.println("Không tìm thấy phiếu nhập để cập nhật.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<PhieuNhap> searchPhieuNhap(String keyword) {
        List<PhieuNhap> result = new ArrayList<>();
        String query = "SELECT * FROM phieunhap WHERE maPhieu LIKE ?"; // Chỉ tìm kiếm theo mã phiếu

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword); // Chỉ truyền tham số cho mã phiếu

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuNhap phieuNhap = new PhieuNhap(
                            rs.getString("maPhieu"),
                            rs.getTimestamp("thoiGianTao").toLocalDateTime(),
                            rs.getString("nguoiTao"),
                            rs.getString("maNhaCungCap"),
                            rs.getDouble("tongTien")
                    );
                    result.add(phieuNhap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
