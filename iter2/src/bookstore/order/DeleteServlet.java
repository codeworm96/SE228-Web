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

/**
 * Created by codeworm on 5/4/16.
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/order/delete"})
public class DeleteServlet extends DBServlet {
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = (User)request.getSession().getAttribute("user");
        boolean ok; // ok for deletion?
        String status;

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT username, status FROM `order` WHERE id=?;");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                if (rs.getString("status").equals("paid")) {
                    request.setAttribute("err_msg", "已支付订单不能删除!");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                if (rs.getString("username").equals(user.getUsername()) || user.isAdmin()) {
                    ok = true;
                } else {
                    ok = false;
                }
            } else {
                request.setAttribute("err_msg", "订单不存在!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            if (ok) {
                PreparedStatement stmt = con.prepareStatement("DELETE FROM `order` WHERE id=?;");
                stmt.setInt(1, id);
                stmt.executeUpdate();

                // go to last page
                String last_page = request.getHeader("Referer");
                if (last_page == null) {
                    last_page = "/order/my"; // default page
                }

                response.sendRedirect(last_page);
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
        delete(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        delete(request, response);
    }
}
