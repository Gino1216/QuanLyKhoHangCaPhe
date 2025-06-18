package EX;

import DTO.NhanVienDTO;
import Dao.DaoNV;
import Repository.NhanVienRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExNhanVien {

    public static void exportNhanVienToExcel(String filePath) {
        // Khởi tạo repository để lấy danh sách nhân viên
        NhanVienRepo repo = new DaoNV();
        List<NhanVienDTO> nhanVienList = repo.layDanhSachNhanVien();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachNhanVien");

        // Tạo style cho tiêu đề
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "Số ĐT", "Email", "Địa Chỉ", "Chức Vụ"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (NhanVienDTO nv : nhanVienList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(nv.getMaNV());
            row.createCell(1).setCellValue(nv.getHoTen());
            row.createCell(2).setCellValue(nv.getGioiTinh());
            row.createCell(3).setCellValue(nv.getNgaySinh());
            row.createCell(4).setCellValue(nv.getSoDT());
            row.createCell(5).setCellValue(nv.getEmail());
            row.createCell(6).setCellValue(nv.getDiaChi());
            row.createCell(7).setCellValue(nv.getChucVu());
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