package bookstore.controller;

import bookstore.entity.User;
import bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by codeworm on 6/2/16.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/WEB-INF/view/auth/login.jsp";
    }

    @RequestMapping(value = "/denied")
    public String denied(ModelMap model) {

        model.addAttribute("msg", "权限不足！");

        return "/WEB-INF/view/error.jsp";
    }

    @RequestMapping(value = "/failed")
    public String failed(ModelMap model) {

        model.addAttribute("msg", "登陆失败！");

        return "/WEB-INF/view/error.jsp";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "/WEB-INF/view/auth/register.jsp";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String do_register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setIsAdmin("N");

        userService.saveOrUpdate(user);

        return "redirect:/auth/login";
    }
}
