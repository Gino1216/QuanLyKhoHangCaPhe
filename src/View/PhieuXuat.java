package View;

import DTO.PXDTO;
import Dao.DaoPhieuXuat;
import Repository.PhieuXuatRepo;
import View.Dialog.CreaterPhieuXuat;
import Gui.InputDate;
import Gui.MainFunction;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
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

    public PhieuXuat() {
        exportEntries = new ArrayList<>();
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

    private void setupFunctionBarActions() {
        // Nút "Thêm" (create)
        functionBar.setButtonActionListener("create", () -> {
            CreaterPhieuXuat createrPhieuXuat = new CreaterPhieuXuat(this);
            createrPhieuXuat.setVisible(true);
        });

        // Nút "Chi tiết" (detail)
        functionBar.setButtonActionListener("detail", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin từ hàng được chọn
            PXDTO entry = exportEntries.get(selectedRow);
            // TODO: Cần cập nhật dialog ChiTietXuat để hiển thị thông tin từ PXDTO
            JOptionPane.showMessageDialog(this, "Chức năng xem chi tiết chưa được cập nhật!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Nút "Hủy" (cancel)
        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Hủy", selectedRow, 6);
            exportEntries.get(selectedRow).setTrangThai("Hủy");
        });

        // Nút "Duyệt" (sucess)
        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để duyệt!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            table.setValueAt("Đã Duyệt", selectedRow, 6);
            exportEntries.get(selectedRow).setTrangThai("Đã Duyệt");
        });

        // Nút "Xuất" (export)
        functionBar.setButtonActionListener("export", () -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất phiếu xuất chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã phiếu xuất", "Khách hàng", "Nhân viên"});
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
            loadData();
            table.setRowSorter(null);
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
        JComboBox<String> cbbCustomer = new JComboBox<>(new String[]{"Tất cả"});
        cbbCustomer.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(cbbCustomer, gbc);

        gbc.gridy++;
        JLabel lblEmployee = new JLabel("Nhân viên xuất");
        leftPanel.add(lblEmployee, gbc);

        gbc.gridy++;
        JComboBox<String> cbbEmployee = new JComboBox<>(new String[]{"Tất cả"});
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
        JTextField txtFromAmount = new JTextField();
        txtFromAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtFromAmount, gbc);

        gbc.gridy++;
        JLabel lblToAmount = new JLabel("Đến số tiền (VNĐ)");
        leftPanel.add(lblToAmount, gbc);

        gbc.gridy++;
        JTextField txtToAmount = new JTextField();
        txtToAmount.setPreferredSize(new Dimension(200, 35));
        leftPanel.add(txtToAmount, gbc);

        return leftPanel;
    }

    private void loadTableData(DefaultTableModel model) {
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
                        px.getSTT(),
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
        String[] columns = {"STT", "Mã phiếu xuất", "Khách hàng", "Nhân viên xuất", "Thời gian", "Tổng tiền", "Trạng thái"};

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
            columnIndices = new int[]{0, 1, 2, 3, 4};
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Mã phiếu xuất" -> 1;
                case "Khách hàng" -> 2;
                case "Nhân viên" -> 3;
                default -> 0;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        loadTableData(tableModel);
        System.out.println("Dữ liệu đã được làm mới.");
    }

    public void addPhieuXuat(String maPX, String maKhachHang, String maNhanVien, float tongTien) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDate = LocalDateTime.now();
        DecimalFormat df = new DecimalFormat("#,###");
        int stt = table.getRowCount() + 1;

        // Tạo một PXDTO mới
        PXDTO entry = new PXDTO(
                stt,
                maPX,
                maKhachHang,
                maNhanVien,
                currentDate,
                tongTien,
                "Chưa duyệt"
        );

        // Thêm vào danh sách và bảng
        exportEntries.add(entry);
        tableModel.addRow(new Object[]{
                entry.getSTT(),
                entry.getMaPX(),
                entry.getMaKhachHang(),
                entry.getMaNhanVien(),
                entry.getThoiGian().format(formatter),
                df.format(entry.getTongTien()),
                entry.getTrangThai()
        });
    }
}