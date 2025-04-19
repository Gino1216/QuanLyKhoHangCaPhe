package DTO;

public class KhachHangDTO {
    private String MaKH;
    private String HoTen;
    private String DiaChi;
    private String NgayThamGia;
    private String Email;
    private String SoDT;

    public KhachHangDTO(){

    }

    public KhachHangDTO(String maKH, String hoTen, String diaChi, String ngayThamGia, String email, String soDT) {
        MaKH = maKH;
        HoTen = hoTen;
        DiaChi = diaChi;
        NgayThamGia = ngayThamGia;
        Email = email;
        SoDT = soDT;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgayThamGia() {
        return NgayThamGia;
    }

    public void setNgayThamGia(String ngayThamGia) {
        NgayThamGia = ngayThamGia;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }


}

