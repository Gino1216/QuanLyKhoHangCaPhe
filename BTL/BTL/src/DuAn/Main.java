package DuAn;

import DuAn.controller.*;
import DuAn.dao.MayTinhDAO;
import DuAn.dao.NguoiDungDAO;
import DuAn.dao.NhaCungCapDAO;
import DuAn.dao.PhieuNhapDAO;
import DuAn.view.DangNhapView;
import DuAn.view.QuanLyCuaHangView;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo giao diện đăng nhập
        DangNhapView dangNhapView = new DangNhapView();
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

        // Khởi tạo giao diện chính và các DAO
        QuanLyCuaHangView quanLyCuaHangView = new QuanLyCuaHangView();
        MayTinhDAO mayTinhDAO = new MayTinhDAO();
        NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
        PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();

        // Khởi tạo controller cho ứng dụng chính
        QuanLyCuaHangController quanLyCuaHangController = new QuanLyCuaHangController(
                quanLyCuaHangView, mayTinhDAO, nhaCungCapDAO, phieuNhapDAO
        );

        MayTinhController mayTinhController = new MayTinhController(quanLyCuaHangView, mayTinhDAO);
        NhaCungCapController nhaCungCapController =new NhaCungCapController(quanLyCuaHangView,nhaCungCapDAO);
        PhieuNhapController phieuNhapController=new PhieuNhapController(quanLyCuaHangView,phieuNhapDAO);

        // Gán controller cho QuanLyCuaHangView
        quanLyCuaHangView.setController(mayTinhController); // Gán MayTinhController
        quanLyCuaHangView.setNhaCungCap(nhaCungCapController);
        quanLyCuaHangView.setPhieuNhap(phieuNhapController);

        // Khởi tạo controller đăng nhập và truyền MayTinhController vào
        DangNhapController dangNhapController = new DangNhapController(
                dangNhapView, nguoiDungDAO, quanLyCuaHangView, mayTinhController
        );

        // Hiển thị giao diện đăng nhập
        dangNhapView.hienThi();
    }
}