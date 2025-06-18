package DTO;

public class PhanQuyenDTO {
    private int MaQuyen;
    private String NoiDung;


    public PhanQuyenDTO(){

    }

    public PhanQuyenDTO(int maQuyen, String noiDung) {
        MaQuyen = maQuyen;
        NoiDung = noiDung;
    }

    public int getMaQuyen() {
        return MaQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        MaQuyen = maQuyen;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
