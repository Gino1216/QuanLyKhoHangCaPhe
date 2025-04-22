package Repository;

import DTO.PXDTO;

import java.util.List;

public interface PhieuXuatRepo {

    void themPhieuXuat(PXDTO phieuXuat);

    boolean suaPhieuXuat(PXDTO phieuXuat);

    boolean xoaPhieuXuat(String MaPX);


    List<PXDTO> layDanhSachPhieuXuat();

    boolean kiemTraMaPXTonTai(String MaPX);

//    int demSoLuongDonHang();

    List<PXDTO>layDanhSachPhieuXuatTheoMaPXVaTrangThaiHoanThanh(String maPX);

    void themMaPxVaTT(String maPX);


}
