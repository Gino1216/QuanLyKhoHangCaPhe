package View;

import Config.Session;
import DTO.Account;
import Dao.DaoAccount;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginMain extends JFrame {

    private JTextField userText;
    private JPasswordField passField;

    // Các hằng số dùng để cấu hình kiểu dáng của giao diện
    private static final Color COFFEE_BROWN = new Color(186, 110, 64); // Màu nâu cà phê
    private static final Color COFFEE_DARK = new Color(135, 83, 40); // Màu nâu tối cà phê
    private static final Color WHITE = new Color(255, 255, 255); // Màu trắng
    private static final Color LIGHT_GRAY = new Color(200, 200, 200); // Màu xám nhạt
    private static final Color DARK_GRAY = new Color(50, 50, 50); // Màu xám tối
    private static final Color Yellow = new Color(153,102,0); // màu vàng
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 36); // Phông chữ tiêu đề
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 18); // Phông chữ phụ
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14); // Phông chữ nhãn
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16); // Phông chữ nút bấm
    private static final Dimension FIELD_SIZE = new Dimension(320, 45); // Kích thước của ô nhập liệu

// Khởi tạo cửa sổ JFrame chính cho màn hình đăng nhập
    public LoginMain() {
        initializeLookAndFeel(); // Cài đặt giao diện cho toàn bộ ứng dụng
        initializeFrame(); // Cài đặt cửa sổ chính
    }

// Cài đặt giao diện của ứng dụng
    private void initializeLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Cài đặt Look and Feel là Nimbus
        } catch (Exception e) {
            e.printStackTrace(); // Nếu gặp lỗi, in thông báo lỗi ra console
        }
    }

// Cài đặt các thuộc tính của JFrame (cửa sổ chính)
    private void initializeFrame() {
        setTitle("Coffee Shop Management System"); // Tiêu đề của cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng ứng dụng khi cửa sổ được đóng
        setSize(1146, 700); // Kích thước của cửa sổ
        setLocationRelativeTo(null); // Căn giữa cửa sổ trên màn hình
        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        setUndecorated(true); // Ẩn thanh tiêu đề của cửa sổ
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Bo góc cửa sổ

        // Panel nền cho giao diện
        JPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel cho form đăng nhập
        LoginFormPanel loginPanel = new LoginFormPanel();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        backgroundPanel.add(loginPanel, gbc);

        add(backgroundPanel); // Thêm backgroundPanel vào cửa sổ
        setVisible(true); // Hiển thị cửa sổ
    }

// Panel nền cho giao diện, có hình nền tùy chỉnh
    private class BackgroundPanel extends JPanel {

        private Image backgroundImage; // Hình nền

        public BackgroundPanel() {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/imgs/head.jpg")); // Tải hình nền từ thư mục resources
                backgroundImage = icon.getImage(); // Lấy đối tượng hình ảnh
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi nếu có sự cố khi tải hình nền
            }
        }

        // Vẽ hình nền lên panel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Tính toán tỷ lệ kích thước hình ảnh và panel
                int imgWidth = backgroundImage.getWidth(this);
                int imgHeight = backgroundImage.getHeight(this);
                double imgAspect = (double) imgWidth / imgHeight;
                double panelAspect = (double) getWidth() / getHeight();

                int drawWidth, drawHeight;
                if (panelAspect > imgAspect) {
                    drawWidth = getWidth();
                    drawHeight = (int) (getWidth() / imgAspect);
                } else {
                    drawHeight = getHeight();
                    drawWidth = (int) (getHeight() * imgAspect);
                }

                // Vẽ hình nền sao cho căn giữa
                int x = (getWidth() - drawWidth) / 2;
                int y = (getHeight() - drawHeight) / 2;
                g2d.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);
            }
        }
    }

