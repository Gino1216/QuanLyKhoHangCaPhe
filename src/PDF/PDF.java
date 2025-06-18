package PDF;

import DTO.ChiTietPhieuXuatDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDF {

    public void exportToPDF(Component parent, String title, String[] labels, String[] values,
                            List<ChiTietPhieuXuatDTO> coffeeItems, String outputPath) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (labels == null || values == null || labels.length != values.length) {
                throw new IllegalArgumentException("Nhãn hoặc giá trị không hợp lệ!");
            }
            if (outputPath == null || outputPath.isEmpty()) {
                throw new IllegalArgumentException("Đường dẫn file không hợp lệ!");
            }

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load font hỗ trợ Unicode
            File fontFile = new File("E:/arial.ttf"); // Cập nhật đường dẫn chính xác
            if (!fontFile.exists()) {
                throw new IOException("Font file không tồn tại: " + fontFile.getAbsolutePath());
            }
            PDType0Font font = PDType0Font.load(document, fontFile);

            // Tiêu đề chính
            contentStream.beginText();
            contentStream.setFont(font, 16);
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText(title);
            contentStream.endText();

            // Vẽ đường kẻ dưới tiêu đề
            contentStream.setLineWidth(1f);
            contentStream.moveTo(200, 740);
            contentStream.lineTo(400, 740);
            contentStream.stroke();

            // Nội dung chi tiết (Thông tin phiếu xuất)
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.setLeading(20f);
            contentStream.newLineAtOffset(50, 700);

            for (int i = 0; i < labels.length; i++) {
                contentStream.showText(labels[i] + " " + values[i]);
                contentStream.newLine();
            }

            contentStream.endText();


            // Xuất bảng danh sách sản phẩm
            if (coffeeItems != null && !coffeeItems.isEmpty()) {
                System.out.println("Đang xuất bảng với " + coffeeItems.size() + " sản phẩm");
                for (ChiTietPhieuXuatDTO item : coffeeItems) {
                    System.out.println("Sản phẩm: " + item.getSanPham() + ", Số lượng: " + item.getSoLuong());
                }

                // Tiêu đề "Danh sách sản phẩm"
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset(210, 560);
                contentStream.showText("Danh sách sản phẩm:");
                contentStream.endText();


                // Tiêu đề bảng
                String[] headers = {"Mã PX", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
                float[] columnWidths = {50, 30, 20, 10, 10,  20}; // Điều chỉnh kích thước cột
                float tableY = 520;
                float tableX = 30;

                // Vẽ tiêu đề bảng
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(tableX, tableY); // Đặt tọa độ Y chính xác
                float x = tableX;
                for (int i = 0; i < headers.length; i++) {
                    contentStream.newLineAtOffset(x - tableX, 0);
                    contentStream.showText(headers[i]);
                    x += columnWidths[i];
                }
                contentStream.endText();

                // Vẽ đường kẻ ngang dưới tiêu đề
                contentStream.setLineWidth(1f);
                contentStream.moveTo(tableX, tableY - 10);
                contentStream.lineTo(tableX + 550, tableY - 10);
                contentStream.stroke();

                // Vẽ dữ liệu bảng
                tableY -= 30;
                for (ChiTietPhieuXuatDTO item : coffeeItems) {
                    contentStream.beginText();
                    contentStream.setFont(font, 12);
                    contentStream.newLineAtOffset(tableX, tableY); // Đặt tọa độ Y cho mỗi dòng
                    x = tableX;
                    String[] rowData = {
                            item.getMaPX() != null ? item.getMaPX() : "",
                            item.getMaSP() != null ? item.getMaSP() : "",
                            item.getSanPham() != null ? item.getSanPham() : "",
                            String.valueOf(item.getSoLuong()),
                            String.format("%,.0f", item.getDonGia()),
                            String.format("%,.0f", item.getDonGia() * item.getSoLuong())
                    };
                    for (int i = 0; i < rowData.length; i++) {
                        contentStream.newLineAtOffset(x - tableX, 0);
                        contentStream.showText(rowData[i]);
                        x += columnWidths[i];
                    }
                    contentStream.endText();
                    tableY -= 20;


                }

                // Vẽ khung xung quanh bảng
                contentStream.moveTo(tableX, 540);
                contentStream.lineTo(tableX + 550, 540);
                contentStream.lineTo(tableX + 550, tableY);
                contentStream.lineTo(tableX, tableY);
                contentStream.lineTo(tableX, 540);
                contentStream.stroke();
            } else {
                System.out.println("Không có sản phẩm để xuất bảng");
            }

            contentStream.close();
            document.save(outputPath);
            document.close();

            JOptionPane.showMessageDialog(parent, "Đã xuất file PDF thành công!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất file PDF: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}