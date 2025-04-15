package View;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class CreaterPhieuNhap extends JFrame {

    private PhieuNhap parent; // Tham chiếu đến PhieuNhap
    private JTextField txtSearch;
    private JTextField txtReceiptId;
    private JTextField txtPrice;
    private JTextField txtEmployee;
    private JTextField txtQuantity;
    private JTextField txtWeight;
    private JComboBox<String> cbbCoffeeName;
    private JComboBox<String> cbbCoffeeType;
    private JComboBox<String> cbbMethod;
    private JComboBox<String> cbbSupplier;
    private JList<String> coffeeList;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private Color backgroundColor = new Color(240, 247, 250);
    private long totalAmount = 0;

    // Constructor mặc định
    public CreaterPhieuNhap() {
        FlatLightLaf.setup();

        setTitle("Thêm Phiếu Nhập Cafe");
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

    // Constructor nhận tham chiếu đến PhieuNhap
    CreaterPhieuNhap(PhieuNhap aThis) {
        this.parent = aThis; // Lưu tham chiếu đến PhieuNhap
        FlatLightLaf.setup();

        setTitle("Thêm Phiếu Nhập Cafe");
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
        coffees.add("CF001 - Cafe Đắk Lắk - 100kg");
        coffees.add("CF002 - Cafe Buôn Ma Thuột - 80kg");
        coffees.add("CF003 - Cafe Lâm Đồng - 50kg");
        coffees.add("CF004 - Cafe Gia Lai - 120kg");
        coffees.add("CF005 - Cafe Khe Sanh - 60kg");

        coffeeList = new JList<>(coffees);
        coffeeList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        coffeeList.setVisibleRowCount(10);
        JScrollPane coffeeScroll = new JScrollPane(coffeeList);
        coffeeScroll.setPreferredSize(new Dimension(300, 200));
        leftPanel.add(coffeeScroll, BorderLayout.CENTER);

        centerPanel.add(leftPanel, BorderLayout.WEST);

        // Right Panel (Input Form)
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

        txtReceiptId = new JTextField("PN-0");
        txtReceiptId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtReceiptId.setPreferredSize(new Dimension(200, 35));
        txtReceiptId.setEditable(false);
        gbc.gridx = 1;
        rightPanel.add(txtReceiptId, gbc);

        // Tên cafe
        JLabel lblCoffeeName = new JLabel("Tên cafe");
        lblCoffeeName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblCoffeeName, gbc);

        cbbCoffeeName = new JComboBox<>(new String[]{"Chọn cafe", "Cafe Đắk Lắk", "Cafe Buôn Ma Thuột", "Cafe Lâm Đồng", "Cafe Gia Lai", "Cafe Khe Sanh"});
        cbbCoffeeName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbCoffeeName.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(cbbCoffeeName, gbc);

        // Nhân viên nhập
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        lblEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblEmployee, gbc);

        txtEmployee = new JTextField("Hoàng Gia Bảo");
        txtEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmployee.setPreferredSize(new Dimension(200, 35));
        txtEmployee.setEditable(false);
        gbc.gridx = 5;
        rightPanel.add(txtEmployee, gbc);

        // Phương thức nhập
        JLabel lblMethod = new JLabel("Phương thức nhập");
        lblMethod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(lblMethod, gbc);

        cbbMethod = new JComboBox<>(new String[]{"Nhập theo bao"});
        cbbMethod.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbMethod.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        rightPanel.add(cbbMethod, gbc);

        // Nhà cung cấp
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        lblSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblSupplier, gbc);

        cbbSupplier = new JComboBox<>(new String[]{"Công Ty Cafe Trung Nguyên", "Nông Trại Cafe Đắk Lắk", "Hợp Tác Xã Cafe Lâm Đồng"});
        cbbSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbSupplier.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(cbbSupplier, gbc);

        // Giá nhập
        JLabel lblPrice = new JLabel("Giá nhập (VNĐ/kg)");
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblPrice, gbc);

        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrice.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 5;
        rightPanel.add(txtPrice, gbc);

        // Số lượng
        JLabel lblQuantity = new JLabel("Số lượng (bao)");
        lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(lblQuantity, gbc);

        txtQuantity = new JTextField();
        txtQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtQuantity.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        rightPanel.add(txtQuantity, gbc);

        // Loại cafe
        JLabel lblCoffeeType = new JLabel("Loại cafe");
        lblCoffeeType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        rightPanel.add(lblCoffeeType, gbc);

        cbbCoffeeType = new JComboBox<>(new String[]{"Arabica", "Robusta", "Culi", "Blend"});
        cbbCoffeeType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbbCoffeeType.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        rightPanel.add(cbbCoffeeType, gbc);

        // Trọng lượng
        JLabel lblWeight = new JLabel("Trọng lượng (kg/bao)");
        lblWeight.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        rightPanel.add(lblWeight, gbc);

        txtWeight = new JTextField();
        txtWeight.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtWeight.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 5;
        rightPanel.add(txtWeight, gbc);

        centerPanel.add(rightPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listener cho JList để tự động điền thông tin
        coffeeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCoffee = coffeeList.getSelectedValue();
                if (selectedCoffee != null) {
                    String[] parts = selectedCoffee.split(" - ");
                    String coffeeName = parts[1];
                    cbbCoffeeName.setSelectedItem(coffeeName);
                    txtPrice.setText(getPriceForCoffee(coffeeName));
                    cbbCoffeeType.setSelectedItem(getTypeForCoffee(coffeeName));
                    txtWeight.setText("50"); // Giả lập trọng lượng mặc định là 50kg/bao
                }
            }
        });

        return centerPanel;
    }

    private String getPriceForCoffee(String coffeeName) {
        return switch (coffeeName) {
            case "Cafe Đắk Lắk" ->
                "120000";
            case "Cafe Buôn Ma Thuột" ->
                "110000";
            case "Cafe Lâm Đồng" ->
                "100000";
            case "Cafe Gia Lai" ->
                "115000";
            case "Cafe Khe Sanh" ->
                "105000";
            default ->
                "0";
        };
    }

    private String getTypeForCoffee(String coffeeName) {
        return switch (coffeeName) {
            case "Cafe Đắk Lắk" ->
                "Arabica";
            case "Cafe Buôn Ma Thuột" ->
                "Robusta";
            case "Cafe Lâm Đồng" ->
                "Culi";
            case "Cafe Gia Lai" ->
                "Blend";
            case "Cafe Khe Sanh" ->
                "Robusta";
            default ->
                "Arabica";
        };
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
            String coffeeName = (String) cbbCoffeeName.getSelectedItem();
            String coffeeType = (String) cbbCoffeeType.getSelectedItem();
            String priceStr = txtPrice.getText();
            String quantityStr = txtQuantity.getText();
            String weightStr = txtWeight.getText();
            String supplier = (String) cbbSupplier.getSelectedItem();

            // Kiểm tra dữ liệu
            if (coffeeName == null || coffeeName.equals("Chọn cafe")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại cafe!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (supplier == null || supplier.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long price;
            int quantity;
            double weight;
            try {
                price = Long.parseLong(priceStr);
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá nhập phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                weight = Double.parseDouble(weightStr);
                if (weight <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Trọng lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm vào bảng
            int stt = table.getRowCount() + 1;
            String coffeeCode = "CF" + String.format("%03d", stt); // Giả lập mã cafe
            tableModel.addRow(new Object[]{stt, coffeeCode, coffeeName, coffeeType, weight, price, quantity});

            // Cập nhật tổng tiền
            totalAmount += price * quantity * weight;
            updateTotalLabel();

            // Reset các trường
            txtQuantity.setText("");
            txtWeight.setText("");
            cbbCoffeeName.setSelectedIndex(0);
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại cafe để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Trừ tổng tiền của hàng cũ
            long oldPrice = Long.parseLong(table.getValueAt(selectedRow, 5).toString());
            int oldQuantity = Integer.parseInt(table.getValueAt(selectedRow, 6).toString());
            double oldWeight = Double.parseDouble(table.getValueAt(selectedRow, 4).toString());
            totalAmount -= oldPrice * oldQuantity * oldWeight;

            // Lấy thông tin mới
            String coffeeName = (String) cbbCoffeeName.getSelectedItem();
            String coffeeType = (String) cbbCoffeeType.getSelectedItem();
            String priceStr = txtPrice.getText();
            String quantityStr = txtQuantity.getText();
            String weightStr = txtWeight.getText();
            String supplier = (String) cbbSupplier.getSelectedItem();

            // Kiểm tra dữ liệu
            if (coffeeName == null || coffeeName.equals("Chọn cafe")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại cafe!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (supplier == null || supplier.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long price;
            int quantity;
            double weight;
            try {
                price = Long.parseLong(priceStr);
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá nhập phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                weight = Double.parseDouble(weightStr);
                if (weight <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Trọng lượng phải là một số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật hàng trong bảng
            table.setValueAt(coffeeName, selectedRow, 2);
            table.setValueAt(coffeeType, selectedRow, 3);
            table.setValueAt(weight, selectedRow, 4);
            table.setValueAt(price, selectedRow, 5);
            table.setValueAt(quantity, selectedRow, 6);

            // Cập nhật tổng tiền
            totalAmount += price * quantity * weight;
            updateTotalLabel();

            // Reset các trường
            txtQuantity.setText("");
            txtWeight.setText("");
            cbbCoffeeName.setSelectedIndex(0);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại cafe để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Trừ tổng tiền của hàng bị xóa
            long price = Long.parseLong(table.getValueAt(selectedRow, 5).toString());
            int quantity = Integer.parseInt(table.getValueAt(selectedRow, 6).toString());
            double weight = Double.parseDouble(table.getValueAt(selectedRow, 4).toString());
            totalAmount -= price * quantity * weight;

            // Xóa hàng
            tableModel.removeRow(selectedRow);

            // Cập nhật STT
            for (int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt(i + 1, i, 0);
                table.setValueAt("CF" + String.format("%03d", i + 1), i, 1); // Cập nhật mã
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

        // Table
        String[] columns = {"STT", "Mã cafe", "Tên cafe", "Loại cafe", "Trọng lượng (kg/bao)", "Đơn giá (VNĐ/kg)", "Số lượng (bao)"};
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

        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // STT
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Mã cafe
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Tên cafe
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Loại cafe
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Trọng lượng
        table.getColumnModel().getColumn(5).setPreferredWidth(120); // Đơn giá
        table.getColumnModel().getColumn(6).setPreferredWidth(100); // Số lượng

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

        // Total and Submit Button
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
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một loại cafe để nhập hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Gửi dữ liệu về PhieuNhap
            String receiptId = txtReceiptId.getText();
            String supplier = (String) cbbSupplier.getSelectedItem();
            String employee = txtEmployee.getText();
            parent.addPhieuNhap(receiptId, supplier, employee, totalAmount);

            JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
