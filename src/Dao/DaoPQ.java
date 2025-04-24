package Dao;

import Config.Mysql;
import DTO.PhanQuyenDTO;
import DTO.SanPhamDTO;
import Repository.PhanQuyenRepo;
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

    @Override
    public void xuatExcel(String filePath) {
        // Lấy danh sách phân quyền (giả sử có phương thức này)
        List<PhanQuyenDTO> phanQuyenList = layDanhSachQuyen();

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachPhanQuyen");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã Quyền", "Tên Quyền"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (PhanQuyenDTO pq : phanQuyenList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(pq.getMaQuyen());
            row.createCell(1).setCellValue(pq.getNoiDung());
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
