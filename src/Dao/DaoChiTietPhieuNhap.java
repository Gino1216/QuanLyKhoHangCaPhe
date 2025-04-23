package Dao;

import Config.Mysql;
import DTO.ChiTietPhieuNhapDTO;
import DTO.SanPhamDTO;
import Repository.ChiTietPhieuNhapRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoChiTietPhieuNhap implements ChiTietPhieuNhapRepo {

    @Override
    public void themChiTietPhieuNhap(ChiTietPhieuNhapDTO ct) {
        String sql = "INSERT INTO chitietpn (MaPN, MaSP, TenSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ct.getMaPN());
            stmt.setString(2, ct.getMaSP());
            stmt.setString(3, ct.getTenSP());
            stmt.setInt(4, ct.getSoLuong());
            stmt.setFloat(5, ct.getDonGia());
            stmt.setFloat(6, ct.getThanhTien());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết PN: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean suaChiTietPhieuNhap(ChiTietPhieuNhapDTO ct) {
        String sql = "UPDATE chitietpn SET TenSP = ?, SoLuong = ?, DonGia = ?, ThanhTien = ? WHERE MaPN = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ct.getTenSP());
            stmt.setInt(2, ct.getSoLuong());
            stmt.setFloat(3, ct.getDonGia());
            stmt.setFloat(4, ct.getThanhTien());
            stmt.setString(5, ct.getMaPN());
            stmt.setString(6, ct.getMaSP());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật chi tiết PN thành công!");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean xoaChiTietPhieuNhap(String maPN, String maSP) {
        String sql = "DELETE FROM chitietpn WHERE MaPN = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            stmt.setString(2, maSP);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Xóa chi tiết PN thành công!");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    @Override
    public List<ChiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaPN(String maPN) {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietpn WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO(
                            rs.getString("MaPN"),
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
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy chi tiết PN: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public boolean kiemTraTonTai(String maPN, String maSP) {
        String sql = "SELECT COUNT(*) FROM chitietpn WHERE MaPN = ? AND MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            stmt.setString(2, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra chi tiết PN: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }



    public List<ChiTietPhieuNhapDTO> layDanhSach() {
        List<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM chitietpn";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maPN = rs.getString("MaPN");
                String maSP = rs.getString("MaSP");
                String tenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuong");
                float donGia = rs.getFloat("DonGia");
                float thanhTien = rs.getFloat("ThanhTien");

                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO(maPN, maSP, tenSP, soLuong, donGia, thanhTien);
                result.add(ct);
            }

            System.out.println("Lấy danh sách: " + result.size() + " sản phẩm được lấy");
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sản phẩm: " + e);
        }
        return result;

    }

}