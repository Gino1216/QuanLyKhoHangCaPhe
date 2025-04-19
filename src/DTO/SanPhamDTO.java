package DTO;

public class SanPhamDTO {
    private String MaSP;
    private String TenSP;
    private int SoLuong;
    private String TinhTrang;
    private String HanSD;
    private float GiaNhap;
    private float GiaXuat;
    // Constructor không tham số
    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSP, String tenSP, int soLuong, String tinhTrang, String hanSD, float giaNhap, float giaXuat) {
        MaSP = maSP;
        TenSP = tenSP;
        SoLuong = soLuong;
        TinhTrang = tinhTrang;
        HanSD = hanSD;
        GiaNhap = giaNhap;
        GiaXuat = giaXuat;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getHanSD() {
        return HanSD;
    }

    public void setHanSD(String hanSD) {
        HanSD = hanSD;
    }

    public float getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        GiaNhap = giaNhap;
    }

    public float getGiaXuat() {
        return GiaXuat;
    }

    public void setGiaXuat(float giaXuat) {
        GiaXuat = giaXuat;
    }
}
