package Repository;

import DTO.NhanVienDTO;
import java.util.List;

public interface NhanVienRepo {

    List<String> getMaNVWithoutAccount(); // Thêm dòng này

    void themNhanVien(NhanVienDTO nv);

    boolean suaNhanVien(NhanVienDTO nv);

    boolean xoaNhanVien(String maNV);


    List<NhanVienDTO> layDanhSachNhanVien();

    boolean kiemTraMaNVTonTai(String maNV);


    void xuatExcel(String filePath);

}
