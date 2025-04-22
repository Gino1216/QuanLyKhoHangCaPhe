/*
 * Click nbproject://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbproject://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import PDF.PDFExporter;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author hjepr
 */
public class ChiTietNhanVien extends JDialog {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(59, 130, 246);
    private Color buttonColor = new Color(59, 130, 246);
    private Color cancelButtonColor = new Color(239, 68, 68);
    private Color buttonHoverColor = new Color(100, 149, 237);
    private Color cancelHoverColor = new Color(248, 113, 113);
    String[] labels = {"Mã Nhân Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Số Điện Thoại", "Chức Vụ"};

    public ChiTietNhanVien(String id, String name, String gender, String birthDate, String phone, String email, String address, String chucvu) {
        FlatLightLaf.setup();
        setTitle("Chi Tiết Nhân Viên");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));

        // Header
        JLabel lblHeader = new JLabel("CHI TIẾT NHÂN VIÊN", JLabel.CENTER);
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

        String[] values = {id, name, gender, birthDate, phone, email, address, chucvu};

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
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JButton btnExportPDF = new JButton("Xuất file PDF");
        btnExportPDF.setBackground(buttonColor);
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExportPDF.setPreferredSize(new Dimension(150, 40));
        btnExportPDF.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnExportPDF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExportPDF.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExportPDF.setBackground(buttonColor);
            }
        });
        btnExportPDF.addActionListener(e -> {
            String[] labelsPDF = {"Mã Nhân Viên:", "Họ Tên:", "Giới Tính:", "Ngày Sinh:", "Địa Chỉ:", "Email:", "Số Điện Thoại:", "Chức Vụ:"};
            String[] valuesPDF = {id, name, gender, birthDate, address, email, phone, chucvu};
            PDFExporter exporter = new PDFExporter();
            exporter.exportToPDF(this, "CHI TIẾT NHÂN VIÊN", labelsPDF, valuesPDF, "E:/ChiTietNhanVien_" + id + ".pdf");
        });

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(cancelButtonColor);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(cancelHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(cancelButtonColor);
            }
        });
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnExportPDF);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}