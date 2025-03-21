package DuAn.controller;

import DuAn.dao.NguoiDungDAO;
import DuAn.model.NguoiDung;
import DuAn.view.DangNhapView;
import DuAn.view.QuanLyCuaHangView;
import DuAn.down.BCrypt; // Import lớp Bryct từ package DuAn.down

public class DangNhapController {
    private DangNhapView view;
    private NguoiDungDAO nguoiDungDAO;
    private QuanLyCuaHangView mainView;
    private MayTinhController mayTinhController;

    public DangNhapController(DangNhapView view, NguoiDungDAO nguoiDungDAO, QuanLyCuaHangView mainView, MayTinhController mayTinhController) {
        this.view = view;
        this.nguoiDungDAO = nguoiDungDAO;
        this.mainView = mainView;
        this.mayTinhController = mayTinhController;

        // Gán sự kiện cho nút đăng nhập
        view.setLoginButtonListener(this);
    }

    public void dangNhap() {
        String username = view.getUsername();
        String password = view.getPassword();

        // Kiểm tra đăng nhập
        NguoiDung nguoiDung = nguoiDungDAO.kiemTraDangNhap(username);
        if (nguoiDung != null && BCrypt.checkpw(password, nguoiDung.getPassword())) {
            view.hienThiThongBao("Đăng nhập thành công!");
            view.dongCuaSo();
            mainView.hienThi(); // Hiển thị giao diện chính
            mayTinhController.hienThiMayTinh(); // Gọi phương thức hiển thị dữ liệu máy tính
        } else {
            view.hienThiThongBao("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

// dang nhap controller k dung Bryct
//    public void dangNhap() {
//        String username = view.getUsername();
//        String password = view.getPassword();
//
//        // Kiểm tra đăng nhập
//        NguoiDung nguoiDung = nguoiDungDAO.kiemTraDangNhap(username, password);
//        if (nguoiDung != null) {
//            view.hienThiThongBao("Đăng nhập thành công!");
//            view.dongCuaSo();
//            mainView.hienThi(); // Hiển thị giao diện chính
//            mayTinhController.hienThiMayTinh(); // Gọi phương thức hiển thị dữ liệu máy tính
//        } else {
//            view.hienThiThongBao("Tên đăng nhập hoặc mật khẩu không đúng!");
//        }
//    }


}