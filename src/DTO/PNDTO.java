package DTO;

public class PNDTO {
    private String MaPN;
    private String MaNCC;
    private String MaNV;
    private String NgayNhap;
    private float TongTien;
    private String TrangThai;


    public  PNDTO(){

    }
    public PNDTO(String maPN, String maNCC, String maNV, String ngayNhap, float tongTien, String trangThai) {
        MaPN = maPN;
        MaNCC = maNCC;
        MaNV = maNV;
        NgayNhap = ngayNhap;
        TongTien = tongTien;
        TrangThai = trangThai;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String maPN) {
        MaPN = maPN;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float tongTien) {
        TongTien = tongTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
