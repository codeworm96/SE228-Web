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
 * Remove a user
 */
@WebServlet(name = "UserRemoveServlet", urlPatterns = "/admin/user/remove")
public class RemoveServlet extends DBServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM user WHERE username=?;");
            stmt.setString(1, request.getParameter("username"));
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
