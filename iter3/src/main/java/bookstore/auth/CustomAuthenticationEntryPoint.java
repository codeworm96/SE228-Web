package bookstore.auth;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by codeworm on 5/24/16.
 * 当一个【未登录】的用户访问某个需要权限的地址时会抛出异常，在这里处理。如果这个请求时ajax请求，那么返回一个json对象。
 * 如果是普通的浏览器页面请求，那么定向到登录页面。
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private String loginPage;
    private static final Map<String, Object> map = new HashMap<String, Object>();
    static {
        map.put("success", false);
        map.put("msg", "Not logged in");
    }

    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint");
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) { // 是ajax请求，返回一个json
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(new Gson().toJson(map));
        } else if (loginPage != null) { // 是页面请求，重定向到登陆页面
            response.sendRedirect(request.getContextPath() + loginPage);
        }
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}