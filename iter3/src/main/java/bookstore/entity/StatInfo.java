package bookstore.entity;

/**
 * Created by codeworm on 6/11/16.
 *
 * Unified entry for statistics
 */
public class StatInfo {
    private String label;
    private int cents;
    private double price;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public int getCents() { return cents; }

    public void setCents(int cents) {
        this.cents = cents;
        this.price = cents / 100.0; // set price here
    }
}
