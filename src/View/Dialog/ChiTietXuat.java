/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author hjepr
 */
public class ChiTietXuat extends JFrame {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(0, 102, 204);
    private Color buttonColor = new Color(0, 120, 215); // Màu xanh dương giống hình ảnh
    private Color cancelButtonColor = new Color(255, 69, 58); // Màu đỏ giống hình ảnh

    public ChiTietXuat(String exportId, String exportDate, String customer, String customerId, String customerAddress, String customerPhone, String customerEmail,
            String employee, String employeeId, String employeePhone, String employeeEmail,
            String totalAmount, String status, Object[][] coffeeData) {
        FlatLightLaf.setup();
        setTitle("Chi tiết phiếu xuất");
        setSize(1200, 800); // Kích thước giống hình ảnh
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("THÔNG TIN PHIẾU XUẤT", JLabel.CENTER);
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

        // Info panel (Thông tin phiếu xuất)
        JPanel infoPanel = new JPanel(new GridLayout(1, 8, 5, 5)); // 1 hàng, 8 cột (4 nhãn + 4 giá trị)
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Mã phiếu xuất
        JLabel lblExportId = new JLabel("Mã phiếu");
        lblExportId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblExportId);

        JTextField txtExportId = new JTextField(exportId);
        txtExportId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtExportId.setEditable(false);
        txtExportId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtExportId);

        // Nhân viên xuất
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblEmployee);

        JTextField txtEmployee = new JTextField(employee);
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmployee.setEditable(false);
        txtEmployee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtEmployee);

        // Khách hàng
        JLabel lblCustomer = new JLabel("Khách hàng");
        lblCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblCustomer);

        JTextField txtCustomer = new JTextField(customer);
        txtCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCustomer.setEditable(false);
        txtCustomer.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtCustomer);

        // Thời gian
        JLabel lblExportDate = new JLabel("Thời gian tạo");
        lblExportDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblExportDate);

        JTextField txtExportDate = new JTextField(exportDate);
        txtExportDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtExportDate.setEditable(false);
        txtExportDate.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtExportDate);

        contentPanel.add(infoPanel, BorderLayout.NORTH);

        // Table of exported coffee products (Danh sách sản phẩm)
        String[] columns = {"STT", "Mã cà phê", "Tên cà phê", "Loại cà phê", "Trọng lượng (kg/bao)", "Đơn giá (VNĐ/kg)", "Số lượng (bao)"};
        DefaultTableModel tableModel = new DefaultTableModel(coffeeData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(35); // Chiều cao hàng giống PhieuXuat
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font tiêu đề giống PhieuXuat
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font nội dung giống PhieuXuat
        table.setGridColor(new Color(200, 200, 200)); // Màu lưới giống PhieuXuat
        table.setShowGrid(false); // Không hiển thị lưới giống PhieuXuat
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Tự động điều chỉnh cột giống PhieuXuat

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Viền ngoài giống hình ảnh
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
            JOptionPane.showMessageDialog(this, "Chức năng xuất file PDF chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
