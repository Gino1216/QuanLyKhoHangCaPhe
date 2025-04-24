package Dao;

import Config.Mysql;
import DTO.PNDTO;
import Repository.PhieuNhapRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPhieuNhap implements PhieuNhapRepo {

    @Override
    public void themPhieuNhap(PNDTO phieuNhap) {
        String maPN = sinhMaPN();
        if (maPN == null) return;

        String sql = "INSERT INTO phieunhap (MaPN, MaNCC, MaNV, NgayNhap, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            stmt.setString(2, phieuNhap.getMaNCC());
            stmt.setString(3, phieuNhap.getMaNV());
            stmt.setString(4, phieuNhap.getNgayNhap());
            stmt.setFloat(5, phieuNhap.getTongTien());
            stmt.setString(6, phieuNhap.getTrangThai());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean suaPhieuNhap(PNDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET MaNCC = ?, MaNV = ?, NgayNhap = ?, TongTien = ?, TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phieuNhap.getMaNCC());
            stmt.setString(2, phieuNhap.getMaNV());
            stmt.setString(3, phieuNhap.getNgayNhap());
            stmt.setFloat(4, phieuNhap.getTongTien());
            stmt.setString(5, phieuNhap.getTrangThai());
            stmt.setString(6, phieuNhap.getMaPN());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean xoaPhieuNhap(String MaPN) {
        String sql = "DELETE FROM phieunhap WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, MaPN);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean keToanDuyetPhieuNhap(PNDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Kế toán duyệt");
            stmt.setString(2, phieuNhap.getMaPN());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi duyệt phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public boolean duyetPhieuNhap(PNDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Hoàn thành");
            stmt.setString(2, phieuNhap.getMaPN());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi duyệt phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public boolean huyDuyetPhieuNhap(PNDTO phieuNhap) {
        String sql = "UPDATE phieunhap SET TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Không duyệt");
            stmt.setString(2, phieuNhap.getMaPN());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi hủy duyệt phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public int demSoLuongPNchuaDuyet() {
        int soLuong = 0;
        String sql = "SELECT COUNT(*) AS SoLuong FROM phieunhap WHERE TrangThai = 'Chưa duyệt'";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi đếm số lượng phiếu nhập: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return soLuong;
    }


    public List<PNDTO> layDanhSachPhieuNhap() {
        List<PNDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PNDTO pn = new PNDTO(
                        rs.getString("MaPN"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV"),
                        rs.getString("NgayNhap"),
                        rs.getFloat("TongTien"),
                        rs.getString("TrangThai")
                        );
                result.add(pn);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public float tinhTongTienPNDaDuyet() {
        float tongTien = 0;
        String sql = "SELECT SUM(TongTien) AS TongTien FROM phieunhap WHERE TrangThai =  'Hoàn thành'";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                tongTien = rs.getFloat("TongTien");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tính tổng tiền phiếu nhập đã duyệt: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return tongTien;
    }




    public List<String> layDanhSachPhieuNhapTongTienBang0() {
        List<String> maPNList = new ArrayList<>();
        String sql = "SELECT MaPN FROM phieunhap WHERE TongTien IS NULL ORDER BY MaPN";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                maPNList.add(rs.getString("MaPN"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách mã phiếu nhập: " + e.getMessage());

        }

        return maPNList;
    }












    public boolean kiemTraMaPNTonTai(String MaPN) {
        String sql = "SELECT COUNT(*) FROM phieunhap WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, MaPN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra mã phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public List<PNDTO> layDanhSachPhieuNhapTheoMaPNVaTrangThaiHoanThanh(String maPN) {
        List<PNDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PNDTO pn = new PNDTO(
                            rs.getString("MaPN"),
                            rs.getString("MaNCC"),
                            rs.getString("MaNV"),
                            rs.getString("NgayNhap"),
                            rs.getFloat("TongTien"),
                            rs.getString("TrangThai")
                    );
                    result.add(pn);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phiếu nhập theo mã: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }




    public void themMaPnVaTT(String maPN) {
        String sql = "INSERT INTO phieunhap(MaPN) VALUES (?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm mã phiếu nhập thất bại, không có dòng nào bị ảnh hưởng.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm mã phiếu nhập: " + e.getMessage(), e);
        }
    }

    public String sinhMaPN() {
        String sql = "SELECT MAX(MaPN) FROM phieunhap";
        String newMaPN = null;
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int nextNumber = 1;
            if (rs.next() && rs.getString(1) != null) {
                String lastMaPN = rs.getString(1);
                String numberPart = lastMaPN.substring(2);
                nextNumber = Integer.parseInt(numberPart) + 1;
            }
            newMaPN = String.format("PN%03d", nextNumber);
            int attempts = 0;
            while (kiemTraMaPNTonTai(newMaPN) && attempts < 10) {
                nextNumber++;
                newMaPN = String.format("PN%03d", nextNumber);
                attempts++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi sinh mã phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return newMaPN;
    }



    public boolean capNhatPhieuNhap(String maPN, String maNCC, String maNV, float tongTien) {
        if (maPN == null || maPN.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã phiếu nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String sql = "UPDATE phieunhap SET MaNCC = ?, MaNV = ?, TongTien = ?, TrangThai = ? WHERE MaPN = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNCC);
            stmt.setString(2, maNV);
            stmt.setFloat(3, tongTien);
            stmt.setString(4, "Chưa duyệt");
            stmt.setString(5, maPN);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập với mã: " + maPN, "Lỗi", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}