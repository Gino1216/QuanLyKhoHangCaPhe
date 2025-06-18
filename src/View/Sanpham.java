/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Config.Session;
import DTO.SanPhamDTO;
import Dao.DaoSP;
import Gui.MainFunction;
import Repository.SanPhamRepo;
import View.Dialog.ChiTietSanPham;
import com.formdev.flatlaf.FlatLightLaf;
import EX.ExSanPham;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author hjepr
 */
public class Sanpham extends JPanel {

    private MainFunction functionBar;
    private JTextField txtSearch;
    private JComboBox<String> cbbFilter;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Color backgroundColor = new Color(240, 247, 250);
    private Color accentColor = new Color(52, 73, 94);



    public Sanpham() {
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
        functionBar = new MainFunction("ncc", new String[]{"create", "update", "delete", "detail", "export"});
        topPanel.add(functionBar, BorderLayout.WEST);

        // Create and add search/filter panel to the top panel
        JPanel searchPanel = createSearchPanel();
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button actions for toolbar
        functionBar.setButtonActionListener("create", () -> {
            if (Session.getRole() == 3) {
                try {
                    showAddProductDialog();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi thêm sản phẩm: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền thêm sản phẩm!",
                        "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        functionBar.setButtonActionListener("update", () -> {
            if (Session.getRole() == 3) {
                try {
                    showEditProductDialog();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi chỉnh sửa sản phẩm: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền chỉnh sửa sản phẩm!",
                        "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        functionBar.setButtonActionListener("delete", () -> {
            if (Session.getRole() == 3) {
                try {
                    DeleteProduct(); // Sửa thành chữ thường để theo chuẩn
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi xóa sản phẩm: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền xóa sản phẩm!",
                        "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        functionBar.setButtonActionListener("detail", this::showCustomerDetails);
        functionBar.setButtonActionListener("export", this::exportToExcel);

        return topPanel;
    }
    private void exportToExcel() {
        ExSanPham.exportSanPhamToExcel("E:/DanhSachSanPham.xlsx"); // Dùng / thay cho \\
    }


    private void showAddProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Sản Phẩm Mới", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("THÊM SẢN PHẨM MỚI", JLabel.CENTER);
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

        // Các nhãn và trường nhập liệu
        String[] labels = {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Tình trạng", "Hạn sử dụng", "Giá nhập", "Giá xuất"
        };

        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

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

            // Trường nhập liệu hoặc combobox
            if (labels[i].equals("Tình trạng")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"Còn hàng", "Sắp hết hàng", "Hết hàng"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[i].setPreferredSize(new Dimension(200, 40));
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));
                // Thêm placeholder cho Hạn sử dụng
                if (labels[i].equals("Hạn sử dụng")) {
                    textFields[i].setText("dd/MM/yyyy");
                    textFields[i].setForeground(Color.GRAY);
                    textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            JTextField textField = (JTextField) evt.getSource();
                            if (textField.getText().equals("dd/MM/yyyy")) {
                                textField.setText("");
                                textField.setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            JTextField textField = (JTextField) evt.getSource();
                            if (textField.getText().isEmpty()) {
                                textField.setText("dd/MM/yyyy");
                                textField.setForeground(Color.GRAY);
                            }
                        }
                    });
                }
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(textFields[i], gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(173, 216, 230));
        JButton btnAdd = new JButton("Thêm sản phẩm"); // Đổi tên nút
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

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra dữ liệu đầu vào
                String maSP = textFields[0].getText().trim();
                String tenSP = textFields[1].getText().trim();
                String soLuongStr = textFields[2].getText().trim();
                String tinhTrang = (String) comboBoxes[3].getSelectedItem();
                String hanSD = textFields[4].getText().trim();
                String giaNhapStr = textFields[5].getText().trim();
                String giaXuatStr = textFields[6].getText().trim();

                // Validate input
                if (maSP.isEmpty() || tenSP.isEmpty() || soLuongStr.isEmpty() || tinhTrang == null || hanSD.isEmpty() || giaNhapStr.isEmpty() || giaXuatStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra số lượng
                int soLuong;
                try {
                    soLuong = Integer.parseInt(soLuongStr);
                    if (soLuong <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Số lượng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra giá nhập/xuất
                float giaNhap, giaXuat;
                try {
                    giaNhap = Float.parseFloat(giaNhapStr);
                    giaXuat = Float.parseFloat(giaXuatStr);
                    if (giaNhap <= 0 || giaXuat <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Giá nhập và giá xuất phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra định dạng hạn sử dụng
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    sdf.parse(hanSD); // Kiểm tra định dạng ngày
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng sản phẩm
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(maSP);
                sanPhamDTO.setTenSP(tenSP);
                sanPhamDTO.setSoLuong(soLuong);
                sanPhamDTO.setTinhTrang(tinhTrang);
                sanPhamDTO.setHanSD(hanSD);
                sanPhamDTO.setGiaNhap(giaNhap);
                sanPhamDTO.setGiaXuat(giaXuat);

                // Thực hiện thêm vào database
                try {
                    DaoSP daoSP = new DaoSP(); // TODO: Cân nhắc dùng singleton
                    if (daoSP.kiemTraMaSPTonTai(maSP)) {
                        JOptionPane.showMessageDialog(dialog,
                                "Mã sản phẩm đã tồn tại! Vui lòng nhập mã khác.",
                                "Trùng mã sản phẩm",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    daoSP.themSanPham(sanPhamDTO);

                    // Thêm vào bảng hiển thị
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{
                            sanPhamDTO.getMaSP(),
                            sanPhamDTO.getTenSP(),
                            sanPhamDTO.getSoLuong(),
                            sanPhamDTO.getTinhTrang(),
                            sanPhamDTO.getHanSD(),
                            sanPhamDTO.getGiaNhap(),
                            sanPhamDTO.getGiaXuat()
                    });

                    JOptionPane.showMessageDialog(dialog, "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
        table.setRowSorter(null);

    }

    private void showEditProductDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để chỉnh sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString(); // Mã sản phẩm
        String name = table.getValueAt(modelRow, 1).toString(); // Tên sản phẩm
        String quantity = table.getValueAt(modelRow, 2).toString(); // Số lượng
        String status = table.getValueAt(modelRow, 3).toString(); // Tình trạng
        String expiryDate = table.getValueAt(modelRow, 4).toString(); // Hạn sử dụng
        String PriceN = table.getValueAt(modelRow, 5).toString(); // Giá
        String PriceX = table.getValueAt(modelRow, 6).toString(); // Giá


        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Sản Phẩm", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("CHỈNH SỬA SẢN PHẨM", JLabel.CENTER);
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

        // Các nhãn và trường nhập liệu
        String[] labels = {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Tình trạng", "Hạn sử dụng", "Giá nhập", "Giá xuất"
        };

        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String>[] comboBoxes = new JComboBox[labels.length];

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

            // Trường nhập liệu hoặc combobox
            if (labels[i].equals("Tình trạng")) {
                comboBoxes[i] = new JComboBox<>(new String[]{"Còn hàng", "Sắp hết hàng", "Hết hàng"});
                comboBoxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                comboBoxes[i].setPreferredSize(new Dimension(200, 40));
                comboBoxes[i].setSelectedItem(status); // Gán giá trị Tình trạng từ bảng
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(comboBoxes[i], gbc);
            } else {
                textFields[i] = new JTextField(15);
                textFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                textFields[i].setPreferredSize(new Dimension(200, 40));

                if (labels[i].equals("Mã sản phẩm")) {
                    textFields[i].setEditable(false); // Không cho phép chỉnh sửa
                    textFields[i].setBackground(new Color(240, 240, 240)); // Nền nhạt để biểu thị chỉ đọc
                }

                // Gán giá trị từ bảng
                switch (i) {
                    case 0:
                        textFields[i].setText(id);
                        break;
                    case 1:
                        textFields[i].setText(name);
                        break;
                    case 2:
                        textFields[i].setText(quantity);
                        break;
                    case 4: // Hạn sử dụng
                        textFields[i].setText(expiryDate);
                        // Thêm placeholder và logic focus cho Hạn sử dụng
                        if (expiryDate.isEmpty()) {
                            textFields[i].setText("dd/MM/yyyy");
                            textFields[i].setForeground(Color.GRAY);
                        } else {
                            textFields[i].setForeground(Color.BLACK);
                        }
                        textFields[i].addFocusListener(new java.awt.event.FocusAdapter() {
                            @Override
                            public void focusGained(java.awt.event.FocusEvent evt) {
                                JTextField textField = (JTextField) evt.getSource();
                                if (textField.getText().equals("dd/MM/yyyy")) {
                                    textField.setText("");
                                    textField.setForeground(Color.BLACK);
                                }
                            }

                            @Override
                            public void focusLost(java.awt.event.FocusEvent evt) {
                                JTextField textField = (JTextField) evt.getSource();
                                if (textField.getText().isEmpty()) {
                                    textField.setText("dd/MM/yyyy");
                                    textField.setForeground(Color.GRAY);
                                }
                            }
                        });
                        break;
                    case 5:
                        textFields[i].setText(PriceN);
                        break;
                    case 6:
                        textFields[i].setText(PriceX);
                        break;
                }
                gbc.gridx = i % 2 == 0 ? 1 : 3;
                gbc.gridy = row;
                formPanel.add(textFields[i], gbc);
            }

            if (i % 2 == 1) {
                row++;
            }
        }

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

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy dữ liệu từ form
                String maSP = textFields[0].getText().trim();
                String tenSP = textFields[1].getText().trim();
                String soLuongStr = textFields[2].getText().trim();
                String tinhTrang = (String) comboBoxes[3].getSelectedItem();
                String hanSD = textFields[4].getText().trim();
                String GiaNhap = textFields[5].getText().trim();
                String GiaXuat = textFields[6].getText().trim();

                float giaNhap = Float.parseFloat(GiaNhap);
                float giaXuat = Float.parseFloat(GiaXuat);



                // Validate dữ liệu
                if (maSP.isEmpty() || tenSP.isEmpty() || soLuongStr.isEmpty() || GiaNhap.isEmpty() || hanSD.isEmpty() || hanSD.equals("dd/MM/yyyy") || GiaXuat.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra số lượng hợp lệ
                int soLuong;
                try {
                    soLuong = Integer.parseInt(soLuongStr);
                    if (soLuong < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Số lượng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Chuyển đổi định dạng Hạn sử dụng từ dd/MM/yyyy sang yyyy-MM-dd
                String formattedHanSD;
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    inputFormat.setLenient(false);
                    Date date = inputFormat.parse(hanSD);
                    formattedHanSD = outputFormat.format(date);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Hạn sử dụng phải có định dạng dd/MM/yyyy hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                // Tạo đối tượng sản phẩm
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(maSP);
                sanPhamDTO.setTenSP(tenSP);
                sanPhamDTO.setSoLuong(soLuong);
                sanPhamDTO.setTinhTrang(tinhTrang);
                sanPhamDTO.setHanSD(formattedHanSD); // Sử dụng ngày đã chuyển đổi
                sanPhamDTO.setGiaNhap(giaNhap);
                sanPhamDTO.setGiaXuat(giaXuat);


                // Thực hiện cập nhật vào database
                try {
                    DaoSP daoSP = new DaoSP();
                    boolean success = daoSP.suaSanPham(sanPhamDTO);

                    if (success) {
                        // Cập nhật lên table nếu thành công
                        table.setValueAt(maSP, modelRow, 0);
                        table.setValueAt(tenSP, modelRow, 1);
                        table.setValueAt(soLuong, modelRow, 2);
                        table.setValueAt(tinhTrang, modelRow, 3);
                        table.setValueAt(hanSD, modelRow, 4); // Hiển thị dd/MM/yyyy trên bảng
                        table.setValueAt(GiaNhap, modelRow, 5);
                        table.setValueAt(GiaXuat, modelRow, 6);


                        JOptionPane.showMessageDialog(dialog, "Cập nhật sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
        table.setRowSorter(null);
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        searchPanel.setBackground(backgroundColor);

        cbbFilter = new JComboBox<>(new String[]{"Tất cả", "Mã SP", "Tên sp", "Số lượng", "Tình trạng", "Hạn sử dụng", "Giá nhập", "Giá xuất"});
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

        // Sự kiện nút làm mới
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cbbFilter.setSelectedIndex(0); // Reset về "Tất cả"
            table.setRowSorter(null);
        });

        // Gắn sự kiện tìm kiếm khi gõ vào ô tìm kiếm
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

    private void filterData() {
        String searchText = txtSearch.getText().toLowerCase(); // Lấy dữ liệu tìm kiếm và chuyển thành chữ thường
        String selectedFilter = (String) cbbFilter.getSelectedItem(); // Lấy giá trị của filter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel()); // Khởi tạo TableRowSorter
        table.setRowSorter(sorter); // Gắn sorter vào table

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null); // Nếu không có từ khóa tìm kiếm, bỏ lọc
            return;
        }

        int[] columnIndices;
        if ("Tất cả".equals(selectedFilter)) {
            // Nếu chọn "Tất cả", lọc tất cả các cột
            columnIndices = new int[]{0, 1, 2, 3, 4, 5, 6};
        } else {
            // Dùng switch để xác định cột cần lọc dựa vào lựa chọn của người dùng
            int columnIndex = switch (selectedFilter) {
                case "Mã SP" -> 0;  // Cột "Mã SP" có chỉ số 0
                case "Tên sp" -> 1;  // Cột "Tên sp" có chỉ số 1
                case "Số lượng" -> 2;  // Cột "Số lượng" có chỉ số 2
                case "Tình trạng" -> 3;  // Cột "Tình trạng" có chỉ số 3
                case "Hạn sử dụng" -> 4;  // Cột "Hạn sử dụng" có chỉ số 4
                case "Giá nhập" -> 5;  // Cột "Giá" có chỉ số 5
                case "Giá xuất" -> 6;  // Cột "Giá" có chỉ số 5

                default -> 0;  // Mặc định lọc theo cột "Mã SP"
            };
            columnIndices = new int[]{columnIndex}; // Lọc theo cột đã chọn
        }

        // Cài đặt bộ lọc với regex không phân biệt chữ hoa chữ thường
        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndices);
        sorter.setRowFilter(rf); // Áp dụng bộ lọc
    }











    private void showCustomerDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String id = table.getValueAt(modelRow, 0).toString();
        String name = table.getValueAt(modelRow, 1).toString();
        String quantity = table.getValueAt(modelRow, 2).toString();
        String state = table.getValueAt(modelRow, 3).toString();
        String expiryDate = table.getValueAt(modelRow, 4).toString();
        String PriceN = table.getValueAt(modelRow, 5).toString();
        String PriceX = table.getValueAt(modelRow, 6).toString();



        ChiTietSanPham detailDialog = new ChiTietSanPham(id, name, quantity, state,expiryDate, PriceN,PriceX);
        detailDialog.setVisible(true);
    }



    private JScrollPane createTable() {
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Columns
        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Tình trạng", "Hạn sử dụng","Giá nhập","Giá xuất"};

        // Tạo model với 0 row ban đầu
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo table với model
        table = new JTable(model);
        customizeTableAppearance();

        // Load data (tách riêng để có thể gọi lại khi cần refresh)
        loadTableData(model);

        return new JScrollPane(table);
    }


    private void loadTableData(DefaultTableModel model) {
        try {
            SanPhamRepo repo = new DaoSP();
            List<SanPhamDTO> danhSach = repo.layDanhSachSanPham();

            model.setRowCount(0); // Xóa dữ liệu cũ

            for (SanPhamDTO sp : danhSach) {
                model.addRow(new Object[]{
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getSoLuong(),
                        sp.getTinhTrang(),
                        sp.getHanSD(),
                        sp.getGiaNhap(),
                        sp.getGiaXuat()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void customizeTableAppearance() {
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }



    private void DeleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sản phẩm cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maSP = table.getValueAt(selectedRow, 0).toString();
        String tenSP = table.getValueAt(selectedRow, 1).toString();

        // Hiển thị hộp thoại xác nhận
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm:\n" +
                        "Mã: " + maSP + "\n" +
                        "Tên: " + tenSP,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                DaoSP daoSP = new DaoSP();

                // Thêm kiểm tra có thể xóa
                if (!kiemTraCoTheXoa(maSP)) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa sản phẩm do có dữ liệu liên quan",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daoSP.xoaSanPham(maSP)) {
                    // Cập nhật giao diện
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this,
                            "Đã xóa sản phẩm thành công",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa sản phẩm thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa sản phẩm: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra có thể xóa (nếu cần)
    private boolean kiemTraCoTheXoa(String maSP) {
        return true;
    }









}