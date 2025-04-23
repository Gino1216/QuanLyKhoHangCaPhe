/*
 * Click nbproject://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbproject://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import DTO.TraHangDTO;
import PDF.PDFExporter;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class ChiTietTraHang extends JDialog {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(59, 130, 246);
    private Color buttonColor = new Color(59, 130, 246);
    private Color cancelButtonColor = new Color(239, 68, 68);

    public ChiTietTraHang(TraHangDTO traHang) {
        FlatLightLaf.setup();
        setTitle("Chi Tiết Trả Hàng");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("CHI TIẾT TRẢ HÀNG", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBackground(headerColor);
        lblHeader.setOpaque(true);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Mã trả hàng", "Mã phiếu xuất", "Mã nhân viên", "Mã khách hàng", "Ngày trả", "Lý do trả", "Tổng tiền hoàn trả", "Trạng thái"};
        String[] values = {
                traHang.getMaTraHang(),
                traHang.getMaPX(),
                traHang.getMaNV(),
                traHang.getMaKH(),
                traHang.getNgayTra(),
                traHang.getLyDoTra(),
                String.valueOf(traHang.getTongTienHoanTra()),
                traHang.getTrangThai()
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = i;
            infoPanel.add(label, gbc);

            JTextField textField = new JTextField(values[i]);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textField.setEditable(false);
            textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            textField.setPreferredSize(new Dimension(300, 40));
            gbc.gridx = 1;
            gbc.gridy = i;
            infoPanel.add(textField, gbc);
        }

        add(infoPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnExportPDF = new JButton("Xuất file PDF");
        btnExportPDF.setBackground(buttonColor);
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnExportPDF.setPreferredSize(new Dimension(180, 50));
        btnExportPDF.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnExportPDF.addActionListener(e -> {
            String[] labelsPDF = {"Mã trả hàng:", "Mã phiếu xuất:", "Mã nhân viên:", "Mã khách hàng:", "Ngày trả:", "Lý do trả:", "Tổng tiền hoàn trả:", "Trạng thái:"};
            String[] valuesPDF = {
                    traHang.getMaTraHang(),
                    traHang.getMaPX(),
                    traHang.getMaNV(),
                    traHang.getMaKH(),
                    traHang.getNgayTra(),
                    traHang.getLyDoTra(),
                    String.valueOf(traHang.getTongTienHoanTra()),
                    traHang.getTrangThai()
            };
            PDFExporter exporter = new PDFExporter();
            exporter.exportToPDF(this, "CHI TIẾT TRẢ HÀNG", labelsPDF, valuesPDF, "E:/ChiTietTraHang_" + traHang.getMaTraHang() + ".pdf");
        });

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(cancelButtonColor);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnExportPDF);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}