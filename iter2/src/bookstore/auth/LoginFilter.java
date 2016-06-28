package bookstore.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by codeworm on 4/20/16.
 * Prevent guest from accessing pages for logged-in users only
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/logout", "/admin/*", "/stat.jsp", "/stat/*", "/order/*", "/cart/*"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest r = (HttpServletRequest)req;
        HttpServletResponse res = (HttpServletResponse)resp;
        HttpSession session = r.getSession();
        if (session.getAttribute("user") == null) {
            res.sendRedirect("/login"); // for guests, let them login
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
