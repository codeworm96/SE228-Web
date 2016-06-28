package bookstore.entity;

/**
 * Created by codeworm on 4/19/16.
 */
public class User {
    private String username;
    private String password;
    private String isAdmin;

    private String email;
    private String phone;
    private String address;

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

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String newValue) {
        isAdmin = newValue;
    }

    // helper method
    public boolean isAdmin() { return isAdmin.equals("Y"); }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
