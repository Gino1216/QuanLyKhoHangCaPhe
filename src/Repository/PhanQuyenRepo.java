package Repository;

import DTO.PhanQuyenDTO;
import java.util.List;

public interface PhanQuyenRepo {

    List<PhanQuyenDTO> layDanhSachQuyen();

    void themQuyen(PhanQuyenDTO quyen);

    boolean suaQuyen(PhanQuyenDTO quyen);

    boolean xoaQuyen(int maQuyen);


    boolean kiemTraMaQuyenTonTai(int maQuyen);

    void xuatExcel(String filePath);

}
