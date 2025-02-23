/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JDialog;

/**
 *
 * @author Thang-1007
 */

import HeThong.ProductDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
public class ProductDialog extends JDialog {
    private JTextField txtMaMay, txtTenMay, txtSoLuong, txtDonGia, txtBoXuLy, txtRAM, txtBoNho, txtLoaiMay;
    private DefaultTableModel model;
    private boolean isUpdateMode = false; // Chế độ Thêm (false) / Cập nhật (true)
    private String maMayCu; // Mã máy cũ dùng để cập nhật

    // Constructor cho thêm sản phẩm
    public ProductDialog(JFrame parent, DefaultTableModel tableModel) {
        this(parent, tableModel, null);
    }

    // Constructor cho cập nhật sản phẩm
    public ProductDialog(JFrame parent, DefaultTableModel tableModel, Object[] productData) {
        super(parent, (productData == null) ? "Thêm sản phẩm" : "Cập nhật sản phẩm", true);
        this.model = tableModel;
        this.isUpdateMode = (productData != null); // Nếu có dữ liệu, tức là đang cập nhật
        setSize(400, 300);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(parent);

        // Tạo các thành phần nhập liệu
        add(new JLabel("Mã máy:"));
        txtMaMay = new JTextField();
        add(txtMaMay);

        add(new JLabel("Tên máy:"));
        txtTenMay = new JTextField();
        add(txtTenMay);

        add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        add(txtSoLuong);

        add(new JLabel("Đơn giá:"));
        txtDonGia = new JTextField();
        add(txtDonGia);

        add(new JLabel("Bộ xử lý:"));
        txtBoXuLy = new JTextField();
        add(txtBoXuLy);

        add(new JLabel("RAM:"));
        txtRAM = new JTextField();
        add(txtRAM);

        add(new JLabel("Bộ nhớ:"));
        txtBoNho = new JTextField();
        add(txtBoNho);

        add(new JLabel("Loại máy:"));
        txtLoaiMay = new JTextField();
        add(txtLoaiMay);

        JButton btnXacNhan = new JButton(isUpdateMode ? "Cập nhật" : "Thêm");
        JButton btnHuy = new JButton("Hủy");

        add(btnXacNhan);
        add(btnHuy);

        // Nếu ở chế độ cập nhật, điền dữ liệu vào các ô nhập
        if (isUpdateMode) {
            maMayCu = productData[0].toString();
            txtMaMay.setText(maMayCu);
            txtTenMay.setText(productData[1].toString());
            txtSoLuong.setText(productData[2].toString());
            txtDonGia.setText(productData[3].toString());
            txtBoXuLy.setText(productData[4].toString());
            txtRAM.setText(productData[5].toString());
            txtBoNho.setText(productData[6].toString());
            txtLoaiMay.setText(productData[7].toString());

            txtMaMay.setEnabled(false); // Không cho sửa mã máy
        }

        // Xử lý khi nhấn "Xác nhận"
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isUpdateMode) {
                    updateProductInDatabase();
                } else {
                    addProductToDatabase();
                }
            }
        });

        // Xử lý khi nhấn "Hủy"
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ
            }
        });

        setVisible(true);
    }

    private void addProductToDatabase() {
        String maMay = txtMaMay.getText();
        String tenMay = txtTenMay.getText();
        String soLuongStr = txtSoLuong.getText();
        String donGiaStr = txtDonGia.getText();
        String boXuLy = txtBoXuLy.getText();
        String ram = txtRAM.getText();
        String boNho = txtBoNho.getText();
        String loaiMay = txtLoaiMay.getText();

        if (!maMay.isEmpty() && !tenMay.isEmpty() && !soLuongStr.isEmpty() && !donGiaStr.isEmpty()
                && !boXuLy.isEmpty() && !ram.isEmpty() && !boNho.isEmpty() && !loaiMay.isEmpty()) {
            try {
                int soLuong = Integer.parseInt(soLuongStr);
                double donGia = Double.parseDouble(donGiaStr);

                boolean inserted = ProductDao.insertProduct(maMay, tenMay, soLuong, donGia, boXuLy, ram, boNho, loaiMay);
                if (inserted) {
                    model.addRow(new Object[]{maMay, tenMay, soLuong, donGia, boXuLy, ram, boNho, loaiMay});
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm vào database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProductInDatabase() {
        String tenMay = txtTenMay.getText();
        String soLuongStr = txtSoLuong.getText();
        String donGiaStr = txtDonGia.getText();
        String boXuLy = txtBoXuLy.getText();
        String ram = txtRAM.getText();
        String boNho = txtBoNho.getText();
        String loaiMay = txtLoaiMay.getText();

        if (!tenMay.isEmpty() && !soLuongStr.isEmpty() && !donGiaStr.isEmpty()
                && !boXuLy.isEmpty() && !ram.isEmpty() && !boNho.isEmpty() && !loaiMay.isEmpty()) {
            try {
                int soLuong = Integer.parseInt(soLuongStr);
                double donGia = Double.parseDouble(donGiaStr);

                ProductDao.updateProduct(maMayCu, tenMay, soLuong, donGia, boXuLy, ram, boNho, loaiMay);

                // Cập nhật lại bảng
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).toString().equals(maMayCu)) {
                        model.setValueAt(tenMay, i, 1);
                        model.setValueAt(soLuong, i, 2);
                        model.setValueAt(donGia, i, 3);
                        model.setValueAt(boXuLy, i, 4);
                        model.setValueAt(ram, i, 5);
                        model.setValueAt(boNho, i, 6);
                        model.setValueAt(loaiMay, i, 7);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
