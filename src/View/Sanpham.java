/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Gui.InputDate;
import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author hjepr
 */
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

    // Method to create top panel (includes function bar and search/filter panel)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("ncc", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddProductDialog);
        functionBar.setButtonActionListener("update", this::showEditProductDialog);

        return topPanel;
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Sản Phẩm Mới", true);
        dialog.setSize(900, 700); // Tăng kích thước dialog để chứa các thành phần lớn hơn
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM SẢN PHẨM MỚI", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Tăng font chữ tiêu đề
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0)); // Tăng padding cho tiêu đề
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding cho form
        formPanel.setBackground(new Color(255, 255, 255, 255)); // Màu nền trắng
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các nhãn và trường nhập liệu
        String[] labels = {
            "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Hạn sử dụng", "Mã nguyên liệu", "Khu vực kho"
        };

        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ cho nhãn
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu hoặc combobox
            if (labels[i].equals("Khu vực kho")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"", "Khu vực A", "Khu vực B", "Khu vực C"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ
                comboBoxes[i].setPreferredSize(new Dimension(200, 40)); // Kích thước combobox
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ
                textFields[i].setPreferredSize(new Dimension(200, 40)); // Kích thước textfield
                // Thêm placeholder cho Hạn sử dụng
                if (labels[i].equals("Hạn sử dụng")) {
                    textFields[i].setText("dd/MM/yyyy"); // Placeholder hướng dẫn định dạng
                    textFields[i].setForeground(Color.GRAY); // Màu chữ placeholder
                    textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            JTextField textField = (JTextField) evt.getSource();
                            if (textField.getText().equals("dd/MM/yyyy")) {
                                textField.setText("");
                                textField.setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
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

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); // Tăng khoảng cách giữa các nút
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Tạo cấu hình");
        btnAdd.setBackground(new Color(59, 130, 246));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tăng font chữ cho nút
        btnAdd.setPreferredSize(new Dimension(180, 50)); // Tăng kích thước nút
        btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Tăng padding cho nút

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tăng font chữ cho nút
        btnCancel.setPreferredSize(new Dimension(180, 50)); // Tăng kích thước nút
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Tăng padding cho nút

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra dữ liệu đầu vào
                String id = textFields[0].getText(); // Mã sản phẩm
                String name = textFields[1].getText(); // Tên sản phẩm
                String quantity = textFields[2].getText(); // Số lượng
                String expiryDate = textFields[3].getText(); // Hạn sử dụng
                String materialCode = textFields[4].getText(); // Mã nguyên liệu
                String storageArea = comboBoxes[5] != null ? (String) comboBoxes[5].getSelectedItem() : ""; // Khu vực kho

                // Kiểm tra định dạng ngày tháng cho Hạn sử dụng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false); // Không cho phép định dạng lỏng lẻo
                try {
                    if (!expiryDate.equals("dd/MM/yyyy")) {
                        dateFormat.parse(expiryDate); // Thử phân tích ngày
                    } else {
                        expiryDate = ""; // Nếu vẫn là placeholder thì để rỗng
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra các trường bắt buộc
                if (id.isEmpty() || name.isEmpty() || quantity.isEmpty() || expiryDate.isEmpty() || materialCode.isEmpty() || storageArea.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Thêm vào bảng
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{id, name, quantity, expiryDate, materialCode});
                dialog.dispose();
            }
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

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Sản Phẩm", true);
        dialog.setSize(900, 700); // Tăng kích thước dialog để chứa các thành phần lớn hơn
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA SẢN PHẨM", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Tăng font chữ tiêu đề
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0)); // Tăng padding cho tiêu đề
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding cho form
        formPanel.setBackground(new Color(255, 255, 255, 255)); // Màu nền trắng
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các nhãn và trường nhập liệu
        String[] labels = {
            "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Hạn sử dụng", "Mã nguyên liệu", "Khu vực kho"
        };

        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ cho nhãn
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu hoặc combobox
            if (labels[i].equals("Khu vực kho")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"", "Khu vực A", "Khu vực B", "Khu vực C"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ
                comboBoxes[i].setPreferredSize(new Dimension(200, 40)); // Kích thước combobox
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ
                textFields[i].setPreferredSize(new Dimension(200, 40)); // Kích thước textfield
                // Thêm placeholder cho Hạn sử dụng
                if (labels[i].equals("Hạn sử dụng")) {
                    textFields[i].setText("dd/MM/yyyy"); // Placeholder hướng dẫn định dạng
                    textFields[i].setForeground(Color.GRAY); // Màu chữ placeholder
                    textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            JTextField textField = (JTextField) evt.getSource();
                            if (textField.getText().equals("dd/MM/yyyy")) {
                                textField.setText("");
                                textField.setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            JTextField textField = (JTextField) evt.getSource();
                            if (textField.getText().isEmpty()) {
                                textField.setText("dd/MM/yyyy");
                                textField.setForeground(Color.GRAY);
                            }
                        }
                    });
                    // Điền dữ liệu từ bảng nếu không phải placeholder
                    if (!expiryDate.isEmpty()) {
                        textFields[i].setText(expiryDate);
                        textFields[i].setForeground(Color.BLACK);
                    }
                } else {
                    // Điền dữ liệu từ bảng cho các trường khác
                    switch (i) {
                        case 0:
                            textFields[i].setText(id);
                            break;
                        case 1:
                            textFields[i].setText(name);
                            break;
                        case 2:
                            textFields[i].setText(quantity);
                            break;
                        case 4:
                            textFields[i].setText(materialCode);
                            break;
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

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); // Tăng khoảng cách giữa các nút
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnSave = new JButton("Lưu thông tin");
        btnSave.setBackground(new Color(59, 130, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tăng font chữ cho nút
        btnSave.setPreferredSize(new Dimension(180, 50)); // Tăng kích thước nút
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Tăng padding cho nút

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tăng font chữ cho nút
        btnCancel.setPreferredSize(new Dimension(180, 50)); // Tăng kích thước nút
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Tăng padding cho nút

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra dữ liệu đầu vào
                String newId = textFields[0].getText(); // Mã sản phẩm
                String newName = textFields[1].getText(); // Tên sản phẩm
                String newQuantity = textFields[2].getText(); // Số lượng
                String newExpiryDate = textFields[3].getText(); // Hạn sử dụng
                String newMaterialCode = textFields[4].getText(); // Mã nguyên liệu
                String newStorageArea = comboBoxes[5] != null ? (String) comboBoxes[5].getSelectedItem() : ""; // Khu vực kho

                // Kiểm tra định dạng ngày tháng cho Hạn sử dụng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false); // Không cho phép định dạng lỏng lẻo
                try {
                    if (!newExpiryDate.equals("dd/MM/yyyy")) {
                        dateFormat.parse(newExpiryDate); // Thử phân tích ngày
                    } else {
                        newExpiryDate = ""; // Nếu vẫn là placeholder thì để rỗng
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra các trường bắt buộc
                if (newId.isEmpty() || newName.isEmpty() || newQuantity.isEmpty() || newExpiryDate.isEmpty() || newMaterialCode.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cập nhật dữ liệu vào bảng
                table.setValueAt(newId, modelRow, 0);
                table.setValueAt(newName, modelRow, 1);
                table.setValueAt(newQuantity, modelRow, 2);
                table.setValueAt(newExpiryDate, modelRow, 3);
                table.setValueAt(newMaterialCode, modelRow, 4);
                dialog.dispose();
            }
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên nhà cung cấp", "Địa chỉ", "Email"});
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
        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Hạn Sử Dụng", "Mã NL", "Khu Vực Kho"};
        Object[][] data = {
            {"SP001", "Cà phê Arabica", 100, "2025-12-31", "NL001"},
            {"SP002", "Cà phê Robusta", 150, "2025-11-30", "NL002"},
            {"SP003", "Cà phê Blend", 200, "2025-10-15", "NL003"},
            {"SP004", "Cà phê Espresso", 120, "2025-08-20", "NL004"},
            {"SP005", "Cà phê Mocha", 80, "2025-09-05", "NL005"}
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
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);

        // Auto-resize columns
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
                    1;
                case "Địa chỉ" ->
                    2;
                case "Email" ->
                    3;
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
}
