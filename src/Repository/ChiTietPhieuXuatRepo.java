package Repository;

import DTO.ChiTietPhieuXuatDTO;
import java.util.List;

public interface ChiTietPhieuXuatRepo {

    void themChiTietPhieuXuat(ChiTietPhieuXuatDTO ct);

    boolean suaChiTietPhieuXuat(ChiTietPhieuXuatDTO ct);

    boolean xoaChiTietPhieuXuat(String maPX, String maSP);

    List<ChiTietPhieuXuatDTO> layChiTietPhieuXuatTheoMaPX(String maPX);

    boolean kiemTraTonTai(String maPX, String maSP);
}
