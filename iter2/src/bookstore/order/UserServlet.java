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
@WebServlet("/admin/orders/user")
public class UserServlet extends DBServlet {
    private void UserOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id, price, status, date FROM `order` WHERE username=? ORDER BY DATE DESC;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List order_list = new ArrayList<Order>();
            int total_price = 0;
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUsername(username);
                order.setCents(rs.getInt("price"));
                order.setStatus(rs.getString("status"));
                order.setDate(rs.getDate("date"));
                order_list.add(order);
                total_price += order.getCents();
            }
            int num = order_list.size();
            if (num > 0) {
                request.setAttribute("num", num);
                request.setAttribute("total_price", total_price);
                request.setAttribute("order", order_list);
                request.getRequestDispatcher("/WEB-INF/view/order/user_stat.jsp").forward(request, response);
            } else {
                request.setAttribute("err_msg", "用户不存在或无订单");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserOrder(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserOrder(request, response);
    }
}
