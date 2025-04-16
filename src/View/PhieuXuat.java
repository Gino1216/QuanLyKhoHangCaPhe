package View;

import View.Dialog.CreaterPhieuXuat;
import Gui.InputDate;
import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import DTO.PhieuXuatDTO;
import View.Dialog.ChiTietXuat;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhieuXuat extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private DefaultTableModel tableModel;
    private InputDate dateStart, dateEnd;
    private Color backgroundColor = new Color(255, 255, 255);
    private List<PhieuXuatDTO> exportEntries;

    public PhieuXuat() {
        exportEntries = new ArrayList<>();
        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        // Top Panel (Toolbar and Search)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("phieuxuat", new String[]{"create", "detail", "cancel", "sucess", "export"});
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

        // Setup actions for toolbar buttons
        setupFunctionBarActions();
    }

    private void setupFunctionBarActions() {
        // Nút "Thêm" (create)
        functionBar.setButtonActionListener("create", () -> {
            CreaterPhieuXuat createrPhieuXuat = new CreaterPhieuXuat(this);
            createrPhieuXuat.setVisible(true);
        });

        // Nút "Chi tiết" (detail)
        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin từ hàng được chọn
            PhieuXuatDTO entry = exportEntries.get(selectedRow);
            ChiTietXuat detailView = new ChiTietXuat(
                    entry.getExportId(),
                    entry.getTime(),
                    entry.getCustomer(),
                    entry.getCustomerId(),
                    entry.getCustomerAddress(),
                    entry.getCustomerPhone(),
                    entry.getCustomerEmail(),
                    entry.getEmployee(),
                    entry.getEmployeeId(),
                    entry.getEmployeePhone(),
                    entry.getEmployeeEmail(),
                    entry.getTotalAmount(),
                    entry.getStatus(),
                    entry.getCoffeeData()
            );
            detailView.setVisible(true);
        });

        // Nút "Hủy" (cancel)
        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Hủy", selectedRow, 6);
            exportEntries.get(selectedRow).setStatus("Hủy");
        });

        // Nút "Duyệt" (sucess)
        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để duyệt!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Đã Duyệt", selectedRow, 6);
            exportEntries.get(selectedRow).setStatus("Đã Duyệt");
        });

        // Nút "Xuất" (export)
        functionBar.setButtonActionListener("export", () -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất phiếu xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Khách hàng", "Địa chỉ", "Email"});
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

        JLabel lblTitle = new JLabel("Khách Hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        leftPanel.add(lblTitle, gbc);

        gbc.gridy++;
        JLabel lblCustomer = new JLabel("Khách Hàng");
        leftPanel.add(lblCustomer, gbc);

        gbc.gridy++;
        JComboBox<String> cbbCustomer = new JComboBox<>(new String[]{"Tất cả"});
        cbbCustomer.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbCustomer, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên xuất");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        JComboBox<String> cbbEmployee = new JComboBox<>(new String[]{"Tất cả"});
        cbbEmployee.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbEmployee, gbc);

        gbc.gridy++;
        dateStart = new InputDate("Từ ngày", 200, 70);
        leftPanel.add(dateStart, gbc);

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
        String[] columns = {"STT", "Mã phiếu xuất", "Khách hàng", "Nhân viên xuất", "Thời gian", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(new Object[][]{}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Thêm dữ liệu giả lập vào danh sách exportEntries và bảng
        Object[][] coffeeData1 = {
            {1, "CF001", "Cafe Đắk Lắk", "Arabica", "50", "150000", "10"},
            {2, "CF002", "Cafe Buôn Ma Thuột", "Robusta", "50", "140000", "5"}
        };
        exportEntries.add(new PhieuXuatDTO("PX001", "Nguyễn Văn A", "KH001", "123 Đường Láng, Hà Nội", "0912345678", "nguyenvana@example.com",
                "Trần Thị B", "NV001", "0987654321", "tranthib@example.com",
                "2025-04-14 08:00", "10.000.000đ", "Đã duyệt", coffeeData1));

        Object[][] coffeeData2 = {
            {1, "CF003", "Cafe Lâm Đồng", "Culi", "50", "130000", "8"}
        };
        exportEntries.add(new PhieuXuatDTO("PX002", "Lê Văn C", "KH002", "456 Nguyễn Trãi, TP.HCM", "0923456789", "levanc@example.com",
                "Phạm Văn D", "NV002", "0976543210", "phamvand@example.com",
                "2025-04-13 14:30", "6.000.000đ", "Chưa duyệt", coffeeData2));

        Object[][] coffeeData3 = {
            {1, "CF004", "Cafe Gia Lai", "Blend", "50", "145000", "12"},
            {2, "CF005", "Cafe Khe Sanh", "Robusta", "50", "135000", "6"}
        };
        exportEntries.add(new PhieuXuatDTO("PX003", "Trần Thị E", "KH003", "789 Lê Lợi, Đà Nẵng", "0934567890", "tranthie@example.com",
                "Ngô Văn F", "NV003", "0965432109", "ngovanf@example.com",
                "2025-04-12 10:15", "12.500.000đ", "Đã duyệt", coffeeData3));

        // Thêm dữ liệu vào bảng
        for (int i = 0; i < exportEntries.size(); i++) {
            tableModel.addRow(exportEntries.get(i).toTableRow(i + 1));
        }

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
                case "Khách hàng" ->
                    2;
                case "Địa chỉ" ->
                    2;
                case "Email" ->
                    2;
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

    public void addPhieuXuat(String receiptId, String customer, String customerId, String customerAddress, String customerPhone, String customerEmail,
            String employee, String employeeId, String employeePhone, String employeeEmail,
            long totalAmount, Object[][] coffeeData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("#,###");
        int stt = table.getRowCount() + 1;

        // Tạo một PhieuNhapDTO mới
        PhieuXuatDTO entry = new PhieuXuatDTO(
                receiptId,
                customer,
                customerId,
                customerAddress,
                customerPhone,
                customerEmail,
                employee,
                employeeId,
                employeePhone,
                employeeEmail,
                currentDate,
                df.format(totalAmount) + "đ",
                "Chưa duyệt",
                coffeeData
        );

        // Thêm vào danh sách và bảng
        exportEntries.add(entry);
        tableModel.addRow(entry.toTableRow(stt));
    }
}
