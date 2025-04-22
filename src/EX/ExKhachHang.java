package EX;

import DTO.KhachHangDTO;
import Dao.DaoKH;
import Repository.KhachHangRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExKhachHang {

    public static void exportKhachHangToExcel(String filePath) {
        // Khởi tạo repository
        KhachHangRepo repo = new DaoKH();
        List<KhachHangDTO> khachHangList = repo.layDanhSachKhachHang();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachKhachHang");

        // Tạo style cho tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã KH", "Họ Tên", "Địa Chỉ", "Ngày Tham Gia", "Email", "Số ĐT"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);  // Áp dụng style cho tiêu đề
        }

        // Ghi dữ liệu
        int rowNum = 1;
        for (KhachHangDTO kh : khachHangList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(kh.getMaKH());
            row.createCell(1).setCellValue(kh.getHoTen());
            row.createCell(2).setCellValue(kh.getDiaChi());
            row.createCell(3).setCellValue(kh.getNgayThamGia());
            row.createCell(4).setCellValue(kh.getEmail());
            row.createCell(5).setCellValue(kh.getSoDT());
        }

        // Tự động điều chỉnh độ rộng
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
