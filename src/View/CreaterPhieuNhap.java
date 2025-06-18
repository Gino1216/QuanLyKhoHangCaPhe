package View;

import Config.Session;
import DTO.*;
import Dao.DaoPhieuNhap;
import Dao.DaoSP;
import Dao.DaoNCC;
import Dao.DaoChiTietPhieuNhap;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

public class CreaterPhieuNhap extends JFrame {

    private PhieuNhap parent;
    private JComboBox<String> cbbReceiptId;
    private JTextField txtPrice;
    private JTextField txtEmployee;
    private JTextField txtQuantity;
    private JComboBox<String> cbbMaSP;
    private JComboBox<String> cbbSupplier;
    private JList<String> productList;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private Color backgroundColor = new Color(240, 247, 250);
    private long totalAmount = 0;
    private List<SanPhamDTO> sanPhamList;
    private List<NhaCungCapDTO> nhaCungCapList;
    private List<String> maPNList;
    private DaoSP daoSP;
    private DaoNCC daoNCC;
    private DaoPhieuNhap daoPhieuNhap;
    private DaoChiTietPhieuNhap daoChiTietPhieuNhap;

    public CreaterPhieuNhap(PhieuNhap parent) {
        if (parent == null) {
            throw new IllegalArgumentException("PhieuNhap parent cannot be null");
        }
        this.parent = parent;
        FlatLightLaf.setup();

        // Khởi tạo các DAO
        daoSP = new DaoSP();
        sanPhamList = daoSP.layDanhSachSanPham();
        daoNCC = new DaoNCC();
        nhaCungCapList = daoNCC.layDanhSachNhaCungCap();
        daoPhieuNhap = new DaoPhieuNhap();
        daoChiTietPhieuNhap = new DaoChiTietPhieuNhap();
        maPNList = daoPhieuNhap.layDanhSachPhieuNhapTongTienBang0();



        setTitle("Thêm Phiếu Nhập Hàng");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 8));

        // Khởi tạo cbbReceiptId
        cbbReceiptId = new JComboBox<>(new Vector<>(maPNList));
        cbbReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbReceiptId.setPreferredSize(new Dimension(200, 35));

        // Center Panel (Product List and Input Form)
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel (Table and Total)
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(backgroundColor);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Left Panel (Product List)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(backgroundColor);
        leftPanel.setPreferredSize(new Dimension(300, 0));

        Vector<String> products = new Vector<>();
        for (SanPhamDTO sp : sanPhamList) {
            products.add(sp.getMaSP() + " - " + sp.getTenSP() + " - " + sp.getSoLuong() + " bao");
        }

        productList = new JList<>(products);
        productList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productList.setVisibleRowCount(10);
        JScrollPane productScroll = new JScrollPane(productList);
        productScroll.setPreferredSize(new Dimension(300, 200));
        leftPanel.add(productScroll, BorderLayout.CENTER);

        centerPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã phiếu nhập
        JLabel lblReceiptId = new JLabel("Mã phiếu nhập");
        lblReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(lblReceiptId, gbc);

        gbc.gridx = 1;
        rightPanel.add(cbbReceiptId, gbc);

        // Mã sản phẩm
        JLabel lblMaSP = new JLabel("Mã sản phẩm");
        lblMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblMaSP, gbc);

        Vector<String> maSPs = new Vector<>();
        maSPs.add("Tất cả");
        for (SanPhamDTO sp : sanPhamList) {
            maSPs.add(sp.getMaSP());
        }
        cbbMaSP = new JComboBox<>(maSPs);
        cbbMaSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbMaSP.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(cbbMaSP, gbc);

        // Nhân viên nhập
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblEmployee, gbc);

        txtEmployee = new JTextField(Session.getUsername());
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmployee.setPreferredSize(new Dimension(200, 35));
        txtEmployee.setEditable(false);
        gbc.gridx = 5;
        rightPanel.add(txtEmployee, gbc);

        // Nhà cung cấp
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        lblSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(lblSupplier, gbc);

        Vector<String> tenNhaCungCaps = new Vector<>();
        for (NhaCungCapDTO ncc : nhaCungCapList) {
            tenNhaCungCaps.add(ncc.getTenNCC() + " (" + ncc.getMaNCC() + ")");
        }
        cbbSupplier = new JComboBox<>(tenNhaCungCaps);
        cbbSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbSupplier.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        rightPanel.add(cbbSupplier, gbc);

        // Giá nhập
        JLabel lblPrice = new JLabel("Giá nhập (VNĐ)");
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblPrice, gbc);

        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrice.setPreferredSize(new Dimension(200, 35));
        txtPrice.setEditable(false);
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
            if (selectedMaSP != null && !selectedMaSP.equals("Tất cả")) {
                for (SanPhamDTO sp : sanPhamList) {
                    if (sp.getMaSP().equals(selectedMaSP)) {
                        txtPrice.setText(String.format("%.0f", sp.getGiaNhap()));
                        break;
                    }
                }
            } else {
                txtPrice.setText("");
            }
        });

        // Listener cho JList để tự động điền thông tin
        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedProduct = productList.getSelectedValue();
                if (selectedProduct != null) {
                    String[] parts = selectedProduct.split(" - ");
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

        JButton btnDelete = new JButton("Xóa sản phẩm");
        btnDelete.setBackground(new Color(135, 206, 250));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setPreferredSize(new Dimension(150, 35));

        btnAdd.addActionListener(e -> {
            String maPN = (String) cbbReceiptId.getSelectedItem();
            String maSP = (String) cbbMaSP.getSelectedItem();
            String tenSP = "";
            float giaNhap = 0;
            for (SanPhamDTO sp : sanPhamList) {
                if (sp.getMaSP().equals(maSP)) {
                    tenSP = sp.getTenSP();
                    break;
                }
            }
            String priceStr = txtPrice.getText();
            String quantityStr = txtQuantity.getText();
            int selectedSupplierIndex = cbbSupplier.getSelectedIndex();

            // Kiểm tra dữ liệu
            if (maPN == null || maPN.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (maSP == null || maSP.isEmpty() || maSP.equals("Tất cả")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mã sản phẩm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedSupplierIndex == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float price;
            try {
                price = Float.parseFloat(priceStr);
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá nhập phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem chi tiết phiếu nhập đã tồn tại chưa
            if (daoChiTietPhieuNhap.kiemTraTonTai(maPN, maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm này đã tồn tại trong phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tính thành tiền
            float thanhTien = price * quantity;

            // Thêm vào database
            ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPN, maSP, tenSP, quantity, price, thanhTien);
            daoChiTietPhieuNhap.themChiTietPhieuNhap(chiTiet);

            // Thêm vào bảng
            int stt = table.getRowCount() + 1;
            tableModel.addRow(new Object[]{stt, maSP, tenSP, price, quantity, thanhTien});

            // Cập nhật tổng tiền
            totalAmount += (long) thanhTien;
            updateTotalLabel();



            // Reset các trường
            txtQuantity.setText("");
            txtPrice.setText("");
            cbbMaSP.setSelectedIndex(0);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPN = (String) cbbReceiptId.getSelectedItem();
            String maSP = table.getValueAt(selectedRow, 1).toString();
            int quantity = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());

            // Xóa trong database
            if (!daoChiTietPhieuNhap.xoaChiTietPhieuNhap(maPN, maSP)) {
                JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnImportExcel);

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

        JButton btnSubmit = new JButton("Nhập hàng");
        btnSubmit.setBackground(new Color(34, 139, 34));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setPreferredSize(new Dimension(150, 35));

        btnSubmit.addActionListener(e -> {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một sản phẩm để nhập hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String receiptId = (String) cbbReceiptId.getSelectedItem();
            int selectedSupplierIndex = cbbSupplier.getSelectedIndex();
            String employee = txtEmployee.getText();

            if (receiptId == null || receiptId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedSupplierIndex == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (employee == null || employee.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không thể lấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy MaNCC từ danh sách nhà cung cấp
            String supplierId = nhaCungCapList.get(selectedSupplierIndex).getMaNCC();

            // Gọi capNhatPhieuNhap để lưu phiếu nhập vào database
            boolean success = daoPhieuNhap.capNhatPhieuNhap(receiptId, supplierId, Session.getUsername(), totalAmount);


            if (success) {
                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                parent.reloadTable(); // Gọi reload bảng từ parent
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nhập hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            table.setRowSorter(null);
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