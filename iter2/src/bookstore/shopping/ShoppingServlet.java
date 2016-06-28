package bookstore.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bookstore.DBServlet;
import bookstore.book.Book;

/**
 * Created by codeworm on 4/23/16.
 */
@WebServlet(name = "ShoppingServlet", urlPatterns = "/shopping")
public class ShoppingServlet extends DBServlet {
    private void shopping(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> book_list = Book.loadall(con);

        if (book_list != null) {
            request.setAttribute("book", book_list);
            request.getRequestDispatcher("/WEB-INF/view/shopping/shopping.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        shopping(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        shopping(request, response);
    }
}
