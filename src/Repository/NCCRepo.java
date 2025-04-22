package Repository;

import DTO.NhaCungCapDTO;

import java.util.List;

 public interface NCCRepo {

    void themNhaCungCap(NhaCungCapDTO ncc);

    boolean suaNhaCungCap(NhaCungCapDTO ncc);

    boolean xoaNhaCungCap(String maNCC);

    List<NhaCungCapDTO> timKiemNhaCungCap(String keyword);

    List<NhaCungCapDTO> layDanhSachNhaCungCap();

    boolean kiemTraMaNCCTonTai(String maNCC);

    void xuatExcel(String filePath);


 }
