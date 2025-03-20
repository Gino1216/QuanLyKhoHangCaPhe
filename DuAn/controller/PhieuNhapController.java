package DuAn.controller;

import DuAn.dao.PhieuNhapDAO;
import DuAn.model.PhieuNhap;
import DuAn.view.QuanLyCuaHangView;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PhieuNhapController {
    private QuanLyCuaHangView view;
    private PhieuNhapDAO phieuNhapModel;

    public PhieuNhapController(QuanLyCuaHangView view, PhieuNhapDAO phieuNhapModel) {
        this.view = view;
        this.phieuNhapModel = phieuNhapModel;
    }

    // Hiển thị danh sách phiếu nhập lên bảng
    public void hienThiPhieuNhap() {

        // Hiển thị tiêu đề cột
        view.hienThiBang(new String[]{"Mã Phiếu", "Thời gian Tạo", "Người Tạo", "Mã NCC", "Tổng Tiền"});

        // Lấy danh sách phiếu nhập từ DAO
        List<PhieuNhap> danhSachPhieuNhap = phieuNhapModel.getPhieuNhapData();

        // Thêm dữ liệu vào bảng
        for (PhieuNhap phieuNhap : danhSachPhieuNhap) {
            Object[] row = {
                    phieuNhap.getMaPhieu(),
                    phieuNhap.getThoiGianTao(),
                    phieuNhap.getNguoiTao(),
                    phieuNhap.getMaNhaCungCap(),
                    phieuNhap.getTongTien()
            };
            view.themDuLieuVaoBang(row);
        }
    }

    // Thêm phiếu nhập mới
    public void themPhieuNhap() {
        JTextField maPhieuField = new JTextField();
        JTextField nguoiTaoField = new JTextField();
        JTextField maNCCField = new JTextField();
        JTextField tongTienField = new JTextField();

        Object[] fields = {
                "Mã Phiếu:", maPhieuField,
                "Người Tạo:", nguoiTaoField,
                "Mã NCC:", maNCCField,
                "Tổng Tiền:", tongTienField
        };

        int option = JOptionPane.showConfirmDialog(view, fields, "Thêm Phiếu Nhập", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String maPhieu = maPhieuField.getText().trim();
            String nguoiTao = nguoiTaoField.getText().trim();
            String maNCC = maNCCField.getText().trim();
            double tongTien = Double.parseDouble(tongTienField.getText().trim());

            // Kiểm tra mã nhà cung cấp có tồn tại không
            if (!phieuNhapModel.kiemTraMaNCC(maNCC)) {
                JOptionPane.showMessageDialog(view, "Mã nhà cung cấp không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm phiếu nhập vào CSDL
            phieuNhapModel.addPhieuNhap(maPhieu, nguoiTao, maNCC, tongTien);

            // Cập nhật lại bảng hiển thị
            hienThiPhieuNhap();
        }
    }

    // Xóa phiếu nhập
    public void xoaPhieuNhap(String maPhieu) {
        int option = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa phiếu nhập này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            phieuNhapModel.deletePhieuNhap(maPhieu);
            hienThiPhieuNhap();
        }
    }

    // Cập nhật thông tin phiếu nhập
    public void capNhatPhieuNhap(String maPhieu, String nguoiTao, String maNCC, double tongTien) {
        // Mã phiếu và người tạo không thể sửa, hiển thị bằng JLabel
        JLabel maPhieuLabel = new JLabel(maPhieu);
        JLabel nguoiTaoLabel = new JLabel(nguoiTao); // Sử dụng JLabel thay vì JTextField

        // Các trường có thể chỉnh sửa
        JTextField maNCCField = new JTextField(maNCC);
        JTextField tongTienField = new JTextField(String.valueOf(tongTien));

        // Tạo giao diện hộp thoại
        Object[] fields = {
                "Mã Phiếu:", maPhieuLabel,
                "Người Tạo:", nguoiTaoLabel, // Hiển thị người tạo dưới dạng nhãn
                "Mã NCC:", maNCCField,
                "Tổng Tiền:", tongTienField
        };

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(view, fields, "Cập nhật Phiếu Nhập", JOptionPane.OK_CANCEL_OPTION);

        // Xử lý khi người dùng chọn OK
        if (option == JOptionPane.OK_OPTION) {
            // Lấy giá trị từ các trường có thể chỉnh sửa
            String newMaNCC = maNCCField.getText().trim();
            double newTongTien = Double.parseDouble(tongTienField.getText().trim());

            // Kiểm tra mã nhà cung cấp mới có tồn tại không
            if (!phieuNhapModel.kiemTraMaNCC(newMaNCC)) {
                JOptionPane.showMessageDialog(view, "Mã nhà cung cấp không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật phiếu nhập
            phieuNhapModel.updatePhieuNhap(maPhieu, nguoiTao, newMaNCC, newTongTien);

            // Cập nhật lại bảng hiển thị
            hienThiPhieuNhap();
        }
    }
    public void timKiemPhieuNhap(String keyword) {
        List<PhieuNhap> danhSachPhieuNhap = phieuNhapModel.searchPhieuNhap(keyword);

        // Xóa dữ liệu cũ trong bảng
        view.xoaTatCaDuLieuTrongBang();

        // Hiển thị tiêu đề cột
        view.hienThiBang(new String[]{"Mã Phiếu", "Thời Gian Tạo", "Người Tạo", "Mã Nhà Cung Cấp", "Tổng Tiền"});

        // Kiểm tra nếu danh sách rỗng
        if (danhSachPhieuNhap.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không tìm thấy phiếu nhập nào với mã: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Thêm dữ liệu vào bảng
            for (PhieuNhap phieuNhap : danhSachPhieuNhap) {
                Object[] row = {
                        phieuNhap.getMaPhieu(),
                        phieuNhap.getThoiGianTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                        phieuNhap.getNguoiTao(),
                        phieuNhap.getMaNhaCungCap(),
                        phieuNhap.getTongTien()
                };
                view.themDuLieuVaoBang(row);
            }
        }
    }
}