package bookstore.entity;

import java.io.Serializable;

/**
 * Created by codeworm on 6/8/16.
 */
public class OrderItem implements Serializable {
    private int orderId;
    private int bookId;
    private int num;
    private double price; // derived price for display only, never use it in calculation (float is not accurate)
    private int cents; // accurate price in cents
    private Book book;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getPrice() {
        return price;
    }

    public int getCents() { return cents; }

    public void setCents(int cents) {
        this.cents = cents;
        this.price = cents / 100.0; // set price here
    }

    // required for mapping

    @Override
    public int hashCode() {
        final int p1 = 10007;
        final int p2 = 179425033;
        int res = (orderId * p1) % p2;
        res = (res + bookId) % p2;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        OrderItem other = (OrderItem) o;
        return (bookId == other.getBookId() && orderId == other.getOrderId());
    }
}
