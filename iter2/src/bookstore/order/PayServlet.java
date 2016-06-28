package bookstore.order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookstore.DBServlet;
import bookstore.user.User;

/**
 * Created by codeworm on 5/4/16.
 *
 * Pay for an order
 */
@WebServlet(name = "PayServlet", urlPatterns = {"/order/pay"})
public class PayServlet extends DBServlet {
    private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = (User)request.getSession().getAttribute("user");
        String username = user.getUsername();

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT username, status FROM `order` WHERE id=?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                if (!rs.getString("username").equals(username)) {
                    request.setAttribute("err_msg", "不支持代付款");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                if (!rs.getString("status").equals("accepted")) {
                    request.setAttribute("err_msg", "不能重复支付");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                PreparedStatement stmt = con.prepareStatement("UPDATE `order` SET status='paid' WHERE id=?;");
                stmt.setInt(1, id);
                stmt.execute();
                response.sendRedirect("/order/my");
            } else {
                request.setAttribute("err_msg", "订单不存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pay(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pay(request, response);
    }
}
