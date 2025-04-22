package EX;

import DTO.SanPhamDTO;
import Dao.DaoSP;
import Repository.SanPhamRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class ExSanPham {

    public static void exportSanPhamToExcel(String filePath) {
        // Khởi tạo repository để lấy danh sách sản phẩm
        SanPhamRepo repo = new DaoSP();
        List<SanPhamDTO> sanPhamList = repo.layDanhSachSanPham();

        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachSanPham");

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

        // Tạo style cho dữ liệu số (Giá Nhập, Giá Xuất)
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Tình Trạng", "Hạn SD", "Giá Nhập", "Giá Xuất"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
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
            Cell giaNhapCell = row.createCell(5);
            giaNhapCell.setCellValue(sp.getGiaNhap());
            giaNhapCell.setCellStyle(numberStyle);
            Cell giaXuatCell = row.createCell(6);
            giaXuatCell.setCellValue(sp.getGiaXuat());
            giaXuatCell.setCellStyle(numberStyle);
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