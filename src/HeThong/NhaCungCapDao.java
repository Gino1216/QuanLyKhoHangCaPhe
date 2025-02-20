/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeThong;

import Model.NhaCungCap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author hjepr
 */
public class NhaCungCapDao {
     public static NhaCungCapDao getInstance() {
        return new NhaCungCapDao();
    }
     
      public ArrayList<NhaCungCap> selectAll() {
        ArrayList<NhaCungCap> ketQua = new ArrayList<NhaCungCap>();
        try {
            java.sql.Connection con = MySQL.getConnection();
            String sql = "SELECT * FROM NhaCungCap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt");
                String diaChi = rs.getString("diaChi");
                NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
                ketQua.add(ncc);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
}
