package DuAn.controller;

import DuAn.dao.NguoiDungDAO;
import DuAn.model.NguoiDung;
import DuAn.view.DangNhapView;
import DuAn.view.QuanLyCuaHangView;

public class DangNhapController {
    private DangNhapView view;
    private NguoiDungDAO nguoiDungDAO;
    private QuanLyCuaHangView mainView;
    private MayTinhController mayTinhController; // Thêm biến MayTinhController

    // Thêm MayTinhController vào constructor
    public DangNhapController(DangNhapView view, NguoiDungDAO nguoiDungDAO, QuanLyCuaHangView mainView, MayTinhController mayTinhController) {
        this.view = view;
        this.nguoiDungDAO = nguoiDungDAO;
        this.mainView = mainView;
        this.mayTinhController = mayTinhController; // Khởi tạo MayTinhController

        // Gán sự kiện cho nút đăng nhập
        view.setLoginButtonListener(this);
    }

    public void dangNhap() {
        String username = view.getUsername();
        String password = view.getPassword();

        // Kiểm tra đăng nhập
        NguoiDung nguoiDung = nguoiDungDAO.kiemTraDangNhap(username, password);
        if (nguoiDung != null) {
            view.hienThiThongBao("Đăng nhập thành công!");
            view.dongCuaSo();
            mainView.hienThi(); // Hiển thị giao diện chính
            mayTinhController.hienThiMayTinh(); // Gọi phương thức hiển thị dữ liệu máy tính
        } else {
            view.hienThiThongBao("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }
}