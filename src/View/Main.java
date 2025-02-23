/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import HeThong.MySQL;
import java.sql.Connection;

/**
 *
 * @author hjepr
 */
public class Main {
    
  public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = MySQL.getConnection();
            if (connection != null) {
                System.out.println("Kết nối thành công đến cơ sở dữ liệu!");
                MySQL.printInfo(connection);
            } else {
                System.err.println("Kết nối thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MySQL.closeConnection(connection);
        }
    }
}
