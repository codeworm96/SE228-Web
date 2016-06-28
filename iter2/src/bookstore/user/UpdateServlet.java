package bookstore.user;

import bookstore.DBServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by codeworm on 4/22/16.
 *
 * Update a user
 */
@WebServlet(name = "UserUpdateServlet", urlPatterns = "/admin/user/update")
public class UpdateServlet extends DBServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        if (request.getParameter("is_admin").equals("Y")) {
            user.setIsAdmin("Y");
        } else {
            user.setIsAdmin("N");
        }

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE user SET password=?, email=?, is_admin=? WHERE username=?;");
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getIsAdmin());
            stmt.setString(4, user.getUsername());
            stmt.execute();

            HashMap res = new HashMap<String, Object>();
            res.put("success", true);
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        } catch (SQLException e) {
            e.printStackTrace();

            HashMap res = new HashMap<String, Object>();
            res.put("msg", "Some errors occured.");
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
