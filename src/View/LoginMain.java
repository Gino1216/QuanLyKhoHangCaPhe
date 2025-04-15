package View;

import DTO.Account;
import Dao.DaoUse;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 760);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create form panel with semi-transparent white background
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);

        // Add padding panel to center the form
        JPanel paddingPanel = new JPanel();
        paddingPanel.setOpaque(false);
        paddingPanel.setLayout(new GridBagLayout());

        // Create the form content
        // Create the form content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                new EmptyBorder(50, 50, 30, 30) // Correct syntax for EmptyBorder
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Correct syntax for Insets
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title label
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(230, 230, 230));
        gbc.gridy = 1;
        contentPanel.add(subtitleLabel, gbc);

        // Username label and field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        contentPanel.add(userLabel, gbc);

        JTextField userText = new JTextField(15);
        userText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        userText.setOpaque(false);
        userText.setForeground(Color.WHITE);
        userText.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        contentPanel.add(userText, gbc);

        // Password label and field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        passField.setOpaque(false);
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        contentPanel.add(passField, gbc);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setForeground(new Color(41, 128, 185));
        loginButton.setBackground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(150, 40));

        // Button hover effects
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(230, 230, 230));
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
                // Lấy thông tin từ các trường nhập liệu
                String username = userText.getText();
                char[] password = passField.getPassword();

                // Biến checkLogin dùng để kiểm tra trạng thái đăng nhập
                boolean checkLogin = false;
                Account loggedInAccount = null; // To store the logged-in account information

                // Kiểm tra nếu username hoặc password trống
                if (username.isEmpty() || password.length == 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Sử dụng phương thức checkLogin từ DaoUse để kiểm tra tài khoản và mật khẩu
                    loggedInAccount = DaoUse.checkLogin(username, String.valueOf(password));

                    // Nếu đăng nhập không thành công
                    if (loggedInAccount == null) {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        checkLogin = true;
                    }
                }

                // Nếu đăng nhập thành công, thay đổi giao diện
                if (checkLogin) {
                    frame.dispose();

                    new Main(loggedInAccount); // Mở cửa sổ chính với tài khoản đã đăng nhập
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);

        contentPanel.add(loginButton, gbc);

        // Add content panel to padding panel
        paddingPanel.add(contentPanel);

        // Add components to main panel
        mainPanel.add(paddingPanel, BorderLayout.CENTER);

        // Add footer
        JLabel footerLabel = new JLabel("© 2023 ");

        footerLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(
                new Color(200, 200, 200));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);

        frame.setVisible(
                true);
    }
}
