package Dao;

import Config.Mysql;
import DTO.PXDTO;
import Repository.PhieuXuatRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPhieuXuat implements PhieuXuatRepo {

    @Override
    public void themPhieuXuat(PXDTO phieuXuat) {
        String maPX = sinhMaPX();  // Hàm sinh mã tự động
        if (maPX == null) return;

        String sql = "INSERT INTO phieuxuat (MaPX, MaKH, MaNV, ThoiGian, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            stmt.setString(2, phieuXuat.getMaKhachHang());
            stmt.setString(3, phieuXuat.getMaNhanVien());
            stmt.setTimestamp(4, Timestamp.valueOf(phieuXuat.getThoiGian()));
            stmt.setFloat(5, phieuXuat.getTongTien());
            stmt.setString(6, phieuXuat.getTrangThai());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm phiếu xuất thành công với mã: " + maPX);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean suaPhieuXuat(PXDTO phieuXuat) {
        String sql = "UPDATE phieuxuat SET MaKH = ?, MaNV = ?, ThoiGian = ?, TongTien = ?, TrangThai = ? WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phieuXuat.getMaKhachHang());
            stmt.setString(2, phieuXuat.getMaNhanVien());
            stmt.setTimestamp(3, Timestamp.valueOf(phieuXuat.getThoiGian()));
            stmt.setFloat(4, phieuXuat.getTongTien());
            stmt.setString(5, phieuXuat.getTrangThai());
            stmt.setString(6, phieuXuat.getMaPX());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean xoaPhieuXuat(String MaPX) {
        String sql = "DELETE FROM phieuxuat WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, MaPX);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }



    @Override
    public List<PXDTO> layDanhSachPhieuXuat() {
        List<PXDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM phieuxuat";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PXDTO px = new PXDTO(
                        rs.getInt("STT"),
                        rs.getString("MaPX"),
                        rs.getString("MaKH"),
                        rs.getString("MaNV"),
                        rs.getTimestamp("ThoiGian").toLocalDateTime(),
                        rs.getFloat("TongTien"),
                        rs.getString("TrangThai")
                );
                result.add(px);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public boolean kiemTraMaPXTonTai(String MaPX) {
        String sql = "SELECT COUNT(*) FROM phieuxuat WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, MaPX);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra mã phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Hàm sinh mã phiếu xuất tự động
    private String sinhMaPX() {
        String sql = "SELECT COUNT(*) FROM phieuxuat";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int stt = rs.getInt(1) + 1; // +1 vì sẽ thêm mới
                return String.format("PX_%03d", stt); // Ví dụ: PX_001, PX_012
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi sinh mã phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
