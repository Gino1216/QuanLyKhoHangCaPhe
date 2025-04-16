/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hjepr
 */
public class ChiTietCaLam extends JDialog {

    private Color backgroundColor = new Color(255, 255, 255);
    private Color headerColor = new Color(0, 102, 204);
    private Color buttonColor = new Color(0, 120, 215); // Màu xanh dương giống hình ảnh
    private Color cancelButtonColor = new Color(255, 69, 58); // Màu đỏ giống hình ảnh

    public ChiTietCaLam(String shiftId, String shiftName, String startTime, String endTime, String workDate, String status) {
        FlatLightLaf.setup();
        setTitle("Chi tiết ca làm việc");
        setSize(800, 300); // Kích thước giống hình ảnh
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 5));

        // Header
        JLabel lblHeader = new JLabel("THÔNG TIN CA LÀM VIỆC", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBackground(headerColor);
        lblHeader.setOpaque(true);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Info panel (Thông tin ca làm việc)
        JPanel infoPanel = new JPanel(new GridLayout(2, 6, 5, 5)); // 2 hàng, 6 cột (6 nhãn + 6 giá trị)
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Mã ca
        JLabel lblShiftId = new JLabel("Mã ca");
        lblShiftId.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblShiftId);

        JTextField txtShiftId = new JTextField(shiftId);
        txtShiftId.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtShiftId.setEditable(false);
        txtShiftId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtShiftId);

        // Tên ca
        JLabel lblShiftName = new JLabel("Tên ca");
        lblShiftName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblShiftName);

        JTextField txtShiftName = new JTextField(shiftName);
        txtShiftName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtShiftName.setEditable(false);
        txtShiftName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtShiftName);

        // Giờ bắt đầu
        JLabel lblStartTime = new JLabel("Giờ bắt đầu");
        lblStartTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblStartTime);

        JTextField txtStartTime = new JTextField(startTime);
        txtStartTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtStartTime.setEditable(false);
        txtStartTime.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtStartTime);

        // Giờ kết thúc
        JLabel lblEndTime = new JLabel("Giờ kết thúc");
        lblEndTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblEndTime);

        JTextField txtEndTime = new JTextField(endTime);
        txtEndTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEndTime.setEditable(false);
        txtEndTime.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtEndTime);

        // Ngày làm
        JLabel lblWorkDate = new JLabel("Ngày làm");
        lblWorkDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblWorkDate);

        JTextField txtWorkDate = new JTextField(workDate);
        txtWorkDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtWorkDate.setEditable(false);
        txtWorkDate.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtWorkDate);

        // Trạng thái
        JLabel lblStatus = new JLabel("Trạng thái");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblStatus);

        JTextField txtStatus = new JTextField(status);
        txtStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtStatus.setEditable(false);
        txtStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(txtStatus);

        contentPanel.add(infoPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton btnExportPDF = new JButton("Xuất file PDF");
        btnExportPDF.setBackground(buttonColor);
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportPDF.setPreferredSize(new Dimension(120, 30));
        btnExportPDF.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất file PDF chưa được triển khai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(cancelButtonColor);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setPreferredSize(new Dimension(120, 30));
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnExportPDF);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
