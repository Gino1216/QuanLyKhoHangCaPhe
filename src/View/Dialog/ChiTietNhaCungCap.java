package View.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ChiTietNhaCungCap extends JDialog {

        private final Color backgroundColor = Color.WHITE;
        private final Color headerColor = new Color(59, 130, 246);
        private final Color buttonColor = new Color(59, 130, 246);
        private final Color cancelButtonColor = new Color(239, 68, 68);
        private final Color buttonHoverColor = new Color(100, 149, 237);
        private final Color cancelHoverColor = new Color(248, 113, 113);

        public ChiTietNhaCungCap(String id, String name, String address, String email, String phone) {
            FlatLightLaf.setup();
            setTitle("Chi Tiết Nhà Cung Cấp");
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(0, 10));

            // ======= Header =======
            JLabel lblHeader = new JLabel("CHI TIẾT NHÀ CUNG CẤP", JLabel.CENTER);
            lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblHeader.setForeground(Color.WHITE);
            lblHeader.setBackground(headerColor);
            lblHeader.setOpaque(true);
            lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            add(lblHeader, BorderLayout.NORTH);

            // ======= Info Panel =======
            JPanel infoPanel = new JPanel(new GridBagLayout());
            infoPanel.setBackground(backgroundColor);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(12, 12, 12, 12);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            String[] labels = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
            String[] values = {id, name, address, email, phone};

            for (int i = 0; i < labels.length; i++) {
                gbc.gridx = 0;
                gbc.gridy = i;
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                infoPanel.add(label, gbc);

                gbc.gridx = 1;
                JTextField textField = new JTextField(values[i]);
                textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textField.setEditable(false);
                textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                textField.setPreferredSize(new Dimension(300, 35));
                infoPanel.add(textField, gbc);
            }

            add(infoPanel, BorderLayout.CENTER);

            // ======= Button Panel =======
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonPanel.setBackground(backgroundColor);

            JButton btnExportPDF = createButton("Xuất file PDF", buttonColor, buttonHoverColor);
            btnExportPDF.addActionListener(e
                    -> JOptionPane.showMessageDialog(this, "Chức năng xuất file PDF chưa được triển khai!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE)
            );

            JButton btnCancel = createButton("Hủy bỏ", cancelButtonColor, cancelHoverColor);
            btnCancel.addActionListener(e -> dispose());

            buttonPanel.add(btnExportPDF);
            buttonPanel.add(btnCancel);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private JButton createButton(String text, Color baseColor, Color hoverColor) {
            JButton button = new JButton(text);
            button.setBackground(baseColor);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setPreferredSize(new Dimension(150, 40));
            button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(hoverColor);
                }

                public void mouseExited(MouseEvent evt) {
                    button.setBackground(baseColor);
                }
            });

            return button;
        }
    
}
