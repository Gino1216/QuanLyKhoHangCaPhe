package EX;

import DTO.TraHangDTO;
import Dao.DaoTraHang;
import Repository.TraHangRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExTraHang {

    public static void exportTraHangToExcel(String filePath) {
        // Khởi tạo repository để lấy danh sách trả hàng
        TraHangRepo repo = new DaoTraHang();
        List<TraHangDTO> traHangList = repo.layDanhSachTraHang();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachTraHang");

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

        // Tạo style cho dữ liệu số (Tổng Tiền Hoàn Trả)
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã Trả Hàng", "Mã Phiếu Xuất", "Mã Nhân Viên", "Mã Khách Hàng", "Ngày Trả", "Lý Do Trả", "Tổng Tiền Hoàn Trả", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (TraHangDTO th : traHangList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(th.getMaTraHang());
            row.createCell(1).setCellValue(th.getMaPX());
            row.createCell(2).setCellValue(th.getMaNV());
            row.createCell(3).setCellValue(th.getMaKH());
            row.createCell(4).setCellValue(th.getNgayTra());
            row.createCell(5).setCellValue(th.getLyDoTra());
            Cell tongTienCell = row.createCell(6);
            tongTienCell.setCellValue(th.getTongTienHoanTra());
            tongTienCell.setCellStyle(numberStyle);
            row.createCell(7).setCellValue(th.getTrangThai());
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