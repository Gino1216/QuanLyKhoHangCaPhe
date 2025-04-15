package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class HoaDonBan extends JPanel {

    private DefaultTableModel tableModelBan, tableModelTra;
    private JTable tableBan, tableTra;
    private JTabbedPane tabbedPane;

    // Màu sắc palette cao cấp
    private final Color DARK_BG = new Color(18, 18, 24);
    private final Color CARD_BG = new Color(30, 32, 40);
    private final Color ACCENT = new Color(0, 150, 255);
    private final Color TEXT_PRIMARY = new Color(240, 240, 245);
    private final Color TEXT_SECONDARY = new Color(180, 180, 190);
    private final Color BORDER_COLOR = new Color(60, 63, 70);
    private final Color TABLE_HEADER_BG = new Color(40, 42, 50);
    private final Color TABLE_ROW_BG = new Color(35, 37, 45);
    private final Color TABLE_ALT_ROW_BG = new Color(30, 32, 40);

    public HoaDonBan() {
        setLayout(new BorderLayout());
        setBackground(DARK_BG);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header với hiệu ứng gradient
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();

                // Gradient background
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(20, 22, 30),
                        w, 0, new Color(30, 35, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Hiệu ứng ánh sáng
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fill(new RoundRectangle2D.Double(w / 2 - 150, -50, 300, h + 100, 100, 100));
            }
        };
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("QUẢN LÝ HÓA ĐƠN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 25, 25, 25));
        mainPanel.setBackground(DARK_BG);

        // Tabbed pane custom
        tabbedPane = new JTabbedPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(DARK_BG);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Vẽ indicator
                if (getTabCount() > 0) {
                    Rectangle rect = getUI().getTabBounds(this, getSelectedIndex());
                    g2d.setColor(ACCENT);
                    g2d.fillRect(rect.x, rect.y + rect.height - 3, rect.width, 3);
                }
            }
        };

        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement,
                    Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect,
                    boolean isSelected) {
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement,
                    int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }
        });

        tabbedPane.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        tabbedPane.setForeground(TEXT_SECONDARY);
        tabbedPane.setBackground(DARK_BG);

        // Thêm các tab
        tabbedPane.addTab("HÓA ĐƠN BÁN", createInvoicePanel("BÁN"));
        tabbedPane.addTab("HÓA ĐƠN TRẢ", createInvoicePanel("TRẢ"));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        initTables();
        addDemoData();
    }

    private JPanel createInvoicePanel(String type) {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(DARK_BG);

        // Form card
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
                new EmptyBorder(25, 25, 25, 25)
        ));

        // Form title
        JLabel formTitle = new JLabel("THÔNG TIN HÓA ĐƠN " + type);
        formTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        formTitle.setForeground(TEXT_PRIMARY);
        formTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        formCard.add(formTitle);

        // Form grid
        JPanel formGrid = new JPanel(new GridLayout(0, 2, 20, 15));
        formGrid.setBackground(CARD_BG);

        String[] labels = {"Mã HD:", "Mã KH:", "Mã NV:", "Mã KM:", "Ngày:", "Tổng Tiền:", "Trạng Thái:", "Loại HD:"};

        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setForeground(TEXT_SECONDARY);
            formGrid.add(lbl);

            if (label.equals("Trạng Thái:") || label.equals("Loại HD:")) {
                JComboBox<String> combo = new JComboBox<>(label.equals("Trạng Thái:")
                        ? new String[]{"Đã Thanh Toán", "Chưa Thanh Toán"}
                        : new String[]{"Hóa đơn bán", "Hóa đơn trả"});

                combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                combo.setBackground(DARK_BG);
                combo.setForeground(TEXT_PRIMARY);
                combo.setBorder(new CompoundBorder(
                        new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
                        new EmptyBorder(5, 10, 5, 10)
                ));
                combo.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        setBackground(isSelected ? ACCENT : new Color(40, 42, 50));
                        setForeground(isSelected ? Color.WHITE : TEXT_PRIMARY);
                        return this;
                    }
                });
                formGrid.add(combo);
            } else {
                JTextField txt = new JTextField();
                txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                txt.setForeground(TEXT_PRIMARY);
                txt.setCaretColor(ACCENT);
                txt.setBackground(new Color(40, 42, 50));
                txt.setBorder(new CompoundBorder(
                        new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
                        new EmptyBorder(8, 12, 8, 12)
                ));
                formGrid.add(txt);
            }
        }

        formCard.add(formGrid);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        String[] btnTexts = {"Thêm", "Xóa", "Sửa", "Làm mới", "Tìm kiếm", "Xuất Excel"};
        Color[] btnColors = {ACCENT, new Color(255, 80, 90), new Color(0, 200, 120),
            new Color(100, 110, 130), new Color(180, 120, 220), new Color(0, 180, 180)};

        for (int i = 0; i < btnTexts.length; i++) {
            JButton btn = createModernButton(btnTexts[i], btnColors[i]);
            buttonPanel.add(btn);
        }

        formCard.add(buttonPanel);

        // Table card
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(DARK_BG);
        tableCard.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, BORDER_COLOR),
                new EmptyBorder(0, 0, 0, 0)
        ));

        // Table
        JTable table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(row % 2 == 0 ? TABLE_ROW_BG : TABLE_ALT_ROW_BG);
                c.setForeground(TEXT_PRIMARY);
                return c;
            }
        };

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setBackground(TABLE_ROW_BG);
        table.setForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(50, 52, 60));
        table.setSelectionBackground(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 150));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(36);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        // Table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(TEXT_SECONDARY);
        header.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(TABLE_ROW_BG);

        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Layout panel
        panel.add(formCard, BorderLayout.NORTH);
        panel.add(tableCard, BorderLayout.CENTER);

        return panel;
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };

        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorder(new EmptyBorder(12, 25, 12, 25));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private void initTables() {
        JPanel banPanel = (JPanel) tabbedPane.getComponentAt(0);
        JScrollPane scrollPane = (JScrollPane) ((JPanel) banPanel.getComponent(1)).getComponent(0);
        tableBan = (JTable) scrollPane.getViewport().getView();
        tableModelBan = new DefaultTableModel(new Object[]{"Mã HD", "Mã KH", "Mã NV", "Mã KM", "Ngày", "Tổng tiền", "Trạng thái"}, 0);
        tableBan.setModel(tableModelBan);

        JPanel traPanel = (JPanel) tabbedPane.getComponentAt(1);
        scrollPane = (JScrollPane) ((JPanel) traPanel.getComponent(1)).getComponent(0);
        tableTra = (JTable) scrollPane.getViewport().getView();
        tableModelTra = new DefaultTableModel(new Object[]{"Mã HD", "Mã KH", "Mã NV", "Mã KM", "Ngày", "Tổng tiền", "Trạng thái"}, 0);
        tableTra.setModel(tableModelTra);
    }

    private void addDemoData() {
        Object[][] demoDataBan = {
            {"HD001", "KH001", "NV001", "KM001", "15/05/2023", "1,250,000", "Đã Thanh Toán"},
            {"HD002", "KH002", "NV002", null, "16/05/2023", "850,000", "Đã Thanh Toán"},
            {"HD003", "KH003", "NV001", "KM002", "17/05/2023", "2,150,000", "Chưa Thanh Toán"}
        };
        Object[][] demoDataTra = {
            {"HDT001", "KH001", "NV001", null, "20/05/2023", "250,000", "Đã Hoàn Tiền"},
            {"HDT002", "KH003", "NV002", null, "21/05/2023", "150,000", "Chưa Hoàn Tiền"}
        };
        for (Object[] row : demoDataBan) {
            tableModelBan.addRow(row);
        }
        for (Object[] row : demoDataTra) {
            tableModelTra.addRow(row);
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Cực kỳ quan trọng để giữ màu nền!
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);

        return btn;
    }
}
