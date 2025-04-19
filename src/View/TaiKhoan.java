package View;

import DTO.Account;
import DTO.NhanVienDTO;
import DTO.PhanQuyenDTO;
import DTO.SanPhamDTO;
import Dao.DaoAccount;
import Dao.DaoNV;
import Dao.DaoPQ;
import Dao.DaoSP;
import Gui.MainFunction;
import Repository.AccountRepo;
import Repository.SanPhamRepo;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        functionBar.setButtonActionListener("delete", this::deleteAccount);


        return topPanel;
    }

    public void showAddAccountDialog() {
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
        String[] labels = {"Mã Nhân Viên", "Mã Quyền", "Tên Đăng Nhập", "Mật Khẩu"};
        JTextField txtUsername = null;
        JPasswordField txtPassword = null;
        JComboBox<PhanQuyenDTO> cbbRole = null;
        JComboBox<String> cbbMaNV = null;

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
            if (labels[i].equals("Tên Đăng Nhập")) {
                txtUsername = new JTextField(15);
                txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtUsername.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtUsername, gbc);
            } else if (labels[i].equals("Mật Khẩu")) {
                txtPassword = new JPasswordField(15);
                txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtPassword.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtPassword, gbc);
            } else if (labels[i].equals("Mã Quyền")) {
                cbbRole = new JComboBox<>();
                cbbRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbRole.setPreferredSize(new Dimension(200, 40));

                try {
                    DaoPQ daoPQ = new DaoPQ();
                    List<PhanQuyenDTO> dsQuyen = daoPQ.layDanhSachQuyen();

                    DefaultComboBoxModel<PhanQuyenDTO> model = new DefaultComboBoxModel<>();
                    for (PhanQuyenDTO quyen : dsQuyen) {
                        model.addElement(quyen);
                    }
                    cbbRole.setModel(model);

                    cbbRole.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                      boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof PhanQuyenDTO) {
                                PhanQuyenDTO quyen = (PhanQuyenDTO) value;
                                setText(quyen.getNoiDung());
                            }
                            return this;
                        }
                    });
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi tải danh sách quyền: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbRole, gbc);
            } else if (labels[i].equals("Mã Nhân Viên")) {
                cbbMaNV = new JComboBox<>();
                cbbMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbMaNV.setPreferredSize(new Dimension(200, 40));
                try {
                    DaoNV daoNV = new DaoNV();
                    List<String> maNVList = daoNV.getMaNVWithoutAccount();
                    if (maNVList.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Tất cả nhân viên đã có tài khoản!",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        return;
                    }
                    for (String maNV : maNVList) {
                        cbbMaNV.addItem(maNV);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi tải danh sách mã nhân viên: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    dialog.dispose();
                    return;
                }
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbMaNV, gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        // Để sử dụng trong ActionListener
        final JTextField finalTxtUsername = txtUsername;
        final JPasswordField finalTxtPassword = txtPassword;
        final JComboBox<PhanQuyenDTO> finalCbbRole = cbbRole;
        final JComboBox<String> finalCbbMaNV = cbbMaNV;

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm Tài Khoản");
        btnAdd.setBackground(new Color(59, 130, 246));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setPreferredSize(new Dimension(180, 50));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("Hủy Bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnAdd.addActionListener(e -> {
            String maNV = (String) finalCbbMaNV.getSelectedItem();
            String username = finalTxtUsername.getText().trim();
            String password = new String(finalTxtPassword.getPassword()).trim();
            PhanQuyenDTO selectedQuyen = (PhanQuyenDTO) finalCbbRole.getSelectedItem();

            // Kiểm tra các trường bắt buộc
            if (maNV == null || username.isEmpty() || password.isEmpty() || selectedQuyen == null) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng tên đăng nhập
            if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
                JOptionPane.showMessageDialog(dialog,
                        "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu phải có ít nhất 6 ký tự!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra tên đăng nhập đã tồn tại
            DaoAccount daoAccount = new DaoAccount();
            try {
                if (daoAccount.kiemTraUsernameTonTai(username)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Tên đăng nhập đã tồn tại! Vui lòng chọn tên khác.",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Lỗi khi kiểm tra tên đăng nhập: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            // Lấy thông tin quyền từ đối tượng đã chọn
            int roleValue = selectedQuyen.getMaQuyen();
            String roleStr = selectedQuyen.getNoiDung();

            // Tạo đối tượng Account
            Account account = new Account(maNV, roleValue, username, password);

            // Lưu vào cơ sở dữ liệu
            try {
                daoAccount.themAccount(account);
            } catch (Exception ex) {
                // Thông báo lỗi đã được xử lý trong DaoAccount
                return;
            }

            // Cập nhật bảng hiển thị
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{
                    maNV,
                    roleStr,
                    username,
                    "******"
            });
            loadData();

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
        String username = table.getValueAt(modelRow, 1).toString();
        String role = table.getValueAt(modelRow, 2).toString();

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
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Mã quyền", "Tên đăng nhập", "Mật khẩu"};
        JComboBox<PhanQuyenDTO> cbbRole = null;
        JTextField txtUsername = null;
        JPasswordField txtPassword = null;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(label, gbc);

            // Trường nhập liệu
            if (labels[i].equals("Mã quyền")) {
                cbbRole = new JComboBox<>();
                cbbRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbRole.setPreferredSize(new Dimension(200, 40));

                try {
                    DaoPQ daoPQ = new DaoPQ();
                    List<PhanQuyenDTO> dsQuyen = daoPQ.layDanhSachQuyen();

                    DefaultComboBoxModel<PhanQuyenDTO> model = new DefaultComboBoxModel<>();
                    for (PhanQuyenDTO quyen : dsQuyen) {
                        model.addElement(quyen);
                    }
                    cbbRole.setModel(model);

                    // Chọn quyền hiện tại của tài khoản
                    for (int j = 0; j < cbbRole.getItemCount(); j++) {
                        if (cbbRole.getItemAt(j).getNoiDung().equals(role)) {
                            cbbRole.setSelectedIndex(j);
                            break;
                        }
                    }

                    cbbRole.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                      boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof PhanQuyenDTO) {
                                PhanQuyenDTO quyen = (PhanQuyenDTO) value;
                                setText(quyen.getNoiDung());
                            }
                            return this;
                        }
                    });
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi tải danh sách quyền: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                gbc.gridx = 1;
                gbc.gridy = i;
                formPanel.add(cbbRole, gbc);
            } else if (labels[i].equals("Tên đăng nhập")) {
                txtUsername = new JTextField(15);
                txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtUsername.setPreferredSize(new Dimension(200, 40));
                txtUsername.setText(username);
                txtUsername.setEditable(false); // Không cho chỉnh sửa username
                gbc.gridx = 1;
                gbc.gridy = i;
                formPanel.add(txtUsername, gbc);
            } else if (labels[i].equals("Mật khẩu")) {
                txtPassword = new JPasswordField(15);
                txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtPassword.setPreferredSize(new Dimension(200, 40));
                txtPassword.setText(""); // Để trống, người dùng nhập nếu muốn đổi
                gbc.gridx = 1;
                gbc.gridy = i;
                formPanel.add(txtPassword, gbc);
            }
        }

        // Để sử dụng trong ActionListener
        final JComboBox<PhanQuyenDTO> finalCbbRole = cbbRole;
        final JTextField finalTxtUsername = txtUsername;
        final JPasswordField finalTxtPassword = txtPassword;

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
            String newUsername = finalTxtUsername.getText().trim();
            String newPassword = new String(finalTxtPassword.getPassword()).trim();
            PhanQuyenDTO selectedQuyen = (PhanQuyenDTO) finalCbbRole.getSelectedItem();

            // Kiểm tra các trường bắt buộc
            if (newUsername.isEmpty() || selectedQuyen == null) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng tên đăng nhập
            if (!newUsername.matches("^[a-zA-Z0-9_]{3,20}$")) {
                JOptionPane.showMessageDialog(dialog,
                        "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ cái, số và dấu gạch dưới!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra mật khẩu (nếu nhập mới)
            if (!newPassword.isEmpty() && newPassword.length() < 6) {
                JOptionPane.showMessageDialog(dialog,
                        "Mật khẩu phải có ít nhất 6 ký tự!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy thông tin quyền
            int roleValue = selectedQuyen.getMaQuyen();
            String roleStr = selectedQuyen.getNoiDung();

            // Tạo đối tượng Account
            Account account = new Account(newUsername, newPassword.isEmpty() ? null : newPassword, roleValue);

            // Lưu vào cơ sở dữ liệu
            DaoAccount daoAccount = new DaoAccount();
            try {
                boolean updated = daoAccount.suaAccount(account);
                if (updated) {
                    // Cập nhật bảng hiển thị
                    table.setValueAt(newUsername, modelRow, 1);
                    table.setValueAt(roleStr, modelRow, 2);

                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Không thể cập nhật tài khoản!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Lỗi khi cập nhật tài khoản: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            loadData();
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên đăng nhập", "Mã quyền"});
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
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Columns
        String[] columns = {"Mã quyền", "Tài khoản", "Mật khẩu"};

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
            AccountRepo repo = new DaoAccount();
            List<Account> danhSach = repo.layDanhSachAccount();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (Account acc : danhSach) {
                String tenVaiTro;
                int role = acc.getRole();

                if (role == 1) {
                    tenVaiTro = "Nhân viên";
                } else if (role == 2) {
                    tenVaiTro = "Kế toán";
                } else {
                    tenVaiTro = "Không xác định";
                }

                model.addRow(new Object[]{
                        tenVaiTro,
                        acc.getUsername(),
                        acc.getPassword()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }


    private void customizeTableAppearance() {
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
                case "Tên đăng nhập" -> 1;
                case "Mã quyền" -> 2;
                default -> 1;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        // Khởi tạo tableModel nếu chưa có
        if (tableModel == null) {
            tableModel = new DefaultTableModel(
                    new String[]{"Mã quyền", "Tên đăng nhập", "Mật khẩu"}, 0);
            table.setModel(tableModel);
        } else {
            // Đảm bảo có cột nếu tableModel đã tồn tại mà bị xóa cột trước đó
            if (tableModel.getColumnCount() == 0) {
                tableModel.setColumnIdentifiers(new String[]{"Quyền", "Tên đăng nhập", "Mật khẩu"});
            }
        }

        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        try {
            AccountRepo repo = new DaoAccount();
            List<Account> danhSach = repo.layDanhSachAccount();

            // Lấy danh sách quyền để ánh xạ mã số thành tên
            DaoPQ daoPQ = new DaoPQ();
            List<PhanQuyenDTO> dsQuyen = daoPQ.layDanhSachQuyen();
            Map<Integer, String> mapQuyen = new HashMap<>();
            for (PhanQuyenDTO quyen : dsQuyen) {
                mapQuyen.put(quyen.getMaQuyen(), quyen.getNoiDung());
            }

            // Thêm dữ liệu vào table
            for (Account acc : danhSach) {
                String tenQuyen = mapQuyen.getOrDefault(acc.getRole(), "Không xác định");
                tableModel.addRow(new Object[]{
                        tenQuyen,  // Hiển thị tên quyền thay vì mã số
                        acc.getUsername(),
                        acc.getPassword()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteAccount() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn tài khoản cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String taikhoan = table.getValueAt(selectedRow, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa tài khoản:\n" +
                        "Tài khoản: " + taikhoan,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoAccount daoAccount =new DaoAccount();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(taikhoan)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa tài khoản do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoAccount.xoaAccount(taikhoan)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa tài khoản thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa tài khoản: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra có thể xóa (nếu cần)
    private boolean kiemTraCoTheXoa(String username) {
        // Thêm logic kiểm tra nếu cần
        return true;
    }
}