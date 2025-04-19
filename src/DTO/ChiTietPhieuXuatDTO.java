package DTO;

public class ChiTietPhieuXuatDTO {
    private String MaPX;
    private String MaSP;
    private String SanPham;
    private int SoLuong;
    private float DonGia;
    private float ThanhTien;

    public ChiTietPhieuXuatDTO(){

    }

    public ChiTietPhieuXuatDTO(String maPX, String maSP, String sanPham, int soLuong, float donGia, float thanhTien) {
        MaPX = maPX;
        MaSP = maSP;
        SanPham = sanPham;
        SoLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
    }

    public String getMaPX() {
        return MaPX;
    }

    public void setMaPX(String maPX) {
        MaPX = maPX;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getSanPham() {
        return SanPham;
    }

    public void setSanPham(String sanPham) {
        SanPham = sanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float donGia) {
        DonGia = donGia;
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(float thanhTien) {
        ThanhTien = thanhTien;
    }
}
