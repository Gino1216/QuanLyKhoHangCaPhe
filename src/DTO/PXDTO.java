package DTO;

import java.time.LocalDateTime;

public class PXDTO {
    private String MaPX;
    private String maKhachHang;
    private String maNhanVien;
    private LocalDateTime thoiGian;
    private float tongTien;
    private String trangThai;

    public PXDTO(){

    }

    public PXDTO( String MaPX, String maKhachHang, String maNhanVien, LocalDateTime thoiGian, float tongTien, String trangThai) {
        this.MaPX = MaPX;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }



    public String getMaPX() {
        return MaPX;
    }

    public void setMaPX(String MaPX) {
        this.MaPX = MaPX;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
