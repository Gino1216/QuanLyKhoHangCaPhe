package View;

import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EmptyBorder;

public class TaiKhoan extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);

    public TaiKhoan() {
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

    // Method to create top panel (includes function bar and search panel)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("taikhoan", new String[]{"create", "update", "delete", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddAccountDialog);
        functionBar.setButtonActionListener("update", this::showEditAccountDialog);

        return topPanel;
    }

    private void showAddAccountDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Tài Khoản", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM TÀI KHOẢN", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Tên đăng nhập", "Mật khẩu", "Mã quyền", "Tình trạng"};
        JTextField txtUsername = null;
        JPasswordField txtPassword = null;
        JComboBox<String> cbbRole = null;
        JComboBox<String> cbbStatus = null;

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu
            if (labels[i].equals("Tên đăng nhập")) {
                txtUsername = new JTextField(15);
                txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtUsername.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtUsername, gbc);
            } else if (labels[i].equals("Mật khẩu")) {
                txtPassword = new JPasswordField(15);
                txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtPassword.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtPassword, gbc);
            } else if (labels[i].equals("Mã quyền")) {
                cbbRole = new JComboBox<>(new String[]{"Admin", "User"});
                cbbRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbRole.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbRole, gbc);
            } else if (labels[i].equals("Tình trạng")) {
                cbbStatus = new JComboBox<>(new String[]{"Hoạt động", "Tạm ngừng"});
                cbbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbStatus.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbStatus, gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        // Để sử dụng trong ActionListener
        final JTextField finalTxtUsername = txtUsername;
        final JPasswordField finalTxtPassword = txtPassword;
        final JComboBox<String> finalCbbRole = cbbRole;
        final JComboBox<String> finalCbbStatus = cbbStatus;

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm tài khoản");
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
            String username = finalTxtUsername.getText();
            String password = new String(finalTxtPassword.getPassword());
            String role = (String) finalCbbRole.getSelectedItem();
            String status = (String) finalCbbStatus.getSelectedItem();

            // Kiểm tra định dạng tên đăng nhập
            if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
                JOptionPane.showMessageDialog(dialog, "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra các trường bắt buộc
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm vào bảng với mã tài khoản tự động tăng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int newId = table.getRowCount() + 1;
            model.addRow(new Object[]{newId, username, role, status});
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditAccountDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String username = table.getValueAt(modelRow, 1).toString();
        String role = table.getValueAt(modelRow, 2).toString();
        String status = table.getValueAt(modelRow, 3).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Tài Khoản", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA TÀI KHOẢN", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Mã quyền", "Tình trạng"};
        JTextField txtId = null;
        JTextField txtUsername = null;
        JPasswordField txtPassword = null;
        JComboBox<String> cbbRole = null;
        JComboBox<String> cbbStatus = null;

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu
            if (labels[i].equals("Mã tài khoản")) {
                txtId = new JTextField(15);
                txtId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtId.setPreferredSize(new Dimension(200, 40));
                txtId.setText(id);
                txtId.setEditable(false);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtId, gbc);
            } else if (labels[i].equals("Tên đăng nhập")) {
                txtUsername = new JTextField(15);
                txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtUsername.setPreferredSize(new Dimension(200, 40));
                txtUsername.setText(username);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtUsername, gbc);
            } else if (labels[i].equals("Mật khẩu")) {
                txtPassword = new JPasswordField(15);
                txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtPassword.setPreferredSize(new Dimension(200, 40));
                txtPassword.setText(""); // Để trống, người dùng phải nhập lại nếu muốn thay đổi
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtPassword, gbc);
            } else if (labels[i].equals("Mã quyền")) {
                cbbRole = new JComboBox<>(new String[]{"Admin", "User"});
                cbbRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbRole.setPreferredSize(new Dimension(200, 40));
                cbbRole.setSelectedItem(role);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbRole, gbc);
            } else if (labels[i].equals("Tình trạng")) {
                cbbStatus = new JComboBox<>(new String[]{"Hoạt động", "Tạm ngừng"});
                cbbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbStatus.setPreferredSize(new Dimension(200, 40));
                cbbStatus.setSelectedItem(status);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbStatus, gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        // Để sử dụng trong ActionListener
        final JTextField finalTxtUsername = txtUsername;
        final JPasswordField finalTxtPassword = txtPassword;
        final JComboBox<String> finalCbbRole = cbbRole;
        final JComboBox<String> finalCbbStatus = cbbStatus;

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
            String newUsername = finalTxtUsername.getText();
            String newPassword = new String(finalTxtPassword.getPassword());
            String newRole = (String) finalCbbRole.getSelectedItem();
            String newStatus = (String) finalCbbStatus.getSelectedItem();

            // Kiểm tra định dạng tên đăng nhập
            if (!newUsername.matches("^[a-zA-Z0-9_]{3,20}$")) {
                JOptionPane.showMessageDialog(dialog, "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu (nếu nhập mới)
            if (!newPassword.isEmpty() && newPassword.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra các trường bắt buộc
            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật dữ liệu vào bảng
            table.setValueAt(newUsername, modelRow, 1);
            table.setValueAt(newRole, modelRow, 2);
            table.setValueAt(newStatus, modelRow, 3);
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên đăng nhập", "Mã quyền", "Tình trạng"});
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
        String[] columns = {"Mã tài khoản", "Tên đăng nhập", "Mã quyền", "Tình trạng"};
        Object[][] data = {
            {"1", "Admin", "Admin", "Hoạt động"},
            {"2", "Admin2", "User", "Hoạt động"},
            {"3", "Admin3", "User", "Tạm ngừng"},
            {"4", "Admin4", "Admin", "Hoạt động"},
            {"5", "Admin5", "User", "Hoạt động"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setGridColor(new Color(200, 200, 200));
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
            columnIndices = new int[]{1, 2, 3}; // Bỏ cột Mã tài khoản
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên đăng nhập" ->
                    1;
                case "Mã quyền" ->
                    2;
                case "Tình trạng" ->
                    3;
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
