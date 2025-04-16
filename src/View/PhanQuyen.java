package View;

import Gui.MainFunction;
import View.Dialog.ChiTietPhanQuyen;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EmptyBorder;

public class PhanQuyen extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);

    // Map để lưu trữ danh sách quyền cho từng nhóm quyền
    private Map<String, Object[][]> permissionDetailsMap;

    public PhanQuyen() {
        // Khởi tạo Map và dữ liệu giả lập
        permissionDetailsMap = new HashMap<>();
        initializePermissionDetails();

        // Set up FlatLaf theme
        FlatLightLaf.setup();

        // Configure JPanel layout
        setLayout(new BorderLayout(0, 8));

        // Create top panel for the toolbar and search panel
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Create table
        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        // Set background color for the panel
        setBackground(backgroundColor);
    }

    // Khởi tạo dữ liệu giả lập cho danh sách quyền của từng nhóm quyền
    private void initializePermissionDetails() {
        // Nhóm quyền "Admin" (Mã quyền: 1)
        permissionDetailsMap.put("1", new Object[][]{
            {"Quản lý khách hàng", true, true, true, true},
            {"Quản lý khu vực kho", true, true, true, true},
            {"Quản lý chung cấp", true, true, true, true},
            {"Quản lý nhập hàng", true, true, true, true},
            {"Quản lý nhóm quyền", true, true, true, true},
            {"Quản lý sản phẩm", true, true, true, true},
            {"Quản lý tài khoản", true, true, true, true},
            {"Quản lý thống kê", true, true, true, true},
            {"Quản lý xuất hàng", true, true, true, true}
        });

        // Nhóm quyền "User" (Mã quyền: 2)
        permissionDetailsMap.put("2", new Object[][]{
            {"Quản lý khách hàng", true, false, false, false},
            {"Quản lý khu vực kho", true, false, false, false},
            {"Quản lý chung cấp", true, false, false, false},
            {"Quản lý nhập hàng", true, false, false, false},
            {"Quản lý nhóm quyền", false, false, false, false},
            {"Quản lý sản phẩm", true, false, false, false},
            {"Quản lý tài khoản", false, false, false, false},
            {"Quản lý thống kê", true, false, false, false},
            {"Quản lý xuất hàng", true, false, false, false}
        });

        // Nhóm quyền "Nhân viên" (Mã quyền: 3)
        permissionDetailsMap.put("3", new Object[][]{
            {"Quản lý khách hàng", true, true, false, false},
            {"Quản lý khu vực kho", true, false, false, false},
            {"Quản lý chung cấp", true, false, false, false},
            {"Quản lý nhập hàng", true, true, false, false},
            {"Quản lý nhóm quyền", false, false, false, false},
            {"Quản lý sản phẩm", true, true, false, false},
            {"Quản lý tài khoản", false, false, false, false},
            {"Quản lý thống kê", false, false, false, false},
            {"Quản lý xuất hàng", true, true, false, false}
        });

        // Nhóm quyền "Quản lý" (Mã quyền: 4)
        permissionDetailsMap.put("4", new Object[][]{
            {"Quản lý khách hàng", true, true, true, false},
            {"Quản lý khu vực kho", true, true, true, false},
            {"Quản lý chung cấp", true, true, true, false},
            {"Quản lý nhập hàng", true, true, true, false},
            {"Quản lý nhóm quyền", true, false, false, false},
            {"Quản lý sản phẩm", true, true, true, false},
            {"Quản lý tài khoản", true, false, false, false},
            {"Quản lý thống kê", true, true, true, false},
            {"Quản lý xuất hàng", true, true, true, false}
        });

        // Nhóm quyền "Kế toán" (Mã quyền: 5)
        permissionDetailsMap.put("5", new Object[][]{
            {"Quản lý khách hàng", true, false, false, false},
            {"Quản lý khu vực kho", false, false, false, false},
            {"Quản lý chung cấp", true, false, false, false},
            {"Quản lý nhập hàng", true, false, false, false},
            {"Quản lý nhóm quyền", false, false, false, false},
            {"Quản lý sản phẩm", false, false, false, false},
            {"Quản lý tài khoản", false, false, false, false},
            {"Quản lý thống kê", true, true, true, false},
            {"Quản lý xuất hàng", true, false, false, false}
        });
    }

    // Method to create top panel (includes function bar and search panel)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("phanquyen", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddPermissionDialog);
        functionBar.setButtonActionListener("update", this::showEditPermissionDialog);
        functionBar.setButtonActionListener("detail", this::showPermissionDetailDialog);
        functionBar.setButtonActionListener("delete", this::deletePermission);
        functionBar.setButtonActionListener("import", this::importPermissions);
        functionBar.setButtonActionListener("export", this::exportPermissions);

        return topPanel;
    }

    private void showPermissionDetailDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm quyền để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String permissionId = table.getValueAt(modelRow, 0).toString();
        String roleName = table.getValueAt(modelRow, 1).toString();

        // Lấy danh sách quyền từ Map dựa trên permissionId
        Object[][] permissionData = permissionDetailsMap.getOrDefault(permissionId, new Object[][]{});

        // Mở PermissionDetailView
        ChiTietPhanQuyen detailView = new ChiTietPhanQuyen(permissionId, roleName, permissionData);
        detailView.setVisible(true);
    }

    private void deletePermission() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm quyền để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhóm quyền này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String permissionId = table.getValueAt(modelRow, 0).toString();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(modelRow);

            // Xóa dữ liệu khỏi Map
            permissionDetailsMap.remove(permissionId);

            // Cập nhật lại STT
            for (int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt(i + 1, i, 0);
            }
        }
    }

    private void importPermissions() {
        JOptionPane.showMessageDialog(this, "Chức năng nhập nhóm quyền chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportPermissions() {
        JOptionPane.showMessageDialog(this, "Chức năng xuất nhóm quyền chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAddPermissionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Nhóm Quyền", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM NHÓM QUYỀN", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));

        // Tên nhóm quyền
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        JLabel lblRoleName = new JLabel("Tên nhóm quyền");
        lblRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField txtRoleName = new JTextField(30);
        txtRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtRoleName.setPreferredSize(new Dimension(400, 40));
        namePanel.add(lblRoleName);
        namePanel.add(txtRoleName);
        formPanel.add(namePanel, BorderLayout.NORTH);

        // Danh sách mục chức năng
        JLabel lblFunctionList = new JLabel("Danh mục chức năng", JLabel.LEFT);
        lblFunctionList.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFunctionList.setBorder(new EmptyBorder(10, 0, 10, 0));
        formPanel.add(lblFunctionList, BorderLayout.CENTER);

        // Bảng quyền
        String[] columns = {"", "Xem", "Tạo mới", "Cập nhật", "Xóa"};
        Object[][] data = {
            {"Quản lý khách hàng", false, false, false, false},
            {"Quản lý khu vực kho", false, false, false, false},
            {"Quản lý chung cấp", false, false, false, false},
            {"Quản lý nhập hàng", false, false, false, false},
            {"Quản lý nhóm quyền", false, false, false, false},
            {"Quản lý sản phẩm", false, false, false, false},
            {"Quản lý tài khoản", false, false, false, false},
            {"Quản lý thống kê", false, false, false, false},
            {"Quản lý xuất hàng", false, false, false, false}
        };

        DefaultTableModel permissionModel = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Chỉ cho phép chỉnh sửa các cột checkbox
            }
        };

        JTable permissionTable = new JTable(permissionModel);
        permissionTable.setRowHeight(35);
        permissionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        permissionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        permissionTable.setShowGrid(true);
        permissionTable.setGridColor(new Color(200, 200, 200));
        permissionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Tắt hiệu ứng bôi đen
        permissionTable.setCellSelectionEnabled(false);
        permissionTable.setSelectionBackground(Color.WHITE); // Đặt màu nền khi chọn giống màu nền mặc định
        permissionTable.setSelectionForeground(Color.BLACK); // Giữ màu chữ khi chọn

        JScrollPane permissionScroll = new JScrollPane(permissionTable);
        formPanel.add(permissionScroll, BorderLayout.CENTER);

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm nhóm quyền");
        btnAdd.setBackground(new Color(59, 130, 246));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setPreferredSize(new Dimension(180, 50));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnAdd.addActionListener(e -> {
            String roleName = txtRoleName.getText();

            // Kiểm tra trường bắt buộc
            if (roleName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập tên nhóm quyền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm vào bảng chính với mã quyền tự động tăng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int newId = table.getRowCount() + 1;
            String newPermissionId = String.valueOf(newId);
            model.addRow(new Object[]{newPermissionId, roleName});

            // Lưu danh sách quyền vào Map
            Object[][] permissionData = new Object[data.length][columns.length];
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < columns.length; j++) {
                    permissionData[i][j] = permissionModel.getValueAt(i, j);
                }
            }
            permissionDetailsMap.put(newPermissionId, permissionData);

            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditPermissionDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm quyền để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String roleName = table.getValueAt(modelRow, 1).toString();

        // Lấy danh sách quyền từ Map
        Object[][] permissionData = permissionDetailsMap.getOrDefault(id, new Object[][]{
            {"Quản lý khách hàng", false, false, false, false},
            {"Quản lý khu vực kho", false, false, false, false},
            {"Quản lý chung cấp", false, false, false, false},
            {"Quản lý nhập hàng", false, false, false, false},
            {"Quản lý nhóm quyền", false, false, false, false},
            {"Quản lý sản phẩm", false, false, false, false},
            {"Quản lý tài khoản", false, false, false, false},
            {"Quản lý thống kê", false, false, false, false},
            {"Quản lý xuất hàng", false, false, false, false}
        });

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Nhóm Quyền", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA NHÓM QUYỀN", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));

        // Tên nhóm quyền
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        JLabel lblRoleName = new JLabel("Tên nhóm quyền");
        lblRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField txtRoleName = new JTextField(roleName, 30);
        txtRoleName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtRoleName.setPreferredSize(new Dimension(400, 40));
        namePanel.add(lblRoleName);
        namePanel.add(txtRoleName);
        formPanel.add(namePanel, BorderLayout.NORTH);

        // Danh sách mục chức năng
        JLabel lblFunctionList = new JLabel("Danh mục chức năng", JLabel.LEFT);
        lblFunctionList.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFunctionList.setBorder(new EmptyBorder(10, 0, 10, 0));
        formPanel.add(lblFunctionList, BorderLayout.CENTER);

        // Bảng quyền
        String[] columns = {"", "Xem", "Tạo mới", "Cập nhật", "Xóa"};
        DefaultTableModel permissionModel = new DefaultTableModel(permissionData, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Chỉ cho phép chỉnh sửa các cột checkbox
            }
        };

        JTable permissionTable = new JTable(permissionModel);
        permissionTable.setRowHeight(35);
        permissionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        permissionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        permissionTable.setShowGrid(true);
        permissionTable.setGridColor(new Color(200, 200, 200));
        permissionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Tắt hiệu ứng bôi đen
        permissionTable.setCellSelectionEnabled(false);
        permissionTable.setSelectionBackground(Color.WHITE); // Đặt màu nền khi chọn giống màu nền mặc định
        permissionTable.setSelectionForeground(Color.BLACK); // Giữ màu chữ khi chọn

        JScrollPane permissionScroll = new JScrollPane(permissionTable);
        formPanel.add(permissionScroll, BorderLayout.CENTER);

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnSave = new JButton("Lưu thông tin");
        btnSave.setBackground(new Color(59, 130, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setPreferredSize(new Dimension(180, 50));
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnSave.addActionListener(e -> {
            String newRoleName = txtRoleName.getText();

            // Kiểm tra trường bắt buộc
            if (newRoleName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập tên nhóm quyền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật dữ liệu vào bảng
            table.setValueAt(newRoleName, modelRow, 1);

            // Cập nhật danh sách quyền trong Map
            Object[][] updatedPermissionData = new Object[permissionData.length][columns.length];
            for (int i = 0; i < permissionData.length; i++) {
                for (int j = 0; j < columns.length; j++) {
                    updatedPermissionData[i][j] = permissionModel.getValueAt(i, j);
                }
            }
            permissionDetailsMap.put(id, updatedPermissionData);

            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên Quyền"});
        cbbFilter.setPreferredSize(new Dimension(100, 25));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(100, 25));

        btnRefresh = new JButton("Làm mới");
        try {
            btnRefresh.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.svg")));
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon refresh.png trong /icon/");
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

    private JScrollPane createTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Cột và dữ liệu mẫu
        String[] columns = {"Mã Quyền", "Tên Quyền"};
        Object[][] data = {
            {"1", "Admin"},
            {"2", "User"},
            {"3", "Nhân viên"},
            {"4", "Quản lý"},
            {"5", "Kế toán"}
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
        table.setShowGrid(false);
        table.setGridColor(new Color(200, 200, 200));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setBackground(new Color(0x808080));

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
            columnIndices = new int[]{1}; // Chỉ lọc cột Tên Quyền
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên Quyền" ->
                    1;
                default ->
                    1;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        System.out.println("Dữ liệu đã được làm mới.");
    }
}
