package bookstore.controller;

import bookstore.entity.User;
import bookstore.service.UserService;
import bookstore.utility.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show_profile(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userService.get(username);
        model.addAttribute(user);
        return "/WEB-INF/view/profile/update.jsp";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update_profile(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userService.get(username);

        if (!request.getParameter("password").equals("")) {
            user.setPassword(request.getParameter("password"));
        }

        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));

        userService.saveOrUpdate(user);

        return "redirect:/profile/";
    }

    @RequestMapping(value = "/avatar")
    @ResponseBody
    public String upload_avatar(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        byte[] content = FileUtil.getUploadContent(request, "avatar");
        return userService.saveOrUpdateAvatar(username, content);
    }
}