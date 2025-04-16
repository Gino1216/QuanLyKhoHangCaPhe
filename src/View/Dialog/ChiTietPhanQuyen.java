/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author hjepr
 */
public class ChiTietPhanQuyen extends JDialog {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(0, 102, 204);
    private Color buttonColor = new Color(0, 120, 215); // Màu xanh dương giống hình ảnh
    private Color cancelButtonColor = new Color(255, 69, 58); // Màu đỏ giống hình ảnh

    public ChiTietPhanQuyen(String permissionId, String roleName, Object[][] permissionData) {
        FlatLightLaf.setup();
        setTitle("Chi tiết nhóm quyền");
        setSize(800, 600); // Kích thước giống hình ảnh
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("THÔNG TIN NHÓM QUYỀN", JLabel.CENTER);
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

        // Info panel (Thông tin nhóm quyền)
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 hàng, 4 cột (2 nhãn + 2 giá trị)
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Mã quyền
        JLabel lblPermissionId = new JLabel("Mã quyền");
        lblPermissionId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblPermissionId);

        JTextField txtPermissionId = new JTextField(permissionId);
        txtPermissionId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPermissionId.setEditable(false);
        txtPermissionId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtPermissionId);

        // Tên nhóm quyền
        JLabel lblRoleName = new JLabel("Tên nhóm quyền");
        lblRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblRoleName);

        JTextField txtRoleName = new JTextField(roleName);
        txtRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtRoleName.setEditable(false);
        txtRoleName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtRoleName);

        contentPanel.add(infoPanel, BorderLayout.NORTH);

        // Table of permissions (Danh sách quyền)
        String[] columns = {"", "Xem", "Tạo mới", "Cập nhật", "Xóa"};
        DefaultTableModel tableModel = new DefaultTableModel(permissionData, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(35); // Chiều cao hàng giống showAddPermissionDialog
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font tiêu đề giống showAddPermissionDialog
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font nội dung giống showAddPermissionDialog
        table.setGridColor(new Color(200, 200, 200)); // Màu lưới giống showAddPermissionDialog
        table.setShowGrid(true); // Hiển thị lưới giống showAddPermissionDialog
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Tự động điều chỉnh cột giống showAddPermissionDialog

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
