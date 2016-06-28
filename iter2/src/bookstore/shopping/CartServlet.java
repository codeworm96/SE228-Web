package bookstore.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import bookstore.DBServlet;
import bookstore.book.Book;
import bookstore.order.OrderItem;

/**
 * Created by codeworm on 4/23/16.
 *
 * Show the cart
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends DBServlet {
    private void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map cart = (HashMap) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap();
        }

        List item_list = new ArrayList();

        Iterator iter = cart.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Book book = Book.load(con, (Integer) entry.getKey());
            OrderItem item = new OrderItem();
            item.setId(book.getId());
            item.setIsbn(book.getIsbn());
            item.setName(book.getName());
            item.setCategory(book.getCategory());
            item.setCents(book.getCents());
            item.setNum((Integer)entry.getValue());
            item_list.add(item);
        }

        request.setAttribute("cart", item_list);

        request.getRequestDispatcher("/WEB-INF/view/shopping/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showCart(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showCart(request, response);
    }
}
