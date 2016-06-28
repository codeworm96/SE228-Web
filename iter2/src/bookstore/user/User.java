package bookstore.user;

/**
 * Created by codeworm on 4/19/16.
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String is_admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String newValue) {
        username = newValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newValue) {
        password = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }

    public String getIsAdmin() {
        return is_admin;
    }

    public void setIsAdmin(String newValue) {
        is_admin = newValue;
    }

    // helper method
    public boolean isAdmin() { return is_admin.equals("Y"); }
}
