package bookstore.auth;

import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by codeworm on 5/24/16.
 * 当一个【已经登录】的用户访问某个需要权限的地址（但是权限不够）时会抛出异常，在这里处理。 如果这个请求时ajax请求，那么返回一个json对象。
 * 如果是普通的浏览器页面请求，那么定向到权限不足页面。
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private String errorPage;
    private static final Map<String, Object> map = new HashMap<String, Object>();
    static {
        map.put("success", false);
        map.put("msg", "not permission");
    }

    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) { // 是ajax请求，返回一个json
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(new Gson().toJson(map));
        } else if (errorPage != null) { // 是页面请求，重定向到错误页面
            response.sendRedirect(request.getContextPath() + errorPage);
        }
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}