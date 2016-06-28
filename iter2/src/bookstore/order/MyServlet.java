package bookstore.order;

import bookstore.DBServlet;
import bookstore.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codeworm on 5/4/16.
 */
@WebServlet(name = "MyServlet", urlPatterns = {"/order/my"})
public class MyServlet extends DBServlet {
    private void myOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        String username = user.getUsername();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id, price, status, date FROM `order` WHERE username=? ORDER BY DATE DESC;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List order_list = new ArrayList<Order>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUsername(username);
                order.setCents(rs.getInt("price"));
                order.setStatus(rs.getString("status"));
                order.setDate(rs.getDate("date"));
                order_list.add(order);
            }

            request.setAttribute("order", order_list);
            request.getRequestDispatcher("/WEB-INF/view/order/my_order.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        myOrder(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        myOrder(request, response);
    }
}
