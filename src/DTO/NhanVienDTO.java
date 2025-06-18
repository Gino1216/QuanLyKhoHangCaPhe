package DTO;

public class NhanVienDTO {
    private String MaNV;
    private String HoTen;
    private String GioiTinh;
    private String NgaySinh;
    private String SoDT;
    private String Email;
    private String DiaChi;
    private String ChucVu;


    public NhanVienDTO(){

    }

    public NhanVienDTO(String maNV, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email, String diaChi, String chucVu) {
        MaNV = maNV;
        HoTen = hoTen;
        GioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        SoDT = soDT;
        Email = email;
        DiaChi = diaChi;
        ChucVu = chucVu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
    }
}
