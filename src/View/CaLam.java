package View;

import Gui.MainFunction;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.border.EmptyBorder;

public class CaLam extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);

    public CaLam() {
        // Set up FlatLaf theme
        FlatLightLaf.setup();

        // Configure JPanel layout
        setLayout(new BorderLayout(0, 8));

        // Create top panel for the toolbar and search panel
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Create table
        scroll = createTable();
        add(scroll, BorderLayout.CENTER);

        // Set background color for the panel
        setBackground(backgroundColor);
    }

    // Method to create top panel (includes function bar and search/filter panel)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Initialize main function toolbar
        functionBar = new MainFunction("ca", new String[]{"create", "update", "delete", "detail", "import", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", this::showAddShiftDialog);
        functionBar.setButtonActionListener("update", this::showEditShiftDialog);

        return topPanel;
    }

    private void showAddShiftDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Ca Làm Việc", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM CA LÀM VIỆC", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Ngày làm", "Trạng thái"};
        JTextField txtName = null;
        JTextField txtStartTime = null;
        JTextField txtEndTime = null;
        JTextField txtDate = null;
        JComboBox<String> cbbStatus = null;

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu
            if (labels[i].equals("Tên ca")) {
                txtName = new JTextField(15);
                txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtName.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtName, gbc);
            } else if (labels[i].equals("Giờ bắt đầu")) {
                txtStartTime = new JTextField(15);
                txtStartTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtStartTime.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtStartTime, gbc);
            } else if (labels[i].equals("Giờ kết thúc")) {
                txtEndTime = new JTextField(15);
                txtEndTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtEndTime.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtEndTime, gbc);
            } else if (labels[i].equals("Ngày làm")) {
                txtDate = new JTextField(15);
                txtDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtDate.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtDate, gbc);
            } else if (labels[i].equals("Trạng thái")) {
                cbbStatus = new JComboBox<>(new String[]{"Hoạt động", "Tạm ngừng"});
                cbbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbStatus.setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbStatus, gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        // Để sử dụng trong ActionListener
        final JTextField finalTxtName = txtName;
        final JTextField finalTxtStartTime = txtStartTime;
        final JTextField finalTxtEndTime = txtEndTime;
        final JTextField finalTxtDate = txtDate;
        final JComboBox<String> finalCbbStatus = cbbStatus;

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm ca làm việc");
        btnAdd.setBackground(new Color(59, 130, 246));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setPreferredSize(new Dimension(180, 50));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnAdd.addActionListener(e -> {
            String name = finalTxtName.getText();
            String startTime = finalTxtStartTime.getText();
            String endTime = finalTxtEndTime.getText();
            String date = finalTxtDate.getText();
            String status = (String) finalCbbStatus.getSelectedItem();

            // Kiểm tra định dạng giờ (HH:mm)
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            timeFormat.setLenient(false);
            try {
                timeFormat.parse(startTime);
                timeFormat.parse(endTime);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Giờ bắt đầu và giờ kết thúc phải có định dạng HH:mm (ví dụ: 08:00)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng ngày (yyyy-MM-dd)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày làm phải có định dạng yyyy-MM-dd (ví dụ: 2025-04-14)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra các trường bắt buộc
            if (name.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm vào bảng với mã ca tự động tăng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int newId = table.getRowCount() + 1;
            model.addRow(new Object[]{newId, name, startTime, endTime, date, status});
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditShiftDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ca làm việc để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String startTime = table.getValueAt(modelRow, 2).toString();
        String endTime = table.getValueAt(modelRow, 3).toString();
        String date = table.getValueAt(modelRow, 4).toString();
        String status = table.getValueAt(modelRow, 5).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Ca Làm Việc", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA CA LÀM VIỆC", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(59, 130, 246));
        header.setOpaque(true);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        dialog.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Mã ca", "Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Ngày làm", "Trạng thái"};
        JTextField txtId = null;
        JTextField txtName = null;
        JTextField txtStartTime = null;
        JTextField txtEndTime = null;
        JTextField txtDate = null;
        JComboBox<String> cbbStatus = null;

        int row = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;

            // Nhãn
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = row;
            formPanel.add(label, gbc);

            // Trường nhập liệu
            if (labels[i].equals("Mã ca")) {
                txtId = new JTextField(15);
                txtId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtId.setPreferredSize(new Dimension(200, 40));
                txtId.setText(id);
                txtId.setEditable(false);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtId, gbc);
            } else if (labels[i].equals("Tên ca")) {
                txtName = new JTextField(15);
                txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtName.setPreferredSize(new Dimension(200, 40));
                txtName.setText(name);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtName, gbc);
            } else if (labels[i].equals("Giờ bắt đầu")) {
                txtStartTime = new JTextField(15);
                txtStartTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtStartTime.setPreferredSize(new Dimension(200, 40));
                txtStartTime.setText(startTime);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtStartTime, gbc);
            } else if (labels[i].equals("Giờ kết thúc")) {
                txtEndTime = new JTextField(15);
                txtEndTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtEndTime.setPreferredSize(new Dimension(200, 40));
                txtEndTime.setText(endTime);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtEndTime, gbc);
            } else if (labels[i].equals("Ngày làm")) {
                txtDate = new JTextField(15);
                txtDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                txtDate.setPreferredSize(new Dimension(200, 40));
                txtDate.setText(date);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(txtDate, gbc);
            } else if (labels[i].equals("Trạng thái")) {
                cbbStatus = new JComboBox<>(new String[]{"Hoạt động", "Tạm ngừng"});
                cbbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                cbbStatus.setPreferredSize(new Dimension(200, 40));
                cbbStatus.setSelectedItem(status);
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(cbbStatus, gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        // Để sử dụng trong ActionListener
        final JTextField finalTxtName = txtName;
        final JTextField finalTxtStartTime = txtStartTime;
        final JTextField finalTxtEndTime = txtEndTime;
        final JTextField finalTxtDate = txtDate;
        final JComboBox<String> finalCbbStatus = cbbStatus;

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnSave = new JButton("Lưu thông tin");
        btnSave.setBackground(new Color(59, 130, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.setPreferredSize(new Dimension(180, 50));
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("Hủy bỏ");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setPreferredSize(new Dimension(180, 50));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnSave.addActionListener(e -> {
            String newName = finalTxtName.getText();
            String newStartTime = finalTxtStartTime.getText();
            String newEndTime = finalTxtEndTime.getText();
            String newDate = finalTxtDate.getText();
            String newStatus = (String) finalCbbStatus.getSelectedItem();

            // Kiểm tra định dạng giờ (HH:mm)
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            timeFormat.setLenient(false);
            try {
                timeFormat.parse(newStartTime);
                timeFormat.parse(newEndTime);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Giờ bắt đầu và giờ kết thúc phải có định dạng HH:mm (ví dụ: 08:00)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra định dạng ngày (yyyy-MM-dd)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(newDate);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày làm phải có định dạng yyyy-MM-dd (ví dụ: 2025-04-14)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra các trường bắt buộc
            if (newName.isEmpty() || newStartTime.isEmpty() || newEndTime.isEmpty() || newDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật dữ liệu vào bảng
            table.setValueAt(newName, modelRow, 1);
            table.setValueAt(newStartTime, modelRow, 2);
            table.setValueAt(newEndTime, modelRow, 3);
            table.setValueAt(newDate, modelRow, 4);
            table.setValueAt(newStatus, modelRow, 5);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Ngày làm", "Trạng thái"});
        cbbFilter.setPreferredSize(new Dimension(100, 25));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(100, 25));

        btnRefresh = new JButton("Làm mới");
        try {
            btnRefresh.setIcon(new ImageIcon(getClass().getResource("/icon/refresh.svg")));
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon refresh.png trong /icon/");
        }
        btnRefresh.setPreferredSize(new Dimension(150, 35));

        searchPanel.add(new JLabel("Lọc theo:"));
        searchPanel.add(cbbFilter);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnRefresh);

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
            table.setRowSorter(null);
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterData();
            }
        });

        return searchPanel;
    }

    private JScrollPane createTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Cột và dữ liệu mẫu
        String[] columns = {"Mã ca", "Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Ngày làm", "Trạng thái"};
        tableModel = new DefaultTableModel(new Object[][]{}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setGridColor(new Color(200, 200, 200));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setBackground(new Color(0x808080));

        // Đặt renderer để xen kẽ màu nền
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    component.setBackground(row % 2 == 0 ? new Color(248, 249, 250) : Color.WHITE);
                }
                return component;
            }
        });

        // Set table sorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return scrollPane;
    }

    private void filterData() {
        String searchText = txtSearch.getText().toLowerCase();
        String selectedFilter = (String) cbbFilter.getSelectedItem();
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        int[] columnIndices;
        if ("Tất cả".equals(selectedFilter)) {
            columnIndices = new int[]{1, 2, 3, 4, 5}; // Bỏ cột Mã ca
        } else {
            int columnIndex = switch (selectedFilter) {
                case "Tên ca" ->
                    1;
                case "Giờ bắt đầu" ->
                    2;
                case "Giờ kết thúc" ->
                    3;
                case "Ngày làm" ->
                    4;
                case "Trạng thái" ->
                    5;
                default ->
                    1;
            };
            columnIndices = new int[]{columnIndex};
        }

        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        // Populate with sample data
        tableModel.addRow(new Object[]{1, "Ca sáng", "06:00", "14:00", "2025-04-14", "Hoạt động"});
        tableModel.addRow(new Object[]{2, "Ca chiều", "14:00", "22:00", "2025-04-14", "Hoạt động"});
        tableModel.addRow(new Object[]{3, "Ca tối", "22:00", "06:00", "2025-04-14", "Hoạt động"});
    }
}
