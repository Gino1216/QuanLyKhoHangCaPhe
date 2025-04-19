package Repository;

import DTO.PhanQuyenDTO;
import java.util.List;

public interface PhanQuyenRepo {

    List<PhanQuyenDTO> layDanhSachQuyen();

    void themQuyen(PhanQuyenDTO quyen);

    boolean suaQuyen(PhanQuyenDTO quyen);

    boolean xoaQuyen(int maQuyen);

    List<PhanQuyenDTO> timKiemQuyen(String keyword);

    boolean kiemTraMaQuyenTonTai(int maQuyen);
}
