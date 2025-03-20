package DuAn.view;

import DuAn.controller.DangNhapController;

import javax.swing.*;
import java.awt.*;

public class DangNhapView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public DangNhapView() {
        setTitle("Đăng Nhập");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel nhập thông tin
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên đăng nhập:"), gbc);


        gbc.gridx = 1;
        usernameField = new JTextField(20);
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        inputPanel.add(passwordField, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Panel nút đăng nhập
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        loginButton = new JButton("Đăng Nhập");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        buttonPanel.add(loginButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setLoginButtonListener(DangNhapController controller) {
        loginButton.addActionListener(e -> controller.dangNhap());
    }

    public void hienThiThongBao(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void dongCuaSo() {
        this.dispose();
    }

    public void hienThi() {
        this.setVisible(true);
    }
}