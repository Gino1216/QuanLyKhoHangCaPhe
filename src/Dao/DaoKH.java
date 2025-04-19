package Dao;

import Config.Mysql;
import DTO.KhachHangDTO;
import Repository.KhachHangRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoKH implements KhachHangRepo {

    @Override
    public void themKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO khachhang (MaKH, HoTen, DiaChi, NgayThamGia, Email, SoDT) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getDiaChi());
            stmt.setString(4, kh.getNgayThamGia());
            stmt.setString(5, kh.getEmail());
            stmt.setString(6, kh.getSoDT());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean suaKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE khachhang SET HoTen=?, DiaChi=?, NgayThamGia=?, Email=?, SoDT=? WHERE MaKH=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getHoTen());
            stmt.setString(2, kh.getDiaChi());
            stmt.setString(3, kh.getNgayThamGia());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getSoDT());
            stmt.setString(6, kh.getMaKH());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaKhachHang(String maKH) {
        String sql = "DELETE FROM khachhang WHERE MaKH=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKH);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể xóa khách hàng. Có thể đang được liên kết với hóa đơn hoặc thông tin khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<KhachHangDTO> timKiemKhachHang(String keyword) {
        List<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE MaKH LIKE ? OR HoTen LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("NgayThamGia"),
                        rs.getString("Email"),
                        rs.getString("SoDT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<KhachHangDTO> layDanhSachKhachHang() {
        List<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ds.add(new KhachHangDTO(
                        rs.getString("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("NgayThamGia"),
                        rs.getString("Email"),
                        rs.getString("SoDT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public boolean kiemTraMaKHTonTai(String maKH) {
        String sql = "SELECT MaKH FROM khachhang WHERE MaKH=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
