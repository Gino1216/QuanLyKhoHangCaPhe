package Dao;

import Config.Mysql;
import DTO.ChiTietPhieuXuatDTO;
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




    public boolean DuyetPhieuXuat(PXDTO phieuXuat) {
        String sql = "UPDATE phieuxuat SET TrangThai = ? WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Hoàn thành");
            stmt.setString(2, phieuXuat.getMaPX());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public boolean keToanDuyetPhieuXuat(PXDTO phieuXuat) {
        String sql = "UPDATE phieuxuat SET TrangThai = ? WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Kế toán duyệt");
            stmt.setString(2, phieuXuat.getMaPX());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<String> layDanhSachPhieuXuatTongTienBang0() {
        List<String> maPXList = new ArrayList<>();
        String sql = "SELECT MaPX FROM phieuxuat WHERE TongTien IS NULL ORDER BY MaPX";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                maPXList.add(rs.getString("MaPX"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách mã phiếu xuất: " + e.getMessage());

        }

        return maPXList;
    }



    public boolean HuyDuyetPhieuXuat(PXDTO phieuXuat) {
        String sql = "UPDATE phieuxuat SET TrangThai = ? WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Không duyệt");
            stmt.setString(2, phieuXuat.getMaPX());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public List<PXDTO> layDanhSachPhieuXuatTheoMaPXVaTrangThaiHoanThanh(String maPX) {
        List<PXDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM phieuxuat WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PXDTO px = new PXDTO(
                            rs.getString("MaPX"),
                            rs.getString("MaKH"),
                            rs.getString("MaNV"),
                            rs.getTimestamp("ThoiGian").toLocalDateTime(),
                            rs.getFloat("TongTien"),
                            rs.getString("TrangThai")
                    );
                    result.add(px);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phiếu xuất theo mã: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }


    public List<PXDTO> getPhieuXuatChuaTraHang() {
        List<PXDTO> danhSach = new ArrayList<>();

        String sql = "SELECT * FROM phieuxuat WHERE maPX NOT IN (SELECT maPX FROM trahang)";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PXDTO px = new PXDTO();
                px.setMaPX(rs.getString("maPX"));
                px.setMaKhachHang(rs.getString("maKH"));
                px.setMaNhanVien(rs.getString("maNV"));
                px.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime());
                px.setTongTien(rs.getFloat("tongTien"));
                px.setTrangThai(rs.getString("trangThai"));

                danhSach.add(px);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return danhSach;
    }

    @Override
    public void themMaPxVaTT(String maPX) {
        String sql = "INSERT INTO phieuxuat(maPX) VALUES (?)";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPX);
            int affectedRows = stmt.executeUpdate(); // THÊM DÒNG NÀY

            if (affectedRows == 0) {
                throw new SQLException("Thêm mã phiếu xuất thất bại, không có dòng nào bị ảnh hưởng.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm mã phiếu xuất: " + e.getMessage(), e);
        }

    }

    public String sinhMaPX() {
        String sql = "SELECT MAX(MaPX) FROM phieuxuat";
        String newMaPX = null;

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int nextNumber = 1;
            if (rs.next() && rs.getString(1) != null) {
                String lastMaPX = rs.getString(1);
                String numberPart = lastMaPX.substring(3);
                nextNumber = Integer.parseInt(numberPart) + 1;
            }

            newMaPX = String.format("PX%03d", nextNumber);

            // Kiểm tra tối đa 10 lần
            int attempts = 0;
            while (kiemTraMaPXTonTai(newMaPX) && attempts < 10) {
                nextNumber++;
                newMaPX = String.format("PX_%03d", nextNumber);
                attempts++;
            }



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return newMaPX;
    }


    public List<String> layDanhSachMaPX() {
        List<String> maPXList = new ArrayList<>();
        String sql = "SELECT MaPX FROM phieuxuat ORDER BY MaPX";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                maPXList.add(rs.getString("MaPX"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Có thể thêm logging hoặc thông báo lỗi
        }
        return maPXList;
    }


    public boolean capNhatPhieuXuat(String maPX, String maKH, String maNV, float tongTien) {
        // Kiểm tra MaPX có hợp lệ không
        if (maPX == null || maPX.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã phiếu xuất không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "UPDATE phieuxuat SET MaKH = ?, MaNV = ?, TongTien = ?, TrangThai = ? WHERE MaPX = ?";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập các giá trị cần cập nhật
            stmt.setString(1, maKH);
            stmt.setString(2, maNV);
            stmt.setFloat(3, tongTien);
            stmt.setString(4, "Chưa duyệt");  // Mặc định trạng thái
            stmt.setString(5, maPX);           // Điều kiện WHERE (MaPX)

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu xuất với mã: " + maPX, "Lỗi", JOptionPane.WARNING_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
