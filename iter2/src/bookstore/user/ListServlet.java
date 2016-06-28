package bookstore.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import bookstore.DBServlet;

/**
 * Created by codeworm on 4/22/16.
 *
 * List all user
 */
@WebServlet(name = "UserListServlet", urlPatterns = "/admin/user/list")
public class ListServlet extends DBServlet {

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tmp;
        int page, rows;
        tmp = request.getParameter("page");
        if (tmp != null) {
            page = Integer.parseInt(tmp);
        } else {
            page = 1;
        }
        tmp = request.getParameter("rows");
        if (tmp != null) {
            rows = Integer.parseInt(tmp);
        } else {
            rows = 10;
        }
        int offset = (page - 1) * rows;

        try {
            HashMap res = new HashMap<String, Object>();
            Statement cnt_stmt = con.createStatement();
            ResultSet cnt_rs = cnt_stmt.executeQuery("SELECT count(*) FROM user;");
            cnt_rs.first();
            res.put("total", cnt_rs.getInt(1));

            PreparedStatement stmt = con.prepareStatement("SELECT username, password, email, is_admin FROM user LIMIT ?,?;");
            stmt.setInt(1, offset);
            stmt.setInt(2, rows);

            ResultSet rs = stmt.executeQuery();
            List user_list = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setIsAdmin(rs.getString("is_admin"));
                user_list.add(user);
            }

            res.put("rows", user_list);

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
