/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Gui.InputDate;
import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.json.ParseException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class KhachHang extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);

    public KhachHang() {
        // Set up FlatLaf theme
        FlatLightLaf.setup();

        // Configure JPanel (you don't need setSize, setLocation, etc. for JPanel)
        setLayout(new BorderLayout(0, 8));

        // Create top panel to hold both toolbar and search panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("ncc", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create search/filter panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Add top panel to JPanel
        add(topPanel, BorderLayout.NORTH);

        // Initialize table
        scroll = createTable();
        add(scroll, BorderLayout.CENTER); // Table takes remaining space

        // Set background
        setBackground(backgroundColor);
        functionBar.setButtonActionListener("create", this::showAddSupplierDialog);
        functionBar.setButtonActionListener("update", this::showEditSupplierDialog);
    }

    private void showAddSupplierDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Khách Hàng", true);
        dialog.setSize(900, 700); // Tăng kích thước dialog để chứa thêm các trường
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM KHÁCH HÀNG", JLabel.CENTER);
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
        String[] labels = {"Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày tham gia", "Email"};
        JTextField[] textFields = new JTextField[labels.length];

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
            } else if (labels[i].equals("Ngày tham gia")) {
                textFields[i].setText("dd/MM/yyyy");
                textFields[i].setForeground(Color.GRAY);
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

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm khách hàng");
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
            String name = textFields[0].getText();
            String phone = textFields[1].getText();
            String address = textFields[2].getText();
            String joinDate = textFields[3].getText();
            String email = textFields[4].getText();

            // Kiểm tra định dạng ngày tham gia
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
            } catch (java.text.ParseException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Kiểm tra định dạng số điện thoại
            if (!phone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!phone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                phone = "";
            }

            // Kiểm tra định dạng email (nếu có)
            if (!email.equals("Nhập email (tùy chọn)")) {
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                email = "";
            }

            // Kiểm tra các trường bắt buộc
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || joinDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm vào bảng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int newId = table.getRowCount() + 1;
            model.addRow(new Object[]{newId, name, address, joinDate, email, phone});
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
        dialog.setSize(900, 700); // Tăng kích thước dialog để chứa thêm các trường
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA KHÁCH HÀNG", JLabel.CENTER);
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
        String[] labels = {"Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày tham gia", "Email"};
        JTextField[] textFields = new JTextField[labels.length];

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
            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textFields[i].setPreferredSize(new Dimension(200, 40));

            if (labels[i].equals("Mã khách hàng")) {
                textFields[i].setText(id);
                textFields[i].setEditable(false); // Không cho chỉnh sửa mã khách hàng
            } else if (labels[i].equals("Số điện thoại")) {
                textFields[i].setText(phone);
                textFields[i].setForeground(Color.BLACK);
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
            } else if (labels[i].equals("Ngày tham gia")) {
                textFields[i].setText(joinDate);
                textFields[i].setForeground(Color.BLACK);
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
            } else {
                // Điền dữ liệu từ bảng
                switch (i) {
                    case 1:
                        textFields[i].setText(name);
                        break;
                    case 3:
                        textFields[i].setText(address);
                        break;
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
            String newName = textFields[1].getText();
            String newPhone = textFields[2].getText();
            String newAddress = textFields[3].getText();
            String newJoinDate = textFields[4].getText();
            String newEmail = textFields[5].getText();

            // Kiểm tra định dạng ngày tham gia
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
            } catch (java.text.ParseException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Kiểm tra định dạng số điện thoại
            if (!newPhone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!newPhone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                newPhone = "";
            }

            // Kiểm tra định dạng email (nếu có)
            if (!newEmail.equals("Nhập email (tùy chọn)")) {
                if (!newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                newEmail = "";
            }

            // Kiểm tra các trường bắt buộc
            if (newName.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty() || newJoinDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật dữ liệu vào bảng
            table.setValueAt(newName, modelRow, 1); // Tên khách hàng
            table.setValueAt(newAddress, modelRow, 2); // Địa chỉ
            table.setValueAt(newJoinDate, modelRow, 3); // Ngày tham gia
            table.setValueAt(newEmail, modelRow, 4); // Email
            table.setValueAt(newPhone, modelRow, 5); // Số điện thoại
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên nhà cung cấp", "Địa chỉ", "Email"});
        cbbFilter.setPreferredSize(new Dimension(100, 25));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(100, 25));

        btnRefresh = new JButton("Làm mới");
        try {
            btnRefresh.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.png")));
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
        table.setShowGrid(true);

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
                case "Tên Khách Hàng" ->
                    1;
                case "Địa chỉ" ->
                    2;
                case "Số Điện Thoại" ->
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
