/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Config.Session;
import DTO.ChiTietPhieuXuatDTO;
import DTO.PXDTO;
import DTO.SanPhamDTO;
import DTO.TraHangDTO;
import Dao.DaoChiTietPhieuXuat;
import Dao.DaoPhieuXuat;
import Dao.DaoSP;
import Dao.DaoTraHang;

import EX.ExTraHang;
import Gui.MainFunction;
import Repository.TraHangRepo;
import View.Dialog.ChiTietTraHang;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hjepr
 */

public class TraHang extends JPanel {
    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);
    private List<TraHangDTO> exportEntries;
    private List<SanPhamDTO> sanPhamList;



    public TraHang() {
        exportEntries = new ArrayList<>(); // Khởi tạo rỗng nếu không có dữ liệu\
        sanPhamList=new ArrayList<>();

        DaoSP daoSP = new DaoSP();
        sanPhamList = daoSP.layDanhSachSanPham();
        if (sanPhamList == null) {
            sanPhamList = new ArrayList<>(); // Đảm bảo không null
            System.out.println("Danh sách sản phẩm rỗng hoặc không tải được!");
        }

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
        createTopPanel();
        loadData();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("ncc", new String[]{"create","detail","sucess","cancel", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        functionBar.setButtonActionListener("create", this::showAddPhieuTraDialog);
        functionBar.setButtonActionListener("detail",this::showTraHangDetails);
        functionBar.setButtonActionListener("export", this:: exportToExcel);

        functionBar.setButtonActionListener("cancel", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu trả hàng để hủy duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }


            TraHangDTO selectedPhieuTraHang = exportEntries.get(selectedRow);
            String status = selectedPhieuTraHang.getTrangThai();

            // Kiểm tra quyền
            if (Session.getRole() == 1) {
                JOptionPane.showMessageDialog(this, "Không đủ quyền để hủy duyệt",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra vai trò 2 hoặc 3 và trạng thái hợp lệ
            if (Session.getRole() == 2 || Session.getRole() == 3) {
                if ("Hoàn thành".equals(status) || "Không duyệt".equals(status) || "Kế toán duyệt".equals(status)) {
                    JOptionPane.showMessageDialog(this, "Không thể hủy phiếu trả hàng này!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cập nhật database trước
                DaoTraHang daoTraHang = new DaoTraHang();
                boolean isSuccess = daoTraHang.huyDuyetPhieuTraHang(selectedPhieuTraHang);

                // Cập nhật giao diện và đối tượng nếu thành công
                if (isSuccess) {
                    selectedPhieuTraHang.setTrangThai("Không duyệt");
                    table.setValueAt("Không duyệt", selectedRow, 7);
                    JOptionPane.showMessageDialog(this, "Hủy duyệt phiếu trả hàng thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi hủy duyệt phiếu trả hàng!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vai trò không hợp lệ để hủy duyệt!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        functionBar.setButtonActionListener("sucess", () -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để duyệt!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }


            TraHangDTO selectedPhieuXuat = exportEntries.get(selectedRow);
            String status = selectedPhieuXuat.getTrangThai();

            if (Session.getRole() == 1) {
                JOptionPane.showMessageDialog(this, "Không đủ thẩm quyền để duyệt!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else if (Session.getRole() == 2) {
                if("Quản lý duyệt".equals(status)) {
                    DaoTraHang daoTraHang=new DaoTraHang();
                    DaoChiTietPhieuXuat daoChiTietPhieuXuat =new DaoChiTietPhieuXuat();
                    List<ChiTietPhieuXuatDTO> chiTietList = daoChiTietPhieuXuat.layChiTietPhieuXuatTheoMaPX(selectedPhieuXuat.getMaPX());

                    table.setValueAt("Hoàn thành", selectedRow, 7);
                    selectedPhieuXuat.setTrangThai("Hoàn thành");


                    boolean isSuccess =daoTraHang.keToanDuyetTraHang(selectedPhieuXuat);

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
                }

                }else {
                    JOptionPane.showMessageDialog(this, "Không thể duyệt phiếu này!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }else if (Session.getRole() == 3) {
                if ("Chưa duyệt".equals(status)) {
                    table.setValueAt("Quản lý duyệt", selectedRow, 7); // Cột 5 là cột trạng thái
                    selectedPhieuXuat.setTrangThai("Quản lý duyệt");

                    // Gọi DAO để cập nhật trạng thái "Hoàn thành" trong cơ sở dữ liệu
                    DaoTraHang daoTraHang=new DaoTraHang();
                    boolean isSuccess = daoTraHang.duyetPhieuTraHang(selectedPhieuXuat);

                    // Thông báo kết quả
                    if (isSuccess) {
                        JOptionPane.showMessageDialog(this, "Duyệt phiếu xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi khi duyệt phiếu trả hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else{
                    JOptionPane.showMessageDialog(this, "Phiếu xuất này đã được duyệt!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        return topPanel;
    }





    private void showAddPhieuTraDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Phiếu Trả Hàng", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM PHIẾU TRẢ Hàng", JLabel.CENTER);
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
        String[] labels = {"Mã Phiếu Trả", "Mã Phiếu Xuất", "Mã Khách Hàng", "Mã Nhân Viên", "Tổng Tiền", "Ngày Trả", "Lý Do Trả", "Tổng Tiền Hoàn Trả", "Trạng Thái"};
        JTextField[] textFields = new JTextField[5]; // Mã Phiếu Trả, Mã KH, Mã NV, Tổng Tiền, Ngày Trả
        JComboBox<String> cbMaPX = new JComboBox<>();
        JTextArea taLyDoTra = new JTextArea(3, 20);
        JTextField tfTongTienHoanTra = new JTextField();
        JTextField  cbTrangThai = new JTextField ("Chưa duyệt");

        // Lấy danh sách MaPX từ phiếu xuất đã hoàn thành và chưa được trả
        DaoPhieuXuat daoPhieuXuat = new DaoPhieuXuat();
        DaoTraHang daoTraHang = new DaoTraHang();
        List<PXDTO> danhSachPX = daoPhieuXuat.getPhieuXuatChuaTraHang();
        cbMaPX.addItem("Chọn Mã PX"); // Thêm mục mặc định
        boolean hasValidMaPX = false;
        for (PXDTO px : danhSachPX) {
            if ("Hoàn thành".equals(px.getTrangThai()) && !daoTraHang.kiemTraMaPXTonTaiTrongTraHang(px.getMaPX())) {
                cbMaPX.addItem(px.getMaPX());
                hasValidMaPX = true;
            }
        }
        cbMaPX.setSelectedItem("Chọn Mã PX"); // Đặt mục mặc định được chọn

        // Kiểm tra nếu không có MaPX nào hợp lệ
        if (!hasValidMaPX) {
            JOptionPane.showMessageDialog(dialog, "Chưa có hàng nào để trả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            return;
        }

        // Khởi tạo các trường
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
            if (labels[i].equals("Mã Phiếu Xuất")) {
                cbMaPX.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbMaPX.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbMaPX, gbc);

                // Sự kiện chọn MaPX
                cbMaPX.addActionListener(e -> {

                    String selectedMaPX = (String) cbMaPX.getSelectedItem();
                    if (selectedMaPX != null && !selectedMaPX.equals("Chọn Mã PX")) {
                        List<PXDTO> pxList = daoPhieuXuat.layDanhSachPhieuXuatTheoMaPXVaTrangThaiHoanThanh(selectedMaPX);
                        if (!pxList.isEmpty()) {
                            PXDTO px = pxList.get(0);
                            textFields[1].setText(px.getMaKhachHang()); // Mã KH
//                            textFields[2].setText(px.getMaNhanVien()); // Mã NV
                            textFields[2].setText(Session.getUsername()); // Mã NV

                            textFields[3].setText(String.format("%.2f", px.getTongTien())); // Tổng Tiền
                            tfTongTienHoanTra.setText(String.format("%.2f", px.getTongTien())); // Tổng Tiền Hoàn Trả
                        } else {
                            textFields[1].setText("");
                            textFields[2].setText("");
                            textFields[3].setText("");
                            tfTongTienHoanTra.setText("");
                        }
                    } else {
                        textFields[1].setText("");
                        textFields[2].setText("");
                        textFields[3].setText("");
                        tfTongTienHoanTra.setText("");
                    }
                });
            } else if (labels[i].equals("Lý Do Trả")) {
                taLyDoTra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                taLyDoTra.setLineWrap(true);
                taLyDoTra.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(taLyDoTra);
                scrollPane.setPreferredSize(new Dimension(200, 60));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(scrollPane, gbc);
            } else if (labels[i].equals("Trạng Thái")) {
                cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbTrangThai.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbTrangThai, gbc);
            } else if (labels[i].equals("Tổng Tiền Hoàn Trả")) {
                tfTongTienHoanTra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                tfTongTienHoanTra.setPreferredSize(new Dimension(200, 40));
                tfTongTienHoanTra.setEditable(false); // Không cho phép chỉnh sửa
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(tfTongTienHoanTra, gbc);
            } else {
                int index = switch (labels[i]) {
                    case "Mã Phiếu Trả" -> 0;
                    case "Mã Khách Hàng" -> 1;
                    case "Mã Nhân Viên" -> 2;
                    case "Tổng Tiền" -> 3;
                    case "Ngày Trả" -> 4;
                    default -> -1;
                };
                textFields[index] = new JTextField(15);
                textFields[index].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[index].setPreferredSize(new Dimension(200, 40));

                // Mã Phiếu Trả, Ngày Trả, Mã KH, Mã NV, Tổng Tiền chỉ đọc
                if (labels[i].equals("Mã Phiếu Trả") || labels[i].equals("Ngày Trả") || labels[i].equals("Mã Khách Hàng") || labels[i].equals("Mã Nhân Viên") || labels[i].equals("Tổng Tiền")) {
                    textFields[index].setEditable(false);
                }

                // Gán giá trị mặc định
                if (labels[i].equals("Mã Phiếu Trả")) {
                    String maTraHang = daoTraHang.sinhMaTraHang();
                    textFields[index].setText(maTraHang != null ? maTraHang : "TH_XXX");
                } else if (labels[i].equals("Ngày Trả")) {
                    String ngayTra = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    textFields[index].setText(ngayTra);
                }

                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(textFields[index], gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm Phiếu Trả");
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
            String maTraHang = textFields[0].getText().trim();
            String maPX = (String) cbMaPX.getSelectedItem();
            String maKH = textFields[1].getText().trim();
            String maNV = textFields[2].getText().trim();
            String tongTien = textFields[3].getText().trim();
            String ngayTra = textFields[4].getText().trim();
            String lyDoTra = taLyDoTra.getText().trim();
            String tongTienHoanTraStr = tfTongTienHoanTra.getText().trim();
            String trangThai =  cbTrangThai.getText();

            // Kiểm tra các trường bắt buộc
            if (maPX == null || lyDoTra.isEmpty() || maPX.equals("Chọn Mã PX")) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn Mã Phiếu Xuất và điền Lý Do Trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra MaPX đã được trả
            if (daoTraHang.kiemTraMaPXTonTaiTrongTraHang(maPX)) {
                JOptionPane.showMessageDialog(dialog, "Không có hàng có thể trả cho phiếu xuất này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra tổng tiền hoàn trả
            float tongTienHoanTra;
            try {
                tongTienHoanTra = Float.parseFloat(tongTienHoanTraStr);
                if (tongTienHoanTra < 0) {
                    JOptionPane.showMessageDialog(dialog, "Tổng tiền hoàn trả không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Tổng tiền hoàn trả không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng TraHangDTO
            TraHangDTO traHang = new TraHangDTO();
            traHang.setMaTraHang(maTraHang);
            traHang.setMaPX(maPX);
            traHang.setMaNV(maNV);
            traHang.setMaKH(maKH);
            traHang.setNgayTra(ngayTra);
            traHang.setLyDoTra(lyDoTra);
            traHang.setTongTienHoanTra(tongTienHoanTra);
            traHang.setTrangThai(trangThai);

            // Thêm vào cơ sở dữ liệu
            try {
                if (daoTraHang.kiemTraMaTraHangTonTai(maTraHang)) {
                    JOptionPane.showMessageDialog(dialog, "Mã phiếu trả đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                daoTraHang.themTraHang(traHang);

                // Cập nhật bảng hiển thị
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        traHang.getMaTraHang(),
                        traHang.getMaPX(),
                        traHang.getMaNV(),
                        traHang.getMaKH(),
                        traHang.getNgayTra(),
                        traHang.getLyDoTra(),
                        traHang.getTongTienHoanTra(),
                        traHang.getTrangThai()
                });

                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm phiếu trả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Phím tắt


        dialog.setVisible(true);
    }


    private void exportToExcel() {
        ExTraHang.exportTraHangToExcel("E:/DanhSachTraHang.xlsx");
    }


    private JScrollPane createTable() {
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Columns
        String[] columns = {"Mã trả hàng", "Mã phiếu xuất","Nhân viên", "Khách hàng" ,"Ngày trả", "Lý do","Tổng tiền hoàn trả", "Tình trạng"};

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
            TraHangRepo repo=new DaoTraHang();
            List<TraHangDTO> danhSach = repo.layDanhSachTraHang();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (TraHangDTO th : danhSach) {
                model.addRow(new Object[]{
                        th.getMaTraHang(),
                        th.getMaPX(),
                        th.getMaNV(),
                        th.getMaKH(),
                        th.getNgayTra(),
                        th.getLyDoTra(),
                        th.getTongTienHoanTra(),
                        th.getTrangThai()
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

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã SP", "Tên sp", "Số lượng", "Tình trạng", "Hạn sử dụng", "Giá nhập", "Giá xuất"});
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

        // Sự kiện nút làm mới
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cbbFilter.setSelectedIndex(0); // Reset về "Tất cả"
            table.setRowSorter(null);
        });

        // Gắn sự kiện tìm kiếm khi gõ vào ô tìm kiếm
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
            // Nếu chọn "Tất cả", lọc tất cả các cột
            columnIndices = new int[]{0, 1, 2, 3, 4, 5, 6};
        } else {
            // Dùng switch để xác định cột cần lọc dựa vào lựa chọn của người dùng
            int columnIndex = switch (selectedFilter) {
                case "Mã SP" -> 0;  // Cột "Mã SP" có chỉ số 0
                case "Tên sp" -> 1;  // Cột "Tên sp" có chỉ số 1
                case "Số lượng" -> 2;  // Cột "Số lượng" có chỉ số 2
                case "Tình trạng" -> 3;  // Cột "Tình trạng" có chỉ số 3
                case "Hạn sử dụng" -> 4;  // Cột "Hạn sử dụng" có chỉ số 4
                case "Giá nhập" -> 5;  // Cột "Giá" có chỉ số 5
                case "Giá xuất" -> 6;  // Cột "Giá" có chỉ số 5

                default -> 0;  // Mặc định lọc theo cột "Mã SP"
            };
            columnIndices = new int[]{columnIndex}; // Lọc theo cột đã chọn
        }

        // Cài đặt bộ lọc với regex không phân biệt chữ hoa chữ thường
        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf); // Áp dụng bộ lọc
    }

    private void showTraHangDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một trả hàng để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String maTraHang = table.getValueAt(modelRow, 0).toString();
        String maPX = table.getValueAt(modelRow, 1).toString();
        String maNV = table.getValueAt(modelRow, 2).toString();
        String maKH = table.getValueAt(modelRow, 3).toString();
        String ngayTra = table.getValueAt(modelRow, 4).toString();
        String lyDoTra = table.getValueAt(modelRow, 5).toString();
        String tongTienHoanTra = table.getValueAt(modelRow, 6).toString();
        String trangThai = table.getValueAt(modelRow, 7).toString();

        TraHangDTO traHang = new TraHangDTO(maTraHang, maPX, maNV, maKH, ngayTra, lyDoTra, Float.parseFloat(tongTienHoanTra), trangThai);
        ChiTietTraHang detailDialog = new ChiTietTraHang(traHang);
        detailDialog.setVisible(true);
    }

    private void loadData() {
        DaoTraHang daoTraHang = new DaoTraHang();
        exportEntries = daoTraHang.layDanhSachTraHang();
        if (exportEntries == null) {
            exportEntries = new ArrayList<>();
        }
        System.out.println("Loaded " + exportEntries.size() + " TraHang records.");
    }

















}
