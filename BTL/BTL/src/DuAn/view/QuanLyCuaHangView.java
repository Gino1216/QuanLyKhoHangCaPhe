package DuAn.view;

import DuAn.controller.MayTinhController;
import DuAn.controller.NhaCungCapController;
import DuAn.controller.PhieuNhapController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyCuaHangView extends JFrame {
    private JPanel sidebar, contentPanel, bottomNav;
    private JButton btnMayTinh, btnNhaCungCap, btnPhieuNhap, btnThem, btnXoa, btnCapNhat;
    private JTable table;
    private DefaultTableModel model;
    private String trangHienTai = "maytinh"; // Biến để lưu trang hiện tại
    private MayTinhController controller; // Tham chiếu đến controller
    private NhaCungCapController nhaCungCapController;
    private PhieuNhapController phieuNhapController;

    // Getter methods
    public JButton getBtnMayTinh() {
        return btnMayTinh;
    }

    public JButton getBtnNhaCungCap() {
        return btnNhaCungCap;
    }

    public JButton getBtnPhieuNhap() {
        return btnPhieuNhap;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnCapNhat() {
        return btnCapNhat;
    }

    public JTable getTable() {
        return table;
    }

    public String getTrangHienTai() {
        return trangHienTai;
    }

    public void setTrangHienTai(String trangHienTai) {
        this.trangHienTai = trangHienTai;
    }

    // Hiển thị giao diện
    public void hienThi() {
        this.setVisible(true);
    }

    // Gán controller
    public void setController(MayTinhController controller) {
        this.controller = controller;
        System.out.println("Controller đã được gán: " + (controller != null));
    }

    public void setNhaCungCap(NhaCungCapController nhaCungCapController){
        this.nhaCungCapController= nhaCungCapController;
    }
    public void setPhieuNhap(PhieuNhapController phieuNhapController){
        this.phieuNhapController=phieuNhapController;
    }

    // Constructor
    public QuanLyCuaHangView() {
        setTitle("Quản Lý Cửa Hàng");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        taoSidebar();
        taoContentPanel();
        taoBottomNav();
    }

    // Tạo thanh sidebar
    private void taoSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(3, 1));
        sidebar.setPreferredSize(new Dimension(150, 600));

        btnMayTinh = new JButton("Máy Tính");
        btnNhaCungCap = new JButton("Nhà Cung Cấp");
        btnPhieuNhap = new JButton("Phiếu Nhập");

        sidebar.add(btnMayTinh);
        sidebar.add(btnNhaCungCap);
        sidebar.add(btnPhieuNhap);

        add(sidebar, BorderLayout.WEST);
    }

    // Tạo panel chính
    private void taoContentPanel() {
        contentPanel = new JPanel(new BorderLayout());
        model = new DefaultTableModel();
        table = new JTable(model);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Xử lý sự kiện tìm kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();
            if (controller != null) {
                switch (trangHienTai) {
                    case "maytinh":
                        controller.timKiemMayTinh(keyword);
                        break;
                    case "nhacungcap":
                        nhaCungCapController.timKiemNhaCungCap(keyword); // Gọi controller của Nhà Cung Cấp
                        break;
                    case "phieunhap":
                        phieuNhapController.timKiemPhieuNhap(keyword);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Chức năng tìm kiếm chưa được hỗ trợ cho trang này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Controller chưa được khởi tạo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Tạo thanh bottom
    private void taoBottomNav() {
        bottomNav = new JPanel();
        bottomNav.setLayout(new FlowLayout(FlowLayout.CENTER));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập nhật");

        bottomNav.add(btnThem);
        bottomNav.add(btnXoa);
        bottomNav.add(btnCapNhat);

        add(bottomNav, BorderLayout.SOUTH);
    }

    // Hiển thị bảng với các cột
    public void hienThiBang(String[] columnNames) {
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        revalidate();
        repaint();
    }

    public void themDuLieuVaoBang(Object[] rowData) {
        model.addRow(rowData);
    }

    public void xoaTatCaDuLieuTrongBang() {
        model.setRowCount(0);
    }
}