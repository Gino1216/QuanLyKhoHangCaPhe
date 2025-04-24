package Repository;

import DTO.SanPhamDTO;
import java.util.List;

public interface SanPhamRepo {
    void themSanPham(SanPhamDTO sp);
    boolean suaSanPham(SanPhamDTO sp);
    boolean xoaSanPham(String maSP);
    List<SanPhamDTO> timKiemSanPham(String keyword);
    List<SanPhamDTO> layDanhSachSanPham();
    boolean kiemTraMaSPTonTai(String maSP);
    void xuatExcel(String filePath);
    int demSoLuongSanPham();
}