package Dao;

import Config.Mysql;
import DTO.PhanQuyenDTO;
import Repository.PhanQuyenRepo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPQ implements PhanQuyenRepo {

    @Override
    public List<PhanQuyenDTO> layDanhSachQuyen() {
        List<PhanQuyenDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM phanquyen";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ds.add(new PhanQuyenDTO(
                        rs.getInt("MaQuyen"),
                        rs.getString("NoiDung")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public void themQuyen(PhanQuyenDTO quyen) {
        String sql = "INSERT INTO phanquyen (MaQuyen, NoiDung) VALUES (?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quyen.getMaQuyen());
            stmt.setString(2, quyen.getNoiDung());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean suaQuyen(PhanQuyenDTO quyen) {
        String sql = "UPDATE phanquyen SET NoiDung=? WHERE MaQuyen=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, quyen.getNoiDung());
            stmt.setInt(2, quyen.getMaQuyen());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaQuyen(int maQuyen) {
        String sql = "DELETE FROM phanquyen WHERE MaQuyen=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maQuyen);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể xóa quyền. Có thể đang được liên kết với người dùng hoặc bảng khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<PhanQuyenDTO> timKiemQuyen(String keyword) {
        List<PhanQuyenDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM phanquyen WHERE NoiDung LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(new PhanQuyenDTO(
                        rs.getInt("MaQuyen"),
                        rs.getString("NoiDung")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public boolean kiemTraMaQuyenTonTai(int maQuyen) {
        String sql = "SELECT MaQuyen FROM phanquyen WHERE MaQuyen=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maQuyen);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
