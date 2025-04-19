package View.Dialog;

import DTO.SanPhamDTO;
import DTO.KhachHangDTO;
import Dao.DaoSP;
import Dao.DaoKH;
import View.PhieuXuat;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class CreaterPhieuXuat extends JFrame {

    private PhieuXuat parent;
    private JTextField txtSearch;
    private JTextField txtReceiptId;
    private JTextField txtPrice;
    private JTextField txtEmployee;
    private JTextField txtQuantity;
    private JComboBox<String> cbbMaSP;
    private JComboBox<String> cbbCustomer;
    private JList<String> coffeeList;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private Color backgroundColor = new Color(240, 247, 250);
    private long totalAmount = 0;
    private List<SanPhamDTO> sanPhamList;
    private List<KhachHangDTO> khachHangList;
    private DaoSP daoSP;
    private DaoKH daoKH;


    private String generateRandomReceiptId() {
        // Tạo mã gồm "PX-" + 6 số ngẫu nhiên
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Số từ 100000 đến 999999
        return "PX-" + randomNumber;
    }

    public CreaterPhieuXuat(PhieuXuat parent) {
        if (parent == null) {
            throw new IllegalArgumentException("PhieuXuat parent cannot be null");
        }
        this.parent = parent;
        FlatLightLaf.setup();

        // Khởi tạo DaoSP và lấy danh sách sản phẩm
        daoSP = new DaoSP();
        sanPhamList = daoSP.layDanhSachSanPham();

        // Khởi tạo DaoKH và lấy danh sách khách hàng
        daoKH = new DaoKH();
        khachHangList = daoKH.layDanhSachKhachHang();

        setTitle("Thêm Phiếu Xuất Cafe");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 8));

        // Top Panel (Search)
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Center Panel (Coffee List and Input Form)
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel (Table and Total)
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(backgroundColor);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        txtSearch = new JTextField("Tên cafe, mã cafe...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(0, 35));
        topPanel.add(txtSearch, BorderLayout.NORTH);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Left Panel (Coffee List)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(backgroundColor);
        leftPanel.setPreferredSize(new Dimension(300, 0));

        Vector<String> coffees = new Vector<>();
        for (SanPhamDTO sp : sanPhamList) {
            coffees.add(sp.getMaSP() + " - " + sp.getTenSP() + " - " + sp.getSoLuong() + "kg");
        }

        coffeeList = new JList<>(coffees);
        coffeeList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        coffeeList.setVisibleRowCount(10);
        JScrollPane coffeeScroll = new JScrollPane(coffeeList);
        coffeeScroll.setPreferredSize(new Dimension(300, 200));
        leftPanel.add(coffeeScroll, BorderLayout.CENTER);

        centerPanel.add(leftPanel, BorderLayout.WEST);


        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã phiếu xuất
        JLabel lblReceiptId = new JLabel("Mã phiếu xuất");
        lblReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(lblReceiptId, gbc);

        txtReceiptId = new JTextField(generateRandomReceiptId());
        txtReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtReceiptId.setPreferredSize(new Dimension(200, 35));
        txtReceiptId.setEditable(false);
        gbc.gridx = 1;
        rightPanel.add(txtReceiptId, gbc);

        // Mã sản phẩm
        JLabel lblMaSP = new JLabel("Mã sản phẩm");
        lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblMaSP, gbc);

        Vector<String> maSPs = new Vector<>();
        maSPs.add("Tất cả");  // Thêm lựa chọn "Tất cả" đầu tiên
        for (SanPhamDTO sp : sanPhamList) {
            maSPs.add(sp.getMaSP());
        }
        cbbMaSP = new JComboBox<>(maSPs);
        cbbMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbMaSP.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(cbbMaSP, gbc);

        // Nhân viên xuất
        JLabel lblEmployee = new JLabel("Nhân viên xuất");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblEmployee, gbc);

        txtEmployee = new JTextField("Hoàng Gia Bảo");
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmployee.setPreferredSize(new Dimension(200, 35));
        txtEmployee.setEditable(false);
        gbc.gridx = 5;
        rightPanel.add(txtEmployee, gbc);

        // Khách hàng
        JLabel lblCustomer = new JLabel("Khách hàng");
        lblCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(lblCustomer, gbc);

        Vector<String> tenKhachHangs = new Vector<>();
        for (KhachHangDTO kh : khachHangList) {
            tenKhachHangs.add(kh.getHoTen() + " (" + kh.getMaKH() + ")");
        }
        cbbCustomer = new JComboBox<>(tenKhachHangs);
        cbbCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbCustomer.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        rightPanel.add(cbbCustomer, gbc);

        // Giá bán
        JLabel lblPrice = new JLabel("Giá bán (VNĐ)");
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblPrice, gbc);

        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrice.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(txtPrice, gbc);

        // Số lượng
        JLabel lblQuantity = new JLabel("Số lượng");
        lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblQuantity, gbc);

        txtQuantity = new JTextField();
        txtQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtQuantity.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 5;
        rightPanel.add(txtQuantity, gbc);

        centerPanel.add(rightPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listener cho JComboBox để tự động điền thông tin
        cbbMaSP.addActionListener(e -> {
            String selectedMaSP = (String) cbbMaSP.getSelectedItem();
            if (selectedMaSP != null) {
                for (SanPhamDTO sp : sanPhamList) {
                    if (sp.getMaSP().equals(selectedMaSP)) {
                        txtPrice.setText(String.format("%.0f", sp.getGiaXuat()));
                        break;
                    }
                }
            }
        });

        // Listener cho JList để tự động điền thông tin
        coffeeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCoffee = coffeeList.getSelectedValue();
                if (selectedCoffee != null) {
                    String[] parts = selectedCoffee.split(" - ");
                    String maSP = parts[0];
                    cbbMaSP.setSelectedItem(maSP);
                }
            }
        });

        return centerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JButton btnAdd = new JButton("Thêm sản phẩm");
        btnAdd.setBackground(new Color(0, 102, 204));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setPreferredSize(new Dimension(150, 35));

        JButton btnImportExcel = new JButton("Nhập Excel");
        btnImportExcel.setBackground(new Color(34, 139, 34));
        btnImportExcel.setForeground(Color.WHITE);
        btnImportExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnImportExcel.setPreferredSize(new Dimension(150, 35));

        JButton btnEdit = new JButton("Sửa sản phẩm");
        btnEdit.setBackground(new Color(135, 206, 250));
        btnEdit.setForeground(Color.BLACK);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEdit.setPreferredSize(new Dimension(150, 35));

        JButton btnDelete = new JButton("Xóa sản phẩm");
        btnDelete.setBackground(new Color(211, 211, 211));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setPreferredSize(new Dimension(150, 35));

        btnAdd.addActionListener(e -> {
            String maSP = (String) cbbMaSP.getSelectedItem();
            String tenSP = "";
            float giaXuat = 0;
            int soLuongTon = 0;
            for (SanPhamDTO sp : sanPhamList) {
                if (sp.getMaSP().equals(maSP)) {
                    tenSP = sp.getTenSP();
                    giaXuat = sp.getGiaXuat();
                    soLuongTon = sp.getSoLuong();
                    break;
                }
            }
            String quantityStr = txtQuantity.getText();
            int selectedCustomerIndex = cbbCustomer.getSelectedIndex();

            // Kiểm tra dữ liệu
            if (maSP == null || maSP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedCustomerIndex == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
                if (quantity > soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho (" + soLuongTon + ")!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy MaKH từ danh sách khách hàng
            String maKH = khachHangList.get(selectedCustomerIndex).getMaKH();

            // Tính thành tiền
            float thanhTien = giaXuat * quantity;

            // Thêm vào bảng
            int stt = table.getRowCount() + 1;


            tableModel.addRow(new Object[]{stt, maSP, tenSP, giaXuat, quantity, thanhTien});

            // Cập nhật tổng tiền
            totalAmount += (long) thanhTien;
            updateTotalLabel();

            // Reset các trường
            txtQuantity.setText("");
            cbbMaSP.setSelectedIndex(0);
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Trừ tổng tiền của hàng cũ
            float oldThanhTien = Float.parseFloat(table.getValueAt(selectedRow, 5).toString());
            totalAmount -= (long) oldThanhTien;

            // Lấy thông tin mới
            String maSP = (String) cbbMaSP.getSelectedItem();
            String tenSP = "";
            float giaXuat = 0;
            int soLuongTon = 0;
            for (SanPhamDTO sp : sanPhamList) {
                if (sp.getMaSP().equals(maSP)) {
                    tenSP = sp.getTenSP();
                    giaXuat = sp.getGiaXuat();
                    soLuongTon = sp.getSoLuong();
                    break;
                }
            }
            String quantityStr = txtQuantity.getText();
            int selectedCustomerIndex = cbbCustomer.getSelectedIndex();

            // Kiểm tra dữ liệu
            if (maSP == null || maSP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedCustomerIndex == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
                if (quantity > soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho (" + soLuongTon + ")!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tính thành tiền
            float thanhTien = giaXuat * quantity;

            // Cập nhật hàng trong bảng
            table.setValueAt(maSP, selectedRow, 1);
            table.setValueAt(tenSP, selectedRow, 2);
            table.setValueAt(giaXuat, selectedRow, 3);
            table.setValueAt(quantity, selectedRow, 4);
            table.setValueAt(thanhTien, selectedRow, 5);

            // Cập nhật tổng tiền
            totalAmount += (long) thanhTien;
            updateTotalLabel();

            // Reset các trường
            txtQuantity.setText("");
            cbbMaSP.setSelectedIndex(0);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Trừ tổng tiền của hàng bị xóa
            float thanhTien = Float.parseFloat(table.getValueAt(selectedRow, 5).toString());
            totalAmount -= (long) thanhTien;

            // Xóa hàng
            tableModel.removeRow(selectedRow);

            // Cập nhật STT
            for (int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt(i + 1, i, 0);
            }

            // Cập nhật tổng tiền
            updateTotalLabel();
        });

        btnImportExcel.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng nhập Excel chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnImportExcel);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        return buttonPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Thêm cột "Thành tiền"
        String[] columns = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Đơn giá (VNĐ)", "Số lượng", "Thành tiền"};
        tableModel = new DefaultTableModel(new Object[][]{}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setGridColor(new Color(200, 200, 200));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    component.setBackground(row % 2 == 0 ? new Color(248, 249, 250) : Color.WHITE);
                }
                return component;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(backgroundColor);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        lblTotal = new JLabel("TỔNG TIỀN: 0đ");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(Color.RED);
        totalPanel.add(lblTotal, BorderLayout.WEST);

        JButton btnSubmit = new JButton("Xuất hàng");
        btnSubmit.setBackground(new Color(34, 139, 34));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setPreferredSize(new Dimension(150, 35));
        btnSubmit.addActionListener(e -> {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một sản phẩm để xuất hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String receiptId = txtReceiptId.getText();
            int selectedCustomerIndex = cbbCustomer.getSelectedIndex();
            String employee = txtEmployee.getText();

            if (selectedCustomerIndex == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy MaKH từ danh sách khách hàng
            String customerId = khachHangList.get(selectedCustomerIndex).getMaKH();
            String employeeId = "NV" + System.currentTimeMillis();

            parent.addPhieuXuat(receiptId, customerId, employeeId, totalAmount);

            JOptionPane.showMessageDialog(this, "Xuất hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        totalPanel.add(btnSubmit, BorderLayout.EAST);

        bottomPanel.add(totalPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private void updateTotalLabel() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        lblTotal.setText("TỔNG TIỀN: " + df.format(totalAmount));
    }
}