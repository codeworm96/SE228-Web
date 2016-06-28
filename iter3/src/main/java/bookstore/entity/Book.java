package bookstore.entity;

/**
 * Created by codeworm on 4/23/16.
 */
public class Book {
    private int id;
    private String isbn;
    private String name;
    private double price; // derived price for display only, never use it in calculation (float is not accurate)
    private int cents; // accurate price in cents
    private String category;

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

    public double getPrice() {
        return price;
    }

    public int getCents() { return cents; }

    public void setCents(int cents) {
        this.cents = cents;
        this.price = cents / 100.0; // set price here
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
