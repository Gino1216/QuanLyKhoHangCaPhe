package View;

import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;
import View.Dialog.ChiTietNhanVien;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class NhanVien extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);

    public NhanVien() {
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

        functionBar = new MainFunction("nhanvien", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        functionBar.setButtonActionListener("create", this::showAddEmployeeDialog);
        functionBar.setButtonActionListener("update", this::showEditEmployeeDialog);
        functionBar.setButtonActionListener("delete", this::deleteEmployee);
        functionBar.setButtonActionListener("detail", this::showEmployeeDetails);
        functionBar.setButtonActionListener("import", () -> JOptionPane.showMessageDialog(this, "Chức năng nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        functionBar.setButtonActionListener("export", () -> JOptionPane.showMessageDialog(this, "Chức năng xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));

        return topPanel;
    }

    private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Nhân Viên", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("THÊM NHÂN VIÊN", JLabel.CENTER);
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

        String[] labels = {"Họ tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email"};
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String> cbbGender = null;

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

            if (labels[i].equals("Giới tính")) {
                cbbGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
                cbbGender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbGender.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbGender, gbc);
            } else {
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
                } else if (labels[i].equals("Ngày sinh")) {
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
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        final JComboBox<String> finalCbbGender = cbbGender;

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JButton btnAdd = new JButton("Thêm nhân viên");
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
            String birthDate = textFields[2].getText();
            String phone = textFields[3].getText();
            String email = textFields[4].getText();
            String gender = (String) finalCbbGender.getSelectedItem();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!birthDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(birthDate);
                } else {
                    birthDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày sinh phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!phone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (table.getValueAt(i, 4).toString().equals(phone)) {
                        JOptionPane.showMessageDialog(dialog, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
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

            if (name.isEmpty() || birthDate.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newId = table.getRowCount() + 1;
            tableModel.addRow(new Object[]{String.valueOf(newId), name, gender, birthDate, phone, email});
            JOptionPane.showMessageDialog(dialog, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditEmployeeDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String gender = table.getValueAt(modelRow, 2).toString();
        String birthDate = table.getValueAt(modelRow, 3).toString();
        String phone = table.getValueAt(modelRow, 4).toString();
        String email = table.getValueAt(modelRow, 5).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Nhân Viên", true);
        dialog.setSize(900, 700);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("CHỈNH SỬA NHÂN VIÊN", JLabel.CENTER);
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

        String[] labels = {"Mã nhân viên", "Họ tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email"};
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String> cbbGender = null;

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

            if (labels[i].equals("Giới tính")) {
                cbbGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
                cbbGender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbGender.setPreferredSize(new Dimension(200, 40));
                cbbGender.setSelectedItem(gender);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbGender, gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));

                if (labels[i].equals("Mã nhân viên")) {
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
                } else if (labels[i].equals("Ngày sinh")) {
                    textFields[i].setText(birthDate.isEmpty() ? "dd/MM/yyyy" : birthDate);
                    textFields[i].setForeground(birthDate.isEmpty() ? Color.GRAY : Color.BLACK);
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
                    switch (i) {
                        case 1 ->
                            textFields[i].setText(name);
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

        final JComboBox<String> finalCbbGender = cbbGender;

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

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
            String newBirthDate = textFields[3].getText();
            String newPhone = textFields[4].getText();
            String newEmail = textFields[5].getText();
            String newGender = (String) finalCbbGender.getSelectedItem();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                if (!newBirthDate.equals("dd/MM/yyyy")) {
                    dateFormat.parse(newBirthDate);
                } else {
                    newBirthDate = "";
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày sinh phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPhone.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!newPhone.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (i != modelRow && table.getValueAt(i, 4).toString().equals(newPhone)) {
                        JOptionPane.showMessageDialog(dialog, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
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

            if (newName.isEmpty() || newBirthDate.isEmpty() || newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            table.setValueAt(newName, modelRow, 1);
            table.setValueAt(newGender, modelRow, 2);
            table.setValueAt(newBirthDate, modelRow, 3);
            table.setValueAt(newPhone, modelRow, 4);
            table.setValueAt(newEmail, modelRow, 5);
            JOptionPane.showMessageDialog(dialog, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showEmployeeDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String gender = table.getValueAt(modelRow, 2).toString();
        String birthDate = table.getValueAt(modelRow, 3).toString();
        String phone = table.getValueAt(modelRow, 4).toString();
        String email = table.getValueAt(modelRow, 5).toString();

        ChiTietNhanVien detailDialog = new ChiTietNhanVien(id, name, gender, birthDate, phone, email);
        detailDialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã NV", "Họ tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email"});
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
        String[] columns = {"Mã NV", "Họ tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email"};
        Object[][] data = {
            {"NV001", "Trần Nhật Sinh", "Nam", "20/12/2003", "0387913347", "transinh085@gmail.com"},
            {"NV002", "Hoàng Gia Bảo", "Nam", "11/04/2003", "0355574322", "musicanime2501@gmail.com"},
            {"NV003", "Đỗ Nam Công Chính", "Nam", "11/04/2003", "0912345678", "chinchinh@gmail.com"},
            {"NV004", "Đình Ngọc Ân", "Nam", "03/04/2003", "0987654321", "ngocan@gmail.com"},
            {"NV005", "Vũ Trung Hiếu", "Nam", "06/05/2003", "0978123456", "hieu@gmail.com"}
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
            columnIndices = new int[]{0, 1, 2, 3, 4, 5};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Mã NV" ->
                    0;
                case "Họ tên" ->
                    1;
                case "Giới tính" ->
                    2;
                case "Ngày sinh" ->
                    3;
                case "Số điện thoại" ->
                    4;
                case "Email" ->
                    5;
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
