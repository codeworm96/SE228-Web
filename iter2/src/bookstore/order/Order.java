package bookstore.order;

import java.sql.Date;

/**
 * Created by codeworm on 5/4/16.
 */
public class Order {
    private int id;
    private String username;
    private int cents;
    private String status;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    // get price for display
    public double getPrice() {
        return cents / 100.0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // get status name
    public String getStatusInfo() {
        if (status.equals("accepted")) {
            return "待付款";
        } else if (status.equals("paid")) {
            return "支付完成";
        } else {
            return "未知";
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
