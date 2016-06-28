package bookstore.auth;

import bookstore.DBServlet;
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

/**
 * Created by codeworm on 4/20/16.
 */
@WebServlet(name = "RegisterServlet", urlPatterns="/register")
public class RegisterServlet extends DBServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setIsAdmin("N");

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO user(username, password, email, is_admin) VALUES(?,?,?,?);");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getIsAdmin());
            stmt.execute();
            response.sendRedirect("/login");

        } catch (SQLException e) {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response); // show register form
    }
}
