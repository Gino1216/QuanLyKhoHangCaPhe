package DuAn.controller;

import DuAn.dao.NhaCungCapDAO;
import DuAn.model.NhaCungCap;
import DuAn.view.QuanLyCuaHangView;
import javax.swing.*;
import java.util.List;

public class NhaCungCapController {
    private QuanLyCuaHangView view;
    private NhaCungCapDAO nhaCungCapModel;

    public NhaCungCapController(QuanLyCuaHangView view, NhaCungCapDAO nhaCungCapModel) {
        this.view = view;
        this.nhaCungCapModel = nhaCungCapModel;
    }

    public void hienThiNhaCungCap() {
        view.hienThiBang(new String[]{"Mã NCC", "Tên NCC", "SĐT", "Địa Chỉ"});
        List<NhaCungCap> danhSachNCC = nhaCungCapModel.getNhaCungCapData();

        for (NhaCungCap ncc : danhSachNCC) {
            Object[] row = {
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNCC(),
                    ncc.getSdt(),
                    ncc.getDiaChi()
            };
            view.themDuLieuVaoBang(row);
        }
    }

    public void themNhaCungCap() {
        JTextField maNCC = new JTextField();
        JTextField tenNCC = new JTextField();
        JTextField sdt = new JTextField();
        JTextField diaChi = new JTextField();

        Object[] fields = {"Mã NCC:", maNCC, "Tên NCC:", tenNCC, "SĐT:", sdt, "Địa Chỉ:", diaChi};

        int option = JOptionPane.showConfirmDialog(view, fields, "Thêm Nhà Cung Cấp", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            nhaCungCapModel.addNhaCungCap(
                    maNCC.getText().trim(),
                    tenNCC.getText().trim(),
                    sdt.getText().trim(),
                    diaChi.getText().trim()
            );
            hienThiNhaCungCap();
        }
    }

    public void xoaNhaCungCap(String maNCC) {
        int option = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhà cung cấp này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            nhaCungCapModel.deleteNhaCungCap(maNCC);
            hienThiNhaCungCap();
        }
    }

    public void capNhatNhaCungCap(String maNCC, String tenNCC, String sdt, String diaChi) {
        JLabel maNCCLabel = new JLabel(maNCC);
        JTextField tenNCCField = new JTextField(tenNCC);
        JTextField sdtField = new JTextField(sdt);
        JTextField diaChiField = new JTextField(diaChi);

        Object[] fields = {
                "Mã NCC:", maNCCLabel,
                "Tên NCC:", tenNCCField,
                "SĐT:", sdtField,
                "Địa Chỉ:", diaChiField
        };

        int option = JOptionPane.showConfirmDialog(view, fields, "Cập nhật Nhà Cung Cấp", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            nhaCungCapModel.updateNhaCungCap(
                    maNCC,
                    tenNCCField.getText().trim(),
                    sdtField.getText().trim(),
                    diaChiField.getText().trim()
            );
            hienThiNhaCungCap();
        }
    }
    public void timKiemNhaCungCap(String keyword) {
        List<NhaCungCap> danhSachNCC = nhaCungCapModel.searchNhaCungCap(keyword);

        // Xóa dữ liệu cũ trong bảng
        view.xoaTatCaDuLieuTrongBang();

        // Hiển thị tiêu đề cột
        view.hienThiBang(new String[]{"Mã NCC", "Tên NCC", "Địa Chỉ", "Số Điện Thoại"});

        // Kiểm tra nếu danh sách rỗng
        if (danhSachNCC.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không tìm thấy nhà cung cấp nào với mã: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Thêm dữ liệu vào bảng
            for (NhaCungCap ncc : danhSachNCC) {
                Object[] row = {
                        ncc.getMaNhaCungCap(),
                        ncc.getTenNCC(),
                        ncc.getDiaChi(),
                        ncc.getSdt()
                };
                view.themDuLieuVaoBang(row);
            }
        }
    }
}
