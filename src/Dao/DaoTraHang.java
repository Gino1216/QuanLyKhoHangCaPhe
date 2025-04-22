package Dao;

import Config.Mysql;
import DTO.TraHangDTO;
import Repository.TraHangRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DaoTraHang implements TraHangRepo {

    @Override
    public boolean themTraHang(TraHangDTO traHang) {
        String sql = "INSERT INTO trahang (MaTraHang, MaPX, MaNV, MaKH, NgayTra, LyDoTra, TongTienHoanTra, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, traHang.getMaTraHang());
            stmt.setString(2, traHang.getMaPX());
            stmt.setString(3, traHang.getMaNV());
            stmt.setString(4, traHang.getMaKH());
            stmt.setString(5, traHang.getNgayTra());
            stmt.setString(6, traHang.getLyDoTra());
            stmt.setFloat(7, traHang.getTongTienHoanTra());
            stmt.setString(8, traHang.getTrangThai());

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm trả hàng thành công!");
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean suaTraHang(TraHangDTO traHang) {
        String sql = "UPDATE trahang SET MaPX = ?, MaNV = ?, MaKH = ?, NgayTra = ?, LyDoTra = ?, TongTienHoanTra = ?, TrangThai = ? WHERE MaTraHang = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, traHang.getMaPX());
            stmt.setString(2, traHang.getMaNV());
            stmt.setString(3, traHang.getMaKH());
            stmt.setString(4, traHang.getNgayTra());
            stmt.setString(5, traHang.getLyDoTra());
            stmt.setFloat(6, traHang.getTongTienHoanTra());
            stmt.setString(7, traHang.getTrangThai());
            stmt.setString(8, traHang.getMaTraHang());

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cập nhật trả hàng thành công!");
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean xoaTraHang(String maTraHang) {
        String sql = "DELETE FROM trahang WHERE MaTraHang = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTraHang);
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa trả hàng thành công!");
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public TraHangDTO timTheoMa(String maTraHang) {
        String sql = "SELECT * FROM trahang WHERE MaTraHang = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTraHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TraHangDTO(
                            rs.getString("MaTraHang"),
                            rs.getString("MaPX"),
                            rs.getString("MaNV"),
                            rs.getString("MaKH"),
                            rs.getString("NgayTra"),
                            rs.getString("LyDoTra"),
                            rs.getFloat("TongTienHoanTra"),
                            rs.getString("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<TraHangDTO> timKiemTraHang(String keyword) {
        List<TraHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM trahang WHERE MaTraHang LIKE ? OR MaPX LIKE ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TraHangDTO dto = new TraHangDTO(
                            rs.getString("MaTraHang"),
                            rs.getString("MaPX"),
                            rs.getString("MaNV"),
                            rs.getString("MaKH"),
                            rs.getString("NgayTra"),
                            rs.getString("LyDoTra"),
                            rs.getFloat("TongTienHoanTra"),
                            rs.getString("TrangThai")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public List<TraHangDTO> layDanhSachTraHang() {
        List<TraHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM trahang";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TraHangDTO dto = new TraHangDTO(
                        rs.getString("MaTraHang"),
                        rs.getString("MaPX"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        rs.getString("NgayTra"),
                        rs.getString("LyDoTra"),
                        rs.getFloat("TongTienHoanTra"),
                        rs.getString("TrangThai")
                );
                list.add(dto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public void xuatExcel(String filePath) {
        List<TraHangDTO> danhSach = layDanhSachTraHang();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachTraHang");

        String[] headers = {"Mã Trả Hàng", "Mã PX", "Mã NV", "Mã KH", "Ngày Trả", "Lý Do Trả", "Tổng Tiền Hoàn Trả", "Trạng Thái"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowIndex = 1;
        for (TraHangDTO dto : danhSach) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(dto.getMaTraHang());
            row.createCell(1).setCellValue(dto.getMaPX());
            row.createCell(2).setCellValue(dto.getMaNV());
            row.createCell(3).setCellValue(dto.getMaKH());
            row.createCell(4).setCellValue(dto.getNgayTra());
            row.createCell(5).setCellValue(dto.getLyDoTra());
            row.createCell(6).setCellValue(dto.getTongTienHoanTra());
            row.createCell(7).setCellValue(dto.getTrangThai());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công tại: " + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean kiemTraMaPXTonTaiTrongTraHang(String maPX) {
        String sql = "SELECT COUNT(*) FROM trahang WHERE MaPX = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPX);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra mã phiếu xuất trong trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    @Override
    public boolean kiemTraMaTraHangTonTai(String maTraHang) {
        String sql = "SELECT COUNT(*) FROM trahang WHERE MaTraHang = ?";
        try (Connection conn = Mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTraHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra mã trả hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }



    @Override
    public String sinhMaTraHang() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder ma = new StringBuilder("TH_");
        Random rand = new Random();

        // Tạo mã gồm 6 ký tự ngẫu nhiên
        for (int i = 0; i < 6; i++) {
            int index = rand.nextInt(characters.length());
            ma.append(characters.charAt(index));
        }

        return ma.toString(); // Ví dụ: TH_X7A9F2
    }




}
