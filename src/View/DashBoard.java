package View;

import Dao.DaoPhieuNhap;
import Dao.DaoPhieuXuat;
import Dao.DaoSP;
import Dao.DaoTraHang;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import javax.swing.*;

public class DashBoard extends JPanel {

    private Color backgroundColor = new Color(240, 247, 250); // Light background
    private Color textColor = new Color(52, 73, 94); // Dark text for contrast
    private Color cardBackground = Color.WHITE; // White background for cards
    private Color borderColor = new Color(200, 200, 200); // Light gray for borders

    public DashBoard() {
        // Apply FlatLaf theme
        FlatLightLaf.setup();

        setLayout(new BorderLayout(20, 20));
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tiêu đề với icon
        JLabel title = new JLabel(" Dashboard - Thống kê tổng quan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textColor);
        // Load the PNG icon using direct file path
        try {
            ImageIcon icon = new ImageIcon("icon/home.svg");
            // Resize the icon to 24x24 pixels for better fit
            Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            title.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
            // Fallback to text if the icon fails to load
            title.setText("🏠 Dashboard - Thống kê tổng quan");
        }
        title.setIconTextGap(8); // Space between icon and text
        add(title, BorderLayout.NORTH);

        // Panel tổng quan
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        summaryPanel.setOpaque(false);

        DaoPhieuXuat daoPhieuXuat =new DaoPhieuXuat();
        DaoPhieuNhap daoPhieuNhap =new DaoPhieuNhap();
        DaoTraHang daoTraHang =new DaoTraHang();
        System.out.println(daoPhieuXuat.tinhTongTienPXDaDuyet());
        System.out.println(daoPhieuNhap.tinhTongTienPNDaDuyet());
        System.out.println(daoTraHang.tinhTongTienHoanTraDaDuyet());

        float Tien = daoPhieuXuat.tinhTongTienPXDaDuyet() - daoPhieuNhap.tinhTongTienPNDaDuyet() - daoTraHang.tinhTongTienHoanTraDaDuyet();

        summaryPanel.add(createSummaryCard("Doanh Thu", String.format("%.1f VND", Tien), new Color(0, 168, 150)));
        int countPN =daoPhieuNhap.demSoLuongPNchuaDuyet();
        summaryPanel.add(createSummaryCard("Số đơn nhập chưa duyệt", String.valueOf(countPN)+" đơn", new Color(255, 193, 7)));


        int count = daoPhieuXuat.demSoLuongPXChuaDuyet();
        summaryPanel.add(createSummaryCard("Số đơn xuất chưa duyệt", String.valueOf(count)+ " đơn", new Color(100, 181, 246)));

        add(summaryPanel, BorderLayout.CENTER);

        // Panel biểu đồ
    }

    private JPanel createSummaryCard(String title, String value, Color valueColor) {
        JPanel card = new JPanel();
        card.setBackground(cardBackground);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 120));
        // Add subtle shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 50)), // Shadow
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(120, 120, 120)); // Light gray for title

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(valueColor);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.SOUTH);

        return card;
    }


}