package View;

import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import View.Dialog.ChiTietNhaCungCap;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class NhaCungCap extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private DefaultTableModel tableModel;
    private Color backgroundColor = new Color(240, 247, 250);

    public NhaCungCap() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("ncc", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        functionBar.setButtonActionListener("create", this::showAddSupplierDialog);
        functionBar.setButtonActionListener("update", this::showEditSupplierDialog);
        functionBar.setButtonActionListener("delete", this::deleteSupplier);
        functionBar.setButtonActionListener("detail", this::showSupplierDetails);
        functionBar.setButtonActionListener("import", () -> JOptionPane.showMessageDialog(this, "Chức năng nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        functionBar.setButtonActionListener("export", () -> JOptionPane.showMessageDialog(this, "Chức năng xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
    }

    private void showAddSupplierDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Nhà Cung Cấp", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("THÊM NHÀ CUNG CẤP", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
        JTextField[] textFields = new JTextField[labels.length];

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textFields[i].setPreferredSize(new Dimension(200, 40));

            if (labels[i].equals("Số điện thoại")) {
                textFields[i].setText("Nhập số điện thoại (10 chữ số)");
                textFields[i].setForeground(Color.GRAY);
                textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập số điện thoại (10 chữ số)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập số điện thoại (10 chữ số)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else if (labels[i].equals("Email")) {
                textFields[i].setText("Nhập email (tùy chọn)");
                textFields[i].setForeground(Color.GRAY);
                textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập email (tùy chọn)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập email (tùy chọn)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            }
            gbc.gridx = i % 2 == 0 ? 1 : 3;
            gbc.gridy = row;
            formPanel.add(textFields[i], gbc);

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JButton btnAdd = new JButton("Thêm nhà cung cấp");
        btnAdd.setBackground(new Color(59, 130, 246));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setPreferredSize(new Dimension(150, 40));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btnAdd.addActionListener(e -> {
            String id = textFields[0].getText();
            String name = textFields[1].getText();
            String address = textFields[2].getText();
            String email = textFields[3].getText();
            String phone = textFields[4].getText();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Mã nhà cung cấp là bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).toString().equals(id)) {
                    JOptionPane.showMessageDialog(dialog, "Mã nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (!phone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!phone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                phone = "";
            }

            if (!email.equals("Nhập email (tùy chọn)")) {
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                email = "";
            }

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{id, name, address, email, phone});
            JOptionPane.showMessageDialog(dialog, "Thêm nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditSupplierDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String address = table.getValueAt(modelRow, 2).toString();
        String email = table.getValueAt(modelRow, 3).toString();
        String phone = table.getValueAt(modelRow, 4).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Nhà Cung Cấp", true);
        dialog.setSize(900, 700);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("CHỈNH SỬA NHÀ CUNG CẤP", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
        JTextField[] textFields = new JTextField[labels.length];

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textFields[i].setPreferredSize(new Dimension(200, 40));

            if (labels[i].equals("Mã nhà cung cấp")) {
                textFields[i].setText(id);
                textFields[i].setEditable(false);
            } else if (labels[i].equals("Số điện thoại")) {
                textFields[i].setText(phone.isEmpty() ? "Nhập số điện thoại (10 chữ số)" : phone);
                textFields[i].setForeground(phone.isEmpty() ? Color.GRAY : Color.BLACK);
                textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập số điện thoại (10 chữ số)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập số điện thoại (10 chữ số)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else if (labels[i].equals("Email")) {
                textFields[i].setText(email.isEmpty() ? "Nhập email (tùy chọn)" : email);
                textFields[i].setForeground(email.isEmpty() ? Color.GRAY : Color.BLACK);
                textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập email (tùy chọn)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập email (tùy chọn)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else {
                switch (i) {
                    case 1 ->
                        textFields[i].setText(name);
                    case 2 ->
                        textFields[i].setText(address);
                }
            }
            gbc.gridx = i % 2 == 0 ? 1 : 3;
            gbc.gridy = row;
            formPanel.add(textFields[i], gbc);

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JButton btnSave = new JButton("Lưu thông tin");
        btnSave.setBackground(new Color(59, 130, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnSave.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setPreferredSize(new Dimension(150, 40));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btnSave.addActionListener(e -> {
            String newName = textFields[1].getText();
            String newAddress = textFields[2].getText();
            String newEmail = textFields[3].getText();
            String newPhone = textFields[4].getText();

            if (!newPhone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!newPhone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                newPhone = "";
            }

            if (!newEmail.equals("Nhập email (tùy chọn)")) {
                if (!newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                newEmail = "";
            }

            if (newName.isEmpty() || newAddress.isEmpty() || newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            table.setValueAt(newName, modelRow, 1);
            table.setValueAt(newAddress, modelRow, 2);
            table.setValueAt(newEmail, modelRow, 3);
            table.setValueAt(newPhone, modelRow, 4);
            JOptionPane.showMessageDialog(dialog, "Cập nhật nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void deleteSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhà cung cấp này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showSupplierDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String address = table.getValueAt(modelRow, 2).toString();
        String email = table.getValueAt(modelRow, 3).toString();
        String phone = table.getValueAt(modelRow, 4).toString();

        ChiTietNhaCungCap detailDialog = new ChiTietNhaCungCap(id, name, address, email, phone);
        detailDialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"});
        cbbFilter.setPreferredSize(new Dimension(120, 25));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(150, 25));

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

    private JScrollPane createTable() {
        String[] columns = {"Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
        Object[][] data = {
            {"NCC001", "Công ty TNHH Cà Phê Trung Nguyên", "Buôn Ma Thuột, Đắk Lắk", "contact@trungnguyen.com", "02623939000"},
            {"NCC002", "Công ty Cổ phần Vinacafe Biên Hòa", "Biên Hòa, Đồng Nai", "info@vinacafe.com.vn", "02513826039"},
            {"NCC003", "Công ty TNHH Nestlé Việt Nam", "Đồng Nai", "nestle@nestle.com.vn", "18006666"},
            {"NCC004", "Công ty Cổ phần Tập đoàn Intimex", "Quận 1, TP.HCM", "info@intimex.com", "02838216177"},
            {"NCC005", "Công ty TNHH Cà phê Ngon", "Gia Lai", "support@caphengon.vn", "02693888888"},
            {"NCC006", "Công ty TNHH Cà phê Phúc Long", "Thủ Đức, TP.HCM", "hotro@phuclong.com.vn", "19006769"},
            {"NCC007", "Công ty TNHH MTV Cà phê Thái Hòa", "Lâm Đồng", "contact@thaihoacoffee.vn", "02633829555"}
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
        table.setShowGrid(true);
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
                case "Mã NCC" ->
                    0;
                case "Tên nhà cung cấp" ->
                    1;
                case "Địa chỉ" ->
                    2;
                case "Email" ->
                    3;
                case "Số điện thoại" ->
                    4;
                default ->
                    1;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        tableModel.fireTableDataChanged();
    }
}
