package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public final class MainFunction extends JToolBar {

    public HashMap<String, ButtonToolBar> btn = new HashMap<>();

    public MainFunction(String chucnang, String[] listBtn) {
        setFloatable(false); // Không cho phép di chuyển toolbar
        setBackground(new Color(240, 247, 250)); // Màu nền đồng bộ
        initData(listBtn);
    }

    public void initData(String[] listBtn) {
        // Clear existing buttons if any
        this.removeAll();

        // Dynamically create buttons based on the listBtn array
        for (String action : listBtn) {
            ButtonToolBar button = createButton(action);
            if (button != null) {
                btn.put(action, button);
                this.add(button);

                // Thêm separator sau mỗi nút (trừ nút cuối)
                if (!action.equals(listBtn[listBtn.length - 1])) {
                    JSeparator separator = new JSeparator(JSeparator.VERTICAL);
                    separator.setPreferredSize(new Dimension(2, 30)); // Kích thước cố định cho separator
                    separator.setBackground(new Color(240, 247, 250));
                    separator.setForeground(new Color(200, 200, 200));
                    this.add(separator);
                }
            } else {
                System.err.println("Hành động không hợp lệ: " + action);
            }
        }

        // Ensure that the toolbar is correctly displayed after modification
        this.revalidate();
        this.repaint();
    }

    public void setButtonActionListener(String action, Runnable listener) {
        ButtonToolBar button = btn.get(action);
        if (button != null) {
            button.addActionListener(e -> listener.run());
        }
    }

    // Create ButtonToolBar based on action name
    private ButtonToolBar createButton(String action) {
        switch (action) {
            case "create":
                return new ButtonToolBar("THÊM", "add.svg", "create");
            case "delete":
                return new ButtonToolBar("XÓA", "delete.svg", "delete");
            case "update":
                return new ButtonToolBar("SỬA", "edit.svg", "update");
            case "cancel":
                return new ButtonToolBar("HUỶ PHIẾU", "cancel.svg", "delete");
            case "detail":
                return new ButtonToolBar("CHI TIẾT", "detail.svg", "view");
            case "import":
                return new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create");
            case "export":
                return new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view");
            case "phone":
                return new ButtonToolBar("XEM DS", "phone.svg", "view");
            case "sucess":
                return new ButtonToolBar("DUYỆT PHIẾU", "accept-icon.svg", "delete");
            default:
                return null; // If action is not recognized, return null
        }
    }
}