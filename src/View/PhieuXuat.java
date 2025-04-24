package View;

import Config.Session;
import DTO.*;
import Dao.*;
import EX.ExPhieuXuat;
import Repository.PhieuXuatRepo;
import Gui.InputDate;
import Gui.MainFunction;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhieuXuat extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private DefaultTableModel tableModel;
    private InputDate dateStart, dateEnd;
    private Color backgroundColor = new Color(255, 255, 255);
    private List<PXDTO> exportEntries;

    private JComboBox<String> cbbCustomer;
    private JComboBox<String> cbbEmployee;
    private JTextField txtFromAmount;
    private JTextField txtToAmount;
    private List<SanPhamDTO> sanPhamList;


    public PhieuXuat() {
        exportEntries = new ArrayList<>();
        sanPhamList =new ArrayList<>();

        DaoSP daoSP = new DaoSP();
        sanPhamList = daoSP.layDanhSachSanPham();
        if (sanPhamList == null) {
            sanPhamList = new ArrayList<>(); // Đảm bảo không null
            System.out.println("Danh sách sản phẩm rỗng hoặc không tải được!");
        }

        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        // Top Panel (Toolbar and Search)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("phieuxuat", new String[]{"create", "detail", "cancel", "sucess", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Left Panel (Filter Options)
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        // Center Panel (Table)
        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        // Setup actions for toolbar buttons
        setupFunctionBarActions();
    }

    private String generateReceiptId() {
        DaoPhieuXuat daoPhieuXuat = new DaoPhieuXuat();
        String maPX;
        int maxAttempts = 10; // Giới hạn tối đa số lần thử
        int attempt = 0;
        do {
            maPX = daoPhieuXuat.sinhMaPX(); // Sử dụng sinhMaPX từ DaoPhieuXuat
            if (maPX == null) {
                JOptionPane.showMessageDialog(this, "Không thể sinh mã phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            attempt++;
        } while (daoPhieuXuat.kiemTraMaPXTonTai(maPX) && attempt < maxAttempts);
        if (attempt >= maxAttempts) {
            JOptionPane.showMessageDialog(this, "Không thể tạo mã phiếu xuất duy nhất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return maPX;
    }

    private void addMaPXTT() {
        DaoPhieuXuat daoPhieuXuat = new DaoPhieuXuat();
        String maPX = generateReceiptId();

        if (maPX == null) {
            return;
        }

        if (!daoPhieuXuat.kiemTraMaPXTonTai(maPX)) {
            daoPhieuXuat.themMaPxVaTT(maPX); // Thêm mã phiếu xuất vào database
            loadTableData(tableModel); // Cập nhật bảng
        } else {
            JOptionPane.showMessageDialog(this, "Mã phiếu xuất đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupFunctionBarActions() {
        // Nút "Thêm" (create)
        functionBar.setButtonActionListener("create", () -> {
            addMaPXTT(); // Tự động thêm mã phiếu xuất vào database và cập nhật bảng
            CreaterPhieuXuat createrPhieuXuat = new CreaterPhieuXuat(this);
            createrPhieuXuat.setVisible(true);
        });
        // Nút "Chi tiết" (detail)
        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            System.out.println("Selected row: " + selectedRow);
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xem chi tiết!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PXDTO entry = exportEntries.get(selectedRow);
            System.out.println("Selected entry: " + entry.getMaPX());
            if (entry == null) {
                JOptionPane.showMessageDialog(this, "Dữ liệu phiếu xuất không hợp lệ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String formattedDate = entry.getThoiGian() != null
                    ? entry.getThoiGian().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : "";

            DaoChiTietPhieuXuat dao = new DaoChiTietPhieuXuat();
            List<ChiTietPhieuXuatDTO> coffeeItems;
            try {
                coffeeItems = dao.layChiTietPhieuXuatHoanThanhTheoMaPX(entry.getMaPX());
                System.out.println("Coffee items size: " + (coffeeItems != null ? coffeeItems.size() : "null"));
                if (coffeeItems == null || coffeeItems.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết phiếu xuất cho mã: " + entry.getMaPX(),
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy chi tiết phiếu xuất: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietXuat detail = new ChiTietXuat(
                    entry.getMaPX(),
                    entry.getMaKhachHang(),
                    entry.getMaNhanVien(),
                    formattedDate,
                    String.format("%,.0f", entry.getTongTien()),
                    entry.getTrangThai(),
                    coffeeItems
            );
            System.out.println("ChiTietXuat params: maPX=" + entry.getMaPX() + ", coffeeItemsSize=" +
                    (coffeeItems != null ? coffeeItems.size() : "null"));
            detail.setVisible(true);
        });

        //cancel
        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            PXDTO selectedPhieuXuat = exportEntries.get(selectedRow);

            String status = selectedPhieuXuat.getTrangThai();

            if(Session.getRole()==1){
                JOptionPane.showMessageDialog(this,"Không đủ quyền để hủy","Thông báo",JOptionPane.ERROR_MESSAGE);
            }else{
                if ("Hoàn thành".equals(status)||"Không duyệt".equals(status) || "Kế toán duyệt".equals(status)) {
                    JOptionPane.showMessageDialog(this, "Không thể hủy phiếu này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    table.setValueAt("Không duyệt", selectedRow, 5);  // Column 6 is where the status is stored
                    selectedPhieuXuat.setTrangThai("Không duyệt");

                    // Call DuyetPhieuXuat to update the status in the database
                    DaoPhieuXuat daoPhieuXuat = new DaoPhieuXuat();  // Assuming you have this DAO available
                    boolean isSuccess = daoPhieuXuat.HuyDuyetPhieuXuat(selectedPhieuXuat);

                    // If updating the database was successful, show a success message
                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Không duyệt thành công !", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi duyệt phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }


            PXDTO selectedPhieuXuat = exportEntries.get(selectedRow);
            String status = selectedPhieuXuat.getTrangThai();

            if (Session.getRole() == 1) {
                JOptionPane.showMessageDialog(this, "Không đủ thẩm quyền để duyệt!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else if (Session.getRole() == 2) {
                if ("Chưa duyệt".equals(status)) {
                    table.setValueAt("Kế toán duyệt", selectedRow, 5); // Cột 5 là cột trạng thái
                    selectedPhieuXuat.setTrangThai("Kế toán duyệt");

                    // Gọi DAO để cập nhật trạng thái "Kế toán duyệt" trong cơ sở dữ liệu
                    DaoPhieuXuat daoPhieuXuat = new DaoPhieuXuat();
                    boolean isSuccess = daoPhieuXuat.keToanDuyetPhieuXuat(selectedPhieuXuat);

                    // Thông báo kết quả
                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Kế toán duyệt phiếu xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi kế toán duyệt phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(this, "Không thể duyệt phiếu này!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                DaoChiTietPhieuXuat daoChiTietPhieuXuat =new DaoChiTietPhieuXuat();
                List<ChiTietPhieuXuatDTO> chiTietList = daoChiTietPhieuXuat.layChiTietPhieuXuatTheoMaPX(selectedPhieuXuat.getMaPX());

                if ("Kế toán duyệt".equals(status)) {
                    table.setValueAt("Hoàn thành", selectedRow, 5);
                    selectedPhieuXuat.setTrangThai("Hoàn thành");

                    DaoPhieuXuat daoPhieuXuat=new DaoPhieuXuat();
                    boolean isSuccess = daoPhieuXuat.DuyetPhieuXuat(selectedPhieuXuat);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Duyệt phiếu nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        DaoSP daoSP = new DaoSP();
                        for (ChiTietPhieuXuatDTO chiTiet : chiTietList) {
                            String maSP = chiTiet.getMaSP();
                            int soLuongNhap = chiTiet.getSoLuong();
                            boolean found = false;
                            for (SanPhamDTO sp : sanPhamList) {
                                if (sp.getMaSP().equals(maSP)) {
                                    sp.setSoLuong(sp.getSoLuong() - soLuongNhap);
                                    if (daoSP.suaSanPham(sp)) {
                                        System.out.println("Cập nhật sản phẩm: " + maSP + ", Số lượng mới: " + sp.getSoLuong());
                                    } else {
                                        System.out.println("Lỗi khi cập nhật sản phẩm: " + maSP);
                                    }
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Không tìm thấy sản phẩm với mã: " + maSP);
                            }
                        }
                        sanPhamList = daoSP.layDanhSachSanPham();

                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi duyệt phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể duyệt phiếu này!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        // Nút "Xuất" (export)
        functionBar.setButtonActionListener("export", this:: exportToExcel);

    }

    private void exportToExcel() {
        ExPhieuXuat.exportPhieuXuatToExcel("E:/DanhSachPhieuXuat.xlsx"); // Dùng / thay cho \\
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã phiếu xuất", "Khách hàng", "Nhân viên xuất", "Thời gian", "Tổng tiền", "Trạng thái"});
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

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(backgroundColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("Bộ lọc");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        leftPanel.add(lblTitle, gbc);

        gbc.gridy++;
        JLabel lblCustomer = new JLabel("Khách Hàng");
        leftPanel.add(lblCustomer, gbc);

        gbc.gridy++;
        // Tạo danh sách khách hàng từ DaoKH
        DaoKH daoKH = new DaoKH();
        List<KhachHangDTO> khachHangList = daoKH.layDanhSachKhachHang();
        List<String> customerOptions = new ArrayList<>();
        customerOptions.add("Tất cả");
        for (KhachHangDTO kh : khachHangList) {
            customerOptions.add(kh.getMaKH());
        }
        cbbCustomer = new JComboBox<>(customerOptions.toArray(new String[0]));
        cbbCustomer.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbCustomer, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên xuất");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        List<String> employeeOptions = new ArrayList<>();
        DaoAccount daoAccount =new DaoAccount();
        List<Account>nhanVienList=daoAccount.layDanhSachAccountFull();
        employeeOptions.add("Tất cả");
        for (Account nv : nhanVienList) {
            employeeOptions.add(nv.getUsername());
        }
        cbbEmployee = new JComboBox<>(employeeOptions.toArray(new String[0]));
        cbbEmployee.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbEmployee, gbc);

        gbc.gridy++;
        dateStart = new InputDate("Từ ngày", 200, 70);
        leftPanel.add(dateStart, gbc);

        gbc.gridy++;
        dateEnd = new InputDate("Đến ngày", 200, 70);
        leftPanel.add(dateEnd, gbc);

        gbc.gridy++;
        JLabel lblFromAmount = new JLabel("Từ số tiền (VNĐ)");
        leftPanel.add(lblFromAmount, gbc);

        gbc.gridy++;
        txtFromAmount = new JTextField();
        txtFromAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtFromAmount, gbc);

        gbc.gridy++;
        JLabel lblToAmount = new JLabel("Đến số tiền (VNĐ)");
        leftPanel.add(lblToAmount, gbc);

        gbc.gridy++;
        txtToAmount = new JTextField();
        txtToAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtToAmount, gbc);

        // Add listeners to trigger filtering when left panel components change
        cbbCustomer.addActionListener(e -> filterData());
        cbbEmployee.addActionListener(e -> filterData());
        txtFromAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterData(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterData(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterData(); }
        });
        txtToAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterData(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterData(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterData(); }
        });
        dateStart.addPropertyChangeListener("date", e -> filterData());
        dateEnd.addPropertyChangeListener("date", e -> filterData());

        return leftPanel;
    }

    public void loadTableData(DefaultTableModel model) {
        try {
            PhieuXuatRepo repo = new DaoPhieuXuat();
            List<PXDTO> danhSach = repo.layDanhSachPhieuXuat();
            exportEntries.clear();
            exportEntries.addAll(danhSach);

            model.setRowCount(0); // Xóa dữ liệu cũ
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DecimalFormat df = new DecimalFormat("#,###");

            for (PXDTO px : danhSach) {
                model.addRow(new Object[]{
                        px.getMaPX(),
                        px.getMaKhachHang(),
                        px.getMaNhanVien(),
                        px.getThoiGian().format(formatter),
                        df.format(px.getTongTien()),
                        px.getTrangThai()
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

    private JScrollPane createTable() {
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Columns
        String[] columns = {"Mã phiếu xuất", "Khách hàng", "Nhân viên xuất", "Thời gian", "Tổng tiền", "Trạng thái"};

        // Tạo model với 0 row ban đầu
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo table với model
        table = new JTable(tableModel);
        customizeTableAppearance();

        loadTableData(tableModel);

        return new JScrollPane(table);
    }

    private void filterData() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        // 1. Text search filter
        String searchText = txtSearch.getText().trim().toLowerCase();
        String selectedFilter = (String) cbbFilter.getSelectedItem();
        if (!searchText.isEmpty()) {
            int[] columnIndices;
            if ("Tất cả".equals(selectedFilter)) {
                columnIndices = new int[]{0, 1, 2, 3, 4, 5};
            } else {
                int columnIndex = switch (selectedFilter) {
                    case "Mã phiếu xuất" -> 0;
                    case "Khách hàng" -> 1;
                    case "Nhân viên xuất" -> 2;
                    case "Thời gian" -> 3;
                    case "Tổng tiền" -> 4;
                    case "Trạng thái" -> 5;
                    default -> 0;
                };
                columnIndices = new int[]{columnIndex};
            }
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText), columnIndices));
        }

        // 2. Customer filter
        String selectedCustomerName = (String) cbbCustomer.getSelectedItem();
        if (!"Tất cả".equals(selectedCustomerName)) {
            filters.add(RowFilter.regexFilter("^" + Pattern.quote(selectedCustomerName) + "$", 1));
        }

        // 3. Employee filter
        String selectedEmployee = (String) cbbEmployee.getSelectedItem();
        if (!"Tất cả".equals(selectedEmployee)) {
            filters.add(RowFilter.regexFilter("^" + Pattern.quote(selectedEmployee) + "$", 2));
        }


        // 4. Date range filter
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;



        // 5. Amount range filter
        String fromAmountStr = txtFromAmount.getText().trim();
        String toAmountStr = txtToAmount.getText().trim();
        if (!fromAmountStr.isEmpty() || !toAmountStr.isEmpty()) {
            filters.add(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                    String amountStr = (String) entry.getValue(4); // Tổng tiền column
                    try {
                        double amount = Double.parseDouble(amountStr.replace(",", ""));
                        if (!fromAmountStr.isEmpty()) {
                            double fromAmount = Double.parseDouble(fromAmountStr);
                            if (amount < fromAmount) {
                                return false;
                            }
                        }
                        if (!toAmountStr.isEmpty()) {
                            double toAmount = Double.parseDouble(toAmountStr);
                            if (amount > toAmount) {
                                return false;
                            }
                        }
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            });
        }

        // Combine all filters
        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }


    public void reloadTable() {
        loadTableData(tableModel);
    }
}