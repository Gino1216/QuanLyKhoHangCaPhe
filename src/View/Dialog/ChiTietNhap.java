package View.Dialog;

import DTO.CoffeItemDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietNhap extends JFrame {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(0, 102, 204);
    private Color buttonColor = new Color(0, 120, 215);
    private Color cancelButtonColor = new Color(255, 69, 58);

    public ChiTietNhap(String receiptId, LocalDateTime time, String supplier, String supplierId, 
            String supplierAddress, String supplierPhone, String supplierEmail, String employee, 
            String employeeId, String employeePhone, String employeeEmail, long totalAmount, 
            String status, List<CoffeItemDTO> coffeeItems) {
        FlatLightLaf.setup();
        setTitle("Chi tiết phiếu nhập");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("THÔNG TIN PHIẾU NHẬP", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBackground(headerColor);
        lblHeader.setOpaque(true);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Receipt ID
        JLabel lblReceiptId = new JLabel("Mã phiếu");
        lblReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(lblReceiptId, gbc);

        JTextField txtReceiptId = new JTextField(receiptId);
        txtReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtReceiptId.setEditable(false);
        txtReceiptId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtReceiptId.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        infoPanel.add(txtReceiptId, gbc);

        // Employee
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 2;
        infoPanel.add(lblEmployee, gbc);

        JTextField txtEmployee = new JTextField(employee);
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmployee.setEditable(false);
        txtEmployee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtEmployee.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        infoPanel.add(txtEmployee, gbc);

        // Supplier
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        lblSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(lblSupplier, gbc);

        JTextField txtSupplier = new JTextField(supplier);
        txtSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSupplier.setEditable(false);
        txtSupplier.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtSupplier.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        infoPanel.add(txtSupplier, gbc);

        // Time
        JLabel lblTime = new JLabel("Thời gian tạo");
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 2;
        infoPanel.add(lblTime, gbc);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTime = time != null ? time.format(formatter) : "";
        JTextField txtTime = new JTextField(formattedTime);
        txtTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTime.setEditable(false);
        txtTime.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtTime.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        infoPanel.add(txtTime, gbc);

        // Supplier Address
        JLabel lblSupplierAddress = new JLabel("Địa chỉ nhà cung cấp");
        lblSupplierAddress.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(lblSupplierAddress, gbc);

        JTextField txtSupplierAddress = new JTextField(supplierAddress);
        txtSupplierAddress.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSupplierAddress.setEditable(false);
        txtSupplierAddress.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtSupplierAddress.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        infoPanel.add(txtSupplierAddress, gbc);

        // Supplier Phone
        JLabel lblSupplierPhone = new JLabel("Số điện thoại");
        lblSupplierPhone.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 2;
        infoPanel.add(lblSupplierPhone, gbc);

        JTextField txtSupplierPhone = new JTextField(supplierPhone);
        txtSupplierPhone.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSupplierPhone.setEditable(false);
        txtSupplierPhone.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtSupplierPhone.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        infoPanel.add(txtSupplierPhone, gbc);

        // Supplier Email
        JLabel lblSupplierEmail = new JLabel("Email nhà cung cấp");
        lblSupplierEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(lblSupplierEmail, gbc);

        JTextField txtSupplierEmail = new JTextField(supplierEmail);
        txtSupplierEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSupplierEmail.setEditable(false);
        txtSupplierEmail.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtSupplierEmail.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        infoPanel.add(txtSupplierEmail, gbc);

        // Total Amount
        JLabel lblTotalAmount = new JLabel("Tổng tiền");
        lblTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 2;
        infoPanel.add(lblTotalAmount, gbc);

        JTextField txtTotalAmount = new JTextField(String.format("%,dđ", totalAmount));
        txtTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTotalAmount.setEditable(false);
        txtTotalAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtTotalAmount.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        infoPanel.add(txtTotalAmount, gbc);

        // Status
        JLabel lblStatus = new JLabel("Trạng thái");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(lblStatus, gbc);

        JTextField txtStatus = new JTextField(status);
        txtStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtStatus.setEditable(false);
        txtStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtStatus.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        infoPanel.add(txtStatus, gbc);

        contentPanel.add(infoPanel, BorderLayout.NORTH);

        // Coffee items table
        String[] columns = {"STT", "Mã cà phê", "Tên cà phê", "Loại cà phê", "Trọng lượng (kg/bao)", 
                "Đơn giá (VNĐ/kg)", "Số lượng (bao)"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (CoffeItemDTO item : coffeeItems) {
            tableModel.addRow(item.toTableRow());
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton btnExportPDF = new JButton("Xuất file PDF");
        btnExportPDF.setBackground(buttonColor);
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportPDF.setPreferredSize(new Dimension(120, 30));
        btnExportPDF.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất file PDF chưa được triển khai!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(cancelButtonColor);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setPreferredSize(new Dimension(120, 30));
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnExportPDF);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}