package DuAn.model;


import java.time.LocalDateTime;

public class PhieuNhap {
    private String maPhieu;
    private LocalDateTime thoiGianTao;
    private String nguoiTao;
    private String maNhaCungCap;
    private double tongTien;

    public PhieuNhap(){

    }

    public PhieuNhap(String maPhieu, LocalDateTime thoiGianTao, String nguoiTao, String maNhaCungCap, double tongTien) {
        this.maPhieu = maPhieu;
        this.thoiGianTao = thoiGianTao;
        this.nguoiTao = nguoiTao;
        this.maNhaCungCap = maNhaCungCap;
        this.tongTien = tongTien;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "PhieuNhap{" +
                "maPhieu='" + maPhieu + '\'' +
                ", thoiGianTao=" + thoiGianTao +
                ", nguoiTao='" + nguoiTao + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}
