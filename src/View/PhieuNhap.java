package View;

import DTO.PhieuNhapDTO;
import DTO.CoffeItemDTO;
import View.Dialog.CreaterPhieuNhap;
import View.Dialog.ChiTietNhap;
import Gui.InputDate;
import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private List<PhieuNhapDTO> importEntries;

    public PhieuNhap() {
        importEntries = new ArrayList<>();
        setLayout(new BorderLayout(0, 10));
        setBackground(backgroundColor);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("phieunhap", new String[]{"create", "detail", "cancel", "sucess", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        setupFunctionBarActions();
    }

    private void setupFunctionBarActions() {
        functionBar.setButtonActionListener("create", () -> {
            CreaterPhieuNhap createrPhieuNhap = new CreaterPhieuNhap(this);
            createrPhieuNhap.setVisible(true);
        });

        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PhieuNhapDTO entry = importEntries.get(selectedRow);
            ChiTietNhap detailView = new ChiTietNhap(
                    entry.getReceiptId(), entry.getTime(), entry.getSupplier(), entry.getSupplierId(),
                    entry.getSupplierAddress(), entry.getSupplierPhone(), entry.getSupplierEmail(),
                    entry.getEmployee(), entry.getEmployeeId(), entry.getEmployeePhone(), entry.getEmployeeEmail(),
                    entry.getTotalAmount(), entry.getStatus(), entry.getCoffeeItems()
            );
            detailView.setVisible(true);
        });

        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để hủy!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Hủy", selectedRow, 6);
            importEntries.get(selectedRow).setStatus("Hủy");
        });

        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Đã duyệt", selectedRow, 6);
            importEntries.get(selectedRow).setStatus("Đã duyệt");
        });

        functionBar.setButtonActionListener("export", () -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất phiếu nhập chưa được triển khai!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã phiếu", "Nhà cung cấp", "Nhân viên nhập"});
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

        JLabel lblTitle = new JLabel("Bộ lọc");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        leftPanel.add(lblTitle, gbc);

        gbc.gridy++;
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        leftPanel.add(lblSupplier, gbc);

        gbc.gridy++;
        JComboBox<String> cbbSupplier = new JComboBox<>(new String[]{"Tất cả", "Công Ty Cafe Trung Nguyên",
            "Nông Trại Cafe Đắk Lắk", "Hợp Tác Xã Cafe Lâm Đồng"});
        cbbSupplier.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbSupplier, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        JComboBox<String> cbbEmployee = new JComboBox<>(new String[]{"Tất cả", "Hoàng Gia Bảo"});
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
        String[] columns = {"STT", "Mã phiếu", "Nhà cung cấp", "Nhân viên nhập", "Thời gian", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(new Object[][]{}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<CoffeItemDTO> coffeeItems1 = List.of(
                new CoffeItemDTO(1, "CF001", "Cafe Đắk Lắk", "Arabica", 50.0, 150000, 10),
                new CoffeItemDTO(2, "CF002", "Cafe Buôn Ma Thuột", "Robusta", 50.0, 140000, 5)
        );
        importEntries.add(new PhieuNhapDTO("PN001", "Công Ty Cafe Trung Nguyên", "NCC001",
                "123 Đường Láng, Hà Nội", "0912345678", "trungnguyen@example.com", "Hoàng Gia Bảo",
                "NV001", "0987654321", "hoanggiabao@example.com", LocalDateTime.of(2025, 4, 14, 8, 0),
                10000000, "Đã duyệt", coffeeItems1));

        List<CoffeItemDTO> coffeeItems2 = List.of(
                new CoffeItemDTO(1, "CF003", "Cafe Lâm Đồng", "Culi", 50.0, 130000, 8)
        );
        importEntries.add(new PhieuNhapDTO("PN002", "Nông Trại Cafe Đắk Lắk", "NCC002",
                "456 Nguyễn Trãi, TP.HCM", "0923456789", "daklak@example.com", "Hoàng Gia Bảo",
                "NV002", "0976543210", "hoanggiabao@example.com", LocalDateTime.of(2025, 4, 13, 14, 30),
                6000000, "Chưa duyệt", coffeeItems2));

        List<CoffeItemDTO> coffeeItems3 = List.of(
                new CoffeItemDTO(1, "CF004", "Cafe Gia Lai", "Blend", 50.0, 145000, 12),
                new CoffeItemDTO(2, "CF005", "Cafe Khe Sanh", "Robusta", 50.0, 135000, 6)
        );
        importEntries.add(new PhieuNhapDTO("PN003", "Hợp Tác Xã Cafe Lâm Đồng", "NCC003",
                "789 Lê Lợi, Đà Nẵng", "0934567890", "lamdong@example.com", "Hoàng Gia Bảo",
                "NV003", "0965432109", "hoanggiabao@example.com", LocalDateTime.of(2025, 4, 12, 10, 15),
                12500000, "Đã duyệt", coffeeItems3));

        for (int i = 0; i < importEntries.size(); i++) {
            tableModel.addRow(importEntries.get(i).toTableRow(i + 1));
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

        int columnIndex = switch (selectedFilter) {
            case "Mã phiếu" ->
                1;
            case "Nhà cung cấp" ->
                2;
            case "Nhân viên nhập" ->
                3;
            default ->
                -1;
        };

        RowFilter<TableModel, Object> rf;
        if (columnIndex == -1) {
            rf = RowFilter.regexFilter("(?i)" + searchText, 1, 2, 3, 4, 6);
        } else {
            rf = RowFilter.regexFilter("(?i)" + searchText, columnIndex);
        }
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (int i = 0; i < importEntries.size(); i++) {
            tableModel.addRow(importEntries.get(i).toTableRow(i + 1));
        }
    }

    public void addPhieuNhap(String receiptId, String supplier, String supplierId, String supplierAddress,
            String supplierPhone, String supplierEmail, String employee, String employeeId,
            String employeePhone, String employeeEmail, LocalDateTime time, long totalAmount,
            List<CoffeItemDTO> coffeeItems) {
        int stt = table.getRowCount() + 1;
        PhieuNhapDTO entry = new PhieuNhapDTO(receiptId, supplier, supplierId, supplierAddress,
                supplierPhone, supplierEmail, employee, employeeId, employeePhone, employeeEmail,
                time, totalAmount, "Chưa duyệt", coffeeItems);
        importEntries.add(entry);
        tableModel.addRow(entry.toTableRow(stt));
    }
}
