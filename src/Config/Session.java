package Config;

public class Session {
    private static String username = null;
    private static int role = -1; // Giá trị mặc định, -1 nghĩa là chưa đăng nhập

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        if (Session.role != -1) {
            System.out.println("Warning: Attempt to change role from " + Session.role + " to " + role + ", Caller: " + Thread.currentThread().getStackTrace()[2]);
            return; // Không cho phép thay đổi role
        }
        System.out.println("Setting role to: " + role + ", Caller: " + Thread.currentThread().getStackTrace()[2]);
        Session.role = role;
    }

    public static void clear() {
        username = null;
        role = -1;
    }

    public static boolean isLoggedIn() {
        return username != null && role != -1;
    }
}