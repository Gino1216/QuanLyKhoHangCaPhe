package Config;

public class Session {
    // Static variables để lưu thông tin
    private static String username = null;
    private static int role = -1; // Giá trị mặc định, -1 nghĩa là chưa đăng nhập

    // Getter và Setter cho username
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    // Getter và Setter cho role
    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        Session.role = role;
    }

    // Xóa thông tin đăng nhập (khi đăng xuất)
    public static void clear() {
        username = null;
        role = -1;
    }

    // Kiểm tra xem đã đăng nhập chưa
    public static boolean isLoggedIn() {
        return username != null && role != -1;
    }
}