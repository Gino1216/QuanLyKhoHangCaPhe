package Config;

public class MenuConfig {
    private static final String[] EMPLOYEE_ITEMS = {
            "Sản phẩm", "Phiếu nhập", "Phiếu xuất",
            "Khách hàng", "Nhà cung cấp","Trả hàng"
    };

    private static final String[] ACCOUNTANT_ITEMS = {
            "Dashboard", "Sản phẩm", "Phiếu nhập", "Phiếu xuất",
            "Khách hàng", "Nhà cung cấp","Trả hàng"
    };

    private static final String[] MANAGER_ITEMS = {
            "Dashboard", "Sản phẩm", "Phiếu nhập", "Phiếu xuất",
            "Khách hàng", "Nhà cung cấp", "Nhân viên",
            "Tài khoản", "Phân quyền","Trả hàng"
    };

    public static String[] getMenuItems(int role) {
        switch (role) {
            case 1: // Nhân viên
                return EMPLOYEE_ITEMS;
            case 2: // Kế toán
                return ACCOUNTANT_ITEMS;
            case 3: // Quản lý
                return MANAGER_ITEMS;
            default: // Vai trò không hợp lệ
                return new String[]{}; // Trả về mảng rỗng
        }
    }
}