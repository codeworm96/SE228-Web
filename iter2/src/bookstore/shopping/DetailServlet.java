package bookstore.shopping;

import bookstore.DBServlet;
import bookstore.book.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by codeworm on 4/23/16.
 */
@WebServlet(name = "DetailServlet", urlPatterns = {"/detail"})
public class DetailServlet extends DBServlet {
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = Book.load(con, Integer.parseInt(request.getParameter("id")));
        if (book != null) {
            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/view/shopping/detail.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        detail(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        detail(request, response);
    }
}
