package EX;

import DTO.NhaCungCapDTO;
import Dao.DaoNCC;
import Repository.NCCRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExNhaCungCap {

    public static void exportNhaCungCapToExcel(String filePath) {
        // Lấy danh sách nhà cung cấp
        NCCRepo repo = new DaoNCC();
        List<NhaCungCapDTO> dsNCC = repo.layDanhSachNhaCungCap();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachNhaCungCap");

        // Tạo style tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Tạo dòng tiêu đề
        String[] columns = {"Mã NCC", "Tên NCC", "Số ĐT", "Địa Chỉ", "Email", "Tình Trạng"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Ghi dữ liệu
        int rowNum = 1;
        for (NhaCungCapDTO ncc : dsNCC) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ncc.getMaNCC());
            row.createCell(1).setCellValue(ncc.getTenNCC());
            row.createCell(2).setCellValue(ncc.getSoDT());
            row.createCell(3).setCellValue(ncc.getDiaChi());
            row.createCell(4).setCellValue(ncc.getEmail());
            row.createCell(5).setCellValue(ncc.getTinhTrang());
        }

        // Tự động điều chỉnh độ rộng cột
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
