package DTO;

import Config.Session;

public class Account {
    private String MaNV;
    private String username;
    private String password;
    private int role; // 1: admin, 0: user

    // Constructor
    public Account() {
    }

    public Account(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
        saveToSession();
    }

    public Account(String maNV, int role, String username, String password) {
        this.MaNV = maNV;
        this.username = username;
        this.password = password;
        this.role = role;
        // Không gọi saveToSession
    }

    // Phương thức lưu vào Session (chỉ gọi khi đăng nhập)
    public void saveToSession() {
        System.out.println("Saving to session: username=" + this.username + ", role=" + this.role);
        Session.setUsername(this.username);
        Session.setRole(this.role);
    }

    // Getter và Setter
    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        this.MaNV = maNV;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        // Không gọi saveToSession
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
        // Không gọi saveToSession
    }
}