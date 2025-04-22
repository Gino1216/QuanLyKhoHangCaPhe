package View;

import Dao.DaoSP;
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

        summaryPanel.add(createSummaryCard("Doanh Thu Hôm Nay", "12,500,000đ", new Color(0, 168, 150)));

        summaryPanel.add(createSummaryCard("Số Đơn Bán", "28 Đơn", new Color(255, 193, 7)));


        DaoSP daoSP =new DaoSP();
        int count = daoSP.demSoLuongSanPham();
        summaryPanel.add(createSummaryCard("Số sản phẩm", String.valueOf(count)+ " sản phẩm", new Color(100, 181, 246)));

        add(summaryPanel, BorderLayout.CENTER);

        // Panel biểu đồ
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.SOUTH);
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

    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                // Gradient background for chart panel
                GradientPaint gp = new GradientPaint(0, 0, new Color(220, 240, 255), 0, h, Color.WHITE);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w - 1, h - 1, 15, 15);
            }
        };
        chartPanel.setOpaque(false);
        chartPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                "Biểu đồ Doanh Thu Tuần",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 16),
                textColor
        ));
        chartPanel.setPreferredSize(new Dimension(0, 250));
        chartPanel.setLayout(new BorderLayout());

        // Mock chart placeholder with a simple line graph outline
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(150, 150, 150));
                g2d.setStroke(new BasicStroke(2));

                // Draw axes
                g2d.drawLine(50, 150, 50, 50); // Y-axis
                g2d.drawLine(50, 150, 300, 150); // X-axis

                // Draw a simple line graph
                g2d.setColor(new Color(59, 130, 246));
                int[] xPoints = {50, 100, 150, 200, 250, 300};
                int[] yPoints = {150, 120, 80, 110, 90, 70};
                g2d.drawPolyline(xPoints, yPoints, xPoints.length);

                // Add labels
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                g2d.setColor(textColor);
                g2d.drawString("📈 Đang phát triển...", 50, 30);
            }
        };
        chartPlaceholder.setOpaque(false);
        chartPanel.add(chartPlaceholder, BorderLayout.CENTER);

        return chartPanel;
    }

}