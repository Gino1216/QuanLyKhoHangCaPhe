package Repository;

import DTO.TraHangDTO;

import java.util.List;

public interface TraHangRepo {

    boolean themTraHang(TraHangDTO traHang);


    List<TraHangDTO> layDanhSachTraHang();

    void xuatExcel(String filePath);

    boolean kiemTraMaTraHangTonTai(String maTraHang);

    String sinhMaTraHang();

}
