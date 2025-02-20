/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeThong;

import Model.PC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hjepr
 */
public class PCDao implements DAOInterface<PC> {
     public static PCDao getInstance() {
        return new PCDao();
    }
     
     @Override
    public int insert(PC t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     @Override
    public int update(PC t) {
        int ketqua = 0;
        try {
            Connection con = MySQL.getConnection();
            String sql = "UPDATE MayTinh SET tenMay = ?,soLuong=?,gia=?,tenCpu=?,ram=?,xuatXu=?,cardManHinh=?,rom=?,trangThai=? WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTenMay());
            pst.setInt(2, t.getSoLuong());
            pst.setDouble(3, t.getGia());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getXuatXu());
            pst.setString(7, t.getCardManHinh());
            pst.setString(8, t.getRom());
            pst.setInt(9, t.getTrangThai());
            pst.setString(10, t.getMaMay());
            ketqua = pst.executeUpdate(sql);
            MySQL.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PCDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }

    @Override
    public int delete(PC t) {
        int ketQua = 0;
        try {
            Connection con = MySQL.getConnection();
            String sql = "DELETE FROM MayTinh WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            ketQua = pst.executeUpdate();

            MySQL.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<PC> selectAll() {
        ArrayList<PC> ketQua = new ArrayList<PC>();
        try {
            Connection con = MySQL.getConnection();
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                PC mt = new PC(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            MySQL.closeConnection(con);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public PC selectById(String t) {
        PC ketQua = null;
        try {
            Connection con = MySQL.getConnection();
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh WHERE maMay = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                ketQua = new PC(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
            }
            MySQL.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    public int updateSoLuong(String maMay, int soluong) {
        int ketQua = 0;
        try {
            Connection con = MySQL.getConnection();
            //String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, dungLuongPin, dungLuongPin, loaiMay, rom) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql = "UPDATE MayTinh SET soLuong=? WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, soluong);
            pst.setString(2, maMay);
            ketQua = pst.executeUpdate();
            MySQL.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public int deleteTrangThai(String maMay){
        int ketQua = 0;
        try {
            Connection con = MySQL.getConnection();
            //String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, dungLuongPin, dungLuongPin, loaiMay, rom) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql = "UPDATE MayTinh SET trangThai=0 WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maMay);
            ketQua = pst.executeUpdate();
            MySQL.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<PC> selectAllE() {
        ArrayList<PC> ketQua = new ArrayList<PC>();
        ArrayList<PC> ketQuaTonKho = new ArrayList<>();
        try {
            Connection con = MySQL.getConnection();
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                PC mt = new PC(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            for (PC mayTinh : ketQua) {
                if (mayTinh.getSoLuong() > 0) {
                    ketQuaTonKho.add(mayTinh);
                }
            }
            MySQL.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQuaTonKho;
    }
    
        public ArrayList<PC> selectAllExist() {
        ArrayList<PC> ketQua = new ArrayList<PC>();
        try {
            Connection con = MySQL.getConnection();
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh WHERE trangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                PC mt = new PC(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            MySQL.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
        
    public int getSl() {
        int soluong = 0;
        try {
            Connection con = MySQL.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE trangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                soluong++;
            }
            MySQL.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return soluong;
    }
}
