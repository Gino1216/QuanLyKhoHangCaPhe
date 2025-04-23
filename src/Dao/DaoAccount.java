package Dao;

import Config.Mysql;
import Config.Session;
import DTO.Account;
import DTO.PNDTO;
import DTO.TraHangDTO;
import Repository.AccountRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;





public class DaoAccount implements AccountRepo {

    public boolean kiemTraUsernameTonTai(String username) {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE username = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void themAccount(Account account) {
        String sql = "INSERT INTO taikhoan (MaNV, MaQuyen, username, pass) VALUES (?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getMaNV());
            stmt.setInt(2, account.getRole());
            stmt.setString(3, account.getUsername());
            stmt.setString(4, account.getPassword());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public boolean suaAccount(Account account) {
        String sql;
        PreparedStatement stmt;
        try (Connection conn = Mysql.getConnection()) {
            if (account.getPassword() == null || account.getPassword().isEmpty()) {
                sql = "UPDATE taikhoan SET MaQuyen = ? WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, account.getRole());
                stmt.setString(2, account.getUsername());
            } else {
                sql = "UPDATE taikhoan SET pass = ?, MaQuyen = ? WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, account.getPassword());
                stmt.setInt(2, account.getRole());
                stmt.setString(3, account.getUsername());
            }
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công!");
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean xoaAccount(String username) {
        if ("admin".equals(username)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa tài khoản admin!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String sql = "DELETE FROM taikhoan WHERE username = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
            }
            return affected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Account> timKiemAccount(String keyword) {
        List<Account> result = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan WHERE username LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Account acc = new Account(
                            rs.getString("username"),
                            rs.getString("pass"),
                            rs.getInt("MaQuyen")
                    );
                    result.add(acc);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public List<Account> layDanhSachAccount() {
        List<Account> result = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan WHERE MaQuyen < 3";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Account acc = new Account(
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getInt("MaQuyen")
                );
                result.add(acc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }



    public List<Account> layDanhSachAccountFull() {
        List<Account> result = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan ";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Account acc = new Account(
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getInt("MaQuyen")
                );
                result.add(acc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách tài khoản: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }










    @Override
    public Account checkLogin(String username, String password) {
        String sql = "SELECT * FROM taikhoan WHERE username = ? AND pass = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng Account, sẽ tự động lưu vào Session
                    return new Account(
                            rs.getString("username"),
                            rs.getString("pass"),
                            rs.getInt("MaQuyen")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đăng nhập: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }



    @Override
    public void xuatExcel(String filePath) {
        // Lấy danh sách tài khoản (giả sử có phương thức này)
        List<Account> accountList = layDanhSachAccount();

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachAccount");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã NV", "Username", "Password", "Role"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (Account account : accountList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(account.getMaNV());
            row.createCell(1).setCellValue(account.getUsername());
            row.createCell(2).setCellValue(account.getPassword());
            row.createCell(3).setCellValue(account.getRole());
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công tại: " + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}