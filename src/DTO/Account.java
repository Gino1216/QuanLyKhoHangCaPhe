/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author hjepr
 */

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
        saveToSession(); // Lưu tự động vào Session
    }

    public Account(String maNV, int role, String username, String password) {
        this.MaNV = maNV;
        this.username = username;
        this.password = password;
        this.role = role;
        saveToSession(); // Lưu tự động vào Session
    }

    // Phương thức lưu tự động vào Session
    private void saveToSession() {
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
        saveToSession(); // Cập nhật Session khi thay đổi username
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
        saveToSession(); // Cập nhật Session khi thay đổi role
    }
}