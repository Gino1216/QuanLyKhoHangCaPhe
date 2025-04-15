/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author hjepr
 */

public class HoaDonTra extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaTraHang, txtMaPN, txtMaNVL, txtNgayTra;
    private JComboBox<String> cbTrangThai;

    public HoaDonTra() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header panel
        JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN TRẢ HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Main content panel with vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn trả"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize text fields
        txtMaTraHang = new JTextField(15);
        txtMaPN = new JTextField(15);
        txtMaNVL = new JTextField(15);
        txtNgayTra = new JTextField(15);
        cbTrangThai = new JComboBox<>(new String[]{"Đã xử lý", "Chưa xử lý", "Đã hủy"});

        // Add form components with aligned labels
        String[] labels = {"Mã trả hàng:", "Mã phiếu nhập:", "Mã NVL:", "Ngày trả:", "Trạng thái:"};
        JComponent[] fields = {txtMaTraHang, txtMaPN, txtMaNVL, txtNgayTra, cbTrangThai};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnAdd = createStyledButton("LƯU", new Color(50, 150, 250));
        JButton btnDelete = createStyledButton("XÓA", new Color(250, 100, 100));
        JButton btnEdit = createStyledButton("SỬA", new Color(100, 200, 100));
        JButton btnSearch = createStyledButton("TÌM", new Color(150, 150, 150));
        JButton btnExportExcel = createStyledButton("XUẤT EXCEL", new Color(100, 180, 100));
        JButton btnExportPDF = createStyledButton("XUẤT PDF", new Color(180, 100, 100));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnExportExcel);
        buttonPanel.add(btnExportPDF);

        // Add form and buttons to content panel
        contentPanel.add(formPanel);
        contentPanel.add(buttonPanel);

        // Table setup
        String[] columnNames = {"Mã trả hàng", "Mã phiếu nhập", "Mã NVL", "Ngày trả", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(800, 300));
        tableScroll.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn trả"));

        // Add demo data
        addDemoData();

        // Add components to main panel
        add(contentPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);

        // Add action listeners
        btnAdd.addActionListener(e -> addInvoice());
        btnDelete.addActionListener(e -> deleteInvoice());
        btnEdit.addActionListener(e -> editInvoice());
        btnSearch.addActionListener(e -> searchInvoice());
        btnExportExcel.addActionListener(e -> exportToExcel());
        btnExportPDF.addActionListener(e -> exportToPDF());
    }

    private void addInvoice() {
        if (validateInput()) {
            Object[] rowData = {
                txtMaTraHang.getText(),
                txtMaPN.getText(),
                txtMaNVL.getText(),
                txtNgayTra.getText(),
                cbTrangThai.getSelectedItem()
            };
            tableModel.addRow(rowData);
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn trả thành công!");
        }
    }

    private void deleteInvoice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Bạn có chắc chắn muốn xóa hóa đơn trả này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                clearForm();
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn trả thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trả cần xóa");
        }
    }

    private void editInvoice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            if (validateInput()) {
                tableModel.setValueAt(txtMaTraHang.getText(), selectedRow, 0);
                tableModel.setValueAt(txtMaPN.getText(), selectedRow, 1);
                tableModel.setValueAt(txtMaNVL.getText(), selectedRow, 2);
                tableModel.setValueAt(txtNgayTra.getText(), selectedRow, 3);
                tableModel.setValueAt(cbTrangThai.getSelectedItem(), selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn trả thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trả cần sửa");
        }
    }

    private void searchInvoice() {
        String searchTerm = JOptionPane.showInputDialog(this, "Nhập mã hóa đơn trả cần tìm:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(searchTerm)) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn trả với mã: " + searchTerm);
        }
    }

    private void exportToExcel() {
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra Excel thành công!");
        // Implement actual Excel export functionality here
    }

    private void exportToPDF() {
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra PDF thành công!");
        // Implement actual PDF export functionality here
    }

    private boolean validateInput() {
        if (txtMaTraHang.getText().isEmpty() || txtMaPN.getText().isEmpty() || 
            txtMaNVL.getText().isEmpty() || txtNgayTra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtMaTraHang.setText("");
        txtMaPN.setText("");
        txtMaNVL.setText("");
        txtNgayTra.setText("");
        cbTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }

    private void addDemoData() {
        Object[][] demoData = {
            {"TH001", "PN001", "NVL001", "15/05/2023", "Đã xử lý"},
            {"TH002", "PN002", "NVL002", "16/05/2023", "Chưa xử lý"},
            {"TH003", "PN003", "NVL003", "17/05/2023", "Đã hủy"},
            {"TH004", "PN001", "NVL004", "18/05/2023", "Đã xử lý"},
            {"TH005", "PN004", "NVL005", "19/05/2023", "Chưa xử lý"}
        };

        for (Object[] row : demoData) {
            tableModel.addRow(row);
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
        return button;
    }
}
