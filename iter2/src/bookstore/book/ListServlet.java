package bookstore.book;

import bookstore.DBServlet;
import com.google.gson.Gson;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by codeworm on 4/23/16.
 *
 * List all book
 */
@WebServlet(name = "BookListServlet", urlPatterns = "/admin/book/list")
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
            ResultSet cnt_rs = cnt_stmt.executeQuery("SELECT count(*) FROM book;");
            cnt_rs.first();
            res.put("total", cnt_rs.getInt(1));

            PreparedStatement stmt = con.prepareStatement("SELECT id, ISBN, name, category, price FROM book LIMIT ?,?;");
            stmt.setInt(1, offset);
            stmt.setInt(2, rows);

            ResultSet rs = stmt.executeQuery();
            List book_list = new ArrayList<Book>();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("ISBN"));
                book.setName(rs.getString("name"));
                book.setCategory(rs.getString("category"));
                book.setCents(rs.getInt("price"));
                book_list.add(book);
            }

            res.put("rows", book_list);

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
