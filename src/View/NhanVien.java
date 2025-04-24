package View;

import DTO.NhanVienDTO;
import Dao.DaoNV;
import EX.ExNhanVien;
import Gui.MainFunction;
import Repository.NhanVienRepo;
import View.Dialog.ChiTietNhanVien;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class NhanVien extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);

    public NhanVien() {
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

    private void exportToExcel() {
        ExNhanVien.exportNhanVienToExcel("E:/DanhSachNhanVien.xlsx"); // Dùng / thay cho \\
    }




    // Method to create top panel (includes function bar and search panel)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("nhanvien", new String[]{"create", "update", "delete", "detail", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddEmployeeDialog);
        functionBar.setButtonActionListener("update", this::showEditEmployeeDialog);
        functionBar.setButtonActionListener("delete", this::DeleteEmploy);
        functionBar.setButtonActionListener("detail", this::showEmployeeDetails);
        functionBar.setButtonActionListener("export", this::exportToExcel);




        return topPanel;
    }

    private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Nhân Viên", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM NHÂN VIÊN", JLabel.CENTER);
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
        String[] labels = {"Mã Nhân Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Số Điện Thoại", "Chức Vụ"};
        JTextField[] textFields = new JTextField[6]; // 6 fields: Mã NV, Họ Tên, Ngày Sinh, Địa Chỉ, Email, Số ĐT
        JComboBox<String>[] comboBoxes = new JComboBox[2]; // 2 fields: Giới Tính, Chức Vụ

        int textFieldIndex = 0;
        int comboBoxIndex = 0;
        int row = 0;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Label
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Input field
            if (labels[i].equals("Giới Tính")) {
                comboBoxes[comboBoxIndex] = new JComboBox<>(new String[]{"Nam", "Nữ"});
                comboBoxes[comboBoxIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[comboBoxIndex].setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[comboBoxIndex], gbc);
                comboBoxIndex++;
            } else if (labels[i].equals("Chức Vụ")) {
                comboBoxes[comboBoxIndex] = new JComboBox<>(new String[]{"Quản Lý", "Nhân Viên", "Kế Toán"});
                comboBoxes[comboBoxIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[comboBoxIndex].setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[comboBoxIndex], gbc);
                comboBoxIndex++;
            } else {
                textFields[textFieldIndex] = new JTextField(15);
                textFields[textFieldIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[textFieldIndex].setPreferredSize(new Dimension(200, 40));

                // Placeholder for Số Điện Thoại
                if (labels[i].equals("Số Điện Thoại")) {
                    textFields[textFieldIndex].setText("Nhập số điện thoại (10 chữ số)");
                    textFields[textFieldIndex].setForeground(Color.GRAY);
                    textFields[textFieldIndex].addFocusListener(new java.awt.event.FocusAdapter() {
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
                }
                // Placeholder for Email
                else if (labels[i].equals("Email")) {
                    textFields[textFieldIndex].setText("Nhập email (tùy chọn)");
                    textFields[textFieldIndex].setForeground(Color.GRAY);
                    textFields[textFieldIndex].addFocusListener(new java.awt.event.FocusAdapter() {
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
                // Placeholder for Ngày Sinh
                else if (labels[i].equals("Ngày Sinh")) {
                    textFields[textFieldIndex].setText("dd/MM/yyyy");
                    textFields[textFieldIndex].setForeground(Color.GRAY);
                    textFields[textFieldIndex].addFocusListener(new java.awt.event.FocusAdapter() {
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
                formPanel.add(textFields[textFieldIndex], gbc);
                textFieldIndex++;
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm Nhân Viên");
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
            // Lấy giá trị từ các trường
            String maNV = textFields[0].getText().trim(); // Mã Nhân Viên
            String hoTen = textFields[1].getText().trim(); // Họ Tên
            String ngaySinh = textFields[2].getText().trim(); // Ngày Sinh
            String diaChi = textFields[3].getText().trim(); // Địa Chỉ
            String soDT = textFields[5].getText().trim(); // Số Điện Thoại
            String email = textFields[4].getText().trim(); // Email
            String gioiTinh = (String) comboBoxes[0].getSelectedItem(); // Giới Tính
            String chucVu = (String) comboBoxes[1].getSelectedItem(); // Chức Vụ

            // Kiểm tra các trường bắt buộc
            if (maNV.isEmpty() || hoTen.isEmpty() || soDT.isEmpty() || diaChi.isEmpty() || ngaySinh.isEmpty() || soDT.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại
            if (!soDT.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!soDT.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                soDT = "";
            }

            // Kiểm tra email
            if (!email.equals("Nhập email (tùy chọn)")) {
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                email = "";
            }

            // Kiểm tra ngày sinh
            if (ngaySinh.equals("dd/MM/yyyy") || ngaySinh.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false); // Không cho phép ngày không hợp lệ
                    Date birthDate = sdf.parse(ngaySinh);
                    Date currentDate = new Date();
                    if (birthDate.after(currentDate)) {
                        JOptionPane.showMessageDialog(dialog, "Ngày sinh không được trong tương lai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Định dạng lại ngày sinh về yyyy-MM-dd để lưu vào cơ sở dữ liệu
                    ngaySinh = new SimpleDateFormat("yyyy-MM-dd").format(birthDate);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Ngày sinh phải có định dạng dd/MM/yyyy và hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Tạo đối tượng NhanVienDTO
            NhanVienDTO nv = new NhanVienDTO();
            nv.setMaNV(maNV);
            nv.setHoTen(hoTen);
            nv.setGioiTinh(gioiTinh);
            nv.setNgaySinh(ngaySinh);
            nv.setDiaChi(diaChi);
            nv.setEmail(email);
            nv.setSoDT(soDT);
            nv.setChucVu(chucVu);

            // Thêm vào cơ sở dữ liệu
            try {
                DaoNV daoNhanVien = new DaoNV();
                if (daoNhanVien.kiemTraMaNVTonTai(maNV)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Mã nhân viên đã tồn tại! Vui lòng nhập mã khác.",
                            "Trùng mã nhân viên",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                daoNhanVien.themNhanVien(nv);

                // Cập nhật bảng hiển thị
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getGioiTinh(),
                        nv.getNgaySinh(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.getSoDT(),
                        nv.getChucVu()
                });

                JOptionPane.showMessageDialog(dialog, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
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
        String maNV = table.getValueAt(modelRow, 0).toString(); // Mã NV
        String hoTen = table.getValueAt(modelRow, 1).toString(); // Họ Tên
        String gioiTinh = table.getValueAt(modelRow, 2).toString(); // Giới Tính
        String ngaySinh = table.getValueAt(modelRow, 3).toString(); // Ngày Sinh
        String diaChi = table.getValueAt(modelRow, 4).toString(); // Địa Chỉ
        String email = table.getValueAt(modelRow, 5).toString(); // Email
        String soDT = table.getValueAt(modelRow, 6).toString(); // Số Điện Thoại
        String chucVu = table.getValueAt(modelRow, 7).toString(); // Chức Vụ

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Nhân Viên", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA NHÂN VIÊN", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Mã Nhân Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Số Điện Thoại", "Chức Vụ"};
        JTextField[] textFields = new JTextField[6]; // For Mã NV, Họ Tên, Ngày Sinh, Địa Chỉ, Email, Số ĐT
        JComboBox<String>[] comboBoxes = new JComboBox[2]; // For Giới Tính, Chức Vụ

        int textFieldIndex = 0;
        int comboBoxIndex = 0;
        int row = 0;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Label
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Input field or combobox
            if (labels[i].equals("Giới Tính")) {
                comboBoxes[comboBoxIndex] = new JComboBox<>(new String[]{"Nam", "Nữ"});
                comboBoxes[comboBoxIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[comboBoxIndex].setPreferredSize(new Dimension(200, 40));
                comboBoxes[comboBoxIndex].setSelectedItem(gioiTinh);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[comboBoxIndex], gbc);
                comboBoxIndex++;
            } else if (labels[i].equals("Chức Vụ")) {
                comboBoxes[comboBoxIndex] = new JComboBox<>(new String[]{"Quản Lý", "Nhân Viên", "Kế Toán"});
                comboBoxes[comboBoxIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[comboBoxIndex].setPreferredSize(new Dimension(200, 40));
                comboBoxes[comboBoxIndex].setSelectedItem(chucVu);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[comboBoxIndex], gbc);
                comboBoxIndex++;
            } else {
                textFields[textFieldIndex] = new JTextField(15);
                textFields[textFieldIndex].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[textFieldIndex].setPreferredSize(new Dimension(200, 40));
                if (labels[i].equals("Mã Nhân Viên")) {
                    textFields[textFieldIndex].setEditable(false);
                    textFields[textFieldIndex].setBackground(new Color(240, 240, 240));
                }

                // Set values from table
                switch (labels[i]) {
                    case "Mã Nhân Viên":
                        textFields[textFieldIndex].setText(maNV);
                        break;
                    case "Họ Tên":
                        textFields[textFieldIndex].setText(hoTen);
                        break;
                    case "Ngày Sinh":
                        textFields[textFieldIndex].setText(ngaySinh);
                        textFields[textFieldIndex].setEditable(false);
                        break;
                    case "Địa Chỉ":
                        textFields[textFieldIndex].setText(diaChi);
                        break;
                    case "Email":
                        textFields[textFieldIndex].setText(email);
                        break;
                    case "Số Điện Thoại":
                        textFields[textFieldIndex].setText(soDT);
                        break;
                }
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(textFields[textFieldIndex], gbc);
                textFieldIndex++;
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnSave = new JButton("Lưu Thông Tin");
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
            // Get values from fields
            String maNVInput = textFields[0].getText().trim();
            String hoTenInput = textFields[1].getText().trim();
            String ngaySinhInput = textFields[2].getText().trim();
            String diaChiInput = textFields[3].getText().trim();
            String emailInput = textFields[4].getText().trim();
            String soDTInput = textFields[5].getText().trim();
            String gioiTinhInput = (String) comboBoxes[0].getSelectedItem();
            String chucVuInput = (String) comboBoxes[1].getSelectedItem();

            // Validate required fields
            if (hoTenInput.isEmpty() || ngaySinhInput.isEmpty() || diaChiInput.isEmpty() || soDTInput.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate phone number
            if (!soDTInput.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email (if provided)
            if (!emailInput.isEmpty() && !emailInput.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date of birth

            // Create NhanVienDTO object
            NhanVienDTO nv = new NhanVienDTO();
            nv.setMaNV(maNVInput);
            nv.setHoTen(hoTenInput);
            nv.setGioiTinh(gioiTinhInput);
            nv.setNgaySinh(ngaySinhInput);
            nv.setDiaChi(diaChiInput);
            nv.setEmail(emailInput);
            nv.setSoDT(soDTInput);
            nv.setChucVu(chucVuInput);

            // Update database
            try {
                DaoNV daoNhanVien = new DaoNV();
                boolean success = daoNhanVien.suaNhanVien(nv);

                if (success) {
                    // Update table
                    table.setValueAt(maNVInput, modelRow, 0);
                    table.setValueAt(hoTenInput, modelRow, 1);
                    table.setValueAt(gioiTinhInput, modelRow, 2);
                    table.setValueAt(ngaySinhInput, modelRow, 3);
                    table.setValueAt(diaChiInput, modelRow, 4);
                    table.setValueAt(emailInput, modelRow, 5);
                    table.setValueAt(soDTInput, modelRow, 6);
                    table.setValueAt(chucVuInput, modelRow, 7);

                    JOptionPane.showMessageDialog(dialog, "Cập nhật nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Keyboard shortcuts
        dialog.getRootPane().setDefaultButton(btnSave);
        dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        dialog.setVisible(true);
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả","Mã Nhân Viên", "Họ Tên","Giới Tính","Ngày Sinh","Địa Chỉ", "Email",  "Số Điện Thoại","Chức Vụ" });
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


    private void customizeTableAppearance() {
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private JScrollPane createTable() {
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Columns
        String[] columns = {"Mã Nhân Viên", "Họ Tên","Giới Tính","Ngày Sinh","Địa Chỉ", "Email",  "Số Điện Thoại","Chức Vụ"};

        // Tạo model với 0 row ban đầu
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo table với model
        table = new JTable(model);
        customizeTableAppearance();

        // Load data (tách riêng để có thể gọi lại khi cần refresh)
        loadTableData(model);

        return new JScrollPane(table);
    }

    private void loadTableData(DefaultTableModel model) {
        try {
            NhanVienRepo repo = new DaoNV();
            List<NhanVienDTO> danhSach =repo.layDanhSachNhanVien();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (NhanVienDTO nv : danhSach) {
                model.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getGioiTinh(),
                        nv.getNgaySinh(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.getSoDT(),
                        nv.getChucVu(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
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
            columnIndices = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Mã nhân viên" -> 0;
                case "Họ tên" -> 1;
                case "Giới tính" -> 2;
                case "Ngày sinh" -> 3;
                case "Địa chỉ" -> 4;
                case "Email" -> 5;
                case "Số điện thoại" -> 6;
                case "Chức vụ" -> 7;
                default -> 0;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
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
        String address =table.getValueAt(modelRow,6).toString();
        String chucvu =table.getValueAt(modelRow,7).toString();


        ChiTietNhanVien detailDialog = new ChiTietNhanVien(id, name, gender, birthDate, phone, email,address,chucvu);
        detailDialog.setVisible(true);
    }


    private void DeleteEmploy() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = table.getValueAt(selectedRow, 0).toString();
        String tenNV = table.getValueAt(selectedRow, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên:\n" +
                        "Mã: " + maNV + "\n" +
                        "Tên: " + tenNV,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoNV daoNV =new DaoNV();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(maNV)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa nhân viên do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoNV.xoaNhanVien(maNV)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this,
                            "Đã xóa nhân viên thành công",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa nhân viên thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhân viên: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra có thể xóa (nếu cần)
    private boolean kiemTraCoTheXoa(String maNCC) {
        // Thêm logic kiểm tra nếu cần
        return true;
    }
}