package View;

import DTO.NhaCungCapDTO;

import Dao.DaoNCC;
import EX.ExNhaCungCap;
import Gui.MainFunction;
import Repository.NCCRepo;
import View.Dialog.ChiTietNhaCungCap;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class NhaCungCap extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);

    public NhaCungCap() {
        // Set up FlatLaf theme
        FlatLightLaf.setup();

        // Configure JPanel (you don't need setSize, setLocation, etc. for JPanel)
        setLayout(new BorderLayout(0, 8));

        // Create top panel to hold both toolbar and search panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("ncc", new String[]{"create", "update", "delete", "detail","export"});
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
        functionBar.setButtonActionListener("delete",this::DeleteNhaCungCap);
        functionBar.setButtonActionListener("detail",this::showSupplierDetails);
        functionBar.setButtonActionListener("export",this::exportToExcel);


    }

    private void showAddSupplierDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Nhà Cung Cấp", true);
        dialog.setSize(900, 700); // Tăng kích thước dialog để chứa thêm các trường
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM NHÀ CUNG CẤP", JLabel.CENTER);
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
        String[] labels = {"Mã Nhà Cung Cấp","Tên nhà cung cấp","Số điện thoại","Email","Địa chỉ","Tình trạng" };
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];


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
            }else if (labels[i].equals("Tình trạng")) {
                comboBoxes[i] = new JComboBox<>(new String[]{ "Hoạt động","Tạm ngưng"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tăng font chữ
                comboBoxes[i].setPreferredSize(new Dimension(200, 40)); // Kích thước combobox
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            }  else if (labels[i].equals("Email")) {
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

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm nhà cung cấp");
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
            String ma = textFields[0].getText();
            String name = textFields[1].getText();
            String phone = textFields[2].getText();
            String email = textFields[3].getText();
            String address = textFields[4].getText();
            String status = (String) comboBoxes[5].getSelectedItem();


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
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || ma.isEmpty() || status.isEmpty() ) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            NhaCungCapDTO ncc =new NhaCungCapDTO();
            ncc.setMaNCC(ma);
            ncc.setTenNCC(name);
            ncc.setSoDT(phone);
            ncc.setEmail(email);
            ncc.setDiaChi(address);
            ncc.setTinhTrang(status);

            try {
                DaoNCC daoNCC = new DaoNCC(); // Đổi thành DAO nhà cung cấp
                if (daoNCC.kiemTraMaNCCTonTai(ma)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Mã nhà cung cấp đã tồn tại! Vui lòng nhập mã khác.",
                            "Trùng mã nhà cung cấp",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (daoNCC.kiemTraMaNCCTonTai(ma)) {  // Vẫn dùng hàm kiểm tra trùng mã
                    JOptionPane.showMessageDialog(dialog,
                            "Mã nhà cung cấp đã tồn tại! Vui lòng nhập mã khác.",
                            "Trùng mã nhà cung cấp",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                daoNCC.themNhaCungCap(ncc); // Thêm vào database

                // Thêm vào bảng hiển thị
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        ncc.getMaNCC(),
                        ncc.getTenNCC(),
                        ncc.getSoDT(),
                        ncc.getDiaChi(),
                        ncc.getEmail(),
                        ncc.getTinhTrang()
                });

                JOptionPane.showMessageDialog(dialog, "Thêm nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // Đóng dialog sau khi thêm

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm nhà cung cấp: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }


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
        String maNCC = table.getValueAt(modelRow, 0).toString(); // Mã NCC
        String tenNCC = table.getValueAt(modelRow, 1).toString(); // Tên NCC
        String soDT = table.getValueAt(modelRow, 2).toString(); // Số điện thoại
        String diaChi = table.getValueAt(modelRow, 3).toString(); // Địa chỉ
        String email = table.getValueAt(modelRow, 4).toString(); // Email
        String tinhTrang = table.getValueAt(modelRow, 5).toString(); // Tình trạng

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Nhà Cung Cấp", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA NHÀ CUNG CẤP", JLabel.CENTER);
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

        // Các nhãn và trường nhập liệu
        String[] labels = {
                 "Mã nhà cung cấp","Tên nhà cung cấp", "Số điện thoại", "Địa chỉ", "Email", "Tình trạng"
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
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu hoặc combobox
            if (labels[i].equals("Tình trạng")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"Hoạt động", "Tạm ngưng"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[i].setPreferredSize(new Dimension(200, 40));
                comboBoxes[i].setSelectedItem(tinhTrang); // Gán giá trị Tình trạng từ bảng
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));
                if (labels[i].equals("Mã nhà cung cấp")) {
                    textFields[i].setEditable(false); // Không cho phép chỉnh sửa
                    textFields[i].setBackground(new Color(240, 240, 240)); // Nền nhạt để biểu thị chỉ đọc
                }

                // Gán giá trị từ bảng
                switch (i) {
                    case 0:
                        textFields[i].setText(maNCC);
                        break;
                    case 1:
                        textFields[i].setText(tenNCC);
                        break;
                    case 2:
                        textFields[i].setText(soDT);
                        break;
                    case 3:
                        textFields[i].setText(email);
                        break;
                    case 4:
                        textFields[i].setText(diaChi);
                        break;
                    case 5:
                        textFields[i].setText(tinhTrang);
                        break;
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

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy dữ liệu từ form
                String maNCC = textFields[0].getText().trim();
                String tenNCC = textFields[1].getText().trim();
                String soDT = textFields[2].getText().trim();
                String email = textFields[3].getText().trim();
                String diaChi = textFields[4].getText().trim();
                String tinhTrang = (String) comboBoxes[5].getSelectedItem();

                // Validate dữ liệu
                if (maNCC.isEmpty() || tenNCC.isEmpty() || soDT.isEmpty() || diaChi.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra định dạng email (tùy chọn)
//                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
//                if (!email.matches(emailRegex)) {
//                    System.out.println("Email không hợp lệ: " + email); // Debug giá trị email
//                    JOptionPane.showMessageDialog(dialog, "Email không hợp lệ! Vui lòng nhập email đúng định dạng (ví dụ: user@domain.com).", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }

                // Kiểm tra định dạng số điện thoại (tùy chọn)
                if (!soDT.matches("\\d{10,11}")) {
                    JOptionPane.showMessageDialog(dialog, "Số điện thoại phải có 10-11 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng nhà cung cấp
                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setMaNCC(maNCC);
                ncc.setTenNCC(tenNCC);
                ncc.setSoDT(soDT);
                ncc.setDiaChi(diaChi);
                ncc.setEmail(email);
                ncc.setTinhTrang(tinhTrang);

                // Thực hiện cập nhật vào database
                try {
                    DaoNCC daoNCC = new DaoNCC();
                    boolean success = daoNCC.suaNhaCungCap(ncc);

                    if (success) {
                        // Cập nhật lên table nếu thành công
                        table.setValueAt(maNCC, modelRow, 0);
                        table.setValueAt(tenNCC, modelRow, 1);
                        table.setValueAt(soDT, modelRow, 2);
                        table.setValueAt(email, modelRow, 3);
                        table.setValueAt(diaChi, modelRow, 4);
                        table.setValueAt(tinhTrang, modelRow, 5);

                        JOptionPane.showMessageDialog(dialog, "Cập nhật nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả","Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ","Tình trạng"});
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
        String[] columns = {"Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ","Tình trạng"};

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
            NCCRepo repo = new DaoNCC();
            List<NhaCungCapDTO> danhSach =repo.layDanhSachNhaCungCap();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (NhaCungCapDTO ncc : danhSach) {
                model.addRow(new Object[]{
                        ncc.getMaNCC(),
                        ncc.getTenNCC(),
                        ncc.getSoDT(),
                        ncc.getEmail(),
                        ncc.getDiaChi(),
                        ncc.getTinhTrang()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
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
        String phone = table.getValueAt(modelRow, 2).toString();
        String email = table.getValueAt(modelRow, 3).toString();
        String address = table.getValueAt(modelRow, 4).toString();
        String status = table.getValueAt(modelRow, 5).toString();


            ChiTietNhaCungCap detailDialog = new ChiTietNhaCungCap(id, name, phone,email,address,status);
        detailDialog.setVisible(true);
    }


    private void exportToExcel() {
                ExNhaCungCap.exportNhaCungCapToExcel( "E:/nhacungcap.xlsx");
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
                case "Mã NCC" -> 0;
                case "Tên NCC" -> 1;
                case "Số điện thoại" -> 2;
                case "Email" -> 3;
                case "Địa chỉ" -> 4;
                case "Tình trạng" -> 5;
                default -> 0;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }





    private void DeleteNhaCungCap() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhà cung cấp cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNCC = table.getValueAt(selectedRow, 0).toString();
        String tenNCC = table.getValueAt(selectedRow, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhà cung cấp:\n" +
                        "Mã: " + maNCC + "\n" +
                        "Tên: " + tenNCC,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoNCC daoNCC = new DaoNCC();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(maNCC)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa nhà cung cấp do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoNCC.xoaNhaCungCap(maNCC)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this,
                            "Đã xóa nhà cung cấp thành công",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa nhà cung cấp thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhà cung cấp: " + e.getMessage(),
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
