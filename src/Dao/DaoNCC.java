package Dao;
import Config.Mysql;
import DTO.NhaCungCapDTO;
import Repository.NCCRepo;
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

public class DaoNCC implements NCCRepo {

    @Override
    public void themNhaCungCap(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO nhacungcap (MaNCC, TenNCC, SoDT, DiaChi, Email, TinhTrang) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ncc.getMaNCC());
            stmt.setString(2, ncc.getTenNCC());
            stmt.setString(3, ncc.getSoDT());
            stmt.setString(4, ncc.getDiaChi());
            stmt.setString(5, ncc.getEmail());
            stmt.setString(6, ncc.getTinhTrang());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean suaNhaCungCap(NhaCungCapDTO ncc) {
        String sql = "UPDATE nhacungcap SET TenNCC=?, SoDT=?, DiaChi=?, Email=?, TinhTrang=? WHERE MaNCC=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2, ncc.getSoDT());
            stmt.setString(3, ncc.getDiaChi());
            stmt.setString(4, ncc.getEmail());
            stmt.setString(5, ncc.getTinhTrang());
            stmt.setString(6, ncc.getMaNCC());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaNhaCungCap(String maNCC) {
        String sql = "DELETE FROM nhacungcap WHERE MaNCC=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNCC);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhà cung cấp. Có thể đang được sử dụng trong hóa đơn hoặc sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<NhaCungCapDTO> timKiemNhaCungCap(String keyword) {
        List<NhaCungCapDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE MaNCC LIKE ? OR TenNCC LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(new NhaCungCapDTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDT"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("TinhTrang")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<NhaCungCapDTO> layDanhSachNhaCungCap() {
        List<NhaCungCapDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ds.add(new NhaCungCapDTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDT"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("TinhTrang")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public boolean kiemTraMaNCCTonTai(String maNCC) {
        String sql = "SELECT MaNCC FROM nhacungcap WHERE MaNCC=?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void xuatExcel(String filePath) {
        // Lấy danh sách nhà cung cấp
        List<NhaCungCapDTO> danhSachNCC = layDanhSachNhaCungCap(); // Hàm này nên nằm trong lớp DaoNCC

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachNhaCungCap");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã NCC", "Tên NCC", "Số ĐT", "Địa Chỉ", "Email", "Tình Trạng"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Ghi dữ liệu
        int rowNum = 1;
        for (NhaCungCapDTO ncc : danhSachNCC) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ncc.getMaNCC());
            row.createCell(1).setCellValue(ncc.getTenNCC());
            row.createCell(2).setCellValue(ncc.getSoDT());
            row.createCell(3).setCellValue(ncc.getDiaChi());
            row.createCell(4).setCellValue(ncc.getEmail());
            row.createCell(5).setCellValue(ncc.getTinhTrang());
        }

        // Tự động căn chỉnh độ rộng cột
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi ra file
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

