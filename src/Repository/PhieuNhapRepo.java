package Repository;

import DTO.PNDTO;

import java.util.List;

public interface PhieuNhapRepo {
    void themPhieuNhap(PNDTO PhieuNhAP);

    boolean suaPhieuNhap(PNDTO PhieuNhap);

    boolean xoaPhieuNhap(String MaPN);


    List<PNDTO> layDanhSachPhieuNhap();

    boolean kiemTraMaPNTonTai(String MaPX);


    List<PNDTO>layDanhSachPhieuNhapTheoMaPNVaTrangThaiHoanThanh(String maPX);

}
