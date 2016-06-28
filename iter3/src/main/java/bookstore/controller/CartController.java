package bookstore.controller;

import bookstore.entity.Book;
import bookstore.entity.Order;
import bookstore.service.BookService;
import bookstore.utility.HibernateUtil;
import bookstore.entity.OrderItem;
import bookstore.utility.OrderStatusConst;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by codeworm on 6/7/16.
 */
@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(@RequestParam int bookid, @RequestParam int num, HttpSession session) {
        Gson gson = new Gson();

        if (num <= 0) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "You have to buy at least one book!");
            return gson.toJson(res);
        }

        HashMap cart = (HashMap) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap();
        }

        Integer cnt = (Integer)cart.get(bookid);
        if (cnt == null) {
            cnt = 0;
        }

        cnt += num;
        cart.put(bookid, cnt);
        session.setAttribute("cart", cart);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return gson.toJson(res);
    }

    @RequestMapping(value = "/")
    public String list() {
        return "/WEB-INF/view/shopping/cart.jsp";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public String data(HttpSession session) {
        Map cart = (HashMap) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap();
        }

        List item_list = new ArrayList();

        Iterator iter = cart.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            OrderItem item = new OrderItem();
            item.setBookId((Integer) entry.getKey());
            item.setNum((Integer) entry.getValue());
            item.setBook(bookService.get(item.getBookId()));
            item_list.add(item);
        }

        return new Gson().toJson(item_list);
    }

    private HashMap parse_cart(HttpServletRequest request) {
        HashMap cart = new HashMap();

        Iterator iter = request.getParameterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            int id = Integer.parseInt((String) entry.getKey());
            int num = Integer.parseInt(((String[]) entry.getValue())[0]);
            if (num > 0) { // discard invalid terms
                cart.put(id, num);
            }
        }

        return cart;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String data(HttpServletRequest request, HttpSession session) {
        session.setAttribute("cart", parse_cart(request));

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/checkout")
    public String checkout(HttpServletRequest request, ModelMap model) {
        Map cart = parse_cart(request);

        if (cart.size() > 0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName(); //get logged in username
            Order order = new Order();
            order.setUsername(username);
            order.setStatus(OrderStatusConst.ACCEPTED);
            order.setDate(new java.sql.Date(new java.util.Date().getTime()));

            Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
            sess.beginTransaction();

            sess.save(order);

            order.setCents(0);
            order.setItems(new HashSet());
            Iterator iter = cart.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                OrderItem item = new OrderItem();
                item.setOrderId(order.getId());
                item.setBookId((Integer) entry.getKey());
                item.setNum((Integer)entry.getValue());
                item.setBook(sess.load(Book.class, item.getBookId()));
                item.setCents(item.getBook().getCents());
                order.getItems().add(item);
                order.setCents(order.getCents() + item.getNum() * item.getCents());
            }

            sess.update(order);

            sess.getTransaction().commit();

            model.addAttribute("order_id", order.getId());
            model.addAttribute("price", order.getPrice());
            return "/WEB-INF/view/shopping/checkout_success.jsp";
        } else {
            model.addAttribute("msg", "购物车为空！");

            return "/WEB-INF/view/error.jsp";
        }
    }
}
