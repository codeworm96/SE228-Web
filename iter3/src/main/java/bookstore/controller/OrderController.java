package bookstore.controller;

import bookstore.entity.Order;
import bookstore.entity.OrderItem;
import bookstore.entity.User;
import bookstore.service.UserService;
import bookstore.utility.HibernateUtil;
import bookstore.utility.OrderStatusConst;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * Created by codeworm on 6/9/16.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/my")
    public String my_order(ModelMap model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Order> orders = session.createQuery("select o from Order o where username=:username order by o.date desc")
                .setParameter("username", username)
                .list();

        session.getTransaction().commit();

        model.addAttribute("orders", orders);
        return "/WEB-INF/view/order/my.jsp";
    }

    @RequestMapping(value = "/pay")
    public String pay(@RequestParam int id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Order order = session.load(Order.class, id);
        if (order.getUsername().equals(username)) {
            if (order.getStatus() == OrderStatusConst.ACCEPTED) {
                order.setStatus(OrderStatusConst.PAID);
                session.update(order);
                session.getTransaction().commit();
                return "redirect:/order/my";
            } else {
                session.getTransaction().commit();
                model.addAttribute("msg", "不可重复支付！");
                return "/WEB-INF/view/error.jsp";
            }
        } else {
            session.getTransaction().commit();
            model.addAttribute("msg", "不可代为支付！");
            return "/WEB-INF/view/error.jsp";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam int id, HttpServletRequest request, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userService.get(username);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Order order = session.load(Order.class, id);
        if (order.getUsername().equals(username) || user.isAdmin()) {
            session.delete(order);
            session.getTransaction().commit();

            // go to last page
            String last_page = request.getHeader("Referer");
            if (last_page == null) {
                last_page = "/order/my"; // default page
            }
            return "redirect:" + last_page;
        } else {
            session.getTransaction().commit();
            model.addAttribute("msg", "权限不足！");
            return "/WEB-INF/view/error.jsp";
        }
    }

    @RequestMapping(value = "/detail")
    public String detail(@RequestParam int id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userService.get(username);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Order order = session.load(Order.class, id);
        if (order.getUsername().equals(username) || user.isAdmin()) {
            Iterator<OrderItem> iter = order.getItems().iterator();
            while (iter.hasNext()) {
                OrderItem item = iter.next();
                Hibernate.initialize(item.getBook());
            }
            session.getTransaction().commit();

            model.addAttribute("order", order);
            return "/WEB-INF/view/order/detail.jsp";
        } else {
            session.getTransaction().commit();
            model.addAttribute("msg", "权限不足！");
            return "/WEB-INF/view/error.jsp";
        }
    }
}
