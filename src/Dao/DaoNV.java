package Dao;

import Config.Mysql;
import DTO.NhanVienDTO;
import Repository.NhanVienRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoNV implements NhanVienRepo {

    @Override
    public void themNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO nhanvien (MaNV, HoTen, GioiTinh, NgaySinh, SoDT, Email, DiaChi, ChucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getMaNV());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getGioiTinh());
            stmt.setString(4, nv.getNgaySinh());
            stmt.setString(5, nv.getSoDT());
            stmt.setString(6, nv.getEmail());
            stmt.setString(7, nv.getDiaChi());
            stmt.setString(8, nv.getChucVu());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<String> getMaNVWithoutAccount() {
        List<String> maNVList = new ArrayList<>();
        String sql = "SELECT MaNV FROM nhanvien WHERE MaNV NOT IN (SELECT MaNV FROM taikhoan)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                maNVList.add(rs.getString("MaNV"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNVList;
    }

    @Override
    public boolean suaNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE nhanvien SET HoTen=?, GioiTinh=?, NgaySinh=?, SoDT=?, Email=?, DiaChi=?, ChucVu=? WHERE MaNV=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getGioiTinh());
            stmt.setString(3, nv.getNgaySinh());
            stmt.setString(4, nv.getSoDT());
            stmt.setString(5, nv.getEmail());
            stmt.setString(6, nv.getDiaChi());
            stmt.setString(7, nv.getChucVu());
            stmt.setString(8, nv.getMaNV());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaNhanVien(String maNV) {
        String sql = "DELETE FROM nhanvien WHERE MaNV=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNV);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhân viên. Có thể đang được sử dụng trong hóa đơn hoặc công việc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<NhanVienDTO> timKiemNhanVien(String keyword) {
        List<NhanVienDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien WHERE MaNV LIKE ? OR HoTen LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("SoDT"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<NhanVienDTO> layDanhSachNhanVien() {
        List<NhanVienDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ds.add(new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("NgaySinh"),
                        rs.getString("SoDT"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public boolean kiemTraMaNVTonTai(String maNV) {
        String sql = "SELECT MaNV FROM nhanvien WHERE MaNV=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NhanVienDTO layNhanVienTheoMaNV(String maNV) {
        String sql = "SELECT * FROM nhanvien WHERE MaNV = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien.setMaNV(rs.getString("MaNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setGioiTinh(rs.getString("GioiTinh"));
                nhanVien.setNgaySinh(rs.getString("NgaySinh"));
                nhanVien.setSoDT(rs.getString("SoDT"));
                nhanVien.setEmail(rs.getString("Email"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVien.setChucVu(rs.getString("ChucVu"));
                return nhanVien;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
