package View;

import DTO.NhanVienDTO;
import DTO.PhanQuyenDTO;
import DTO.SanPhamDTO;
import Dao.DaoNV;
import Dao.DaoPQ;
import Dao.DaoSP;
import Gui.MainFunction;
import Repository.PhanQuyenRepo;
import Repository.SanPhamRepo;
import View.Dialog.ChiTietPhanQuyen;
import View.Dialog.ChiTietSanPham;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PhanQuyen extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);



    // Map để lưu trữ danh sách quyền cho từng nhóm quyền
    private Map<String, Object[][]> permissionDetailsMap;

    public PhanQuyen() {
        // Khởi tạo Map và dữ liệu giả lập


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
        functionBar = new MainFunction("phanquyen", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddPermissionDialog);
        functionBar.setButtonActionListener("update", this::showEditPermissionDialog);
        functionBar.setButtonActionListener("detail", this::showPermissionDetailDialog);
        functionBar.setButtonActionListener("delete", this::deletePermission);
        functionBar.setButtonActionListener("import", this::importPermissions);
        functionBar.setButtonActionListener("export", this::exportPermissions);

        return topPanel;
    }

    private void showPermissionDetailDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phân quyền để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int maQuyen = (int) table.getValueAt(modelRow, 0); // Lấy mã quyền kiểu int
        String tenQuyen = table.getValueAt(modelRow, 1).toString(); // Tên quyền



        ChiTietPhanQuyen detailDialog= new ChiTietPhanQuyen(maQuyen,tenQuyen);
        detailDialog.setVisible(true);
    }




    private void deletePermission() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn phân quyền cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int maQuyen = (int) table.getValueAt(modelRow, 0); // Lấy mã quyền kiểu int
        String tenQuyen = table.getValueAt(modelRow, 1).toString(); // Tên quyền


        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phân quyền:\n" +
                        "Mã: " + maQuyen + "\n" +
                        "Tên: " + tenQuyen,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoPQ daoPQ =new DaoPQ();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(maQuyen)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa phân quyền do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoPQ.xoaQuyen(maQuyen)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this,
                            "Đã xóa quyền thành công",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa quyền thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa phân quyền: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra có thể xóa (nếu cần)
    private boolean kiemTraCoTheXoa(int maQuyen) {
        // Thêm logic kiểm tra nếu cần
        return true;
    }






    private void importPermissions() {
        JOptionPane.showMessageDialog(this, "Chức năng nhập nhóm quyền chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportPermissions() {
        JOptionPane.showMessageDialog(this, "Chức năng xuất nhóm quyền chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showAddPermissionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Phân Quyền", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM PHÂN QUYỀN", JLabel.CENTER);
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
        String[] labels = {"Mã phân quyền", "Tên quyền"};
        JTextField[] textFields = new JTextField[labels.length];

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
            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textFields[i].setPreferredSize(new Dimension(200, 40));

            // Kiểm tra Mã phân quyền chỉ cho phép số
            if (labels[i].equals("Mã phân quyền")) {
                textFields[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                            e.consume(); // Ngăn ký tự không phải số
                            JOptionPane.showMessageDialog(dialog, "Mã phân quyền phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        JButton btnAdd = new JButton("Thêm Phân Quyền");
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
            String maQuyenStr = textFields[0].getText().trim();
            String tenQuyen = textFields[1].getText().trim();

            // Kiểm tra các trường bắt buộc
            if (maQuyenStr.isEmpty() || tenQuyen.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra và chuyển đổi Mã phân quyền thành int
            int maQuyen;
            try {
                maQuyen = Integer.parseInt(maQuyenStr);
                if (maQuyen <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Mã phân quyền phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng PhanQuyenDTO
            PhanQuyenDTO pq = new PhanQuyenDTO();
            pq.setMaQuyen(maQuyen);
            pq.setNoiDung(tenQuyen);

            // Thêm vào cơ sở dữ liệu
            try {
                DaoPQ daoPQ = new DaoPQ();
                if (daoPQ.kiemTraMaQuyenTonTai(maQuyen)) {
                    JOptionPane.showMessageDialog(dialog,
                            "Mã phân quyền đã tồn tại! Vui lòng nhập mã khác.",
                            "Trùng mã phân quyền",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                daoPQ.themQuyen(pq);

                // Cập nhật bảng hiển thị
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        pq.getMaQuyen(),
                        pq.getNoiDung()
                });

                JOptionPane.showMessageDialog(dialog, "Thêm phân quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm phân quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Phím tắt
        dialog.getRootPane().setDefaultButton(btnAdd); // Enter để thêm
        dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW); // Esc để hủy

        dialog.setVisible(true);
    }

    private void showEditPermissionDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phân quyền để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int maQuyen = (int) table.getValueAt(modelRow, 0); // Lấy mã quyền kiểu int
        String tenQuyen = table.getValueAt(modelRow, 1).toString(); // Tên quyền

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Phân Quyền", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Chỉnh Sửa Phân Quyền", JLabel.CENTER);
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
        String[] labels = {"Mã phân quyền", "Tên quyền"};
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

            if (labels[i].equals("Mã phân quyền")) {
                textFields[i].setText(String.valueOf(maQuyen));
                textFields[i].setEditable(false); // Không cho phép chỉnh sửa
                textFields[i].setBackground(new Color(240, 240, 240)); // Nền nhạt để biểu thị chỉ đọc
            } else {
                textFields[i].setText(tenQuyen);
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

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy dữ liệu từ form
                String tenQuyen = textFields[1].getText().trim();

                // Validate dữ liệu
                if (tenQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền tên quyền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng phân quyền
                PhanQuyenDTO phanQuyenDTO = new PhanQuyenDTO();
                phanQuyenDTO.setMaQuyen(maQuyen); // Sử dụng maQuyen kiểu int
                phanQuyenDTO.setNoiDung(tenQuyen);

                // Thực hiện cập nhật vào database
                try {
                    DaoPQ daoPQ = new DaoPQ();
                    boolean success = daoPQ.suaQuyen(phanQuyenDTO);

                    if (success) {
                        // Cập nhật lên table nếu thành công
                        table.setValueAt(maQuyen, modelRow, 0); // Giữ nguyên kiểu int
                        table.setValueAt(tenQuyen, modelRow, 1);

                        JOptionPane.showMessageDialog(dialog, "Cập nhật phân quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật phân quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên Quyền"});
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
        String[] columns = {"Mã phân quyền", "Tên quyền"};

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
            PhanQuyenRepo repo =new DaoPQ();
            List<PhanQuyenDTO> danhSach = repo.layDanhSachQuyen();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (PhanQuyenDTO pq : danhSach) {
                model.addRow(new Object[]{
                        pq.getMaQuyen(),
                        pq.getNoiDung()
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
            columnIndices = new int[]{1}; // Chỉ lọc cột Tên Quyền
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên Quyền" ->
                        1;
                default ->
                        1;
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