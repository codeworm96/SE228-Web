package bookstore.order;

import bookstore.DBServlet;
import bookstore.book.Book;
import bookstore.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by codeworm on 5/4/16.
 */
@WebServlet(name = "AddServlet", urlPatterns={"/order/add"})
public class AddServlet extends DBServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        List items = new ArrayList();
        int total_price = 0;
        Iterator iter = request.getParameterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            int id = Integer.parseInt((String)entry.getKey());
            int num = Integer.parseInt(((String[])entry.getValue())[0]);
            if (num > 0) { // discard invalid terms
                Book book = Book.load(con, id);
                OrderItem item = new OrderItem();
                item.setId(book.getId());
                item.setIsbn(book.getIsbn());
                item.setName(book.getName());
                item.setCategory(book.getCategory());
                item.setCents(book.getCents());
                item.setNum(num);
                items.add(item);
                total_price += item.getCents() * num;
            }
        }

        if (items.size() > 0) { // cart not empty?
            try {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO `order`(username, price, status, date) VALUES(?,?, 'accepted', ?);", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, user.getUsername());
                stmt.setInt(2, total_price);
                stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                stmt.execute();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    int order_id = rs.getInt(1);

                    for (OrderItem item : (List<OrderItem>) items) {
                        PreparedStatement istmt = con.prepareStatement("INSERT INTO order_item(order_id, book_id, num, price) VALUES(?,?,?,?);");
                        istmt.setInt(1, order_id);
                        istmt.setInt(2, item.getId());
                        istmt.setInt(3, item.getNum());
                        istmt.setInt(4, item.getCents());
                        istmt.execute();
                    }

                    request.setAttribute("order_id", order_id);
                    request.setAttribute("price", total_price);

                    session.setAttribute("cart", new HashMap()); // success, empty the cart

                    request.getRequestDispatcher("/WEB-INF/view/order/success.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();

                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("err_msg", "购物车为空");

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
