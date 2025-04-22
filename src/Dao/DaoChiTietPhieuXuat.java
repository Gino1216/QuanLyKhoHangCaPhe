package Dao;

import Config.Mysql;
import DTO.ChiTietPhieuXuatDTO;
import Repository.ChiTietPhieuXuatRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DaoChiTietPhieuXuat implements ChiTietPhieuXuatRepo {

    @Override
    public void themChiTietPhieuXuat(ChiTietPhieuXuatDTO ct) {
        String sql = "INSERT INTO chitietpx (MaPX, MaSP, TenSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ct.getMaPX());
            stmt.setString(2, ct.getMaSP());
            stmt.setString(3, ct.getSanPham());
            stmt.setInt(4, ct.getSoLuong());
            stmt.setFloat(5, ct.getDonGia());
            stmt.setFloat(6, ct.getThanhTien());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết PX: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean suaChiTietPhieuXuat(ChiTietPhieuXuatDTO ct) {
        String sql = "UPDATE chitietpx SET TenSP = ?, SoLuong = ?, DonGia = ?, ThanhTien = ? WHERE MaPX = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ct.getSanPham());
            stmt.setInt(2, ct.getSoLuong());
            stmt.setFloat(3, ct.getDonGia());
            stmt.setFloat(4, ct.getThanhTien());
            stmt.setString(5, ct.getMaPX());
            stmt.setString(6, ct.getMaSP());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật chi tiết PX thành công!");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }










    @Override
    public boolean xoaChiTietPhieuXuat(String maPX, String maSP) {
        String sql = "DELETE FROM chitietpx WHERE MaPX = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            stmt.setString(2, maSP);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Xóa chi tiết PX thành công!");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public List<ChiTietPhieuXuatDTO> layChiTietPhieuXuatTheoMaPX(String maPX) {
        List<ChiTietPhieuXuatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietpx WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuXuatDTO ct = new ChiTietPhieuXuatDTO(
                            rs.getString("MaPX"),
                            rs.getString("MaSP"),
                            rs.getString("TenSP"),
                            rs.getInt("SoLuong"),
                            rs.getFloat("DonGia"),
                            rs.getFloat("ThanhTien")
                    );
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy chi tiết PX: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    // Thêm phương thức mới để lấy thông tin chi tiết phiếu xuất theo mã PX và trạng thái hoàn thành
    public List<ChiTietPhieuXuatDTO> layChiTietPhieuXuatHoanThanhTheoMaPX(String maPX) {
        List<ChiTietPhieuXuatDTO> list = new ArrayList<>();
        String sql = "SELECT c.* FROM chitietpx c JOIN phieuxuat p ON c.MaPX = p.MaPX WHERE c.MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuXuatDTO ct = new ChiTietPhieuXuatDTO(
                            rs.getString("MaPX"),
                            rs.getString("MaSP"),
                            rs.getString("TenSP"),
                            rs.getInt("SoLuong"),
                            rs.getFloat("DonGia"),
                            rs.getFloat("ThanhTien")
                    );
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy chi tiết PX hoàn thành: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public boolean kiemTraTonTai(String maPX, String maSP) {
        String sql = "SELECT COUNT(*) FROM chitietpx WHERE MaPX = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            stmt.setString(2, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra chi tiết PX: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }



}