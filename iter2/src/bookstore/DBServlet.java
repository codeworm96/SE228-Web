package bookstore;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by codeworm on 4/21/16.
 * Servlet with database connection.
 */
public class DBServlet extends HttpServlet {

    @Resource(name="jdbc/bookstore")
    private DataSource db;

    protected Connection con;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            con = db.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot get database connection");
        }
    }

    @Override
    public void destroy() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
