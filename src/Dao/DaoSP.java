package Dao;

import Config.Mysql;
import DTO.SanPhamDTO;
import Repository.SanPhamRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoSP implements SanPhamRepo {

    @Override
    public void themSanPham(SanPhamDTO sp) {
        String sql = "INSERT INTO sanpham (MaSP, TenSP, SoLuong, TinhTrang, HanSD, GiaNhap, GiaXuat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setInt(3, sp.getSoLuong());
            stmt.setString(4, sp.getTinhTrang());
            stmt.setString(5, sp.getHanSD());
            stmt.setFloat(6, sp.getGiaNhap());
            stmt.setFloat(7, sp.getGiaXuat());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm sản phẩm: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean suaSanPham(SanPhamDTO sp) {
        String sql = "UPDATE sanpham SET TenSP = ?, SoLuong = ?, TinhTrang = ?, HanSD = ?, GiaNhap = ?, GiaXuat = ? WHERE MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sp.getTenSP());
            stmt.setInt(2, sp.getSoLuong());
            stmt.setString(3, sp.getTinhTrang());
            stmt.setString(4, sp.getHanSD());
            stmt.setFloat(5, sp.getGiaNhap());
            stmt.setFloat(6, sp.getGiaXuat());
            stmt.setString(7, sp.getMaSP());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Sửa sản phẩm: " + rowsAffected + " dòng affected");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean xoaSanPham(String maSP) {
        String checkSql = "SELECT COUNT(*) FROM chitietpx WHERE MaSP = ?";
        String deleteSql = "DELETE FROM sanpham WHERE MaSP = ?";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            // Kiểm tra ràng buộc
            checkStmt.setString(1, maSP);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null,
                        "Không thể xóa sản phẩm vì đã có trong hóa đơn",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Nếu không có ràng buộc thì xóa
            deleteStmt.setString(1, maSP);
            int affected = deleteStmt.executeUpdate();
            if (affected == 0) {
                System.err.println("Không tìm thấy sản phẩm với MaSP: " + maSP);
            }
            return affected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa sản phẩm: " + e.getMessage());
            throw new RuntimeException("Lỗi khi xóa sản phẩm: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SanPhamDTO> timKiemSanPham(String keyword) {
        List<SanPhamDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM sanpham WHERE MaSP LIKE ? OR TenSP LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSP(rs.getString("MaSP"));
                    sp.setTenSP(rs.getString("TenSP"));
                    sp.setSoLuong(rs.getInt("SoLuong"));
                    sp.setTinhTrang(rs.getString("TinhTrang"));
                    sp.setHanSD(rs.getString("HanSD"));
                    sp.setGiaNhap(rs.getFloat("GiaNhap"));
                    sp.setGiaXuat(rs.getFloat("GiaXuat"));
                    result.add(sp);
                }
            }
            System.out.println("Tìm kiếm: " + result.size() + " sản phẩm tìm thấy với từ khóa: " + keyword);
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<SanPhamDTO> layDanhSachSanPham() {
        List<SanPhamDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM sanpham";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(rs.getString("MaSP"));
                sanPhamDTO.setTenSP(rs.getString("TenSP"));
                sanPhamDTO.setSoLuong(rs.getInt("SoLuong"));
                sanPhamDTO.setTinhTrang(rs.getString("TinhTrang"));
                sanPhamDTO.setHanSD(rs.getString("HanSD"));
                sanPhamDTO.setGiaNhap(rs.getFloat("GiaNhap"));
                sanPhamDTO.setGiaXuat(rs.getFloat("GiaXuat"));
                result.add(sanPhamDTO);
            }

            System.out.println("Lấy danh sách: " + result.size() + " sản phẩm được lấy");
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sản phẩm: " + e);
        }

        return result;
    }

    @Override
    public boolean kiemTraMaSPTonTai(String maSP) {
        String sql = "SELECT MaSP FROM sanpham WHERE MaSP = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Nếu có dòng trả về => mã đã tồn tại
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void xuatExcel(String filePath) {
        // Lấy danh sách sản phẩm
        List<SanPhamDTO> sanPhamList = layDanhSachSanPham();

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachSanPham");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Tình Trạng", "Hạn SD", "Giá Nhập", "Giá Xuất"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (SanPhamDTO sp : sanPhamList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sp.getMaSP());
            row.createCell(1).setCellValue(sp.getTenSP());
            row.createCell(2).setCellValue(sp.getSoLuong());
            row.createCell(3).setCellValue(sp.getTinhTrang());
            row.createCell(4).setCellValue(sp.getHanSD());
            row.createCell(5).setCellValue(sp.getGiaNhap());
            row.createCell(6).setCellValue(sp.getGiaXuat());
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

    @Override
    public int demSoLuongSanPham() {
        int count = 0;
        try (Connection conn = Mysql.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sanpham")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}