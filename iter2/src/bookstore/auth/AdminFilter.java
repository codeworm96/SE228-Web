package bookstore.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import bookstore.user.User;

/**
 * Created by codeworm on 4/20/16.
 * Prevent normal user from accessing admin zone
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*", "/stat.jsp", "/stat/*"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest r = (HttpServletRequest)req;
        HttpServletResponse res = (HttpServletResponse)resp;
        HttpSession session = r.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            res.setStatus(403);
            r.setAttribute("err_msg", "权限不足!");
            r.getRequestDispatcher("/error.jsp").forward(r, res);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
