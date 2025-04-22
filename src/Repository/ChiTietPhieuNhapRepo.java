package Repository;

import DTO.ChiTietPhieuNhapDTO;

import java.util.List;

public interface ChiTietPhieuNhapRepo {

    void themChiTietPhieuNhap(ChiTietPhieuNhapDTO ct);

    boolean suaChiTietPhieuNhap(ChiTietPhieuNhapDTO  ct);

    boolean xoaChiTietPhieuNhap(String maPN, String maSP);

    List<ChiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaPN(String maPN);

    boolean kiemTraTonTai(String maPN, String maSP);
}
