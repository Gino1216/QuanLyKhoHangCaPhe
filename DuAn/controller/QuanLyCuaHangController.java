package DuAn.controller;

import DuAn.dao.MayTinhDAO;
import DuAn.dao.NhaCungCapDAO;
import DuAn.dao.PhieuNhapDAO;
import DuAn.view.QuanLyCuaHangView;

import javax.swing.*;

public class QuanLyCuaHangController {
    private QuanLyCuaHangView view;
    private MayTinhController mayTinhController;
    private NhaCungCapController nhaCungCapController;
    private PhieuNhapController phieuNhapController;

    public QuanLyCuaHangController(QuanLyCuaHangView view, MayTinhDAO mayTinhModel, NhaCungCapDAO nhaCungCapModel, PhieuNhapDAO phieuNhapModel) {
        this.view = view;
        this.mayTinhController = new MayTinhController(view, mayTinhModel);
        this.nhaCungCapController = new NhaCungCapController(view, nhaCungCapModel);
        this.phieuNhapController = new PhieuNhapController(view, phieuNhapModel);

        // Gán sự kiện cho các nút
        this.view.getBtnMayTinh().addActionListener(e -> {
            view.setTrangHienTai("maytinh");
            mayTinhController.hienThiMayTinh();
        });

        this.view.getBtnNhaCungCap().addActionListener(e -> {
            view.setTrangHienTai("nhacungcap");
            nhaCungCapController.hienThiNhaCungCap();
        });

        this.view.getBtnPhieuNhap().addActionListener(e -> {
            view.setTrangHienTai("phieunhap");
            phieuNhapController.hienThiPhieuNhap();
        });

        this.view.getBtnThem().addActionListener(e -> themDuLieu());
        this.view.getBtnXoa().addActionListener(e -> xoaDuLieu());
        this.view.getBtnCapNhat().addActionListener(e -> capNhatDuLieu());
    }

    private void themDuLieu() {
        String trangHienTai = view.getTrangHienTai();
        switch (trangHienTai) {
            case "maytinh":
                mayTinhController.themMayTinh();
                break;
            case "nhacungcap":
                nhaCungCapController.themNhaCungCap();
                break;
            case "phieunhap":
                phieuNhapController.themPhieuNhap();
                break;
            default:
                JOptionPane.showMessageDialog(view, "Chưa hỗ trợ thêm dữ liệu cho trang này!");
        }
    }

    private void xoaDuLieu() {
        String trangHienTai = view.getTrangHienTai();
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            switch (trangHienTai) {
                case "maytinh":
                    String maMay = (String) view.getTable().getValueAt(selectedRow, 0);
                    mayTinhController.xoaMayTinh(maMay);
                    break;
                case "nhacungcap":
                    String maNCC = (String) view.getTable().getValueAt(selectedRow, 0);
                    nhaCungCapController.xoaNhaCungCap(maNCC);
                    break;
                case "phieunhap":
                    String maPhieu = (String) view.getTable().getValueAt(selectedRow, 0);
                    phieuNhapController.xoaPhieuNhap(maPhieu);
                    break;
                default:
                    JOptionPane.showMessageDialog(view, "Chưa hỗ trợ xóa dữ liệu cho trang này!");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một hàng để xóa!");
        }
    }

    private void capNhatDuLieu() {
        String trangHienTai = view.getTrangHienTai();
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            switch (trangHienTai) {
                case "maytinh":
                    String maMay = (String) view.getTable().getValueAt(selectedRow, 0);
                    String tenMay = (String) view.getTable().getValueAt(selectedRow, 1);
                    int soLuong = (int) view.getTable().getValueAt(selectedRow, 2);
                    double gia = (double) view.getTable().getValueAt(selectedRow, 3);
                    mayTinhController.capNhatMayTinh(maMay, tenMay, soLuong, gia);
                    break;
                case "nhacungcap":
                    String maNCC = (String) view.getTable().getValueAt(selectedRow, 0);
                    String tenNCC = (String) view.getTable().getValueAt(selectedRow, 1);
                    String sdt = (String) view.getTable().getValueAt(selectedRow, 2);
                    String diaChi = (String) view.getTable().getValueAt(selectedRow, 3);
                    nhaCungCapController.capNhatNhaCungCap(maNCC, tenNCC, sdt, diaChi);
                    break;
                case "phieunhap":
                    String maPhieu = (String) view.getTable().getValueAt(selectedRow, 0);
                    String nguoiTao = (String) view.getTable().getValueAt(selectedRow, 2);
                    String maNCCPhieu = (String) view.getTable().getValueAt(selectedRow, 3);
                    double tongTien = (double) view.getTable().getValueAt(selectedRow, 4);
                    phieuNhapController.capNhatPhieuNhap(maPhieu, nguoiTao, maNCCPhieu, tongTien);
                    break;
                default:
                    JOptionPane.showMessageDialog(view, "Chưa hỗ trợ cập nhật dữ liệu cho trang này!");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một hàng để cập nhật!");
        }
    }
}