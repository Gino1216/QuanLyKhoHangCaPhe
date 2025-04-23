package EX;

import DTO.PXDTO;

import Dao.DaoPhieuXuat;
import Repository.PhieuXuatRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExPhieuXuat {

    public static void exportPhieuXuatToExcel(String filePath) {
        // Khởi tạo repository để lấy danh sách phiếu xuất
        PhieuXuatRepo repo = new DaoPhieuXuat();
        List<PXDTO> phieuXuatList = repo.layDanhSachPhieuXuat();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachPhieuXuat");

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

        // Tạo style cho dữ liệu số (Tổng Tiền)
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã Phiếu Xuất", "Mã Khách Hàng", "Mã Nhân Viên", "Thời Gian", "Tổng Tiền", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Định dạng thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Điền dữ liệu
        int rowNum = 1;
        for (PXDTO px : phieuXuatList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(px.getMaPX());
            row.createCell(1).setCellValue(px.getMaKhachHang());
            row.createCell(2).setCellValue(px.getMaNhanVien());
            row.createCell(3).setCellValue(px.getThoiGian().format(formatter));
            Cell tongTienCell = row.createCell(4);
            tongTienCell.setCellValue(px.getTongTien());
            tongTienCell.setCellStyle(numberStyle);
            row.createCell(5).setCellValue(px.getTrangThai());
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