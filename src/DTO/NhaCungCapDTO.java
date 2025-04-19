package DTO;

public class NhaCungCapDTO {
    private String MaNCC;
    private String TenNCC;
    private String SoDT;
    private String DiaChi;
    private String Email;
    private String TinhTrang;

    public NhaCungCapDTO(){

    }

    public NhaCungCapDTO(String maNCC, String tenNCC, String soDT, String diaChi, String email, String tinhTrang) {
        MaNCC = maNCC;
        TenNCC = tenNCC;
        SoDT = soDT;
        DiaChi = diaChi;
        Email = email;
        TinhTrang = tinhTrang;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }
}
