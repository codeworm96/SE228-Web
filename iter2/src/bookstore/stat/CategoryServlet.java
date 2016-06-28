package bookstore.stat;

import bookstore.DBServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by codeworm on 5/5/16.
 *
 * Statistic by category
 */
@WebServlet("/stat/category")
public class CategoryServlet extends DBServlet {
    protected void stat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sql = "select category, sum(num*order_item.price) as price " +
                    "from (book join order_item on book.id=book_id) join `order` on order_id=`order`.id " +
                    "WHERE status='paid' GROUP BY category;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List res = new ArrayList();
            while(rs.next()) {
                CategoryInfo item = new CategoryInfo();
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getInt("price"));
                res.add(item);
            }
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        } catch (SQLException e){
            e.printStackTrace();

            HashMap res = new HashMap<String, Object>();
            res.put("msg", "Some errors occured.");
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        stat(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        stat(request, response);
    }
}
