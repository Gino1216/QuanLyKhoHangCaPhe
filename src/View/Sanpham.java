package View;

import Gui.InputDate;
import Gui.MainFunction;
import View.Dialog.ChiTietSanPham;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.*;

public class Sanpham extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);

    public Sanpham() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        scroll = createTable();
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("sanpham", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        functionBar.setButtonActionListener("create", this::showAddProductDialog);
        functionBar.setButtonActionListener("update", this::showEditProductDialog);
        functionBar.setButtonActionListener("delete", this::deleteProduct);
        functionBar.setButtonActionListener("detail", this::showProductDetails);
        functionBar.setButtonActionListener("import", () -> JOptionPane.showMessageDialog(this, "Chức năng nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        functionBar.setButtonActionListener("export", () -> JOptionPane.showMessageDialog(this, "Chức năng xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));

        return topPanel;
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Sản Phẩm Mới", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("THÊM SẢN PHẨM MỚI", JLabel.CENTER);
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

        String[] labels = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Hạn sử dụng", "Mã nguyên liệu", "Khu vực kho"};
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

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

            if (labels[i].equals("Khu vực kho")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"", "Khu vực A", "Khu vực B", "Khu vực C"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[i].setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));
                if (labels[i].equals("Hạn sử dụng")) {
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
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Tạo cấu hình");
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
            String id = textFields[0].getText();
            String name = textFields[1].getText();
            String quantity = textFields[2].getText();
            String expiryDate = textFields[3].getText();
            String materialCode = textFields[4].getText();
            String storageArea = comboBoxes[5] != null ? (String) comboBoxes[5].getSelectedItem() : "";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!expiryDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(expiryDate);
                } else {
                    expiryDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (id.isEmpty() || name.isEmpty() || quantity.isEmpty() || expiryDate.isEmpty() || materialCode.isEmpty() || storageArea.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int qty = Integer.parseInt(quantity);
                if (qty < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Số lượng phải là một số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{id, name, quantity, expiryDate, materialCode, storageArea});
            JOptionPane.showMessageDialog(dialog, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditProductDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String quantity = table.getValueAt(modelRow, 2).toString();
        String expiryDate = table.getValueAt(modelRow, 3).toString();
        String materialCode = table.getValueAt(modelRow, 4).toString();
        String storageArea = table.getValueAt(modelRow, 5).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Sản Phẩm", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("CHỈNH SỬA SẢN PHẨM", JLabel.CENTER);
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

        String[] labels = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Hạn sử dụng", "Mã nguyên liệu", "Khu vực kho"};
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

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

            if (labels[i].equals("Khu vực kho")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"", "Khu vực A", "Khu vực B", "Khu vực C"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[i].setPreferredSize(new Dimension(200, 40));
                comboBoxes[i].setSelectedItem(storageArea);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));
                if (labels[i].equals("Hạn sử dụng")) {
                    textFields[i].setText(expiryDate.isEmpty() ? "dd/MM/yyyy" : expiryDate);
                    textFields[i].setForeground(expiryDate.isEmpty() ? Color.GRAY : Color.BLACK);
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
                        case 0 ->
                            textFields[i].setText(id);
                        case 1 ->
                            textFields[i].setText(name);
                        case 2 ->
                            textFields[i].setText(quantity);
                        case 4 ->
                            textFields[i].setText(materialCode);
                    }
                }
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(textFields[i], gbc);
            }

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
            String newId = textFields[0].getText();
            String newName = textFields[1].getText();
            String newQuantity = textFields[2].getText();
            String newExpiryDate = textFields[3].getText();
            String newMaterialCode = textFields[4].getText();
            String newStorageArea = comboBoxes[5] != null ? (String) comboBoxes[5].getSelectedItem() : "";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!newExpiryDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(newExpiryDate);
                } else {
                    newExpiryDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newId.isEmpty() || newName.isEmpty() || newQuantity.isEmpty() || newExpiryDate.isEmpty() || newMaterialCode.isEmpty() || newStorageArea.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int qty = Integer.parseInt(newQuantity);
                if (qty < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Số lượng phải là một số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            table.setValueAt(newId, modelRow, 0);
            table.setValueAt(newName, modelRow, 1);
            table.setValueAt(newQuantity, modelRow, 2);
            table.setValueAt(newExpiryDate, modelRow, 3);
            table.setValueAt(newMaterialCode, modelRow, 4);
            table.setValueAt(newStorageArea, modelRow, 5);
            JOptionPane.showMessageDialog(dialog, "Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showProductDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String quantity = table.getValueAt(modelRow, 2).toString();
        String expiryDate = table.getValueAt(modelRow, 3).toString();
        String materialCode = table.getValueAt(modelRow, 4).toString();
        String storageArea = table.getValueAt(modelRow, 5).toString();

        ChiTietSanPham detailDialog = new ChiTietSanPham(id, name, quantity, expiryDate, materialCode, storageArea);
        detailDialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã sản phẩm", "Tên sản phẩm", "Mã nguyên liệu"});
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
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Hạn Sử Dụng", "Mã NL", "Khu Vực Kho"};
        Object[][] data = {
            {"SP001", "Cà phê Arabica", "100", "2025-12-31", "NL001", "Khu vực A"},
            {"SP002", "Cà phê Robusta", "150", "2025-11-30", "NL002", "Khu vực B"},
            {"SP003", "Cà phê Blend", "200", "2025-10-15", "NL003", "Khu vực C"},
            {"SP004", "Cà phê Espresso", "120", "2025-08-20", "NL004", "Khu vực A"},
            {"SP005", "Cà phê Mocha", "80", "2025-09-05", "NL005", "Khu vực B"}
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
            columnIndices = new int[]{0, 1, 4};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Mã sản phẩm" ->
                    0;
                case "Tên sản phẩm" ->
                    1;
                case "Mã nguyên liệu" ->
                    4;
                default ->
                    0;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        // Placeholder for refreshing data, e.g., from a database
        tableModel.fireTableDataChanged();
    }
}
