package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class LichSuGia extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaNuocUong, txtGiaMoi, txtSearch;
    private JComboBox<String> cbDateRange;

    public LichSuGia() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header panel
        JLabel lblTitle = new JLabel("LỊCH SỬ THAY ĐỔI GIÁ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Cập nhật giá mới"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaNuocUong = new JTextField(15);
        txtGiaMoi = new JTextField(15);
        JButton btnUpdate = new JButton("Cập nhật giá");
        btnUpdate.setBackground(new Color(50, 150, 250));
        btnUpdate.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã Nước Uống:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaNuocUong, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Giá Mới (VND):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtGiaMoi, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        inputPanel.add(btnUpdate, gbc);

        // Search panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm lịch sử"));
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.anchor = GridBagConstraints.WEST;
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        txtSearch = new JTextField(20);
        cbDateRange = new JComboBox<>(new String[]{"Tất cả", "Hôm nay", "Tuần này", "Tháng này", "7/12/2020-14/12/2020"});
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(70, 130, 180));
        btnSearch.setForeground(Color.WHITE);

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchPanel.add(new JLabel("Từ khóa:"), gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(txtSearch, gbcSearch);

        gbcSearch.gridx = 0; gbcSearch.gridy = 1;
        searchPanel.add(new JLabel("Khoảng thời gian:"), gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(cbDateRange, gbcSearch);

        gbcSearch.gridx = 0; gbcSearch.gridy = 2;
        gbcSearch.gridwidth = 2;
        gbcSearch.fill = GridBagConstraints.CENTER;
        searchPanel.add(btnSearch, gbcSearch);

        // Combine input and search panels
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        topPanel.add(inputPanel);
        topPanel.add(searchPanel);

        // Table setup
        String[] columnNames = {"STT", "Mã Nước Uống", "Tên Sản Phẩm", "Giá Cũ (VND)", "Giá Mới (VND)", "Thời Gian Thay Đổi", "Chọn"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Chỉ cho phép chọn checkbox
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? Boolean.class : Object.class;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Custom renderer for currency columns
        table.getColumnModel().getColumn(3).setCellRenderer((TableCellRenderer) new CurrencyRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer) new CurrencyRenderer());
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(0, 250));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnUndo = createStyledButton("Hoàn tác thay đổi", new Color(220, 20, 60));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(34, 139, 34));
        JButton btnPrint = createStyledButton("In báo cáo", new Color(128, 0, 128));

        buttonPanel.add(btnUndo);
        buttonPanel.add(btnExport);
        buttonPanel.add(btnPrint);

        // Add components to main panel
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add sample data
        addSampleData();

        // Add action listeners
        btnUpdate.addActionListener(e -> updatePrice());
        btnSearch.addActionListener(e -> searchHistory());
        btnUndo.addActionListener(e -> undoChange());
        btnExport.addActionListener(e -> exportToExcel());
        btnPrint.addActionListener(e -> printReport());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 30));
        return button;
    }

    // Custom renderer for currency format
    private static class CurrencyRenderer extends DefaultTableCellRenderer {
        public CurrencyRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                setText(value.toString());
            }
            return this;
        }
    }

    private void addSampleData() {
        Object[][] sampleData = {
            {1, "NU001", "Cà phê đen", "20,000", "22,000", "15/06/2023 08:30", false},
            {2, "NU002", "Cà phê sữa", "25,000", "27,000", "15/06/2023 09:15", false},
            {3, "NU003", "Trà đào", "30,000", "32,000", "16/06/2023 10:00", false},
            {4, "NU004", "Sinh tố bơ", "35,000", "38,000", "16/06/2023 14:30", false},
            {5, "NU005", "Nước cam", "25,000", "28,000", "17/06/2023 11:45", false}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void updatePrice() {
        String maNuoc = txtMaNuocUong.getText().trim();
        String giaMoi = txtGiaMoi.getText().trim();
        
        if (maNuoc.isEmpty() || giaMoi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double price = Double.parseDouble(giaMoi);
            // Thêm logic cập nhật giá vào CSDL ở đây
            
            // Giả lập thêm vào bảng
            Object[] newRow = {
                tableModel.getRowCount() + 1,
                maNuoc,
                "Sản phẩm mới", // Lấy từ CSDL
                "30,000", // Giá cũ từ CSDL
                String.format("%,.0f", price),
                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()),
                false
            };
            tableModel.addRow(newRow);
            
            JOptionPane.showMessageDialog(this, "Cập nhật giá thành công!");
            txtMaNuocUong.setText("");
            txtGiaMoi.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá mới phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchHistory() {
        String keyword = txtSearch.getText().trim();
        String timeRange = cbDateRange.getSelectedItem().toString();
        
        // Thêm logic tìm kiếm vào CSDL ở đây
        JOptionPane.showMessageDialog(this, "Đang tìm kiếm: '" + keyword + "' trong khoảng '" + timeRange + "'");
    }

    private void undoChange() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean)tableModel.getValueAt(i, 6)) {
                // Thêm logic hoàn tác thay đổi vào CSDL ở đây
                JOptionPane.showMessageDialog(this, "Đã hoàn tác thay đổi cho dòng " + (i+1));
                tableModel.setValueAt(false, i, 6);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một mục để hoàn tác", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    private void exportToExcel() {
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra Excel thành công!");
    }

    private void printReport() {
        JOptionPane.showMessageDialog(this, "In báo cáo thành công!");
    }
}