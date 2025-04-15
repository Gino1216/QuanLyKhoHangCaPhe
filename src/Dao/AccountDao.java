///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Dao;
//
//import Config.Mysql;
//import DTO.Account;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// *
// * @author hjepr
// */
//public class AccountDao{
//    public ArrayList<Account> getListAccount() {
//        ArrayList<Account> list = new ArrayList<>();
//        String sql = "SELECT * FROM TaiKhoan WHERE TinhTrang <> 'Đã xóa'";
//        try {
//            Statement st = Mysql.getConnection().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                list.add(new Account(
//                    rs.getString("MaNV"),
//                    rs.getString("MaQuyen"),
//                    rs.getString("TenDangNhap"),
//                    rs.getString("MatKhau"),
//                    rs.getString("TinhTrang")
//                ));
//            }
//            rs.close();
//            st.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public boolean insertAccount(String maNV, String maQuyen, String tenDangNhap, String matKhau, String tinhTrang) {
//        String sql = "INSERT INTO TaiKhoan (MaNV, MaQuyen, TenDangNhap, MatKhau, TinhTrang) VALUES (?, ?, ?, ?, ?)";
//        try {
//            PreparedStatement ps = Mysql.getConnection().prepareStatement(sql);
//            ps.setString(1, maNV);
//            ps.setString(2, maQuyen);
//            ps.setString(3, tenDangNhap);
//            ps.setString(4, matKhau);
//            ps.setString(5, tinhTrang);
//            return ps.executeUpdate() == 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean updateAccount(Account ac) {
//        String sql = "UPDATE TaiKhoan SET MaQuyen = ?, TenDangNhap = ?, MatKhau = ?, TinhTrang = ? WHERE MaNV = ?";
//        try {
//            PreparedStatement ps = Mysql.getConnection().prepareStatement(sql);
//            ps.setString(1, ac.getMaQuyen());
//            ps.setString(2, ac.getTenDangNhap());
//            ps.setString(3, ac.getMatKhau());
//            ps.setString(4, ac.getTinhTrang());
//            ps.setString(5, ac.getMaNV());
//            return ps.executeUpdate() == 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean deleteAccount(String maNV) {
//        String sql = "UPDATE TaiKhoan SET TinhTrang = 'Đã xóa' WHERE MaNV = ?";
//        try {
//            PreparedStatement ps = Mysql.getConnection().prepareStatement(sql);
//            ps.setString(1, maNV);
//            return ps.executeUpdate() == 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean checkAccount(String tenDangNhap, String matKhau) {
//        String sql = "SELECT * FROM user WHERE username = ? AND password = ? ";
//        try {
//            PreparedStatement ps = Mysql.getConnection().prepareStatement(sql);
//            ps.setString(1, tenDangNhap);
//            ps.setString(2, matKhau);
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}