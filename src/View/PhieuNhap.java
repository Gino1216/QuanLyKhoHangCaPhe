package View;

import Gui.InputDate;
import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhieuNhap extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private InputDate dateStart, dateEnd;
    private DefaultTableModel tableModel;
    private Color backgroundColor = new Color(255, 255, 255);

    public PhieuNhap() {
        setLayout(new BorderLayout(0, 10));
        setBackground(backgroundColor);

        // Top Panel (Toolbar and Search)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("phieunhap", new String[]{"create", "detail", "cancel", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Left Panel (Filter Options)
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        // Center Panel (Table)
        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        // Add panel actions
        setupFunctionBarActions();
    }

    private void setupFunctionBarActions() {
        // Nút "Thêm" (create)
        functionBar.setButtonActionListener("create", () -> {
            CreaterPhieuNhap createrPhieuNhap = new CreaterPhieuNhap(this);
            createrPhieuNhap.setVisible(true);
        });

        // Nút "Chi tiết" (detail)
        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Chức năng xem chi tiết chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Nút "Hủy" (cancel)
        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Hủy", selectedRow, 6); // Cập nhật trạng thái thành "Hủy"
        });

        // Nút "Xuất" (export)
        functionBar.setButtonActionListener("export", () -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất phiếu nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên nhà cung cấp", "Địa chỉ", "Email"});
        cbbFilter.setPreferredSize(new Dimension(100, 25));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(100, 25));

        btnRefresh = new JButton("Làm mới");
        try {
            btnRefresh.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.svg")));
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon refresh.svg trong /icon/");
        }
        btnRefresh.setPreferredSize(new Dimension(150, 35));

        searchPanel.add(new JLabel("Lọc theo:"));
        searchPanel.add(cbbFilter);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnRefresh);

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
            table.setRowSorter(null);
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterData();
            }
        });

        return searchPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(backgroundColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("Nhà cung cấp");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        leftPanel.add(lblTitle, gbc);

        gbc.gridy++;
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        leftPanel.add(lblSupplier, gbc);

        gbc.gridy++;
        JComboBox<String> cbbSupplier = new JComboBox<>(new String[]{"Tất cả"});
        cbbSupplier.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbSupplier, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        JComboBox<String> cbbEmployee = new JComboBox<>(new String[]{"Tất cả"});
        cbbEmployee.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbEmployee, gbc);

        // --- TỪ NGÀY ---
        gbc.gridy++;
        dateStart = new InputDate("Từ ngày", 200, 70);
        leftPanel.add(dateStart, gbc);

        // --- ĐẾN NGÀY ---
        gbc.gridy++;
        dateEnd = new InputDate("Đến ngày", 200, 70);
        leftPanel.add(dateEnd, gbc);

        gbc.gridy++;
        JLabel lblFromAmount = new JLabel("Từ số tiền (VNĐ)");
        leftPanel.add(lblFromAmount, gbc);

        gbc.gridy++;
        JTextField txtFromAmount = new JTextField();
        txtFromAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtFromAmount, gbc);

        gbc.gridy++;
        JLabel lblToAmount = new JLabel("Đến số tiền (VNĐ)");
        leftPanel.add(lblToAmount, gbc);

        gbc.gridy++;
        JTextField txtToAmount = new JTextField();
        txtToAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtToAmount, gbc);

        return leftPanel;
    }

    private JScrollPane createTable() {
        String[] columns = {"STT", "Mã phiếu", "Nhà cung cấp", "Nhân viên nhập", "Thời gian", "Tổng tiền", "Trạng thái"};
        Object[][] data = {
            {1, "PN001", "Công ty A", "Nguyễn Văn A", "2025-04-14 10:30", "5.000.000", "Đã duyệt"},
            {2, "PN002", "Công ty B", "Trần Thị B", "2025-04-13 09:00", "3.200.000", "Chưa duyệt"},
            {3, "PN003", "Công ty C", "Lê Văn C", "2025-04-12 14:45", "8.000.000", "Đã duyệt"}
        };

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return scrollPane;
    }

    private void filterData() {
        String searchText = txtSearch.getText().toLowerCase();
        String selectedFilter = (String) cbbFilter.getSelectedItem();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        int[] columnIndices;
        if ("Tất cả".equals(selectedFilter)) {
            columnIndices = new int[]{0, 1, 2, 3, 4};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên nhà cung cấp" ->
                    2; // Cột "Nhà cung cấp"
                case "Địa chỉ" ->
                    2; // Không có cột "Địa chỉ", tạm ánh xạ đến "Nhà cung cấp"
                case "Email" ->
                    2;   // Không có cột "Email", tạm ánh xạ đến "Nhà cung cấp"
                default ->
                    0;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        System.out.println("Dữ liệu đã được làm mới.");
    }

    // Phương thức để thêm phiếu nhập mới vào bảng dữ liệu ảo
    public void addPhieuNhap(String receiptId, String supplier, String employee, long totalAmount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("#,###");
        int stt = table.getRowCount() + 1;
        tableModel.addRow(new Object[]{stt, receiptId, supplier, employee, currentDate, df.format(totalAmount) + "đ", "Chưa duyệt"});
    }
}
