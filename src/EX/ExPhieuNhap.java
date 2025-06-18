package EX;

import DTO.PNDTO;
import Dao.DaoPhieuNhap;
import Repository.PhieuNhapRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExPhieuNhap {

    public static void exportPhieuNhapToExcel(String filePath) {
        // Khởi tạo repository để lấy danh sách phiếu nhập
        PhieuNhapRepo repo = new DaoPhieuNhap();
        List<PNDTO> phieuNhapList = repo.layDanhSachPhieuNhap();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachPhieuNhap");

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
        String[] columns = {"Mã Phiếu Nhập", "Mã Nhà Cung Cấp", "Mã Nhân Viên", "Ngày Nhập", "Tổng Tiền", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (PNDTO pn : phieuNhapList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(pn.getMaPN());
            row.createCell(1).setCellValue(pn.getMaNCC());
            row.createCell(2).setCellValue(pn.getMaNV());
            row.createCell(3).setCellValue(pn.getNgayNhap());
            Cell tongTienCell = row.createCell(4);
            tongTienCell.setCellValue(pn.getTongTien());
            tongTienCell.setCellStyle(numberStyle);
            row.createCell(5).setCellValue(pn.getTrangThai());
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