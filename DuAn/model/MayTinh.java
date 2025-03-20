package DuAn.model;

public class MayTinh {
    private  String maMay;
    private String tenMay;
    private int soLuong;
    private double gia;

    public MayTinh(){

    }

    public MayTinh(String maMay, String tenMay, int soLuong, double gia) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "MayTinh{" +
                "maMay='" + maMay + '\'' +
                ", tenMay='" + tenMay + '\'' +
                ", soLuong=" + soLuong +
                ", gia=" + gia +
                '}';
    }
}