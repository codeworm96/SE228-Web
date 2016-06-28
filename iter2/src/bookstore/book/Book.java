package bookstore.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codeworm on 4/23/16.
 */
public class Book {
    private int id;
    private String isbn;
    private String name;
    private double price; // derived price for display only, never use it in calculation (float is not accurate)
    private int cents; //accurate price in cents
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

    // no setter for price (it's a derived attribute)
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

    // load a book with its id from database
    public static Book load(Connection con, int id) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id, ISBN, name, category, price FROM book WHERE id=?;");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("ISBN"));
                book.setName(rs.getString("name"));
                book.setCategory(rs.getString("category"));
                book.setCents(rs.getInt("price"));
                return book;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    // load all books
    public static List<Book> loadall(Connection con) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, ISBN, name, category, price FROM book;");
            List book_list = new ArrayList<Book>();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("ISBN"));
                book.setName(rs.getString("name"));
                book.setCategory(rs.getString("category"));
                book.setCents(rs.getInt("price"));
                book_list.add(book);
            }
            return book_list;
        } catch (SQLException e) {
            return null;
        }
    }
}
