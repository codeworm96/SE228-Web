package bookstore.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by codeworm on 4/23/16.
 */
@WebServlet(name = "AddCartServlet", urlPatterns = {"/cart/add"})
public class AddCartServlet extends HttpServlet {
    private void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap cart = (HashMap) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap();
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer num = (Integer)cart.get(id);
        if (num == null) {
            num = 0;
        }

        int add_num = Integer.parseInt(request.getParameter("num"));
        if (add_num > 0) { /* check the num */
            num += add_num;

            cart.put(id, num);
            session.setAttribute("cart", cart);
            response.sendRedirect("/shopping");
        } else {
            request.setAttribute("err_msg", "添加数量至少要为一件");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCart(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCart(request, response);
    }
}
