package View;

import Gui.InputDate;
import Gui.MainFunction;
import View.Dialog.ChiTietKhachHang;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.*;

public class KhachHang extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private DefaultTableModel tableModel;
    private Color backgroundColor = new Color(240, 247, 250);

    public KhachHang() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("khachhang", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        functionBar.setButtonActionListener("create", this::showAddCustomerDialog);
        functionBar.setButtonActionListener("update", this::showEditCustomerDialog);
        functionBar.setButtonActionListener("delete", this::deleteCustomer);
        functionBar.setButtonActionListener("detail", this::showCustomerDetails);
        functionBar.setButtonActionListener("import", () -> JOptionPane.showMessageDialog(this, "Chức năng nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        functionBar.setButtonActionListener("export", () -> JOptionPane.showMessageDialog(this, "Chức năng xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
    }

    private void showAddCustomerDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Khách Hàng", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("THÊM KHÁCH HÀNG", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày tham gia", "Email"};
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
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập số điện thoại (10 chữ số)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
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
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập email (tùy chọn)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập email (tùy chọn)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else if (labels[i].equals("Ngày tham gia")) {
                textFields[i].setText("dd/MM/yyyy");
                textFields[i].setForeground(Color.GRAY);
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("dd/MM/yyyy")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("dd/MM/yyyy");
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
        JButton btnAdd = new JButton("Thêm khách hàng");
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
            String name = textFields[0].getText();
            String phone = textFields[1].getText();
            String address = textFields[2].getText();
            String joinDate = textFields[3].getText();
            String email = textFields[4].getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!joinDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(joinDate);
                } else {
                    joinDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày tham gia phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
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

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || joinDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newId = table.getRowCount() + 1;
            tableModel.addRow(new Object[]{newId, name, address, joinDate, email, phone});
            JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditCustomerDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String address = table.getValueAt(modelRow, 2).toString();
        String joinDate = table.getValueAt(modelRow, 3).toString();
        String email = table.getValueAt(modelRow, 4).toString();
        String phone = table.getValueAt(modelRow, 5).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Khách Hàng", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("CHỈNH SỬA KHÁCH HÀNG", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày tham gia", "Email"};
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

            if (labels[i].equals("Mã khách hàng")) {
                textFields[i].setText(id);
                textFields[i].setEditable(false);
            } else if (labels[i].equals("Số điện thoại")) {
                textFields[i].setText(phone.isEmpty() ? "Nhập số điện thoại (10 chữ số)" : phone);
                textFields[i].setForeground(phone.isEmpty() ? Color.GRAY : Color.BLACK);
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập số điện thoại (10 chữ số)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
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
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("Nhập email (tùy chọn)")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("Nhập email (tùy chọn)");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else if (labels[i].equals("Ngày tham gia")) {
                textFields[i].setText(joinDate.isEmpty() ? "dd/MM/yyyy" : joinDate);
                textFields[i].setForeground(joinDate.isEmpty() ? Color.GRAY : Color.BLACK);
                textFields[i].addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().equals("dd/MM/yyyy")) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent evt) {
                        JTextField textField = (JTextField) evt.getSource();
                        if (textField.getText().isEmpty()) {
                            textField.setText("dd/MM/yyyy");
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
            } else {
                switch (i) {
                    case 1 ->
                        textFields[i].setText(name);
                    case 3 ->
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
            String newPhone = textFields[2].getText();
            String newAddress = textFields[3].getText();
            String newJoinDate = textFields[4].getText();
            String newEmail = textFields[5].getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!newJoinDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(newJoinDate);
                } else {
                    newJoinDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày tham gia phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

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

            if (newName.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty() || newJoinDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            table.setValueAt(newName, modelRow, 1);
            table.setValueAt(newAddress, modelRow, 2);
            table.setValueAt(newJoinDate, modelRow, 3);
            table.setValueAt(newEmail, modelRow, 4);
            table.setValueAt(newPhone, modelRow, 5);
            JOptionPane.showMessageDialog(dialog, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showCustomerDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String address = table.getValueAt(modelRow, 2).toString();
        String joinDate = table.getValueAt(modelRow, 3).toString();
        String email = table.getValueAt(modelRow, 4).toString();
        String phone = table.getValueAt(modelRow, 5).toString();

        ChiTietKhachHang detailDialog = new ChiTietKhachHang(id, name, address, joinDate, email, phone);
        detailDialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Email"});
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

    private JScrollPane createTable() {
        String[] columns = {"Mã KH", "Tên khách hàng", "Địa chỉ", "Ngày tham gia", "Email", "Số điện thoại"};
        Object[][] data = {
            {1, "Nguyễn Văn A", "Quận 1, TP.HCM", "01/01/2023", "nguyenvana@gmail.com", "0909123456"},
            {2, "Trần Thị B", "Hà Nội", "15/02/2023", "tranthib@yahoo.com", "0912345678"},
            {3, "Lê Văn C", "Đà Nẵng", "22/03/2023", "levanc123@gmail.com", "0938123123"},
            {4, "Phạm Thị D", "Cần Thơ", "10/04/2023", "phamthid@outlook.com", "0923456789"},
            {5, "Đỗ Mạnh E", "Nha Trang", "30/04/2023", "doemanh@gmail.com", "0966789123"},
            {6, "Võ Thị F", "Huế", "12/05/2023", "fvo@example.com", "0978123123"},
            {7, "Bùi Văn G", "Biên Hòa, Đồng Nai", "01/06/2023", "buig@yahoo.com", "0989988776"}
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
            columnIndices = new int[]{1, 2, 4, 5};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên khách hàng" ->
                    1;
                case "Địa chỉ" ->
                    2;
                case "Số điện thoại" ->
                    5;
                case "Email" ->
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
