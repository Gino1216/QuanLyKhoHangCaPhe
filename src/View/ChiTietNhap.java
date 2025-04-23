/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import DTO.ChiTietPhieuNhapDTO;
import PDF.PDFN;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 *
 * @author hjepr
 */
public class ChiTietNhap extends JFrame {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(0, 102, 204);
    private Color buttonColor = new Color(0, 120, 215); // Màu xanh dương giống hình ảnh
    private Color cancelButtonColor = new Color(255, 69, 58); // Màu đỏ giống hình ảnh

    public ChiTietNhap(String exportId, String customer, String employee, String exportDate,
                String totalAmount, String status, List<ChiTietPhieuNhapDTO> coffeeItems) {
        FlatLightLaf.setup();
        setTitle("Chi tiết phiếu nhập");
        setSize(1200, 800); // Kích thước giống hình ảnh
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

        // Info panel (Thông tin phiếu nhập)
        JPanel infoPanel = new JPanel(new GridLayout(1, 8, 5, 5)); // 1 hàng, 8 cột (4 nhãn + 4 giá trị)
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Mã phiếu nhập
        JLabel lblExportId = new JLabel("Mã phiếu");
        lblExportId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblExportId);

        JTextField txtExportId = new JTextField(exportId);
        txtExportId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtExportId.setEditable(false);
        txtExportId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtExportId);

        // Nhân viên nhập
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblEmployee);

        JTextField txtEmployee = new JTextField(employee);
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmployee.setEditable(false);
        txtEmployee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtEmployee);

        // Nhà cung cấp
        JLabel lblCustomer = new JLabel("Nhà cung cấp");
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

        // Table of imported coffee products (Danh sách sản phẩm)
        String[] columns = {"Mã phiếu nhập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng (bao)", "Đơn giá", "Thành tiền"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô
            }
        };

        // Điền dữ liệu vào bảng
        try {
            for (ChiTietPhieuNhapDTO item : coffeeItems) {
                Object[] row = {
                        item.getMaPN(),
                        item.getMaSP(),
                        item.getMaPN(),
                        item.getSoLuong(), // Số lượng
                        String.format("%,.0f", item.getDonGia()),
                        String.format("%,.0f", item.getDonGia() * item.getSoLuong())
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane); // Thêm vào container cha
        revalidate(); // Cập nhật giao diện

        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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
            try {
                // Tạo thư mục lưu file trong ổ E nếu chưa tồn tại
                String directoryPath = "E:/";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs(); // Tạo thư mục
                    if (!created) {
                        JOptionPane.showMessageDialog(this, "Không thể tạo thư mục " + directoryPath + " trong ổ E!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Định nghĩa đường dẫn file trong ổ E
                String filePath = directoryPath + "PhieuNhap_" + exportId + ".pdf";

                // Kiểm tra dữ liệu
                String[] labels = {"Mã phiếu:", "Khách hàng:", "Nhân viên xuất:", "Thời gian tạo:", "Tổng tiền:"};
                String[] values = {
                        exportId != null ? exportId : "",
                        customer != null ? customer : "",
                        employee != null ? employee : "",
                        exportDate != null ? exportDate : "",
                        totalAmount != null ? totalAmount : ""
                };

                if (labels.length != values.length) {
                    JOptionPane.showMessageDialog(this, "Số lượng nhãn và giá trị không khớp!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra coffeeItems trước khi xuất PDF
                if (coffeeItems == null || coffeeItems.isEmpty()) {
                    System.out.println("coffeeItems is null or empty when exporting PDF");
                    JOptionPane.showMessageDialog(this, "Không có dữ liệu sản phẩm để xuất PDF!",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    System.out.println("Xuất PDF với " + coffeeItems.size() + " sản phẩm");
                    for (ChiTietPhieuNhapDTO item : coffeeItems) {
                        System.out.println("Sản phẩm trong PDF: " + item.getMaSP() + ", Số lượng: " + item.getSoLuong());
                    }
                }

                // Xuất file PDF trực tiếp vào ổ E
                PDFN exporter = new PDFN();
                exporter.exportToPDF(this, "THÔNG TIN PHIẾU NHẬP", labels, values, coffeeItems, filePath);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file PDF vào ổ E: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
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