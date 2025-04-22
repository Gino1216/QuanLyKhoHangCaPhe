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

    // Kiểm tra mã sản phẩm tồn tại
    boolean kiemTraMaSPTonTai(String kew);

    // Xuất danh sách sản phẩm ra file Excel
    void xuatExcel(String filePath);

    int demSoLuongSanPham();

}