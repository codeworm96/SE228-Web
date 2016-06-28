package bookstore.entity;

import bookstore.utility.OrderStatusConst;

import java.sql.Date;
import java.util.Set;

/**
 * Created by codeworm on 6/9/16.
 */
public class Order {
    private int id;
    private String username;
    private double price; // derived price for display only, never use it in calculation (float is not accurate)
    private int cents; // accurate price in cents
    private int status;
    private Date date;
    private Set items;

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

    public double getPrice() {
        return price;
    }

    public int getCents() { return cents; }

    public void setCents(int cents) {
        this.cents = cents;
        this.price = cents / 100.0; // set price here
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // help function for representation
    public String getStatusInfo() {
        return OrderStatusConst.to_str(getStatus());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set getItems() {
        return items;
    }

    public void setItems(Set items) {
        this.items = items;
    }
}
