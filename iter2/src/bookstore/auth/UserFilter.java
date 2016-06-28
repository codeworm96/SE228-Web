package bookstore.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by codeworm on 4/20/16.
 * Setting the user attribute for all page
 */
@WebFilter(filterName = "UserFilter", urlPatterns = "/*")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest r = (HttpServletRequest)req;
        HttpSession session = r.getSession();
        req.setAttribute("user", session.getAttribute("user"));
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
