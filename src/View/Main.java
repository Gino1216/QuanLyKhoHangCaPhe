package View;

import DTO.Account;
import Gui.IconUtils;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    private JPanel contentPanel;
    private static Account account;
    private JButton[] menuButtons;

    private final Color lightBackground = new Color(245, 245, 245);
    private final Color menuItemColor = new Color(50, 50, 50);
    private final Color hoverColor = new Color(220, 220, 220);
    private final Color activeColor = new Color(0, 168, 150);
    private final Color logoutColor = new Color(230, 60, 60);
    private final Color contentBgColor = Color.WHITE;
    private final Color separatorColor = new Color(200, 200, 200);

    public Main(Account account) {
        setTitle("☕ Coffee");
        setSize(1280, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set app icon
        try {
            setIconImage(new ImageIcon("icon/coffee-icon.png").getImage());
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        Main.account = account;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(lightBackground);

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(contentBgColor);
        add(contentPanel, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);

        // Set first view as Dashboard
        if (menuButtons != null && menuButtons.length > 0) {
            menuButtons[0].doClick();
        }

        applyModernStyle();
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(lightBackground);
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sidebar.setPreferredSize(new Dimension(300, getHeight()));

        // Header with logo
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(lightBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel logoLabel = new JLabel("☕");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setForeground(new Color(200, 160, 100));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("COFFEE MANAGER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
        sidebar.add(headerPanel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(separatorColor);
        sidebar.add(separator);

        // Menu items with centered layout
        String[] menuItems = {
            "Dashboard", "Sản phẩm", "Phiếu nhập", "Phiếu xuất",
            "Khách hàng", "Nhà cung cấp", "Nhân viên",
            "Tài khoản", "Phân quyền", "Ca làm"
        };

        String[] icons = {
            "icon/home.svg",
            "icon/product.svg",
            "icon/import.svg",
            "icon/export.svg",
            "icon/customer.svg",
            "icon/supplier.svg",
            "icon/staff.svg",
            "icon/account.svg",
            "icon/permission.svg",
            "icon/statistical.svg"
        };

        menuButtons = new JButton[menuItems.length];

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());  // Use GridBagLayout for centering
        menuPanel.setBackground(lightBackground);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;  // Stack buttons vertically
        gbc.anchor = GridBagConstraints.CENTER;    // Center the buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Đảm bảo nút chiếm toàn bộ chiều rộng
        gbc.insets = new Insets(5, 0, 5, 0);      // Khoảng cách giữa các nút

        for (int i = 0; i < menuItems.length; i++) {
            menuButtons[i] = createMenuItem(menuItems[i], icons[i], menuItemColor, hoverColor);
            menuButtons[i].setPreferredSize(new Dimension(260, 50)); // Kích thước cố định cho nút
            menuButtons[i].setMaximumSize(new Dimension(260, 50));
            menuButtons[i].setMinimumSize(new Dimension(260, 50));
            menuButtons[i].setHorizontalAlignment(SwingConstants.LEFT); // Căn trái
            final String item = menuItems[i];
            menuButtons[i].addActionListener(e -> {
                switchPanel(item);
                setActiveButton((JButton) e.getSource(), activeColor);
            });

            menuPanel.add(menuButtons[i], gbc);
        }

        sidebar.add(menuPanel);
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(lightBackground);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 25, 20));

        JButton logoutBtn = createMenuItem("Đăng Xuất", "icon/log_out.svg", logoutColor, logoutColor.darker());
        logoutBtn.setPreferredSize(new Dimension(260, 50)); // Kích thước cố định, đồng bộ với các nút khác
        logoutBtn.setMaximumSize(new Dimension(260, 50));
        logoutBtn.setMinimumSize(new Dimension(260, 50));
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái
        logoutBtn.addActionListener(e -> showLogoutConfirmation());

        logoutPanel.add(logoutBtn);
        sidebar.add(logoutPanel);

        return sidebar;
    }

    private JButton createMenuItem(String text, String svgIconPath, Color textColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getClientProperty("active") == Boolean.TRUE) {
                    g2.setColor(activeColor);
                } else if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(lightBackground);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };

        // Load SVG icon for the button
        if (svgIconPath != null) {
            ImageIcon icon = IconUtils.loadSVGIcon(svgIconPath, 24, 24); // Tùy chỉnh kích thước icon
            button.setIcon(icon);
        }

        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(textColor);
        button.setBackground(lightBackground);
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(Cursor.getDefaultCursor());
                button.repaint();
            }
        });

        return button;
    }

    private void switchPanel(String panelName) {
        contentPanel.removeAll();
        JPanel newPanel = null;

        switch (panelName) {
            case "Dashboard":
                newPanel = new DashBoard();
                break;
            case "Sản phẩm":
                newPanel = new Sanpham();
                break;
            case "Phiếu nhập":
                newPanel = new PhieuNhap();
                break;
            case "Phiếu xuất":
                newPanel = new PhieuXuat();
                break;
            case "Khách hàng":
                newPanel = new KhachHang();
                break;
            case "Nhà cung cấp":
                newPanel = new NhaCungCap();
                break;
            case "Nhân viên":
                newPanel = new NhanVien();
                break;
            case "Tài khoản":
                newPanel = new TaiKhoan();
                break;
            case "Phân quyền":
                newPanel = new PhanQuyen();
                break;
            case "Ca làm":
                newPanel = new CaLam();
                break;
            // Add other cases as needed
        }

        if (newPanel != null) {
            contentPanel.add(newPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    private void setActiveButton(JButton activeButton, Color activeColor) {
        for (JButton btn : menuButtons) {
            btn.putClientProperty("active", Boolean.FALSE);
            btn.repaint();
        }
        activeButton.putClientProperty("active", Boolean.TRUE);
        activeButton.repaint();
    }

    private void showLogoutConfirmation() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Đăng Xuất", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBackground(new Color(100, 100, 100));
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel statusLabel = new JLabel("Chào mừng bạn đến với hệ thống quản lý cà phê!");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel);

        return statusBar;
    }

    private void applyModernStyle() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
