package Repository;

import DTO.TraHangDTO;

import java.util.List;

public interface TraHangRepo {

    boolean themTraHang(TraHangDTO traHang);

    boolean suaTraHang(TraHangDTO traHang);

    boolean xoaTraHang(String maTraHang);

    TraHangDTO timTheoMa(String maTraHang);

    List<TraHangDTO> timKiemTraHang(String keyword);

    List<TraHangDTO> layDanhSachTraHang();

    void xuatExcel(String filePath);

    boolean kiemTraMaTraHangTonTai(String maTraHang);

    String sinhMaTraHang();

}
