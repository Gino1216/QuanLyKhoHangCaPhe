package View;

import Config.Session;
import DTO.*;
import Dao.*;
import EX.ExPhieuNhap;
import Repository.PhieuNhapRepo;
import Gui.InputDate;
import Gui.MainFunction;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhieuNhap extends JPanel {

    private MainFunction functionBar;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cbbFilter;
    private JTextField txtSearch;
    private JButton btnRefresh;
    private DefaultTableModel tableModel;
    private InputDate dateStart, dateEnd;
    private Color backgroundColor = new Color(255, 255, 255);
    private List<PNDTO> importEntries;
    private List<SanPhamDTO> sanPhamList;


    private JComboBox<String> cbbSupplier;
    private JComboBox<String> cbbEmployee;
    private JTextField txtFromAmount;
    private JTextField txtToAmount;

    public PhieuNhap() {
        sanPhamList = new ArrayList<>();
        importEntries = new ArrayList<>();
        setLayout(new BorderLayout(0, 8));
        setBackground(backgroundColor);

        DaoSP daoSP = new DaoSP();
        sanPhamList = daoSP.layDanhSachSanPham();
        if (sanPhamList == null) {
            sanPhamList = new ArrayList<>(); // Đảm bảo không null
            System.out.println("Danh sách sản phẩm rỗng hoặc không tải được!");
        }

        // Top Panel (Toolbar and Search)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        functionBar = new MainFunction("phieunhap", new String[]{"create", "detail", "cancel", "sucess", "export"});
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
        DaoPhieuNhap daoPhieuNhap = new DaoPhieuNhap();
        String maPN;
        int maxAttempts = 10;
        int attempt = 0;
        do {
            maPN = daoPhieuNhap.sinhMaPN();
            if (maPN == null) {
                JOptionPane.showMessageDialog(this, "Không thể sinh mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            attempt++;
        } while (daoPhieuNhap.kiemTraMaPNTonTai(maPN) && attempt < maxAttempts);
        if (attempt >= maxAttempts) {
            JOptionPane.showMessageDialog(this, "Không thể tạo mã phiếu nhập duy nhất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return maPN;
    }

    private void addMaPNTT() {
        DaoPhieuNhap daoPhieuNhap = new DaoPhieuNhap();
        String maPN = generateReceiptId();

        if (maPN == null) {
            return;
        }

        if (!daoPhieuNhap.kiemTraMaPNTonTai(maPN)) {
            daoPhieuNhap.themMaPnVaTT(maPN);
            loadTableData(tableModel);
        } else {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToExcel() {
        ExPhieuNhap.exportPhieuNhapToExcel("E:/DanhSachPhieuNhap.xlsx"); // Dùng / thay cho \\
    }

    private void setupFunctionBarActions() {
        // Nút "Thêm" (create)
        functionBar.setButtonActionListener("create", () -> {
            addMaPNTT();
            CreaterPhieuNhap createrPhieuNhap = new CreaterPhieuNhap(this);
            createrPhieuNhap.setVisible(true);
        });

        // Nút "Chi tiết" (detail)
        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PNDTO entry = importEntries.get(selectedRow);
            if (entry == null) {
                JOptionPane.showMessageDialog(this, "Dữ liệu phiếu nhập không hợp lệ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DaoChiTietPhieuNhap dao = new DaoChiTietPhieuNhap();
            List<ChiTietPhieuNhapDTO> items;
            try {
                items = dao.layChiTietPhieuNhapTheoMaPN(entry.getMaPN());
                if (items == null || items.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết phiếu nhập cho mã: " + entry.getMaPN(),
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy chi tiết phiếu nhập: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietNhap detail = new ChiTietNhap(
                    entry.getMaPN(),
                    entry.getMaNCC(),
                    entry.getMaNV(),
                    entry.getNgayNhap(),
                    String.format("%,.0f", entry.getTongTien()),
                    entry.getTrangThai(),
                    items
            );
            detail.setVisible(true);
        });

        // Nút "Hủy" (cancel)
        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để hủy!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PNDTO selectedPhieuNhap = importEntries.get(selectedRow);
            String status = selectedPhieuNhap.getTrangThai();

            if (Session.getRole() == 1) {
                JOptionPane.showMessageDialog(this, "Không đủ quyền để hủy", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else if(Session.getRole()==2 || Session.getRole() ==3) {
                if ("Hoàn thành".equals(status)||"Không duyệt".equals(status) || "Kế toán duyệt".equals(status)) {
                    JOptionPane.showMessageDialog(this, "Không thể hủy phiếu này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }else {
                    table.setValueAt("Không duyệt", selectedRow, 5);
                    selectedPhieuNhap.setTrangThai("Không duyệt");

                    DaoPhieuNhap daoPhieuNhap = new DaoPhieuNhap();
                    boolean isSuccess = daoPhieuNhap.huyDuyetPhieuNhap(selectedPhieuNhap);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Hủy phiếu nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi hủy phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Nút "Duyệt" (sucess)
        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PNDTO selectedPhieuNhap = importEntries.get(selectedRow);
            String status = selectedPhieuNhap.getTrangThai();
            System.out.println("Status: " + status);

            if (Session.getRole() == 1) {
                JOptionPane.showMessageDialog(this, "Không đủ thẩm quyền để duyệt!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else if (Session.getRole() == 2) {
                if ("Chưa duyệt".equals(status)) {
                    table.setValueAt("Kế toán duyệt", selectedRow, 5);
                    selectedPhieuNhap.setTrangThai("Kế toán duyệt");

                    DaoPhieuNhap daoPhieuNhap = new DaoPhieuNhap();
                    boolean isSuccess = daoPhieuNhap.keToanDuyetPhieuNhap(selectedPhieuNhap);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Kế toán duyệt phiếu nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi kế toán duyệt phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể duyệt phiếu này!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                DaoChiTietPhieuNhap daoChiTietPhieuNhap = new DaoChiTietPhieuNhap();
                List<ChiTietPhieuNhapDTO> chiTietList = daoChiTietPhieuNhap.layChiTietPhieuNhapTheoMaPN(selectedPhieuNhap.getMaPN());

                if ("Kế toán duyệt".equals(status)) {
                    table.setValueAt("Hoàn thành", selectedRow, 5);
                    selectedPhieuNhap.setTrangThai("Hoàn thành");

                    DaoPhieuNhap daoPhieuNhap = new DaoPhieuNhap();
                    boolean isSuccess = daoPhieuNhap.duyetPhieuNhap(selectedPhieuNhap);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Duyệt phiếu nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        DaoSP daoSP = new DaoSP();
                        for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
                            String maSP = chiTiet.getMaSP();
                            int soLuongNhap = chiTiet.getSoLuong();
                            boolean found = false;
                            for (SanPhamDTO sp : sanPhamList) {
                                if (sp.getMaSP().equals(maSP)) {
                                    sp.setSoLuong(sp.getSoLuong() + soLuongNhap);
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

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên nhập", "Ngày nhập", "Tổng tiền", "Trạng thái"});
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
        JLabel lblSupplier = new JLabel("Nhà cung cấp");
        leftPanel.add(lblSupplier, gbc);

        gbc.gridy++;
        DaoNCC daoNCC = new DaoNCC();
        List<NhaCungCapDTO> nhaCungCapList = daoNCC.layDanhSachNhaCungCap();
        List<String> supplierOptions = new ArrayList<>();
        supplierOptions.add("Tất cả");
        for (NhaCungCapDTO ncc : nhaCungCapList) {
            supplierOptions.add(ncc.getMaNCC());
        }
        cbbSupplier = new JComboBox<>(supplierOptions.toArray(new String[0]));
        cbbSupplier.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbSupplier, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên nhập");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        DaoAccount daoAccount = new DaoAccount();
        List<Account> nhanVienList = daoAccount.layDanhSachAccountFull();
        List<String> employeeOptions = new ArrayList<>();
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

        cbbSupplier.addActionListener(e -> filterData());
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
            PhieuNhapRepo repo = new DaoPhieuNhap();
            List<PNDTO> danhSach = repo.layDanhSachPhieuNhap();
            importEntries.clear();
            importEntries.addAll(danhSach);

            model.setRowCount(0);
            DecimalFormat df = new DecimalFormat("#,###");

            for (PNDTO pn : danhSach) {
                model.addRow(new Object[]{
                        pn.getMaPN(),
                        pn.getMaNCC(),
                        pn.getMaNV(),
                        pn.getNgayNhap(),
                        df.format(pn.getTongTien()),
                        pn.getTrangThai()
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
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        String[] columns = {"Mã phiếu nhập", "Nhà cung cấp", "Nhân viên nhập", "Ngày nhập", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        customizeTableAppearance();
        loadTableData(tableModel);

        return new JScrollPane(table);
    }

    private void filterData() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        String searchText = txtSearch.getText().trim().toLowerCase();
        String selectedFilter = (String) cbbFilter.getSelectedItem();
        if (!searchText.isEmpty()) {
            int[] columnIndices;
            if ("Tất cả".equals(selectedFilter)) {
                columnIndices = new int[]{0, 1, 2, 3, 4, 5};
            } else {
                int columnIndex = switch (selectedFilter) {
                    case "Mã phiếu nhập" -> 0;
                    case "Nhà cung cấp" -> 1;
                    case "Nhân viên nhập" -> 2;
                    case "Ngày nhập" -> 3;
                    case "Tổng tiền" -> 4;
                    case "Trạng thái" -> 5;
                    default -> 0;
                };
                columnIndices = new int[]{columnIndex};
            }
            filters.add(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(searchText), columnIndices));
        }

        String selectedSupplier = (String) cbbSupplier.getSelectedItem();
        if (!"Tất cả".equals(selectedSupplier)) {
            filters.add(RowFilter.regexFilter("^" + java.util.regex.Pattern.quote(selectedSupplier) + "$", 1));
        }

        String selectedEmployee = (String) cbbEmployee.getSelectedItem();
        if (!"Tất cả".equals(selectedEmployee)) {
            filters.add(RowFilter.regexFilter("^" + java.util.regex.Pattern.quote(selectedEmployee) + "$", 2));
        }

        String fromAmountStr = txtFromAmount.getText().trim();
        String toAmountStr = txtToAmount.getText().trim();
        if (!fromAmountStr.isEmpty() || !toAmountStr.isEmpty()) {
            filters.add(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                    String amountStr = (String) entry.getValue(4);
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

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }
}