// Panel chứa form đăng nhập
    private class LoginFormPanel extends JPanel {

        public LoginFormPanel() {
            setBorder(new EmptyBorder(60, 80, 60, 80)); // Đặt khoảng cách bên trong panel
            setOpaque(false); // Panel không có nền
            setLayout(new BorderLayout()); // Sử dụng layout BorderLayout

            // Nút đóng cửa sổ
            JButton closeButton = createCloseButton();
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel chứa nút đóng ở góc trên bên phải
            topPanel.setOpaque(false);
            topPanel.add(closeButton);
            add(topPanel, BorderLayout.NORTH);

            // Form đăng nhập (được căn giữa)
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout để xếp theo chiều dọc
            formPanel.setOpaque(false);
            formPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa

            // Tiêu đề và phụ đề
            JLabel titleLabel = new JLabel("Chào Mừng Bạn", SwingConstants.CENTER); // Tiêu đề
            titleLabel.setFont(TITLE_FONT);
            titleLabel.setForeground(Yellow);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitleLabel = new JLabel("Đăng nhập vào tài khoản của bạn", SwingConstants.CENTER); // Phụ đề
            subtitleLabel.setFont(SUBTITLE_FONT);
            subtitleLabel.setForeground(Yellow);
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            subtitleLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0)); // Khoảng cách giữa các phần tử

            // Các trường nhập liệu
            userText = (JTextField) createInputField("Username", "Nhập Tên Đăng Nhập");
            passField = (JPasswordField) createInputField("Password", "Nhập Mật Khẩu");

            // Checkbox "Remember me"
            JCheckBox rememberCheck = new JCheckBox("Remember me");
            rememberCheck.setFont(LABEL_FONT);
            rememberCheck.setForeground(LIGHT_GRAY);
            rememberCheck.setOpaque(false);
            rememberCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
            rememberCheck.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 0));

            // Nút đăng nhập với hiệu ứng
            JButton loginButton = new JButton("SIGN IN");
            loginButton.setFont(BUTTON_FONT);
            loginButton.setForeground(WHITE);
            loginButton.setBackground(COFFEE_DARK);
            loginButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
            loginButton.setFocusPainted(false); // Loại bỏ viền khi chọn nút
            loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginButton.setMaximumSize(new Dimension(320, 50));

            // Hiệu ứng khi di chuột vào nút đăng nhập
            loginButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    loginButton.setBackground(COFFEE_BROWN); // Thay đổi màu nền khi chuột di vào
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    loginButton.setBackground(COFFEE_DARK); // Khôi phục màu nền khi chuột ra ngoài
                }
            });

            // Xử lý sự kiện khi nhấn nút đăng nhập
            loginButton.addActionListener(e -> performLogin());

            // Liên kết "Forgot Password"
            JLabel forgotLabel = new JLabel("Forgot Password?");
            forgotLabel.setFont(LABEL_FONT);
            forgotLabel.setForeground(new Color(0, 102, 204)); // Màu xanh dương cho liên kết
            forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            forgotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            forgotLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            forgotLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(LoginMain.this,
                            "Please contact your system administrator to reset your password.",
                            "Forgot Password",
                            JOptionPane.INFORMATION_MESSAGE); // Thông báo khi nhấn vào "Forgot Password"
                }
            });

            // Thêm các phần tử vào form
            formPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống để căn giữa
            formPanel.add(titleLabel);
            formPanel.add(subtitleLabel);
            formPanel.add(createFieldPanel("Tên Đăng Nhập", userText));
            formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            formPanel.add(createFieldPanel("Mật Khẩu", passField));
            formPanel.add(loginButton);
            formPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống ở cuối

            // Bọc formPanel trong một panel trung tâm
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(formPanel);
            add(centerPanel, BorderLayout.CENTER);
        }

        // Tạo nút đóng cửa sổ
        private JButton createCloseButton() {
            JButton closeButton = new JButton();
            closeButton.setText("X"); // Mặc định là chữ "X" trên nút đóng
            closeButton.setFont(new Font("Arial", Font.PLAIN, 18));
            closeButton.setContentAreaFilled(false); // Không tô màu nền
            closeButton.setBorderPainted(false); // Không vẽ viền
            closeButton.setFocusPainted(false); // Không có hiệu ứng chọn
            closeButton.setForeground(WHITE);
            closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hiển thị con trỏ chuột dạng bàn tay
            closeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    closeButton.setForeground(LIGHT_GRAY); // Thay đổi màu chữ khi chuột vào
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    closeButton.setForeground(WHITE); // Khôi phục màu chữ khi chuột ra ngoài
                }
            });
            closeButton.addActionListener(e -> System.exit(0)); // Đóng ứng dụng khi nhấn nút đóng
            return closeButton;
        }

        // Tạo các trường nhập liệu (tên người dùng, mật khẩu)
        private JComponent createInputField(String labelText, String placeholder) {
            JComponent field = labelText.equals("Password") ? new JPasswordField() : new JTextField(); // Tạo trường cho mật khẩu hoặc tên người dùng
            if (field instanceof JPasswordField) {
                ((JPasswordField) field).setEchoChar('•'); // Đặt dấu chấm cho mật khẩu
            }

            field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            field.setBorder(BorderFactory.createEmptyBorder()); // Không viền cho trường nhập liệu
            field.setBackground(WHITE); // Màu nền trắng
            field.setForeground(LIGHT_GRAY); // Màu chữ xám nhạt

            if (field instanceof JTextField) {
                ((JTextField) field).setText(placeholder); // Đặt placeholder cho trường nhập liệu
                field.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (((JTextField) field).getText().equals(placeholder)) {
                            ((JTextField) field).setText(""); // Xóa placeholder khi trường được chọn
                            field.setForeground(DARK_GRAY); // Thay đổi màu chữ khi nhập
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (((JTextField) field).getText().isEmpty()) {
                            ((JTextField) field).setText(placeholder); // Trả lại placeholder nếu trường không có giá trị
                            field.setForeground(LIGHT_GRAY); // Đặt lại màu chữ placeholder
                        }
                    }
                });
            }

            return field;
        }

        // Tạo panel chứa các trường nhập liệu
        private JPanel createFieldPanel(String labelText, JComponent field) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setOpaque(false);
            panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa panel

            JLabel label = new JLabel(labelText, SwingConstants.LEFT);
            label.setFont(LABEL_FONT);
            label.setForeground(LIGHT_GRAY);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel fieldPanel = new JPanel(new BorderLayout());
            fieldPanel.setPreferredSize(FIELD_SIZE);
            fieldPanel.setMaximumSize(FIELD_SIZE);
            fieldPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            fieldPanel.setBackground(WHITE);
            fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            field.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    fieldPanel.setBackground(new Color(245, 245, 245));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    fieldPanel.setBackground(WHITE);
                }
            });

            field.setBorder(null);
            field.setOpaque(false);
            fieldPanel.add(field, BorderLayout.CENTER);

            panel.add(label);
            panel.add(fieldPanel);
            panel.add(Box.createVerticalStrut(10));

            return panel;
        }

        private void performLogin() {
            String username = userText.getText().trim();
            char[] password = passField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                   showError( "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
                    return;
                }

                DaoAccount daoAccount =new DaoAccount();
                // Xác thực tài khoản
                Account loggedInAccount = daoAccount.checkLogin(username, String.valueOf(password));
                System.out.println("Role: " + Session.getRole());

                if (loggedInAccount == null) {
                    showError( "Tên đăng nhập hoặc mật khẩu không đúng!");
                    return;
                }

                // Kiểm tra mã vai trò
                int role = loggedInAccount.getRole();
                if (role < 1 || role > 3) { // Chỉ cho phép vai trò 1, 2, 3
                    showError("Vai trò không hợp lệ! Vui lòng liên hệ quản trị viên.");
                    return;
                }

                // Đăng nhập thành công, mở cửa sổ chính
                dispose();
                new Main(loggedInAccount);
            }

        private void showError(String message) {
            JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginMain());
    }
}
