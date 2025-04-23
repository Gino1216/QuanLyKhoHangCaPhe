/*
 * Click nbproject://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbproject://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import javax.swing.*;
import java.io.File;

public class PDFExporter {

    public void exportToPDF(JDialog parent, String title, String[] labels, String[] values, String outputPath) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load font hỗ trợ Unicode
            File fontFile = new File("E:/arial.ttf");
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

            // Nội dung chi tiết
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.setLeading(20f);
            contentStream.newLineAtOffset(50, 700);

            for (int i = 0; i < labels.length; i++) {
                contentStream.showText(labels[i] + " " + values[i]);
                contentStream.newLine();
            }

            contentStream.endText();

            // Vẽ khung xung quanh nội dung
            contentStream.setLineWidth(1f);
            contentStream.moveTo(40, 710);
            contentStream.lineTo(550, 720);
            contentStream.lineTo(550, 560);
            contentStream.lineTo(40, 560);
            contentStream.lineTo(40, 720);
            contentStream.stroke();

            contentStream.close();
            document.save(outputPath);
            document.close();

            JOptionPane.showMessageDialog(parent, "Đã xuất file PDF thành công!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi xuất file PDF!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}