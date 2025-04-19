/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author hjepr
 */

public class ChiTietSanPham extends JDialog {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(59, 130, 246);
    private Color buttonColor = new Color(59, 130, 246);
    private Color cancelButtonColor = new Color(239, 68, 68);

    public ChiTietSanPham(String id, String name, String quantity,String state,String expiryDate, String PriceN,String PriceX) {
        FlatLightLaf.setup();
        setTitle("Chi Tiết Sản Phẩm");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("CHI TIẾT SẢN PHẨM", JLabel.CENTER);
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

        String[] labels = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Tình trạng", "Hạn sử dụng", "Giá xuất","Giá nhập"};
        String[] values = {id, name, quantity,state, expiryDate, PriceX, PriceN};

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
            JOptionPane.showMessageDialog(this, "Chức năng xuất file PDF chưa được triển khai!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
