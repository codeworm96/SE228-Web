package bookstore.order;

/**
 * Created by codeworm on 5/3/16.
 */
public class OrderItem {
    private int id;
    private String isbn;
    private String name;
    private double price; // derived price for display only, never use it in calculation (float is not accurate)
    private int cents; //accurate price in cents
    private String category;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // no setter for price (it's a derived attribute)
    public double getPrice() {
        return price;
    }

    public int getCents() { return cents; }

    // set price here
    public void setCents(int cents) {
        this.cents = cents;
        this.price = cents / 100.0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
