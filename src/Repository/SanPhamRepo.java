package Repository;

import DTO.SanPhamDTO;

import java.util.List;

public interface SanPhamRepo {

    // Thêm sản phẩm
    void themSanPham(SanPhamDTO sp);

    // Sửa sản phẩm theo mã
    boolean suaSanPham(SanPhamDTO sp);

    // Xoá sản phẩm theo mã
    boolean xoaSanPham(String maSP);

    // Tìm kiếm sản phẩm theo mã hoặc tên
    List<SanPhamDTO> timKiemSanPham(String keyword);

    // Lấy toàn bộ danh sách sản phẩm
    List<SanPhamDTO> layDanhSachSanPham();

    boolean kiemTraMaSPTonTai(String kew);
}
