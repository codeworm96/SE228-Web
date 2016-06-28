package bookstore.auth;

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
import bookstore.DBServlet;

/**
 * Created by codeworm on 4/20/16.
 */
@WebServlet(name = "LoginServlet", urlPatterns="/login")
public class LoginServlet extends DBServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String query = "SELECT username, password, email, is_admin FROM user where username=? and password=?;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, request.getParameter("username"));
            stmt.setString(2, request.getParameter("password"));
            ResultSet rs = stmt.executeQuery();

            if (rs != null && rs.next()) { // login success
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setIsAdmin(rs.getString("is_admin"));

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect("/");
            } else { // login failed, try again
                response.sendRedirect("/login");
            }

        } catch (SQLException e) {
            e.printStackTrace();

            // let user retry
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response); // show login form
    }
}
