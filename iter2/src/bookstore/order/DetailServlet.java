package bookstore.order;

import bookstore.DBServlet;
import bookstore.book.Book;
import bookstore.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by codeworm on 5/4/16.
 */
@WebServlet("/order/detail")
public class DetailServlet extends DBServlet {
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = (User)request.getSession().getAttribute("user");
        boolean ok; // ok to see?

        try {
            if (!user.isAdmin()) {
                PreparedStatement pstmt = con.prepareStatement("SELECT username FROM `order` WHERE id=?;");
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs != null && rs.next() && rs.getString("username").equals(user.getUsername())) {
                    ok = true;
                } else {
                    ok = false;
                }
            } else { // admin
                ok = true;
            }

            if (ok) {
                PreparedStatement stmt = con.prepareStatement("SELECT username, price, status, date FROM `order` WHERE id=?;");
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs != null && rs.next()) {
                    Order order = new Order();
                    order.setId(id);
                    order.setUsername(rs.getString("username"));
                    order.setCents(rs.getInt("price"));
                    order.setStatus(rs.getString("status"));
                    order.setDate(rs.getDate("date"));
                    request.setAttribute("order", order);

                    List items = new ArrayList();
                    PreparedStatement istmt = con.prepareStatement("SELECT book_id, num, price FROM order_item WHERE order_id=?;");
                    istmt.setInt(1, id);
                    ResultSet irs = istmt.executeQuery();
                    while (irs.next()) {
                        OrderItem item = new OrderItem();
                        item.setId(irs.getInt("book_id"));
                        item.setNum(irs.getInt("num"));
                        item.setCents(irs.getInt("price"));
                        Book book = Book.load(con, item.getId());
                        item.setName(book.getName());
                        item.setIsbn(book.getIsbn());
                        item.setCategory(book.getCategory());
                        items.add(item);
                    }
                    request.setAttribute("item", items);

                    request.getRequestDispatcher("/WEB-INF/view/order/detail.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else { // no permission
                response.setStatus(403);
                request.setAttribute("err_msg", "权限不足!");

                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        detail(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        detail(request, response);
    }
}
