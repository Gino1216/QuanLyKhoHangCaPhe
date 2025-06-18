package DTO;

public class TraHangDTO {
    private String maTraHang;
    private String maPX;
    private String maNV;
    private String maKH;
    private String ngayTra;
    private String lyDoTra;
    private float tongTienHoanTra;
    private String trangThai;

    // Constructor
    public TraHangDTO() {
    }

    public TraHangDTO(String maTraHang, String maPX, String maNV, String maKH, String ngayTra, String lyDoTra, float tongTienHoanTra, String trangThai) {
        this.maTraHang = maTraHang;
        this.maPX = maPX;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayTra = ngayTra;
        this.lyDoTra = lyDoTra;
        this.tongTienHoanTra = tongTienHoanTra;
        this.trangThai = trangThai;
    }

    public String getMaTraHang() {
        return maTraHang;
    }

    public void setMaTraHang(String maTraHang) {
        this.maTraHang = maTraHang;
    }

    public String getMaPX() {
        return maPX;
    }

    public void setMaPX(String maPX) {
        this.maPX = maPX;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getLyDoTra() {
        return lyDoTra;
    }

    public void setLyDoTra(String lyDoTra) {
        this.lyDoTra = lyDoTra;
    }

    public float getTongTienHoanTra() {
        return tongTienHoanTra;
    }

    public void setTongTienHoanTra(float tongTienHoanTra) {
        this.tongTienHoanTra = tongTienHoanTra;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
