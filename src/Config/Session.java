package Config;

public class Session {
    private static String username = null;
    private static int role = -1;

    public static String getUsername() {
        return username;
    }

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        if (Session.role != -1) {
            return; // Không cho phép thay đổi role
        }
        Session.role = role;
    }

    public static void setUsername(String username) {
        if (Session.username != null) {
            return;
        }
        Session.username = username;
    }


    public static void clear() {
        username = null;
        role = -1;
    }

    public static boolean isLoggedIn() {
        return username != null && role != -1;
    }
}