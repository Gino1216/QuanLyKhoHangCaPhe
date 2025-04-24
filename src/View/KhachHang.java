package View;

import DTO.KhachHangDTO;
import DTO.NhaCungCapDTO;
import Dao.DaoKH;
import Dao.DaoNCC;
import Dao.DaoSP;
import EX.ExKhachHang;
import EX.ExNhanVien;
import Gui.InputDate;
import Gui.MainFunction;
import Repository.KhachHangRepo;
import Repository.NCCRepo;
import View.Dialog.ChiTietKhachHang;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import java.util.List;
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

        functionBar = new MainFunction("khachhang", new String[]{"create", "update", "delete", "detail","export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        functionBar.setButtonActionListener("create", this::showAddCustomerDialog);
        functionBar.setButtonActionListener("update", this::showEditCustomerDialog);
        functionBar.setButtonActionListener("delete", this::DeleteCustomer);
        functionBar.setButtonActionListener("detail", this::showCustomerDetails);
        functionBar.setButtonActionListener("import", () -> JOptionPane.showMessageDialog(this, "Chức năng nhập chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE));
        functionBar.setButtonActionListener("export", this::exportToExcel);
    }

    private void showAddCustomerDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Khách Hàng", true);
        dialog.setSize(900, 700);
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
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Ngày tham gia", "Email", "Số điện thoại"};
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

            // Xử lý placeholder cho các trường đặc biệt
            if (labels[i].equals("Số điện thoại")) {
                setupPlaceholder(textFields[i], "Nhập số điện thoại (10 chữ số)");
            } else if (labels[i].equals("Email")) {
                setupPlaceholder(textFields[i], "Nhập email (tùy chọn)");
            } else if (labels[i].equals("Ngày tham gia")) {
                setupPlaceholder(textFields[i], "dd/MM/yyyy");
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
        styleButton(btnAdd, new Color(59, 130, 246), new Dimension(180, 50), 16);

        JButton btnCancel = new JButton("Hủy bỏ");
        styleButton(btnCancel, new Color(239, 68, 68), new Dimension(180, 50), 16);

        btnAdd.addActionListener(e -> {
            // Lấy dữ liệu từ các trường nhập
            String maKH = textFields[0].getText().trim();
            String tenKH = textFields[1].getText().trim();
            String diaChi = textFields[2].getText().trim();
            String ngayThamGia = textFields[3].getText().trim();
            String email = textFields[4].getText().trim();
            String soDT = textFields[5].getText().trim();

            // Validate dữ liệu
            if (maKH.isEmpty() || tenKH.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate ngày tham gia
            if (!ngayThamGia.equals("dd/MM/yyyy")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    sdf.parse(ngayThamGia);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Ngày tham gia không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                ngayThamGia = ""; // Nếu không nhập thì để trống
            }

            // Validate số điện thoại
            if (!soDT.equals("Nhập số điện thoại (10 chữ số)")) {
                if (!soDT.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có đúng 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                soDT = ""; // Nếu không nhập thì để trống
            }

            // Validate email (nếu có nhập)
            if (!email.equals("Nhập email (tùy chọn)") && !email.isEmpty()) {
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                email = ""; // Nếu không nhập thì để trống
            }

            // Tạo đối tượng KhachHangDTO và thêm vào database
            try {
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(maKH);
                khachHangDTO.setHoTen(tenKH);
                khachHangDTO.setDiaChi(diaChi);
                khachHangDTO.setNgayThamGia(ngayThamGia.isEmpty() ? null : ngayThamGia);
                khachHangDTO.setEmail(email.isEmpty() ? null : email);
                khachHangDTO.setSoDT(soDT.isEmpty() ? null : soDT);

                // Gọi DAO để thêm vào database
                DaoKH khachHangDAO = new DaoKH();

                // Kiểm tra trùng mã khách hàng
                if (khachHangDAO.kiemTraMaKHTonTai(maKH)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Mã khách hàng đã tồn tại! Vui lòng nhập mã khác.",
                            "Trùng mã khách hàng",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                khachHangDAO.themKhachHang(khachHangDTO);

                // Thêm vào bảng hiển thị
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        khachHangDTO.getMaKH(),
                        khachHangDTO.getHoTen(),
                        khachHangDTO.getDiaChi(),
                        khachHangDTO.getNgayThamGia(),
                        khachHangDTO.getEmail(),
                        khachHangDTO.getSoDT()
                });

                JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm khách hàng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    private void exportToExcel() {
        ExKhachHang.exportKhachHangToExcel("E:/DanhSachKhachHang.xlsx");
    }
    // Helper method để thiết lập placeholder cho text field
    private void setupPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Helper method để style button
    private void styleButton(JButton button, Color bgColor, Dimension size, int fontSize) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        button.setPreferredSize(size);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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

        String[] labels = {"Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Ngày tham gia", "Email", "Số điện thoại"};
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
                textFields[i].setEditable(false);
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
                    case 0:
                        textFields[i].setText(id);
                        break;
                    case 1:
                        textFields[i].setText(name);
                        break;
                    case 2:
                        textFields[i].setText(address);
                        break;
                    case 3:
                        textFields[i].setText(joinDate);

                        break;
                    case 4:
                        textFields[i].setText(email);
                        break;
                    case 5:
                        textFields[i].setText(phone);
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
            String newId = textFields[0].getText();
            String newName = textFields[1].getText();
            String newAddress = textFields[2].getText();
            String newJoinDate = textFields[3].getText();
            String newEmail = textFields[4].getText();
            String newPhone = textFields[5].getText();


            String formattedHanSD;
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                inputFormat.setLenient(false);
                Date date = inputFormat.parse(newJoinDate);
                formattedHanSD = outputFormat.format(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

            if (newName.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty() || newJoinDate.isEmpty() || newId.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHangDTO kh =new KhachHangDTO();
            kh.setMaKH(newId);
            kh.setHoTen(newName);
            kh.setDiaChi(newAddress);
            kh.setNgayThamGia(newJoinDate);
            kh.setEmail(newEmail);
            kh.setSoDT(newPhone);

            try {
                DaoKH daoKH =new DaoKH();
                boolean success = daoKH.suaKhachHang(kh);

                if (success) {
                    // Cập nhật lên table nếu thành công
                    table.setValueAt(newId, modelRow, 0);
                    table.setValueAt(newName, modelRow, 1);
                    table.setValueAt(newAddress, modelRow, 2);
                    table.setValueAt(newJoinDate, modelRow, 3);
                    table.setValueAt(newEmail, modelRow, 4);
                    table.setValueAt(newPhone, modelRow, 5);

                    JOptionPane.showMessageDialog(dialog, "Cập nhật nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        dialog.setVisible(true);
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã Khách hàng","Tên khách hàng", "Địa chỉ","Ngày tham gia", "Email", "Số điện thoại"});

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
        String[] columns = {"Mã khách hàng", "Tên khách hàng","Địa chỉ", "Ngày tham gia","Email" ,"Số điện thoại"};

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
            KhachHangRepo repo=new DaoKH();
            List<KhachHangDTO> danhSach =repo.layDanhSachKhachHang();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (KhachHangDTO kh : danhSach) {
                model.addRow(new Object[]{
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getDiaChi(),
                        kh.getNgayThamGia(),
                        kh.getEmail(),
                        kh.getSoDT()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }


    private void filterData() {
        String searchText = txtSearch.getText().toLowerCase(); // Lấy dữ liệu tìm kiếm và chuyển thành chữ thường
        String selectedFilter = (String) cbbFilter.getSelectedItem(); // Lấy giá trị của filter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel()); // Khởi tạo TableRowSorter
        table.setRowSorter(sorter); // Gắn sorter vào table

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null); // Nếu không có từ khóa tìm kiếm, bỏ lọc
            return;
        }

        int[] columnIndices;
        if ("Tất cả".equals(selectedFilter)) {
            columnIndices = new int[]{0, 1, 2, 3, 4, 5};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Mã Khách hàng" -> 0;
                case "Tên khách hàng" -> 1;
                case "Địa chỉ" -> 2;
                case "Ngày tham gia" -> 3;
                case "Email" -> 4;
                case "Số điện thoại" -> 5;
                default -> 0;
            };
            columnIndices = new int[]{columnIndex}; // Lọc theo cột đã chọn
        }

        // Cài đặt bộ lọc với regex không phân biệt chữ hoa chữ thường
        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf); // Áp dụng bộ lọc
    }


    private void DeleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maKH = table.getValueAt(selectedRow, 0).toString();
        String tenKH = table.getValueAt(selectedRow, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng:\n" +
                        "Mã: " + maKH + "\n" +
                        "Tên: " + tenKH,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoKH daoKH =new DaoKH();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(maKH)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa khách hàng do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoKH.xoaKhachHang(maKH)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this,
                            "Đã xóa khách hàng thành công",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa khách hàng thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa khách hàng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra có thể xóa (nếu cần)
    private boolean kiemTraCoTheXoa(String maSP) {
        // Thêm logic kiểm tra nếu cần
        return true;
    }
}