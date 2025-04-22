package Repository;

import DTO.KhachHangDTO;

import java.util.List;

public interface KhachHangRepo {

    void themKhachHang(KhachHangDTO khachHang);

    boolean suaKhachHang(KhachHangDTO khachHang);

    boolean xoaKhachHang(String maKH);

    List<KhachHangDTO> timKiemKhachHang(String keyword);

    List<KhachHangDTO> layDanhSachKhachHang();

    boolean kiemTraMaKHTonTai(String maKH);

    void xuatExcel(String filePath);

}
