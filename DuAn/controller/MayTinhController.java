package DuAn.controller;

import DuAn.dao.MayTinhDAO;
import DuAn.model.MayTinh;
import DuAn.view.QuanLyCuaHangView;

import javax.swing.*;
import java.util.List;

public class MayTinhController {
    private QuanLyCuaHangView view;
    private MayTinhDAO mayTinhModel;

    public MayTinhController(QuanLyCuaHangView view, MayTinhDAO mayTinhModel) {
        this.view = view;
        this.mayTinhModel = mayTinhModel;
    }

    // Hiển thị dữ liệu máy tính lên bảng
    public void hienThiMayTinh() {
        // Xóa dữ liệu cũ trong bảng

        // Hiển thị tiêu đề cột
        view.hienThiBang(new String[]{"Mã Máy", "Tên Máy", "Số Lượng", "Giá"});

        // Lấy danh sách máy tính từ model
        List<MayTinh> danhSachMayTinh = mayTinhModel.getMayTinhData();

        // Thêm dữ liệu vào bảng
        for (MayTinh mayTinh : danhSachMayTinh) {
            Object[] row = {
                    mayTinh.getMaMay(),
                    mayTinh.getTenMay(),
                    mayTinh.getSoLuong(),
                    mayTinh.getGia()
            };
            view.themDuLieuVaoBang(row);
        }
    }

    // Thêm máy tính mới
    public void themMayTinh() {
        JTextField maMay = new JTextField();
        JTextField tenMay = new JTextField();
        JTextField soLuong = new JTextField();
        JTextField gia = new JTextField();

        Object[] fields = {
                "Mã Máy:", maMay,
                "Tên Máy:", tenMay,
                "Số Lượng:", soLuong,
                "Giá:", gia
        };

        int option = JOptionPane.showConfirmDialog(view, fields, "Thêm Máy Tính", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Tạo đối tượng MayTinh từ dữ liệu nhập vào
            MayTinh mayTinhMoi = new MayTinh(
                    maMay.getText(),
                    tenMay.getText(),
                    Integer.parseInt(soLuong.getText()),
                    Double.parseDouble(gia.getText())
            );

            // Thêm máy tính vào cơ sở dữ liệu
            mayTinhModel.addMayTinh(mayTinhMoi);

            // Cập nhật lại bảng hiển thị
            hienThiMayTinh();
        }
    }

    // Xóa máy tính
    public void xoaMayTinh(String maMay) {
        int option = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa máy tính này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Xóa máy tính từ cơ sở dữ liệu
            mayTinhModel.deleteMayTinh(maMay);

            // Cập nhật lại bảng hiển thị
            hienThiMayTinh();
        }
    }

    // Cập nhật thông tin máy tính
    public void capNhatMayTinh(String maMay, String tenMay, int soLuong, double gia) {
        JLabel maMayLabel = new JLabel(maMay);
        JTextField tenMayField = new JTextField(tenMay);
        JTextField soLuongField = new JTextField(String.valueOf(soLuong));
        JTextField giaField = new JTextField(String.valueOf(gia));

        Object[] fields = {
                "Mã Máy:", maMayLabel,
                "Tên Máy:", tenMayField,
                "Số Lượng:", soLuongField,
                "Giá:", giaField
        };

        int option = JOptionPane.showConfirmDialog(view, fields, "Cập nhật Máy Tính", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Tạo đối tượng MayTinh từ dữ liệu nhập vào
            MayTinh mayTinhCapNhat = new MayTinh(
                    maMay,
                    tenMayField.getText(),
                    Integer.parseInt(soLuongField.getText()),
                    Double.parseDouble(giaField.getText())
            );

            // Cập nhật máy tính trong cơ sở dữ liệu
            mayTinhModel.updateMayTinh(mayTinhCapNhat);

            // Cập nhật lại bảng hiển thị
            hienThiMayTinh();
        }
    }

    public void timKiemMayTinh(String keyword) {
        // Xóa dữ liệu cũ trong bảng
        view.xoaTatCaDuLieuTrongBang();

        // Hiển thị tiêu đề cột
        view.hienThiBang(new String[]{"Mã Máy", "Tên Máy", "Số Lượng", "Giá"});

        // Lấy danh sách máy tính từ model dựa trên từ khóa
        List<MayTinh> danhSachMayTinh = mayTinhModel.searchMayTinh(keyword);

        // Kiểm tra nếu danh sách rỗng
        if (danhSachMayTinh.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không tìm thấy máy tính nào với mã: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Thêm dữ liệu vào bảng
            for (MayTinh mayTinh : danhSachMayTinh) {
                Object[] row = {
                        mayTinh.getMaMay(),
                        mayTinh.getTenMay(),
                        mayTinh.getSoLuong(),
                        mayTinh.getGia()
                };
                view.themDuLieuVaoBang(row);
            }
        }
    }
}